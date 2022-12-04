package com.elwin013.yala.link;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.openapi.*;

import java.net.MalformedURLException;

public final class LinkApi {

    @OpenApi(
            description = "Create new Link",
            path = "/api/link",
            tags = {"Link Management"},
            methods = HttpMethod.POST,
            responses = {
                    @OpenApiResponse(status = "200"),
            },
            requestBody = @OpenApiRequestBody(content = @OpenApiContent(from = CreateLinkDto.class), required = true)
    )
    public static void linkCreate(Context ctx) {
        var dto = ctx.bodyAsClass(CreateLinkDto.class);
        try {
            ctx.json(new LinkService().createlink(dto));
        } catch (MalformedURLException e) {
            throw new BadRequestResponse("Invalid url!");
        }
    }




    @OpenApi(
            description = "Gets link details",
            path = "/api/link/{id}/{secretKey}",
            tags = {"Link Management"},
            methods = HttpMethod.GET,
            responses = {
                    @OpenApiResponse(status = "200", content = @OpenApiContent(from = LinkDto.class)),
                    @OpenApiResponse(status = "404", description = "When not found link details"),
            },
            pathParams = {
                    @OpenApiParam(name = "id", description = "Link ID"),
                    @OpenApiParam(name = "secretKey", description = "Secret key to obtain details"),
            }
    )
    public static void linkGet(Context ctx) {
        var id = ctx.pathParam("id");
        var secretKey = ctx.pathParam("secretKey");
        var linkOpt = new LinkService().getLink(id, secretKey);

        if (linkOpt.isPresent()) {
            ctx.json(linkOpt.get());
        } else {
            ctx.status(404).result("Not found");
        }
    }

    @OpenApi(
            description = "Deletes link",
            path = "/api/link/{id}/{secretKey}",
            tags = {"Link Management"},
            methods = HttpMethod.DELETE,
            responses = {
                    @OpenApiResponse(status = "200", content = @OpenApiContent(from = LinkDto.class)),
                    @OpenApiResponse(status = "404", description = "When not found link details"),
            },
            pathParams = {
                    @OpenApiParam(name = "id", description = "Link ID"),
                    @OpenApiParam(name = "secretKey", description = "Secret key to obtain details"),
            }
    )
    public static void linkDelete(Context ctx) {
        var id = ctx.pathParam("id");
        var secretKey = ctx.pathParam("secretKey");
        var isDeleted = new LinkService().deleteLink(id, secretKey);

        if (isDeleted) {
            ctx.status(200).result("Deleted");
        } else {
            ctx.status(404).result("Not found");
        }
    }

    @OpenApi(
            description = "Jumps to link",
            path = "/j/{slug}",
            tags = {"Link Usage"},
            methods = HttpMethod.GET,
            responses = {
                    @OpenApiResponse(status = "400", description = "Invalid link id"),
                    @OpenApiResponse(status = "302", description = "Redirect to targetUrl")
            },
            pathParams = {
                    @OpenApiParam(name = "slug", description = "Link slug")
            },
            queryParams = {

            }
    )
    public static void jump(Context ctx) {
        var pathParam = ctx.pathParam("slug");
        var service = new LinkService();
        var linkOpt = service.getLink(pathParam);

        if (linkOpt.isPresent()) {
            var link = linkOpt.get();
            service.registerVisit(
                    link,
                    ctx.req().getHeader("user-agent"),
                    ctx.req().getRemoteAddr(),
                    ctx.req().getHeader("referer")
            );


            ctx.redirect(link.targetUrl());
        } else {
            throw new BadRequestResponse();
        }

    }

}
