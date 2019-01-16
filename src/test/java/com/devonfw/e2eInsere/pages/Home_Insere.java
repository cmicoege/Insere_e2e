package com.devonfw.e2eInsere.pages;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import com.capgemini.mrchecker.selenium.core.BasePage;
import com.devonfw.e2eInsere.common.readProperties.ConfigFileReader;
import com.devonfw.e2eInsere.common.utils.Utils;

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

  private static final By searchUserInput = By.id("IDToken1");
  private static final By searchPassInput = By.id("IDToken2");
  private static final By searchLogInButton = By.className("Btn1Def");


  //private static final By searchLogoImage = By.className("logo-container");

  //private static final By searchCatalogTab = By.className("catalog-button");

  //private static final By searcHomePageSearchBar = By.className("mat-input-element");

  private static final By searchITaaSPortalButton = By.xpath("//*[text() = 'ITaaS Portal']");

  // private static final By searchActiveTab = By.className("btn-link-active");

  private static final By searchSupportCard = By.tagName("public-app-support");

  private static final By searchIncidentCard = By.tagName("public-app-incident");

  private static final By searchCatalogContainer = By.className("catalog-container");

  private static final By searchCatalogButton = By.tagName("button");
  // inserePageTitle

  @Override
  public void load() {

    //getDriver().manage().window().maximize();

    // getDriver().navigate().to("https://signin.capgemini.com/opensso/");
    getDriver().navigate().to(insereUrl);
    WebElement body = getDriver().findElement(By.tagName("body"));
    System.out.println(body.getText());
    Utils.fixedWait(2.0);
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


    Utils.fixedWait(2.0);
    // WebElement catalogTab = getDriver().findElementDynamic(searchCatalogTab);
    WebElement catalogContainer = getDriver().findElementDynamic(searchCatalogContainer);
    WebElement catalogButton = catalogContainer.findElement(searchCatalogButton);

    catalogButton.click();
    return new Catalog_Insere();
  }

  public void attemptToLogin() {
	  WebElement userInput = getDriver().findElementDynamic(searchUserInput);
	  WebElement passInput = getDriver().findElementDynamic(searchPassInput);
	  WebElement logInButton = getDriver().findElementDynamic(searchLogInButton);

	  userInput.sendKeys("cmicoege");
	  passInput.sendKeys("Maribel11");

	  logInButton.click();

  }


  public boolean verifyUserIsLogged() {

    boolean userIsLogged;
    try {
      // WebElement homePageSearchBar = getDriver().findElementDynamic(searcHomePageSearchBar);
      // WebElement activeTab = getDriver().findElementDynamic(searchActiveTab);
      // String activeTabName = activeTab.getText();
      // System.out.println(activeTabName);

      String dashboardTabName = "MY DASHBOARD";
      System.out.println(dashboardTabName);

      WebElement supportCard = getDriver().findElementDynamic(searchSupportCard);
      WebElement incidentCard = getDriver().findElementDynamic(searchIncidentCard);
      boolean cardsAreDisplayed = supportCard.isDisplayed() && incidentCard.isDisplayed();
      System.out.println(cardsAreDisplayed);
      // System.out.println(activeTabName.contains(dashboardTabName));
      userIsLogged = cardsAreDisplayed; // activeTabName.contains(dashboardTabName) &&
      System.out.println(userIsLogged);

    } catch (TimeoutException e) {
      userIsLogged = false;
    }

    return userIsLogged;
  }

  public boolean goTo_ITaaS() {

    WebElement ITaaSPortalButton = getDriver().findElementDynamic(searchITaaSPortalButton);
    ITaaSPortalButton.click();
    // ITaaSPortalButton.sendKeys(Keys.CONTROL + "\t");
    ArrayList<String> tabs = new ArrayList<String>(getDriver().getWindowHandles());
    //Class cls = tabs.get(0).getClass();
    //System.out.println(cls);
    //Class cls2 = ((String) tabs.get(0)).getClass();
    //System.out.println(cls);
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
