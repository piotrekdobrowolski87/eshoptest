package common;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import java.time.Duration;
import java.util.List;

public class CommonMethods {

    private WebDriver driver;
    private Duration timeout = Duration.ofSeconds(10);
    private Duration pollingTime = Duration.ofMillis(500);

    public CommonMethods(WebDriver driver){
        this.driver = driver;
    }

    public void waitForPictureChange(WebElement element, String attribute, String attributeValue){
        waitFor().until(ExpectedConditions.not(ExpectedConditions.attributeContains(element,attribute,attributeValue)));
    }

    public void waitForViibility(WebElement element){
        waitFor().until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForClick(WebElement element){
        waitFor().until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForVisibilityOfCollection(List<WebElement> collectionOfElements){
        for(WebElement element : collectionOfElements){
            waitFor().until(ExpectedConditions.visibilityOf(element));
        }
    }

    public void waitForPresence(By locator,String text){
        waitFor().until(ExpectedConditions.not(ExpectedConditions.textToBe(locator,text)));
    }

    public void moveToElement(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
    }

    public void checkCurrentUrl(String url) {
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, url,"Adres strony nie zgadza się");
    }

    public void waitForUrl(String url){
        waitFor().until(ExpectedConditions.urlToBe(url));
    }

    public void log(String message){
        System.out.println("[LOG] " + message);
    }

    private Wait waitFor(){
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(timeout)
                .pollingEvery(pollingTime)
                .ignoring(NoSuchElementException.class);
        return wait;
    }
}
