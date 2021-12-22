package mainFrame;
import java.util.Calendar;
import java.util.Date;

public class LookUpInform
{	

	public LookUpInform(String id,int startStation,int endStation,int outwardSartTime,int returnSartTime,Calendar outwardtDate,
			Calendar returnDate,int seatPreference,int ticketNumberU,boolean willReturn){
		this.id = id;
		this.startStation = startStation;
		this.endStation = endStation;
		this.outwardSartTime = outwardSartTime;
		this.returnSartTime = returnSartTime;
		this.outwardtDate = outwardtDate;
		this.returnDate = returnDate;
		this.seatPreference = seatPreference;
		this.ticketNumberU = ticketNumberU;
		this.isCollege = true;
		this.willReturn = willReturn;
		init();
	} 
	public LookUpInform(String id,int startStation,int endStation,int outwardSartTime,int returnSartTime,
			Calendar outwardtDate,Calendar returnDate,int seatPreference,int[] ticketNumber,boolean willReturn){
		this.id = id;
		this.startStation = startStation;
		this.endStation = endStation;
		this.outwardSartTime = outwardSartTime;
		this.returnSartTime = returnSartTime;
		this.outwardtDate = outwardtDate;
		this.returnDate = returnDate;
		this.seatPreference = seatPreference;
		this.ticketNumber = ticketNumber;
		this.isCollege = false;
		this.willReturn = willReturn;
		init();
	}
	private void init(){
		Calendar calendar = Calendar.getInstance();
		
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.add(Calendar.DATE, -1);
		this.after = calendar.getTime();
		
		calendar.add(Calendar.MONTH, 1);
		this.before = calendar.getTime();
	}
	

	public static void main(String[] args){
		StationId[] p = StationId.values();
		System.out.println(p[0].toString().equals("南港"));
	}

	public String isInputValid(){
		if(id.equals("")){return "請輸入使用者ID";}
		if(!checkIdformatValid(id)) {return "id格式錯誤";}
		if(outwardtDate.getTime().compareTo(before) >0 || returnDate.getTime().compareTo(before) > 0 ){
			return "只能預定28天內的票";
		}

		if(outwardtDate.getTime().compareTo(after) < 0 ){
			return "錯過的日子 就像DCARD一樣";
		}
		if(outwardtDate.after(returnDate)  && willReturn){return "你必須先出發 才能回來";}
		if(outwardtDate.getTime().compareTo(returnDate.getTime())== 0 && outwardSartTime > returnSartTime && willReturn){return "你必須先出發 才能回來";}
		if(startStation == endStation && startStation*endStation != 0){return "起站迄站相同";}
		if(startStation*endStation == 0){return "請選擇 起站 迄站";}
		if(!isCollege){
			int totalTicketNum = 0;
			for(int c : ticketNumber){
				totalTicketNum += c;
			}
			if(totalTicketNum > 10){return "購買張數超過10張";}
			if(totalTicketNum == 0){return "請選擇購買張數";}
		}
		else {
			if(ticketNumberU > 10){return "同學，購買張數超過10張";}
			if(ticketNumberU == 0){return "請選擇購買張數";}
		}
		return "";
	}
	private boolean checkIdformatValid(String id) {
		if(id.length() != 10) {return false;}
		char []c = id.toCharArray();
		String digit = id.substring(1,id.length());
		if(c[0] > 'z' || c[0] < 'a') {return false;}
		try {
			Integer.parseInt(digit);
		}catch(NumberFormatException e) {return false;}
		return true;
	}

	public String getId(){
		return id;
	}
	public String getStartStation(){
		return stationId[startStation].getValue();
	}
	public String getStartStationName(){
		return stationId[startStation].toString();
	}
	public String getEndStation(){
		return stationId[endStation].getValue();
	}
	public String getEndStationName(){
		return stationId[endStation].toString();
	}
	public int getOutwardSartTime(){
		return outwardSartTime;
	}
	public int getReturnSartTime(){
		return returnSartTime;
	}
	public Calendar getOutwardtDate(){
		return outwardtDate;
	}
	public Calendar getReturnDate(){
		return returnDate;
	}
	public int getSeatPreference(){
		return seatPreference;
	}
	public int getTicketNumberU(){
		return ticketNumberU;
	}
	public int[] getOutwardTicketNumber(){
		return ticketNumber;
	}
	public int getTotalTicketNumber() {
		int totalNumber = 0;
		if(ticketNumber != null) {
			for(int n : ticketNumber) {
				totalNumber += n;
			}
		}
		totalNumber += ticketNumberU ;
		return totalNumber;
	}
	public boolean isCollegeStudent(){
		return isCollege;
	}
	public boolean willReturn(){
		return willReturn;
	}

	public void setId(String input){
		id = input;
	}
	public void setStartStation(int input){
		startStation = input;
	}
	public void setEndStation(int input){
		endStation = input;
	}
	public void setOutwardSartTime(int input){
		outwardSartTime = input;
	}
	public void setReturnSartTime(int input){
		returnSartTime = input;
	}
	public void setOutwardtDate(Calendar input){
		outwardtDate = input;
	}
	public void setReturnDate(Calendar input){
		returnDate = input;
	}
	public void setSeatPreference(int input){
		seatPreference = input;
	}
	public void setOTicketNumberU(int input){
		ticketNumberU = input;
	}
	public void setWillReturn(boolean willReturn){
		this.willReturn = willReturn;
	}

	private String id; 
	private int startStation; 
	private int endStation; 
	private int outwardSartTime; 
	private int returnSartTime; 
	private Calendar outwardtDate; 
	private Calendar returnDate; 
	private int seatPreference; 
	private int ticketNumberU; 
	private int[] ticketNumber/* = new int[4]*/;
	private boolean isCollege = true;
	private Date before,after;
	private boolean willReturn = false;
	private StationId[] stationId = StationId.values();
	
	
}