package com.elwin013.yala.mongo;

import com.elwin013.yala.link.visit.LinkVisit;
import com.elwin013.yala.sequence.SequenceDao;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.TimeSeriesGranularity;
import com.mongodb.client.model.TimeSeriesOptions;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public final class MongoDbHolder {
    private static MongoClient client;
    private static MongoDatabase database;

    public static void init(String url) {
        ConnectionString connectionString = new ConnectionString(url);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        client = MongoClients.create(settings);
        CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );

        database = client.getDatabase("yala").withCodecRegistry(pojoCodecRegistry);

        initSequence();
        initTimeSeriesCollection();
    }

    private static void initSequence() {
        var dao = new SequenceDao(database);
        if (!dao.exists("link_seq")) {
            dao.reset("link_seq", 0);
        };
    }

    private static void initTimeSeriesCollection() {
        var collectionExists = database.listCollectionNames().into(new ArrayList<>()).contains(LinkVisit.COLLECTION_NAME);
        if (collectionExists) {
            return;
        }

        database.createCollection(
                LinkVisit.COLLECTION_NAME,
                new CreateCollectionOptions()
                        .timeSeriesOptions(
                                new TimeSeriesOptions("timestamp")
                                        .granularity(TimeSeriesGranularity.SECONDS)
                                        .metaField("metadata")
                                )
        );
    }

    public static MongoDatabase getDatabase() {
        return database;
    }

    public static MongoClient getClient() {
        return client;
    }
}
