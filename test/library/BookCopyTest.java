package library;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

import java.util.Arrays;

/**
 * Test suite for BookCopy ADT.
 */
public class BookCopyTest {

    /*
     * Testing strategy
     * ==================
     *
     * book condition : GOOD, DAMAGED
     * test ADT methods separately
     */
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testBookCopy() {
        String title = "Darwin's Radio";
        List<String> authors = Arrays.asList("Greg Bear");
        int year = 2000;
        Book book = new Book(title, authors, year);

        BookCopy copy = new BookCopy(book);
        assertEquals(book, copy.getBook());
        assertEquals("initally book is in good condition", BookCopy.Condition.GOOD, copy.getCondition());
    }
    @Test
    public void testBookCopySetCondition() {
        String title = "Darwin's Radio";
        List<String> authors = Arrays.asList("Greg Bear");
        int year = 2000;
        Book book = new Book(title, authors, year);

        BookCopy copy = new BookCopy(book);
        assertEquals(book, copy.getBook());
        assertEquals("initally book is in good condition", BookCopy.Condition.GOOD, copy.getCondition());
        copy.setCondition(BookCopy.Condition.DAMAGED);
        assertEquals("book successfully changed to DAMAGED condition", BookCopy.Condition.DAMAGED, copy.getCondition());
    }

    @Test
    public void testBookCopyEquals() {
        String title = "Darwin's Radio";
        List<String> authors = Arrays.asList("Greg Bear");
        int year = 2000;
        Book book = new Book(title, authors, year);

        BookCopy copy = new BookCopy(book);
        BookCopy copy2 = new BookCopy(book);
        assertNotEquals("copy and copy2 are not equal book copies", copy, copy2);
    }

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
