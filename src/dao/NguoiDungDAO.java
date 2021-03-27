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
import model.NguoiDung;

/**
 *
 * @author Asus
 */
public class NguoiDungDAO {

    public void insert(NguoiDung model) {
        String sql = "INSERT INTO NguoiDung(MaND,TenND,Password,MaNhom) VALUES(?,?,?,?)";
        JdbcHelper.executeUpdate(sql, model.getMaND(), model.getTenND(), model.getPassword(), model.getMaNhom());
    }

    public void update(NguoiDung model) {
        String sql = "UPDATE NguoiDung SET TenND=?, Password=?, MaNhom=? WHERE MaND=?";
        JdbcHelper.executeUpdate(sql,model.getTenND(), model.getPassword(), model.getMaNhom(),model.getMaND());
    }

    public void delete(String MaND) {
        String sql = "DELETE FROM NguoiDung WHERE MaND=?";
        JdbcHelper.executeUpdate(sql, MaND);
    }

    public List<NguoiDung> select() {
        String sql = "SELECT * FROM NguoiDung";
        return select(sql);
    }

    public NguoiDung findById(String MaND) {
        String sql = "SELECT * FROM NguoiDung WHERE MaND=?";
        List<NguoiDung> list = select(sql, MaND);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<NguoiDung> select(String sql, Object... args) {
        List<NguoiDung> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    NguoiDung model = readFromResultSet(rs);
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

    private NguoiDung readFromResultSet(ResultSet rs) throws SQLException {
        NguoiDung model = new NguoiDung();
        model.setMaND(rs.getString("MaND"));
        model.setTenND(rs.getString("TenND"));
        model.setPassword(rs.getString("Password"));
        model.setMaNhom(rs.getString("MaNhom"));
        return model;
    }
}
