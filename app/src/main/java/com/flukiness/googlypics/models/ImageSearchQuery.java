package com.flukiness.googlypics.models;

/**
 * Created by Jing Jin on 9/21/14.
 */
public class ImageSearchQuery {
    private static final String SEARCH_PREFIX = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=";
    private static final String NUM_PER_PAGE_PARAM = "&rsz=";
    private static final String START_PARAM = "&start=";

    public static final int MAX_NUM_PER_PAGE = 8;
    public static final int MAX_PAGES = 8; // Maximum # of pages the API returns

    public int numPerPage = MAX_NUM_PER_PAGE;
    public int page = 0;
    public String query;

    public String toString() {
        String str = SEARCH_PREFIX + query + NUM_PER_PAGE_PARAM + numPerPage;
        if (page > 0) {
            query += START_PARAM + (numPerPage * page);
        }
        return str;
    }

    public void resetQuery() {
        query = null;
        page = 0;
    }

    public void resetSettings() {
        numPerPage = MAX_NUM_PER_PAGE;
    }

    public boolean validate() {
        if (query == null || query.isEmpty())
            return false;
        if (numPerPage > MAX_NUM_PER_PAGE)
            return false;
        if (page > MAX_PAGES)
            return false;

        return true;
    }
}
