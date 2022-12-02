package com.elwin013.yala.link.visit;

import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.project;

public final class Aggregations {
    public static List<Bson> BY_MINUTE_LINK_VISIT = Arrays.asList(
            project(
                    Projections.fields(
                            Projections.computed("date", new Document("$dateToParts", new Document("date", "$timestamp"))),
                            Projections.computed("metadata", "$metadata")
                    )
            ),
            // Docs says:
            // The Java driver provides builders for accumulator expressions for use with $group.
            // You must declare all other expressions in JSON format or compatible document format.
            // https://www.mongodb.com/docs/drivers/java/sync/current/fundamentals/aggregation/#aggregation-expression-example
            group(Document.parse("""
                                    {
                                      date: {
                                        year: "$date.year",
                                        month: "$date.month",
                                        day: "$date.day",
                                        hour: "$date.hour",
                                        minute: "$date.minute",
                                      },
                                      metadata: "$metadata"
                                    }
                                """),
                    Accumulators.sum("count", 1L)
            ),
            project(
                    Projections.fields(

                            Projections.computed("_id",
                                    new Document("$dateFromParts",
                                    Document.parse("""
                                            {
                                                year: "$_id.date.year",
                                                month: "$_id.date.month",
                                                day: "$_id.date.day",
                                                hour: "$_id.date.hour",
                                                minute: "$_id.date.minute",
                                              }
                                            """)
                                    )
                            ),
                            Projections.computed("metadata", "$_id.metadata"),
                            Projections.computed("count", "$count")
                    )
            )
    );
}
