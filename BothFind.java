package mainFrame;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class BothFind {
	

	private  ArrayList<String> answer = new ArrayList<String>();
	public String Deptime;
	public String Arrtime;
	public String StudentDisct;
	public String EarlyDisct;
	public int window=298,aisle=298; 

	public  String StartStID ;
	public  String EndstID ;
	public  int Goquerytime;
	
	
	public ArrayList<String> getArray() {
		return this.answer;
		
	}
	
	public void deleteArray() {
		while(this.answer.size()!=0) {
			answer.remove(0);
		}
	}
	
	public void getlo(String StartStID,String EndstID ,Calendar startDateCalendar,int Goquerytime,boolean isStudent,boolean isEarly)  
    {
		Locale.setDefault(new Locale("en"));
		Date startDate = startDateCalendar.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("E");
		//LocalDate localDate = LocalDate.now();//For reference
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E");
		String week = dateFormat.format(startDate);

		String DepNumWeek="";
		
		
		switch(week) {
			case "Mon": DepNumWeek="Monday";
						break;
			case "Tue": DepNumWeek="Tuesday";
						break;
			case "Wed": DepNumWeek="Wednesday";
						break;
			case "Thu": DepNumWeek="Thursday";
						break;
			case "Fri": DepNumWeek="Friday";
						break;
			case "Sat": DepNumWeek="Saturday";
						break;
			case "Sun": DepNumWeek="Sunday";
						break;
	
		}
		int TransferTime=0;
		
		//System.out.println(week);
		//System.out.println(DepNumWeek);
		
		try{
	    	JSONTokener tokener = new JSONTokener(new FileInputStream("timeTable.json"));
	    	
	    	JSONArray root = new JSONArray(tokener);
	    	
	    	
	    	for (Object i : root)
	    		{	
	    			JSONObject io = (JSONObject) i;
	    			JSONObject GeneralTimetable = io.getJSONObject("GeneralTimetable");
	    			JSONObject GeneralTrainInfo = GeneralTimetable.getJSONObject("GeneralTrainInfo");
	    			//int direction =(int) GeneralTrainInfo.get("Direction"); // 0:south , 1:north
	    			//System.out.println("Direction"+":"+direction);
	    			
	    			JSONArray StopTimes = GeneralTimetable.getJSONArray("StopTimes");
	    			

	    			
	    			JSONObject ServiceDay = GeneralTimetable.getJSONObject("ServiceDay");
	    			//System.out.println(ServiceDay.get( DepNumWeek.toString()));
	    			//int service = (int) ServiceDay.get( DepNumWeek.toString()); //is day on service
	    			int service = (int) ServiceDay.get( DepNumWeek);
	    			if(service==0) {
	    				//System.out.println("No service today");
	    				continue;
	    			}
	    			boolean foundStartSt=false;
	    			boolean foundEndtSt=false;
	    			int EndSequence=0,StSequence=0;

	    			for (Object j : StopTimes)
		    			{
	    					JSONObject jo = (JSONObject) j;
	    					Object StationID = jo.get("StationID");
	    					
	    					if(StationID.equals(EndstID)) {
	    						foundEndtSt=true;
	    						EndSequence=(int)jo.get("StopSequence");
	    						Object ArrivedTime = jo.get("DepartureTime");
	    						Arrtime=ArrivedTime.toString();
	    						
	    					}
	    					
	    					if(StationID.equals(StartStID)  ) 
	    					{
	    						foundStartSt=true;
	    						StSequence=(int)jo.get("StopSequence");
	    						
	    						//System.out.println(StationID);
	    						Object DepartureTime = jo.get("DepartureTime");
	    						Deptime=DepartureTime.toString();
	    						//System.out.println(DepartureTime.getClass());
	    						
	    						String[] split_time=DepartureTime.toString().split(":");
	    						TransferTime=Integer.parseInt(split_time[0])*60+Integer.parseInt(split_time[1]);
	    						//System.out.println(Normalfind.DepTime);
	    					}
		    			}

	    			if((EndSequence-StSequence)<0) 
	    			{
	    				
	    				//System.out.println("Wrong direction");
	    				continue;
	    				
	    			}
	    			if(!foundEndtSt) {
	    				//System.out.println("Wrong end station");
	    				continue;
	    			}
	    			if(!foundStartSt) {
	    				
	    				//System.out.println("Wrong start station");
	    				continue;
	    			}
	    			if(Goquerytime>TransferTime) 
	    			{
	    				//System.out.println("Wrong time");
	    			//System.out.println("departuretime:"+TransferTime);
	    				continue;
	    			}
	    			String TrainNo= (String)GeneralTrainInfo.get("TrainNo");
	    			//System.out.println("TrainNo"+TrainNo);
	    			
	    			
	    			if(isStudent || isEarly )
	    			{
	    				try
	    				{
	    			    	JSONTokener tokener1 = new JSONTokener(new FileInputStream("universityDiscount.json"));
	    			    	JSONTokener tokener2 = new JSONTokener(new FileInputStream("earlyDiscount.json"));
	    			    	
	    			    	JSONObject root1 = new JSONObject(tokener1);
	    			    	JSONObject root2 = new JSONObject(tokener2);
	    			    	
	    			    	JSONArray DiscountTrains = root1.getJSONArray("DiscountTrains");
	    			    	JSONArray DiscountTrains2 = root2.getJSONArray("DiscountTrains");
	    			    	
	    			    	for (Object j : DiscountTrains)
	    					{
	    						JSONObject jo = (JSONObject) j;
	    						String tempNo2=(String)jo.get("TrainNo");
	    						if(TrainNo.equals(tempNo2)&&isStudent)
	    						{
	    							JSONObject StudentDiscount=(JSONObject)jo.get("ServiceDayDiscount");
	    							StudentDisct = ""+StudentDiscount.getDouble(DepNumWeek);
	    						}
	    						if(StudentDisct == null) {StudentDisct = "1";}
	    					}
	    			    	
	    			    	for (Object k : DiscountTrains2)
	    					{
	    						JSONObject ko = (JSONObject) k;
	    						String tempNo=(String)ko.get("TrainNo");
	    						//System.out.println(tempNo);
	    						if(TrainNo.equals(tempNo)&&isEarly)
	    						{
	    							JSONObject EarlyDiscount=(JSONObject)ko.get("ServiceDayDiscount");
	    							try {
	    								JSONArray discountArray = EarlyDiscount.getJSONArray(DepNumWeek);
	    								for(Object discountElement : discountArray) {
	    									int discountTicketNumber = ((JSONObject)discountElement).getInt("tickets");
	    									if(discountTicketNumber > 0) {
	    										EarlyDisct = ""+((JSONObject)discountElement).getDouble("discount");
	    										break;
	    									}
	    								}
	    							}catch(JSONException e) {
	    								EarlyDisct = "1"; 
	    							}
//	    							answer.add(tempNo+" Dep:"+Deptime+" Arr:"+Arrtime+" StudentDiscount:"+StudentDisct+" EarlyDiscount:"+EarlyDisct);
	    							answer.add(tempNo+" "+StudentDisct+" "+EarlyDisct+" "+Deptime+" "+Arrtime);
	    							continue;
	    						}
	    					}
	    		
	    			    }catch(Exception e){
	    		    		e.printStackTrace();
	    		    	}
	    				continue;
	    			}
	    			
	    			
	    			//seat
	    			JSONTokener tokener3 = new JSONTokener(new FileInputStream("seat.json"));
	    			JSONObject root3 = new JSONObject(tokener3);
	    			
	    			//end seat
	    			
	    			
	    			//System.out.println("general:"+GeneralTrainInfo.get("TrainNo"));
	    			answer.add((String)GeneralTrainInfo.get("TrainNo")+" Dep:"+Deptime+" Arr:"+Arrtime);
	    		
	    		}

	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
    }
	
	public void getlo2(String StartStID,String EndstID ,Calendar startDateCalendar,int Backquerytime,boolean isStudent,boolean isEarly) {
		getlo(EndstID,StartStID,startDateCalendar,Backquerytime,isStudent,isEarly);
	
	}
	
	public static void main(String[] argv) {
		
		BothFind a =new BothFind();
		BothFind b= new BothFind();
		
		a.getlo("1020","1060",Calendar.getInstance(),600,false,true);
		for(String row : a.getArray()) {
			System.out.println(row);
		}
		b.getlo2("1020","1060",Calendar.getInstance(),720,true,true);
		for(String row : b.getArray()) {
//			System.out.println(row);
		}
	}
	

}
