
package appmanager;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class TestBase  {

    public static ApplicationManager app = new ApplicationManager(BrowserType.FIREFOX);
    
    @BeforeSuite
    public void setUp() throws Exception {
        app.init();
    }
    
    @AfterSuite
    public void tearDown() throws Exception {
        app.stop();
    }

    
}
