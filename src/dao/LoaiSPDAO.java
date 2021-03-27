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
import model.LoaiSP;

/**
 *
 * @author Asus
 */
public class LoaiSPDAO {
    
    public void insert(LoaiSP model){
        String sql = "INSERT INTO LoaiSP (MaLoai,TenLoai) VALUES (?, ?)";
        JdbcHelper.executeUpdate(sql, model.getMaLoai(), model.getTenLoai());
    }
    public void update(LoaiSP model){
        String sql = "UPDATE LoaiSP SET TenLoai=? WHERE MaLoai=?";
        JdbcHelper.executeUpdate(sql, model.getMaLoai(), model.getTenLoai());
    }
    public  void detele(String MaLoai){
        String sql = "DELETE FROM LoaiSP WHERE MaLoai=?";
        JdbcHelper.executeUpdate(sql, MaLoai);
    }
    public List<LoaiSP> select() {
        String sql = "SELECT * FROM LoaiSP";
        return select(sql);
    }

    public LoaiSP findById(String MaLoai) {
        String sql = "SELECT * FROM LoaiSP WHERE MaLoai=?";
        List<LoaiSP> list = select(sql, MaLoai);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<LoaiSP> select(String sql, Object... args) {
        List<LoaiSP> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    LoaiSP model = readFromResultSet(rs);
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

    private LoaiSP readFromResultSet(ResultSet rs) throws SQLException {
        LoaiSP model = new LoaiSP();
        model.setMaLoai(rs.getString("MaLoai"));
        model.setTenLoai(rs.getString("TenLoai"));
        return model;
    }
}
