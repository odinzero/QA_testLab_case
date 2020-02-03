package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HelperBase {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public HelperBase(WebDriver driver, WebDriverWait wait ) {
        this.driver = driver;
        this.wait = wait;
    }

    public void click(By locator) {
        driver.findElement(locator).click();
    }

    public void type(By locator, String text) {
        driver.findElement(locator).click();
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(text);
        driver.findElement(locator).sendKeys(Keys.ENTER);
    }

    protected void redirection(String urlText) {
        driver.get(urlText);
    }
    
    public String getTextLocator(By locator) {
       String text =  driver.findElement(locator).getText();
       return text;
    }
    
    public void waitPageLoaded( By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            Thread.sleep(4000);
        } catch (InterruptedException ex) {
            System.err.println("thread exception " + ex);
        }
    }  
}
