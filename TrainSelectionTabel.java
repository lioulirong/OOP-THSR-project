package mainFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.swing.ListSelectionModel;

public class TrainSelectionTabel extends JFrame {
	/**
	 * Create the frame.
	 */
	// *singleton design pattern
	private TrainSelectionTableModel mtm1 = new TrainSelectionTableModel()
									,mtm2 = new TrainSelectionTableModel();
	private JTable ojt,rjt;
	private Object outwardData[][],returnData[][];
	public static TrainSelectionTabel instance;
	private boolean willReturn = false;
	public static TrainSelectionTabel getInstance(Object data1[][],Object data2[][],boolean willReturn){
		//* 如果查詢的請求沒有改變，那就都回傳同一個實體
		if(instance==null){
			instance = new TrainSelectionTabel(data1,data2,willReturn);
			System.out.println("new instance : TrainSelectionTabel");
			return instance;
		}else if(instance.willReturn != willReturn){
			instance.dispose();
			instance = new TrainSelectionTabel(data1,data2,willReturn);
			System.out.println("willReturn : TrainSelectionTabel");
			return instance;
		}else if(instance.outwardData.length != data1.length || instance.returnData.length != data2.length){
			instance.dispose();
			instance = new TrainSelectionTabel(data1,data2,willReturn);
			System.out.println("new data : TrainSelectionTabel");
			return instance;
		}else if(instance.outwardData.length == data1.length || instance.returnData.length == data2.length){
			
			for(int i = 0 ; i != data1.length;i++){
				if(!instance.outwardData[i][1].equals(data1[i][1])){
					instance.dispose();
					instance = new TrainSelectionTabel(data1,data2,willReturn);
					System.out.println("new data 2 : TrainSelectionTabel ");
					return instance;
				}
			}
			for(int i = 0 ; i != data2.length;i++){
				if(!instance.returnData[i][1].equals(data2[i][1])){
					instance.dispose();
					instance = new TrainSelectionTabel(data1,data2,willReturn);
					System.out.println("new data 2 : TrainSelectionTabel ");
					return instance;
				}
			}
			
		}
		System.out.println("no new data");
		return instance;
	}
	
	private TrainSelectionTabel(Object data1[][],Object data2[][],boolean willReturn) {
		this.outwardData = data1;
		this.returnData = data2;
		this.willReturn = willReturn;
		Object[][] bookingData = new Object[2][];
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);
		setResizable(false);
		setTitle("選擇車次");
		
		JTextArea detailTextArea = new JTextArea();
		detailTextArea.setForeground(Color.DARK_GRAY);
		detailTextArea.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		detailTextArea.setFont(new Font("Monospaced", Font.BOLD, 13));
		detailTextArea.setText("行程\t日期\t車次\t起站\t迄站\t初發時間\t到達時間 \r\n");
//		detailTextArea.append("車廂:標準\t票數:1\t\t\t\t總票價\tNT500 \r\n");
		detailTextArea.setEditable(false);
		detailTextArea.setBounds(10, 460, 765, 90);
		getContentPane().add(detailTextArea);
		
