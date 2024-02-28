package Assignement.Entrata;
import java.time.Duration;
import java.util.Iterator;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;
public class homepage {

	public static void main(String[] args) {

// TODO Auto-generated method stub

		WebDriverManager.chromedriver().setup();

		WebDriver driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.manage().window().maximize();

		driver.get("https://www.entrata.com/");

        // Accept Cookies of Webpage.
		driver.findElement(By.cssSelector("#rcc-decline-button")).click();

        /* Test 1 : Verify New Window get Title of Page*/
		driver.findElement(By.xpath("//a[text()='Base Camp']")).click();

		Set<String> x = driver.getWindowHandles();

		Iterator<String> a = x.iterator();

		String ParentWindow = a.next();

		String ChildWindow = a.next();

		driver.switchTo().window(ChildWindow);

		driver.findElement(By.cssSelector(".w-icon-nav-menu")).click();

		driver.findElement(By.cssSelector(".navigation-link:nth-child(5)")).click();

		String head = driver.getTitle();
        // Print Title of child Page
		System.out.println(head);

		driver.switchTo().window(ParentWindow);
		
        //Print Title of Parent Page
		System.out.println(driver.getTitle());

		
       /* Test2: Verify Login to User in sign-in Page */

		driver.findElement(By.cssSelector("*.outline-dark-button")).click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='main-header-logo']")));

		driver.findElement(By.xpath("//a[contains(.,'Resident Login')]")).click();

		driver.findElement(By.xpath("//div[contains(text(),'View the Website')]")).click();

		driver.findElement(By.cssSelector(".four-content a[href='#app-contact']")).click();


        /* Test3: Enter information for New User in Contact Form */

		driver.findElement(By.id("name")).sendKeys("james");

		driver.findElement(By.id("email")).sendKeys("james@gmail.com");

		driver.findElement(By.id("property_name")).sendKeys("jameshome");

		driver.findElement(By.id("property_url")).sendKeys("google.com");

       // Select option in Category
		
		WebElement category = driver.findElement(By.id("category"));

		Select selectOption = new Select(category);

		selectOption.selectByVisibleText("Inspections");

		driver.findElement(By.xpath("//textarea[@placeholder='Message']")).sendKeys("Thanks for submit");
		
      // Check Submit button is Enable or not
		Boolean match = driver.findElement(By.id("contact-submit")).isEnabled();
		
       // Verify button Enable
		Assert.assertTrue(match);


         /* Test 4: Verify Navigate Pages multiple window*/

        WebElement footerDriver = driver.findElement(By.cssSelector(".footer-nav"));

		int footervalues = footerDriver.findElements(By.tagName("a")).size();

		for (int i = 0; i < footervalues; i++) {

			String valueString = footerDriver.findElements(By.tagName("a")).get(i).getText();

			if (valueString.equalsIgnoreCase("Marketing")) {

				footerDriver.findElement(By.cssSelector("a[href*='marketing']")).click();

				Set<String> handle = driver.getWindowHandles();

				Iterator<String> it = handle.iterator();

				String parenttab = it.next();

				String childtap = it.next();

				driver.switchTo().window(childtap);

				String title = driver.getTitle();
				
				// Get Title of Test 1: Verify first title of chilwindow, if Match or not because its child window still open

				System.out.println(title);
                
				Assert.assertEquals(title, "FAQ");

				driver.switchTo().window(parenttab);

				String windowtitle = driver.getTitle();

				System.out.println(windowtitle);

				Assert.assertEquals(windowtitle, "Welcome to the Resident Portal App");
			}

		}

	}

}