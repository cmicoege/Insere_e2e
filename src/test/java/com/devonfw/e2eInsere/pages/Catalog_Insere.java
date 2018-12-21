package com.devonfw.e2eInsere.pages;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.capgemini.mrchecker.selenium.core.BasePage;
import com.capgemini.mrchecker.test.core.logger.BFLogger;
import com.devonfw.e2eInsere.common.utils.Utils;

/**
 * @author cmicoege
 *
 */
public class Catalog_Insere extends BasePage {

  private static final By searchInput = By.className("mat-input-element");

  private static final By searchCatalogMenu = By.className("catalog-master");

  private static final By searchServiceOptions = By.className("cdk-overlay-pane");

  private static final By searchListOfOptions = By.tagName("mat-option");

  private static final By searchServiceHeader = By.tagName("h1");

  private static final By searchFirstBlock = By.tagName("mat-accordion");

  private static final By searchDescriptionExpansionPanels = By.tagName("mat-expansion-panel");

  private static final By searchDescriptionContainer = By.className("mat-expansion-panel-content");

  private static final By searchDescription = By.tagName("p");

  private static final By searchMenuTree = By.className("sidenav-tree");

  private static final By searcMenuOptions = By.xpath("./mat-list/div/mat-list-item");

  private static final By searchArrowIcon = By.tagName("mat-icon");

  private static final By searchSubOptions = By.xpath("../mat-list/mat-list-item");

  private static final By searchOptionTextElement = By.tagName("strong");

  private static final By searchServiceName = By.className("serviceDefName");

  private static final By searchRequestButton = By.tagName("button");

  private static final By searchFAQButton = By.cssSelector("a[href='/faqs']");

  @Override
  public String pageTitle() {

    return "";
  }

  @Override
  public boolean isLoaded() {

    WebElement catalogMenu = getDriver().findElementDynamic(searchCatalogMenu);
    WebElement catalogMenuInput = getDriver().findElementDynamic(searchInput);

    return catalogMenuInput.isDisplayed();

  }

  @Override
  public void load() {

    BFLogger.logError("Insere Catalog page was not loaded.");

  }

  public void findService(String serviceInfo) {

    WebElement catalogMenu = getDriver().findElementDynamic(searchCatalogMenu);
    WebElement catalogMenuInput = catalogMenu.findElement(searchInput);

    catalogMenuInput.sendKeys(serviceInfo); // "Rome"
    /*
     * try { Thread.sleep(9000); } catch (Exception e) {
     *
     * }
     */
    // catalogMenuField.sendKeys("bleble");
    // System.out.println("blablabla:" + catalogMenuInput.getAttribute("innerHTML"));
    // System.out.println(catalogMenuInput.getText());
    // sendKeysToElement(serviceInfo, catalogMenuInput, getDriver(), getWebDriverWait(), String attribute);
  }

  public String selectService() {

    WebElement serviceOptions = getDriver().findElementDynamic(searchServiceOptions);
    List<WebElement> listOfOptions = serviceOptions.findElements(searchListOfOptions);
    Double randomNumber = Math.random() * listOfOptions.size();
    int randomIndex = randomNumber.intValue();
    WebElement selectedOption = listOfOptions.get(randomIndex);
    String selectedOptionName = selectedOption.getText();
    selectedOption.click();
    return selectedOptionName;
  }

  public String get_ServiceTitle() {

    WebElement serviceHeader = getDriver().findElementDynamic(searchServiceHeader);
    String serviceTitle = serviceHeader.getText();
    return serviceTitle;
  }

  public boolean serviceInfoIsContainedInDescriptionOrTitle(String serviceInfo) {

    String serviceInfoLowercase = serviceInfo.toLowerCase();

    WebElement serviceHeader = getDriver().findElementDynamic(searchServiceHeader);
    String serviceTitle = serviceHeader.getText();

    String serviceTitleLowercase = serviceTitle.toLowerCase();
    boolean containedInServiceTitle = serviceTitleLowercase.contains(serviceInfoLowercase);

    boolean containedInAnyDescription = false;
    List<String> descriptionsList = get_descriptionContent();
    int i = 0;
    while (i < descriptionsList.size() && containedInAnyDescription == false) {

      String description = descriptionsList.get(i);
      String descriptionLowercase = description.toLowerCase();
      containedInAnyDescription = descriptionLowercase.contains(serviceInfoLowercase);
      i++;
    }

    boolean containedInTitleOrDescription = containedInServiceTitle || containedInAnyDescription;
    return containedInTitleOrDescription;
  }

