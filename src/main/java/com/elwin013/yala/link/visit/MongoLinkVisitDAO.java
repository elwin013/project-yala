package com.elwin013.yala.link.visit;

import com.elwin013.yala.mongo.MongoDbHolder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.match;

public final class MongoLinkVisitDAO {
    private final MongoCollection<LinkVisit> collection;

    public MongoLinkVisitDAO() {
        this.collection = MongoDbHolder.getDatabase().getCollection(LinkVisit.COLLECTION_NAME, LinkVisit.class);
    }

    public void addVisit(String slug, ObjectId id, String userAgent, String ipAddr, String referer) {
        var metadata = new LinkVisit.Metadata(slug, id);
        var visit = new LinkVisit(null, metadata, Instant.now(), userAgent, ipAddr, referer);

        collection.insertOne(visit);
    }

    public List<LinkVisitCountDto> getVisitsAllTime(ObjectId id) {
        List<Bson> aggr = new ArrayList<>();
        aggr.add(match(Filters.eq("metadata._id", id)));
        aggr.addAll(Aggregations.BY_HOUR_LINK_VISIT);
        return collection.aggregate(aggr, LinkVisitCountDto.class).into(new ArrayList<>());
    }

    public ArrayList<LinkVisitCountDto> getVisitsTimeframe(ObjectId id, Instant from, Instant to) {
        List<Bson> aggr = new ArrayList<>();
        aggr.add(
                match(
                        Filters.and(
                                Filters.eq("metadata._id", id),
                                Filters.gte("timestamp", from),
                                Filters.lt("timestamp", to)
                        )
                )
        );
        aggr.addAll(Aggregations.BY_HOUR_LINK_VISIT);
        return collection.aggregate(aggr, LinkVisitCountDto.class).into(new ArrayList<>());
    }

    public void deleteLinkData(ObjectId id) {
        collection.deleteMany(Filters.and(
                Filters.eq("metadata._id", id)
        ));
    }
}
