
package tests;

import appmanager.TestBase;
import static appmanager.TestBase.app;
import java.io.IOException;
import java.util.List;
import models.PriceData;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.openqa.selenium.By;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;
import utils.utils;

public class ScenarioTest extends TestBase {
    
    
     @Test(enabled = true)
    public void discountsCase() throws InterruptedException, IOException {
        
        // 2. Check that the price of products in the "Popular Products" section is indicated
        // in accordance with the currency specified in the site header (USD, EUR, UAH).
        boolean b3 = app.getProductPriceHelper().selectorCurrencyCheck("USD $", "$");
        assertTrue(b3, "The currency match the selected USD currency ");
        utils.writeToFileAndConsole("2.1.  The currency match the selected USD currency ");
        boolean b1 = app.getProductPriceHelper().selectorCurrencyCheck("EUR €", "€");
        assertTrue(b1, "The currency match the selected EUR currency");
        utils.writeToFileAndConsole("2.2.  The currency match the selected EUR currency ");
        boolean b2 = app.getProductPriceHelper().selectorCurrencyCheck("UAH ₴", "₴");
        assertTrue(b2, "The currency match the selected UAH currency ");
        utils.writeToFileAndConsole("2.3.  The currency match the selected UAH currency ");
       
        // 3. set price to "USD $"
        utils.writeToFileAndConsole("3.------------- set price to \"USD $\" ------------------ ");
        boolean b4 = app.getProductPriceHelper().selectorCurrencyCheck("USD $", "$");
        assertTrue(b4, "The currency match the selected USD currency ");
        utils.writeToFileAndConsole("3. The currency match the selected USD currency ");
        
        // 4. do search in catalog by word "dress"
        utils.writeToFileAndConsole("4. ------------- type word in catalog \"dress\" ------- ");
        app.getHelperBase().type(By.name("s"), "dress");
        utils.writeToFileAndConsole("4. ------------- do search in catalog by word \"dress\" ------- ");
        
        // 5. Check that the page "Search Results" contains the inscription "Products: x",
        // where x is the number of actually found products.
        utils.writeToFileAndConsole("5. ----- start: the page \"Search Results\" contains the inscription \"Products: x\"  ------- ");
        app.getProductPriceHelper().checkTitleGoods();
        utils.writeToFileAndConsole("5. ----- end: the page \"Search Results\" contains the inscription \"Products: x\"  ------- ");
        
        // 6. Check that the price of all displayed results is displayed in dollars
        utils.writeToFileAndConsole("6. ----- start: Check that the price of all displayed results is displayed in dollars  ------- ");
        app.getProductPriceHelper().checkPrices("$");
        utils.writeToFileAndConsole("6. ----- end: Check that the price of all displayed results is displayed in dollars ------- ");

        utils.writeToFileAndConsole("7. ----- start: do check before select sorting  ------- ");
        // do check before sorting 
        List<PriceData> pricesBefore = app.getProductPriceHelper().pricesAndDiscounts();
        // do reverse sorting according to p.8
        utils.writeToFileAndConsole("7. ----- do reverse sorting according to p.8  ------- ");
        utils.rsort(pricesBefore);
                for (PriceData price : pricesBefore) {
           utils.writeToFileAndConsole("7.  " + price.toString() );            
        }
        utils.writeToFileAndConsole("7. ----- do reverse sorting for future comparing  ------- ");
        // 7. Set the sorting to "high to low."
        utils.writeToFileAndConsole("7. ----- start: Set the sorting to \"high to low.\" ------ ");
        app.getHelperBase().click(By.xpath("//div[@id='js-product-list-top']/div[2]/div/div/a/i"));
        app.getHelperBase().click(By.linkText("Цене: от высокой к низкой"));
        utils.writeToFileAndConsole("7. ----- end: do search in catalog by word \"dress\" ------ ");
        
        // 8. Check that the goods are sorted by price, while some products may be at a discount,
        // and when sorting, the price is used without a discount.
        //Thread.sleep(3000); 
        app.getHelperBase().waitPageLoaded(By.xpath("//div[@id='js-product-list-top']/div[2]/div/div/a/i")); 
        utils.writeToFileAndConsole("8. ----- start: Check that the goods are sorted by price ------ ");
        List<PriceData> pricesAfter = app.getProductPriceHelper().pricesAndDiscounts();
        //Assert.assertEquals(pricesBefore, pricesAfter);
        MatcherAssert.assertThat(pricesAfter, CoreMatchers.equalTo(pricesBefore) );
        utils.writeToFileAndConsole("8. ----- end: Check that the goods are sorted by price ------ ");
        
        utils.writeToFileAndConsole("9. and 10. ----- start:  ------ ");
        // 9. For goods with a discount, the percentage discount is indicated along with 
        // the price before and after the discount.
        // 10. You need to check the price before and after discount coincides
        // with the specified size of the discount.
        app.getProductPriceHelper().checkPricesAndDiscounts();
        utils.writeToFileAndConsole("9. and 10. ----- end:  ------ ");

    }
}
