package com.devonfw.e2eInsere.tests;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import com.capgemini.mrchecker.test.core.BaseTest;
import com.devonfw.e2eInsere.common.readProperties.ConfigFileReader;
import com.devonfw.e2eInsere.common.utils.Utils;
import com.devonfw.e2eInsere.pages.Catalog_Insere;
import com.devonfw.e2eInsere.pages.FAQ_Insere;
import com.devonfw.e2eInsere.pages.Home_Insere;
import com.devonfw.e2eInsere.pages.RequestService_Insere;

import junitparams.JUnitParamsRunner;

/**
 * @author cmicoege
 *
 */
@RunWith(JUnitParamsRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Test_Insere extends BaseTest {

  // private Devon4ngLoginPage myLoginPage = new Devon4ngLoginPage();
  private Home_Insere homePage = new Home_Insere();

  // private static final ConfigFileReader configFileReader = new ConfigFileReader();

  // private static final String insereUrl = configFileReader.getProperty("insere.url");

  @Override
  public void setUp() {

    this.homePage.load();

  }

  @Override
  public void tearDown() {

    try {
      Thread.sleep(3000);
    } catch (Exception e) {

    }
    // TASK Auto-generated method stub
  }

  @Test
  public void Test1_login() {

    boolean userIsLogged = this.homePage.verifyUserIsLogged();
    String errorMessage = "User isn't logged.";
    Assert.assertTrue(errorMessage, userIsLogged);
  }

  @Test
  public void Test2_SearchServiceInTheCatalog() {

    ConfigFileReader configFileReader = new ConfigFileReader();
    // String insereUrl = configFileReader.getProperty("insere.url");
    // String inserePageTitle = configFileReader.getProperty("insere.pagetitle");
    String serviceInfo = configFileReader.getProperty("insere.serviceInfo");
    String falseServiceInfo = configFileReader.getProperty("insere.FalseServiceInfo");
    String selectedOptionName;
    String serviceTitle;

    Catalog_Insere catalogPage = this.homePage.goToCatalog();
    catalogPage.findService(serviceInfo);
    selectedOptionName = catalogPage.selectService();
    serviceTitle = catalogPage.get_ServiceTitle();

    String errorMessage1 = "The title of the service doesn't match the option name.";
    Assert.assertEquals(errorMessage1, selectedOptionName, serviceTitle);

    boolean containedInTitleOrDescription = catalogPage.serviceInfoIsContainedInDescriptionOrTitle(serviceInfo);
    String errorMessage2 = "The information provided isn't in the service found, it probably wasn't searched correctly.";
    Assert.assertTrue(errorMessage2, containedInTitleOrDescription);

    boolean serviceShowedInMenu = catalogPage.checkThatSelectedServiceShowsInTheMenu(selectedOptionName);
    String errorMessage3 = "The service name selected doesn't show in the menu. Maybe the catalog tree didn't update successfully";
    Assert.assertTrue(errorMessage3, serviceShowedInMenu);

    catalogPage.findService(falseServiceInfo);
    boolean noMatchesAreFound = catalogPage.verifyNoMatchesAreFound();
    Assert.assertTrue(noMatchesAreFound);
  }

  @Test
  public void Test3_requestClarityProvisioning() {

    ConfigFileReader configFileReader = new ConfigFileReader();
    // String insereUrl = configFileReader.getProperty("insere.url");
    // String inserePageTitle = configFileReader.getProperty("insere.pagetitle");
    String selectedOptionName;
    String serviceTitle;

    Catalog_Insere catalogPage = this.homePage.goToCatalog();
    catalogPage.goToClarityProvisioning();
    RequestService_Insere requestPage = catalogPage.openClarityProvisioningRequest();
    requestPage.fillFieldsCheckingButton();
    requestPage.cancelRequest();// cancel request
    requestPage = catalogPage.openClarityProvisioningRequest();
    requestPage.fillFields();

    boolean buttonIsEnabled = requestPage.checkNextButton();
    String errorMessage = "After filling all the fields, the button is disabled and it should be enabled.";
    Assert.assertTrue(errorMessage, buttonIsEnabled);
    requestPage.clickNextButton();
    requestPage.backToRequestInfo();

    requestPage.clickNextButton();
    requestPage.confirmRequest();

    boolean requestWasSent = requestPage.verifyRequestWasSent();

    String errorMessage2 = "After successfully sending the service request, the confirmation screen wasn't shown.";
    Assert.assertTrue(errorMessage2, requestWasSent);
  }

  @Test
  public void Test4_footerAndLinks() {

    boolean footerIsDisplayed = Utils.verifyFooterIsDisplayed(this.homePage);
    String errorMessage1 = "The footer isn't displayed in the home page.";
    Assert.assertTrue(errorMessage1, footerIsDisplayed);

    Catalog_Insere catalogPage = this.homePage.goToCatalog();
    footerIsDisplayed = Utils.verifyFooterIsDisplayed(catalogPage);
    String errorMessage2 = "The footer isn't displayed in the home page.";

    FAQ_Insere faqPage = catalogPage.goToFAQ();
    Utils.fixedWait(0.3);
    this.homePage = faqPage.goToHome();

    boolean ITaaS_IsLoaded = this.homePage.goTo_ITaaS();
    Assert.assertTrue(ITaaS_IsLoaded);

  }

}