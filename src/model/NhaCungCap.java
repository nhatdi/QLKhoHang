/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Asus
 */
public class NhaCungCap {
    private String MaNhaCC;
    private String MaLoaiCC;
    private String TenNhaCC;
    private String DiaChiCC;
    
    @Override
    public String toString(){
        return MaNhaCC;
    }

    public String getMaNhaCC() {
        return MaNhaCC;
    }

    public void setMaNhaCC(String MaNhaCC) {
        this.MaNhaCC = MaNhaCC;
    }

    public String getMaLoaiCC() {
        return MaLoaiCC;
    }

    public void setMaLoaiCC(String MaLoaiCC) {
        this.MaLoaiCC = MaLoaiCC;
    }

    public String getTenNhaCC() {
        return TenNhaCC;
    }

    public void setTenNhaCC(String TenNhaCC) {
        this.TenNhaCC = TenNhaCC;
    }

    public String getDiaChiCC() {
        return DiaChiCC;
    }

    public void setDiaChiCC(String DiaChiCC) {
        this.DiaChiCC = DiaChiCC;
    }
    
}
