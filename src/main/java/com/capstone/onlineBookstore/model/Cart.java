    package com.capstone.onlineBookstore.model;

    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotNull;

    import java.math.BigDecimal;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Optional;

    @Entity
    @Table(name = "carts")
    public class Cart {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_carts_user"))
        @NotNull
        private User user;

        @OneToMany(mappedBy ="cart", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<CartItem> cartItems = new ArrayList<>();

        //cart methods

        //helper method for finding book by id
        public Optional<CartItem> getItemByBookId(Long bookId) {
            return cartItems.stream()
                    .filter(ci -> ci.getBook().getId().equals(bookId))
                    .findFirst();
        }

        public boolean removeItem(Long bookId) {
            return cartItems.removeIf(cartItem -> cartItem.getBook().getId().equals(bookId));
        }

        public void clear(){
            cartItems.clear();
        }

        public CartItem addOrIncreaseItem(Book book, int qty) {
            if (qty<=0) {
               throw new IllegalArgumentException("Quantity must be greater than 0");
            }
            Long targetBookId =  book.getId();
            if(targetBookId==null) {
                throw new IllegalArgumentException("Book id cannot be null");
            }

            Optional<CartItem> existing = getItemByBookId(targetBookId);

            if (existing.isPresent()) {
                existing.get().setQuantity(existing.get().getQuantity() + qty);
                return existing.get();
            } else {
                CartItem item = new CartItem(this, book, qty);
                cartItems.add(item);
                return item;
            }
        }

        public void setItemQuantity(Long bookId, int qty) {
            CartItem item = getItemByBookId(bookId)
                    .orElseThrow(() -> new IllegalArgumentException("Item not in cart: bookId=" + bookId));

            if (qty <= 0) {
                cartItems.remove(item);
            } else {
                item.setQuantity(qty);
            }
        }

        public int getTotalQuantity() {
            return cartItems.stream().mapToInt(CartItem::getQuantity).sum();
        }

        public int getDistinctItemsCount() {
            return cartItems.size();
        }







        //getters and setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public List<CartItem> getCartItems() {
            return cartItems;
        }

        public void setCartItems(List<CartItem> cartItems) {
            this.cartItems = cartItems;
        }
    }
