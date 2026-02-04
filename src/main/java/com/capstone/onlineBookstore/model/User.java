package com.capstone.onlineBookstore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**
 * The type User.
 */
@Entity
@Table(name = "users")
public class User {
    //Entity definitions
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="first_name", nullable = false)
    private String firstName;

    @Column(name ="last_name")
    private String lastName;

    @Column(unique = true, nullable = false)
    @NotBlank
    private String email;

    @NotBlank
    @Column(name ="hashed_password", nullable = false)
    private String hashedPassword;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, optional = false)
    private Cart cart;


    /**
     * Gets id.
     *
     * @return the id
     */
//Getters and Setters
    public Long getId() {
        return id;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets hashed password.
     *
     * @return the hashed password
     */
    public String getHashedPassword() {
        return hashedPassword;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets hashed password.
     *
     * @param hashedPassword the hashed password
     */
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user_roleId"))
    private Role role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
