package com.SidStudio.ARay.Databases;

public class ModelGlasses {
    String glassAge, glassBrand, glassFeatures, glassFrameType, glassGender, glassId, glassImage, glassMaterial, glassModel, glassName, glassPrice, glassRating, glassType, glassWarranty;

    public ModelGlasses() {

    }

    public ModelGlasses(String glassAge, String glassBrand, String glassFeatures, String glassFrameType, String glassGender, String glassId, String glassImage, String glassMaterial, String glassModel, String glassName, String glassPrice, String glassRating, String glassType, String glassWarranty) {
        this.glassAge = glassAge;
        this.glassBrand = glassBrand;
        this.glassFeatures = glassFeatures;
        this.glassFrameType = glassFrameType;
        this.glassGender = glassGender;
        this.glassId = glassId;
        this.glassImage = glassImage;
        this.glassMaterial = glassMaterial;
        this.glassModel = glassModel;
        this.glassName = glassName;
        this.glassPrice = glassPrice;
        this.glassRating = glassRating;
        this.glassType = glassType;
        this.glassWarranty = glassWarranty;
    }

    @Override
    public String toString() {
        return "ModelGlasses{" +
                "glassAge='" + glassAge + '\'' +
                ", glassBrand='" + glassBrand + '\'' +
                ", glassFeatures='" + glassFeatures + '\'' +
                ", glassFrameType='" + glassFrameType + '\'' +
                ", glassGender='" + glassGender + '\'' +
                ", glassId='" + glassId + '\'' +
                ", glassImage='" + glassImage + '\'' +
                ", glassMaterial='" + glassMaterial + '\'' +
                ", glassModel='" + glassModel + '\'' +
                ", glassName='" + glassName + '\'' +
                ", glassPrice='" + glassPrice + '\'' +
                ", glassRating='" + glassRating + '\'' +
                ", glassType='" + glassType + '\'' +
                ", glassWarranty='" + glassWarranty + '\'' +
                '}';
    }

    public String getGlassAge() {
        return glassAge;
    }

    public void setGlassAge(String glassAge) {
        this.glassAge = glassAge;
    }

    public String getGlassBrand() {
        return glassBrand;
    }

    public void setGlassBrand(String glassBrand) {
        this.glassBrand = glassBrand;
    }

    public String getGlassFeatures() {
        return glassFeatures;
    }

    public void setGlassFeatures(String glassFeatures) {
        this.glassFeatures = glassFeatures;
    }

    public String getGlassFrameType() {
        return glassFrameType;
    }

    public void setGlassFrameType(String glassFrameType) {
        this.glassFrameType = glassFrameType;
    }

    public String getGlassGender() {
        return glassGender;
    }

    public void setGlassGender(String glassGender) {
        this.glassGender = glassGender;
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

    public String getGlassMaterial() {
        return glassMaterial;
    }

    public void setGlassMaterial(String glassMaterial) {
        this.glassMaterial = glassMaterial;
    }

    public String getGlassModel() {
        return glassModel;
    }

    public void setGlassModel(String glassModel) {
        this.glassModel = glassModel;
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

    public String getGlassRating() {
        return glassRating;
    }

    public void setGlassRating(String glassRating) {
        this.glassRating = glassRating;
    }

    public String getGlassType() {
        return glassType;
    }

    public void setGlassType(String glassType) {
        this.glassType = glassType;
    }

    public String getGlassWarranty() {
        return glassWarranty;
    }

    public void setGlassWarranty(String glassWarranty) {
        this.glassWarranty = glassWarranty;
    }
}
