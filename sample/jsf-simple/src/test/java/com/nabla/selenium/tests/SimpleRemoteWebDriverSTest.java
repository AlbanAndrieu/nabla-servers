package com.nabla.selenium.tests;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import com.saucelabs.common.SauceOnDemandAuthentication;
//import com.saucelabs.common.SauceOnDemandSessionIdProvider;
//import com.saucelabs.junit.SauceOnDemandTestWatcher;

//@RunWith(Parameterized.class)
public class SimpleRemoteWebDriverSTest /* implements SauceOnDemandSessionIdProvider */
{

	private static final String SELENIUM_HUB_URL = "http://home.nabla.mobi:4444/wd/hub";

	private static final transient Logger LOGGER = LoggerFactory.getLogger(SimpleRemoteWebDriverSTest.class);
    
	public static final long           PAGE_TO_LOAD_TIMEOUT = 30000;
    
    private static WebDriver           REAL_DRIVER;
    private static String              BASE_URL           = SimpleWebDriverSTest.DEFAULT_URL;
    private static String              CHROME_DRIVER      = SimpleWebDriverSTest.DEFAULT_CHROMEDRIVER;
    private static String              FIREFOX_BIN        = SimpleWebDriverSTest.DEFAULT_FIREFOXBIN;
    //private boolean                    acceptNextAlert    = true;
    //private static StringBuffer        VERIFICATION_ERRORS = new StringBuffer();
    // private DefaultSelenium selenium;

    private static DesiredCapabilities CAPABILITIES;

    private static long DEPLOY_WAIT = 10;

    // private static Platform ANDROID, LINUX, MAC, UNIX, VISTA, WINDOWS, XP, platformValue;
    // private String browser, browserVersion, platform, sessionId = "";

    // Create an array of available platforms from the "private static Platform" declaration above
    // Platform[] platformValues = Platform.values();

    // public static SeleniumServer server;

    /**
     * Constructs a {@link com.saucelabs.common.SauceOnDemandAuthentication} instance using the supplied Sauce
     * user name and access key. To use the authentication supplied by environment variables or
     * from an external file, use the no-arg {@link com.saucelabs.common.SauceOnDemandAuthentication} constructor.
     */
    /*
     * public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication();
     * public Platform setPlatformCapabilities(String platformParam) {
     * String platformVal = platformParam;
     * for (int p=0; p<platformValues.length; p++) {
     * platformValue = platformValues[p++];
     * if (platformValue.toString() == platformVal) break;
     * }
     * return platformValue;
     * }
     */

    /**
     * JUnit Rule that marks Sauce Jobs as passed/failed when the test succeeds or fails.
     */
    /*
     * public @Rule
     * SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);
     */

    /**
     * JUnit Rule that records the test name of the current test. When this is referenced
     * during the creation of {@link org.openqa.selenium.remote.DesiredCapabilities}, the test method name is assigned
     * to the Sauce Job name and recorded in Jenkins Console Output and in the Sauce Jobs
     * Report in the Jenkins project's home page.
     */
    // public @Rule TestName testName = new TestName();

    /**
     * JUnit annotation that runs each test once for each item in a Collection.
     * Feel free to add as many additional parameters as you like to the capabilitiesParams array.
     * Note: If you add parameters for the MAC platform, make sure that you have Mac minutes in
     * your <a href="https://saucelabs.com/login">Sauce account</a> or the test will fail.
     */
    /*
     * @Parameters
     * public static Collection<Object[]> data() {
     * String json = System.getenv("SAUCE_ONDEMAND_BROWSERS");
     * List<Object[]> browsers = new ArrayList<Object[]>();
     * JSONArray browserArray = null;
     * try {
     * browserArray = new JSONArray(json);
     * for (int i =0;i<browserArray.length();i++) {
     * JSONObject browserJSON = browserArray.getJSONObject(i);
     * browsers.add(new Object[]{browserJSON.get("browser"), browserJSON.get("browser-version"), browserJSON.get("os")});
     * }
     * } catch (JSONException e) {
     * e.printStackTrace();
     * }
     * return browsers;
     * }
     * public SimpleRemoteWebDriverSTest(String s1, String s2, String s3) {
     * browser = s1;
     * browserVersion = s2;
     * platform = s3;
     * }
     */

