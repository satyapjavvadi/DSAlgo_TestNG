package TestPackage;

import org.testng.annotations.DataProvider;

public class RegisterData {

	@DataProvider(name = "Registertext")
	public Object[][] getLinkData() {
		return new Object[][] { { "Register" } };
	}

	@DataProvider(name = "LabelNames")
	public Object[][] getLabelNames() {
		return new Object[][] { { "Username:" }, { "Password:" }, { "Password confirmation:" } };
	}

	@DataProvider(name = "LinkNames")
	public Object[][] getLinkNames() {
		return new Object[][] { { "Register" }, { "Sign in" } };
	}

	@DataProvider(name = "passwordrules")
	public Object[][] getpasswordrules() {
		return new Object[][] { { "Your password cant be too similar to your other personal information" },
				{ "Your password must contain at least 8 characters" },
				{ "Your password cant be a commonly used password" }, { "Your password cant be entirely numeric" } };
	}

	@DataProvider(name = "Negativeregisterdata")
	public Object[][] getnegativeregister() {
		return new Object[][] {
				{ "Null value in  username for register", "submits the register form", "Please fill out this field." },
				{ "Null value in password for register", "submits the register form", "Please fill out this field." },
				{ "Null value in  confirm password for register", "submits the register form",
						"Please fill out this field." },
				{ "with specialcharacter password for register", "submits the register form",
						"Password does not match requirement" },
				{ "with password less then eight characters for register", "submits the register form",
						"Password does not match requirement" },
				{ "with mismatch password for register", "submits the register form",
						"password_mismatch:The two password fields didn’t match." },
				{ "with specialcharacter username for register", "submits the register form",
						"Username must be alphanumeric" },

		};
	}

	@DataProvider(name = "Validregisterdata")
	public Object[][] getvaidregisterdata() {
		return new Object[][] { { "valid_register", "submits the register form","New Account Created. You are logged in as" }

		};
	}

}
