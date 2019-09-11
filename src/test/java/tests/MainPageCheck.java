package tests;

import common.CommonMethods;
import common.SetBrowser;
import pages.MainPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MainPageCheck {

    private WebDriver driver;
    private MainPage mainPage;
    private CommonMethods commonMethods;

    @BeforeTest(alwaysRun = true)
    @Parameters({"browser","url","width","height"})
    public void openMainPage(String browser, String url, int width, int height){
        System.out.println("[LOG] Przeglądarka: " + browser + ", rozdzielczość: " + width + " x " + height);
        driver = new SetBrowser().openBrowserWindow(browser,width,height,url);
    }

    @Test(priority = 1)
    public void mainPageUrl(){
        mainPage = new MainPage(driver);
        mainPage.checkCurrentUrl();
    }

    @Test(priority = 2)
    public void mainSectionsReview(){
        mainPage.checkMainPageSection();
    }

    @Test(priority = 3)
    public  void checkCart(){
        mainPage.checkIfCartIsEmpty();
    }

    //@Test(priority = 4)
    //public void checkAnimation(){
    //    mainPage.checkOwlAnimation();
    //}

    @AfterTest(alwaysRun = true)
    public void closeDriver(){
        commonMethods = new CommonMethods(driver);
        driver.quit();
        commonMethods.log("Testy strony głównej zakończone");
    }
}
