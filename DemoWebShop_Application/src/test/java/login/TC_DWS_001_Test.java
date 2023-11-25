package login;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.tricenties.basetest.BaseClass;
import com.tricenties.listenerutility.ListenerImplementation;
import com.tricenties.objectrepository.HomePage;

@Listeners(ListenerImplementation.class)
public class TC_DWS_001_Test extends BaseClass{
	@Test(groups = "system")
	public void loginTest() {
		hp=new HomePage(driver);
		boolean logoutStatus = hp.getLogoutLink().isDisplayed();
		Assert.assertEquals(logoutStatus, true);
		test.log(Status.PASS, "User logged in successfully");
	}
}
