package mainFrame;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class TrainSelectionTableModel extends AbstractTableModel{

	private Vector<Object[]> data = new Vector<Object[]>();
	private String[] columns={"選擇","車次","優惠*","出發時間","抵達時間","行車時間"};
	public int getColumnCount() {return columns.length;}
	public int getRowCount() {return data.size();}
	public Object getValueAt(int row, int col) {return data.get(row)[col];}
	public String getColumnName(int col) {return columns[col];} 
	public Class getColumnClass(int col) {
		return getValueAt(0,col).getClass();
	}
	public boolean isCellEditable(int row,int col) {return false;}
	public void setValueAt(Object value,int row,int col) {
		data.get(row)[col] = value;
		fireTableCellUpdated(row,col);
	}
	public String[] getColumnsName(){return columns;}
	public Vector<Object[]> getData(){
		return data;
	}
	public void editContent(Object[][] content){
//		if(content == null){return;}
		for(int i = 0 ; i < content.length ; i++){
			//* check if content valid
			if(content[i].length != getColumnCount()){
				System.out.println("content data structure invalid");
				return ;
			}
			data.add(content[i]);
		}
	}
	public static void main(String[] args) {
		TrainSelectionTableModel model = new TrainSelectionTableModel();
		model.getData().add(new String[]{"777","888","999"});
		model.getData().add(new String[]{"777","888","999"});
		model.getData().add(new String[]{"777","888","999"});
		model.getData().add(new String[]{"777","888","999"});
		model.getData().add(new String[]{"777","888","999"});
		model.getData().get(0)[0] = "789";
		System.out.println(model.getData().get(0)[0]);
	}
}
