package com.fun.webscrap;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Paths;

@SpringBootApplication
public class WebscrapApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebscrapApplication.class, args);

	}

}
