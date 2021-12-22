package mainFrame;

import java.util.ArrayList;
import java.util.Calendar;

public class SeatCapacity {
	public static void main(String argv[]) {
		SeatCapacity capacity = new SeatCapacity();
		String seat = capacity.getSeat("77995",Calendar.getInstance(),11,2);
		System.out.println(seat);
	}
	private ArrayList<SeatOcupiedLog> logs = new  ArrayList<SeatOcupiedLog>();
	public String getSeat(String trainNo,Calendar date,int ticketNumber,int seatPreference) {
		String []returnVal = new String[ticketNumber];
		SeatOcupiedLog seatOcupiedLog = getExistedLog(trainNo,date);
		if(seatOcupiedLog == null) {
			seatOcupiedLog = new SeatOcupiedLog(trainNo,date);
			logs.add(seatOcupiedLog);
		}
		for(int i = 0 ,j = 0; i < ticketNumber ; ) {
			for(int k = 0 ; k != seatOcupiedLog.seatLayout.get(j).size() ; k++) {
				String ss[] = seatOcupiedLog.seatLayout.get(j).get(k); 
				for(int m = 0 ; m < ss.length ; m++) {
					if(i == ticketNumber) {break;}
					if(seatPreference == 0) {// no preference 
						if(!ss[m].equals("X")) {
							returnVal[i] = "Car"+(j+1)+" "+(k+1)+ss[m];
							ss[m] = "X";
							i++;
						}
					}
					if(seatPreference == 1) {// window
						if( (!ss[m].equals("X")) && (ss[m].equals("A") ||ss[m].equals("E") )) {
							returnVal[i] = "Car"+(j+1)+" "+(k+1)+ss[m];
							ss[m] = "X";
							i++;
						}
					}
					if(seatPreference == 2) {// aisle
						if( (!ss[m].equals("X")) && (ss[m].equals("C") ||ss[m].equals("D") )) {
							returnVal[i] = "Car"+(j+1)+" "+(k+1)+ss[m];
							ss[m] = "X";
							i++;
						}
					}
				}
				
			}
		}
		String returnString = "";
		for(String s : returnVal) {
			returnString += s+" ";
		}
		return returnString;
	}
	public SeatOcupiedLog getExistedLog(String trainNo,Calendar date) {
		if(this.logs == null) {return null;}
		for(SeatOcupiedLog e : logs) {
			if(e.getTrainNo().equals(trainNo) && e.getCalendar().equals(date)) {
				return e;
			}
		}
		return null;
	}
}
class SeatOcupiedLog{
	
	private String trainNo;
	private Calendar date;
	ArrayList<ArrayList<String[]>> seatLayout;
	public SeatOcupiedLog(String trainNo,Calendar date){
		this.trainNo = trainNo;
		this.date = date;
		seatLayout = MyUtil.getSeatLayout();
	}
	public String getTrainNo() {
		return this.trainNo;
	}
	public Calendar getCalendar() {
		return this.date;
	}
	public ArrayList<ArrayList<String[]>> getSeatLayout(){
		return this.seatLayout;
	}
	
}