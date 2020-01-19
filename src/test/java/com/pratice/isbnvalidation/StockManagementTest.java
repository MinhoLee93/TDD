package com.pratice.isbnvalidation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StockManagementTest {

    // field
    private ExternalISBNDataService testDatabaseService;
    private ExternalISBNDataService testWebService;
    private StockManager stockManager;

    @BeforeEach
    public void setup(){
        // mock
        testDatabaseService = mock(ExternalISBNDataService.class);
        testWebService = mock(ExternalISBNDataService.class);

        //set service
        stockManager = new StockManager();
        stockManager.setDatabaseService(testDatabaseService);
        stockManager.setWebService(testWebService);

    }

    @Test
    public void canGetCorrectLocatorCode(){

        // set return value
        when(testDatabaseService.lookup(anyString())).thenReturn(null);
        when(testWebService.lookup(anyString())).thenReturn(new Book("014077396", "Of Mice And Men", "J. Streinbeck"));

        // do
        String isbn = "014077396";
        String locatorCode = stockManager.getLocatorCode(isbn);

        // assert
        assertEquals("7396J4", locatorCode);
    }

    @Test
    public void databaseIsUsedIfDataIsPresent(){

        // set return value
        when(testDatabaseService.lookup("014077396")).thenReturn(new Book("014077396", "abc", "abc"));

        // do
        String isbn = "014077396";
        String locatorCode = stockManager.getLocatorCode(isbn);

        // verify
        verify(testDatabaseService, times(1)).lookup("014077396");
        verify(testWebService, times(0)).lookup(anyString());
    }

    @Test
    public void webserviceIsUsedIfDataIsNotPresentInDatabase(){

        // set return value
        when(testDatabaseService.lookup("014077396")).thenReturn(null);
        when(testWebService.lookup("014077396")).thenReturn(new Book("014077396", "abc", "abc"));

        // do
        String isbn = "014077396";
        String locatorCode = stockManager.getLocatorCode(isbn);

        // verify
        verify(testDatabaseService, times(1)).lookup("014077396");
        verify(testWebService, times(1)).lookup("014077396");

    }

}
