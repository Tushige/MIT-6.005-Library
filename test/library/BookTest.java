package library;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test suite for Book ADT.
 */
public class BookTest {

    /*
     * Testing strategy
     * ==================
     *
     * title.length: 0, 1, > 1
     * authors.length: 0, 1, > 1
     * year: <0, 0, >0
     */
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    /**************
     * Book() tests
     **************/

    @Test
    public void testBookOneAuthor() {
        String title = "Darwin's Radio";
        List<String> authors = Arrays.asList("Greg Bear");
        int year = 2000;
        Book b = new Book(title, authors, year);

        assertEquals("title is Darwin's Radio", title, b.getTitle());
        assertEquals("there is one author", 1, b.getAuthors().size());
        assertEquals("author is Greg Bear", authors.get(0), b.getAuthors().get(0));
        assertEquals("year is 2000", year, b.getYear());
    }
    @Test
    public void testBookMultipleAuthors() {
        String title = "How to make caldzone";
        String a1 = "Adam Scott";
        String a2 = "Ben W";
        String a3 = "Chris Traeger";
        List<String> authors = Arrays.asList(a1, a2, a3);
        int year = 2011;
        Book b = new Book(title, authors, year);

        assertEquals("title is How to make caldzone", title, b.getTitle());
        assertEquals("there are 3 authors", 3, b.getAuthors().size());
        assertTrue("author is Adam Scott", b.getAuthors().contains(a1));
        assertTrue("author is Ben W", b.getAuthors().contains(a2));
        assertTrue("author is Chris Traeger", b.getAuthors().contains(a3));
        assertEquals("year is 2011", year, b.getYear());
    }

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
