package appmanager;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.testng.Assert.fail;

public class ApplicationManager {

    protected WebDriver driver;
    protected WebDriverWait wait;
    
    private HelperBase helperBase;
    private ProductPriceHelper productPriceHelper;
    private String browser;

    protected StringBuffer verificationErrors = new StringBuffer();

    public ApplicationManager(String browser) {
        this.browser = browser;
    }

    public void init() {
        if (browser.equals(BrowserType.FIREFOX)) {
           System.setProperty("webdriver.gecko.driver", "D:\\= JAVA_TESTING =\\QATestLab_test\\src\\test\\resources\\geckodriver.exe"); 
           driver = new FirefoxDriver();
        } else if (browser.equals(BrowserType.CHROME)) {
            System.setProperty("webdriver.chrome.driver", "D:\\= JAVA_TESTING =\\QATestLab_test\\src\\test\\resources\\chromedriver.exe");
           //System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resources/chromedriver.exe");
           driver = new ChromeDriver();
        } else if (browser.equals(BrowserType.IE)) {
            System.setProperty("webdriver.ie.driver", "D:\\= JAVA_TESTING =\\QATestLab_test\\src\\test\\resources\\IEDriverServer.exe");
            driver = new InternetExplorerDriver();
        }

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://prestashop-automation.qatestlab.com.ua/ru");

        wait = new WebDriverWait(driver, 10);
        
        helperBase = new HelperBase(driver, wait);
        productPriceHelper = new ProductPriceHelper(driver, wait);
    }

    public HelperBase getHelperBase() {
        return helperBase;
    }

    public ProductPriceHelper getProductPriceHelper() {
        return productPriceHelper;
    }

    public void waitPageLoaded(int seconds, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);

        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

        wait.until(ExpectedConditions.stalenessOf(el));
    }
    
     public void stop() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

}
