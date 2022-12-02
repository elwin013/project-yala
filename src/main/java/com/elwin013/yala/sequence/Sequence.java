package com.elwin013.yala.sequence;

import org.bson.codecs.pojo.annotations.BsonId;

public record Sequence(@BsonId String name, Long value) {

}
