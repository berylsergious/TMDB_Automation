package testcases.accountApiTest;

import org.testng.annotations.Test;

import tmdbservices.auth.AuthServices;

public class AuthFlowTest {

	@Test
	public void login() {
		
		
		AuthServices.createRequestToken();
	    
	    
	}
	
	
}