		mtm1.editContent(this.outwardData);
		ojt=new JTable(mtm1);
		ojt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ojt.setPreferredScrollableViewportSize(new Dimension(400,100));
        ListSelectionModel rowSM = ojt.getSelectionModel();
        rowSM.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                //Ignore extra messages.
                if (e.getValueIsAdjusting()) return;

                ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                if (lsm.isSelectionEmpty()) {
                    System.out.println("No rows are selected.");
                } else {
                    int selectedRow = lsm.getMinSelectionIndex();
                    for(int i = 0 ; i != data1.length;i++){
                    	data1[i][0] = false;
                    	ojt.setValueAt(false, i,0);
                    }
                    data1[selectedRow][0] = true;
                    bookingData[0] = data1[selectedRow];
                    ojt.setValueAt(true, selectedRow,0);
                    System.out.println("Row " + selectedRow
                                       + " is now selected.");
                    Date startDateData = LookUpWindow.inform.getOutwardtDate().getTime();
                    Date backDateData = LookUpWindow.inform.getReturnDate().getTime();
            		SimpleDateFormat dateFormat = new SimpleDateFormat("YMMdd");
            		String startDateStr = dateFormat.format(startDateData),backDateStr = dateFormat.format(backDateData);
            		try {
            		detailTextArea.setText("行程\t日期\t車次\t起站\t迄站\t初發時間\t到達時間 \r\n");
            		detailTextArea.append("去程\t"+startDateStr+"\t"+bookingData[0][1]+"\t"+LookUpWindow.inform.getStartStationName()+"\t"+LookUpWindow.inform.getEndStationName()+"\t"
    						+bookingData[0][3]+"\t"+bookingData[0][4]+"\t\r\n");
                    detailTextArea.append("去程\t"+backDateStr+"\t"+bookingData[1][1]+"\t"+LookUpWindow.inform.getEndStationName()+"\t"+LookUpWindow.inform.getStartStationName()+"\t"
    						+bookingData[1][3]+"\t"+bookingData[1][4]+"\t\r\n");}catch(Exception eee) {}
                }
            }
        });
        
        mtm2.editContent(this.returnData);
        rjt=new JTable(mtm2);
        rjt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rjt.setPreferredScrollableViewportSize(new Dimension(400,100));
        ListSelectionModel rowSM2 = rjt.getSelectionModel();
        rowSM2.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                //Ignore extra messages.
                if (e.getValueIsAdjusting()) return;

                ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                if (lsm.isSelectionEmpty()) {
                    System.out.println("No rows are selected.");
                } else {
                    int selectedRow = lsm.getMinSelectionIndex();
                    for(int i = 0 ; i != data2.length;i++){
                    	data2[i][0] = false;
                    	rjt.setValueAt(false, i,0);
                    }
                    data2[selectedRow][0] = true;
                    bookingData[1] = data2[selectedRow];
                    rjt.setValueAt(true, selectedRow,0);
                    System.out.println("Row " + selectedRow
                                       + " is now selected.");
                    Date startDateData = LookUpWindow.inform.getOutwardtDate().getTime();
                    Date backDateData = LookUpWindow.inform.getReturnDate().getTime();
            		SimpleDateFormat dateFormat = new SimpleDateFormat("YMMdd");
            		String startDateStr = dateFormat.format(startDateData),backDateStr = dateFormat.format(backDateData);
            		try {
            		detailTextArea.setText("行程\t日期\t車次\t起站\t迄站\t初發時間\t到達時間 \r\n");
            		detailTextArea.append("去程\t"+startDateStr+"\t"+bookingData[0][1]+"\t"+LookUpWindow.inform.getStartStationName()+"\t"+LookUpWindow.inform.getEndStationName()+"\t"
    						+bookingData[0][3]+"\t"+bookingData[0][4]+"\t\r\n");
                    detailTextArea.append("去程\t"+backDateStr+"\t"+bookingData[1][1]+"\t"+LookUpWindow.inform.getEndStationName()+"\t"+LookUpWindow.inform.getStartStationName()+"\t"
    						+bookingData[1][3]+"\t"+bookingData[1][4]+"\t\r\n");}catch(Exception eee) {}
                }
            }
        });
        JLabel gotoLabel = new JLabel("去程");
        gotoLabel.setBounds(15, 13, 46, 15);
        JLabel backLabel = new JLabel("回程");
        backLabel.setBounds(15, 220, 46, 15);
        JLabel detailLabel = new JLabel("交易明細");
        detailLabel.setBounds(10, 435, 70, 15);
        
        JScrollPane outwardScrollPane= new JScrollPane(ojt);
        JScrollPane returnScrollPane= new JScrollPane(rjt);
        outwardScrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        outwardScrollPane.setBounds(10, 35, 770, 175);
        returnScrollPane.setBounds(10, 243, 770, 175);
        returnScrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
        //如果沒有訂購回程，那就不需要顯示回程版面
        if(!LookUpWindow.inform.willReturn()){
            outwardScrollPane.setBounds(10, 35, 770, 385);
            returnScrollPane.setBounds(10, 243, 0,0);
            backLabel.setBounds(15, 217, 0, 0);
        }
        JButton lookUpDailyTimeTableBnt = new JButton("查詢當日車次");
        lookUpDailyTimeTableBnt.setBounds(655, 8, 128, 25);
        lookUpDailyTimeTableBnt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new JDailyTimeTable(LookUpWindow.inform.getOutwardtDate());
			}
		});
        JButton confirmBookingBnt = new JButton("確認訂票");
        confirmBookingBnt.setBounds(677, 430, 100, 25);
        confirmBookingBnt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(bookingData[0]==null) {
					JOptionPane.showMessageDialog(TrainSelectionTabel.instance,"尚未選擇去程",
                             "溫馨提醒", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if(LookUpWindow.inform.willReturn() && bookingData[1]==null) {
					JOptionPane.showMessageDialog(TrainSelectionTabel.instance,"尚未選擇回程",
                            "溫馨提醒", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				int result = JOptionPane.showConfirmDialog(TrainSelectionTabel.instance,"你確定要這樣選?","確認定票",
                        JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
				if (result==JOptionPane.NO_OPTION) {return;}
				if (result==JOptionPane.YES_OPTION) {
					int sum = 0;
					int ticketPrice = parsePrice(LookUpWindow.inform.getStartStation(),LookUpWindow.inform.getEndStation(),(String)bookingData[0][2]);
					int backTicketPrice = 0;
					sum += ticketPrice*LookUpWindow.inform.getTotalTicketNumber();
					String backSeat = "";
					if(bookingData[1] != null) {
						backTicketPrice = parsePrice(LookUpWindow.inform.getEndStation(),LookUpWindow.inform.getStartStation(),(String)bookingData[1][2]);
						sum += backTicketPrice*LookUpWindow.inform.getTotalTicketNumber();
						backSeat = LookUpWindow.seatCapacity.getSeat((String)bookingData[1][1], LookUpWindow.inform.getReturnDate(), LookUpWindow.inform.getTotalTicketNumber(), LookUpWindow.inform.getSeatPreference());
					}
					String goSeat = LookUpWindow.seatCapacity.getSeat((String)bookingData[0][1], LookUpWindow.inform.getOutwardtDate(), LookUpWindow.inform.getTotalTicketNumber(), LookUpWindow.inform.getSeatPreference());
					Random random = new Random();
					String code1 = ""+(1000000+random.nextInt(8000000));
					String code2 = "";
					
					LookUpInform tmpInfo = LookUpWindow.inform;
					LookUpWindow.historyArray.add(
							new BookingHistory(tmpInfo.getId(),code1,tmpInfo.getOutwardtDate(),(String)bookingData[0][1],tmpInfo.getStartStationName(),tmpInfo.getEndStationName()));
					if(bookingData[1] != null){
						code2 = ""+(1000000+random.nextInt(8000000));
					LookUpWindow.historyArray.add(
							new BookingHistory(tmpInfo.getId(),code2,tmpInfo.getReturnDate(),(String)bookingData[1][1],tmpInfo.getEndStationName(),tmpInfo.getStartStationName()));}
					JOptionPane.showMessageDialog(TrainSelectionTabel.instance,"定位成功\r\n"+"去程票價 : "+ticketPrice+"\r\n"
							+"座位 : "+goSeat+"\r\n"
							+"回程票價 : "+backTicketPrice+"\r\n"
							+"座位 : "+backSeat+"\r\n"
							+"總價 : "+sum+"\r\n"
							+"去程訂位代號 : "+code1+" "
							+"回程訂位代號 : "+code2,
        					"溫馨提醒", JOptionPane.INFORMATION_MESSAGE);
					instance.dispose();
					return;
				}
			}
		});
        
        getContentPane().add(outwardScrollPane);
        getContentPane().add(returnScrollPane);
        getContentPane().add(gotoLabel);
        getContentPane().add(backLabel);
        getContentPane().add(detailLabel);
        getContentPane().add(confirmBookingBnt);
        getContentPane().add(lookUpDailyTimeTableBnt);
        
	}
	private int parsePrice(String OriginStationID,String DesrinationStationId,String discount) {
		int ticketPrice = 0;
		if(Double.parseDouble(discount) == 1.0) {discount = "standard";}
		try {
    		JSONTokener tokener = new JSONTokener(new FileInputStream("price.json"));
    		JSONArray root = new JSONArray(tokener);
    		for(Object _node : root) {
    			JSONObject node = (JSONObject) _node;
    			String id1 = node.getString("OriginStationID");
    			if(id1.equals(OriginStationID)) {
    				JSONArray destinationStations = node.getJSONArray("DesrinationStations");
    				for(Object destinationStation : destinationStations) {
    					if(((JSONObject)destinationStation).getString("ID").equals(DesrinationStationId)) {
    						JSONArray fareArray = ((JSONObject)destinationStation).getJSONArray("Fares");
    						for(Object fare : fareArray) {
    							String ticketType = ((JSONObject) fare).getString("TicketType");
    							if(ticketType.equals(discount)) {
    								ticketPrice = ((JSONObject) fare).getInt("Price");
    								System.out.println(ticketPrice);
    								break;
    							}
    						}
    					}
    				}
    			}
    		}
    		
		}catch(FileNotFoundException fe) {
			fe.printStackTrace();
		}
		return ticketPrice;
	}
	
	public static void main(String[] args) {
		if(LookUpWindow.inform == null) {
			LookUpWindow.inform = new LookUpInform("id",1,2,360+30*2,360+30*4,Calendar.getInstance(),Calendar.getInstance(),0,0,true);
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Object data1[][] = {{false,"652","","~~:~~","~~:~~","~~:~~"},{false,"652","","~~:~~","~~:~~","~~:~~"},{false,"652","","~~:~~","~~:~~","~~:~~"},{false,"652","","~~:~~","~~:~~","~~:~~"},{false,"652","","~~:~~","~~:~~","~~:~~"},{false,"652","","~~:~~","~~:~~","~~:~~"},{false,"652","","~~:~~","~~:~~","~~:~~"},{false,"652","","~~:~~","~~:~~","~~:~~"},{false,"652","","~~:~~","~~:~~","~~:~~"},{false,"652","","~~:~~","~~:~~","~~:~~"},{false,"652","","~~:~~","~~:~~","~~:~~"},{false,"652","","~~:~~","~~:~~","~~:~~"},{false,"652","","~~:~~","~~:~~","~~:~~"},{false,"652","","~~:~~","~~:~~","~~:~~"},{false,"652","","~~:~~","~~:~~","~~:~~"},{false,"652","","~~:~~","~~:~~","~~:~~"},{false,"652","","~~:~~","~~:~~","~~:~~"},{false,"652","","~~:~~","~~:~~","~~:~~"},{false,"652","","~~:~~","~~:~~","~~:~~"},{false,"652","","~~:~~","~~:~~","~~:~~"},{false,"652","","~~:~~","~~:~~","~~:~~"},{false,"652","","~~:~~","~~:~~","~~:~~"},{false,"652","","~~:~~","~~:~~","~~:~~"},{false,"652","","~~:~~","~~:~~","~~:~~"},{false,"652","","~~:~~","~~:~~","~~:~~"},{false,"652","","~~:~~","~~:~~","~~:~~"},{false,"652","","~~:~~","~~:~~","~~:~~"},{false,"652","","~~:~~","~~:~~","~~:~~"},{false,"652","","~~:~~","~~:~~","~~:~~"},{false,"652","","~~:~~","~~:~~","~~:~~"}};
					Object data2[][] = {{false,"741","","~~:~~","~~:~~","~~:~~"},{false,"741","","~~:~~","~~:~~","~~:~~"},{false,"741","","~~:~~","~~:~~","~~:~~"},{false,"741","","~~:~~","~~:~~","~~:~~"},{false,"741","","~~:~~","~~:~~","~~:~~"},{false,"741","","~~:~~","~~:~~","~~:~~"},{false,"741","","~~:~~","~~:~~","~~:~~"},{false,"741","","~~:~~","~~:~~","~~:~~"},{false,"741","","~~:~~","~~:~~","~~:~~"},{false,"741","","~~:~~","~~:~~","~~:~~"},{false,"741","","~~:~~","~~:~~","~~:~~"},{false,"741","","~~:~~","~~:~~","~~:~~"},{false,"741","","~~:~~","~~:~~","~~:~~"},{false,"741","","~~:~~","~~:~~","~~:~~"},{false,"741","","~~:~~","~~:~~","~~:~~"},{false,"741","","~~:~~","~~:~~","~~:~~"},{false,"741","","~~:~~","~~:~~","~~:~~"},{false,"741","","~~:~~","~~:~~","~~:~~"},{false,"741","","~~:~~","~~:~~","~~:~~"},{false,"741","","~~:~~","~~:~~","~~:~~"},{false,"741","","~~:~~","~~:~~","~~:~~"}};
					TrainSelectionTabel frame = new TrainSelectionTabel(data1,data2,true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

