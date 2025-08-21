package com.itgroup.Bean;

public class MyGame {
    String title;
    double price;
    String maker;
    String releasedate;
    int rate;
    public MyGame(){}
    public MyGame(String title, double price, String maker, String releasedate, int rate) {
        this.title = title;
        this.price = price;
        this.maker = maker;
        this.releasedate = releasedate;
        this.rate = rate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getreleasedate() {
        return releasedate;
    }

    public void setreleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return
                "title = " + title  +
                ", price = " + price +
                ", maker = " + maker +
                ", releasedate = " + releasedate +
                ", rate = " + rate ;
    }
}
