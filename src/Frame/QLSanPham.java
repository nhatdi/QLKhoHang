/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frame;

import Helper.DateHelper;
import Helper.DialogHelper;
import Helper.ShareHelper;
import dao.LoaiSPDAO;
import dao.NhaCungCapDAO;
import dao.PhieuNhapDAO;

import dao.SanPhamDAO;
import java.awt.HeadlessException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import model.LoaiSP;
import model.NhaCungCap;
import model.PhieuNhap;
import model.SanPham;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author hoang
 */
public final class QLSanPham extends javax.swing.JFrame {

    private Object[] args;

    /**
     * Creates new form QLSanPham
     */
    public QLSanPham() {
        initComponents();
        setLocationRelativeTo(null);
        this.load();
        this.fillComboBox();
        this.fillComboBoxNCC();
        this.fillComboBoxPN();
            this.setStatus(true);
//        AutoCompleteDecorator.decorate(jcbML);
//        AutoCompleteDecorator.decorate(jcbMNCC);
//           AutoCompleteDecorator.decorate(jcbMaNhap);
////        this.selectComboBox() ;
//this.setStatus(true);

    }
    int index = 0;
    SanPhamDAO dao = new SanPhamDAO();
    LoaiSPDAO lspdao = new LoaiSPDAO();
    NhaCungCapDAO nccdao = new NhaCungCapDAO();
    PhieuNhapDAO pndao = new PhieuNhapDAO();
void setStatus(boolean insertable) {
        txtMSP.setEditable(insertable);
        btnInsert.setEnabled(insertable);
        btnUpdate.setEnabled(!insertable);
        btnDelete.setEnabled(!insertable);
        boolean first = this.index > 0;
        boolean last = this.index < tlbB.getRowCount() - 1;
        btnFirst.setEnabled(!insertable && first);
        btnPrev.setEnabled(!insertable && first);
        btnNext.setEnabled(!insertable && last);
        btnLast.setEnabled(!insertable && last);
}
    void load() {
        DefaultTableModel model = (DefaultTableModel) tlbB.getModel();
        model.setRowCount(0);
        try {
            String keyword = txttk.getText();
            List<SanPham> list = dao.selectByKeyword(keyword);
            for (SanPham nh : list) {
                Object[] row = {
                    nh.getMaSP(),
                    nh.getTenSP(),
                    nh.getMaLoai(),
                    nh.getManhap(),
                    nh.getMaNhaCC(),
                    DateHelper.toString(nh.getNSX()),
                    DateHelper.toString(nh.getHSD())
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }
    public boolean checkNgay(){
                
        Date ngKG = DateHelper.toDate(txtHSD.getText(), "MM/dd/yyyy");
        Date ngTao = new Date();
//        int ngay = ngKG.getDate()- ngTao.getDate();
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(ngTao);
        c2.setTime(ngKG);
        // Công thức tính số ngày giữa 2 mốc thời gian:
        long noDay = (c2.getTime().getTime() - c1.getTime().getTime()) / (24 * 3600 * 1000);
        if (noDay >=1) {
            return true;
        } else {
            DialogHelper.alert(this, "Ngày San Xuat phải nhỏ hơn ngày HSD!!");
            txtNSX.requestFocus();

            return false;
        }
      
        
    }

    public boolean isvalid() {
        String ma = txtMSP.getText();
        SanPham model = dao.findById(ma);
        if (model != null) {
            DialogHelper.alert(this, "Mã nhập đã tồn tại");
            txtMSP.requestFocus();
            return false;
        } else if (txtMSP.getText().length() == 0) {
            DialogHelper.alert(this, "Không để trống mã sp");
            txtMSP.requestFocus();
            return false;
        } else if (txtTSP.getText().length() == 0) {
            DialogHelper.alert(this, "Không để trống ten sp");
            txtTSP.requestFocus();
            return false;

        } else if (jcbML.getSelectedObjects().length == 0) {
            DialogHelper.alert(this, "Chưa chọn mã sản phẩm");
            return false;
        } else if (jcbMNCC.getSelectedObjects().length == 0) {
            DialogHelper.alert(this, "Chưa chọn mã kho");
        } else if (jcbMaNhap.getSelectedObjects().length == 0) {
            DialogHelper.alert(this, "Chưa chọn mã kho");
            return false;
        }else if (!txtNSX.getText().matches("\\d{1,2}[-|/]\\d{1,2}[-|/]\\d{4}")) {
            DialogHelper.alert(this, "Sai định dạng ngày");
            txtNSX.requestFocus();
            return false;
        }
        else if (!txtHSD.getText().matches("\\d{1,2}[-|/]\\d{1,2}[-|/]\\d{4}")) {
            DialogHelper.alert(this, "Sai định dạng ngày");
            txtHSD.requestFocus();
            return false;
        }
        
                
        return true;
    }

    public boolean issvalid() {
        String ma = txtMSP.getText();
        SanPham model = dao.findById(ma);

        if (txtMSP.getText().length() == 0) {
            DialogHelper.alert(this, "Không để trống mã sp");
            txtMSP.requestFocus();
            return false;
        } else if (txtTSP.getText().length() == 0) {
            DialogHelper.alert(this, "Không để trống ten sp");
            txtTSP.requestFocus();
            return false;

        } else if (jcbML.getSelectedObjects().length == 0) {
            DialogHelper.alert(this, "Chưa chọn mã sản phẩm");
            return false;
        } else if (jcbMNCC.getSelectedObjects().length == 0) {
            DialogHelper.alert(this, "Chưa chọn mã kho");
        } else if (jcbMaNhap.getSelectedObjects().length == 0) {
            DialogHelper.alert(this, "Chưa chọn mã kho");
return false;
        }else if (!txtNSX.getText().matches("\\d{1,2}[-|/]\\d{1,2}[-|/]\\d{4}")) {
            DialogHelper.alert(this, "Sai định dạng ngày");
            txtNSX.requestFocus();
            return false;
        }
        else if (!txtHSD.getText().matches("\\d{1,2}[-|/]\\d{1,2}[-|/]\\d{4}")) {
            DialogHelper.alert(this, "Sai định dạng ngày");
            txtHSD.requestFocus();
            return false;
        }

        return true;
    }

    void insert() {

        SanPham model = getModel();
        model.setNSX(new Date());
        model.setHSD(new Date());
        try {
            dao.insert(model);
            this.load();
            this.clear();
            DialogHelper.alert(this, "Thêm mới thành công!");
        } catch (Exception e) {
            DialogHelper.alert(this, "Thêm mới thất bại!" + e);
        }

    }

    void update() {

        SanPham model = getModel();
        try {
            dao.update(model);
            this.load();
            DialogHelper.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            DialogHelper.alert(this, "Cập nhật thất bại!" + e);
        }
    }

    void delete() {
        if (DialogHelper.confirm(this, "Bạn thực sự muốn xóa người học này?")) {
            String manh = txtMSP.getText();
            try {
                dao.delete(manh);
                this.load();
                this.clear();
                DialogHelper.alert(this, "Xóa thành công!");
            } catch (HeadlessException e) {
                DialogHelper.alert(this, "Xóa thất bại!");
            }
        }
    }

    void clear() {
        model.SanPham model = new SanPham();

        this.setModel(model);
        this.fillComboBox();
        this.fillComboBoxNCC();
        this.fillComboBoxPN();

        this.setStatus(true); 
    }

    void edit() {
        try {
            String manh = (String) tlbB.getValueAt(this.index, 0);
            SanPham model = dao.findById(manh);
            if (model != null) {
                this.setModel(model);
                this.setStatus(false); 

            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!" + e);

        }
    }

    void setModel(SanPham model) {
        txtMSP.setText(model.getMaSP());
        txtTSP.setText(model.getTenSP());
        
        jcbMaNhap.setSelectedItem(model.getManhap());
        
jcbML.setSelectedItem(lspdao.findById(model.getMaLoai()));
jcbML.setToolTipText(String.valueOf(model.getMaSP()));

//      jcbML.setSelectedItem(model.getMaLoai());


//        jcbMNCC.setSelectedItem(model.getMaNhaCC());
jcbMNCC.setSelectedItem(nccdao.findById(model.getMaNhaCC()));
jcbMNCC.setToolTipText(String.valueOf(model.getMaSP()));

//
//        jcbML.setToolTipText(String.valueOf(model.getMaSP()));
//        jcbML.setSelectedItem(lspdao.findById(model.getMaLoai()));
//        
//        jcbMaNhap.setToolTipText(String.valueOf(model.getMaSP()));
//        jcbMaNhap.setSelectedItem(pndao.findById(model.getMaNhap()));
//        
//        
//        jcbMNCC.setToolTipText(String.valueOf(model.getMaSP()));
//        jcbMNCC.setSelectedItem(nccdao.findById(model.getMaNhaCC()));
//    
//        jcbML.setText(model.getMaLoai()); 
//        jcbMNCC.setText(model.getMaNhaCC()); 
        txtNSX.setText(DateHelper.toString(model.getNSX()));
        txtHSD.setText(DateHelper.toString(model.getHSD()));

    }

    SanPham getModel() {
        SanPham model = new SanPham();
        model.setMaSP(txtMSP.getText());
        model.setTenSP(txtTSP.getText());
        
//        model.setMaLoai((String) jcbML.getSelectedItem());
    LoaiSP lsp = (LoaiSP) jcbML.getSelectedItem();
    model.setMaLoai(lsp.getMaLoai());
//        model.setMaNhaCC((String) jcbMNCC.getSelectedItem());
NhaCungCap ncc = (NhaCungCap) jcbMNCC.getSelectedItem();
    model.setMaNhaCC(ncc.getMaNhaCC());
    
        model.setManhap((String) jcbMaNhap.getSelectedItem());

        model.setNSX(DateHelper.toDate(txtNSX.getText()));
        model.setHSD(DateHelper.toDate(txtHSD.getText()));

        return model;
    }

//    void setStatus(boolean insertable){ 
//        txtMNH.setEditable(insertable); 
//        btnT.setEnabled(insertable); 
//        btnS.setEnabled(!insertable); 
//        btnX.setEnabled(!insertable); 
// 
//        boolean first = this.index > 0; 
//        boolean last = this.index < tblB.getRowCount() - 1; 
//        btn1.setEnabled(!insertable && first); 
//        btn2.setEnabled(!insertable && first); 
//        btn3.setEnabled(!insertable && last); 
//        btn4.setEnabled(!insertable && last); 
//    } 
    void fillComboBox() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) jcbML.getModel();
        model.removeAllElements();
        try {
            List<LoaiSP> list = lspdao.select();
            for (LoaiSP cd : list) {
                model.addElement(cd);

            }

        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void fillComboBoxNCC() {
        DefaultComboBoxModel modell = (DefaultComboBoxModel) jcbMNCC.getModel();
        modell.removeAllElements();
        try {
            List<NhaCungCap> list = nccdao.select();
            for (NhaCungCap cd : list) {

                modell.addElement(cd);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void fillComboBoxPN() {
        DefaultComboBoxModel modell = (DefaultComboBoxModel) jcbMaNhap.getModel();
        modell.removeAllElements();
        try {
            List<PhieuNhap> list = pndao.select();
            for (PhieuNhap cd : list) {

                modell.addElement(cd.getMaNhap());
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }
//    void selectComboBox() {
//      LoaiSP lsp = (LoaiSP) jcbML.getSelectedItem();
//
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMSP = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTSP = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtNSX = new javax.swing.JTextField();
        txtHSD = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jcbML = new javax.swing.JComboBox<>();
        jcbMNCC = new javax.swing.JComboBox<>();
        jlbMaNhap = new javax.swing.JLabel();
        jcbMaNhap = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tlbB = new javax.swing.JTable();
        txttk = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 51));
        jLabel1.setText("Quản Lý Sản Phẩm");

        jLabel2.setText("Mã sản phẩm:");

        jLabel3.setText("Tên sản phẩm:");

        jLabel4.setText("Tên Loại:");

        jLabel5.setText("Tên Nhà Cung Cấp:");

        jLabel6.setText("Ngày sản xuất:");

        jLabel7.setText("Hạn sử dụng:");

        btnInsert.setText("Thêm");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnUpdate.setText("Sửa");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setText("Mới");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        jcbML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbMLActionPerformed(evt);
            }
        });

        jcbMNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbMNCCActionPerformed(evt);
            }
        });

        jlbMaNhap.setText("Ma Nhap");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNSX, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 138, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(txtHSD, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbMNCC, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTSP)
                            .addComponent(txtMSP)
                            .addComponent(jcbML, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jcbMaNhap, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(jlbMaNhap)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(btnInsert)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnUpdate)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnDelete)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnClear)))
                                        .addGap(100, 100, 100)
                                        .addComponent(btnLast)
                                        .addGap(52, 52, 52)
                                        .addComponent(btnNext)
                                        .addGap(35, 35, 35)
                                        .addComponent(btnPrev)
                                        .addGap(32, 32, 32)
                                        .addComponent(btnFirst)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jcbML, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jlbMaNhap)
                .addGap(18, 18, 18)
                .addComponent(jcbMaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jcbMNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNSX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtHSD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInsert)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete)
                    .addComponent(btnClear)
                    .addComponent(btnLast)
                    .addComponent(btnNext)
                    .addComponent(btnPrev)
                    .addComponent(btnFirst))
                .addGap(62, 62, 62))
        );

        tabs.addTab("Cập nhật", jPanel1);

        tlbB.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Mã loại", "Mã Nhập", "Mã Nhà CC", "Ngày SX", "Hạn SD"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tlbB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tlbBMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tlbB);

        txttk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttkActionPerformed(evt);
            }
        });

        jButton2.setText("Tìm Kiếm");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 771, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txttk, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(jButton2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(175, Short.MAX_VALUE))
        );

        tabs.addTab("Chi tiết", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 796, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
       if (isvalid()&&checkNgay()) {
            insert();
           

       }
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (issvalid()&&checkNgay()) {
            update();
//this.insert();
        }

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        this.delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        this.clear();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        this.index = 0;
        this.edit();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        this.index--;
        this.edit();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        this.index++;
        this.edit();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        this.index = tlbB.getRowCount() - 1;
        this.edit();
    }//GEN-LAST:event_btnLastActionPerformed

    private void txttkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttkActionPerformed
        // TODO add your handling code here:
        this.load();
    }//GEN-LAST:event_txttkActionPerformed

    private void tlbBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tlbBMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.index = tlbB.rowAtPoint(evt.getPoint());
            if (this.index >= 0) {
                this.edit();
                tabs.setSelectedIndex(0);
            }
        }


    }//GEN-LAST:event_tlbBMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.load();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jcbMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbMLActionPerformed
//       this.fillComboBox();
//this.selectComboBox() ;

    }//GEN-LAST:event_jcbMLActionPerformed

    private void jcbMNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbMNCCActionPerformed
        // TODO add your handling code here:
//        this.fillComboBoxNCC();

    }//GEN-LAST:event_jcbMNCCActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QLSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QLSanPham().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> jcbML;
    private javax.swing.JComboBox<String> jcbMNCC;
    private javax.swing.JComboBox<String> jcbMaNhap;
    private javax.swing.JLabel jlbMaNhap;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tlbB;
    private javax.swing.JTextField txtHSD;
    private javax.swing.JTextField txtMSP;
    private javax.swing.JTextField txtNSX;
    private javax.swing.JTextField txtTSP;
    private javax.swing.JTextField txttk;
    // End of variables declaration//GEN-END:variables
}
