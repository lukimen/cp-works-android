package com.kmr.model;


import java.io.Serializable;
import java.util.List;

public class Laporan implements Serializable {
  private List<LaporanDetail> laporanDetails;
  private double total;

  public List<LaporanDetail> getLaporanDetails() {
    return laporanDetails;
  }

  public void setLaporanDetails(List<LaporanDetail> laporanDetails) {
    this.laporanDetails = laporanDetails;
  }

  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
  }
}