    private static final Thread           CLOSE_THREAD         = new Thread()
    {
        @Override
        public void run()
        {
            if (null != SimpleRemoteWebDriverSTest.REAL_DRIVER)
            {
                REAL_DRIVER.close();
                SimpleRemoteWebDriverSTest.LOGGER.info("closing the browser");
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

        BASE_URL = System.getProperty("webdriver.base.url");

        if (null == BASE_URL)
        {
            System.out.println("Use default webdriver.base.url");
            BASE_URL = SimpleWebDriverSTest.DEFAULT_URL;
            System.setProperty("webdriver.base.url", BASE_URL);
        }
        System.out.println("webdriver.base.url is : " + BASE_URL + "\n");

        CHROME_DRIVER = System.getProperty("webdriver.chrome.driver");
        if (null == CHROME_DRIVER)
        {
            System.out.println("Use default webdriver.base.url");
            CHROME_DRIVER = SimpleWebDriverSTest.DEFAULT_CHROMEDRIVER;
        }
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER);        
        System.out.println("webdriver.chrome.driver is : " + CHROME_DRIVER + "\n");

        // System.setProperty("webdriver.safari.noinstall", "true");
        FIREFOX_BIN = System.getProperty("webdriver.firefox.bin");
        if (null == FIREFOX_BIN)
        {
            System.out.println("Use default webdriver.firefox.bin");
            FIREFOX_BIN = SimpleWebDriverSTest.DEFAULT_FIREFOXBIN;
        }
        System.setProperty("webdriver.firefox.bin", FIREFOX_BIN);        
        System.out.println("webdriver.firefox.bin is : " + FIREFOX_BIN + "\n");
        // System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\Mozilla Firefox\\firefox");

        // http://localhost:4444/selenium-server/driver/?cmd=shutDownSeleniumServer
        // startSeleniumServer(server);

        REAL_DRIVER = SimpleRemoteWebDriverSTest.getCurrentDriver();
        /*
         * this.driver = new RemoteWebDriver(
         * new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
         * capabilities);
         * this.sessionId = ((RemoteWebDriver)driver).getSessionId().toString();
         * if (browserVersion == "") browserVersion = "unspecified";
         * String browserName = String.format("%-19s", browser).replaceAll(" ", ".").replaceFirst("[.]", " ");
         * String browserVer = String.format("%-19s", browserVersion).replaceAll(" ", ".");
         * System.out.println("@Test validateTitle() testing browser/version: " + browserName + browserVer + "platform: " + platform);
         */

        // FirefoxProfile profile = new FirefoxProfile();
        // FirefoxBinary binary = new FirefoxBinary(new File("C:\\path to firefox\\firefox.exe"));
        // driver = new FirefoxDriver(binary, profile);
        // driver = new FirefoxDriver();
        // driver = new HtmlUnitDriver(true);

        // RemoteWebDriver does not implement the TakesScreenshot class
        // if the driver does have the Capabilities to take a screenshot
        // then Augmenter will add the TakesScreenshot methods to the instance
        // WebDriver augmentedDriver = new Augmenter().augment(driver);
        // File screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);

        REAL_DRIVER.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        REAL_DRIVER.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
        // driver.manage().window().setSize(new Dimension(1920, 1080));
        // selenium = new WebDriverBackedSelenium(driver, baseUrl);

        SimpleRemoteWebDriverSTest.LOGGER.info("Waiting for deploy to be finished before starting test (in seconds) : {}", PAGE_TO_LOAD_TIMEOUT / 1000);
        //TimeUnit.SECONDS.sleep(DEPLOY_WAIT);
        Thread.sleep(PAGE_TO_LOAD_TIMEOUT); // 30 s
        // screenshot.
    }

    /*
     * @Before
     * public void homePageRefresh() throws IOException
     * {
     * driver.manage().deleteAllCookies();
     * // driver.get(propertyKeysLoader("login.base.url"));
     * }
     */
    private synchronized static WebDriver getCurrentDriver() 
    {
        if (SimpleRemoteWebDriverSTest.REAL_DRIVER == null)
        {
            // FirefoxProfile profile = new ProfilesIni().getProfile("Selenium");

            ChromeOptions options = new ChromeOptions();
            options.addArguments("no-sandbox");

            CAPABILITIES = DesiredCapabilities.chrome();
            CAPABILITIES.setCapability(ChromeOptions.CAPABILITY, options);

            CAPABILITIES = DesiredCapabilities.chrome();
            // capabilities = DesiredCapabilities.firefox();
            // capabilities.setCapability(FirefoxDriver.PROFILE, profile);
            CAPABILITIES.setJavascriptEnabled(true);

            // FirefoxBinary ffb = new FirefoxBinary(new File(firefoxBin));

            // capabilities.setCapability(FirefoxDriver.BINARY, ffb);
            // say you use the redhat5 label to indicate RHEL5 and the amd64 label
            // to specify the architecture
            // capabilities.setCapability("jenkins.label", "redhat5 && amd64");
            // Say you want a specific node to thread your request, just specify the
            // node name (it must be running a selenium configuration though)
            // capabilities.setCapability("jenkins.nodeName", "(master)");

            // capabilities.setVersion("12");
            // capabilities.setPlatform(Platform.WINDOWS);

            // capabilities.setCapability("version", "8");
            // capabilities.setCapability(CapabilityType.BROWSER_NAME, "firefox");

            // capabilities.setCapability(CapabilityType.VERSION, "24.0");
            // capabilities.setCapability(CapabilityType.BROWSE_NAME, "*googlechrome");

            // capabilities.setCapability(CapabilityType.PLATFORM, Platform.LINUX);

            // driver = new RemoteWebDriver(new URL("http://albanandrieu:2e5a4730-39e1-41c2-9e1f-a84fa24e15fd@ondemand.saucelabs.com:80/wd/hub"), capabilities);
            // driver = new RemoteWebDriver(new URL("http://nabla:5655798f-14ba-4bc6-9d11-8d039a2517c0@ondemand.saucelabs.com:80/wd/hub"), capabilities);

            // capabilities = new DesiredCapabilities(browser, browserVersion, setPlatformCapabilities(platform));
            // capabilities.setCapability("name", this.getClass().getName() + "." + testName.getMethodName());
            try {
				REAL_DRIVER = new RemoteWebDriver(new URL(SELENIUM_HUB_URL), CAPABILITIES);
			} catch (MalformedURLException e) {
				SimpleRemoteWebDriverSTest.LOGGER.info("Wrong selenium URL : {}", SELENIUM_HUB_URL);
			}
            
        	SimpleRemoteWebDriverSTest.REAL_DRIVER = new ChromeDriver();
        }
        
        return SimpleRemoteWebDriverSTest.REAL_DRIVER;
    }
    
    @Test
    public void testSimpleS() throws Exception
    {
    	getCurrentDriver().get(BASE_URL + "/welcome/hello.xhtml");

        // WebElement myDynamicElement = (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(By.id("j_idt8")));
        assertEquals("JSF 2.0 Hello World Example - hello.xhtml", getCurrentDriver().findElement(By.cssSelector("h3")).getText());
        getCurrentDriver().findElement(By.name(SimpleWebDriverSTest.INPUT_TEXT_ID)).clear();
        getCurrentDriver().findElement(By.name(SimpleWebDriverSTest.INPUT_TEXT_ID)).sendKeys("Test me !!!");

        // wait for the application to get fully loaded
        WebElement findOwnerLink = (new WebDriverWait(getCurrentDriver(), 5)).until(new ExpectedCondition<WebElement>()
        {
            public WebElement apply(WebDriver d)
            {
                // d.get(baseUrl);
                return d.findElement(By.name(SimpleWebDriverSTest.INPUT_TEXT_ID));
            }
        });

        findOwnerLink.click();

        WebDriverWait wait = new WebDriverWait(getCurrentDriver(), 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.name(SimpleWebDriverSTest.SUBMIT_BUTTON_ID)));
        getCurrentDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        getCurrentDriver().findElement(By.name(SimpleWebDriverSTest.SUBMIT_BUTTON_ID)).click();
        
        Thread.sleep(SimpleWebDriverSTest.PAGE_TO_LOAD_TIMEOUT); // 30 s
        
        assertEquals("JSF 2.0 Hello World Example - welcome.xhtml", getCurrentDriver().findElement(By.cssSelector("h3")).getText());
        assertEquals("Welcome Test me !!!", getCurrentDriver().findElement(By.cssSelector("h4")).getText());
        
    }

    @AfterClass
    public static void tearDown() throws Exception
    {
        // stopSeleniumServer(server, selenium);
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
     * @Override
     * public String getSessionId() {
     * return sessionId;
     * }
     */

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
