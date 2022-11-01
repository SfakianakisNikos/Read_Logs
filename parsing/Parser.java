package com.eclipse.logging.parsing;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.eclipse.logging.Log;

public class Parser {

	String filepath;
	private int counter;
	private String[] splitLine;
	private List<String> allClasses;
	private Set<String> availableClasses;
	private HashMap<String, Integer> mapCounter;
	private List<Log> logList;

	public Parser(String filepath) {

		this.filepath = filepath;
		availableClasses = new HashSet<String>();
		mapCounter = new HashMap<String, Integer>();

	}

	public List<Log> parse() throws FileNotFoundException, IOException {
		logList = new ArrayList<>();
		allClasses = new ArrayList<>();

		FileInputStream fin = new FileInputStream(filepath);
		BufferedReader reader = new BufferedReader(new InputStreamReader(fin, StandardCharsets.UTF_8));
		String str = null;

		while (counter < 10000) {
			Log fileLog = new Log();
			str = reader.readLine();

//			Split log line

			fileLog.setDateTime(parseDate(str));
			fileLog.setInfo(parseInfo(str));
			fileLog.setKlash(parseKlash(str));
			fileLog.setThreadId(parseThread(str));
			fileLog.setIp(parseIp(str));
			fileLog.setPort(parsePort(str));
			fileLog.setMessage(parseMessage(str));

//			Add log to list
			addLog(fileLog);

			counter++;
		}

		getAvailableClassesSet();
		fin.close();
		return logList;
	}

	private void addLog(Log newlog) {

		logList.add(newlog);
		allClasses.add(newlog.getKlash());
		availableClasses.add(newlog.getKlash());

		Integer integer = mapCounter.get(newlog.getKlash());
		if (integer == null) {
			mapCounter.put(newlog.getKlash(), 1);
		} else {
			mapCounter.put(newlog.getKlash(), integer.intValue() + 1);
		}

	}

	private Date parseDate(String str) {

		splitLine = str.split(" ", 2);
//		newlog.setDate(splitLine[0]);
		String str1 = splitLine[0];

		splitLine = splitLine[1].split(" ", 2);
//		newlog.setTime(splitLine[0]);
		String str2 = splitLine[0];

//		newlog.setDateTime(str1 + " " + str2);
		return convertDate(str1 + " " + str2);

	}

	private static Date convertDate(String time) {
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,sss");
		try {
			return ft.parse(time);
		} catch (ParseException e) {
			System.out.println("Unparseable using " + ft);
		}
		return null;
	}

	private String parseInfo(String str) {

		splitLine = splitLine[1].split("  ", 2);
		return splitLine[0];
	}

	private String parseKlash(String str) {

		splitLine = splitLine[1].split("\\(", 2);
		int lenght = splitLine[0].length();
		String a = splitLine[0].substring(1, lenght - 2);
		if (lenght == 3) {
			String newString = "Class name is missing";
			return newString;
		} else {
			return a;
		}
	}

	private int parseThread(String str) {


		splitLine = splitLine[1].split("\\[", 2);
		String s = splitLine[0].substring(13);
		int threadId = Integer.parseInt(s);
		return threadId;

	}

	private String parseIp(String str) {

		splitLine = splitLine[1].split(":", 2);
		return splitLine[0];
	}

	private int parsePort(String str) {

		splitLine = splitLine[1].split("\\]", 2);
		int port = Integer.parseInt(splitLine[0]);
		return port;
	}

	private String parseMessage(String str) {

		splitLine = splitLine[1].split(" ", 2);
		return splitLine[1];
	}

	public Set getAvailableClassesSet() {

		return availableClasses;

	}

	public int getCounter(String klash) {

		return mapCounter.get(klash);

	}

}
