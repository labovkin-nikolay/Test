/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Владелец
 */
public class TableRenderer extends DefaultTableCellRenderer
 {	
 public Component getTableCellRendererComponent(JTable table,
                                Object value,
                                boolean isSelected,
                                boolean hasFocus,
                                int row,
                                int column)
      {
         setText(value.toString());
		
         if ((table.getValueAt(row, 3)) == "Остаток")
         {
            setBackground(Color.RED);
            setForeground(Color.WHITE);
         }
         return this;		   
												   
      }
   }