  public boolean checkThatSelectedServiceShowsInTheMenu(String selectedService) {

    String arrowDownText = "keyboard_arrow_down";
    WebElement catalogMenu = getDriver().findElementDynamic(searchCatalogMenu);
    WebElement catalogMenuTree = catalogMenu.findElement(searchMenuTree);
    List<WebElement> treeOptionsList = catalogMenuTree.findElements(searcMenuOptions);
    boolean serviceIsShowedInTheMenu = false;
    for (WebElement treeOption : treeOptionsList) {
      WebElement arrowIcon = treeOption.findElement(searchArrowIcon);
      String arrowIconText = arrowIcon.getText();
      if (arrowIconText.equals(arrowDownText)) {
        List<WebElement> treeSubOptions = treeOption.findElements(searchSubOptions);
        for (WebElement subOption : treeSubOptions) {
          String subOptionText = subOption.getText();
          if (subOptionText.equals(selectedService)) {
            serviceIsShowedInTheMenu = true;
          }
        }

      }
    }

    return serviceIsShowedInTheMenu;
  }

  public List<String> get_descriptionContent() {

    WebElement firstBlock = getDriver().findElementDynamic(searchFirstBlock);
    List<WebElement> expansionPanels = firstBlock.findElements(searchDescriptionExpansionPanels);
    List<String> descriptionsList = new ArrayList();
    for (WebElement panel : expansionPanels) {
      panel.click();

      WebElement descriptionContainer = panel.findElement(searchDescriptionContainer);
      WebElement descriptionElement = descriptionContainer.findElement(searchDescription);
      String description = descriptionElement.getText();
      descriptionsList.add(description);
    }

    return descriptionsList;
  }

  public boolean verifyNoMatchesAreFound() {

    boolean noMatchesAreFound = false;
    String expectedText = "No matches found";
    WebElement serviceOptions = getDriver().findElementDynamic(searchServiceOptions);
    List<WebElement> listOfOptions = serviceOptions.findElements(searchListOfOptions);
    int expectedAmountOfOptions = 1;
    int amountOfOptions = listOfOptions.size();
    String errorMessage = "Found more than one result, expected: " + expectedAmountOfOptions + ".";
    Assert.assertEquals(errorMessage, expectedAmountOfOptions, amountOfOptions);
    WebElement onlyOption = listOfOptions.get(0);
    String onlyOptionText = onlyOption.getText();
    String errorMessage2 = "The text in the option is not as expected. Expected: " + expectedText + ". Actual: "
        + onlyOptionText + ".";
    Assert.assertEquals(errorMessage2, expectedText, onlyOptionText);

    noMatchesAreFound = onlyOptionText.equals(expectedText);
    return noMatchesAreFound;
  }

  public void goToClarityProvisioning() {

    String optionToClick = "Project Management";
    String subOptionToClick = "Clarity";
    WebElement catalogMenu = getDriver().findElementDynamic(searchCatalogMenu);
    WebElement catalogMenuTree = catalogMenu.findElement(searchMenuTree);
    List<WebElement> treeOptionsList = catalogMenuTree.findElements(searcMenuOptions);

    for (WebElement treeOption : treeOptionsList) {
      WebElement treeOptionTextElement = treeOption.findElement(searchOptionTextElement);

      if (treeOptionTextElement.getText().equals(optionToClick)) {
        treeOption.click();
        List<WebElement> treeSubOptions = treeOption.findElements(searchSubOptions);

        for (WebElement subOption : treeSubOptions) {
          if (subOption.getText().equals(subOptionToClick)) {
            subOption.click();
          }
        }
      }
    }
  }

  public RequestService_Insere openClarityProvisioningRequest() {

    String clarityProvisioning = "Clarity Provisioning";
    WebElement firstBlock = getDriver().findElementDynamic(searchFirstBlock);
    List<WebElement> expansionPanels = firstBlock.findElements(searchDescriptionExpansionPanels);
    for (WebElement panel : expansionPanels) {
      Utils.fixedWait(0.5);
      WebElement serviceNameElement = panel.findElement(searchServiceName);
      String serviceName = serviceNameElement.getText();
      Utils.fixedWait(0.5);
      if (serviceName.equals(clarityProvisioning)) {
        Utils.fixedWait(0.5);
        panel.click();
        WebElement requestButton = panel.findElement(searchRequestButton);
        requestButton.click();
      }
    }
    return new RequestService_Insere();
  }

  public FAQ_Insere goToFAQ() {

    WebElement FAQButton = getDriver().findElementDynamic(searchFAQButton);
    FAQButton.click();
    return new FAQ_Insere();
  }
}
