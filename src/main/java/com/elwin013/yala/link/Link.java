package com.elwin013.yala.link;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.time.Instant;

public final class Link {
    public static final String COLLECTION_NAME = "link";
    @BsonId
    ObjectId id;
    String targetUrl;
    String slug;
    String secretKey;
    Long clicks;
    Instant createdAt;

    public Link() {
    }

    public Link(ObjectId id, String targetUrl, String slug, String secretKey, Long clicks, Instant createdAt) {
        this.id = id;
        this.targetUrl = targetUrl;
        this.slug = slug;
        this.secretKey = secretKey;
        this.clicks = clicks;
        this.createdAt = createdAt;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Long getClicks() {
        return clicks;
    }

    public void setClicks(Long clicks) {
        this.clicks = clicks;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
