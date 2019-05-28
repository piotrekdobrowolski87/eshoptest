package pages.cart;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;


public class ProductFactory {

    private List<WebElement>names;
    private List<WebElement>prices;
    private List<WebElement> action;

    public List<ProductObject> createProductList(List<WebElement>names,List<WebElement>prices, List<WebElement>actionOnProduct){
        List<ProductObject> productList = new ArrayList<>();

        this.names = names;
        this.prices = prices;
        this.action = actionOnProduct;
        String name;
        String price;
        WebElement addToCart;

        if(checkAllLists() == true){
            for(int i = 0; i < names.size(); i++){
                name = names.get(i)
                        .getText();
                price = prices.get(i)
                        .getText()
                        .replace(" zł","")
                        .replace(",",".");
                addToCart = actionOnProduct.get(i);

                productList.add(new ProductObject(name,price,addToCart));
            }
        }
        return productList;
    }

    private boolean checkAllLists(){
        int namesSize = names.size();
        int pricesSize = prices.size();
        int addToCartButtonsSize = action.size();

        if(namesSize == pricesSize &&
                pricesSize == addToCartButtonsSize){
            return true;
        }
        else{
            return false;
        }
    }
}
