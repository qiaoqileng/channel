package com.qql.dagger.recommend.model.bean;

public class Shop {
    private String brand;

    private String create_time;

    private String description;

    private int id;

    private String logo;

    private String name;

    private String tb_url;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTb_url() {
        return tb_url;
    }

    public void setTb_url(String tb_url) {
        this.tb_url = tb_url;
    }
}
