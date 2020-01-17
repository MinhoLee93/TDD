package com.pratice.isbnvalidation;

public class StockManager {

    // database service
    private ExternalISBNDataService databaseService;
    // web service
    private ExternalISBNDataService webService;

    // set database service
    public void setDatabaseService(ExternalISBNDataService databaseService){
        this.databaseService = databaseService;
    }

    // set seb service
    public void setWebService(ExternalISBNDataService webService){

        this.webService = webService;
    }

    public String getLocatorCode(String isbn) {
       // check database have book info
       Book book = databaseService.lookup(isbn);

       // database have no data -> then call web service
       if(book == null){
           book = webService.lookup(isbn);
       }

       StringBuilder locator = new StringBuilder();
       locator.append(isbn.substring(isbn.length() - 4));
       locator.append(book.getAuthor().substring(0,1));
       locator.append(book.getTitle().split(" ").length);

       return locator.toString();
    }
}
