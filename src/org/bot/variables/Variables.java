package org.bot.variables;

import java.util.ArrayList;

import org.bot.util.Bot;

public class Variables {
	private static String driverPath;
	private static String username;
	private static String password;
	private static int clientCount;
	private static ArrayList<Bot> bots;
	private static ArrayList<Thread> threads;
	private static int actions = 0;
	private static boolean randomPresent;

	public static boolean getRandomPresent() {
		return randomPresent;
	}

	public static void setRandomPresent(boolean i) {
		randomPresent = i;
	}

	public static int getActions() {
		return actions;
	}

	public static void setActions(int i) {
		actions = i;
	}

	public static ArrayList<Thread> getThreads() {
		if (threads == null) {
			threads = new ArrayList<Thread>();
		}
		return threads;
	}

	public static void addThread(Thread thread) {
		if (threads == null) {
			threads = new ArrayList<Thread>();
		}
		threads.add(thread);
	}

	public static ArrayList<Bot> getBots() {
		if (bots == null) {
			bots = new ArrayList<Bot>();
		}
		return bots;
	}

	public static void addBot(Bot bot) {
		if (bots == null) {
			bots = new ArrayList<Bot>();
		}
		bots.add(bot);
	}

	public static int getClientCount() {
		return clientCount;
	}

	public static void setClientCount(int count) {
		clientCount = count;
	}

	public static String getDriverPath() {
		return driverPath;
	}

	public static String getPassword() {
		return password;
	}

	public static String getUsername() {
		return username;
	}

	public static void setDriverPath(final String d) {
		driverPath = d;
	}

	public static void setPassword(final String i) {
		password = i;
	}

	public static void setUsername(final String i) {
		username = i;
	}

}
