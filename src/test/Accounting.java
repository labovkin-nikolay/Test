/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
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
    private String login = "root";
    private String pass = "08071994";

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

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
                dbConnection = DriverManager.getConnection("jdbc:mysql://192.168.1.14/test", login, pass);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка!", JOptionPane.INFORMATION_MESSAGE);
                System.exit(1);
            }
            return dbConnection;
        }
    }

    public void comboFilling(String sql, MyComboBox box) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        box.removeAllItems();
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

    private void tableShow(String sql) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DefaultTableModel dtm = null;
        ArrayList<String> columnsList = new ArrayList();
        Object rows[][] = null;
        int a = 0;
        int b = 0;
        try {
            pstmt = dbConnection.prepareStatement(sql);
            dtm = new DefaultTableModel();
            if (pstmt.execute()) {
                rs = pstmt.getResultSet();
                rs.last();
                a = rs.getRow();
                rs.beforeFirst();
                ResultSetMetaData rsmd = rs.getMetaData();
                b = rsmd.getColumnCount();
                for (int col = 1; col <= rsmd.getColumnCount(); col++) {
                    dtm.addColumn(rsmd.getColumnLabel(col));
                    columnsList.add(rsmd.getColumnLabel(col));
                }
                if (a == 0) {
                    rows = new Object[a + 1][b];
                    for (int i = 0; i < b; i++) {
                        rows[a][i] = "-";
                    }
                } else {
                    rows = new Object[a][b];
                }
                int i = 0;
                while (rs.next()) {
                    Vector<String> row = new Vector<String>();
                    for (int col = 1; col <= rsmd.getColumnCount(); col++) {
                        int type = rsmd.getColumnType(col);
                        switch (type) {
                            case Types.INTEGER:
                                row.add(new Integer(rs.getInt(col)).toString());
                                rows[i][col - 1] = rs.getInt(col);
                                break;
                            case Types.VARCHAR:
                                row.add(rs.getString(col));
                                rows[i][col - 1] = rs.getString(col);
                                break;
                            case Types.DECIMAL:
                                if (rsmd.getColumnLabel(col).equals("Количество")) {
                                    if (rs.getString(col - 1).equals("1000 шт")) {
                                        rows[i][col - 1] = rs.getDouble(col) * 1000;
                                    }
                                    else if (rs.getString(col - 1).equals("100 шт")) {
                                        rows[i][col - 1] = rs.getDouble(col) * 100;
                                    }
                                    else if (rs.getString(col - 1).equals("шт")) {
                                        rows[i][col - 1] = rs.getDouble(col);
                                    }
                                    else rows[i][col - 1] = rs.getDouble(col);
                                }
                                else{
                                row.add(Double.toString(rs.getDouble(col)));
                                rows[i][col - 1] = rs.getDouble(col);
                                }
                                break;
                            case Types.TIMESTAMP:
                                row.add(rs.getDate(col).toString());
                                rows[i][col - 1] = rs.getDate(col);
                                break;
                            default:
                                throw new Exception("Неподдерживаемый тип");
                        }

                    }
                    i++;
                    dtm.addRow(row);

                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] columns = columnsList.toArray(new String[columnsList.size()]);
        TableModel model = new DefaultTableModel(rows, columns) {
            public Class getColumnClass(int column) {
                Class returnValue;
                if ((column >= 0) && (column < getColumnCount())) {
                    returnValue = getValueAt(0, column).getClass();
                } else {
                    returnValue = Object.class;
                }
                return returnValue;
            }
        };
        jTable1.setModel(model);
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(
                model);
        jTable1.setRowSorter(sorter);

    }

    private void tableShow(String sql, String sql1) {
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        ResultSet rs = null;

        ResultSet rs1 = null;
        DefaultTableModel dtm = null;
        DefaultTableModel dtm1 = null;
        Double balance = 0.000;
        Double cost_balance = 0.00;
        ArrayList<String> columnsList = new ArrayList();
        Object rows[][] = null;
        int a = 0;
        int b = 0;
        try {
            pstmt = dbConnection.prepareStatement(sql);
            pstmt1 = dbConnection.prepareStatement(sql1);
            dtm = new DefaultTableModel();
            dtm1 = new DefaultTableModel();
            if (pstmt.execute() & pstmt1.execute()) {
                rs = pstmt.getResultSet();
                rs1 = pstmt1.getResultSet();
                rs.last();
                rs1.last();
                a = rs.getRow() + rs1.getRow();
                rs.beforeFirst();
                rs1.beforeFirst();
                ResultSetMetaData rsmd = rs.getMetaData();
                ResultSetMetaData rsmd1 = rs1.getMetaData();
                b = rsmd.getColumnCount() + 1;
                if (a == 0) {
                    rows = new Object[a + 1][b];
                    for (int i = 0; i < b; i++) {
                        rows[a][i] = "-";
                    }
                } else {
                    rows = new Object[a][b];
                }
                for (int col = 1; col <= rsmd.getColumnCount(); col++) {
                    if (col == 4) {
                        dtm.addColumn(rsmd.getColumnLabel(col));
                        dtm.addColumn("Общая стоимость");
                        columnsList.add(rsmd.getColumnLabel(col));
                        columnsList.add("Общая стоимость");
                    } else {
                        dtm.addColumn(rsmd.getColumnLabel(col));
                        columnsList.add(rsmd.getColumnLabel(col));
                    }
                }
                Vector<String> title1 = new Vector(Arrays.asList("Акты закупки"));
                dtm.addRow(title1);
                int i = 0;
                while (rs.next()) {
                    Vector<String> row = new Vector<String>();
                    int k = 1;
                    for (int col = 1; col <= rsmd.getColumnCount(); col++) {
                        if ("number".equals(rsmd.getColumnName(col))) {
                            if (rs.getString(col - 1).equals("1000 шт")) {
                                balance += rs.getDouble(col) * 1000;
                            }
                            if (rs.getString(col - 1).equals("100 шт")) {
                                balance += rs.getDouble(col) * 100;
                            }
                            if (rs.getString(col - 1).equals("шт")) {
                                balance += rs.getDouble(col);
                            }
                        }
                        if (col == 5) {
                            double cost = rs.getDouble(3) * rs.getDouble(4);
                            cost_balance += cost;
                            row.add(String.format("%.2f", cost));
                            rows[i][k - 1] = cost;
                            k++;
                        }
                        int type = rsmd.getColumnType(col);
                        switch (type) {
                            case Types.INTEGER:
                                row.add(new Integer(rs.getInt(col)).toString());
                                rows[i][k - 1] = rs.getInt(col);
                                break;
                            case Types.VARCHAR:
                                row.add(rs.getString(col));
                                rows[i][k - 1] = rs.getString(col);
                                break;
                            case Types.DECIMAL:
                                if (rsmd.getColumnLabel(col).equals("Количество")) {
                                    if (rs.getString(col - 1).equals("1000 шт")) {
                                        rows[i][col - 1] = rs.getDouble(col) * 1000;
                                    }
                                    else if (rs.getString(col - 1).equals("100 шт")) {
                                        rows[i][col - 1] = rs.getDouble(col) * 100;
                                    }
                                    else if (rs.getString(col - 1).equals("шт")) {
                                        rows[i][col - 1] = rs.getDouble(col);
                                    }
                                    else rows[i][col - 1] = rs.getDouble(col);
                                }
                                else{
                                row.add(Double.toString(rs.getDouble(col)));
                                rows[i][col - 1] = rs.getDouble(col);
                                }
                                break;
                            case Types.TIMESTAMP:
                                row.add(rs.getDate(col).toString());
                                rows[i][k - 1] = rs.getDate(col);
                                break;
                            default:
                                throw new Exception("Неподдерживаемый тип");
                        }
                        k++;
                    }
                    dtm.addRow(row);
                    i++;
                }
                Vector<String> title2 = new Vector(Arrays.asList("Акты затрат"));
                dtm.addRow(title2);
                while (rs1.next()) {

                    Vector<String> row = new Vector<String>();
                    int k = 1;
                    for (int col = 1; col <= rsmd1.getColumnCount(); col++) {
                        if ("number".equals(rsmd1.getColumnName(col))) {
                            if (rs1.getString(col - 1).equals("1000 шт")) {
                                balance -= rs1.getDouble(col) * 1000;
                            }
                            if (rs1.getString(col - 1).equals("100 шт")) {
                                balance -= rs1.getDouble(col) * 100;
                            }
                            if (rs1.getString(col - 1).equals("шт")) {
                                balance -= rs1.getDouble(col);
                            }
                        }
                        System.out.println(rs1.getString(2));
                        if (col == 5) {
                            double cost = rs1.getDouble(3) * rs1.getDouble(4);
                            cost_balance -= cost;
                            row.add(String.format("%.2f", cost));
                            rows[i][k - 1] = cost;
                            k++;
                        }
                        int type = rsmd1.getColumnType(col);
                        switch (type) {
                            case Types.INTEGER:
                                row.add(new Integer(rs1.getInt(col)).toString());
                                rows[i][k - 1] = rs1.getInt(col);
                                break;
                            case Types.VARCHAR:
                                row.add(rs1.getString(col));
                                rows[i][k - 1] = rs1.getString(col);
                                break;
                            case Types.DECIMAL:
                                if (rsmd1.getColumnLabel(col).equals("Количество")) {
                                    if (rs1.getString(col - 1).equals("1000 шт")) {
                                        rows[i][col - 1] = rs1.getDouble(col) * 1000;
                                    }
                                    else if (rs1.getString(col - 1).equals("100 шт")) {
                                        rows[i][col - 1] = rs1.getDouble(col) * 100;
                                    }
                                    else if (rs1.getString(col - 1).equals("шт")) {
                                        rows[i][col - 1] = rs1.getDouble(col);
                                    }
                                    else rows[i][col - 1] = rs1.getDouble(col);
                                }
                                else{
                                row.add(Double.toString(rs1.getDouble(col)));
                                rows[i][col - 1] = rs1.getDouble(col);
                                }
                                break;
                            case Types.TIMESTAMP:
                                row.add(rs1.getDate(col).toString());
                                rows[i][k - 1] = rs1.getDate(col);
                                break;
                            default:
                                throw new Exception("Неподдерживаемый тип");
                        }
                        k++;
                    }
                    dtm.addRow(row);
                    i++;
                }
                dtm1.addColumn("");
                dtm1.addColumn("Количество");
                dtm1.addColumn("Общая стоимость");
                Vector<String> r = new Vector(Arrays.asList("Остаток", balance, String.format("%.2f", cost_balance)));
                dtm1.addRow(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] columns = columnsList.toArray(new String[columnsList.size()]);
        TableModel model = new DefaultTableModel(rows, columns) {
            public Class getColumnClass(int column) {
                Class returnValue;
                if ((column >= 0) && (column < getColumnCount())) {
                    returnValue = getValueAt(0, column).getClass();
                } else {
                    returnValue = Object.class;
                }
                return returnValue;
            }
        };
        jTable1.setModel(model);
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        jTable1.setRowSorter(sorter);
        jTable2.setModel(dtm1);
        jTable2.setVisible(true);
    }

    public Accounting() {
        initComponents();
        dbConnection = getConnection();
        String sql_command1 = "SELECT id, name from products_list order by name asc";
        comboFilling(sql_command1, myComboBox1);
        String sql_command2 = "SELECT id, name from equipments_list";
        comboFilling(sql_command2, myComboBox2);
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
        jComboBox1 = new javax.swing.JComboBox<>();
        dateChooserCombo2 = new datechooser.beans.DateChooserCombo();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        myComboBox2 = new test.myComboBox.MyComboBox();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Учет материалов");
        setLocation(new java.awt.Point(200, 100));

        ((JLabel) myComboBox1.getRenderer()).setHorizontalAlignment(JLabel.LEFT);
        JTextField editor1 = (JTextField) myComboBox1.getEditor().getEditorComponent();
        editor1.setHorizontalAlignment(JTextField.LEFT);
        myComboBox1.setMaximumRowCount(15);
        myComboBox1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        myComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myComboBox1ActionPerformed(evt);
            }
        });

        try {
            dateChooserCombo1.setDefaultPeriods(new datechooser.model.multiple.PeriodSet());
        } catch (datechooser.model.exeptions.IncompatibleDataExeption e1) {
            e1.printStackTrace();
        }
        dateChooserCombo1.setCalendarPreferredSize(new java.awt.Dimension(400, 300));

        jButton1.setText("Добавить / Удалить");
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(300, 5));

        jTable1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable1.setRequestFocusEnabled(false);
        jScrollPane1.setViewportView(jTable1);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Акт на поступление материала", "Акт на расход материала", "Наименование материала", "Оборудование" }));
        jComboBox1.setSelectedItem(null);

        try {
            dateChooserCombo2.setDefaultPeriods(new datechooser.model.multiple.PeriodSet());
        } catch (datechooser.model.exeptions.IncompatibleDataExeption e1) {
            e1.printStackTrace();
        }
        dateChooserCombo2.setCalendarPreferredSize(new java.awt.Dimension(400, 300));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("Наименование материала");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel2.setText("С");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel3.setText("По");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Выборка по дате");

        jButton2.setText("Обновить список");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        ((JLabel) myComboBox2.getRenderer()).setHorizontalAlignment(JLabel.LEFT);
        JTextField editor2 = (JTextField) myComboBox1.getEditor().getEditorComponent();
        editor1.setHorizontalAlignment(JTextField.LEFT);
        myComboBox2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        myComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myComboBox2ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Оборудование");

        jTable2.setBackground(new java.awt.Color(255, 0, 0));
        jTable2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jTable2.setEnabled(false);
        jScrollPane2.setViewportView(jTable2);

        jButton3.setText("Затраты за период времени");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(26, 26, 26)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(myComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 878, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(myComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 878, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5))
                                        .addGap(53, 53, 53)
                                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(jLabel4)
                                .addGap(29, 29, 29)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(dateChooserCombo1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(jLabel3)
                                .addGap(36, 36, 36)
                                .addComponent(dateChooserCombo2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(jButton3)))
                        .addGap(0, 42, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(myComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addComponent(jLabel5)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(myComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dateChooserCombo2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateChooserCombo1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(jLabel4))))
                    .addComponent(jButton3))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
                break;
            case "Акт на поступление материала":
                new AddAdmission().setVisible(true);
                break;
            case "Акт на расход материала":
                new AddConsumption().setVisible(true);
                break;

        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void myComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myComboBox1ActionPerformed
        // TODO add your handling code here:
        if (dateChooserCombo1.getSelectedDate() == null || dateChooserCombo2.getSelectedDate() == null) {
            String name = myComboBox1.getSelectedItem().toString();
            String sql_select = "select product 'Товар', unit 'Единица измерения', number 'Количество', price 'Цена/шт', equipment 'Оборудование', date Дата from purchase_list where product = '" + name + "' order by date desc;";
            String sql_select1 = "select product 'Товар', unit 'Единица измерения', number Количество, price 'Цена/шт', equipment Оборудование, date Дата from spending_list where product = '" + name + "'  order by date desc;";
            tableShow(sql_select, sql_select1);
        } else {
            Calendar d1 = dateChooserCombo1.getSelectedDate();
            long i1 = d1.getTimeInMillis();
            java.sql.Date date1 = new java.sql.Date(i1);
            Calendar d2 = dateChooserCombo2.getSelectedDate();
            long i2 = d2.getTimeInMillis();
            java.sql.Date date2 = new java.sql.Date(i2);
            String name = myComboBox1.getSelectedItem().toString();
            String sql_select = "select product Товар, unit 'Единица измерения', number Количество, price 'Цена/шт',  equipment Оборудование, date Дата from purchase_list where product = '" + name + "' and date between '" + date1 + "' AND '" + date2 + "' order by date desc;";
            String sql_select1 = "select product Товар, unit 'Единица измерения', number Количество, price 'Цена/шт', equipment Оборудование, date Дата from spending_list where product = '" + name + "' and date between '" + date1 + "' AND '" + date2 + "'  order by date desc;";
            tableShow(sql_select, sql_select1);

        }
    }//GEN-LAST:event_myComboBox1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String sql_command1 = "SELECT id, name from products_list";
        comboFilling(sql_command1, myComboBox1);
        this.dispose();
        new Accounting().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void myComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myComboBox2ActionPerformed
        // TODO add your handling code here:
        if (dateChooserCombo1.getSelectedDate() == null || dateChooserCombo2.getSelectedDate() == null) {
            jTable2.setVisible(false);
            String name = myComboBox2.getSelectedItem().toString();
            String sql_select = "select product 'Товар', unit 'Единица измерения', number 'Количество', price 'Цена/шт', equipment 'Оборудование', date Дата from spending_list where equipment = '" + name + "';";
            tableShow(sql_select);

        } else {
            jTable2.setVisible(false);
            Calendar d1 = dateChooserCombo1.getSelectedDate();
            long i1 = d1.getTimeInMillis();
            java.sql.Date date1 = new java.sql.Date(i1);
            Calendar d2 = dateChooserCombo2.getSelectedDate();
            long i2 = d2.getTimeInMillis();
            java.sql.Date date2 = new java.sql.Date(i2);
            String name = myComboBox2.getSelectedItem().toString();
            String sql_select = "select product 'Товар', unit 'Единица измерения', number 'Количество', price 'Цена/шт', equipment 'Оборудование', date Дата from spending_list where equipment = '" + name + "' and date between '" + date1 + "' AND '" + date2 + "' order by date desc;";
            tableShow(sql_select);

        }
    }//GEN-LAST:event_myComboBox2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        jTable2.setVisible(false);
        Calendar d1 = dateChooserCombo1.getSelectedDate();
        long i1 = d1.getTimeInMillis();
        java.sql.Date date1 = new java.sql.Date(i1);
        Calendar d2 = dateChooserCombo2.getSelectedDate();
        long i2 = d2.getTimeInMillis();
        java.sql.Date date2 = new java.sql.Date(i2);
        String sql_select = "select product 'Товар', unit 'Единица измерения', number 'Количество', price 'Цена/шт', equipment 'Оборудование', date Дата from spending_list where date between '" + date1 + "' AND '" + date2 + "' order by date desc;";
        tableShow(sql_select);

    }//GEN-LAST:event_jButton3ActionPerformed

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
    private datechooser.beans.DateChooserCombo dateChooserCombo2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    public test.myComboBox.MyComboBox myComboBox1;
    public test.myComboBox.MyComboBox myComboBox2;
    // End of variables declaration//GEN-END:variables
}
