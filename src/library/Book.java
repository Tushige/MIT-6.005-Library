package library;

import java.lang.IllegalArgumentException;
import java.util.List;
import java.util.ArrayList;
/**
 * Book is an immutable type representing an edition of a book -- not the physical object,
 * but the combination of words and pictures that make up a book.  Each book is uniquely
 * identified by its title, author list, and publication year.  Alphabetic case and author
 * order are significant, so a book written by "Fred" is different than a book written by "FRED".
 */
public class Book {

    // rep
    private final String title;
    private final List<String> authors;
    private final int year;
    /**
     * REP INVARIANT
     * =============
     * @param title : String that contains at learst one non-space character
     * @param authors : Names of the authors of the book. Must be non-empty, and each
     *                  name must contain at least one non-space character.
     * @param year : Non-negative integer
     */

    /**
     * ABSTRACTION FUNCTION
     * ====================
     * represents an edition of a book that is uniquely identified by its title,
     * author list, and publication year.
     */
     
    /**
     * SAFETY FROM REP EXPOSURE
     * ========================
     * All fields are private.
     * title is a String, so it is guaranteed to be immutable.
     * authors is a list of String, so elements are guaranteed to be immutable.
     * authors is final, so it cannot be reassigned.
     * authors is mutable list, so getAuthors() performs defensive copying to avoid
     * sharing the representation with clients.
     * year is final, so it is guaranteed to be immutable
     */

    /**
     * Make a Book.
     * @param title Title of the book. Must contain at least one non-space character.
     * @param authors Names of the authors of the book.  Must have at least one name, and each name must contain
     * at least one non-space character.
     * @param year Year when this edition was published in the conventional (Common Era) calendar.  Must be nonnegative.
     */
    public Book(String title, List<String> authors, int year) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("title cannot be empty");
        }
        if (authors == null || authors.isEmpty()) {
            throw new IllegalArgumentException("authors cannot be empty");
        }
        if (year < 0) {
            throw new IllegalArgumentException("year cannot be negative integer");
        }
        this.title = title;
        this.authors = new ArrayList<>();
        this.authors.addAll(authors);
        this.year = year;
    }

    // assert the rep invariant
    private void checkRep() {
        assert title!=null && !title.isEmpty();
        assert authors!=null && !authors.isEmpty();
        assert year >= 0;
    }

    /**
     * @return the title of this book
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the authors of this book
     */
    public List<String> getAuthors() {
        List<String> authorCopy = new ArrayList<>();
        authorCopy.addAll(authors);
        return authorCopy;
    }

    /**
     * @return the year that this book was published
     */
    public int getYear() {
        return year;
    }

    /**
     * @return human-readable representation of this book that includes its title,
     *    authors, and publication year
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("title: " + title + "\n");
        sb.append("publication year: " + year +  "\n");
        for (String author : authors) {
            sb.append("author: " + author + "\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        else if (!(that instanceof Book)) return false;
        Book b2 = (Book) that;
        return this.title.equals(b2.title) && this.year == b2.year && this.authors.equals(b2.authors);
    }

    /**
     * hashing strategy taken from effective java : unit 9
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.title.hashCode();
        for (String author : authors) {
            result = 31 * result + author.hashCode();
        }
        result = result * 31 + year;
        return result;
    }



    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
