/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.myComboBox;

/**
 *
 * @author Владелец
 */
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
 
import javax.swing.AbstractListModel;
import javax.swing.MutableComboBoxModel;
 
 
public class DataBaseListModel extends AbstractListModel implements MutableComboBoxModel{
    
    private Vector<Object> dataRow;
    private Vector<Object> idRow;
    private Object selectedData;
    private Object selectedId;
    
    
    public DataBaseListModel() {
        dataRow = new Vector<Object>();
        idRow = new Vector<Object>();
        selectedData = null;
        selectedId = null;
    }
 
    @Override
    public Object getElementAt(int index) {
        return dataRow.get(index);
    }
 
    @Override
    public int getSize() {
        return dataRow.size();
    }
 
    @Override
    public Object getSelectedItem() {
        return selectedData;
    }
 
    @Override
    public void setSelectedItem(Object item) {
        selectedData = item;
        selectedId = idRow.get(dataRow.indexOf(item));
    }
 
    @Override
    public void addElement(Object element) {
        dataRow.add(element);
    }
 
    @Override
    public void insertElementAt(Object arg0, int arg1) {
        // TODO Auto-generated method stub
        
    }
 
    @Override
    public void removeElement(Object arg0) {
        // TODO Auto-generated method stub
        
    }
 
    @Override
    public void removeElementAt(int arg0) {
        // TODO Auto-generated method stub
        
    }
    
    //Это наш метод получения ID выбранного элемента
    public Object getSelectedItemId(){
        return selectedId;
    }
    
    public void addElement(Object id, Object data){
        idRow.add(id);
        dataRow.add(data);
    }
 
}
