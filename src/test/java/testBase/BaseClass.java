package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public Logger logger;
    public Properties p;

    public WebDriver getDriver() {
        return driver.get();
    }

    @BeforeClass(groups = { "Sanity", "Regression", "Master" })
    @Parameters({ "browser" })
    public void setup(String br) throws IOException {

        // Load config.properties
        FileReader file = new FileReader("./src/test/resources/config.properties");
        p = new Properties();
        p.load(file);

        logger = LogManager.getLogger(this.getClass());

        if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {

          URL gridUrl = URI.create("http://localhost:4444/wb/hub").toURL();

        	
        	

            switch (br.toLowerCase()) {

                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    driver.set(new RemoteWebDriver(gridUrl, chromeOptions));
                    break;

                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    driver.set(new RemoteWebDriver(gridUrl, firefoxOptions));
                    break;

                default:
                    throw new RuntimeException("Invalid browser name");
            }
        } else {
            throw new RuntimeException("Local execution not configured");
        }

        getDriver().manage().deleteAllCookies();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        getDriver().get(p.getProperty("appURL"));
        getDriver().manage().window().maximize();
    }

    @AfterClass(groups = { "Sanity", "Regression", "Master" })
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }

    // ---------- Utilities ----------

    public String randomeString() {
        return RandomStringUtils.randomAlphabetic(5);
    }

    public String randomeNumber() {
        return RandomStringUtils.randomNumeric(10);
    }

    public String randomeAlphaNumberic() {
        return RandomStringUtils.randomNumeric(3) + "@" +
               RandomStringUtils.randomAlphabetic(3);
    }

    public String captureScreen(String tname) throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        File sourceFile = ((TakesScreenshot) getDriver())
                .getScreenshotAs(OutputType.FILE);

        String targetPath = System.getProperty("user.dir")
                + File.separator + "screenshots"
                + File.separator + tname + "_" + timeStamp + ".png";

        File targetFile = new File(targetPath);
        sourceFile.renameTo(targetFile);

        return targetPath;
    }
}
