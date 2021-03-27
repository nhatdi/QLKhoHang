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
import java.sql.Date;
import java.util.List;
import model.SanPham;

/**
 *
 * @author Asus
 */
public class SanPhamDAO {
    
    public void insert(SanPham model){
        Date ngaysx = new Date(model.getNSX().getTime());
        Date ngaysd = new Date(model.getHSD().getTime());
        String slq = "INSERT INTO SanPham (MaSP,TenSP,MaLoai,MaNhap,MaNhaCC,NSX,HSD) VALUES (?,?,?,?,?,?,?)";
        JdbcHelper.executeUpdate(slq, model.getMaSP(), model.getTenSP(), model.getMaLoai(),model.getManhap(), model.getMaNhaCC(), model.getNSX(), model.getHSD());           
    }
    
    public void update(SanPham model){
        Date ngaysx = new Date(model.getNSX().getTime());
        Date ngaysd = new Date(model.getHSD().getTime());
        String slq = "UPDATE SanPham SET TenSP=?, MaLoai=?,MaNhap=?, MaNhaCC=?, NSX=?, HSD=? WHERE MaSP=?";
        JdbcHelper.executeUpdate(slq, model.getTenSP(), model.getMaLoai(),model.getManhap(), model.getMaNhaCC(), ngaysx, ngaysd,model.getMaSP());           
    }
    public void delete(String MaSP){
        String sql = "DELETE FROM SanPham WHERE MaSP=?";
        JdbcHelper.executeUpdate(sql, MaSP);
    }
    public List<SanPham> select() {
        String sql = "SELECT * FROM SanPham";
        return select(sql);
    }
      public List<SanPham> selectByKeyword(String keyword) {
        String sql = "SELECT * FROM SanPham WHERE TenSP LIKE ?";
        return select(sql, "%" + keyword + "%");
    }

    public SanPham findById(String MaSP) {
        String sql = "SELECT * FROM SanPham WHERE MaSP=?";
        List<SanPham> list = select(sql, MaSP);
        return list.size() > 0 ? list.get(0) : null;
    }

    public List<SanPham> select(String sql, Object... args) {
        List<SanPham> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    SanPham model = readFromResultSet(rs);
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

    private SanPham readFromResultSet(ResultSet rs) throws SQLException {
        SanPham model = new SanPham();
        model.setMaSP(rs.getString("MaSP"));
        model.setTenSP(rs.getString("TenSP"));
        model.setMaLoai(rs.getString("MaLoai"));
        model.setManhap(rs.getString("MaNhap"));
        model.setMaNhaCC(rs.getString("MaNhaCC"));
        model.setNSX(rs.getDate("NSX"));
        model.setHSD(rs.getDate("HSD"));
        return model;
    }

  

    
}
