package library;

import java.lang.IllegalArgumentException;

/**
 * BookCopy is a mutable type representing a particular copy of a book that is held in a library's
 * collection.
 */
public class BookCopy {

    //rep
    private final Book book;
    private Condition condition;
    /**
     * REP INVARIANT
     * =============
     * @param book : Book object of which this is a copy
     * @param condition : condition of the book, must be either GOOD or DAMAGED.
     */

    /**
    * ABSTRACTION FUNCTION
    * ====================
    * represents a particular copy of a book.
    */

    /**
    * SAFETY FROM REP EXPOSURE
    * ========================
    * All fields are private.
    */
    public static enum Condition {
        GOOD, DAMAGED
    };

    /**
     * Make a new BookCopy, initially in good condition.
     * @param book the Book of which this is a copy
     */
    public BookCopy(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("book object cannot be null");
        }
        this.book = book;
        condition = Condition.GOOD;
        checkRep();
    }

    // assert the rep invariant
    private void checkRep() {
        assert book!=null;
        assert condition==Condition.GOOD || condition==Condition.DAMAGED;
    }

    /**
     * @return the Book of which this is a copy
     */
    public Book getBook() {
        return book;
    }

    /**
     * @return the condition of this book copy
     */
    public Condition getCondition() {
        return condition;
    }

    /**
     * Set the condition of a book copy.  This typically happens when a book copy is returned and a librarian inspects it.
     * @param condition the latest condition of the book copy
     */
    public void setCondition(Condition condition) {
        this.condition = condition;
        checkRep();
    }

    /**
     * @return human-readable representation of this book that includes book.toString()
     *    and the words "good" or "damaged" depending on its condition
     */
    public String toString() {
        String conditionStr = condition==Condition.GOOD ? "good" : "damaged";
        return book.toString() + "\n" + conditionStr;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        else if (!(that instanceof BookCopy)) return false;
        BookCopy b2 = (BookCopy) that;
        return this.book.equals(b2.book) && this.condition==b2.condition;
    }
    
    /**
     * hashing strategy taken from effective java : unit 9
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + book.hashCode();
        if (condition == Condition.DAMAGED) {
            result = 31 * result + 1;
        }
        return result;
    }


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
