package com.SidStudio.ARay.Databases;

public class CartModel {

    String glassFrameType, glassId, glassImage, glassName, glassPrice, glassDiscount, date, time, quantity;

    public CartModel() {

    }

    public CartModel(String glassFrameType, String glassId, String glassImage, String glassName, String glassPrice, String glassDiscount, String date, String time, String quantity) {
        this.glassFrameType = glassFrameType;
        this.glassId = glassId;
        this.glassImage = glassImage;
        this.glassName = glassName;
        this.glassPrice = glassPrice;
        this.glassDiscount = glassDiscount;
        this.date = date;
        this.time = time;
        this.quantity = quantity;
    }

    public String getGlassFrameType() {
        return glassFrameType;
    }

    public void setGlassFrameType(String glassFrameType) {
        this.glassFrameType = glassFrameType;
    }

    public String getGlassId() {
        return glassId;
    }

    public void setGlassId(String glassId) {
        this.glassId = glassId;
    }

    public String getGlassImage() {
        return glassImage;
    }

    public void setGlassImage(String glassImage) {
        this.glassImage = glassImage;
    }

    public String getGlassName() {
        return glassName;
    }

    public void setGlassName(String glassName) {
        this.glassName = glassName;
    }

    public String getGlassPrice() {
        return glassPrice;
    }

    public void setGlassPrice(String glassPrice) {
        this.glassPrice = glassPrice;
    }

    public String getGlassDiscount() {
        return glassDiscount;
    }

    public void setGlassDiscount(String glassDiscount) {
        this.glassDiscount = glassDiscount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
