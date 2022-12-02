package com.elwin013.yala.link.visit;

import com.elwin013.yala.link.MongoLinkDAO;
import io.javalin.http.Context;
import io.javalin.openapi.*;
import org.bson.types.ObjectId;

import java.time.Instant;

public final class LinkVisitApi {
    @OpenApi(
            description = "Gets Link visits",
            path = "/api/link-visit/{id}/{secretKey}",
            tags = {"Link Statistics"},
            methods = HttpMethod.GET,
            responses = {
                    @OpenApiResponse(status = "200", content = @OpenApiContent(from = LinkVisitsDto.class)),
                    @OpenApiResponse(status = "404", description = "When not found link details"),
            },
            pathParams = {
                    @OpenApiParam(name = "id", description = "Link ID"),
                    @OpenApiParam(name = "secretKey", description = "Secret key to obtain details"),
            },
            queryParams = {
                    @OpenApiParam(name = "fromDate", description = "Date from (inclusive) in ISO format"),
                    @OpenApiParam(name = "toDate", description = "Date to (exclusive) in ISO format"),
            }
    )
    public static void visitsGet(Context ctx) {
        var id = ctx.pathParam("id");
        var secretKey = ctx.pathParam("secretKey");

        var link = new MongoLinkDAO().findLink(new ObjectId(id), secretKey);
        if (link.isEmpty()) {
            ctx.status(404).result("Not found");
            return;
        }

        var fromDate = ctx.queryParamAsClass("fromDate", Instant.class).allowNullable();
        var toDate = ctx.queryParamAsClass("toDate", Instant.class).allowNullable();

        var dao = new MongoLinkVisitDAO();

        var dto = new LinkVisitsDto();

        if (fromDate.get() == null && toDate.get() == null) {
            dto.setVisits(dao.getVisitsAllTime(new ObjectId(id)));
        } else if (fromDate.get() != null && toDate.get() != null) {
            dto.setVisits(dao.getVisitsTimeframe(new ObjectId(id), fromDate.get(), toDate.get()));
        } else {
            ctx.status(400).result("Invalid fromDate/toDate");
            return;
        }

        ctx.json(dto);


    }
}
