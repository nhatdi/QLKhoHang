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
import model.PhieuXuat;

/**
 *
 * @author Asus
 */
public class PhieuXuatDAO {
    public void insert(PhieuXuat model){
         Date ngayXuat = new Date(model.getNgayXuat().getTime());
        String sql = "INSERT INTO PhieuXuat (MaXuat,MaSP,MaKho,Gia,SoLuong,DiaChiXuat,NgayXuat,MaND) VALUES (?,?,?,?,?,?,?,?)";
        JdbcHelper.executeUpdate(sql,model.getMaXuat(),model.getMaSP(),model.getMaKho(),model.getGia(),model.getSoLuong(), model.getDiaChiXuat(),ngayXuat, model.getMaND());
    }
    
    public void update(PhieuXuat model){
        
    Date ngayXuat = new Date(model.getNgayXuat().getTime());
        String sql = "UPDATE PhieuXuat SET MaSP=?,MaKho=?,Gia=?,SoLuong=?,DiaChiXuat=?,NgayXuat=?, MaND=? WHERE MaXuat=?";
        JdbcHelper.executeUpdate(sql, model.getMaSP(), model.getMaKho(), model.getGia(), model.getSoLuong(), model.getDiaChiXuat(),ngayXuat, model.getMaND(), model.getMaXuat());
    }
    
    public void delete(String MaXuat){
        String sql = "DELETE FROM PhieuXuat WHERE MaXuat=?";
        JdbcHelper.executeUpdate(sql, MaXuat);
    }
    
    public List<PhieuXuat> select() {
        String sql = "SELECT * FROM PhieuXuat";
        return select(sql);
    }
    public List<PhieuXuat> selectByKeyword(String keyword) {
        String sql = "SELECT * FROM PhieuXuat WHERE MaXuat LIKE ?";
        return select(sql, "%" + keyword + "%");
    }

    public PhieuXuat findById(String MaXuat) {
        String sql = "SELECT * FROM PhieuXuat WHERE MaXuat=?";
        List<PhieuXuat> list = select(sql, MaXuat);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<PhieuXuat> select(String sql, Object... args) {
        List<PhieuXuat> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    PhieuXuat model = readFromResultSet(rs);
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
    
    private PhieuXuat readFromResultSet(ResultSet rs) throws SQLException {
        PhieuXuat model = new PhieuXuat();
        model.setMaXuat(rs.getString("MaXuat"));
        model.setMaSP(rs.getString("MaSP"));
        model.setMaKho(rs.getString("MaKho"));
        model.setGia(rs.getDouble("Gia"));
        model.setSoLuong(rs.getInt("SoLuong"));
        model.setDiaChiXuat(rs.getString("DiaChiXuat"));
        model.setNgayXuat(rs.getDate("NgayXuat"));
        model.setMaND(rs.getString("MaND"));
        return model;
    }
}
