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
import org.json.JSONObject;
import org.json.JSONTokener;

public class Normalfind {
	

	private  ArrayList<String> answer ;

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
	
	public void getlo(String StartStID,String EndstID ,Calendar startDateCalendar,int Goquerytime,boolean isStudent)  
    {
		Locale.setDefault(new Locale("en"));
		Date startDate = startDateCalendar.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("E");
		answer = new ArrayList<String>();
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
		
		try{
	    	JSONTokener tokener = new JSONTokener(new FileInputStream("D:\\桌面\\106-碩二下\\oop 期末專題\\data\\timeTable.json"));
	    	
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
	    						
	    					}
	    					
	    					if(StationID.equals(StartStID)  ) 
	    					{
	    						foundStartSt=true;
	    						StSequence=(int)jo.get("StopSequence");
	    						
	    						//System.out.println(StationID);
	    						Object DepartureTime = jo.get("DepartureTime");
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
	    			
	    			if(isStudent)
	    			{
	    				try
	    				{
	    			    	JSONTokener tokener1 = new JSONTokener(new FileInputStream("D:\\桌面\\106-碩二下\\oop 期末專題\\data\\universityDiscount.json"));
	    			    	
	    			    	JSONObject root1 = new JSONObject(tokener1);
	    			    	JSONArray DiscountTrains = root1.getJSONArray("DiscountTrains");
	    			    	for (Object j : DiscountTrains)
	    					{
	    						JSONObject jo = (JSONObject) j;
	    						String tempNo=(String)jo.get("TrainNo");
	    						//System.out.println(tempNo);
	    						if(TrainNo.equals(tempNo))
	    						{
	    							JSONObject StudentDiscount=(JSONObject)jo.get("ServiceDayDiscount");
	    							System.out.println("Student:"+tempNo+" discount:"+StudentDiscount.get(DepNumWeek));
	    							answer.add(tempNo);
	    							continue;
	    						}
	    					}
	    		
	    			    }catch(Exception e){
	    		    		e.printStackTrace();
	    		    	}
	    				
	    				//System.out.println("No Student disocunt");
	    				continue;
	    			}
	    			
	    			//System.out.println("general:"+GeneralTrainInfo.get("TrainNo"));
	    			answer.add((String)GeneralTrainInfo.get("TrainNo"));
	    		
	    		}

	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
    }
	
	public void getlo2(String StartStID,String EndstID ,Calendar startDateCalendar,int Backquerytime,boolean isStudent) {
		getlo(EndstID,StartStID,startDateCalendar,Backquerytime,isStudent);
	
	}
	
/*	public static void main(String[] argv) {
		
		Normalfind a =new Normalfind();
		Normalfind b= new Normalfind();
		
		a.getlo("1020","1060",600,false);
	
		System.out.println(a.getArray());
		
		b.getlo2("1020","1060",720,true);
		System.out.println(b.getArray());
		
	}*/
	

}
