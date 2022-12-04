package com.elwin013.yala.link.visit;

import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Aggregates.*;

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
                                        hour: "$date.hour"
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
                                                hour: "$_id.date.hour"
                                              }
                                            """)
                                    )
                            ),
                            Projections.computed("metadata", "$_id.metadata"),
                            Projections.computed("count", "$count")
                    )
            ),
            sort(Sorts.descending("_id"))
    );
}
