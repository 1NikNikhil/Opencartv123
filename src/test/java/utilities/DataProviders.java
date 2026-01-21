package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;
public class DataProviders {
	
	// DataProvider 1
    @DataProvider(name = "LoginData")
    public String[][] getData() throws IOException {

        // taking xl file from testData
        String path = ".\\testData\\Opencart_LoginData.xlsx";

        // creating an object for XLUtility
        ExcelUtility xlutil = new ExcelUtility(path);

        int totalrows = xlutil.getRowCount("Sheet1");
        int totalcols = xlutil.getCellCount("Sheet1", 1);

        // created two dimensional array
        String logindata[][] = new String[totalrows][totalcols];

        // read the data from xl storing in two dimensional array
        for (int i = 1; i <= totalrows; i++) {
            for (int j = 0; j < totalcols; j++) {
                logindata[i - 1][j] = xlutil.getCellData("Sheet1", i, j);
            }
        }

        return logindata; // returning two dimensional array
    }
}
