package com.elwin013.yala.link;

import java.time.Instant;

public record LinkDto(
        String id,
        String targetUrl,
        String slug,
        String adminKey,
        Long clicks,
        Instant createdAt

) {
    public static LinkDto fromEntity(Link entity) {
        return new LinkDto(
                entity.id.toHexString(),
                entity.targetUrl,
                entity.slug,
                entity.secretKey,
                entity.clicks,
                entity.createdAt
        );
    }
}
