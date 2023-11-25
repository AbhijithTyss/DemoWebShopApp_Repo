package shoppingcart;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.tricenties.basetest.BaseClass;
import com.tricenties.listenerutility.ListenerImplementation;
import com.tricenties.objectrepository.HomePage;
import com.tricenties.objectrepository.ShoppingCartPage;
import com.tricenties.objectrepository.WelComePage;

@Listeners(ListenerImplementation.class)
public class TC_DWS_002_Test extends BaseClass{
	@Test(groups = "system")
	public void addTocart() throws EncryptedDocumentException, IOException {
		hp=new HomePage(driver);
		hp.getAddToCartButtons().get(1).click();
		boolean productAddedMsg = hp.getProductAddedMsg().isDisplayed();
		Assert.assertEquals(productAddedMsg, true);
		test.log(Status.PASS, "Product added message has been displayed");
		wait.until(ExpectedConditions.invisibilityOf(hp.getProductAddedMsg()));
		wp=new WelComePage(driver);
		wp.getShoppingCartLink().click();
		String EXPECTED_TITLE = eLib.getDataFromExcel("ShoppingCart", 1, 0);
		Assert.assertEquals(driver.getTitle(), EXPECTED_TITLE);
		test.log(Status.INFO, "Shopping cart page is displayed");
		ShoppingCartPage sp=new ShoppingCartPage(driver);
		boolean productname = sp.getProductName().isDisplayed();
		Assert.assertEquals(productname, true);
		test.log(Status.PASS, "Product added to cart successfully");
	}
}
