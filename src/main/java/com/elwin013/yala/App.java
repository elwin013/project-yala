package com.elwin013.yala;

import com.elwin013.yala.link.LinkApi;
import com.elwin013.yala.mongo.MongoDbHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.json.JavalinJackson;
import io.javalin.openapi.plugin.OpenApiConfiguration;
import io.javalin.openapi.plugin.OpenApiPlugin;
import io.javalin.openapi.plugin.swagger.SwaggerConfiguration;
import io.javalin.openapi.plugin.swagger.SwaggerPlugin;
import io.javalin.validation.JavalinValidation;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

import static io.javalin.apibuilder.ApiBuilder.*;

public class App {
    public static String APP_FRONTEND_URL;

    public static void main(String[] args) {
        var appUrl = System.getenv().get("APP_FRONTEND_URL");
        APP_FRONTEND_URL = appUrl.endsWith("/") ? appUrl.substring(0, appUrl.length() - 1) : appUrl;

        MongoDbHolder.init(System.getenv().get("MONGO_DB_URL"));

        var app = Javalin.create(cfg -> {
            openApiConfig(cfg);
            configureJacksonMapper(cfg);
        });

        JavalinValidation.register(Instant.class, text -> {
            return Instant.from(DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(text));
        });


        app.get("/", ctx -> ctx.result("Hello World"));
        app.routes(() -> {
            path("/api/link", () -> {
                post("/", LinkApi::linkCreate);
                get("/{id}/{secretKey}", LinkApi::linkGet);
                delete("/{id}/{secretKey}", LinkApi::linkDelete);
            });
        });

        app.routes(() -> {
            get("/j/{slug}", LinkApi::jump);
        });


        app.start(7070);
    }

    private static void configureJacksonMapper(JavalinConfig config) {
        var objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // to have ISO-8601 dates representation
        objectMapper.registerModule(new JavaTimeModule());

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