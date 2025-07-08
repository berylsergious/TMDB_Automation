package testcases.accountApiTest;

import org.testng.annotations.Test;

import tmdbservices.auth.AuthServices;


public class AuthFlowTest {

	@Test(priority=1)
	public void login() {
		AuthServices.ensureLoggedIn();
	    
	}
	
	
	
	@Test(priority=2)
	public void logOut() {
		AuthServices.deleteSession();
	    
	}
	
	
}
