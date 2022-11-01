package com.eclipse.logging;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

	private String date;
	private String time;

	private Date dateTime;

	private String info;
	private String klash;
	private int threadId;
	private String ip;
	private int port;
	private String message;

	public Log(String date, String time, String info, String klash, int threadId, String ip, String Message) {
		setDate(date);
		setTime(time);
		setInfo(info);
		setKlash(klash);
		setThreadId(threadId);
		setIp(ip);
		setMessage(Message);

	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(String time) {

		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,sss");
		try {
			dateTime = ft.parse(time);
		} catch (ParseException e) {
			System.out.println("Unparseable using " + ft);
		}

	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public Log() {

	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {

		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getKlash() {
		return klash;
	}

	public void setKlash(String klash) {
		this.klash = klash;
	}

	public int getThreadId() {
		return threadId;
	}

	public void setThreadId(int threadId) {

		this.threadId = threadId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
