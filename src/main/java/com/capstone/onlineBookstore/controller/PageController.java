package com.capstone.onlineBookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    //landing page for login
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping
    public String registerPage() {
        return "register";
    }

    //landing page for books
    @GetMapping("/books")
    public String booksPage() {

        return "books";
    }

    //landing page for carts
    @GetMapping("/cart")
    public String cartPage() {
        return "cart"; // resolves src/main/resources/templates/cart.html
    }

    //landing page for orders
    @GetMapping("/orders")
    public String ordersPage() {

        return "orders";
    }


}
