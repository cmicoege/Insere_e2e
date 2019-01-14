package com.devonfw.e2eInsere.pages;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.capgemini.mrchecker.selenium.core.BasePage;
import com.capgemini.mrchecker.test.core.logger.BFLogger;
import com.devonfw.e2eInsere.common.readProperties.ConfigFileReader;
import com.devonfw.e2eInsere.common.utils.Utils;

/**
 * @author cmicoege
 *
 */
public class RequestService_Insere extends BasePage {

  private static final By searchRequestServiceDialog = By.tagName("mat-dialog-container");

  private static final By searchEngagementSelector = By.name("engagementCtrl");

  private static final By searchSelectorPanel = By.className("ng-trigger-transformPanel");

  private static final By searchPrioritySelectorOptions = By.tagName("mat-option");

  private static final By searchEngagementSelectorOptions = By.className("mat-option-text");

  private static final By searchUserField = By.cssSelector("public-ldap-user[placeholder='RequestedFor']");

  private static final By searchUserInput = By.tagName("input");

  private static final By searchTitleInput = By.name("titleCtrl");

  private static final By searchPrioritySelector = By.name("priorityCtrl");

  private static final By searchDescriptionInput = By.name("descriptionCtrl");

  private static final By searchNextButton = By.cssSelector("button[type='submit']");

  private static final By searchCancelButton = By.xpath("../../button");

  private static final By searchConfirmCancelationDialog = By.tagName("public-yes-no-dialog");

  private static final By searchConfirmCancelationOptions = By.tagName("mat-dialog-actions");

  private static final By searchConfirmCancelationButtons = By.tagName("button");

  private static final By searchConfirmRequestDialog = By.tagName("public-show-request-info");

  private static final By searchConfirmRequestButton = By.className("mat-flat-button");

  private static final By searchBackToRequestInfoButton = By.cssSelector("button[type=button]");

  private static final By searchHappyIcon = By.xpath("//*[text()[contains(., 'sentiment_satisfied_alt')]]");

  private static final By searchConfirmationScreen = By.tagName("public-confirmation-screen");

  @Override
  public String pageTitle() {

    return "";
  }

  @Override
  public boolean isLoaded() {

    WebElement requestServiceDialog = getDriver().findElementDynamic(searchRequestServiceDialog);

    return requestServiceDialog.isDisplayed();

  }

  @Override
  public void load() {

    BFLogger.logError("Insere Request Service page was not loaded.");

  }
  /*
   * public void fillFields() {
   *
   * WebElement engagementSelector = getDriver().findElementDynamic(searchEngagementSelector);
   * engagementSelector.click(); WebElement engagementSelectorPanel =
   * getDriver().findElementDynamic(searchEngagementSelectorPanel); List<WebElement> engagementSelectorOptions =
   * engagementSelectorPanel.findElements(searchEngagementSelectorOptions); engagementSelectorOptions.get(0).click();
   * try { Thread.sleep(5000); } catch (Exception e) {
   *
   * } engagementSelector = getDriver().findElementDynamic(searchEngagementSelector); try { Thread.sleep(5000); } catch
   * (Exception e) {
   *
   * } }
   */

  public void fillEngagement() {

    ConfigFileReader configFileReader = new ConfigFileReader();
    String engagement = configFileReader.getProperty("request.engagement");
    WebElement engagementSelector = getDriver().findElementDynamic(searchEngagementSelector);
    Utils.fixedWait(0.2);
    engagementSelector.click();
    Utils.fixedWait(0.2);
    WebElement engagementSelectorPanel = getDriver().findElementDynamic(searchSelectorPanel);
    Utils.fixedWait(0.2);
    List<WebElement> engagementSelectorOptions = engagementSelectorPanel.findElements(searchEngagementSelectorOptions); // searchPrioritySelectorOptions
    // engagementSelectorOptions.get(0).click();
    // System.out.println(engagementSelectorOptions.size());
    for (WebElement option : engagementSelectorOptions) {
      String optionText = option.getText();
      if (optionText.contains(engagement)) {
        option.click();
      }
    }
  }

  public void fillUser() {

    ConfigFileReader configFileReader = new ConfigFileReader();
    String user = configFileReader.getProperty("request.user");
    WebElement userField = getDriver().findElementDynamic(searchUserField);
    WebElement userInput = userField.findElement(searchUserInput);
    userInput.sendKeys(user);

  }

  public void fillTitle() {

    ConfigFileReader configFileReader = new ConfigFileReader();
    String title = configFileReader.getProperty("request.title");
    WebElement titleInput = getDriver().findElementDynamic(searchTitleInput);
    titleInput.sendKeys(title);
  }

  public void fillPriority() {

    ConfigFileReader configFileReader = new ConfigFileReader();
    String priority = configFileReader.getProperty("request.priority");

    WebElement prioritySelector = getDriver().findElementDynamic(searchPrioritySelector);
    prioritySelector.click();
    WebElement prioritySelectorPanel = getDriver().findElementDynamic(searchSelectorPanel);
    List<WebElement> prioritySelectorOptions = prioritySelectorPanel.findElements(searchPrioritySelectorOptions);

    for (WebElement option : prioritySelectorOptions) {
      String optionText = option.getText();
      if (optionText.contains(priority)) {
        option.click();
      }
    }

  }

  public void fillDescription() {

    ConfigFileReader configFileReader = new ConfigFileReader();
    String description = configFileReader.getProperty("request.description");
    WebElement descriptionInput = getDriver().findElementDynamic(searchDescriptionInput);
    descriptionInput.sendKeys(description);
  }

