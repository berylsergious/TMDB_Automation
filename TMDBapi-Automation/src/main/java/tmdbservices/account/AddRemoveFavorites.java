package tmdbservices.account;

import io.restassured.response.Response;
import utils.ConfigManager;
import utils.JsonTemplateManager;
import tmdbservices.auth.SessionManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AddRemoveFavorites {
    
	private static final Logger logger = LogManager.getLogger(AddRemoveFavorites.class);
	private static final String TEMPLATE_PATH = System.getProperty("user.dir") + "//src//test//resources//jasonPayload_Templates//Favorites.json";

    public static void addRemoveFavorites(String mediaType, int mediaId, boolean isFavorite) {
    	logger.info("Starting addRemoveFavorites operation - MediaType: {}, MediaID: {}, isFavorite: {}", 
                mediaType, mediaId, isFavorite);
    	try {
    	// 1. Load Configurations
        String baseUri = ConfigManager.getConfigProperties("base.uri");
        String apiKey = ConfigManager.getConfigProperties("api.key");
        String accountId = ConfigManager.getConfigProperties("account.id");
        String sessionId = SessionManager.getSessionId();

        logger.debug("Configuration loaded - BaseURI: {}, AccountID: {}", baseUri, accountId);
        
        // 2. Populate JSON Template
        logger.debug("Populating JSON template from path: {}", TEMPLATE_PATH);
        Map<String, Object> templateValues = new HashMap<>();
        templateValues.put("media_type", mediaType);
        templateValues.put("media_id", mediaId);
        templateValues.put("favorite", isFavorite);

        String requestBody = JsonTemplateManager.populateTemplate(TEMPLATE_PATH, templateValues);
        logger.debug("Request body populated: {}", requestBody);
        
        // 3. Send API Request
        logger.info("Sending API request to add/remove favorites");
        Response response = given()
            .baseUri(baseUri)
            .queryParam("api_key", apiKey)
            .queryParam("session_id", sessionId)
            .contentType("application/json")
            .body(requestBody)
            .when()
            .post("/account/" + accountId + "/favorite");
        
        // Log response details
        logger.debug("API Response - Status: {}, Body: {}", 
                     response.getStatusCode(), response.getBody().asString());
        logger.info("API request completed in {} ms", response.getTime());
             
        // 4. Assertions
        if (isFavorite== true) {  
            response.then()
                .statusCode(201)  // Addition case
                .body("success", equalTo(true))
                .body("status_code", equalTo(1));
        } else {
            response.then()
                .statusCode(200)  // Deletion case
                .body("success", equalTo(true))
                .body("status_code", equalTo(13));
        }
        logger.info("Successfully completed addRemoveFavorites operation");
    
    } catch (IOException e) {
    	logger.error("Failed to read JSON template from path: {}", TEMPLATE_PATH, e);
        throw new RuntimeException("Failed to read JSON template from path: " + TEMPLATE_PATH, e);
    } catch (Exception e) {
    	logger.error("Error in addRemoveFavorites: {}", e.getMessage(), e);
        throw new RuntimeException("Error in addRemoveFavorites: " + e.getMessage(), e);
    }
}
}