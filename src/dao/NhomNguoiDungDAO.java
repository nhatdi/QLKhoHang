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
import model.NhomNguoiDung;

/**
 *
 * @author Asus
 */
public class NhomNguoiDungDAO {
    
    public void insert(NhomNguoiDung model){
        String sql = "INSERT INTO NhomNguoiDung (MaNhom,TenNhom) VALUES (?, ?)";
        JdbcHelper.executeUpdate(sql, model.getMaNhom(), model.getTenNhom());
    }
    public void update(NhomNguoiDung model){
        String sql = "UPDATE NhomNguoiDung SET MaNhom=? WHERE TenNhom=?";
        JdbcHelper.executeUpdate(sql, model.getMaNhom(), model.getTenNhom());
    }
    public void delete(String MaNhom) {
        String sql = "DELETE FROM NhomNguoiDung WHERE MaNhom=?";
        JdbcHelper.executeUpdate(sql, MaNhom);
    }
    public List<NhomNguoiDung> select() {
        String sql = "SELECT * FROM NhomNguoiDung";
        return select(sql);
    }
    public NhomNguoiDung findById(String MaNhom) {
        String sql = "SELECT * FROM NhomNguoiDung WHERE MaNhom=?";
        List<NhomNguoiDung> list = select(sql, MaNhom);
        return list.size() > 0 ? list.get(0) : null;
    }
     private List<NhomNguoiDung> select(String sql, Object... args) {
        List<NhomNguoiDung> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);

                while (rs.next()) {
                    NhomNguoiDung model = readFromResultSet(rs);
                    list.add(model);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }
     private NhomNguoiDung readFromResultSet(ResultSet rs) throws SQLException {
        NhomNguoiDung model = new NhomNguoiDung();
        model.setMaNhom(rs.getString("MaNhom"));
        model.setTenNhom(rs.getString("TenNhom"));
        return model;
    }

}
