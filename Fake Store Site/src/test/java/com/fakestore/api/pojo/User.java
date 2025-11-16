package com.fakestore.api.pojo;

public class User {

    private int id;
    private String email;
    private String username;
    private String password;
    private Name name;
    private Address address;
    private String phone;
    private int __v;

    public User() {}

    public User(int id)
    {
        this.id = id;
    }

    public User(int id, String email)
    {
        this.id = id;
        this.email = email;
    }

    public User(int id, String email, String username)
    {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public User(int id, String email, String username,String password)
    {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String email, String username,String password)
    {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String username,String password)
    {
        this.username = username;
        this.password = password;
    }

    public User(String username)
    {
        this.username = username;
    }

    public User(int id, String email, String username, String password,
                Name name, Address address, String phone, int __v) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.__v = __v;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Name getName() { return name; }
    public void setName(Name name) { this.name = name; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public int get__v() { return __v; }
    public void set__v(int __v) { this.__v = __v; }

    // ===== Inner Class: Name =====
    public static class Name {
        private String firstname;
        private String lastname;

        public Name() {}

        public Name(String firstname, String lastname) {
            this.firstname = firstname;
            this.lastname = lastname;
        }

        public String getFirstname() { return firstname; }
        public void setFirstname(String firstname) { this.firstname = firstname; }

        public String getLastname() { return lastname; }
        public void setLastname(String lastname) { this.lastname = lastname; }
    }

    // ===== Inner Class: Address =====
    public static class Address {
        private Geolocation geolocation;
        private String city;
        private String street;
        private int number;
        private String zipcode;

        public Address() {}

        public Address(Geolocation geolocation, String city, String street,
                       int number, String zipcode) {
            this.geolocation = geolocation;
            this.city = city;
            this.street = street;
            this.number = number;
            this.zipcode = zipcode;
        }

        public Geolocation getGeolocation() { return geolocation; }
        public void setGeolocation(Geolocation geolocation) { this.geolocation = geolocation; }

        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }

        public String getStreet() { return street; }
        public void setStreet(String street) { this.street = street; }

        public int getNumber() { return number; }
        public void setNumber(int number) { this.number = number; }

        public String getZipcode() { return zipcode; }
        public void setZipcode(String zipcode) { this.zipcode = zipcode; }
    }

    // ===== Inner Class: Geolocation =====
    public static class Geolocation {
        private String lat;
        private String _long;

        public Geolocation() {}

        public Geolocation(String lat, String _long) {
            this.lat = lat;
            this._long = _long;
        }

        public String getLat() { return lat; }
        public void setLat(String lat) { this.lat = lat; }

        public String getLong() { return _long; }
        public void setLong(String _long) { this._long = _long; }
    }
}

