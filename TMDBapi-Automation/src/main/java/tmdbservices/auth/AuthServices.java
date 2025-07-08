package tmdbservices.auth;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.auth.CreateSessionResponse;
import pojo.auth.RequestTokenResponse;
import pojo.auth.ValidateTokenRequest;
import utils.ConfigManager;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AuthServices {
	
	//LogIn flow
	public static void ensureLoggedIn() {
        if (SessionManager.isSessionActive()) return;

        String baseUri = ConfigManager.getConfigProperties("base.uri");
        String apiKey = ConfigManager.getConfigProperties("api.key");
        String username = ConfigManager.getConfigProperties("username");
        String password = ConfigManager.getConfigProperties("password");

        RestAssured.baseURI = baseUri;

        // Step 1: Get Request Token
        Response tokenResponse = given()
                .queryParam("api_key", apiKey)
                .when()
                .get("/authentication/token/new");

        assertThat(tokenResponse.statusCode(), is(200));
        RequestTokenResponse tokenData = tokenResponse.as(RequestTokenResponse.class);
        assertThat(tokenData.isSuccess(), is(true));

        // Step 2: Validate login with token
        ValidateTokenRequest loginRequest = new ValidateTokenRequest(username, password, tokenData.getRequest_token());

        Response loginResponse = given()
                .queryParam("api_key", apiKey)
                .header("Content-Type", "application/json")
                .body(loginRequest)
                .when()
                .post("/authentication/token/validate_with_login");

        assertThat(loginResponse.statusCode(), is(200));
        assertThat(loginResponse.jsonPath().getBoolean("success"), is(true));

        // Step 3: Create Session
        Response createSessionResponse = given()
                .queryParam("api_key", apiKey)
                .header("Content-Type", "application/json")
                .body("{\"request_token\": \"" + tokenData.getRequest_token() + "\"}")
                .when()
                .post("/authentication/session/new");

        assertThat(createSessionResponse.statusCode(), is(200));
        CreateSessionResponse session = createSessionResponse.as(CreateSessionResponse.class);
        assertThat(session.isSuccess(), is(true));

        SessionManager.setSessionId(session.getSession_id());
    }
	
	//LogOut flow
	 public static void deleteSession() {
	        String baseUri = ConfigManager.getConfigProperties("base.uri");
	        String apiKey = ConfigManager.getConfigProperties("api.key");

	        String sessionId = SessionManager.getSessionId();
	        if (sessionId == null) return;
	        String deleteSessionRequestBody = "{\"session_id\": \"" + sessionId + "\"}";
	        
	        
	        
	        RestAssured.baseURI = baseUri;

	        Response deleteSessionResponse = given()
	                .queryParam("api_key", apiKey)
	                .header("Content-Type", "application/json")
	                .body(deleteSessionRequestBody)
	                .when()
	                .delete("/authentication/session");

	        assertThat(deleteSessionResponse.statusCode(), is(200));
	        assertThat(deleteSessionResponse.jsonPath().getBoolean("success"), is(true));

	        SessionManager.clearSession();
	    }
    
}