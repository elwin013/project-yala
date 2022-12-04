package com.elwin013.yala;

import com.elwin013.yala.link.LinkApi;
import com.elwin013.yala.link.LinkPages;
import com.elwin013.yala.link.visit.LinkVisitApi;
import com.elwin013.yala.mongo.MongoDbHolder;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.json.JavalinJackson;
import io.javalin.openapi.plugin.OpenApiConfiguration;
import io.javalin.openapi.plugin.OpenApiPlugin;
import io.javalin.openapi.plugin.swagger.SwaggerConfiguration;
import io.javalin.openapi.plugin.swagger.SwaggerPlugin;
import io.javalin.rendering.template.JavalinJte;
import io.javalin.validation.JavalinValidation;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static io.javalin.apibuilder.ApiBuilder.*;

public class App {
    public static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").withZone(ZoneId.of("UTC"));
    public static String APP_FRONTEND_URL;
    public static boolean PREVIEW_MODE;

    public static void main(String[] args) {
        var isProduction = System.getenv().get("PRODUCTION") != null;
        var appUrl = System.getenv().get("APP_FRONTEND_URL");
        var mongoUrl = System.getenv().get("MONGO_DB_URL");
        PREVIEW_MODE = System.getenv().get("PREVIEW_MODE") != null && Boolean.parseBoolean(System.getenv().get("PREVIEW_MODE"));

        assert appUrl != null : "APP_FRONTEND_URL is null";
        assert mongoUrl != null : "MONGO_DB_URL is null";

        APP_FRONTEND_URL = appUrl.endsWith("/") ? appUrl.substring(0, appUrl.length() - 1) : appUrl;


        MongoDbHolder.init(mongoUrl);

        if (isProduction) {
            JavalinJte.init(TemplateEngine.createPrecompiled(ContentType.Html));
        } else {
            JavalinJte.init();
        }


        var app = Javalin.create(cfg -> {
            openApiConfig(cfg);
            configureJacksonMapper(cfg);
        });

        JavalinValidation.register(Instant.class, text -> Instant.from(DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(text)));


        app.routes(() -> {
            path("/api/link", () -> {
                post("/", LinkApi::linkCreate);
                get("/{id}/{secretKey}", LinkApi::linkGet);
                delete("/{id}/{secretKey}", LinkApi::linkDelete);
            });
            path("/api/link-visit", () -> {
                get("/{id}/{secretKey}", LinkVisitApi::visitsGet);
            });
        });

        app.routes(() -> {
            get("/j/{slug}", LinkApi::jump);
        });

        app.routes(() -> {
            get("/", ctx -> ctx.render("index.jte"));
            get("/invalid_url", ctx -> ctx.render("invalidUrl.jte"));
            get("/create_link", ctx -> ctx.redirect("/"));
            post("/create_link", LinkPages::createLink);
            get("/link/{id}/{secretKey}", LinkPages::showDetails);
            get("/link/{id}/{secretKey}/visits", LinkPages::showVisits);
            get("/delete/{id}/{secretKey}", LinkPages::linkDelete);
            get("/j/{slug}/preview", LinkPages::previewLink);
        });

        if (PREVIEW_MODE) {
            app.get("/preview_mode", ctx -> ctx.render("previewMode.jte"));
        }

        app.error(404, ctx -> ctx.render("404.jte"));
        app.error(500, ctx -> ctx.render("error.jte"));


        app.start(7070);
    }

    private static void configureJacksonMapper(JavalinConfig config) {
        var objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // to have ISO-8601 dates representation
        objectMapper.registerModule(new JavaTimeModule());

        var objectIdDeserializerModule = new SimpleModule();
        objectIdDeserializerModule.addSerializer(ObjectId.class, new JsonSerializer<>() {
            @Override
            public void serialize(ObjectId value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString(value.toHexString());
            }
        });
        objectMapper.registerModule(objectIdDeserializerModule);

        config.jsonMapper(new JavalinJackson(objectMapper));
    }

    private static void openApiConfig(JavalinConfig config) {
        OpenApiConfiguration openApiConfiguration = new OpenApiConfiguration();
        openApiConfiguration.getInfo().setTitle("Project YALA");
        openApiConfiguration.setDocumentationPath("/swagger/api");
        config.plugins.register(new OpenApiPlugin(openApiConfiguration));

        SwaggerConfiguration swaggerConfiguration = new SwaggerConfiguration();
        swaggerConfiguration.setUiPath("/swagger/ui");
        swaggerConfiguration.setDocumentationPath(openApiConfiguration.getDocumentationPath());
        config.plugins.register(new SwaggerPlugin(swaggerConfiguration));
    }
}
