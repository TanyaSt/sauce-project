package com.saucelabs;

import com.saucelabs.Pages.spectoryPage;
import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.testng.SauceOnDemandAuthenticationProvider;
import com.saucelabs.testng.SauceOnDemandTestListener;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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

import static org.testng.Assert.assertEquals;


/**
 * Simple {@link org.openqa.selenium.remote.RemoteWebDriver} test that demonstrates how to run your Selenium tests with <a href="http://saucelabs.com/ondemand">Sauce OnDemand</a>.
 *
 * This test also includes the <a href="https://github.com/saucelabs/sauce-java/tree/master/testng">Sauce TestNG</a> helper classes, which will use the Sauce REST API to mark the Sauce Job as passed/failed.
 *
 * In order to use the {@link com.saucelabs.testng.SauceOnDemandTestListener}, the test must implement the {@link com.saucelabs.common.SauceOnDemandSessionIdProvider} and {@link com.saucelabs.testng.SauceOnDemandAuthenticationProvider} interfaces.
 * @author Ross Rowe
 */
@Listeners({SauceOnDemandTestListener.class})
public class WebDriverWithHelperTest implements SauceOnDemandSessionIdProvider, SauceOnDemandAuthenticationProvider {

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
    @BeforeMethod
    public void setUp(@Optional("ivolf") String username,
                      @Optional("90e3bb89-c21d-4885-85cf-f25494db06ff") String key,
                      @Optional("Windows 8.1") String os,
                      @Optional("chrome") String browser,
                      @Optional("39") String browserVersion,
                      Method method) throws Exception {

        if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(key)) {
            authentication = new SauceOnDemandAuthentication(username, key);
        } else {
            authentication = new SauceOnDemandAuthentication("ivolf", "90e3bb89-c21d-4885-85cf-f25494db06ff");
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
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public String getSessionId() {
        SessionId sessionId = ((RemoteWebDriver) driver).getSessionId();
        return (sessionId == null) ? null : sessionId.toString();
    }

    //@Test
    public void webDriverWithHelper() throws Exception {
        driver.get("http://www.amazon.com/");
        assertEquals(driver.getTitle(), "Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more");
    }

    @Test(groups = {"positive"}, description = "If fields are filled. Message sent")
    public void FillFields() {
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
    public void FillRequiredFields() {
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
    public void EmptyFields() {
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
    public void fillJustPhoneNumberField() {
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
    public void fillJustemailAddress() {
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
    public void FillRequiredFieldsIncorrectly() {
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
    public void FillRequiredFieldsIncorrectlyAddress() {
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
    public void FillRequiredFieldsIncorrectlyPnumber() {
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

    /**
     * Closes the WebDriver instance.
     *
     * @throws Exception thrown if an error occurs closing the WebDriver instance
     */
    @AfterMethod
    public void tearDown() throws Exception {
        driver.quit();
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public SauceOnDemandAuthentication getAuthentication() {
        return authentication;
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}

