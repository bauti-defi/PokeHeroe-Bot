package org.bot.util;

import org.bot.variables.Constants;
import org.bot.variables.Variables;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Bot implements Runnable {
	private WebDriver driver;
	private boolean admin;
	private int id;
	private boolean started;

	public Bot(int id, boolean admin) {
		driver = new ChromeDriver();
		// driver.manage().window().setPosition(new Point(-2000, 0));
		this.id = id;
		this.admin = admin;
		print("Created...");
	}

	public void run() {
		if (!started) {
			print("Started...");
			started = true;
		}
		if (driver != null) {
			// Go to login page
			driver.get(Constants.LOGIN_PAGE_URL);
			logIn();
			navigateToGame();
			// play game
			playGame();
		}
	}

	private void playGame() {
		while (driver.getTitle().toLowerCase().startsWith("pokemon")) {
			WebElement element = null;
			if (elementIsPresent(Constants.FIRST_RANDOM_BUTTON_XPATH)) {
				print("Random detected...");
				if (!admin) {
					print("Waiting for admin to solve random...");
					if (!Variables.getRandomPresent()) {
						Variables.setRandomPresent(true);
					}
					break;
				}
				print("Solving random...");
				element = driver.findElement(By.xpath(Constants.FIRST_RANDOM_BUTTON_XPATH));
				print("Clicking: First random pokemon picture...");
				element.click();
				refresh();
				Variables.setRandomPresent(false);
				print("Random solved...");
			} else if (elementIsPresent(Constants.WARM_EGG_BUTTON_XPATH)) {
				element = driver.findElement(By.xpath(Constants.WARM_EGG_BUTTON_XPATH));
				print("Clicking: Warm Egg...");
				element.click();
				Variables.setActions(Variables.getActions() + 1);
				refresh();
				if (admin) {
					print("Actions performed: " + Variables.getActions());
				}
			} else if (elementIsPresent(Constants.TRAIN_BUTON_XPATH)) {
				element = driver.findElement(By.xpath(Constants.TRAIN_BUTON_XPATH));
				print("Clicking: Train...");
				element.click();
				Variables.setActions(Variables.getActions() + 1);
				refresh();
				if (admin) {
					print("Actions performed: " + Variables.getActions());
				}
			}
		}
		while (Variables.getRandomPresent() && !admin) {
			try {
				sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		refresh();
		playGame();
	}

	private void logIn() {
		print("Logging in...");
		print("Username: " + Variables.getUsername() + " || Password: "
				+ Variables.getPassword().replaceAll("(?s).", "*"));
		fillTextField(driver.findElement(By.name(Constants.LOGIN_USERNAME_FIELD)),
				Variables.getUsername(), false);
		fillTextField(driver.findElement(By.name(Constants.LOGIN_PASSWORD_FIELD)),
				Variables.getPassword(), true);
		(new WebDriverWait(driver, 15)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(final WebDriver d) {
				return !d.getTitle().toLowerCase().startsWith("log in");
			}
		});
	}

	private void navigateToGame() {
		print("Navigating to game...");
		if (driver.getTitle().toLowerCase().startsWith("index")) {
			driver.get("http://pokeheroes.com/clicklist");
		}
		(new WebDriverWait(driver, 15)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(final WebDriver d) {
				return !d.getTitle().toLowerCase().startsWith("index");
			}
		});
		if (driver.getTitle().toLowerCase().startsWith("your")) {
			final WebElement element = driver
					.findElement(By.xpath("//*[@id=\"blue_table\"]/tbody/tr[7]/td[2]/a"));
			element.click();
		}
		(new WebDriverWait(driver, 15)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(final WebDriver d) {
				return d.getTitle().toLowerCase().startsWith("pokemon");
			}
		});
	}

	private void fillTextField(final WebElement e, final String text, final boolean submit) {
		e.click();
		e.sendKeys(text);
		if (submit) {
			e.submit();
		}
	}

	private boolean elementIsPresent(final String xpath) {
		if (!driver.findElements(By.xpath(xpath)).isEmpty()) {
			return driver.findElement(By.xpath(xpath)).isDisplayed()
					&& driver.findElement(By.xpath(xpath)).isEnabled();
		}
		return false;
	}

	public void refresh() {
		print("Refreshing page...");
		driver.navigate().refresh();
	}

	private synchronized void print(String message) {
		System.out.println("[Bot Client #" + id + "]" + (admin ? "[ADMIN]" : "") + message);
	}

	private void sleep(int milliSeconds) throws InterruptedException {
		Thread.sleep((long) milliSeconds);
	}

}
