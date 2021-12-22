package mainFrame;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.constraints.RangeConstraint;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import java.awt.Color;

public class LookUpWindow {

	private JFrame frmBooksystem;
	private JTextField textField;
	private JTextField textField_2;
	private Date after,before;
	public static LookUpInform inform;
	public static SeatCapacity seatCapacity;
	public static ArrayList<BookingHistory> historyArray;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					LookUpWindow window = new LookUpWindow();
					window.frmBooksystem.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LookUpWindow() {
		seatCapacity = new SeatCapacity();
		historyArray = new ArrayList<BookingHistory>();
		Calendar calendar = Calendar.getInstance();
		this.after = calendar.getTime();
		calendar.add(Calendar.MONTH, 1);
		this.before = calendar.getTime();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
        Locale.setDefault(new Locale("en"));
		frmBooksystem = new JFrame();
		frmBooksystem.setResizable(false);
		frmBooksystem.setTitle("訂票系統");
		frmBooksystem.setBounds(100, 100, 800, 420);
		frmBooksystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBooksystem.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		tabbedPane.setBounds(10, 10, 764, 280);
		frmBooksystem.getContentPane().add(tabbedPane);
		boolean willReturn = false;
		
		//@ 一般訂票窗口
		JPanel panel = new JPanel();
		tabbedPane.addTab("一般訂票", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblNewLabelG = new JLabel("身分id");
		lblNewLabelG.setBounds(24, 34, 46, 15);
		panel.add(lblNewLabelG);
		
		JLabel lblTakeOffG = new JLabel("起程時間");
		lblTakeOffG.setBounds(24, 69, 56, 15);
		panel.add(lblTakeOffG);
		
		JLabel lblSeatPreferG = new JLabel("座位偏好");
		lblSeatPreferG.setBounds(24, 103, 56, 15);
		panel.add(lblSeatPreferG);
		
		JLabel lblStartG = new JLabel("起站");
		lblStartG.setBounds(24, 140, 56, 15);
		panel.add(lblStartG);
		
		JLabel lblEndG = new JLabel("終點");
		lblEndG.setBounds(24, 176, 56, 15);
		panel.add(lblEndG);
		
		JLabel lblTicketNumG = new JLabel("\u7968\u6578");
		lblTicketNumG.setBounds(24, 212, 35, 15);
		panel.add(lblTicketNumG);
		
		textField = new JTextField();
		textField.setBounds(90, 31, 96, 21);
		panel.add(textField);
		textField.setColumns(10);
		
        // Create the JDatePicker
        final JDatePicker datePickerG = new JDatePicker(Calendar.getInstance());
        datePickerG.getButton().addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        datePickerG.addDateSelectionConstraint(new RangeConstraint(after, before));
        datePickerG.setBounds(90, 66, 150, 30);
        panel.add(datePickerG);
		
		JComboBox seatPreferCombo = new JComboBox(new String[]{"--","靠窗","靠走道"});
		seatPreferCombo.setToolTipText("seat preferrence");
		seatPreferCombo.setBounds(90, 97, 75, 21);
		panel.add(seatPreferCombo);
		
		JComboBox startStationCombo = new JComboBox(new String[]{"請選擇...","南港","台北","板橋","桃園","新竹","苗栗","台中","彰化","雲林","嘉義","台南","左營"});
		startStationCombo.setToolTipText("tain station id");
		startStationCombo.setBounds(90, 134, 75, 21);
		panel.add(startStationCombo);
		
		JComboBox endStationCombo = new JComboBox(new String[]{"請選擇...","南港","台北","板橋","桃園","新竹","苗栗","台中","彰化","雲林","嘉義","台南","左營"});
		endStationCombo.setToolTipText("tain station id");
		endStationCombo.setBounds(90, 170, 75, 21);
		panel.add(endStationCombo);
		
		JComboBox genCombo = new JComboBox(new Integer[]{0,1,2,3,4,5,6,7,8,9,10});
		genCombo.setToolTipText("ticket amount");
		genCombo.setBounds(119, 209, 46, 21);
		panel.add(genCombo);
		
		class DepartureTime{
			String[] getTime(){
				String out[] = new String[36];
				for(int i = 0 ,j = 6; i != out.length ; i++){
					out[i++] = ""+j+":00";
					out[i] = ""+j+":30";
					j++;
				}
				return out;
				}
		}
		
		JLabel lblTakeOff_1G = new JLabel("起程時間");
		lblTakeOff_1G.setBounds(426, 69, 56, 15);
		panel.add(lblTakeOff_1G);
		
		
        final JDatePicker datePicker_2G = new JDatePicker(Calendar.getInstance());
        datePicker_2G.getButton().addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        datePicker_2G.addDateSelectionConstraint(new RangeConstraint(after, before));
        datePicker_2G.setBounds(492, 66, 150, 30);
        datePicker_2G.setEnabled(false);
        panel.add(datePicker_2G);
		
		JComboBox returnEpochCombo = new JComboBox(new DepartureTime().getTime());
		returnEpochCombo.setEnabled(false);
		returnEpochCombo.setBounds(652, 66, 56, 21);
		panel.add(returnEpochCombo);
		JRadioButton rdbtnNewRadioButtonG = new JRadioButton("訂購回程");
		rdbtnNewRadioButtonG.setBounds(333, 65, 85, 23);
		rdbtnNewRadioButtonG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnNewRadioButtonG.isSelected()){
					datePicker_2G.setEnabled(true);
					returnEpochCombo.setEnabled(true);
				}
				if(!rdbtnNewRadioButtonG.isSelected()){
					datePicker_2G.setEnabled(false);
					returnEpochCombo.setEnabled(false);
				}
				
			}
		});
		panel.add(rdbtnNewRadioButtonG);
		
		JLabel lblGenral = new JLabel("\u5168\u7968");
		lblGenral.setBounds(90, 212, 35, 15);
		panel.add(lblGenral);
		
		JComboBox childCombo = new JComboBox(new Integer[]{0,1,2,3,4,5,6,7,8,9,10});
		childCombo.setToolTipText("ticket amount");
		childCombo.setBounds(204, 209, 46, 21);
		panel.add(childCombo);
		
		JLabel lblChild = new JLabel("\u5B69\u7AE5");
		lblChild.setBounds(175, 212, 35, 15);
		panel.add(lblChild);
		
		JComboBox elderComboBox = new JComboBox(new Integer[]{0,1,2,3,4,5,6,7,8,9,10});
		elderComboBox.setToolTipText("ticket amount");
		elderComboBox.setBounds(289, 209, 46, 21);
		panel.add(elderComboBox);
		
		JLabel lblElder = new JLabel("\u656C\u8001");
		lblElder.setBounds(260, 212, 35, 15);
		panel.add(lblElder);
		
		JLabel lblLove = new JLabel("\u611B\u5FC3");
		lblLove.setBounds(343, 212, 35, 15);
		panel.add(lblLove);
		
		JComboBox loveCombo = new JComboBox(new Integer[]{0,1,2,3,4,5,6,7,8,9,10});
		loveCombo.setToolTipText("ticket amount");
		loveCombo.setBounds(372, 209, 46, 21);
		panel.add(loveCombo);
		
		JComboBox startEpochCombo = new JComboBox(new DepartureTime().getTime());
		startEpochCombo.setBounds(250, 66, 56, 21);
		panel.add(startEpochCombo);
		
		

		
		//@ 大學生訂票窗口--------------------------------------------------------------------------
		JPanel panel_1 = new JPanel();
		panel_1.setToolTipText("");
		tabbedPane.addTab("大學生訂票", null, panel_1, null);
		panel_1.setLayout(null);
		
		
		JLabel lblNewLabel = new JLabel("身分id");
		lblNewLabel.setBounds(24, 34, 46, 15);
		panel_1.add(lblNewLabel);
		
		JLabel lblTakeOff = new JLabel("起程時間");
		lblTakeOff.setBounds(24, 69, 56, 15);
		panel_1.add(lblTakeOff);
		
		JLabel lblSeatPrefer = new JLabel("座位偏好");
		lblSeatPrefer.setBounds(24, 103, 56, 15);
		panel_1.add(lblSeatPrefer);
		
		JLabel lblStart = new JLabel("起站");
		lblStart.setBounds(24, 140, 56, 15);
		panel_1.add(lblStart);
		
		JLabel lblEnd = new JLabel("終點");
		lblEnd.setBounds(24, 176, 56, 15);
		panel_1.add(lblEnd);
		
		JLabel lblTicketNum = new JLabel("優惠票數");
		lblTicketNum.setBounds(24, 212, 56, 15);
		panel_1.add(lblTicketNum);
		
		textField_2 = new JTextField();
		textField_2.setBounds(90, 31, 96, 21);
		panel_1.add(textField_2);
		textField_2.setColumns(10);
		
        // Create the JDatePicker
        final JDatePicker datePicker = new JDatePicker(Calendar.getInstance());
        datePicker.getButton().addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        datePicker.addDateSelectionConstraint(new RangeConstraint(after, before));
        datePicker.setBounds(90, 66, 150, 30);
        panel_1.add(datePicker);
		
		JComboBox seatComboU = new JComboBox(new String[]{"--","靠窗","靠走道"});
		seatComboU.setToolTipText("seat preferrence");
		seatComboU.setBounds(90, 97, 75, 21);
		panel_1.add(seatComboU);
		
		JComboBox startComboU = new JComboBox(new String[]{"請選擇...","南港","台北","板橋","桃園","新竹","苗栗","台中","彰化","雲林","嘉義","台南","左營"});
		startComboU.setToolTipText("tain station id");
		startComboU.setBounds(90, 134, 75, 21);
		panel_1.add(startComboU);
		
		JComboBox arrivalCombo = new JComboBox(new String[]{"請選擇...","南港","台北","板橋","桃園","新竹","苗栗","台中","彰化","雲林","嘉義","台南","左營"});
		arrivalCombo.setToolTipText("tain station id");
		arrivalCombo.setBounds(90, 170, 75, 21);
		panel_1.add(arrivalCombo);
		
		JComboBox ticketNumCombo = new JComboBox(new Integer[]{0,1,2,3,4,5,6,7,8,9,10});
		ticketNumCombo.setToolTipText("ticket amount");
		ticketNumCombo.setBounds(90, 206, 75, 21);
		panel_1.add(ticketNumCombo);
		
		JLabel lblTakeOff_1 = new JLabel("起程時間");
		lblTakeOff_1.setBounds(426, 69, 56, 15);
		panel_1.add(lblTakeOff_1);
		
		
        final JDatePicker datePicker_2 = new JDatePicker(Calendar.getInstance());
        datePicker_2.getButton().addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        datePicker_2.addDateSelectionConstraint(new RangeConstraint(after, before));
        datePicker_2.setBounds(492, 66, 150, 30);
        datePicker_2.setEnabled(false);
        panel_1.add(datePicker_2);
        
		JComboBox returnEpochComboU = new JComboBox(new DepartureTime().getTime());
		returnEpochComboU.setEnabled(false);
		returnEpochComboU.setBounds(652, 66, 56, 21);
		panel_1.add(returnEpochComboU);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("訂購回程");
		rdbtnNewRadioButton.setBounds(333, 65, 85, 23);
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnNewRadioButton.isSelected()){
					datePicker_2.setEnabled(true);
					returnEpochComboU.setEnabled(true);
				}
				if(!rdbtnNewRadioButton.isSelected()){
					datePicker_2.setEnabled(false);
					returnEpochComboU.setEnabled(false);
				}
			}
		});
		panel_1.add(rdbtnNewRadioButton);
		
		JComboBox startEpochComboU = new JComboBox(new DepartureTime().getTime());
		startEpochComboU.setBounds(250, 66, 56, 21);
		panel_1.add(startEpochComboU);
		
		JLabel messageLabel = new JLabel("");
		messageLabel.setForeground(Color.RED);
		messageLabel.setBounds(20, 316, 161, 15);
		frmBooksystem.getContentPane().add(messageLabel);
		
		JButton btnLookup = new JButton("查詢");
		btnLookup.setBounds(687, 316, 87, 23);
		
		JPanel bookingHistoryLookUpPanel = new JPanel();
		setUpBookingHistoryLookUpPanel(bookingHistoryLookUpPanel);//call this method to keep this scope not too long
		tabbedPane.addTab("訂位紀錄查詢", null, bookingHistoryLookUpPanel, null);
		
		JPanel bookingCodeLookUpPanel = new JPanel();
		setUpBookingCodeLookUpPanel(bookingCodeLookUpPanel);//call this method to keep this scope not too long
		tabbedPane.addTab("訂位代號查詢", null, bookingCodeLookUpPanel, null);
		
		btnLookup.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(tabbedPane.getSelectedIndex()==0){
					String id = textField.getText();
					Calendar outwardDate = (Calendar) datePickerG.getModel().getValue();
					Calendar returnDate = (Calendar) datePicker_2G.getModel().getValue();
					int seatPrefer = seatPreferCombo.getSelectedIndex();
					int startStation = startStationCombo.getSelectedIndex();
					int endStation = endStationCombo.getSelectedIndex();
					int genNum = genCombo.getSelectedIndex();
					int childNum = childCombo.getSelectedIndex();
					int elderNum = elderComboBox.getSelectedIndex();
					int loveNum = loveCombo.getSelectedIndex();
					int startEpoch = startEpochCombo.getSelectedIndex()*30+360;
					int arrivalEpoch = returnEpochCombo.getSelectedIndex()*30+360;
					int[] ticketNumber = new int[]{genNum,childNum,elderNum,loveNum};
					
					inform = new LookUpInform(id, startStation, endStation, startEpoch, arrivalEpoch,
							outwardDate, returnDate, seatPrefer, ticketNumber,returnEpochCombo.isEnabled());
					messageLabel.setText(inform.isInputValid());
					if(messageLabel.getText()!=""){return; }
					BothFind lookUpman = new BothFind();
					Object outwardData[][],returnData[][];
					lookUpman.getlo(inform.getStartStation(),inform.getEndStation(),outwardDate,inform.getOutwardSartTime(),inform.isCollegeStudent(),true);					
					outwardData = MyUtil.fillData(lookUpman.getArray());
					lookUpman.getlo2(inform.getStartStation(),inform.getEndStation(),returnDate,inform.getReturnSartTime(),inform.isCollegeStudent(),true);
					returnData = MyUtil.fillData(lookUpman.getArray());
					TrainSelectionTabel tableWindow = TrainSelectionTabel.getInstance(outwardData,returnData,inform.willReturn());
					tableWindow.setVisible(true);
				}
				else if(tabbedPane.getSelectedIndex()==1){
					String id = textField_2.getText();
					Calendar outwardDate = (Calendar) datePicker.getModel().getValue();
					Calendar returnDate = (Calendar) datePicker_2.getModel().getValue();
					int seatPrefer = seatComboU.getSelectedIndex();
					int startStation = startComboU.getSelectedIndex();
					int endStation = arrivalCombo.getSelectedIndex();
					int ticketNum = ticketNumCombo.getSelectedIndex();
					int startEpoch = startEpochComboU.getSelectedIndex()*30+360;
					int arrivalEpoch = returnEpochComboU.getSelectedIndex()*30+360;
					
					inform = new LookUpInform(id, startStation, endStation, startEpoch, arrivalEpoch,
							outwardDate, returnDate, seatPrefer, ticketNum,returnEpochComboU.isEnabled());
					messageLabel.setText(inform.isInputValid());
					if(messageLabel.getText()!=""){return; }
					
					BothFind lookUpman = new BothFind();
					Object outwardData[][],returnData[][];
					lookUpman.getlo(inform.getStartStation(),inform.getEndStation(),outwardDate,inform.getOutwardSartTime(),inform.isCollegeStudent(),true);
					outwardData = MyUtil.fillData(lookUpman.getArray());
					lookUpman.getlo2(inform.getStartStation(),inform.getEndStation(),returnDate,inform.getReturnSartTime(),inform.isCollegeStudent(),true);					
					returnData = MyUtil.fillData(lookUpman.getArray());
					TrainSelectionTabel tableWindow = TrainSelectionTabel.getInstance(outwardData,returnData,inform.willReturn());
					tableWindow.setVisible(true);
				}
				else if(tabbedPane.getSelectedIndex() == 2) {
					String id = id_lookup_textField.getText();
					String code = code_looj_up_textField.getText();
					BookingHistory history = BookingHistory.bookingHistoryLookUp(historyArray,id,code);
					if(history == null) {messageLabel.setText("查無紀錄");return ;}
					history.showLookUpOutCome();
				}
				else if(tabbedPane.getSelectedIndex() == 3 ) {
					StationId stationId[] = StationId.values();
					
					String id = id_lookup_textField2.getText(); 
					String startStationName = stationId[startComboLookUp.getSelectedIndex()].toString();
					String endStationName = stationId[endComboLookUp.getSelectedIndex()].toString();
					String trainNo = trainNo_lookup_textField.getText();
					Calendar date = (Calendar) lookUpDatePicker.getModel().getValue();
					//ArrayList<BookingHistory> historyArray,String targetId,String startStation,String endStation,Calendar date,String trainNo
					BookingHistory history = BookingHistory.bookingHistoryLookUp(historyArray,id,startStationName,endStationName,date,trainNo);
					if(history == null) {messageLabel.setText("查無紀錄");return ;}
					history.showLookUpOutCome();
				}
			}
		});
		frmBooksystem.getContentPane().add(btnLookup);
	}
	private JTextField id_lookup_textField;
	private JTextField code_looj_up_textField;
	
	private JDatePicker lookUpDatePicker;
	private JComboBox startComboLookUp;
	private JComboBox endComboLookUp;
	private JTextField id_lookup_textField2;
	private JTextField trainNo_lookup_textField;
	private void setUpBookingHistoryLookUpPanel(JPanel panel) {
		panel.setLayout(null);
		JLabel lblNewLabelG = new JLabel("身分id");
		lblNewLabelG.setBounds(24, 34, 46, 15);
		panel.add(lblNewLabelG);
		
		id_lookup_textField = new JTextField();
		id_lookup_textField.setBounds(90, 31, 96, 21);
		panel.add(id_lookup_textField);
		id_lookup_textField.setColumns(10);
		
		JLabel lblTakeOffG = new JLabel("訂位代碼");
		lblTakeOffG.setBounds(24, 69, 56, 15);
		panel.add(lblTakeOffG);
		
		code_looj_up_textField = new JTextField();
		code_looj_up_textField.setBounds(90, 69, 96, 21);
		panel.add(code_looj_up_textField);
		code_looj_up_textField.setColumns(102);
		
	}
	private void setUpBookingCodeLookUpPanel(JPanel panel) {
		panel.setLayout(null);
		JLabel lblNewLabelG = new JLabel("身分id");
		lblNewLabelG.setBounds(24, 34, 46, 15);
		panel.add(lblNewLabelG);
		
		id_lookup_textField2 = new JTextField();
		id_lookup_textField2.setBounds(90, 31, 96, 21);
		panel.add(id_lookup_textField2);
		id_lookup_textField2.setColumns(10);
		
		JLabel lblTakeOffG = new JLabel("起站");
		lblTakeOffG.setBounds(24, 69, 56, 15);
		panel.add(lblTakeOffG);
		
		startComboLookUp = new JComboBox(new String[]{"請選擇...","南港","台北","板橋","桃園","新竹","苗栗","台中","彰化","雲林","嘉義","台南","左營"});
		startComboLookUp.setToolTipText("tain station id");
		startComboLookUp.setBounds(90, 69, 75, 21);
		panel.add(startComboLookUp);
		
		JLabel lbArrival = new JLabel("到站");
		lbArrival.setBounds(24, 100, 56, 15);
		panel.add(lbArrival);
		
		endComboLookUp = new JComboBox(new String[]{"請選擇...","南港","台北","板橋","桃園","新竹","苗栗","台中","彰化","雲林","嘉義","台南","左營"});
		endComboLookUp.setToolTipText("tain station id");
		endComboLookUp.setBounds(90, 97, 75, 21);
		panel.add(endComboLookUp);
		
		JLabel lblTakeOff_1 = new JLabel("起程時間");
		lblTakeOff_1.setBounds(426, 69, 56, 15);
		panel.add(lblTakeOff_1);
		
        lookUpDatePicker = new JDatePicker(Calendar.getInstance());
        lookUpDatePicker.getButton().addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        lookUpDatePicker.addDateSelectionConstraint(new RangeConstraint(after, before));
        lookUpDatePicker.setBounds(492, 66, 150, 30);
        panel.add(lookUpDatePicker);
		
		JLabel lblStart = new JLabel("車次代號");
		lblStart.setBounds(24, 140, 56, 15);
		panel.add(lblStart);
		
		trainNo_lookup_textField = new JTextField();
		trainNo_lookup_textField.setBounds(90, 140, 96, 21);
		panel.add(trainNo_lookup_textField);
		trainNo_lookup_textField.setColumns(10);
		
		
	}
}
