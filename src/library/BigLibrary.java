package library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * BigLibrary represents a large collection of books that might be held by a city or
 * university library system -- millions of books.
 * 
 * In particular, every operation needs to run faster than linear time (as a function of the number of books
 * in the library).
 */
public class BigLibrary implements Library {

    // TODO: rep
    
    // TODO: rep invariant
    // TODO: abstraction function
    // TODO: safety from rep exposure argument
    
 // rep
    private Set<BookCopy> inLibrary;
    private Set<BookCopy> checkedOut;

    // rep invariant:
    //    the intersection of inLibrary and checkedOut is the empty set
    //
    // abstraction function:
    //    represents the collection of books inLibrary union checkedOut,
    //      where if a book copy is in inLibrary then it is available,
    //      and if a copy is in checkedOut then it is checked out

    // TODO: safety from rep exposure argument

    public BigLibrary() {
        inLibrary = new HashSet<>();
        checkedOut = new HashSet<>();
        checkRep();
    }

    // assert the rep invariant
    private void checkRep() {
        // same BookCopy cannot be both in inLibrary and checkedOut
        for (BookCopy copy : checkedOut) {
            assert !inLibrary.contains(copy);
        }
    }

    @Override
    public BookCopy buy(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("book cannot be null");
        }
        BookCopy newCopy = new BookCopy(book);
        inLibrary.add(newCopy);
        checkRep();
        return newCopy;
    }

    @Override
    public void checkout(BookCopy copy) {
        if (copy == null || !inLibrary.contains(copy)) {
            throw new IllegalArgumentException("book copy not in library");
        }
        inLibrary.remove(copy);
        checkedOut.add(copy);
        checkRep();
    }

    @Override
    public void checkin(BookCopy copy) {
        if (copy == null) {
            throw new IllegalArgumentException("book copy cannot be null");
        }
        if (!checkedOut.contains(copy)) {
            throw new IllegalArgumentException("book copy needs to be checked out");
        }
        checkedOut.remove(copy);
        inLibrary.add(copy);
        checkRep();
    }

    @Override
    public boolean isAvailable(BookCopy copy) {
        if (copy == null) {
            throw new IllegalArgumentException("book copy cannot be null");
        }
        return inLibrary.contains(copy);
    }

    @Override
    public Set<BookCopy> allCopies(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("book object cannot benull");
        }
        Set<BookCopy> allBookCopies = new HashSet<>();
        // get all available copies
        for (BookCopy copy : inLibrary) {
            if (copy.getBook().equals(book)) {
                allBookCopies.add(copy);
            }
        }
        // get all checkedOutCopies
        for (BookCopy copy : checkedOut) {
            if (copy.getBook().equals(book)) {
                allBookCopies.add(copy);
            }
        }
        return allBookCopies;
    }

    @Override
    public Set<BookCopy> availableCopies(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("book object cannot be null");
        }
        Set<BookCopy> availableCopies = new HashSet<>();
        // get all available copies
        for (BookCopy copy : inLibrary) {
            if (copy.getBook().equals(book)) {
                availableCopies.add(copy);
            }
        }
        return availableCopies;
    }

    @Override
    public List<Book> find(String query) {
        Set<Book> matchingBooksSet = new HashSet<Book>();
        for (BookCopy copy : inLibrary) {
            Book book = copy.getBook();
            String title = book.getTitle();
            // exact title match
            if ( (title.equals(query) || query.lastIndexOf(title) > -1) && !matchingBooksSet.contains(book)) {
                matchingBooksSet.add(book);
                continue;           
            }
            List<String> authors = book.getAuthors();
            for (String author : authors) {
                // exact author match
                if ( (author.equals(query) || query.lastIndexOf(author) > -1) && !matchingBooksSet.contains(book)) {
                    matchingBooksSet.add(book);
                    break;
                }
            }
        }
        List<Book> matchedBooks = new ArrayList<Book>(matchingBooksSet);
        Collections.sort(matchedBooks, new SortyByQueryMatch());
        return matchedBooks;
    }
    class SortyByQueryMatch implements Comparator<Book> {
        @Override
        public int compare(Book a, Book b) {
            if (a.getTitle().equals(b.getTitle()) && a.getAuthors().equals(b.getAuthors())) {
                return -a.getYear() + b.getYear();
            }
            return 0;
        }
    }
    @Override
    public void lose(BookCopy copy) {
        if (copy == null) {
            throw new IllegalArgumentException("book copy cannot be null");
        }
        inLibrary.remove(copy);
        checkedOut.remove(copy);
        checkRep();
    }

    // uncomment the following methods if you need to implement equals and hashCode,
    // or delete them if you don't
    // @Override
    // public boolean equals(Object that) {
    //     throw new RuntimeException("not implemented yet");
    // }
    // 
    // @Override
    // public int hashCode() {
    //     throw new RuntimeException("not implemented yet");
    // }


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
