package tmdbservices.account;

import io.restassured.response.Response;
import utils.ConfigManager;
import utils.JsonTemplateManager;
import tmdbservices.auth.SessionManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AddRemoveFavorites {
    
	
	private static final String TEMPLATE_PATH = System.getProperty("user.dir") + "//src//test//resources//jasonPayload_Templates//Favorites.json";

    public static void addRemoveFavorites(String mediaType, int mediaId, boolean isFavorite) {
       
    	try {
    	// 1. Load Configurations
        String baseUri = ConfigManager.getConfigProperties("base.uri");
        String apiKey = ConfigManager.getConfigProperties("api.key");
        String accountId = ConfigManager.getConfigProperties("account.id");
        String sessionId = SessionManager.getSessionId();

        // 2. Populate JSON Template
        Map<String, Object> templateValues = new HashMap<>();
        templateValues.put("media_type", mediaType);
        templateValues.put("media_id", mediaId);
        templateValues.put("favorite", isFavorite);

        String requestBody = JsonTemplateManager.populateTemplate(TEMPLATE_PATH, templateValues);

        // 3. Send API Request
        Response response = given()
            .baseUri(baseUri)
            .queryParam("api_key", apiKey)
            .queryParam("session_id", sessionId)
            .contentType("application/json")
            .body(requestBody)
            .when()
            .post("/account/" + accountId + "/favorite");

        // 4. Assertions
        response.then()
            .statusCode(201)
            .body("success", equalTo(true));
    
    } catch (IOException e) {
        throw new RuntimeException("Failed to read JSON template from path: " + TEMPLATE_PATH, e);
    } catch (Exception e) {
        throw new RuntimeException("Error in addRemoveFavorites: " + e.getMessage(), e);
    }
}
}