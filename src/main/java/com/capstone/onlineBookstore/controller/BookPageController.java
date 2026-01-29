// src/main/java/com/capstone/onlineBookstore/controller/BookPageController.java
package com.capstone.onlineBookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookPageController {

    @GetMapping("/books")
    public String booksPage() {

        return "books";
    }
}
