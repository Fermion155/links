package com.example.links.repository;

import com.example.links.model.Link;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class LinksRepositoryTest {

    @Autowired
    private LinksRepository linksRepository;

    Link link;

    @BeforeEach
    public void init() {
        System.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        System.setProperty("spring.h2.console.enabled", "true");
        System.setProperty("spring.h2.console.path", "/h2-console");
        link = new Link();
        link.setUrl("myUrl");
    }

    @Test
    public void saveLink() {
        link = linksRepository.save(link);

        assertNotNull(link.getId());
        assertEquals(link.getUrl(), "myUrl");
        assertEquals(link.getId().length(), 7);

    }

    @Test
    public void saveTwoLinks() {
        link = linksRepository.save(link);
        Link link2 = new Link();
        link2.setUrl("myUrl2");
        link2 = linksRepository.save(link2);
        assertNotNull(link.getId());
        assertEquals(link.getUrl(), "myUrl");
        assertEquals(link.getId().length(), 7);
        assertNotNull(link2.getId());
        assertEquals(link2.getUrl(), "myUrl2");
        assertEquals(link2.getId().length(), 7);
        Optional<Link> optionalLink = linksRepository.findById(link.getId());
        Optional<Link> optionalLink2 = linksRepository.findById(link2.getId());
        assertNotNull(optionalLink.get().getId());
        assertEquals(optionalLink.get().getUrl(), "myUrl");
        assertEquals(optionalLink.get().getId().length(), 7);
        assertNotNull(optionalLink2.get().getId());
        assertEquals(optionalLink2.get().getUrl(), "myUrl2");
        assertEquals(optionalLink2.get().getId().length(), 7);
    }

    @Test
    public void saveTwoLinksWithSameUrl() {
        link = linksRepository.save(link);
        Link link2 = new Link();
        link2.setUrl(link.getUrl());
        linksRepository.save(link2);

        assertThrows(Exception.class, () -> {
            linksRepository.save(link2);
            linksRepository.flush();
        });
    }

    @Test
    public void findLinkById() {
        link = linksRepository.save(link);
        Optional<Link> optionalLink = linksRepository.findById(link.getId());

        assertTrue(optionalLink.isPresent());
        assertEquals(optionalLink.get(), link);
        assertNotNull(optionalLink.get().getId());
        assertNotNull(optionalLink.get().getUrl());
    }

    @Test
    public void findLinkByUrl() {
        link = linksRepository.save(link);
        Optional<Link> optionalLink = linksRepository.findByUrl("myUrl");

        assertTrue(optionalLink.isPresent());
        assertEquals(optionalLink.get(), link);
        assertNotNull(optionalLink.get().getId());
        assertEquals(optionalLink.get().getUrl(), "myUrl");
    }
}
