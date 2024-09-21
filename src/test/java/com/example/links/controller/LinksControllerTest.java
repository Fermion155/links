package com.example.links.controller;

import com.example.links.exception.BadLinkException;
import com.example.links.exception.LinkNotFoundException;
import com.example.links.service.LinksService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(LinksController.class)
public class LinksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LinksService linksService;


    @Test
    public void createShortenedLink() throws Exception {
        String url = "LongUrl";
        String shortUrl = "SUrl";
        Mockito.when(linksService.createShortenedLink(url)).thenReturn(shortUrl);
        mockMvc.perform(MockMvcRequestBuilders.post("/").contentType(MediaType.TEXT_PLAIN_VALUE).content(url))
                .andExpect(status().isCreated()).andExpect(content().string(shortUrl));
    }

    @Test
    public void createShortenedLinkWhenThrowsError() throws Exception {
        String url = "LongUrl";
        String shortUrl = "SUrl";
        Mockito.when(linksService.createShortenedLink(url)).thenThrow(new BadLinkException("My message"));
        mockMvc.perform(MockMvcRequestBuilders.post("/").contentType(MediaType.TEXT_PLAIN_VALUE).content(url))
                .andExpect(status().isBadRequest()).andExpect(content().string("My message"));
    }

    @Test
    public void redirect() throws Exception {
        String url = "LongUrl";
        String shortPath = "path";
        Mockito.when(linksService.getFullURL(shortPath)).thenReturn(url);
        mockMvc.perform(MockMvcRequestBuilders.get("/" + shortPath))
                .andExpect(status().isFound()).andExpect(redirectedUrl(url));
    }

    @Test
    public void redirectWhenLinkNotFound() throws Exception {
        String shortPath = "path";
        Mockito.when(linksService.getFullURL(shortPath)).thenThrow(new LinkNotFoundException("My message"));
        mockMvc.perform(MockMvcRequestBuilders.get("/" + shortPath))
                .andExpect(status().isNotFound()).andExpect(content().string("My message"));
    }

}
