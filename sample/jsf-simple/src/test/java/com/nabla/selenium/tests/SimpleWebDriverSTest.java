package com.nabla.selenium.tests;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.os.WindowsUtils;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleWebDriverSTest
{
	
    private static final transient Logger LOGGER = LoggerFactory.getLogger(SimpleWebDriverSTest.class);
    
    public static final String         FORM_ID              = "hello-form";
    public static final String         SUBMIT_BUTTON_ID     = FORM_ID + ":submit-text";
    public static final String         INPUT_TEXT_ID        = FORM_ID + ":input-text";
    // private static final String DEFAULT_CHROMEDRIVER = "C:\\chromedriver\\chromedriver.exe"; // "/var/lib/chromedriver"
    // private static final String DEFAULT_FIREFOXBIN = "C:\\Program Files\\Mozilla Firefox\\firefox.exe"; // "/usr/lib/firefox/firefox"
    public static final String         DEFAULT_CHROMEDRIVER = "/var/lib/chromedriver";                      // "C:\\chromedriver\\chromedriver.exe"
    public static final String         DEFAULT_FIREFOXBIN   = "/usr/lib/firefox/firefox";                   // "C:\\Program Files\\Mozilla Firefox\\firefox.exe"

    public static final String         DEFAULT_HOST         = "localhost";
    public static final String         DEFAULT_PORT         = "9090";
    public static final String         DEFAULT_URL          = "http://" + DEFAULT_HOST + ":" + DEFAULT_PORT;

    public static final long           PAGE_TO_LOAD_TIMEOUT = 30000;

    private static WebDriver           REAL_DRIVER;
    private static String              BASE_URL             = DEFAULT_URL;
    private static String              CHROME_DRIVER        = DEFAULT_CHROMEDRIVER;
    private static String              FIREFOX_BIN          = DEFAULT_FIREFOXBIN;
    //private boolean                    acceptNextAlert      = true;
    //private static StringBuffer        VERIFICATION_ERRORS = new StringBuffer();
    //private static DefaultSelenium     SELENIUM;

    //private static DesiredCapabilities CAPABILITIES;

    private static long DEPLOY_WAIT = 10;
    // public static SeleniumServer server;

    private static final Thread           CLOSE_THREAD         = new Thread()
    {
        @Override
        public void run()
        {
            if (null != SimpleWebDriverSTest.REAL_DRIVER)
            {
                REAL_DRIVER.close();
                SimpleWebDriverSTest.LOGGER.info("closing the browser");
            }
        }
    };

    static
    {
        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
    }
    
    public static void close()
    {
        if (Thread.currentThread() != CLOSE_THREAD)
        {
            throw new UnsupportedOperationException("You shouldn't close this WebDriver. It's shared and will close when the JVM exits.");
        }
        // super.close();
    }
    
    @BeforeClass
    public static void setUp() throws Exception
    {
    	
        WindowsUtils.tryToKillByName("firefox.exe");        

        BASE_URL = System.getProperty("webdriver.base.url");

        if (null == BASE_URL)
        {
            System.out.println("Use default webdriver.base.url");
            BASE_URL = DEFAULT_URL;
            System.setProperty("webdriver.base.url", BASE_URL);
        }
        System.out.println("webdriver.base.url is : " + BASE_URL + "\n");

        CHROME_DRIVER = System.getProperty("webdriver.chrome.driver");
        if (null == CHROME_DRIVER)
        {
            System.out.println("Use default webdriver.base.url");
            CHROME_DRIVER = DEFAULT_CHROMEDRIVER;
        }
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER);
        System.out.println("webdriver.chrome.driver is : " + CHROME_DRIVER + "\n");

        FIREFOX_BIN = System.getProperty("webdriver.firefox.bin");
        if (null == FIREFOX_BIN)
        {
            System.out.println("Use default webdriver.firefox.bin");
            FIREFOX_BIN = DEFAULT_FIREFOXBIN;
        }
        System.setProperty("webdriver.firefox.bin", FIREFOX_BIN);        
        System.out.println("webdriver.firefox.bin is : " + FIREFOX_BIN + "\n");
        // ProfilesIni allProfiles = new ProfilesIni();
        // FirefoxProfile profile = allProfiles.getProfile("Selenium");
        // FirefoxProfile profile = new FirefoxProfile();
        // FirefoxBinary binary = new FirefoxBinary(new File(firefoxBin));
        // driver = new FirefoxDriver(binary, profile);

        REAL_DRIVER = SimpleWebDriverSTest.getCurrentDriver();
        // driver = new FirefoxDriver(profile);
        // driver = new HtmlUnitDriver(true);

        // RemoteWebDriver does not implement the TakesScreenshot class
        // if the driver does have the Capabilities to take a screenshot
        // then Augmenter will add the TakesScreenshot methods to the instance
        // WebDriver augmentedDriver = new Augmenter().augment(driver);
        // File screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);

        REAL_DRIVER.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        REAL_DRIVER.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
        // driver.manage().window().setSize(new Dimension(1920, 1080));
        //SELENIUM = new WebDriverBackedSelenium(DRIVER, BASE_URL);

        SimpleWebDriverSTest.LOGGER.info("Waiting for deploy to be finished before starting test (in seconds) : {}", PAGE_TO_LOAD_TIMEOUT / 1000);
        //TimeUnit.SECONDS.sleep(DEPLOY_WAIT);
        Thread.sleep(SimpleWebDriverSTest.PAGE_TO_LOAD_TIMEOUT); // 30 s
        // screenshot.
    }

    private synchronized static WebDriver getCurrentDriver()
    {
        if (SimpleWebDriverSTest.REAL_DRIVER == null)
        {
            //ChromeOptions options = new ChromeOptions();
        	//options.addArguments("no-sandbox");

        	//CAPABILITIES = DesiredCapabilities.chrome();
        	//CAPABILITIES.setCapability(ChromeOptions.CAPABILITY, options);

        	//CAPABILITIES.setJavascriptEnabled(true);

        	//SimpleWebDriverSTest.REAL_DRIVER = new ChromeDriver(CAPABILITIES);
            
        	SimpleWebDriverSTest.REAL_DRIVER = new ChromeDriver();
        }
        return SimpleWebDriverSTest.REAL_DRIVER;
    }
    
    /*
     * @Before
     * public void homePageRefresh() throws IOException
     * {
     * driver.manage().deleteAllCookies();
     * // driver.get(propertyKeysLoader("login.base.url"));
     * }
     */

    @Test
    public void testSimpleS() throws Exception
    {
        getCurrentDriver().get(BASE_URL + "/welcome/hello.xhtml");

        // WebElement myDynamicElement = (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(By.id("j_idt8")));
        assertEquals("JSF 2.0 Hello World Example - hello.xhtml", getCurrentDriver().findElement(By.cssSelector("h3")).getText());
        getCurrentDriver().findElement(By.name(INPUT_TEXT_ID)).clear();
        getCurrentDriver().findElement(By.name(INPUT_TEXT_ID)).sendKeys("Test me !!!");

        // wait for the application to get fully loaded
        WebElement findOwnerLink = (new WebDriverWait(getCurrentDriver(), 5)).until(new ExpectedCondition<WebElement>()
        {
            public WebElement apply(WebDriver d)
            {
                // d.get(baseUrl);
                return d.findElement(By.name(INPUT_TEXT_ID));
            }
        });

        findOwnerLink.click();

        WebDriverWait wait = new WebDriverWait(getCurrentDriver(), 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.name(SUBMIT_BUTTON_ID)));
        getCurrentDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        getCurrentDriver().findElement(By.name(SUBMIT_BUTTON_ID)).click();

        Thread.sleep(SimpleWebDriverSTest.PAGE_TO_LOAD_TIMEOUT); // 30 s
        
        assertEquals("JSF 2.0 Hello World Example - welcome.xhtml", getCurrentDriver().findElement(By.cssSelector("h3")).getText());
        assertEquals("Welcome Test me !!!", getCurrentDriver().findElement(By.cssSelector("h4")).getText());
        
    }

    @AfterClass
    public static void tearDown() throws Exception
    {
        if (null != getCurrentDriver())
        {
            getCurrentDriver().quit();
        }
        //String verificationErrorString = VERIFICATION_ERRORS.toString();
        //if (!"".equals(verificationErrorString))
        //{
        //    fail(verificationErrorString);
        //}
    }

    /*
    private boolean isElementPresent(By by)
    {
        try
        {
            getCurrentDriver().findElement(by);
            return true;
        } catch (NoSuchElementException e)
        {
            return false;
        }
    }

    private boolean isAlertPresent()
    {
        try
        {
            getCurrentDriver().switchTo().alert();
            return true;
        } catch (NoAlertPresentException e)
        {
            return false;
        }
    }

    private String closeAlertAndGetItsText()
    {
        try
        {
            Alert alert = getCurrentDriver().switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert)
            {
                alert.accept();
            } else
            {
                alert.dismiss();
            }
            return alertText;
        } finally
        {
            acceptNextAlert = true;
        }
    }
    */

}
