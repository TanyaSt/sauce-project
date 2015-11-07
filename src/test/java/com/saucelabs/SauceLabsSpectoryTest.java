package com.saucelabs;

import com.saucelabs.Pages.spectoryPage;
import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.testng.SauceOnDemandAuthenticationProvider;
import com.saucelabs.testng.SauceOnDemandTestListener;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.concurrent.TimeUnit;


@Listeners({SauceOnDemandTestListener.class})
public class SauceLabsSpectoryTest implements SauceOnDemandSessionIdProvider, SauceOnDemandAuthenticationProvider {

    //for positive
    public static String name = "User1";
    public static String eAddress = "dwash@yopmail.com";
    public static String pNumber = "0541234567";
    public static String tAboutProject = "It`s my project";
    //for negative
    public static String eAddress_false = "dwashyopmail";
    public static String pNumber_false = "abcdD!@#$%^&*()";
    public SauceOnDemandAuthentication authentication;
    public spectoryPage SpectoryPage;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private WebDriver driver;

    /**
     * Creates a new {@link org.openqa.selenium.remote.RemoteWebDriver} instance to be used to run WebDriver tests using Sauce.
     *
     * @param username       the Sauce username
     * @param key            the Sauce access key
     * @param os             the operating system to be used
     * @param browser        the name of the browser to be used
     * @param browserVersion the version of the browser to be used
     * @param method         the test method being executed
     * @throws Exception thrown if any errors occur in the creation of the WebDriver instance
     */
    @Parameters({"username", "key", "os", "browser", "browserVersion"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("Snigirev") String username,
                      @Optional("5e9bb1a7-6d1c-44e5-b460-e65d18e2597c") String key,
                      @Optional("Windows 8.1") String os,
                      @Optional("chrome") String browser,
                      @Optional("39") String browserVersion,
                      Method method) throws Exception {

        if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(key)) {
            authentication = new SauceOnDemandAuthentication(username, key);
        } else {
            authentication = new SauceOnDemandAuthentication("Snigirev", "5e9bb1a7-6d1c-44e5-b460-e65d18e2597c");
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browser);
        capabilities.setCapability("version", browserVersion);
        capabilities.setCapability("platform", os);
        capabilities.setCapability("name", method.getName());
        this.driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                capabilities);

        SpectoryPage = PageFactory.initElements(driver, spectoryPage.class);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://spectory-web.herokuapp.com/en");
    }


    @Override
    public String getSessionId() {
        SessionId sessionId = ((RemoteWebDriver) driver).getSessionId();
        return (sessionId == null) ? null : sessionId.toString();
    }


    @Test(groups = {"positive"}, description = "If fields are filled. Message sent")
    public void FillFieldsPositive() {
        try {

            SpectoryPage
                    .clickToAnchor()
                    .fillName(name)
                    .fillemailAddress(eAddress)        //RequiredFields
                    .fillphoneNumber(pNumber)          //RequiredFields
                    .filltellAboutProject(tAboutProject)
                    .clickSentButton();

            Assert.assertTrue(SpectoryPage.CheckConfirmLabel(), "Confirm Label not appeared");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test(groups = {"positive"}, description = "If fields are filled. Message sent")
    public void FillRequiredFieldsPositive() {
        try {
            SpectoryPage
                    .clickToAnchor()

                    .fillemailAddress(eAddress)     //RequiredFields
                    .fillphoneNumber(pNumber)       //RequiredFields
                    .clickSentButton();

            Assert.assertTrue(SpectoryPage.CheckConfirmLabel(), "Confirm Label not appeared");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test(groups = {"negative"}, description = "If fields are empty. Message not be sent")
    public void EmptyFieldsNegative() {
        try {
            SpectoryPage
                    .clickToAnchor()
                    .clickSentButton();

            Assert.assertTrue(!SpectoryPage.CheckConfirmLabel(), "Confirm Label not appeared");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test(groups = {"negative"}, description = "If one of required fields are empty. Message not be sent")
    public void fillJustPhoneNumberFieldNegative() {
        try {
            SpectoryPage
                    .clickToAnchor()
                    .fillphoneNumber(pNumber) //RequiredFields
                    .clickSentButton();

            Assert.assertTrue(!SpectoryPage.CheckConfirmLabel(), "Confirm Label not appeared");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test(groups = {"negative"}, description = "If one of required fields are empty. Message not be sent")
    public void fillJustemailAddressNegative() {
        try {
            SpectoryPage
                    .clickToAnchor()
                    .fillemailAddress(eAddress)         //RequiredFields
                    .clickSentButton();

            Assert.assertTrue(!SpectoryPage.CheckConfirmLabel(), "Confirm Label not appeared");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test(groups = {"negative"}, description = "If fields are empty. Message not be sent")
    public void FillRequiredFieldsIncorrectlyNegative() {
        try {
            SpectoryPage
                    .clickToAnchor()
                    .fillemailAddress(eAddress_false)   //RequiredFields
                    .fillphoneNumber(pNumber_false)     //RequiredFields
                    .clickSentButton();

            Assert.assertTrue(!SpectoryPage.CheckConfirmLabel(), "Confirm Label not appeared");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test(groups = {"negative"}, description = "If one of field is incorrectly. Message not be sent")
    public void FillRequiredFieldsIncorrectlyAddressNegative() {
        try {
            SpectoryPage
                    .clickToAnchor()
                    .fillemailAddress(eAddress_false)   //RequiredFields
                    .fillphoneNumber(pNumber)           //RequiredFields
                    .clickSentButton();

            Assert.assertTrue(!SpectoryPage.CheckConfirmLabel(), "Confirm Label not appeared");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test(groups = {"negative"}, description = "If one of field is incorrectly. Message not be sent")
    public void FillRequiredFieldsIncorrectlyPnumberNegative() {
        try {
            SpectoryPage
                    .clickToAnchor()
                    .fillemailAddress(eAddress)         //RequiredFields
                    .fillphoneNumber(pNumber_false)     //RequiredFields
                    .clickSentButton();

            Assert.assertTrue(!SpectoryPage.CheckConfirmLabel(), "Confirm Label not appeared");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void tearDown() throws Exception {
        driver.quit();
    }


    @Override
    public SauceOnDemandAuthentication getAuthentication() {
        return authentication;
    }

}

