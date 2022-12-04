package com.elwin013.yala.link;

import com.elwin013.yala.App;

import java.time.Instant;

public record LinkDto(
        String id,
        String targetUrl,
        String shortUrl,
        String shortWithPrevievUrl,
        String detailsUrl,
        String deleteUrl,
        String slug,
        String secretKey,
        Long clicks,
        Instant createdAt

) {
    public static LinkDto fromEntity(Link entity) {
        return new LinkDto(
                entity.id.toHexString(),
                entity.targetUrl,
                App.APP_FRONTEND_URL + "/j/" + entity.slug,
                App.APP_FRONTEND_URL + "/j/" + entity.slug + "/preview",
                App.APP_FRONTEND_URL + "/link/" + entity.id.toHexString() + "/" + entity.secretKey + "/visits",
                App.APP_FRONTEND_URL + "/delete/" + entity.id.toHexString() + "/" + entity.secretKey,
                entity.slug,
                entity.secretKey,
                entity.clicks,
                entity.createdAt
        );
    }
}
