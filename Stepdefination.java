package StepDefs;

import Utills.BaseTest;
import Utills.DriverFactory;
import Utills.Testcontext;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Log4j2
public class Stepdefination extends BaseTest {
    Scenario scenario;
    Testcontext testcontext;

    public Stepdefination(Testcontext testcontext){
        this.testcontext = testcontext;
    }

    @Before
    public void setUp(Scenario scenario) {
        this.scenario = scenario;
        scenario.log("executed before step");
    }

    @Given("user operated the browser")
    public void user_operated_the_browser() {
     // String browserName= System.getProperty("browser");
        testcontext.setDriver(DriverFactory.createInstance("firefox"));
        testcontext.getDriver().manage().window().maximize();
        log.debug("Browser Maximized");
        testcontext.getDriver().manage().deleteAllCookies();
        log.debug("delete all cookies");
        testcontext.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        log.debug("Each step hook is executed,screen shot taken");
    }

    @Given("user navigate to the application url")
    public void user_navigate_to_the_application_url() {
        testcontext.getDriver().get(url);
        log.debug("manage url");
    }

    @When("user enter username as {string} and password as {string} and click")
    public void user_enter_username_as_and_password_as_and_click(String username, String password) {
        testcontext.getDriver().findElement(By.xpath("//input[@name= 'username']")).sendKeys(username);
        testcontext.getDriver().findElement(By.xpath("//input[@name= 'password']")).sendKeys(password);
        testcontext.getDriver().findElement(By.xpath("//input[@class= 'button']")).click();
    }

    @Then("user is able to login in the application")
    public void user_is_able_to_login_in_the_application() {
        Assert.assertEquals("ParaBank | Accounts Overview",testcontext.getDriver().getTitle());
        boolean accountTableDisplayed =testcontext.getDriver().findElement(By.id("accountTable")).isDisplayed();
        Assert.assertTrue(accountTableDisplayed);
    }

    @Given("User is logged In")
    public void user_is_logged_in() {
        user_operated_the_browser();
        user_navigate_to_the_application_url();
        user_enter_username_as_and_password_as_and_click(username, password);
    }

    @Given("User clicked on link {string}")
    public void User_clicked_on_link(String linkName) {
        testcontext.getDriver().findElement(By.linkText(linkName)).click();
        log.debug("Link Clicked.Link Name" + linkName);
    }

    @When("User select account as {string} and select a existing account number")
    public void User_select_account_as_and_select_a_existing_account_number(String type) {
        WebElement dropdownAccType =testcontext.getDriver().findElement(By.id("type"));
        Select selectAccType = new Select(dropdownAccType);
        selectAccType.selectByVisibleText(type);

        WebElement dropdownAccNumber = testcontext.getDriver().findElement(By.id("fromAccountId"));
        Select selectAccNumber = new Select(dropdownAccNumber);
        selectAccNumber.selectByIndex(0);
    }

    @And("User click on Button {string}")
    public void User_click_on_Button(String string) {
        testcontext.getDriver().findElement(By.xpath("//input[@class='button' and @value='Open New Account']")).click();
    }

    @Then("Account Opened Message is Display")
    public void Account_Opened_Message_is_Display() {
        WebElement element =testcontext.getDriver().findElement(By.id("newAccountId"));
        Assert.assertEquals(element.isDisplayed(), true, "Account Success Message");
    }

    @And("A new account number is generated")
    public void A_new_account_number_is_generated() {
        WebElement element = testcontext.getDriver().findElement(By.id("newAccountId"));
        String accountNumber = element.getText();
        Assert.assertEquals(element.isDisplayed(), true, "New account number link");
        scenario.log("New Account Number Generated As: " + accountNumber);
    }

    @Given("User able to logged In")
    public void user_able_to_logged_in() {
        user_operated_the_browser();
        user_navigate_to_the_application_url();
        user_enter_username_as_and_password_as_and_click(username, password);
    }

    @Given("User clicked on link Transfer Funds")
    public void User_clicked_on_link_Transfer_Funds() {
        testcontext.getDriver().findElement(By.xpath("//a[text()='Transfer Funds']")).click();
    }

    @When("User enter amount to transfer")
    public void User_enter_amount_to_transfer() throws InterruptedException {
        Thread.sleep(2000);
        testcontext.getDriver().findElement(By.id("amount")).sendKeys("5000");
    }

    @When("User select first account number from which to transfer and another account number in which to be transfer")
    public void User_select_first_account_number_from_which_to_transfer_and_another_account_number_in_which_to_be_transfer() throws InterruptedException {
        WebElement dropdownAccType = testcontext.getDriver().findElement(By.id("fromAccountId"));
        Select FromAcc = new Select(dropdownAccType);
        FromAcc.selectByIndex(0);
        WebElement dropdownAccType1 = testcontext.getDriver().findElement(By.id("toAccountId"));
        Select ToAcc = new Select(dropdownAccType1);
        ToAcc.selectByIndex(0);
    }

    @When("User click on TRANSFER Button")
    public void user_click_on_TRANSFER_Button() {
        testcontext.getDriver().findElement(By.xpath("//input[@value = 'Transfer']")).click();
    }

