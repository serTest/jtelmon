/*
 * http://forums.sun.com/thread.jspa?threadID=5368192
 * Swing - How to set row data for JTable from ArrayList ?
 *
 * http://www.informit.com/articles/article.aspx?p=333472
 * Building a Professional Swing JTable
 *
 * http://www.informit.com/articles/article.aspx?p=337310
 * Sortable Swing JTable
 *
 * http://www.informit.com/articles/article.aspx?p=332278
 * Creating a Custom Java Swing Tablemodel
 *
 * http://download.oracle.com/javase/tutorial/uiswing/components/table.html
 * How to Use Tables
 * 
 */


package JD028;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.awt.BorderLayout;
import java.util.*;

/**
 * @author mrityunjoy_saha
 * @version 1.0
 */
public class TableWithList2 {

    private JFrame frame = new JFrame("TableWithList");
    private JTable table = null;

    private class MyTableModel extends DefaultTableModel{
    	private List<MyObject> list=null;
    	public MyTableModel(List<MyObject> list){
    		this.list=list;
    	}
    	public int getColumnCount(){
    		return 2;
    	}
    	public int getRowCount(){
    		// Better return the list size.
    		return 5;
    	}
    	public Object getValueAt(int row, int column){
    		if(row>=list.size()){
    			return null;
    		}
    		MyObject o=list.get(row);
    		if(column==0){
    			return o.getName();
    		} else if(column ==1){
    			return o.getValue();
    		} else{
    			return null;
    		}
    	}
    }
    public void test() throws Exception{
    	table=new JTable(10,2);
    	MyObject o1=new MyObject("Name", "Mrityunjoy");
    	MyObject o2=new MyObject("Surname", "Saha");
    	MyObject o3=new MyObject("Language", "English");
    	List<MyObject> list=new ArrayList<MyObject>();
    	list.add(o1);
    	list.add(o2);
    	list.add(o3);
    	table.setModel(new MyTableModel(list));
        frame.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
        frame.setSize(200, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private class MyObject{
    	private String name;
    	private String value;
    	// You can have any number of properties.
    	public MyObject(String name, String value){
    		this.name=name;
    		this.value=value;
    	}
    	public String getName(){
    		return this.name;
    	}
    	public void setName(String name){
    		this.name=name;
    	}
    	public String getValue(){
    		return this.value;
    	}
    	public void setValue(String value){
    		this.value=value;
    	}
    }

    public static void main(String args[]) throws Exception{
        new TableWithList2().test();
    }
}
