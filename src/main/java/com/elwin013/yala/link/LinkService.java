package com.elwin013.yala.link;

import com.elwin013.yala.link.visit.LinkVisitsDto;
import com.elwin013.yala.link.visit.MongoLinkVisitDAO;
import com.elwin013.yala.sequence.SequenceDao;
import org.bson.types.ObjectId;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public final class LinkService {
    MongoLinkDAO linkDao = new MongoLinkDAO();
    MongoLinkVisitDAO linkVisitDAO = new MongoLinkVisitDAO();
    SequenceDao sequenceDao = new SequenceDao();

    public LinkDto createlink(CreateLinkDto dto) throws MalformedURLException {
        var url = new URL(dto.targetUrl());
        if (!"http".equals(url.getProtocol()) && !"https".equals(url.getProtocol())) {
            throw new MalformedURLException("Invalid protocol");
        }
        var sequenceNumber = sequenceDao.getNextValue("link_seq");
        return LinkDto.fromEntity(linkDao.createLink(dto.targetUrl().trim(), sequenceNumber));
    }

    public Optional<LinkDto> getLink(String id, String secretKey) {
        return new MongoLinkDAO().findLink(new ObjectId(id), secretKey).flatMap(link -> Optional.of(LinkDto.fromEntity(link)));
    }

    public Optional<LinkDto> getLink(String slug) {
        return new MongoLinkDAO().getLink(slug).flatMap(link -> Optional.of(LinkDto.fromEntity(link)));
    }

    public boolean deleteLink(String id, String secretKey) {
        Optional<Link> linkOpt = linkDao.findLink(new ObjectId(id), secretKey);

        if (linkOpt.isPresent()) {
            linkDao.deleteLink(linkOpt.get().id);
            linkVisitDAO.deleteLinkData(linkOpt.get().id);
            return true;
        } else {
            return false;
        }
    }

    public void registerVisit(LinkDto link, String header, String remoteAddr, String referer) {
        var id = new ObjectId(link.id());
        linkDao.incrementClicks(id);
        linkVisitDAO.addVisit(
                link.slug(), id, header, remoteAddr, referer
        );
    }

    public LinkVisitsDto getLinkVisits(LinkDto linkDto) {
        var dto = new LinkVisitsDto();
        dto.setVisits(linkVisitDAO.getVisitsAllTime(new ObjectId(linkDto.id())));
        return dto;
    }
}
