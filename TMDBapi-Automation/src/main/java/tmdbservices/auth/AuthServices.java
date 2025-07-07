package tmdbservices.auth;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.ConfigManager; // Make sure this path is correct for your project

public class AuthServices {

    public static String requestToken;

     
    // It's good practice to set the base URI globally if all your API calls
    // use the same base. You likely have this in a setup method or static block.
    // If not, you should add it as discussed in previous responses.
    static String baseUri = ConfigManager.getConfigProperties("base.uri");
    String apiKey = ConfigManager.getConfigProperties("api.key");
    String username = ConfigManager.getConfigProperties("username");
    String password = ConfigManager.getConfigProperties("password");
    


    public static void createRequestToken() {
    	 RestAssured.baseURI = baseUri;
            Response response = RestAssured
                    .given()
                    .header("accept", "application/json") // <-- HERE IS WHERE YOU ADD THE HEADER
                    .queryParam("api_key", ConfigManager.getConfigProperties("api.key"))
                    .get("/authentication/token/new");

            // It's crucial to check the response status code
         
                requestToken = response.jsonPath().getString("request_token");
                System.out.println("requestToken generated successfully: " + requestToken);
    }
    
}