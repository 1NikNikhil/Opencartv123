package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import PageObjects.AccountRegistrationPage;
import PageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

    @Test(groups = {"Regression", "Master"})
    public void verify_account_registration() {

        logger.info("*** Starting TC001 Account Registration Test ***");

        try {
            HomePage hp = new HomePage(getDriver());
            hp.clickMyAccount();
            logger.info("Clicked on MyAccount Link");

            hp.clickRegister();
            logger.info("Clicked on Register Link");

            AccountRegistrationPage regpage = new AccountRegistrationPage(getDriver());

            logger.info("Providing customer details");
            regpage.setFirstName(randomeString().toUpperCase());
            regpage.setLastName(randomeString().toLowerCase());
            regpage.setEmail(randomeString() + "@gmail.com");
            regpage.setTelephone(randomeNumber());

            String password = randomeAlphaNumberic();
            regpage.setPassword(password);
            regpage.setConfirmPassword(password);

            regpage.setPrivacyPolicy();
            regpage.clickContinue();

            logger.info("Validating expected message");
            String confmsg = regpage.getConfirmationMsg();

            Assert.assertEquals(confmsg, "Your Account Has Been Created!");

        } catch (Exception e) {
            logger.error("Test Failed due to exception", e);
            Assert.fail();
        }

        logger.info("*** Finished TC001 Account Registration Test ***");
    }
}
