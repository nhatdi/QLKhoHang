/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Helper.JdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.PhieuNhap;
import model.PhieuXuat;

/**
 *
 * @author Hai Au
 */
public class ThongKeDAO {
    public List<Object[]> getNhapKho(int nam) {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "{call sp_ThongKeNhapKho(?)}";
                rs = JdbcHelper.executeQuery(sql, nam);
                while (rs.next()) {
                    Object[] model = {rs.getString("TenSP"),
                        rs.getString("TenLoai"), rs.getInt("SoLuong"), rs.getDouble("VonNhap")};
                    list.add(model);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<Object[]> getXuatKho(int nam) {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "{call sp_ThongKeXuatKho(?)}";
                rs = JdbcHelper.executeQuery(sql, nam);
                while (rs.next()) {
                    Object[] model = {rs.getString("TenSP"),
                        rs.getString("TenLoai"), rs.getInt("SoLuong"), rs.getDouble("VonXuat")};
                    list.add(model);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

   
}
