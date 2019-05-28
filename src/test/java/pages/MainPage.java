package pages;

import common.CommonMethods;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;

public class MainPage {

    @FindBy(how = How.XPATH, using = "//img[@class='b-header-main__logo']")
    private WebElement logo;

    @FindBy(how = How.XPATH, using = "//*[@class='b-header-main']")
    private WebElement header;

    @FindBy(how = How.XPATH, using = "//*[@id='main_menu']/div[@class='pn']")
    private WebElement mainMenu;

    @FindBy(how = How.XPATH, using = "//*[@id='main-container']//div[@class='owl-stage']/div")
    private List<WebElement> owlIetms;

    @FindBy(how = How.XPATH, using = "//div[1][@class='b-shops-shares']/ul[@class='b-shops-shares__list']/li[@class='b-shops-shares__list-item']")
    private List<WebElement> shopsSharesList;

    @FindBy(how = How.XPATH, using = "//div[@class='promoted-products__category b-products-wrap']")
    private List<WebElement> promotedProductsCategory;

    @FindBy(how = How.XPATH, using = "//a[@class='promoted-products-title']")
    private List<WebElement> promotedProductsCategoryNames;

    @FindBy(how = How.XPATH, using = "//button[@class='merlin-legal-note__close']")
    private WebElement closeLegalNote;

    @FindBy(how = How.XPATH, using = "//*[@class='b-header-main__cart-holder']")
    private WebElement cart;

    @FindBy(how = How.XPATH, using = "//span[@class='b-header-main__cart-goods quantity-text'][@id='header-cart-item-count-text']")
    private WebElement cartCouter;

    @FindBy(how = How.XPATH, using = "//div[@class='ui-dialog-buttonset']/button[1]")
    private WebElement backToShopAfterAddToCart;

    @FindBy(how = How.XPATH, using = "//div[@class='ui-dialog-buttonset']/button[2]")
    private WebElement goToCartAfterAddToCart;

    @FindBy(how = How.XPATH, using = "//div[@class='promoted-products__category b-products-wrap'][1]//li[@data-index]//a[@class='b-products-list__title product-link']")
    private List<WebElement> promotedProducts1Names;

    @FindBy(how = How.XPATH, using = "//div[@class='promoted-products__category b-products-wrap'][1]//li[@data-index]//div[@class='b-products-list__price-holder']//a[@class='b-products-list__price-current product-link']")
    private List<WebElement> promotedProducts1Price;

    @FindBy(how = How.XPATH, using = "//div[@class='promoted-products__category b-products-wrap'][1]//li[@data-index]//div[@class='b-products-list__price-holder']//button")
    private List<WebElement> promotedProducts1AddToCartButtons;


    private WebDriver driver;
    private CommonMethods commonMethods;
    private SoftAssert softAssert = new SoftAssert();
    private String url = "https://merlin.pl/";
    private int owlItemsToCheck = 3;
    private int expectedShopsSharesList = 3;
    private String emptyCart = "pusty";
    private List<String> promotedProductsCategoryNamesValues = new ArrayList<>();

    public MainPage(WebDriver driver) {
        this.driver = driver;
        commonMethods = new CommonMethods(this.driver);
        PageFactory.initElements(driver, this);
    }


    public void checkCurrentUrl() {
        commonMethods.checkCurrentUrl(url);
    }