    @Then("Message is display Amount is transfer successfully")
    public void Message_is_display_Amount_is_transfer_successfully() {
        WebElement element = testcontext.getDriver().findElement(By.xpath("//h1[text()='Transfer Complete!']"));
        Assert.assertEquals(element.isDisplayed(), true, "Amount is transfer successfully message");
    }

    @Given("User clicked on bill pay link")
    public void User_clicked_on_bill_pay_link() {
        testcontext.getDriver().findElement(By.linkText("Bill Pay")).click();
    }

    @When("User enter Payee Name as {string} and enter Address as {string} and City as {string}")
    public void user_enter_payee_name_as_and_enter_address_as_and_city_as(String username, String string2, String string3) {
        testcontext.getDriver().findElement(By.xpath("//input[@name ='payee.name']")).sendKeys(username);
        testcontext.getDriver().findElement(By.xpath("//input[@name ='payee.address.street']")).sendKeys(string2);
        testcontext.getDriver().findElement(By.xpath("//input[@name ='payee.address.city']")).sendKeys(string3);
    }

    @When("User enter State as {string} and enter Zipcode as {string} and Phone No as {string}")
    public void user_enter_state_as_and_enter_zipcode_as_and_phone_no_as(String string, String string2, String string3) {
        testcontext.getDriver().findElement(By.xpath("//input[@name ='payee.address.state']")).sendKeys(string);
        testcontext.getDriver().findElement(By.xpath("//input[@name ='payee.address.zipCode']")).sendKeys(string2);
        testcontext.getDriver().findElement(By.xpath("//input[@name ='payee.phoneNumber']")).sendKeys("1234567891");
    }

    @When("User enter Account number as {string} and enter verify account as {string} and amount as {string}")
    public void user_enter_account_number_as_and_enter_verify_account_as_and_amount_as(String string, String string2, String string3) {
        testcontext.getDriver().findElement(By.xpath("//input[@name ='payee.accountNumber']")).sendKeys(string);
        testcontext.getDriver().findElement(By.xpath("//input[@name ='verifyAccount']")).sendKeys(string2);
        testcontext.getDriver().findElement(By.xpath("//input[@name ='amount']")).sendKeys(string3);
        Select AcNum = new Select(testcontext.getDriver().findElement(By.name("fromAccountId")));
        AcNum.selectByIndex(0);
    }

    @When("User licked on SEND PAYMENT button")
    public void user_licked_on_send_payment_button() {
        testcontext.getDriver().findElement(By.xpath("//input[@value = 'Send Payment']")).click();
    }

    @Then("Bill Payment is completed success Message is display")
    public void bill_payment_is_completed_success_message_is_display() throws InterruptedException {
        Thread.sleep(2000);
        WebElement element = testcontext.getDriver().findElement(By.xpath("//h1[@class ='title' and text() = 'Bill Payment Complete']"));
        Assert.assertEquals(element.isDisplayed(), true, "Message is displayed");
    }

    @Given("user is Login")
    public void user_is_login() {
        user_operated_the_browser();
        user_navigate_to_the_application_url();
        user_enter_username_as_and_password_as_and_click(username, password);
    }

    @Given("User click on link {string}")
    public void user_click_on_link(String string) {
        testcontext.getDriver().findElement(By.linkText("Request Loan")).click();
    }

    @Then("User Enter Loan Amount {string}")
    public void user_enter_loan_amount(String string) {
       testcontext.getDriver().findElement(By.id("amount")).sendKeys("50000");
    }

    @Then("User Enter down Payment {string} And From Account No")
    public void user_enter_down_payment_and_from_account_no(String string) {
        testcontext.getDriver().findElement(By.id("downPayment")).sendKeys("2000");
        Select AcNum = new Select(testcontext.getDriver().findElement(By.id("fromAccountId")));
        AcNum.selectByIndex(0);
    }

    @Then("User Click the Apply Button And {string}")
    public void user_click_the_apply_button_and(String string) throws InterruptedException {
        testcontext.getDriver().findElement(By.xpath("//input[@value = 'Apply Now']")).click();
        Thread.sleep(2000);
        WebElement element =  testcontext.getDriver().findElement(By.xpath("//h1[@class ='title']"));
        Assert.assertEquals(element.isDisplayed(), true,"Message is display" );
    }

    //data feature
    @Given("I want to do something")
    public void i_want_to_do_something() {
    }

    @When("i have a argument to send as {string}")
    public void i_have_a_argument_to_send_as(String arg) {
        System.out.println("Printing the Argument: " + arg);
    }

    @When("I haven a list of items to send")
    public void i_haven_a_list_of_items_to_send(List<String> list) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        System.out.println(list.toString());
    }

    @When("I have students and their marks")
    public void i_have_students_and_their_marks(Map<String, String> map) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        System.out.println(map);
    }

    @Then("something should happen")
    public void something_should_happen() {
    }

    @Given("I am on the search page")
    public void i_am_on_the_search_page() {
    }

    @When("I search for a product as {string}")
    public void i_search_for_a_product_as(String string) {
        System.out.println("product searched: " + string);
    }

    @Then("result should be display related to {string}")
    public void result_should_be_display_related_to(String string) {
        System.out.println("product search sucessing:" + string);
    }
}