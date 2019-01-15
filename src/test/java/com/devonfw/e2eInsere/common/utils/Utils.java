package com.devonfw.e2eInsere.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.capgemini.mrchecker.selenium.core.BasePage;
import com.capgemini.mrchecker.selenium.core.newDrivers.INewWebDriver;
import com.devonfw.e2eInsere.common.data.SampleData;

/**
 * @author Juan Andrés Ambuludi Olmedo
 * @author Jorge Dacal Cantos
 * @author Carlos Micó Egea
 */
public class Utils {
  public static String getRandomEmail(String name) {

    Random random = new Random();
    int rint = random.nextInt(1000);
    return name + "_" + rint + "_@test.com";
  }

  public static String getTomorrowDate() {

    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_YEAR, 1);
    return new SimpleDateFormat("MM/dd/yyyy hh:mm a").format(calendar.getTime());
  }

  public static int getRandom1toMax(int max) {

    Random random = new Random();
    return random.nextInt(max) + 1;
  }

  public static String changeDateFormat(String oldDate, String oldFormat, String newFormat) throws ParseException {

    SimpleDateFormat dateFormat = new SimpleDateFormat(oldFormat);
    Date date = dateFormat.parse(oldDate);
    dateFormat.applyPattern(newFormat);

    return dateFormat.format(date);
  }

  @Deprecated
  public static void sendKeysWithCheck(String text, By textFieldSearchCriteria, INewWebDriver driver,
      WebDriverWait wait, int index) {

    boolean writtenCorrectly;
    WebElement textField;
    char character;
    System.out.println("Texto: " + text);

    for (int i = 0; i < text.length(); i++) {
      System.out.println("Longitud: " + text.length());
      character = text.charAt(i);
      System.out.println("letra: " + character);
      writtenCorrectly = false;
      while (!writtenCorrectly) {

        textField = driver.findElementDynamics(textFieldSearchCriteria).get(index);
        textField.sendKeys(character + "");
        try {

          int l = i;
          wait.until((WebDriver wd) -> driver.findElementDynamic(textFieldSearchCriteria).getAttribute("value")
              .length() == l + 1);
          writtenCorrectly = true;
        } catch (TimeoutException e) {

          System.out.println("Character not written: " + character);
        }
      }
      System.out.println("Progress: " + text.substring(0, i + 1));
    }

  }

  @Deprecated
  public static void sendKeysWithCheck(String text, By textFieldSearchCriteria, INewWebDriver driver,
      WebDriverWait wait) {

    int index = 0;
    sendKeysWithCheck(text, textFieldSearchCriteria, driver, wait, index);
  }

  public static String dataToString(SampleData credentials) {

    String id = credentials.getSampleAttribute1();
    String name = credentials.getSampleAttribute2();
    String address = credentials.getSampleAttribute3();
    String email = credentials.getSampleAttribute4();
    String dataString = id + " " + name + " " + address + " " + email;

    return dataString;
  }

  @Deprecated
  public static void sendKeysWithCheck(String text, By textFieldSearchCriteria, INewWebDriver driver,
      WebDriverWait wait, int index, String attribute) {

    boolean writtenCorrectly;
    WebElement textField;
    char character;
    System.out.println("Texto: " + text);

    for (int i = 0; i < text.length(); i++) {
      System.out.println("Longitud: " + text.length());
      character = text.charAt(i);
      System.out.println("letra: " + character);
      writtenCorrectly = false;
      while (!writtenCorrectly) {

        textField = driver.findElementDynamics(textFieldSearchCriteria).get(index);
        textField.sendKeys(character + "");
        try {

          int l = i;
          wait.until((WebDriver wd) -> driver.findElementDynamic(textFieldSearchCriteria).getAttribute(attribute)
              .length() == l + 1);
          writtenCorrectly = true;
        } catch (TimeoutException e) {

          System.out.println("Character not written: " + character);
        }
      }
      System.out.println("Progress: " + text.substring(0, i + 1));
    }

  }

  public static void sendKeysToElement(String text, WebElement element, INewWebDriver driver, WebDriverWait wait,
      String attribute) {

    boolean writtenCorrectly;
    WebElement textField;
    char character;
    System.out.println("Texto: " + text);

    for (int i = 0; i < text.length(); i++) {
      System.out.println("Longitud: " + text.length());
      character = text.charAt(i);
      System.out.println("letra: " + character);
      writtenCorrectly = false;
      while (!writtenCorrectly) {

        textField = element;
        textField.sendKeys(character + "");
        try {

          int l = i;
          wait.until((WebDriver wd) -> element.getAttribute(attribute).length() == l + 1);
          writtenCorrectly = true;
        } catch (TimeoutException e) {

          System.out.println("Character not written: " + character);
        }
      }
      System.out.println("Progress: " + text.substring(0, i + 1));
    }

  }

  public static void sendKeysToElement(String text, WebElement element, INewWebDriver driver, WebDriverWait wait) {

    String defaultStoringAttribute = "value";
    sendKeysToElement(text, element, driver, wait, defaultStoringAttribute);
  }

  public static void fixedWait(Double seconds) {

    try {
      Double milisecondsDouble = (seconds * 1000);
      int milisecondsInt = milisecondsDouble.intValue();
      TimeUnit.SECONDS.sleep(milisecondsInt);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public static boolean verifyFooterIsDisplayed(BasePage page) {

    By searchFooter = By.tagName("td-layout-footer");
    boolean footerIsDisplayed = false;

    try {
      WebElement footer = BasePage.getDriver().findElementDynamic(searchFooter);
      footerIsDisplayed = footer.isDisplayed();

    } catch (Exception e) {
      System.out.println(e);
    }
    return footerIsDisplayed;
  }

}
