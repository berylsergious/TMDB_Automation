package testcases.accountApiTest;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import tmdbservices.account.AddRemoveFavorites;
import tmdbservices.auth.AuthServices;
import utils.ExcelTestDataManager;

public class FavoritesTest {
	private static final Logger logger = LogManager.getLogger(FavoritesTest.class);
	
	@DataProvider(name = "favoriteMovies")
	public Object[][] getFavoriteMovies() throws Exception {
		logger.info("Initializing test data provider for favorites");
		
		try {
		logger.debug("Loading test data from Excel file");
	    List<Map<String, Object>> sheetData = new ExcelTestDataManager("testdata/testdata.xlsx")
	        .getSheetData("favorites"); // Worksheet name

	    // Convert to TestNG's Object[][]
	    Object[][] testData =  sheetData.stream()
	        .map(row -> new Object[]{
	            row.get("media_type"), 
	            row.get("media_id"), 
	            row.get("favorite")
	        })
	        .toArray(Object[][]::new);
		
	    logger.debug("Successfully prepared test data provider");
        return testData;
        
		}catch(Exception e) {
            logger.error("Failed to initialize test data provider", e);
            throw e;
        }    
	    
	}

	@BeforeClass
    public void setup() {
        logger.info("Initializing test session");
        AuthServices.ensureLoggedIn();
    }

	
	@Test(dataProvider = "favoriteMovies")
	public void testFavorites(String mediaType, int mediaId, boolean favorite) {	
		// Uses the same AddRemoveFavorites service
	    AddRemoveFavorites.addRemoveFavorites(mediaType, mediaId, favorite);
	}
	
	@AfterClass
    public void teardown() {
		
		 AuthServices.deleteSession();
		
	}

}
