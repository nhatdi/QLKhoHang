/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;

/**
 *
 * @author Asus
 */
public class NguoiDung {
    private String MaND;
    private String TenND;
    private String Password;
    private  String MaNhom;
    
    
    @Override
    public String toString(){
        return this.TenND;
    }

    public String getMaND() {
        return MaND;
    }

    public void setMaND(String MaND) {
        this.MaND = MaND;
    }

    public String getTenND() {
        return TenND;
    }

    public void setTenND(String TenND) {
        this.TenND = TenND;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getMaNhom() {
        return MaNhom;
    }

    public void setMaNhom(String MaNhom) {
        this.MaNhom = MaNhom;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NguoiDung other = (NguoiDung) obj;
        if (!Objects.equals(this.MaND, other.MaND)) {
            return false;
        }
        return true;
    }

    
    

    
    
    
}
