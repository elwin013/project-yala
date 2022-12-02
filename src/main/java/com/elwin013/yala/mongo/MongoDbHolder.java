package com.elwin013.yala.mongo;

import com.elwin013.yala.sequence.SequenceDao;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

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
    }

    private static void initSequence() {
        var dao = new SequenceDao(database);
        if (!dao.exists("link_seq")) {
            dao.reset("link_seq", 0);
        };
    }

    public static MongoDatabase getDatabase() {
        return database;
    }

    public static MongoClient getClient() {
        return client;
    }
}
