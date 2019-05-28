package pages.cart;

import org.openqa.selenium.WebElement;

public class ProductObject {

    private String name;
    private double price;
    private WebElement actionOnProduct;

    public ProductObject(String name, String price, WebElement action){
        this.name = name;
        this.price = Double.parseDouble(price);
        this.actionOnProduct = action;
    }

    public String getName(){
        return this.name;
    }

    public double getPrice(){
        return this.price;
    }

    public WebElement actionOnProduct(){
        return this.actionOnProduct;
    }
}
