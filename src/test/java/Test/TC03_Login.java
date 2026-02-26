package Test;

import org.testng.annotations.Test;

import TestPackage.LoginData;

public class TC03_Login {
	
@Test(dataProvider ="negativeData",dataProviderClass = LoginData.class)
public void logindata_negativetest(String tc ,String type , String field) {
	
	
}

}
