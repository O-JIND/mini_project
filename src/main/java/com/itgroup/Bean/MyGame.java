package com.itgroup.Bean;

public class MyGame {
    int no;
    String title;
    double price;
    String maker;
    String genres;
    String releasedate;
    int rate;
    public MyGame(){}
    public MyGame( int no, String title, double price, String maker,String genres, String releasedate, int rate) {
        this.no = no;
        this.title = title;
        this.price = price;
        this.maker = maker;
        this.genres=genres;
        this.releasedate = releasedate;
        this.rate = rate;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
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

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return
                "title = " + title  +
                ", price = " + price +
                ", maker = " + maker +
                ", genres = " +genres+
                ", releasedate = " + releasedate +
                ", rate = " + rate ;
    }
}
