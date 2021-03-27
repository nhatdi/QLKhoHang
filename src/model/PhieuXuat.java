/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Helper.DateHelper;
import java.util.Date;

/**
 *
 * @author Asus
 */
public class PhieuXuat {
    private String MaXuat, MaSP, MaKho, DiaChiXuat, MaND;
    private double Gia;
    private int SoLuong;
    private Date NgayXuat = DateHelper.now();
    
    
    @Override
    public String toString(){
//        return toString();
        return this.MaSP+"("+this.NgayXuat+")";
    }

    public String getMaXuat() {
        return MaXuat;
    }

    public void setMaXuat(String MaXuat) {
        this.MaXuat = MaXuat;
    }

    public String getMaSP() {
        return MaSP;
    }

    public void setMaSP(String MaSP) {
        this.MaSP = MaSP;
    }

    public String getMaKho() {
        return MaKho;
    }

    public void setMaKho(String MaKho) {
        this.MaKho = MaKho;
    }

    public String getDiaChiXuat() {
        return DiaChiXuat;
    }

    public void setDiaChiXuat(String DiaChiXuat) {
        this.DiaChiXuat = DiaChiXuat;
    }

    public String getMaND() {
        return MaND;
    }

    public void setMaND(String MaND) {
        this.MaND = MaND;
    }

    public double getGia() {
        return Gia;
    }

    public void setGia(double Gia) {
        this.Gia = Gia;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public Date getNgayXuat() {
        return NgayXuat;
    }

    public void setNgayXuat(Date NgayXuat) {
        this.NgayXuat = NgayXuat;
    }

    
   
}
