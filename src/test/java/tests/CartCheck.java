package tests;

import common.CommonMethods;
import common.SetBrowser;
import pages.MainPage;
import pages.cart.CartMethods;
import pages.cart.CartPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CartCheck {

    private WebDriver driver;
    private MainPage mainPage;
    private CartMethods cartMethods;
    private CartPage cartPage;

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
    public void checkBasicCartState(){
        mainPage.checkIfCartIsEmpty();
    }

    @Test(priority = 3)
    public void addProductsToCart(){
        mainPage.closeLegalNote();
        cartMethods = new CartMethods(driver, mainPage);
        cartMethods.addProductsToCart();
    }

    @Test(priority = 4)
    public void goToCart(){
        cartMethods.goToCart();
        cartPage = new CartPage(driver);
        cartPage.checkUrl();
    }

    @Test(priority = 5)
    public void checkProductListInCart(){
        cartPage.checkProductsQuantity();
    }

    @Test(priority = 6)
    public void checkValuesAndSum(){
        cartPage.checkValueOfCart();
    }

    @Test(priority = 7)
    public void deleteProducts(){
        cartPage.deleteProducts();
    }

    @AfterTest(alwaysRun = true)
    public void closeDriver(){
        CommonMethods commonMethods = new CommonMethods(driver);
        commonMethods.log("Testy koszyka zakończone");
        driver.quit();
    }
}
