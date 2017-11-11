package com.example.conorbyrne.freebee;

class Item {

    private String id;
    private String name;
    private String image;
    private String description;
    private String quality;
    private User user;

    public Item(){};

    public Item(String id, String name, String image, String description, String quality, User user){
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.quality = quality;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
