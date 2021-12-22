package mainFrame;

import java.awt.EventQueue;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JPanel;

public class JDailyTimeTable {

	private JFrame frame;
	private Calendar startDate;
	private String[][] toSouthTableData;
	private String[][] toNorthTableData;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JDailyTimeTable window = new JDailyTimeTable(Calendar.getInstance());
//					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JDailyTimeTable(Calendar startDate) {
		ArrayList<String[]> tmpTableData; 
		this.startDate = startDate;
		tmpTableData = DailyTimeTable.generateTable(startDate,0);
		this.toSouthTableData = new String[tmpTableData.size()][];
		for(int i = 0 ; i != tmpTableData.size() ; i++) {
			String row[] = tmpTableData.get(i);
			this.toSouthTableData[i] = row.clone();
		}
		tmpTableData = DailyTimeTable.generateTable(startDate,1);
		this.toNorthTableData = new String[tmpTableData.size()][];
		for(int i = 0 ; i != tmpTableData.size() ; i++) {
			String row[] = tmpTableData.get(i);
			this.toNorthTableData[i] = row.clone();
		}
		initialize();
		frame.setVisible(true);
	}
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 680, 770);
		frame.setTitle("當日時刻表");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		String tableHead = "車次 南港站 台北站 板橋站 桃園站 新竹站 苗栗站 台中站 彰化站 雲林站 嘉義站 台南站 左營站";
		
		Date startDateData = startDate.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("Y年M月d日 E 時刻表");
		String topicStr = dateFormat.format(startDateData);
		
		DefaultTableModel toSouthTableModel = new DefaultTableModel(this.toSouthTableData, tableHead.split(" ")) {
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		JTable toSouthTable = new JTable(toSouthTableModel);
		toSouthTable.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollPane = new JScrollPane(toSouthTable);
		scrollPane.setBounds(10, 52, 644, 311);
		frame.getContentPane().add(scrollPane);
		
		DefaultTableModel toNorthTableModel = new DefaultTableModel(this.toNorthTableData, tableHead.split(" ")) {
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		JTable toNorthTable = new JTable(toNorthTableModel);
		toNorthTable.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollPane_1 = new JScrollPane(toNorthTable);
		scrollPane_1.setBounds(10, 399, 644, 311);
		frame.getContentPane().add(scrollPane_1);
		
		JLabel toSouthLabel = new JLabel("\u5317\u4E0A");
		toSouthLabel.setBounds(10, 373, 101, 15);
		frame.getContentPane().add(toSouthLabel);
		
		JLabel toNorthLabel = new JLabel("\u5357\u4E0B");
		toNorthLabel.setBounds(10, 37, 132, 15);
		frame.getContentPane().add(toNorthLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.ORANGE);
		panel.setBounds(10, 7, 644, 24);
		frame.getContentPane().add(panel);
		
		JLabel topicLabel = new JLabel(topicStr);
		panel.add(topicLabel);
	}
}
