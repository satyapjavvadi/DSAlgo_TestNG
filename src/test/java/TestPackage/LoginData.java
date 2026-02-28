package TestPackage;

import org.testng.annotations.DataProvider;

public class LoginData {
	
	@DataProvider(name = "loginLabels")
	public Object[][] loginLabels()
	{
		return new Object[][]
				{
				{"Username:"},
				{"Password:"}
				};
	}
	@DataProvider(name ="negativeData")
     public Object[][] getInvalidLogindata(){
    	 return new Object[][] {
    		 {"Null value in cred", "submits the login form", "username field"}, 
    		 {"Null value in cred", "presses Enter", "username field"}, 
    		 {"Null value in password", "submits the login form", "password field"}, 
    		 {"Null value in password", "presses Enter", "password field"}, 
    		 {"Null value in username", "submits the login form", "username field"}, 
    		 {"Null value in username", "presses Enter", "username field"}, 
    		 {"Invalid user", "initiates login", "alert"},  
    		 {"Invalid password", "initiates login", "alert"}, 
    	 };
     }
	
	@DataProvider(name = "validLoginData")
	public Object[][] validLoginData()
	{
		return new Object[][]
				{
					{"Submits the login form","valid_login"},
					{"confirms login using Enter","valid_login"}
			
				};
	}

}
