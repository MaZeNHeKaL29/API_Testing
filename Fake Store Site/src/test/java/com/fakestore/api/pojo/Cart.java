package com.fakestore.api.pojo;

import java.util.List;

public class Cart {

    private int id;
    private int userId;
    private String date;
    private List<Product> products;
    private int __v;

    public Cart() {
    }

    // 1-field constructors
    public Cart(int id) {
        this.id = id;
    }

    public Cart(int userId,List<Product> products)
    {
        this.userId = userId;
        this.products = products;
    }

    public Cart(int id, int userId) {
        this.id = id;
        this.userId = userId;
    }

    public Cart(int id, int userId, String date) {
        this.id = id;
        this.userId = userId;
        this.date = date;
    }

    public Cart(int id, int userId, String date, List<Product> products) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.products = products;
    }

    public Cart(int id, int userId, String date, List<Product> products, int __v) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.products = products;
        this.__v = __v;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    // ===== INNER CLASS =====
    public static class Product {
        private int productId;
        private int quantity;

        public Product() {
        }

        public Product(int productId, int quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}

