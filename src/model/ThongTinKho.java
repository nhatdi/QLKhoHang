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
public class ThongTinKho {
    private String MaKho;
    private String TenKho;
    private String DiaChiKho;

    @Override
    public int hashCode() {
        int hash = 3;
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
        final ThongTinKho other = (ThongTinKho) obj;
        if (!Objects.equals(this.MaKho, other.MaKho)) {
            return false;
        }
        return true;
    }

  
    @Override
    public String toString() {
        return this.TenKho; 
    }

    public String getMaKho() {
        return MaKho;
    }

    public void setMaKho(String MaKho) {
        this.MaKho = MaKho;
    }

    public String getTenKho() {
        return TenKho;
    }

    public void setTenKho(String TenKho) {
        this.TenKho = TenKho;
    }

    public String getDiaChiKho() {
        return DiaChiKho;
    }

    public void setDiaChiKho(String DiaChiKho) {
        this.DiaChiKho = DiaChiKho;
    }
    
    
    
}
