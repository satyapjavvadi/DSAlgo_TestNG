package TestPackage;

import org.testng.annotations.DataProvider;

public class LoginData {
	
	
	@DataProvider(name ="negativeData")
     public Object[][] getInvalidLogindata(){
    	 return new Object[][] {
    		 { "Null value in cred", "submits the login form","username field"},
    		      {"Null value in cred","presses Enter","username field"}
    	 };
     }

}
