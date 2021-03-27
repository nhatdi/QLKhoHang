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

/**
 *
 * @author Asus
 */
public class NhaCungCapDAO {
    
    public void isert(NhaCungCap model){
        String sql = "INSERT INTO NhaCungCap (MaNhaCC,MaLoaiCC,TenNhaCC,DiaChiCC) VALUES (?, ?, ?, ?)";
        JdbcHelper.executeUpdate(sql, model.getMaNhaCC(), model.getMaLoaiCC(), model.getTenNhaCC(), model.getDiaChiCC());
        
    }
    public void update(NhaCungCap model){
        String sql = "UPDATE NhaCungCap SET MaLoaiCC=?,TenNhaCC=?, DiaChiCC=? WHERE MaNhaCC=?";
        JdbcHelper.executeUpdate(sql, model.getMaNhaCC(), model.getMaLoaiCC(), model.getTenNhaCC(), model.getDiaChiCC());
        
    }
    public void delete(String MaNhaCC){
        String sql = "DELETE FROM NhaCungCap WHERE MaNhaCC=?";
        JdbcHelper.executeUpdate(sql, MaNhaCC);
    }
    public List<NhaCungCap> select() {
        String sql = "SELECT * FROM NhaCungCap";
        return select(sql);
    }

    public NhaCungCap findById(String MaNhaCC) {
        String sql = "SELECT * FROM NhaCungCap WHERE MaNhaCC=?";
        List<NhaCungCap> list = select(sql, MaNhaCC);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<NhaCungCap> select(String sql, Object... args) {
        List<NhaCungCap> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    NhaCungCap model = readFromResultSet(rs);
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

    private NhaCungCap readFromResultSet(ResultSet rs) throws SQLException {
        NhaCungCap model = new NhaCungCap();
        model.setMaNhaCC(rs.getString("MaNhaCC"));
        model.setMaLoaiCC(rs.getString("MaLoaiCC"));
        model.setTenNhaCC(rs.getString("TenNhaCC"));
        model.setDiaChiCC(rs.getString("DiaChiCC"));
        return model;
    }
    
}
