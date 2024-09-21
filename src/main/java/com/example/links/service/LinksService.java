package com.example.links.service;

import com.example.links.exception.BadLinkException;
import com.example.links.exception.LinkNotFoundException;

public interface LinksService {
    public String createShortenedLink(String url) throws BadLinkException;

    public String getFullURL(String id) throws LinkNotFoundException;
}
