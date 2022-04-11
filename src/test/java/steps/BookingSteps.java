package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import staticdata.WebUrls;

import java.util.concurrent.TimeUnit;

public class BookingSteps {

    WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Given("User is on main screen")
    public void userIsOnMainScreen() {
        driver.get(WebUrls.BOOKING_URL);
    }

    @When("User enters the name of the {string} in the search field")
    public void userEntersTheNameOfTheInTheSearchField(String hotelName) {
        String inputHotelNameXpath = "//input[@name='ss']";
        driver.findElement(By.xpath(inputHotelNameXpath)).sendKeys(hotelName);
    }

    @And("User click {string} button")
    public void userClickButton(String searchButton) {
        String buttonXpath = String.format("//button[contains(., '%s')]", searchButton);
        driver.findElement(By.xpath(buttonXpath)).click();
    }

    @And("Searching {string} is displayed")
    public void searchingHotelIsDisplayed(String hotelName) {
        String hotelNameXpath = String.format("//a/div[contains(., '%s')]", hotelName);
        Assert.assertTrue(driver.findElement(By.xpath(hotelNameXpath)).isDisplayed(), "Hotel is not exist");
    }

    @Then("Page shows {string} name and its {string}")
    public void pageShowsNameAndItsRating(String hotelName, String currentRating) {
        String hotelNameXpath = String.format("//a/div[contains(., '%s')]", hotelName);
        String actualHotelName = driver.findElement(By.xpath(hotelNameXpath)).getText();
        String ratingXpath = String.format("//ancestor::div[@data-testid='property-card' and contains(.,'%s')]//div[contains(@aria-label,'Scored')]", hotelName);
        String actualRating = driver.findElement(By.xpath(ratingXpath)).getText();
        Assert.assertEquals(actualHotelName, hotelName, "Hotel is not exist");
        Assert.assertEquals(actualRating, currentRating, "Ratings is not match");
    }
}
