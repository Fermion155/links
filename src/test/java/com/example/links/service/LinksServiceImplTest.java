package com.example.links.service;

import com.example.links.exception.BadLinkException;
import com.example.links.exception.LinkNotFoundException;
import com.example.links.model.Link;
import com.example.links.repository.LinksRepository;
import com.example.links.service.impl.LinksServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class LinksServiceImplTest {

    @MockBean
    private LinksRepository linksRepository;

    @Autowired
    private LinksServiceImpl linksServiceImpl;

    @BeforeAll
    public static void init() {
        System.setProperty("custom-properties.link", "hostAdress");
    }

    @Test
    public void createShortenedLinkTestWhenSchemeIsNotProvided() throws Exception {
        String url = "testThisUrl";
        Link link = new Link("5fdas15", "http://" + url);
        Mockito.when(linksRepository.findByUrl("http://" + url)).thenReturn(Optional.ofNullable(null));
        Mockito.when(linksRepository.save(any())).thenReturn(link);
        assertEquals(linksServiceImpl.createShortenedLink(url), "hostAdress" + link.getId());
    }

    @Test
    public void createShortenedLinkTestWhenSchemeIsProvided() throws Exception {
        String url = "http://testThisUrl";
        Link link = new Link("5fdas15", url);
        Mockito.when(linksRepository.findByUrl(url)).thenReturn(Optional.ofNullable(null));
        Mockito.when(linksRepository.save(any(Link.class))).thenReturn(link);
        assertEquals(linksServiceImpl.createShortenedLink(url), "hostAdress" + link.getId());
    }

    @Test
    public void createShortenedLinkTestWhenLinkAlreadyExistsInDatabase() throws Exception {
        String url = "http://testThisUrl";
        Link link = new Link("5fdas15", url);
        Mockito.when(linksRepository.findByUrl(url)).thenReturn(Optional.of(link));
        assertEquals(linksServiceImpl.createShortenedLink(url), "hostAdress" + link.getId());
    }

    @Test
    public void createShortenedLinkTestWhenBadLinkIsProvided() throws Exception {
        String url = "://testThisUrl";
        Exception ex = assertThrows(BadLinkException.class, () -> linksServiceImpl.createShortenedLink(url));
        assertEquals(ex.getMessage(), "Please provide a valid URL");
    }

    @Test
    public void getFullURLWhenUrlExists() throws Exception {
        String url = "testThisUrl";
        Link link = new Link("5fdas15", "http://" + url);
        Mockito.when(linksRepository.findById(link.getId())).thenReturn(Optional.of(link));
        assertEquals(linksServiceImpl.getFullURL(link.getId()), link.getUrl());
    }

    @Test
    public void getFullURLWhenUrlDoesNotExist() throws Exception {
        String id = "5fdas15";
        Mockito.when(linksRepository.findById(id)).thenReturn(Optional.ofNullable(null));
        Exception ex = assertThrows(LinkNotFoundException.class, () -> linksServiceImpl.getFullURL(id));
        assertEquals(ex.getMessage(), "Not existent link: " + "hostAdress" + id);
    }
}
