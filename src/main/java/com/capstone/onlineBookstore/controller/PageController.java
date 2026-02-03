package com.capstone.onlineBookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The type Page controller.
 */
@Controller
public class PageController {

    /**
     * Login page string.
     *
     * @return the string
     */
//landing page for login
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * Register page string.
     *
     * @return the string
     */
    @GetMapping("register")
    public String registerPage() {
        return "register";
    }

    /**
     * Books page string.
     *
     * @return the string
     */
//landing page for books
    @GetMapping("/books")
    public String booksPage() {

        return "books";
    }

    /**
     * Cart page string.
     *
     * @return the string
     */
//landing page for carts
    @GetMapping("/cart")
    public String cartPage() {
        return "cart"; // resolves src/main/resources/templates/cart.html
    }

    /**
     * Orders page string.
     *
     * @return the string
     */
//landing page for orders
    @GetMapping("/orders")
    public String ordersPage() {

        return "orders";
    }


}