  public void clearUser() {

    WebElement userField = getDriver().findElementDynamic(searchUserField);
    WebElement userInput = userField.findElement(searchUserInput);
    userInput.clear();
    userInput.sendKeys(" ");
    userInput.sendKeys(Keys.BACK_SPACE);

  }

  public void clearTitle() {

    WebElement titleInput = getDriver().findElementDynamic(searchTitleInput);
    titleInput.clear();
    titleInput.sendKeys(" ");
    titleInput.sendKeys(Keys.BACK_SPACE);
  }

  public void fillFields() {

    fillEngagement();
    clearUser();
    fillUser();
    fillPriority();
    fillTitle();
    fillDescription();

  }

  public void fillFieldsCheckingButton() {

    fillDescription();

    clearUser();
    fillUser();
    fillTitle();
    fillPriority();
    // System.out.println("THIS ONE IS GRAY:");
    String errorMessageEngagement = "After all fields but Engagement were filled, Next button is enabled but it should be disabled.";
    Assert.assertFalse(errorMessageEngagement, checkNextButton()); // check button -> gray
    checkNextButton();
    fillEngagement();

    clearUser();
    // System.out.println("THIS ONE IS GRAY:");
    String errorMessageUser = "After all fields but User were filled, Next button is enabled but it should be disabled.";
    // Assert.assertFalse(errorMessageUser, checkNextButton());// check button -> gray
    fillUser();

    clearTitle();
    // System.out.println("THIS ONE IS GRAY:");
    String errorMessageTitle = "After all fields but Title were filled, Next button is enabled but it should be disabled.";
    Assert.assertFalse(errorMessageTitle, checkNextButton()); // check button -> gray
    fillTitle();
    // System.out.println("THIS ONE IS NOT GRAY:");
    String errorMessageFilled = "After all fields were filled, Next button is disabled but it should be enabled.";
    Assert.assertTrue(errorMessageFilled, checkNextButton()); // check button -> not gray

    clearUser();
    // System.out.println("THIS ONE IS GRAY:");
    String errorMessageOneCleared = "After filling all fields and clearing one, Next button is enabled but it should be disabled.";
    // Assert.assertFalse(errorMessageOneCleared, checkNextButton());// check button -> gray
    fillUser();
    // System.out.println("THIS ONE IS NOT GRAY:");

    Assert.assertTrue(errorMessageFilled, checkNextButton());// check button -> not gray
  }

  public boolean checkNextButton() {

    boolean buttonIsEnabled = false;
    WebElement nextButton = getDriver().findElementDynamic(searchNextButton);
    String buttonDisabledProperty = nextButton.getAttribute("disabled");

    if (buttonDisabledProperty != null && buttonDisabledProperty.equals("true")) {
      buttonIsEnabled = false;
    } else {
      buttonIsEnabled = true;
    }

    return buttonIsEnabled;
  }

  public void cancelRequest() {

    WebElement nextButton = getDriver().findElementDynamic(searchNextButton);
    WebElement cancelButton = nextButton.findElement(searchCancelButton);
    cancelButton.click();
    WebElement confirmCancelationDialog = getDriver().findElementDynamic(searchConfirmCancelationDialog);
    WebElement confirmCancelationOptions = confirmCancelationDialog.findElement(searchConfirmCancelationOptions);
    List<WebElement> confirmCancelationButtons = confirmCancelationOptions
        .findElements(searchConfirmCancelationButtons);
    WebElement yesButton = confirmCancelationButtons.get(1);
    yesButton.click();
  }

  public void confirmRequest() {

    Utils.fixedWait(0.5);
    WebElement confirmRequestServiceDialog = getDriver().findElementDynamic(searchConfirmRequestDialog);
    WebElement confirmRequestButton = confirmRequestServiceDialog.findElement(searchConfirmRequestButton);
    confirmRequestButton.click();

  }

  public void backToRequestInfo() {

    Utils.fixedWait(0.5);
    WebElement confirmRequestServiceDialog = getDriver().findElementDynamic(searchConfirmRequestDialog);
    WebElement backToRequestInfoButton = confirmRequestServiceDialog.findElement(searchBackToRequestInfoButton);
    JavascriptExecutor js;
    js = (JavascriptExecutor) getDriver();
    js.executeScript("arguments[0].click()", backToRequestInfoButton);
  }

  public void clickNextButton() {

    Utils.fixedWait(0.5);
    WebElement nextButton = getDriver().findElementDynamic(searchNextButton);
    if (checkNextButton()) {
      nextButton.click();
    }

  }

  public boolean verifyRequestWasSent() {

    WebElement confirmationScreen = getDriver().findElementDynamic(searchConfirmationScreen);
    WebElement happyIcon = confirmationScreen.findElement(searchHappyIcon);

    String confirmationMessage = "Thanks for your request";
    String requestExpectedStatus = "Status: Open";
    String confirmationText = confirmationScreen.getText();

    WebElement thing = getDriver().findElementDynamic(By.tagName("public-confirmation-screen"));

    System.out.println(thing.getText());
    boolean requestWasSent = false;
    if (happyIcon.isDisplayed() && confirmationText.contains(confirmationMessage)
        && confirmationText.contains(requestExpectedStatus)) {
      requestWasSent = true;
    }
    return requestWasSent;
  }
  //
  // public void fillNonMandatoryFields() {
  //
  // }
  //
  // public void fillMandatoryFields() {
  //
  // }
  //
  // public void deleteSomeFields() {
  //
  // }
  //
  // public void checkIfButtonIsClickable() {
  //
  // }
  //
  // public void cancelServiceRequest() {
  //
  // }
  //
  // public void confirmServiceRequest() {
  //
  // }
  //
}
