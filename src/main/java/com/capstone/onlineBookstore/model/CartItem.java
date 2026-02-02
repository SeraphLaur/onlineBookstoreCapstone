package com.capstone.onlineBookstore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * The type Cart item.
 */
@Entity
@Table(name = "cart_items")
public class CartItem {
    //entity definitions
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_id", nullable = false, foreignKey = @ForeignKey(name = "fk_cart_items_cart"))
    private Cart cart;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false, foreignKey = @ForeignKey(name = "fk_cart_items_book"))
    private Book book;

    @Min(1)
    @Column(nullable = false)
    private Integer quantity;

    /**
     * Instantiates a new Cart item.
     */
    protected CartItem() { }


    /**
     * Instantiates a new Cart item.
     *
     * @param cart     the cart
     * @param book     the book
     * @param quantity the quantity
     */
    public CartItem(Cart cart, Book book, int quantity) {
        if (cart == null) throw new IllegalArgumentException("Cart cannot be null");
        if (book == null) throw new IllegalArgumentException("Book cannot be null");
        if (quantity < 1) throw new IllegalArgumentException("Quantity must be >= 1");
        this.cart = cart;
        this.book = book;
        this.quantity = quantity;
    }


    //Getters and setters

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets cart.
     *
     * @return the cart
     */
    public Cart getCart() {
        return cart;
    }

    /**
     * Sets cart.
     *
     * @param cart the cart
     */
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    /**
     * Gets book.
     *
     * @return the book
     */
    public Book getBook() {
        return book;
    }

    /**
     * Sets book.
     *
     * @param book the book
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Sets quantity.
     *
     * @param quantity the quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
