package com.SidStudio.ARay.HelperClasses.HomeAdapter;

public class FeaturedHelperClass {
    int image;
    String title;
    float rating;


    public FeaturedHelperClass(int image, String title, float rating) {
        this.image = image;
        this.title = title;
        this.rating = rating;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public float getRating() {
        return rating;
    }
}
