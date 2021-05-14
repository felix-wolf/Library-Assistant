package test.java;

import library.assistant.data.model.Book;
import library.assistant.database.DataHelper;
import library.assistant.database.DatabaseHandler;
import library.assistant.ui.listbook.BookListController;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BookTest {
    // generic book id
    final String id = "134565432135";
    // test-book
    final Book bookModel = new Book(id, "",  "", "", true);
    // test-book of a different type that is equal to bookModel in values
    final BookListController.Book bookControllerModel = new BookListController.Book("", id, "", "", true);

    /**
     * wipes the database before a new test run
     */
    public BookTest() {
        DataHelper.wipeTable("Issue");
        DataHelper.wipeTable("Member");
        DataHelper.wipeTable("Book");
        System.out.println("Test Database wiped");
    }

    /**
     * tests book insertion
     */
    @Test
    public void insertBookTest() {
        assertTrue(DataHelper.insertNewBook(bookModel));
    }

    /**
     * tests if a book exists after it was inserted into the database
     */
    @Test
    public void bookExistsTest() {
        DataHelper.insertNewBook(bookModel);
        assertTrue(DataHelper.isBookExists(id));
    }

    /**
     * tests book deletion
     */
    @Test
    public void deleteBookTest() {
        DataHelper.insertNewBook(bookModel);
        assertTrue(DatabaseHandler.getInstance().deleteBook(bookControllerModel));
    }

    /**
     * tests whether information can be retrieved from an
     * inserted book
     */
    @Test
    public void getBookInfoTest() {
        DataHelper.insertNewBook(bookModel);
        assertNotNull(DataHelper.getBookInfoWithIssueData(id));
    }
}