    public void checkMainPageSection() {
        setCategoryNames();

        //sprawdzenie widocznosci nagłówków
        commonMethods.waitForViibility(logo);
        commonMethods.waitForViibility(header);
        commonMethods.waitForViibility(mainMenu);

        //sprawdzam widoczność koszyka
        commonMethods.waitForViibility(cart);
        commonMethods.waitForViibility(cartCouter);

        //sprawdzenie widoczności shareShopsList
        commonMethods.waitForVisibilityOfCollection(shopsSharesList);
        int actualShopsSharesList = shopsSharesList.size();
        softAssert.assertEquals(actualShopsSharesList, expectedShopsSharesList, "Ilość promocyjnych linków inna od oczekiwanej");

        //sprawdzenie promoted_products_category
        int actualPromotedCaterory = promotedProductsCategory.size();
        int actualPromotedCategoryNames = promotedProductsCategoryNames.size();
        int expectedSize = promotedProductsCategoryNamesValues.size();
        softAssert.assertEquals(actualPromotedCategoryNames, expectedSize, "Wyświetla się za dużo nazw kategorii promocyjnych");
        softAssert.assertEquals(actualPromotedCaterory, expectedSize, "Wyświetla się za dużo kategorii promocoyjnych");

        //sprawdzenie sekcji promotedProducts
        for (int i = 0; i < expectedSize; i++) {
            WebElement category = promotedProductsCategory.get(i);
            String expectedName = promotedProductsCategoryNamesValues.get(i);
            String actualName = promotedProductsCategoryNames.get(i)
                    .getText();
            commonMethods.waitForViibility(promotedProductsCategoryNames.get(i));
            commonMethods.waitForViibility(category);

            commonMethods.moveToElement(promotedProductsCategory.get(i));
            softAssert.assertEquals(actualName, expectedName, "Nazawa kategorii jest nie zgodna");
        }
        softAssert.assertAll();
        commonMethods.log("Główne elementy sklepu sprawdzone");
    }

    public void checkIfCartIsEmpty(){
        commonMethods.moveToElement(cartCouter);
        String actualCartState = cartCouter.getText();

        Assert.assertEquals(emptyCart,actualCartState,"Koszyk nie jest pusty a powinien być");
        commonMethods.log("Koszyk jest pusty");
    }

    public void checkOwlAnimation() {
        //sprawdzanie animiacji OwlItem
        for (int i = 0; i < owlItemsToCheck; i++) {
            String active;

            for (WebElement owlItem : owlIetms) {
                commonMethods.moveToElement(owlItem);
                active = owlItem.getAttribute("class");
                if (active.contains("active")) {
                    commonMethods.waitForPictureChange(owlItem, "class", "active");
                    break;
                }
            }
        }
        commonMethods.log("Animacja sprawdzona");
    }

    public WebElement getLogo(){
        return logo;
    }

    public WebElement getCart(){
        return cart;
    }

    public WebElement getCartCouter(){
        return cartCouter;
    }

    public WebElement getBackToShopAfterAddToCart(){
        return backToShopAfterAddToCart;
    }

    public WebElement getGoToCartAfterAddToCart(){
        return goToCartAfterAddToCart;
    }

    public List<WebElement> getPromotedProducts1Names(){
        return promotedProducts1Names;
    }

    public List<WebElement> getPromotedProducts1Price(){
        return promotedProducts1Price;
    }

    public List<WebElement> getPromotedProducts1AddToCartButtons(){
        return promotedProducts1AddToCartButtons;
    }

    public void closeLegalNote(){
        commonMethods.waitForViibility(closeLegalNote);
        commonMethods.waitForClick(closeLegalNote);
        closeLegalNote.click();
    }

    private void setCategoryNames() {
        promotedProductsCategoryNamesValues.add("Wszystkie kategorie");
        promotedProductsCategoryNamesValues.add("Książki dla dzieci");
        promotedProductsCategoryNamesValues.add("Biografie");
        promotedProductsCategoryNamesValues.add("Poradniki");
        promotedProductsCategoryNamesValues.add("Pop");
        promotedProductsCategoryNamesValues.add("Rock");
        promotedProductsCategoryNamesValues.add("Filmy dla dzieci");
        promotedProductsCategoryNamesValues.add("Blu Ray");
        promotedProductsCategoryNamesValues.add("Gry");
        promotedProductsCategoryNamesValues.add("Perfumy dla kobiet");
    }
}
