package com.example.links.controller;

import com.example.links.exception.BadLinkException;
import com.example.links.exception.LinkNotFoundException;
import com.example.links.service.LinksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class LinksController {

    private final LinksService linksService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE)
    public String createShortenedLink(@RequestBody String url) throws BadLinkException {
        return linksService.createShortenedLink(url);
    }

    @GetMapping("{id}")
    ResponseEntity<Void> redirect(@PathVariable String id) throws LinkNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(linksService.getFullURL(id)))
                .build();
    }
}
