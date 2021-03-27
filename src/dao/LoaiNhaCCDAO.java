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
import model.LoaiNhaCC;

/**
 *
 * @author Asus
 */
public class LoaiNhaCCDAO {
    
    public void insert(LoaiNhaCC model){
        String sql = "INSERT INTO LoaiNhaCC (MaLoaiCC,QuocGiaCC) VALUES (?, ?)";
        JdbcHelper.executeUpdate(sql, model.getMaLoaiCC(), model.getQuocGiaCC());
    }
    public void update(LoaiNhaCC model){
        String sql = "UPDATE LoaiNhaCC SET QuocGiaCC=? WHERE MaLoaiCC=?";
        JdbcHelper.executeUpdate(sql, model.getQuocGiaCC(), model.getMaLoaiCC());
    }
    public void delete(String MaLoaiCC){
        String sql = "DELETE FROM LoaiNhaCC WHERE MaLoaiCC=?";
        JdbcHelper.executeUpdate(sql, MaLoaiCC);
    }
    public List<LoaiNhaCC> select() {
        String sql = "SELECT * FROM LoaiNhaCC";
        return select(sql);
    }
    public LoaiNhaCC findById(String MaLoaiCC) {
        String sql = "SELECT * FROM LoaiNhaCC WHERE MaLoaiCC=?";
        List<LoaiNhaCC> list = select(sql, MaLoaiCC);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<LoaiNhaCC> select(String sql, Object... args) {
        List<LoaiNhaCC> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    LoaiNhaCC model = readFromResultSet(rs);
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

    private LoaiNhaCC readFromResultSet(ResultSet rs) throws SQLException {
        LoaiNhaCC model = new LoaiNhaCC();
        model.setMaLoaiCC(rs.getString("MaLoaiCC"));
        model.setQuocGiaCC(rs.getString("QuocGiaCC"));
        return model;
    }
}
