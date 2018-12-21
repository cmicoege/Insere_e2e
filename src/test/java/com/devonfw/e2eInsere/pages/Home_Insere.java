package com.devonfw.e2eInsere.pages;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import com.capgemini.mrchecker.selenium.core.BasePage;
import com.devonfw.e2eInsere.common.readProperties.ConfigFileReader;

/**
 * @author cmicoege
 *
 */
public class Home_Insere extends BasePage {

  private static final ConfigFileReader configFileReader = new ConfigFileReader();

  private static final String insereUrl = configFileReader.getProperty("insere.url");
  // private static final String insereUrl = "https://i4u-uat-s2portaldev.uat.s2-eu.capgemini.com/";

  // private static final String insereUrl2 = "https://signindev.capgemini.com";
  // private static final String insereUrl = "https://shared-services.pl.s2-eu.capgemini.com";

  private static final String inserePageTitle = configFileReader.getProperty("insere.pagetitle");

  private static final By searchLogoImage = By.className("logo-container");

  private static final By searchCatalogTab = By.className("btn-catalog");

  private static final By searcHomePageSearchBar = By.className("mat-input-element");

  private static final By searchITaaSPortalButton = By.xpath("//*[text() = 'ITaaS Portal']");

  // inserePageTitle

  @Override
  public void load() {

    getDriver().manage().window().maximize();

    // getDriver().navigate().to("https://signin.capgemini.com/opensso/");
    getDriver().navigate().to(insereUrl);
    try {
      Thread.sleep(2000);
    } catch (Exception e) {

    }
  }

  @Override
  public String pageTitle() {

    return inserePageTitle;
  }

  @Override
  public boolean isLoaded() {

    // WebElement logoImage = getDriver().findElementDynamic(searchLogoImage);
    // boolean titleIsCorrect = getDriver().getTitle().equals(pageTitle());
    // boolean homeIsLoaded = (logoImage.isDisplayed()) && (titleIsCorrect);
    // (logoImage.isDisplayed()) &&
    if (getDriver().getTitle().equals(pageTitle())) {
      return true;
    } else {
      return false;
    }
    // return homeIsLoaded;

  }

  public Catalog_Insere goToCatalog() {

    try {
      Thread.sleep(2000);
    } catch (Exception e) {

    }

    WebElement catalogTab = getDriver().findElementDynamic(searchCatalogTab);
    catalogTab.click();
    return new Catalog_Insere();
  }

  public boolean verifyUserIsLogged() {

    boolean userIsLogged;
    try {
      WebElement homePageSearchBar = getDriver().findElementDynamic(searcHomePageSearchBar);
      userIsLogged = homePageSearchBar.isDisplayed();
      return userIsLogged;
    } catch (TimeoutException e) {
      userIsLogged = false;
      return userIsLogged;
    }
  }

  public boolean goTo_ITaaS() {

    WebElement ITaaSPortalButton = getDriver().findElementDynamic(searchITaaSPortalButton);
    ITaaSPortalButton.click();
    // ITaaSPortalButton.sendKeys(Keys.CONTROL + "\t");
    ArrayList tabs = new ArrayList<String>(getDriver().getWindowHandles());
    Class cls = tabs.get(0).getClass();
    System.out.println(cls);
    Class cls2 = ((String) tabs.get(0)).getClass();
    System.out.println(cls);
    // String newTabId = tabs.get(1);
    getDriver().switchTo().window((String) tabs.get(1));
    // I dont understand why is this (String) needed, tabs.get(1) already is String

    String itaasURL = "https://myservices.itaas.capgemini.com/itaas";
    String currentURL = getDriver().getCurrentUrl();
    boolean ITaaS_IsLoaded = currentURL.equals(itaasURL);
    getDriver().close();
    getDriver().switchTo().window((String) tabs.get(0));
    return ITaaS_IsLoaded;
  }

}
