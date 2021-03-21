package com.SidStudio.ARay.Databases;

public class CartModel {

    String date, glassDiscount, glassFrameType, glassFrameWidth,  glassId, glassImage, glassLensMaterial, glassLensType, glassName, glassPrescription, glassPrescriptionType,  glassPrice, quantity, time;

    public CartModel() {

    }

    public CartModel(String date, String glassDiscount, String glassFrameType, String glassFrameWidth, String glassId, String glassImage, String glassLensMaterial, String glassLensType, String glassName, String glassPrescription, String glassPrescriptionType, String glassPrice, String quantity, String time) {
        this.date = date;
        this.glassDiscount = glassDiscount;
        this.glassFrameType = glassFrameType;
        this.glassFrameWidth = glassFrameWidth;
        this.glassId = glassId;
        this.glassImage = glassImage;
        this.glassLensMaterial = glassLensMaterial;
        this.glassLensType = glassLensType;
        this.glassName = glassName;
        this.glassPrescription = glassPrescription;
        this.glassPrescriptionType = glassPrescriptionType;
        this.glassPrice = glassPrice;
        this.quantity = quantity;
        this.time = time;
    }

    public String getGlassFrameWidth() {
        return glassFrameWidth;
    }

    public void setGlassFrameWidth(String glassFrameWidth) {
        this.glassFrameWidth = glassFrameWidth;
    }

    public String getGlassLensMaterial() {
        return glassLensMaterial;
    }

    public void setGlassLensMaterial(String glassLensMaterial) {
        this.glassLensMaterial = glassLensMaterial;
    }

    public String getGlassLensType() {
        return glassLensType;
    }

    public void setGlassLensType(String glassLensType) {
        this.glassLensType = glassLensType;
    }

    public String getGlassPrescription() {
        return glassPrescription;
    }

    public void setGlassPrescription(String glassPrescription) {
        this.glassPrescription = glassPrescription;
    }

    public String getGlassPrescriptionType() {
        return glassPrescriptionType;
    }

    public void setGlassPrescriptionType(String glassPrescriptionType) {
        this.glassPrescriptionType = glassPrescriptionType;
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
