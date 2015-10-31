package com.saucelabs.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class spectoryPage extends Page {

    @FindBy(xpath = "//*[@id='scroll-down-arrow']/img")
    WebElement anchor;

    @FindBy(xpath = "//*[@class='input-field name-input eng-variant first-input'][\"name\"]")
    WebElement Firstname;

    @FindBy(xpath = "//*[@class='input-field email-input eng-variant'][\"email address\"]")
    WebElement emailAddress;

    @FindBy(xpath = "//*[@class='input-field phone-input eng-variant'][\"phone number\"]")
    WebElement phoneNumber;

    @FindBy(xpath = "//*[@class='text-field desc-input eng-variant'][\"tell us a bit about your project\"]")
    WebElement tellAboutProject;

    @FindBy(xpath = "//*[@class='send-btn eng-variant']['send']")
    WebElement sendButton;

    @FindBy(xpath = "//*[@class='eng-variant'][contains(text(),'Nice to meet you :)')]")
    WebElement confirmlabel;

    @FindBy(xpath = "//*[@class=\"contact-form-fields\"][\"display: block\"]")
    WebElement confirmSendForm;


    public spectoryPage(WebDriver driver) {
        super(driver);
        driver.get("http://spectory-web.herokuapp.com/en");
        PageFactory.initElements(driver, this);
    }

    //Check that 'Nice to meet you :)' on the screen
    public boolean CheckConfirmLabel() {
        return exists(confirmlabel);
    }

    public spectoryPage clickToAnchor() {
        clickElement(anchor);
        return this;
    }

    public spectoryPage fillName(String name) {
        setElementText(Firstname, name);
        return this;
    }

    public spectoryPage clickSentButton() {
        clickElement(sendButton);
        return this;
    }

    public spectoryPage fillemailAddress(String eAddress) {
        setElementText(emailAddress, eAddress);
        return this;
    }

    public spectoryPage fillphoneNumber(String pNumber) {
        setElementText(phoneNumber, pNumber);
        return this;
    }

    public spectoryPage filltellAboutProject(String tAboutProject) {
        setElementText(tellAboutProject, tAboutProject);
        return this;
    }

}
