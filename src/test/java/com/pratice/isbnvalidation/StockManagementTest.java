package com.pratice.isbnvalidation;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StockManagementTest {

    @Test
    @Ignore
    public void canGetCorrectLocatorCode(){

        // Stub
        ExternalISBNDataService service = new ExternalISBNDataService() {
            @Override
            public Book lookup(String isbn) {
                return new Book(isbn, "Of Mice And Men", "J. Streinbeck");
            }
        };

        StockManager stockManager = new StockManager();
        stockManager.setDatabaseService(service);

        String isbn = "014077396";
        String locatorCode = stockManager.getLocatorCode(isbn);
        assertEquals("7396J4", locatorCode);
    }

    @Test
    public void databaseIsUsedIfDataIsPresent(){
        ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
        ExternalISBNDataService webService = mock(ExternalISBNDataService.class);

        when(databaseService.lookup("014077396")).thenReturn(new Book("014077396", "abc", "abc"));

        StockManager stockManager = new StockManager();
        stockManager.setDatabaseService(databaseService);
        stockManager.setWebService(webService);

        String isbn = "014077396";
        String locatorCode = stockManager.getLocatorCode(isbn);

        verify(databaseService, times(1)).lookup("014077396");
        verify(webService, times(0)).lookup(anyString());
    }

    @Test
    public void webserviceIsUsedIfDataIsNotPresentInDatabase(){

        ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
        ExternalISBNDataService webService = mock(ExternalISBNDataService.class);

        when(databaseService.lookup("014077396")).thenReturn(null);
        when(webService.lookup("014077396")).thenReturn(new Book("014077396", "abc", "abc"));

        StockManager stockManager = new StockManager();
        stockManager.setDatabaseService(databaseService);
        stockManager.setWebService(webService);

        String isbn = "014077396";
        String locatorCode = stockManager.getLocatorCode(isbn);

        verify(databaseService, times(1)).lookup("014077396");
        verify(webService, times(1)).lookup("014077396");

    }

}
