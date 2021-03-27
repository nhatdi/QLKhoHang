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
import model.NhaCungCap;
import model.ThongTinKho;

/**
 *
 * @author Asus
 */
public class ThongTinKhoDAO {
    
    public void insert(ThongTinKho model){
        String sql = "INSERT INTO ThongTinKho (MaKho,TenKho,DiaChiKho) VALUES (?, ?,?)";
        JdbcHelper.executeUpdate(sql, model.getMaKho(), model.getTenKho(), model.getDiaChiKho());
    }
    
     public void update(ThongTinKho model){
        String sql = "UPDATE ThongTinKho SET TenKho=?, DiaChiKho=? WHERE MaKho=?";
        JdbcHelper.executeUpdate(sql, model.getMaKho(), model.getTenKho(), model.getDiaChiKho());
    }
     public void delete(String MaKho){
         String sql = "DELETE FROM ThongTinKho WHERE MaKho=?";
         JdbcHelper.executeUpdate(sql, MaKho);
     }
     public List<ThongTinKho> select() {
        String sql = "SELECT * FROM ThongTinKho";
        return select(sql);
    }

    public ThongTinKho findById(String MaKho) {
        String sql = "SELECT * FROM ThongTinKho WHERE MaKho=?";
        List<ThongTinKho> list = select(sql, MaKho);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<ThongTinKho> select(String sql, Object... args) {
        List<ThongTinKho> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    ThongTinKho model = readFromResultSet(rs);
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

    private ThongTinKho readFromResultSet(ResultSet rs) throws SQLException {
        ThongTinKho model = new ThongTinKho();
        model.setMaKho(rs.getString("MaKho"));
        model.setTenKho(rs.getString("TenKho"));
        model.setDiaChiKho(rs.getString("DiaChiKho"));
        return model;
    }
    
}
