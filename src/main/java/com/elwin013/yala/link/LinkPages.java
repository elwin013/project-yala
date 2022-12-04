package com.elwin013.yala.link;

import io.javalin.http.Context;

import java.net.MalformedURLException;
import java.util.Map;

public class LinkPages {
    public static LinkService service = new LinkService();

    public static void previewLink(Context ctx) {
        var link = service.getLink(ctx.pathParam("slug"));
        if (link.isPresent()) {
            service.registerVisit(
                    link.get(),
                    ctx.req().getHeader("user-agent"),
                    ctx.req().getRemoteAddr(),
                    ctx.req().getHeader("referer")
            );
            ctx.render("jump.jte", Map.of("link", link.get()));
        } else {
            ctx.render("404.jte");
        }
    }

    public static void createLink(Context ctx) {
        var targetUrl = ctx.formParam("targetUrl");
        LinkDto link = null;
        try {
            link = service.createlink(new CreateLinkDto(targetUrl));
            ctx.redirect("/link/%s/%s".formatted(link.id(), link.secretKey()));
        } catch (MalformedURLException e) {
            ctx.redirect("/invalid_url");
        }


    }

    public static void showDetails(Context ctx) {
        var link = service.getLink(ctx.pathParam("id"), ctx.pathParam("secretKey"));

        if (link.isPresent()) {
            ctx.render("linkDetails.jte", Map.of("link", link.get()));
        } else {
            ctx.render("404.jte");
        }
    }

    public static void showAnalitycs(Context ctx) {
        var link = service.getLink(ctx.pathParam("id"), ctx.pathParam("secretKey"));
        if (link.isPresent()) {
            var visits = service.getLinkVisits(link.get());
            ctx.render("linkVisits.jte", Map.of("link", link.get(), "visits", visits));
        } else {
            ctx.render("404.jte");
        }
    }
}
