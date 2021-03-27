/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Helper.JdbcHelper;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;
import model.PhieuNhap;

/**
 *
 * @author Asus
 */
public class PhieuNhapDAO {

    public void insert(PhieuNhap model) {

        String sql = "INSERT INTO PhieuNhap (MaNhap, MaSP ,MaKho ,MaND ,Gia ,SoLuong ,NgayNhap) VALUES (?, ?, ?, ?, ?, ?,?)";
        JdbcHelper.executeUpdate(sql, model.getMaNhap(), model.getMaSP(), model.getMaKho(), model.getMaND(), model.getGia(), model.getSoLuong(), model.getNgayNhap());
    }

    public void update(PhieuNhap model) {
       
        String sql = "UPDATE PhieuNhap SET MaSP=?, MaKho=?, Gia=?, SoLuong =?, NgayNhap=?, MaND=? WHERE MaNhap=?";
        JdbcHelper.executeUpdate(sql, model.getMaSP(), model.getMaKho(), model.getGia(), model.getSoLuong(), model.getNgayNhap(), model.getMaND(), model.getMaNhap());
    }

    public void delete(String MaNhap) {
        String sql = "DELETE FROM PhieuNhap WHERE MaNhap=?";
        JdbcHelper.executeUpdate(sql, MaNhap);
    }

    public List<PhieuNhap> select() {
        String sql = "SELECT * FROM PhieuNhap";
        return select(sql);
    }

    public PhieuNhap findById(String MaNhap) {
        String sql = "SELECT * FROM PhieuNhap WHERE MaNhap=?";
        List<PhieuNhap> list = select(sql, MaNhap);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<PhieuNhap> select(String sql, Object... args) {
        List<PhieuNhap> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    PhieuNhap model = readFromResultSet(rs);
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
    private PhieuNhap readFromResultSet(ResultSet rs) throws SQLException {
        PhieuNhap model = new PhieuNhap();
        model.setMaNhap(rs.getString("MaNhap"));
        model.setMaSP(rs.getString("MaSP"));
        model.setMaKho(rs.getString("MaKho"));
        model.setMaND(rs.getString("MaND"));
        model.setGia(rs.getInt("Gia"));
        model.setSoLuong(rs.getInt("SoLuong"));
        model.setNgayNhap(rs.getDate("NgayNhap"));
        return model;
    }
}
