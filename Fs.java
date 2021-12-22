package mainFrame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Calendar;


public class Fs {
		
	public static void main(String[] argv) {
		StationId id[] = StationId.values();
		System.out.println(id[5].toString().equals("·s¦Ë"));
		Random random = new Random();
		System.out.println(1000000+random.nextInt(8000000));
	}
}
