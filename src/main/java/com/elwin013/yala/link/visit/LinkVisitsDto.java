package com.elwin013.yala.link.visit;

import java.time.Instant;
import java.util.List;

public class LinkVisitsDto {
    List<LinkVisitCountDto> visits;

    Instant from;
    Instant to;

    public LinkVisitsDto() {
    }

    public LinkVisitsDto(List<LinkVisitCountDto> visits, Instant from, Instant to) {
        this.visits = visits;
        this.from = from;
        this.to = to;
    }

    public List<LinkVisitCountDto> getVisits() {
        return visits;
    }

    public void setVisits(List<LinkVisitCountDto> visits) {
        this.visits = visits;
    }

    public Instant getFrom() {
        return from;
    }

    public void setFrom(Instant from) {
        this.from = from;
    }

    public Instant getTo() {
        return to;
    }

    public void setTo(Instant to) {
        this.to = to;
    }
}
