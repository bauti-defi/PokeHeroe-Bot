package org.bot.core;

import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.bot.util.Bot;
import org.bot.variables.Variables;

public class Core {

	public static void main(final String[] args) {
		initiate();
		makeBots();
		makeThreads();
		startThreads();
	}

	private static void startThreads() {
		System.out.println("Starting threads...");
		for (Thread thread : Variables.getThreads()) {
			thread.start();
		}
		System.out.println("Threads started...");
	}

	private static void makeThreads() {
		System.out.println("Making threads...");
		for (Bot bot : Variables.getBots()) {
			Variables.addThread(new Thread(bot));
		}
		System.out.println("Threads made...");
	}

	private static void makeBots() {
		System.out.println("Making bots...");
		for (int count = 0; count < Variables.getClientCount(); count++) {
			if (count == 0) {
				Variables.addBot(new Bot((count + 1), true));
			} else {
				Variables.addBot(new Bot((count + 1), false));
			}
		}
		System.out.println("Bots made...");
	}

	private static void initiate() {
		System.out.println("**Welcome to El Maestro's Pokeheroes Bot**");
		System.out.println("If you need any help please contact El Maestro through skype");
		System.out.println("El Maestro's skype username: el.maestro989");
		Variables.setUsername(new JOptionPane().showInputDialog("What is your username?"));
		Variables.setPassword(new JOptionPane().showInputDialog("What is your password?"));
		Variables.setClientCount(Integer
				.parseInt(new JOptionPane().showInputDialog("How many clients do you want?")));
		new JOptionPane().showMessageDialog(null, "Please select your chromedrive.exe");
		final JFileChooser fc = new JFileChooser();
		fc.showOpenDialog(null);
		Variables.setDriverPath(fc.getSelectedFile().getPath());
		System.setProperty("webdriver.chrome.driver", Variables.getDriverPath());
		try {
			System.out.println("Terminating un-used processes...");
			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
			System.out.println("Processes terminated...");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}