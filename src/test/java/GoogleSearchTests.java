
import datamodel.SearchTestData;
import pages.LandingPage;
import org.testng.annotations.Test;
import pages.ResultsPage;
import utils.JSONDataReader;
import utils.Validators;


public class GoogleSearchTests extends BaseTest {

        @Test(description = "Search for 'Systems Egypt' on Google and compare results count on P2 & P3")
        public void testOne() throws Exception {
            LandingPage landingPage = new LandingPage(DriverManager.getDriver());
            ResultsPage resultsPage = new ResultsPage(DriverManager.getDriver());
            SearchTestData testData = JSONDataReader.read("TC_001", "GoogleSearchTests", SearchTestData.class);
            int resultCountPage2 = landingPage
                    .search(testData.getSearchQuery())
                    .navigateToResultsPage("2")
                    .getResultsCount();

            int resultCountPage3 = resultsPage.navigateToResultsPage("3").getResultsCount();

            Validators.assertEquals(resultCountPage2, resultCountPage3, "Mismatch in results count between Page 2 and Page 3");

        }
    }

