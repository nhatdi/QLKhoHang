package Frame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Helper.DateHelper;
import Helper.DialogHelper;
import Helper.ShareHelper;
import dao.NguoiDungDAO;
import dao.PhieuNhapDAO;
import dao.SanPhamDAO;
import dao.ThongTinKhoDAO;
import java.awt.HeadlessException;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import model.NguoiDung;
import model.PhieuNhap;
import model.SanPham;
import model.ThongTinKho;

/**
 *
 * @author ASUS
 */
public class Phieunhap extends javax.swing.JFrame {

    int index = 0;
    PhieuNhapDAO pndao = new PhieuNhapDAO();
    ThongTinKhoDAO khodao = new ThongTinKhoDAO();
    NguoiDungDAO nddao = new NguoiDungDAO();
    SanPhamDAO spdao = new SanPhamDAO();

    /**
     * Creates new form Phieunhap
     */
    public Phieunhap() {
        initComponents();
        setLocationRelativeTo(null);
        this.load();
        this.setStatus(true);
        this.fillComboBoxSP();
        this.fillComboBoxKHO();
        this.fillComboBoxKhoND();
        PhieuNhap model = new PhieuNhap();
        txtNgayNhap.setText(DateHelper.toString(model.getNgayNhap()));
        //không cho sữa dữ liệu bảng
        tblGridView.setDefaultEditor(Object.class, null);
        //không cho thay đổi vị trí các cột
        tblGridView.getTableHeader().setReorderingAllowed(false);
        //sắp xếp các cột trong bảng
        tblGridView.setAutoCreateRowSorter(true);
    }

    public boolean isvalid() {
        String ma = txtMaNhap.getText();
        PhieuNhap model = pndao.findById(ma);
        if (model != null) {
            DialogHelper.alert(this, "Mã nhập đã tồn tại");
            txtMaNhap.requestFocus();
            return false;
        } else if (txtMaNhap.getText().length() == 0) {
            DialogHelper.alert(this, "Không để trống mã nhập");
            txtMaNhap.requestFocus();
            return false;
        } else if (txtMaNhap.getText().length() < 3) {
            DialogHelper.alert(this, "Mã nhập ít nhất 3 ký tự");
            txtMaNhap.requestFocus();
            return false;
        } else if (cboMSPham.getSelectedObjects().length == 0) {
            DialogHelper.alert(this, "Chưa chọn mã sản phẩm");
            return false;
        } else if (cboMKho.getSelectedObjects().length == 0) {
            DialogHelper.alert(this, "Chưa chọn mã kho");
            return false;
        } else if (txtGia.getText().length() == 0) {
            DialogHelper.alert(this, "Gía chưa được nhập");
            txtGia.requestFocus();
            return false;
        } else if (!txtGia.getText().matches("[0-9.]+")) {
            DialogHelper.alert(this, "Sai định dạng chữ số");
            txtGia.requestFocus();
            return false;
        } else if (txtSoLuong.getText().length() == 0) {
            DialogHelper.alert(this, "Số lượng chưa được nhập");
            txtSoLuong.requestFocus();
            return false;
        } else if (!txtSoLuong.getText().matches("[0-9]+")) {
            DialogHelper.alert(this, "Sai định dạng chữ số");
            txtSoLuong.requestFocus();
            return false;
        } else if (txtNgayNhap.getText().length() == 0) {
            DialogHelper.alert(this, "Không để trống ngày nhập");
            txtNgayNhap.requestFocus();
            return false;
        } else if (txtNgayNhap.getText().matches(ma)) {
            DialogHelper.alert(this, "Không để trống ngày nhập");
            txtNgayNhap.requestFocus();
            return false;
        }
        return true;
    }

