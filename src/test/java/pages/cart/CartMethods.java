package pages.cart;

import common.CommonMethods;
import pages.MainPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class CartMethods {

    WebDriver driver;
    MainPage mainPage;
    WebElement cartCounter;
    List<ProductObject> productObjectList;
    CommonMethods commonMethods;

    public CartMethods(WebDriver driver, MainPage mainPage){
        this.driver = driver;
        this.mainPage = mainPage;
        this.cartCounter = mainPage.getCartCouter();
        this.commonMethods = new CommonMethods(this.driver);
        createProductList();
    }

    public void addProductsToCart() {
        ProductObject product;
        WebElement productAddButton;
        WebElement goBackToMainPage = mainPage.getBackToShopAfterAddToCart();
        int promotedProductSize = productObjectList.size();

        for(int i = 0; i < promotedProductSize; i++){
            product = productObjectList.get(i);
            productAddButton = product.actionOnProduct();

            commonMethods.waitForViibility(productAddButton);
            commonMethods.moveToElement(productAddButton);

            productAddButton.click();

            commonMethods.waitForViibility(goBackToMainPage);
            goBackToMainPage.click();
        }

        int actualCounterState = actualCartCounterState();

        Assert.assertEquals(actualCounterState,promotedProductSize,"Ilość produktów w koszyku nie zgadza się z ilością kliknęć");
        commonMethods.log("Ilość produktów w koszyku sprawdzona");
    }

    public void goToCart() {
        WebElement cart = mainPage.getCart();
        commonMethods.waitForViibility(cart);
        cart.click();
    }

    public int actualCartCounterState(){
        String stateInText = mainPage.getCartCouter()
                .getText();
        stateInText = stateInText.substring(0,1);
        int cartState = Integer.parseInt(stateInText);

        return cartState;
    }

    private void createProductList(){
        this.mainPage = new MainPage(this.driver);
        ProductFactory productFactory = new ProductFactory();
        this.productObjectList = productFactory.createProductList(mainPage.getPromotedProducts1Names(), mainPage.getPromotedProducts1Price(),mainPage.getPromotedProducts1AddToCartButtons());
    }
}
