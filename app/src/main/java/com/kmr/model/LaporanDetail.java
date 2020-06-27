package com.kmr.model;


import java.io.Serializable;

public class LaporanDetail implements Serializable {
  private String deskripsi;
  private double total;

  public String getDeskripsi() {
    return deskripsi;
  }

  public void setDeskripsi(String deskripsi) {
    this.deskripsi = deskripsi;
  }

  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
  }
}
