import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class Junit {
    WebDriver driver;
    @BeforeAll
public void setup(){
   driver=new ChromeDriver();
   driver.manage().window().maximize();
   driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
}
@DisplayName("Get website title")
@Test
public void getTitle(){
    driver.get("https://demoqa.com/");
    String titleActual= driver.getTitle();
    System.out.println(titleActual);
    String titleExpected = "DEMOQA";
    Assertions.assertTrue(titleActual.contains(titleExpected));
    Assertions.assertEquals(titleExpected,titleActual);
}
@Test
public void SubmitForm(){
        driver.get("https://demoqa.com/text-box");
       List<WebElement> fromControls=driver.findElements(By.className("form-control"));
       fromControls.get(0).sendKeys("Ishrat jahan");
       fromControls.get(1).sendKeys("ishrat@test.com");
       fromControls.get(2).sendKeys("Gulshan");
       fromControls.get(3).sendKeys("Dhaka");
Utils.scroll(driver,600);
driver.findElement(By.id("submit")).click();

 List<WebElement>resultElem = driver.findElements(By.tagName("p"));
    String nameActual= resultElem.get(0).getText();
    String emailActual= resultElem.get(1).getText();
    String currentAddressActual=resultElem.get(2).getText();
    String permanentAddressActual=resultElem.get(3).getText();


    String nameExpected="Ishrat";
    String emailExpected="ishrat@test.com";
    String currentAddressExpected="Gulshan";
    String permanentAddressExpected="Dhaka";

    Assertions.assertTrue(nameActual.contains(nameExpected));
    Assertions.assertTrue(emailActual.contains(emailExpected));
    Assertions.assertTrue(currentAddressActual.contains(currentAddressExpected));
    Assertions.assertTrue(permanentAddressActual.contains(permanentAddressExpected));

}
@Test
public void btnClick(){
       driver.get("https://demoqa.com/buttons");
    List<WebElement> button= driver.findElements(By.cssSelector(("[type=button]")));
    Actions action =new Actions(driver);
    action.doubleClick(button.get(1)).perform();
    action.contextClick(button.get(2)).perform();
    action.click(button.get(3)).perform();

    String actual_message=driver.findElement(By.id("doubleClickMessage")).getText();
    String actual_message2=driver.findElement(By.id("rightClickMessage")).getText();
    String expected_message= "You have done a double click";
    Assertions.assertTrue(actual_message.contains(expected_message));
    Assertions.assertTrue(actual_message.contains(expected_message));
}
@Test
public void alterClick() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
       // driver.findElement(By.id("timerAlterButton")).click();
        //Thread.sleep(5000);
       // driver.switchTo().alert();
        driver.findElement(By.id("confirmButton")).click();
        driver.switchTo().alert().accept();
}
@Test
public void selectDate(){
        driver.get("https://demoqa.com/date-picker");
        WebElement calenElem = driver.findElement(By.id("datePickerMonthYearInput"));
        calenElem.click();
        calenElem.sendKeys(Keys.CONTROL+"a",Keys.BACK_SPACE);
        calenElem.sendKeys(Keys.ENTER);

}
@Test
public void selectDropDown(){
        driver.get("https://demoqa.com/select-menu");
    Select select=new Select(driver.findElement(By.id("oldSelectMenu")));
    select.selectByIndex(3);

}
@Test
public void takeScreenShot() throws IOException {
    driver.get("https://www.digitalunite.com/practice-webform-learners");
    File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    String time = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss-aa").format(new Date());
    String fileWithPath = "./src/test/resources/screenshots/" + time + ".png";
    File DestFile = new File(fileWithPath);
    FileUtils.copyFile(screenshotFile, DestFile);
}
@Test
public void handleMultipleWindows() throws InterruptedException {
        driver.get("https://demoqa.com/browser-windows");
    driver.findElement(By.id("tabButton")).click();
    Thread.sleep(3000);
    ArrayList<String> w = new ArrayList(driver.getWindowHandles());
    //switch to open tab
    driver.switchTo().window(w.get(1));
    System.out.println("New tab title: " + driver.getTitle());
    String text = driver.findElement(By.id("sampleHeading")).getText();
    Assertions.assertEquals(text,"This is a sample page");
    driver.close();
}
@Test
public void handleChildWindows(){
        driver.get("https://demoqa.com/browser-windows");
    driver.findElement(By.id(("windowButton"))).click();
    String mainWindowHandle = driver.getWindowHandle();
    Set<String> allWindowHandles = driver.getWindowHandles();
    Iterator<String> iterator = allWindowHandles.iterator();

    while (iterator.hasNext()) {
        String ChildWindow = iterator.next();
        if (!mainWindowHandle.equalsIgnoreCase(ChildWindow)) {
            driver.switchTo().window(ChildWindow);
            String text= driver.findElement(By.id("sampleHeading")).getText();
            Assertions.assertTrue(text.contains("This is a sample page"));
        }
    }

    driver.close();
    driver.switchTo().window(mainWindowHandle);
}
@Test

public void scrapData(){
    driver.get("https://demoqa.com/webtables");
    WebElement table = driver.findElement(By.className("rt-tbody"));
    List<WebElement> allRows = table.findElements(By.className("rt-tr"));
    int i=0;
    for (WebElement row : allRows) {
        List<WebElement> columns = row.findElements(By.className("rt-td"));
        for (WebElement cell : columns) {
            i++;
            System.out.println("num["+i+"] "+ cell.getText());
        }
    }
}
@Test
public void handleIframe(){
        driver.get("https://demoqa.com/frames");
    driver.switchTo().frame("frame2");
    String text= driver.findElement(By.id("sampleHeading")).getText();
    Assertions.assertTrue(text.contains("This is a sample page"));
    driver.switchTo().defaultContent();
}


//@AfterAll
public void closeBrowser(){
        driver.quit();
}
}
