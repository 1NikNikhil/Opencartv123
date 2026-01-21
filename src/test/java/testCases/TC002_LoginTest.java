package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import PageObjects.HomePage;
import PageObjects.LoginPage;
import PageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {

    @Test(groups = {"Sanity", "Master"})
    public void verify_login() {

        logger.info("**** Starting TC002_LoginTest *****");

        try {
            // HomePage
            HomePage hp = new HomePage(getDriver());
            hp.clickMyAccount();
            hp.clickLogin();

            // Login
            LoginPage lp = new LoginPage(getDriver());
            lp.setEmail(p.getProperty("email"));
            lp.setPassword(p.getProperty("password"));
            lp.clickLogin();

            // MyAccount
            MyAccountPage macc = new MyAccountPage(getDriver());
            boolean targetPage = macc.isMyAccountPageExists();

            Assert.assertTrue(targetPage, "Login failed");

        } catch (Exception e) {
            logger.error("Login test failed", e);
            Assert.fail();
        }

        logger.info("**** Finished TC002_LoginTest *****");
    }
}
