package com.eric.jerseyrest.db;

import java.util.HashMap;

import com.eric.jerseyrest.model.Message;

public class DataSource {
	private HashMap<Integer, Message> map = new HashMap<Integer, Message>();;

	private static DataSource instance = null;

	private DataSource() {
		System.out.println("preparing datasource......");
		Message msg1 = new Message(1, "Eric");
		Message msg2 = new Message(3, "Jacboson");
		map.put(1, msg1);
		map.put(3, msg2);
	}

	public HashMap<Integer, Message> getMap() {
		return map;
	}

	public static DataSource getInstance() {
		if (instance == null) {
			synchronized (DataSource.class) {
				if (instance == null) {
					instance = new DataSource();
				}
			}
		}
		return instance;
	}
}
