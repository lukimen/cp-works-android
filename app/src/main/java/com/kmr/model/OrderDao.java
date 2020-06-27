package com.kmr.model;

import java.io.Serializable;
import java.util.Date;

public class OrderDao implements Serializable {
  private String id;
  private String placeId;
  private String placeName;
  private Date tanggalAwalSewa;
  private int durasiSewa;
  private String email;
  private double totalBayar;
  private int isDeleted;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPlaceId() {
    return placeId;
  }

  public void setPlaceId(String placeId) {
    this.placeId = placeId;
  }

  public Date getTanggalAwalSewa() {
    return tanggalAwalSewa;
  }

  public void setTanggalAwalSewa(Date tanggalAwalSewa) {
    this.tanggalAwalSewa = tanggalAwalSewa;
  }

  public int getDurasiSewa() {
    return durasiSewa;
  }

  public void setDurasiSewa(int durasiSewa) {
    this.durasiSewa = durasiSewa;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public double getTotalBayar() {
    return totalBayar;
  }

  public void setTotalBayar(double totalBayar) {
    this.totalBayar = totalBayar;
  }

  public int getIsDeleted() {
    return isDeleted;
  }

  public void setIsDeleted(int isDeleted) {
    this.isDeleted = isDeleted;
  }

  public String getPlaceName() {
    return placeName;
  }

  public void setPlaceName(String placeName) {
    this.placeName = placeName;
  }
}
