package com.fun.webscrap.controller;

import com.fun.webscrap.config.InvalidInputException;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

@RestController
@RequestMapping("scrapper")
public class WebScrapController {

    WebScrapController() {

    }

    public final String URL_VALIDATOR = "(https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|www\\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9]+\\.[^\\s]{2,}|www\\.[a-zA-Z0-9]+\\.[^\\s]{2,})";


    @GetMapping
    public Set<String> getLinks(@RequestParam("url") String url) {
        Pattern urlPattern = Pattern.compile(URL_VALIDATOR);

        try {
                new URL(url).openStream().close();

                Playwright playwright = Playwright.create();
                Browser browser = playwright.webkit().launch();
                Page page = browser.newPage();
                page.navigate(url);
                Set<String> links = new HashSet<>();
                page.waitForSelector("BODY").querySelectorAll("a").forEach(elements -> {
                    String href = elements.getAttribute("href");
                    if (Objects.nonNull(href) && urlPattern.matcher(href).matches()) {
                        links.add(href);
                    }
                });
                browser.close();
                return links;
        } catch (IOException e) {
            throw new InvalidInputException("Invalid url : " + url);
        }
    }
}
