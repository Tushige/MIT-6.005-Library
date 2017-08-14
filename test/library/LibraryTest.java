package library;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test suite for Library ADT.
 */
@RunWith(Parameterized.class)
public class LibraryTest {

    /*
     * Note: all the tests you write here must be runnable against any
     * Library class that follows the spec.  JUnit will automatically
     * run these tests against both SmallLibrary and BigLibrary.
     */

    /**
     * Implementation classes for the Library ADT.
     * JUnit runs this test suite once for each class name in the returned array.
     * @return array of Java class names, including their full package prefix
     */
    @Parameters(name="{0}")
    public static Object[] allImplementationClassNames() {
        return new Object[] {
            "library.SmallLibrary",
            "library.BigLibrary"
        };
    }

    /**
     * Implementation class being tested on this run of the test suite.
     * JUnit sets this variable automatically as it iterates through the array returned
     * by allImplementationClassNames.
     */
    @Parameter
    public String implementationClassName;

    /**
     * @return a fresh instance of a Library, constructed from the implementation class specified
     * by implementationClassName.
     */
    public Library makeLibrary() {
        try {
            Class<?> cls = Class.forName(implementationClassName);
            return (Library) cls.newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    /*
     * Testing strategy
     * ==================
     *
     * test each Library method separately
     * buy(book) : book is new, book already exists in library
     * checkout : copy is 'available', or 'checked out'
     * isAvailable : true, false
     * allCopies : zero copies, > 1 copies
     * availableCopies : zero available, 1 available, > 1 available
     * find : empty match, 1 match, >1 match
     *        query is title (exact match)
     *        query is author (exact match)
     *        query contains title (not exact match)
     *        query contains author (not exact match)
     *        multiple books with same title but different publication dates
     *        multiple books with same author but different publication dates
     *        test that returned list has no duplicates
     *
     */
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testExampleTest() {
        Library library = makeLibrary();
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        assertEquals(Collections.emptySet(), library.availableCopies(book));
    }
    /**************
     * buy() tests
     **************/
    @Test
    public void testBuyNewBookInGoodCondition() {
        String title = "Darwin's Radio";
        List<String> authors = Arrays.asList("Greg Bear");
        int year = 2000;
        Book book = new Book(title, authors, year);
        Library library = makeLibrary();
        BookCopy boughtCopy = library.buy(book);

        assertEquals("bought copy is GOOD",BookCopy.Condition.GOOD, boughtCopy.getCondition());
    }
    /**************
     * checkout() tests
     **************/
    @Test
    public void testCheckout() {
        String title = "Darwin's Radio";
        List<String> authors = Arrays.asList("Greg Bear");
        int year = 2000;
        Book book = new Book(title, authors, year);
        Library library = makeLibrary();
        BookCopy copy = library.buy(book);
        library.checkout(copy);
        assertFalse("Darwin's radio is checked out", library.isAvailable(copy));
    }
    /**************
     * allCopies() tests
     **************/
    @Test
    public void testAllCopiesEmpty() {
        String title = "Darwin's Radio";
        List<String> authors = Arrays.asList("Greg Bear");
        int year = 2000;
        Book book = new Book(title, authors, year);
        Library library = makeLibrary();
        Set<BookCopy> copies = library.allCopies(book);
        assertTrue("library has no copies of Darwin", copies.isEmpty());
    }
    @Test
    public void testAllCopiesMultipleCopies() {
        String title = "Darwin's Radio";
        List<String> authors = Arrays.asList("Greg Bear");
        int year = 2000;
        Book book = new Book(title, authors, year);

        Library library = makeLibrary();
        BookCopy copy1 = library.buy(book);
        BookCopy copy2 = library.buy(book);
        BookCopy copy3 = library.buy(book);
        Set<BookCopy> copies = library.allCopies(book);

        assertEquals("there are 3 copies of Darwin", 3, copies.size());
        assertTrue("library has Darwin copy 1", copies.contains(copy1));
        assertTrue("library has Darwin copy 2", copies.contains(copy2));
        assertTrue("library has Darwin copy 3", copies.contains(copy3));
    }
    /*************************
     * availableCopies() tests
     *************************/
    @Test
    public void testAvailableCopiesEmpty() {
        String title = "Darwin's Radio";
        List<String> authors = Arrays.asList("Greg Bear");
        int year = 2000;
        Book book = new Book(title, authors, year);

        Library library = makeLibrary();
        BookCopy copy1 = library.buy(book);
        BookCopy copy2 = library.buy(book);
        BookCopy copy3 = library.buy(book);
        library.checkout(copy1);
        library.checkout(copy2);
        library.checkout(copy3);
        Set<BookCopy> copies = library.availableCopies(book);

        assertEquals("there are 0 available copies", 0, copies.size());
    }
    /*************************
     * find() tests
     *************************/
    @Test
    public void testFindEmptyLibrary() {
        String title = "Darwin's Radio";
        List<String> authors = Arrays.asList("Greg Bear");
        int year = 2000;
        Book book = new Book(title, authors, year);

        Library library = makeLibrary();
        List<Book> matchedBooks = library.find("non-existent book");
        assertTrue("expected no match", matchedBooks.isEmpty());
    }
    @Test
    public void testFindEmptyMatch() {
        String title = "Darwin's Radio";
        List<String> authors = Arrays.asList("Greg Bear");
        int year = 2000;
        Book book = new Book(title, authors, year);

        Library library = makeLibrary();
        library.buy(book);
        library.buy(book);
        library.buy(book);
        List<Book> matchedBooks = library.find("non-existent book");
        assertTrue("expected no match", matchedBooks.isEmpty());
    }
    @Test
    public void testFindExactTitleMatchSingle() {
        String title = "Darwin's Radio";
        List<String> authors = Arrays.asList("Greg Bear");
        int year = 2000;
        Book book = new Book(title, authors, year);

        Library library = makeLibrary();
        library.buy(book);
        library.buy(book);
        library.buy(book);
        List<Book> matchedBooks = library.find("Darwin's Radio");
        assertEquals("expected 1 match", 1, matchedBooks.size());
        assertTrue("expected Darwin", matchedBooks.contains(book));
    }
    @Test
    public void testFindExactTitleMatchMultiple() {
        String title = "Darwin's Radio";
        String title2 = "Darwin's Radio";
        List<String> authors = Arrays.asList("Greg Bear");
        List<String> authors2 = Arrays.asList("Patrick S");
        int year = 2000;
        Book book = new Book(title, authors, year);
        Book book2 = new Book(title2, authors2, year);
        Library library = makeLibrary();
        library.buy(book);
        library.buy(book2);
        library.buy(book);
        List<Book> matchedBooks = library.find("Darwin's Radio");
        assertEquals("expected 2 matches", 2, matchedBooks.size());
        assertTrue("expected Darwin by Bear", matchedBooks.contains(book));
        assertTrue("expected Darwin by Patrick", matchedBooks.contains(book2));
    }
    @Test
    public void testFindExactAuthorMatchSingle() {
        String title = "Darwin's Radio";
        List<String> authors = Arrays.asList("Greg Bear");
        int year = 2000;
        Book book = new Book(title, authors, year);
        Library library = makeLibrary();
        library.buy(book);
        library.buy(book);
        library.buy(book);
        List<Book> matchedBooks = library.find("Greg Bear");
        assertEquals("expected 1 match", 1, matchedBooks.size());
        assertTrue("expected Darwin", matchedBooks.contains(book));
    }
    @Test
    public void testFindExactAuthorMatchMultiple() {
        String title = "Darwin's Radio";
        String title2 = "Darwin's Radio sequel";
        List<String> authors = Arrays.asList("Greg Bear");
        int year = 2000;
        Book book = new Book(title, authors, year);
        Book book2 = new Book(title2, authors, year);
        Library library = makeLibrary();
        library.buy(book);
        library.buy(book2);
        library.buy(book);
        List<Book> matchedBooks = library.find("Greg Bear");
        assertEquals("expected 2 matches", 2, matchedBooks.size());
        assertTrue("expected Darwin", matchedBooks.contains(book));
        assertTrue("expected Darwin sequel", matchedBooks.contains(book2));
    }
    @Test
    public void testFindNonExactTitleMatchMultiple() {
        String title = "Darwin's Radio";
        String title2 = "Darwin's Radio sequel";
        List<String> authors = Arrays.asList("Greg Bear");
        int year = 2000;
        Book book = new Book(title, authors, year);
        Book book2 = new Book(title2, authors, year);
        Library library = makeLibrary();
        library.buy(book);
        library.buy(book2);
        library.buy(book);
        List<Book> matchedBooks = library.find("Radio");
        assertEquals("expected 2 matches", 2, matchedBooks.size());
        assertTrue("expected Darwin", matchedBooks.contains(book));
        assertTrue("expected Darwin sequel", matchedBooks.contains(book2));
    }
    @Test
    public void testFindNonExactAuthorMatchMultiple() {
        String title = "Darwin's Radio";
        String title2 = "Darwin's Radio sequel";
        List<String> authors = Arrays.asList("Greg Bear");
        int year = 2000;
        Book book = new Book(title, authors, year);
        Book book2 = new Book(title2, authors, year);
        Library library = makeLibrary();
        library.buy(book);
        library.buy(book2);
        library.buy(book);
        List<Book> matchedBooks = library.find("Greg");
        assertEquals("expected 2 matches", 2, matchedBooks.size());
        assertTrue("expected Darwin", matchedBooks.contains(book));
        assertTrue("expected Darwin sequel", matchedBooks.contains(book2));
    }
    @Test
    public void testFindSameTitleAuthorDiffYearOrdererdByNew() {
        String title = "Darwin's Radio";
        List<String> authors = Arrays.asList("Greg Bear");
        int year = 2000;
        int year2 = 2001;
        int year3 = 2002;
        Book book = new Book(title, authors, year);
        Book book2 = new Book(title, authors, year2);
        Book book3 = new Book(title, authors, year3);
        Library library = makeLibrary();
        library.buy(book);
        library.buy(book2);
        library.buy(book3);
        List<Book> matchedBooks = library.find("Greg");
        assertEquals("expected 3 matches", 3, matchedBooks.size());
        assertEquals("expected Darwin 2000", book, matchedBooks.get(0));
        assertEquals("expected Darwin 2001", book2, matchedBooks.get(1));
        assertEquals("expected Darwin 2002", book3, matchedBooks.get(2));
    }
    @Test
    public void testFindDecreasingMatchOrderByTitle() {
        String title = "Darwin's Rad";
        String title2 = "Darwin's Radio";
        String title3 = "Darwin's Radio part 3";
        List<String> authors = Arrays.asList("Greg Bear");
        int year = 2000;
        Book book = new Book(title, authors, year);
        Book book2 = new Book(title2, authors, year);
        Book book3 = new Book(title3, authors, year);
        Library library = makeLibrary();
        library.buy(book);
        library.buy(book2);
        library.buy(book3);
        List<Book> matchedBooks = library.find("Darwin's Rad");
        assertEquals("expected 3 matches", 3, matchedBooks.size());
        assertEquals("expected Darwin 2000", book, matchedBooks.get(0));
        assertEquals("expected Darwin 2001", book2, matchedBooks.get(1));
        assertEquals("expected Darwin 2002", book3, matchedBooks.get(2));
    }
    /**
     * lose() tests
     */
    @Test
    public void testLose() {
        assert true;
    }
    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
