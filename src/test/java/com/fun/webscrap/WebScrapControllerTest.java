package com.fun.webscrap;

import com.fun.webscrap.controller.WebScrapController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WebScrapController.class)
public class WebScrapControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void shouldReturnValidURLList() throws Exception {
        this.mockMvc.perform(get("/scrapper")
                                .param("url", "https://www.letmeship.com"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    public void shouldThrowException() throws Exception {
        this.mockMvc.perform(get("/scrapper")
                        .param("url", "https://www.invalidwebsite.com"))
                .andExpect(status().is4xxClientError());
    }
}
