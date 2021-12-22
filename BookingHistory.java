package mainFrame;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;

public class BookingHistory {
	public String id;
	public String code;
	public Calendar date;
	public String trainNo;
	public String startStation;
	public String endStation;
	public BookingHistory(String id,String code,Calendar date,String trainNo,String startStation,String endStation) {
		this.id = id;
		this.code = code;
		this.date = date;
		this.trainNo = trainNo;
		this.startStation = startStation;
		this.endStation = endStation;
	}
	public void showLookUpOutCome() {
		SimpleDateFormat formater = new SimpleDateFormat("YMMdd");
		String dateStr = formater.format(date.getTime());
		JOptionPane.showMessageDialog(TrainSelectionTabel.instance,"查詢結果\r\n"+"訂位代號 : "+code+"\r\n"
				+"起站 : "+startStation+"\r\n"
				+"到站 : "+endStation+"\r\n"
				+"車次 : "+trainNo+"\r\n"
				+"日期 : "+dateStr,
				"查到了", JOptionPane.INFORMATION_MESSAGE);
	}
	public static BookingHistory bookingHistoryLookUp(ArrayList<BookingHistory> historyArray,String targetId,String targetCode) {
		if(historyArray == null) {return null;}
		for(BookingHistory e : historyArray) {
			if( (e.id.equals(targetId)) && (e.code.equals(targetCode)) ) {
				return e;
			} 
		}
		return null;
	}
	public static BookingHistory bookingHistoryLookUp(ArrayList<BookingHistory> historyArray,
			String targetId,String startStation,String endStation,Calendar date,String trainNo) {
		if(historyArray == null) {return null;}
		for(BookingHistory e : historyArray) {
			if( (e.id == targetId) && (e.startStation == startStation) && (e.startStation == startStation)
					&& (e.date == date) && (e.trainNo == trainNo)) {
				return e;
			} 
		}
		return null;
	}
}
