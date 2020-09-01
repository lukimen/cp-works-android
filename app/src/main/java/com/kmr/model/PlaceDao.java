package com.kmr.model;


import java.io.Serializable;

public class PlaceDao implements Serializable {
  private String id;
  private double latitude;
  private double longitude;
  private double price;
  private String placeType;
  private String name;
  private String address;
  private String address2;
  private String durasi;
  private String image;
  private String ukuran;
  private int isDeleted;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getPlaceType() {
    return placeType;
  }

  public void setPlaceType(String placeType) {
    this.placeType = placeType;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getAddress2() {
    return address2;
  }

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  public String getDurasi() {
    return durasi;
  }

  public void setDurasi(String durasi) {
    this.durasi = durasi;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public int getIsDeleted() {
    return isDeleted;
  }

  public void setIsDeleted(int isDeleted) {
    this.isDeleted = isDeleted;
  }

  public String getUkuran() {
    return ukuran;
  }

  public void setUkuran(String ukuran) {
    this.ukuran = ukuran;
  }
}
