    package com.capstone.onlineBookstore.model;

    import jakarta.persistence.*;

    import java.math.BigDecimal;
    import java.util.ArrayList;
    import java.util.List;

    /**
     * The type Order.
     */
    @Entity
    @Table(name = "orders")
    public class Order {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_orders_users"))
        private User user;

        private String status;


        @Column(precision = 12, scale = 2, nullable = false)
        private BigDecimal total;


        @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<OrderItem> orderItems = new ArrayList<>();






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
         * Gets user.
         *
         * @return the user
         */
        public User getUser() {
            return user;
        }

        /**
         * Sets user.
         *
         * @param user the user
         */
        public void setUser(User user) {
            this.user = user;
        }

        /**
         * Gets status.
         *
         * @return the status
         */
        public String getStatus() {
            return status;
        }

        /**
         * Sets status.
         *
         * @param status the status
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * Gets total.
         *
         * @return the total
         */
        public BigDecimal getTotal() {
            return total;
        }

        /**
         * Sets total.
         *
         * @param total the total
         */
        public void setTotal(BigDecimal total) {
            this.total = total;
        }

        /**
         * Gets order items.
         *
         * @return the order items
         */
        public List<OrderItem> getOrderItems() {
            return orderItems;
        }

        /**
         * Sets order items.
         *
         * @param orderItems the order items
         */
        public void setOrderItems(List<OrderItem> orderItems) {
            this.orderItems = orderItems;
        }
    }
