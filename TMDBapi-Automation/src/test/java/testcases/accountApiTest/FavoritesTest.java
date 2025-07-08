package testcases.accountApiTest;

import java.util.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import tmdbservices.account.AddRemoveFavorites;
import tmdbservices.auth.AuthServices;
import utils.ExcelTestDataManager;

public class FavoritesTest {
	
	@DataProvider(name = "favoriteMovies")
	public Object[][] getFavoriteMovies() throws Exception {
	    List<Map<String, Object>> sheetData = new ExcelTestDataManager("testdata/testdata.xlsx")
	        .getSheetData("favorites"); // Worksheet name

	    // Convert to TestNG's Object[][]
	    return sheetData.stream()
	        .map(row -> new Object[]{
	            row.get("media_type"), 
	            row.get("media_id"), 
	            row.get("favorite")
	        })
	        .toArray(Object[][]::new);
	}

	@Test(dataProvider = "favoriteMovies")
	public void testFavorites(String mediaType, int mediaId, boolean favorite) {
	    
		AuthServices.ensureLoggedIn();
		// Uses the same AddRemoveFavorites service
	    AddRemoveFavorites.addRemoveFavorites(mediaType, mediaId, favorite);
	    
	    AuthServices.deleteSession();
	}

}
