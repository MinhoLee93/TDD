package com.pratice.isbnvalidation;

public interface ExternalISBNDataService {
    public Book lookup(String isbn);
}
