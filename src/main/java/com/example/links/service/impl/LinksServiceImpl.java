package com.example.links.service.impl;

import com.example.links.exception.BadLinkException;
import com.example.links.exception.LinkNotFoundException;
import com.example.links.model.Link;
import com.example.links.repository.LinksRepository;
import com.example.links.service.LinksService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LinksServiceImpl implements LinksService {

    private final LinksRepository linksRepository;

    @Value("${custom-properties.link}")
    private String hostAddress;

    @Override
    public String createShortenedLink(String url) throws BadLinkException {
        url = validateURL(url);
        Link link = new Link();
        link.setUrl(url);
        Optional<Link> optionalLink = linksRepository.findByUrl(url);
        if (optionalLink.isPresent()) {
            link = optionalLink.get();
        } else {
            link = linksRepository.save(link);
        }

        return hostAddress + link.getId();
    }

    private String validateURL(String url) throws BadLinkException {
        try {
            URI uri = URI.create(url);
            if (uri.getScheme() == null) {
                url = "http://" + url;
                uri = URI.create(url);
            }
            return url;
        } catch (IllegalArgumentException ex) {
            throw new BadLinkException("Please provide a valid URL");
        }


    }

    @Override
    public String getFullURL(String id) throws LinkNotFoundException {
        return linksRepository.findById(id).orElseThrow(() ->
                new LinkNotFoundException("Not existent link: " + hostAddress + id)
        ).getUrl();
    }
}
