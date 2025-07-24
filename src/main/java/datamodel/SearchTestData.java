package datamodel;

public class SearchTestData {
    private String searchQuery;
    private int resultsCount;
    private String pageNumber;

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public void setResultsCount(String countStr) {
        this.resultsCount = Integer.parseInt(countStr);
    }

    public int getResultsCount() {
        return resultsCount;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }
}
