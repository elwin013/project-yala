package com.elwin013.yala.link.visit;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.time.Instant;

public final class LinkVisit {
    public static final String COLLECTION_NAME = "link_visit";

    @BsonId
    ObjectId id;
    Metadata metadata;
    Instant timestamp;

    String userAgent;
    String referer;
    String ipAddrHash;

    public LinkVisit() {
    }

    public LinkVisit(ObjectId id, Metadata metadata, Instant timestamp, String userAgent, String referer, String ipAddrHash) {
        this.id = id;
        this.metadata = metadata;
        this.timestamp = timestamp;
        this.userAgent = userAgent;
        this.referer = referer;
        this.ipAddrHash = ipAddrHash;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getIpAddrHash() {
        return ipAddrHash;
    }

    public void setIpAddrHash(String ipAddrHash) {
        this.ipAddrHash = ipAddrHash;
    }

    public static class Metadata {
        String slug;
        ObjectId id;

        public Metadata() {
        }

        public Metadata(String slug, ObjectId id) {
            this.slug = slug;
            this.id = id;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public ObjectId getId() {
            return id;
        }

        public void setId(ObjectId id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Metadata{" +
                    "slug='" + slug + '\'' +
                    ", id=" + id +
                    '}';
        }
    }
}
