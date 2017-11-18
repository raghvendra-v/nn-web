package com.nn.entities.test;

import java.util.HashMap;
import java.util.Map;

public class Environment {
	private static Map<String, String> props;

	private Environment() {
	};

	private static Map<String, String> getInstance() {
		synchronized (Environment.class) {
			if (props == null) {
				synchronized (Environment.class) {
					props = new HashMap<String, String>();
				}
			}
		}
		return props;
	}

	public static String getProperty(String key) {
		return Environment.getInstance().get(key);
	}

	public static void setProperty(String key, String value) {
		Environment.getInstance().put(key, value);
	}

}
