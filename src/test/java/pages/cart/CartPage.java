package pages.cart;

import common.CommonMethods;
import pages.MainPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class CartPage {

    @FindBy(how = How.XPATH, using = "//h3[@class='cart-item__title']")
    private List<WebElement> productName;

    @FindBy(how = How.XPATH, using = "//span[@class='value']")
    private List<WebElement> productPrice;

    @FindBy(how = How.XPATH, using = "//a[@class='btn_action delete button--link-green']")
    private List<WebElement> deleteProduct;

    @FindBy(how = How.XPATH, using = "//div[@class='col col2 cart-total__value']")
    private WebElement valueOfCart;

    private By sum = By.xpath("//div[@class='col col2 cart-total__value']");

    private WebDriver driver;
    private CommonMethods commonMethods;
    private MainPage mainPage;
    private String url = "https://merlin.pl/cart/";
    private List<ProductObject> productList = new ArrayList<>();
    private CartMethods cartMethods;
    private int productIdToRemove = 4;

    public CartPage(WebDriver driver){
        this.driver = driver;
        commonMethods = new CommonMethods(this.driver);
        mainPage = new MainPage(this.driver);
        PageFactory.initElements(driver, this);
        createProductList();
    }

    public void checkUrl(){
        commonMethods.checkCurrentUrl(url);
        commonMethods.log("Sprawdzono adres strony koszyka");
    }

    public void checkProductsQuantity(){
        cartMethods = new CartMethods(driver,mainPage);
        int cartState = cartMethods.actualCartCounterState();
        int productsQuantity = productList.size();
        Assert.assertEquals(productsQuantity,cartState,"Jest wiecej produktów na liście niż w koszyku");
        commonMethods.log("Ilość produktów w koszyku i na liście sprawdzona");
    }

    public void checkValueOfCart(){
        double sumValue = 0;
        double productPrice;

        for(ProductObject product : productList){
            productPrice = product.getPrice();
            sumValue = sumValue + productPrice;
        }
        sumValue = Math.round(sumValue*100);
        sumValue = sumValue/100;

        String textValue = valueOfCart.getText()
                .replace(",",".")
                .replace(" zł", "");

        double actualValue = Double.parseDouble(textValue);

        Assert.assertEquals(actualValue,sumValue, "Suma wartości pozycji jest rózna od sumy");
        commonMethods.log("Wartości produktow sprawdzone");
    }

    public void deleteProduct(){
        int startProductListSize = productList.size();
        WebElement deleteAction = productList.get(productIdToRemove)
                .actionOnProduct();

        String oldValue = valueOfCart.getText();
        commonMethods.moveToElement(deleteAction);
        deleteAction.click();
        commonMethods.waitForPresence(sum,oldValue);

        createProductList();
        int newProductListSize = productList.size();
        int result = startProductListSize - newProductListSize;
        Assert.assertEquals(result,1,"Usunięto inną ilość produktów niż 1");
        commonMethods.log("Usuwanie produktów z koszytka sprawdzone");

        checkValueOfCart();
        commonMethods.log("Wartość koszyka po usuwaniu sprawdzona");
    }

    private void createProductList(){
        ProductFactory productFactory = new ProductFactory();
        productList = productFactory.createProductList(productName,productPrice,deleteProduct);
    }
}