    void load() {
        DefaultTableModel model = (DefaultTableModel) tblGridView.getModel();
        model.setRowCount(0);
        try {
            List<PhieuNhap> list = pndao.select();
            for (PhieuNhap pn : list) {
                Object[] row = {
                    pn.getMaNhap(),
                    pn.getMaSP(),
                    pn.getMaKho(),
                    pn.getMaND(),
                    pn.getGia(),
                    pn.getSoLuong(),
                    DateHelper.toString(pn.getNgayNhap())
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");

        }
    }

    void insert() {

        PhieuNhap model = getModel();
        try {
            pndao.insert(model);
            this.load();
            this.clear();
            DialogHelper.alert(this, "Thêm mới thành công!");
        } catch (Exception e) {
            DialogHelper.alert(this, "Thêm mới thất bại!" + e);

        }
    }

    void edit() {
        try {
            String MaNhap = (String) tblGridView.getValueAt(this.index, 0);
            PhieuNhap model = pndao.findById(MaNhap);
            if (model != null) {
                this.setModel(model);
                this.setStatus(false);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");

        }
    }

    void delete() {
        if (DialogHelper.confirm(this, "Bạn thực sự muốn xóa người học này?")) {
            String manh = txtMaNhap.getText();
            try {
                pndao.delete(manh);
                this.load();
                this.clear();
                DialogHelper.alert(this, "Xóa thành công!");
            } catch (HeadlessException e) {
                DialogHelper.alert(this, "Xóa thất bại!");
            }
        }
    }

    void clear() {
        this.setModel(new PhieuNhap());
        this.setStatus(true);
        this.fillComboBoxSP();
        this.fillComboBoxKHO();
        this.fillComboBoxKhoND();
    }

    void setStatus(boolean insertable) {
        txtMaNhap.setEditable(insertable);
        btnThem.setEnabled(insertable);
        btnXoa.setEnabled(!insertable);

        boolean first = this.index > 0;
        boolean last = this.index < tblGridView.getRowCount() - 1;
        btnFirst.setEnabled(!insertable && first);
        btnPrev.setEnabled(!insertable && first);
        btnLast.setEnabled(!insertable && last);
        btnNext.setEnabled(!insertable && last);
    }

    void setModel(PhieuNhap model) {
        txtMaNhap.setText(model.getMaNhap());

        cboMSPham.setToolTipText(String.valueOf(model.getMaNhap()));
        cboMSPham.setSelectedItem(spdao.findById(model.getMaSP()));

        cboMKho.setToolTipText(String.valueOf(model.getMaNhap()));
        cboMKho.setSelectedItem(khodao.findById(model.getMaKho()));

        cboMND.setToolTipText(String.valueOf(model.getMaNhap()));
        cboMND.setSelectedItem(nddao.findById(model.getMaND()));
        txtGia.setText(String.valueOf(model.getGia()));
        txtSoLuong.setText(String.valueOf(model.getSoLuong()));

        txtNgayNhap.setText(DateHelper.toString(model.getNgayNhap()));

    }

    PhieuNhap getModel() {
        PhieuNhap model = new PhieuNhap();

        model.setMaNhap(txtMaNhap.getText());

        SanPham sp = (SanPham) cboMSPham.getSelectedItem();
        model.setMaSP(sp.getMaSP());

        ThongTinKho kho = (ThongTinKho) cboMKho.getSelectedItem();
        model.setMaKho(kho.getMaKho());

        NguoiDung chuyenDe = (NguoiDung) cboMND.getSelectedItem();
        model.setMaND(chuyenDe.getMaND());
        model.setGia(Double.valueOf(txtGia.getText()));
        model.setSoLuong(Integer.valueOf(txtSoLuong.getText()));
        model.setNgayNhap(DateHelper.toDate(txtNgayNhap.getText()));

        return model;
    }

    void fillComboBoxKHO() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboMKho.getModel();
        model.removeAllElements();
        try {
            List<ThongTinKho> list = khodao.select();
            for (ThongTinKho kho : list) {
                model.addElement(kho);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void fillComboBoxSP() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboMSPham.getModel();
        model.removeAllElements();
        try {
            List<SanPham> list = spdao.select();
            for (SanPham sp : list) {
                model.addElement(sp);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void fillComboBoxKhoND() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboMND.getModel();
        model.removeAllElements();
        try {
            List<NguoiDung> list = nddao.select();
            for (NguoiDung nd : list) {
                model.addElement(nd);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!" + e);
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabs = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        lblMN = new javax.swing.JLabel();
        lblMSP = new javax.swing.JLabel();
        lblMKho = new javax.swing.JLabel();
        lblGia = new javax.swing.JLabel();
        lblSoLuong = new javax.swing.JLabel();
        lblNgayXuat = new javax.swing.JLabel();
        txtMaNhap = new javax.swing.JTextField();
        txtGia = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        txtNgayNhap = new javax.swing.JTextField();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        cboMSPham = new javax.swing.JComboBox<>();
        cboMKho = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cboMND = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGridView = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblMN.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblMN.setText("Mã nhập :");

        lblMSP.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblMSP.setText("Tên sản phẩm :");

        lblMKho.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblMKho.setText("Tên kho :");

        lblGia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblGia.setText("Giá :");

        lblSoLuong.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblSoLuong.setText("Số lượng:");

        lblNgayXuat.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblNgayXuat.setText("Ngày nhập:");

        txtNgayNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayNhapActionPerformed(evt);
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

        btnThem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnXoa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnMoi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
            .addComponent(btnMoi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMoi, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        cboMSPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMSPhamActionPerformed(evt);
            }
        });

        jLabel2.setText("Tên người dùng:");

        cboMND.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMNDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMN)
                    .addComponent(lblMSP)
                    .addComponent(lblMKho)
                    .addComponent(jLabel2)
                    .addComponent(lblGia)
                    .addComponent(lblSoLuong)
                    .addComponent(lblNgayXuat))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtGia, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaNhap, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboMSPham, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboMKho, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboMND, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNgayNhap))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(215, 215, 215)
                .addComponent(btnFirst)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPrev)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnNext)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLast)
                .addContainerGap(257, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMN)
                    .addComponent(txtMaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMSP)
                            .addComponent(cboMSPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblMKho)
                            .addComponent(cboMKho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cboMND, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblGia)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSoLuong))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNgayXuat))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst)
                    .addComponent(btnPrev)
                    .addComponent(btnNext)
                    .addComponent(btnLast))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        tabs.addTab("Cập nhật", jPanel1);

        tblGridView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã nhập", "Tên sản phẩm", "Tên kho", "Người dùng", "Giá", "Số lượng", "Ngày nhập"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGridView.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblGridView.setFocusCycleRoot(true);
        tblGridView.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGridViewMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblGridView);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
        );

        tabs.addTab("Danh sách", jPanel2);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Phiếu Nhập");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tabs)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
        this.index = tblGridView.getRowCount() - 1;
        this.edit();
    }//GEN-LAST:event_btnLastActionPerformed

    private void tblGridViewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGridViewMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.index = tblGridView.rowAtPoint(evt.getPoint());
            if (this.index >= 0) {
                this.edit();
                tabs.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_tblGridViewMouseClicked

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        // TODO add your handling code here:
        this.clear();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
//        if (!ShareHelper.USER.isVaiTro()) {
//            DialogHelper.alert(this, "Không được phép thêm");
//        } else if(this.isvalid()){
//            this.insert();
//        }
        if (this.isvalid()) {
            this.insert();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        this.delete();

    }//GEN-LAST:event_btnXoaActionPerformed

    private void cboMSPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMSPhamActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cboMSPhamActionPerformed

    private void cboMNDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMNDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboMNDActionPerformed

    private void txtNgayNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayNhapActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtNgayNhapActionPerformed

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
            java.util.logging.Logger.getLogger(Phieunhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Phieunhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Phieunhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Phieunhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Phieunhap().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboMKho;
    private javax.swing.JComboBox<String> cboMND;
    private javax.swing.JComboBox<String> cboMSPham;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblGia;
    private javax.swing.JLabel lblMKho;
    private javax.swing.JLabel lblMN;
    private javax.swing.JLabel lblMSP;
    private javax.swing.JLabel lblNgayXuat;
    private javax.swing.JLabel lblSoLuong;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblGridView;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtMaNhap;
    private javax.swing.JTextField txtNgayNhap;
    private javax.swing.JTextField txtSoLuong;
    // End of variables declaration//GEN-END:variables
}
