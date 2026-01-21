package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import PageObjects.HomePage;
import PageObjects.LoginPage;
import PageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {

    @Test(
        dataProvider = "LoginData",
        dataProviderClass = DataProviders.class,
        groups = "Datadriven"
    )
    public void verify_loginDDT(String email, String pwd, String exp) {

        logger.info("*************** Starting TC003_LoginDDT ***************");

        try {
            // HomePage
            HomePage hp = new HomePage(getDriver());
            hp.clickMyAccount();
            hp.clickLogin();

            // Login
            LoginPage lp = new LoginPage(getDriver());
            lp.setEmail(email);
            lp.setPassword(pwd);
            lp.clickLogin();

            // MyAccount
            MyAccountPage macc = new MyAccountPage(getDriver());
            boolean targetPage = macc.isMyAccountPageExists();

            if (exp.equalsIgnoreCase("Valid")) {

                if (targetPage) {
                    macc.clickLogout();
                    Assert.assertTrue(true);
                } else {
                    Assert.fail("Valid login failed");
                }

            } else if (exp.equalsIgnoreCase("Invalid")) {

                if (targetPage) {
                    macc.clickLogout();
                    Assert.fail("Invalid login succeeded");
                } else {
                    Assert.assertTrue(true);
                }
            }

        } catch (Exception e) {
            logger.error("Exception in TC003_LoginDDT", e);
            Assert.fail();
        }

        logger.info("*************** Finished TC003_LoginDDT ***************");
    }
}
