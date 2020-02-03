package appmanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import models.PriceData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.utils;

public class ProductPriceHelper extends HelperBase {

    public ProductPriceHelper(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }
    
    
     public boolean  selectorCurrencyCheck(String text, String currency) throws InterruptedException, IOException {
      
//        Thread.sleep(4000);
////      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='_desktop_currency_selector']/div/span[2]")));
////      wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='_desktop_currency_selector']/div/span[2]"))); 
        waitPageLoaded(By.xpath("//div[@id='_desktop_currency_selector']/div/span[2]"));
        click(By.xpath("//div[@id='_desktop_currency_selector']/div/span[2]"));
        click(By.linkText(text));
        checkPrices(currency);
        return true;
    }

    public void checkTitleGoods() {
        WebElement title = driver.findElement(
                By.cssSelector("#products div div div p"));

        // System.out.println("title:" +title.getText() );
        if (title.getText().matches("\\s*(Товаров:\\s*\\d+\\.)\\s*")) {
            utils.logsToFile("5. nameplate \"Товары\" X is on place");
            System.out.println("5. nameplate \"Товары\" X is on place");
        } else {
            utils.logsToFile("5. nameplate \"Товары\" X is not on place");
            Assert.fail("5. nameplate \"Товары\" X is not on place");
        }
    }

    // List<String>
    public boolean checkPrices(String value) throws IOException  {
       // List<String> price = new ArrayList<String>();
        List<WebElement> elements = driver.findElements(
                By.cssSelector(".products article .thumbnail-container div.product-description div.product-price-and-shipping span.price"));

        for (WebElement element : elements) {
            String name = element.getText();
            // boolean b = name.matches("\\s*(\\d+[\\.\\,]*\\d*\\s*\\${1})\\s*");
            boolean b = matchPriceValues(name, value);
            //price.add(name);
            if (!b) {
                utils.logsToFile("The currency " + value + " does NOT match with price");
                return false;
            } else {
                utils.logsToFile("The currency " + value + " match with price");
            }

            //System.out.println("e:" + name);
        }
        return true;
    }

    public boolean matchPriceValues(String price, String value) {

        boolean b = false;

        switch (value) {
            default:
                break;
            case "$":
                b = price.matches("\\s*(\\d+[\\.\\,]*\\d*\\s*\\" + value + "{1})\\s*");
                return b;
            case "€":
            case "₴":
                b = price.matches("\\s*(\\d+[\\.\\,]*\\d*\\s*(" + value + "){1})\\s*");
                return b;
        }
        return b;
    }

    public List<PriceData> pricesAndDiscounts() {
        List<PriceData> prices = new ArrayList<PriceData>();
        
        List<WebElement> elements = driver.findElements(
                By.cssSelector(".products article .thumbnail-container div.product-description div.product-price-and-shipping"));
        
        for (WebElement element : elements) {

            String regularPrice = "";
            String discount = "";
            String price = "";

            boolean isPresent = element.findElements(By.cssSelector("span")).size() == 3;

            if (isPresent) {
                regularPrice = element.findElement(By.cssSelector("span.regular-price")).getText();
                discount = element.findElement(By.cssSelector("span.discount-percentage")).getText();
                price = element.findElement(By.cssSelector("span.price")).getText();
                // System.out.println("=1=");
            } else {
                discount = "";
                price = element.findElement(By.cssSelector("span.price")).getText();
                regularPrice = price;
                // System.out.println("=0=");
            }

            PriceData pd = new PriceData(regularPrice, discount, price);
            prices.add(pd);
            utils.logsToFile(pd.toString()); 
        }
        return prices;
    }

    public void checkPricesAndDiscounts() {
        List<WebElement> elements = driver.findElements(
                By.cssSelector(".products article .thumbnail-container div.product-description div.product-price-and-shipping"));

        for (WebElement element : elements) {

            boolean isPresent = element.findElements(By.cssSelector("span")).size() == 3;

            if (isPresent) {
                String regularPrice = element.findElement(By.cssSelector("span.regular-price")).getText();
                String discount = element.findElement(By.cssSelector("span.discount-percentage")).getText();
                String price = element.findElement(By.cssSelector("span.price")).getText();
                // System.out.println("=1=");
                if (regularPrice.equals("")) {
                    utils.logsToFile("regular price are absent");
                    Assert.fail("regular price are absent");
                } else if (discount.equals("")) {
                    utils.logsToFile("discount price are absent");
                    Assert.fail("discount price are absent");
                } else if (price.equals("")) {
                    utils.logsToFile("priceAfterDiscount are absent");
                    Assert.fail("priceAfterDiscount are absent");
                }
                utils.logsToFile("9. For goods with a discount, the percentage discount is indicated along with \n" +
                             " // the price before and after the discount. ---> OK ");

                String resDiscount = utils.calculateDiscountPrice(regularPrice, discount);

                if (resDiscount.equals(price)) {
                    utils.logsToFile("10. price equal regularPrice * discount");
                    System.out.println("10. price equal regularPrice * discount");
                } else {
                    utils.logsToFile("10. price NOT equal regularPrice * discount");
                    Assert.fail("10. price NOT equal regularPrice * discount");
                }
            }
        }
    }

}
