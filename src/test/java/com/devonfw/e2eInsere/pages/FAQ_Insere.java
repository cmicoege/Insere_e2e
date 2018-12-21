package com.devonfw.e2eInsere.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.capgemini.mrchecker.selenium.core.BasePage;
import com.capgemini.mrchecker.test.core.logger.BFLogger;

/**
 * @author cmicoege
 *
 */
public class FAQ_Insere extends BasePage {

  private static final By searchFAQWrapper = By.className("faqs-wrapper");

  private static final By searchHomeTab = By.id("Home");

  @Override
  public boolean isLoaded() {

    WebElement FAQWrapper = getDriver().findElementDynamic(searchFAQWrapper);

    return FAQWrapper.isDisplayed();
  }

  @Override
  public void load() {

    BFLogger.logError("Insere FAQ page was not loaded.");

  }

  @Override
  public String pageTitle() {

    return "";
  }

  public Home_Insere goToHome() {

    WebElement homeTab = getDriver().findElementDynamic(searchHomeTab);
    homeTab.click();
    return new Home_Insere();
  }

}
