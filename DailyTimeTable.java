package mainFrame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class DailyTimeTable {
	
    public static void main(String[] args) {

    	Calendar startDate = Calendar.getInstance();
    	ArrayList<String[]> tableRows = DailyTimeTable.generateTable(startDate,0);
    	for(String row[] : tableRows) {
    		for(String ele : row) {
    			System.out.print(ele+" ");
    		}
    		System.out.println();
    	}
    }
    private class DailyTimeTableComparator implements Comparator<String[]>{
		public int compare(String[] o1, String[] o2) {
			String hh_mm1[] = o1[7].split(":"),hh_mm2[] = o2[7].split(":");
			int hh1 = Integer.parseInt(hh_mm1[0]);
			int hh2 = Integer.parseInt(hh_mm2[0]);
			int mm1 = Integer.parseInt(hh_mm1[1]);
			int mm2 = Integer.parseInt(hh_mm2[1]);
			if(hh1 == 0) {hh1 = 24;}
			if(hh2 == 0) {hh2 = 24;}
			if(hh1 > hh2) {
				return 1;
			}else if(hh1 < hh2) {
				return -1;
			}else if(hh1 == hh2) {
				if(mm1 > mm2) {
					return 1;
				}else if(mm1 < mm2) {
					return -1;
				}
			}
			return 0;
		}
    }
    public static ArrayList<String[]> generateTable(Calendar startDate,int direction) {
//    	System.out.println("all is well");
    	String tableHead = "車次 南港站 台北站 板橋站 桃園站 新竹站 苗栗站 台中站 彰化站 雲林站 嘉義站 台南站 左營站";
    	String weekDay = toWeekDayString(Calendar.DAY_OF_WEEK);
    	ArrayList<String[]> tableRows = new ArrayList<String[]>();
    	String[] tableRow = new String[getStationNumber()+1];
    	
    	try {
    		JSONTokener tokener = new JSONTokener(new FileInputStream("timeTable.json"));
    		JSONArray root = new JSONArray(tokener);
    	
    		for(Object rootNode : root) {
    			//top hierarchy
    			JSONObject nodes = (JSONObject) rootNode;
    			//2nd hierarchy
    			JSONObject generalTimetable = nodes.getJSONObject("GeneralTimetable");
    			//3nd hierarchy
    			JSONObject generalTainInfo = generalTimetable.getJSONObject("GeneralTrainInfo");
    			JSONArray stopTimes = generalTimetable.getJSONArray("StopTimes");
    			JSONObject serviceDay = generalTimetable.getJSONObject("ServiceDay");
    			
    			int onService = serviceDay.getInt(weekDay);
    			int trainDirection =  generalTainInfo.getInt("Direction");
    			if(onService == 1 && trainDirection == direction) {
    				tableRow[0] = generalTainInfo.getString("TrainNo");
    				for(int i = 1 ; i < tableRow.length ; i++) {
    					tableRow[i] = "**:**";
    				}
    				for(Object seq : stopTimes) {
    					int index = stationId2tableRowIndex( (String)((JSONObject)seq).get("StationID") );
    					tableRow[index] = ((JSONObject)seq).getString("DepartureTime");
    				}
    				tableRows.add(tableRow.clone());
    			}
    		}
    	
    	}catch(FileNotFoundException e) {
    		e.printStackTrace();
    	}catch(JSONException e) {
    		e.printStackTrace();
    	}
    	Collections.sort(tableRows,new DailyTimeTable().new DailyTimeTableComparator());
    	return tableRows;
    }
    private static int getStationNumber(String filename) {
    	int number = 0;
    	try {
    		JSONTokener tokener = new JSONTokener(new FileInputStream(filename));
    		JSONArray root = new JSONArray(tokener);
    		number = root.length();
    		
    	}catch(FileNotFoundException e) {
    		e.printStackTrace();
    	}
    	return number;
    }
    private static int getStationNumber() {
    	return getStationNumber("station.json");
    }
    private static int stationId2tableRowIndex(String stationId) {
    	switch(stationId) {
    		case "0990":
    			return 1;
    		case "1000":
    			return 2;
    		case "1010":
    			return 3;
    		case "1020":
    			return 4;
    		case "1030":
    			return 5;
    		case "1035":
    			return 6;
    		case "1040":
    			return 7;
    		case "1043":
    			return 8;
    		case "1047":
    			return 9;
    		case "1050":
    			return 10;
    		case "1060":
    			return 11;
    		case "1070":
    			return 12;
    	}
    	return -1;
    }
    private static String toWeekDayString(int dayOfWeek) {
    	switch(dayOfWeek) {
			case Calendar.MONDAY :
				return "Monday";
			case Calendar.TUESDAY :
				return "Tuesday";
			case Calendar.WEDNESDAY :
				return "Wednesday";
			case Calendar.THURSDAY :
				return "Thursday";
			case Calendar.FRIDAY :
				return "Friday";
			case Calendar.SATURDAY :
				return "Saturday";
			case Calendar.SUNDAY :
				return "Sunday";
    	}
    	return "dayOfWeek out of index bound!";
    }

}
