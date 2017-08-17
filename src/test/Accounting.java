/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import test.myComboBox.*;

/**
 *
 * @author Владелец
 */
public class Accounting extends javax.swing.JFrame {

    /**
     * Creates new form Accounting
     */
    private Connection dbConnection;

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        if (dbConnection != null) {
            return dbConnection;
        } else {
            try {
                dbConnection = DriverManager.getConnection("jdbc:mysql://192.168.1.14/test", "root", "08071994");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка!",JOptionPane.INFORMATION_MESSAGE);
                System.exit(1);
            }
            return dbConnection;
        }
    }

    public void comboFilling(String sql, MyComboBox box) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = dbConnection.prepareStatement(sql);
            if (pstmt.execute()) {
                rs = pstmt.getResultSet();

                while (rs.next()) {
                    box.addElement(rs.getInt("id"), rs.getString("name"));
                }

            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tableShow(String sql, String sql1) {
        //dbConnection = getConnection();

        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        DefaultTableModel dtm = null;
        int balance = 0;
        try {
            pstmt = dbConnection.prepareStatement(sql);
            pstmt1 = dbConnection.prepareStatement(sql1);
            dtm = new DefaultTableModel();

            if (pstmt.execute() & pstmt1.execute()) {
                rs = pstmt.getResultSet();
                rs1 = pstmt1.getResultSet();
                ResultSetMetaData rsmd = rs.getMetaData();
                ResultSetMetaData rsmd1 = rs1.getMetaData();
                for (int col = 1; col <= rsmd1.getColumnCount(); col++) {
                    dtm.addColumn(rsmd1.getColumnLabel(col));
                }

                while (rs.next()) {

                    Vector<String> row = new Vector<String>();

                    for (int col = 1; col <= rsmd.getColumnCount(); col++) {
                        if("number".equals(rsmd.getColumnName(col)))
                            balance += rs.getInt(col);
                        int type = rsmd.getColumnType(col);
                        switch (type) {
                            case Types.INTEGER:
                                row.add(new Integer(rs.getInt(col)).toString());
                                break;
                            case Types.VARCHAR:
                                row.add(rs.getString(col));
                                break;
                            case Types.DECIMAL:
                                row.add(Double.toString(rs.getDouble(col))); 
                                break;
                            case Types.TIMESTAMP:
                                row.add(rs.getDate(col).toString());
                                break;
                            default:
                                throw new Exception("Неподдерживаемый тип");
                        }
                    }

                    dtm.addRow(row);
                }
                 while (rs1.next()) {

                    Vector<String> row = new Vector<String>();

                    for (int col = 1; col <= rsmd1.getColumnCount(); col++) {
                        if("number".equals(rsmd1.getColumnName(col)))
                            balance -= rs1.getInt(col);
                        int type = rsmd1.getColumnType(col);
                        switch (type) {
                            case Types.INTEGER:
                                row.add(new Integer(rs1.getInt(col)).toString()); 
                                break;
                            case Types.VARCHAR:
                                row.add(rs1.getString(col));
                                break;
                            case Types.DECIMAL:
                                row.add(Double.toString(rs1.getDouble(col)));        
                                break;
                            case Types.TIMESTAMP:
                                row.add(rs1.getDate(col).toString());
                                break;
                            default:
                                throw new Exception("Неподдерживаемый тип");
                        }
                    }

                    dtm.addRow(row);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(balance);
        jTable1.setModel(dtm);
        jTable1.getColumnModel();
    }

    public Accounting() {
        initComponents();
        dbConnection = getConnection();
        String sql_command1 = "SELECT id, name from products_list";
        comboFilling(sql_command1, myComboBox1);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        myComboBox1 = new test.myComboBox.MyComboBox();
        dateChooserCombo1 = new datechooser.beans.DateChooserCombo();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ((JLabel) myComboBox1.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        JTextField editor1 = (JTextField) myComboBox1.getEditor().getEditorComponent();
        editor1.setHorizontalAlignment(JTextField.CENTER);
        myComboBox1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        myComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myComboBox1ActionPerformed(evt);
            }
        });

        dateChooserCombo1.setCalendarPreferredSize(new java.awt.Dimension(400, 300));

        jButton1.setText("Добавить");
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(300, 5));

        jTable1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable1.setRequestFocusEnabled(false);
        jScrollPane1.setViewportView(jTable1);

        jButton2.setText("Просмотр");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Акт на расход материала", "Акт на поступление материала", "Наименование материала", "Оборудование" }));
        jComboBox1.setSelectedItem(null);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(myComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(dateChooserCombo1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(331, 331, 331))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(20, 20, 20)))))
                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(myComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(dateChooserCombo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(45, 45, 45))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String checkStr = jComboBox1.getSelectedItem().toString();
                        switch (checkStr) {
                            case "Наименование материала":
                                new AddMaterial().setVisible(true);
                                break;
                            case "Оборудование":
                                new AddEquipment().setVisible(true);
                        }
        Calendar date = dateChooserCombo1.getSelectedDate();
        long i = date.getTimeInMillis();
        java.sql.Date date1 = new java.sql.Date(i);
        Statement statement = null;
        String s = date1.toString().replace("-", "");
        //String insertTableSQL = "INSERT INTO purchase_list (id, product, provider, price, number, date) VALUES ( '50', 'Винт М5', 'test', '3', '100', '" + s + "')";
        //try {
        //    statement = dbConnection.createStatement();
        //   statement.executeUpdate(insertTableSQL);
        //} catch (SQLException e) {
        //    System.out.println(e.getMessage());
        //}

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String name = myComboBox1.getSelectedItem().toString();
        String sql_select = "select id Номер, product Товар, provider Поставщик, price 'Цена/шт', number Количество, equipment Оборудование, employee Рабочий, date Дата from purchase_list where product = '" + name + "';";
        String sql_select1 = "select id Номер, product Товар, provider Поставщик, price 'Цена/шт', number Количество, equipment Оборудование, employee Рабочий, date Дата from spending_list where product = '" + name + "';";
        tableShow(sql_select, sql_select1);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void myComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_myComboBox1ActionPerformed

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
            java.util.logging.Logger.getLogger(Accounting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Accounting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Accounting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Accounting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Accounting().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserCombo dateChooserCombo1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private test.myComboBox.MyComboBox myComboBox1;
    // End of variables declaration//GEN-END:variables
}
