/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Владелец
 */
public class Second extends javax.swing.JFrame {

    private Connection dbConnection;

    /**
     * Creates new form NewJFrame
     */
    public Second() {
        initComponents();
        tableShow();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Programm");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocation(new java.awt.Point(100, 100));
        setResizable(false);
        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWidths = new int[] {0, 8, 0, 8, 0, 8, 0};
        layout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        getContentPane().setLayout(layout);

        jButton1.setText("Записать");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 43;
        gridBagConstraints.insets = new java.awt.Insets(18, 25, 0, 0);
        getContentPane().add(jButton1, gridBagConstraints);

        jTextField1.setColumns(15);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 25, 0, 0);
        getContentPane().add(jTextField1, gridBagConstraints);

        jLabel1.setText("Имя");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 25, 0, 0);
        getContentPane().add(jLabel1, gridBagConstraints);

        jLabel2.setText("Поставщик");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(25, 25, 0, 0);
        getContentPane().add(jLabel2, gridBagConstraints);

        jTextField2.setColumns(15);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(19, 25, 0, 0);
        getContentPane().add(jTextField2, gridBagConstraints);

        jTextField3.setColumns(15);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 25, 0, 0);
        getContentPane().add(jTextField3, gridBagConstraints);

        jTextField4.setColumns(15);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 25, 0, 0);
        getContentPane().add(jTextField4, gridBagConstraints);

        jLabel3.setText("Количество");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 25, 0, 0);
        getContentPane().add(jLabel3, gridBagConstraints);

        jLabel4.setText("Цена");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 25, 0, 0);
        getContentPane().add(jLabel4, gridBagConstraints);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 362;
        gridBagConstraints.ipady = 375;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(18, 25, 15, 68);
        getContentPane().add(jScrollPane1, gridBagConstraints);

        jButton3.setText("Удалить");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(18, 37, 0, 0);
        getContentPane().add(jButton3, gridBagConstraints);

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
                dbConnection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "08071994");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return dbConnection;
        }
}
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:ff
        dbConnection = getConnection();
        Statement statement = null;
        String name = jTextField1.getText();
        String provider = jTextField2.getText();
        Integer number = Integer.valueOf(jTextField3.getText());
        Integer price = Integer.valueOf(jTextField4.getText());
        String insertTableSQL = "INSERT INTO consumption"
                + "(Name, Provider, Number, Price) " + "VALUES"
                + "( '" + name + "','" + provider + "'," + number + "," + price + " )";
        try {
            statement = dbConnection.createStatement();
            statement.executeUpdate(insertTableSQL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        tableShow();
    }//GEN-LAST:event_jButton1ActionPerformed
    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        dbConnection = getConnection();
        Statement statement = null;
        int[] selectedRows = jTable1.getSelectedRows();
        Object name = null;
        Object provider = null;
        Object number = null;
        Object price = null;
        for (int i = 0; i < selectedRows.length; i++) {
            int selIndex = selectedRows[i];
            TableModel model = jTable1.getModel();
            name = model.getValueAt(selIndex, 0);
            provider = model.getValueAt(selIndex, 1);
            number =  model.getValueAt(selIndex, 2);
            price =  model.getValueAt(selIndex, 3);

        }
        String deleteTableSQL = "DELETE FROM consumption WHERE `Name` = '" + name + "' AND `Provider` = '" + provider + "' AND `Number` = " 
                 + number + " AND `Price` = " + price + ";";
        try {
            statement = dbConnection.createStatement();
            // выполняем запрос delete SQL
            statement.execute(deleteTableSQL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        tableShow();


    }//GEN-LAST:event_jButton3ActionPerformed
    private void tableShow() {
        dbConnection = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        DefaultTableModel dtm = null;

        try {
            pstmt = dbConnection.prepareStatement("select name Имя, provider Поставщик, number Количество, price Цена from consumption;");
            dtm = new DefaultTableModel();

            if (pstmt.execute()) {
                rs = pstmt.getResultSet();

                //Формируем заголовки столбцов из названия полей -->
                ResultSetMetaData rsmd = rs.getMetaData();

                for (int col = 1; col <= rsmd.getColumnCount(); col++) {
                    dtm.addColumn(rsmd.getColumnLabel(col));
                }
                // <--

                while (rs.next()) {

                    Vector<String> row = new Vector<String>(); //Строка таблицы
                    /*Снова с помощью метаданных узнаем количество столбцов 
                     * в результате запроса и ниже в switch'е в зависимости 
                     * от типа текущего столбца забираем данные из ResultSet`a и пишем в 
                     * текущую строку - row. 
                     * */
                    for (int col = 1; col <= rsmd.getColumnCount(); col++) {

                        int type = rsmd.getColumnType(col);

                        switch (type) {

                            case Types.INTEGER:
                                row.add(new Integer(rs.getInt(col)).toString());
                                break;

                            case Types.VARCHAR:
                                row.add(rs.getString(col));
                                break;
                            default:
                                /*В этой моей тестовой таблице всего два типа полей: целое и строка. 
                                 *Соответственно если в таблице/запросе типов больше, то этот switch 
                                 *нужно расширить соответствующими типами, а сейчас, в случае если мы 
                                 *встретим неизвестный коду тип поля, я для наглядности бросаю исключение.                                    
                                 * */
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
        jTable1.setModel(dtm);
    }

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
            java.util.logging.Logger.getLogger(Second.class

.getName()).log(java.util.logging.Level.SEVERE, null, ex);

        

} catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Second.class

.getName()).log(java.util.logging.Level.SEVERE, null, ex);

        

} catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Second.class

.getName()).log(java.util.logging.Level.SEVERE, null, ex);

        

} catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Second.class

.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Second().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
