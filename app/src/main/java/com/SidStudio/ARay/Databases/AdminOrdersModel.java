package com.SidStudio.ARay.Databases;

public class AdminOrdersModel {

    private String date, name, phoneNo, pinCode, progress, shippingAddress, time, totalAmount;

    public AdminOrdersModel() {

    }

    public AdminOrdersModel(String date, String name, String phoneNo, String pinCode, String progress, String shippingAddress, String time, String totalAmount) {
        this.date = date;
        this.name = name;
        this.phoneNo = phoneNo;
        this.pinCode = pinCode;
        this.progress = progress;
        this.shippingAddress = shippingAddress;
        this.time = time;
        this.totalAmount = totalAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
