package com.ocdxsunnah.oxs.Models;

public class UserModels {

    public String beratIdeal;
    public String metode;
    public String nama;
    public String uID;
    public Double beratBadan;
    public Double tinggiBadan;
    public int lama;

    public UserModels() {

    }

    public UserModels(String beratIdeal, String metode, String nama, String uID, Double beratBadan, Double tinggiBadan, int lama) {
        this.beratIdeal = beratIdeal;
        this.metode = metode;
        this.nama = nama;
        this.uID = uID;
        this.beratBadan = beratBadan;
        this.tinggiBadan = tinggiBadan;
        this.lama = lama;
    }

    public String getBeratIdeal() {
        return beratIdeal;
    }

    public void setBeratIdeal(String beratIdeal) {
        this.beratIdeal = beratIdeal;
    }

    public String getMetode() {
        return metode;
    }

    public void setMetode(String metode) {
        this.metode = metode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public Double getBeratBadan() {
        return beratBadan;
    }

    public void setBeratBadan(Double beratBadan) {
        this.beratBadan = beratBadan;
    }

    public Double getTinggiBadan() {
        return tinggiBadan;
    }

    public void setTinggiBadan(Double tinggiBadan) {
        this.tinggiBadan = tinggiBadan;
    }

    public int getLama() {
        return lama;
    }

    public void setLama(int lama) {
        this.lama = lama;
    }
}
