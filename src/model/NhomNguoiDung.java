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
public class NhomNguoiDung {
    private String MaNhom;
    private String TenNhom;
   
    
    @Override
    public String toString(){
        return this.TenNhom;
    }

    public String getMaNhom() {
        return MaNhom;
    }

    public void setMaNhom(String MaNhom) {
        this.MaNhom = MaNhom;
    }

    public String getTenNhom() {
        return TenNhom;
    }

    public void setTenNhom(String TenNhom) {
        this.TenNhom = TenNhom;
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
        final NhomNguoiDung other = (NhomNguoiDung) obj;
        if (!Objects.equals(this.MaNhom, other.MaNhom)) {
            return false;
        }
        return true;
    }
    
}
