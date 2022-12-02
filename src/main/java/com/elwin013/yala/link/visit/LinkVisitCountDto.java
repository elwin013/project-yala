package com.elwin013.yala.link.visit;

import org.bson.types.ObjectId;

import java.time.Instant;

public final class LinkVisitCountDto {
    Instant id;
    LinkVisitCountDto.MetadataDto metadata;
    Long count;

    public LinkVisitCountDto() {
    }

    public LinkVisitCountDto(Instant id, LinkVisitCountDto.MetadataDto metadata, Long count) {
        this.id = id;
        this.metadata = metadata;
        this.count = count;
    }

    public Instant getId() {
        return id;
    }

    public void setId(Instant id) {
        this.id = id;
    }

    public LinkVisitCountDto.MetadataDto getMetadata() {
        return metadata;
    }

    public void setMetadata(LinkVisitCountDto.MetadataDto metadata) {
        this.metadata = metadata;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", metadata=" + metadata +
                ", count=" + count +
                '}';
    }

    public static class MetadataDto {
        String slug;
        ObjectId id;

        public MetadataDto() {
        }

        public MetadataDto(String slug, ObjectId id) {
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
