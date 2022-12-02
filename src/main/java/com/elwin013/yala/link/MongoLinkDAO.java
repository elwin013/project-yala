package com.elwin013.yala.link;

import com.elwin013.yala.mongo.MongoDbHolder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public final class MongoLinkDAO {
    private final MongoCollection<Link> collection;

    public MongoLinkDAO() {
        this.collection = MongoDbHolder.getDatabase().getCollection(Link.COLLECTION_NAME, Link.class);
    }
    public Link createLink(String targetUrl, String secretKey, long sequenceNumber) {
        var key = secretKey != null ? secretKey : UUID.randomUUID().toString();
        var id = ObjectId.get();
        var slug = sequenceNumber + UUID.randomUUID().toString().replace("-", "").substring(0, 6);

        var entity = new Link(id, targetUrl, slug, key, 0L, Instant.now());

        collection.insertOne(entity);
        return entity;
    }

    public Optional<Link> getLink(String slug) {
        var link = collection.find(Filters.eq("slug", slug)).first();
        return Optional.ofNullable(link);

    }

    public void incrementClicks(ObjectId id) {
        collection.updateOne(Filters.eq("_id", id), Updates.inc("clicks", 1));
    }

    public Optional<Link> findLink(ObjectId id, String adminKey) {
        var link = collection.find(
                        Filters.and(
                                Filters.eq("_id", id),
                                Filters.eq("secretKey", adminKey)
                        )
                )
                .first();

        return Optional.ofNullable(link);
    }

    public void deleteLink(ObjectId id) {
        collection.deleteOne(Filters.and(
                Filters.eq("_id", id)
        ));
    }
}
