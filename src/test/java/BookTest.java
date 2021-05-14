package test.java;

import library.assistant.data.model.Book;
import library.assistant.database.DataHelper;
import library.assistant.database.DatabaseHandler;
import library.assistant.ui.listbook.BookListController;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BookTest {
    final String id = "134565432135";
    final Book bookModel = new Book(id, "",  "", "", true);
    final BookListController.Book bookControllerModel = new BookListController.Book("", id, "", "", true);

    public BookTest() {
        // WIPE DATABASE
        DataHelper.wipeTable("Issue");
        DataHelper.wipeTable("Member");
        DataHelper.wipeTable("Book");
        System.out.println("Test Database wiped");
    }

    @Test
    public void insertBookTest() {
        assertTrue(DataHelper.insertNewBook(bookModel));
    }

    @Test
    public void bookExistsTest() {
        DataHelper.insertNewBook(bookModel);
        assertTrue(DataHelper.isBookExists(id));
    }

    @Test
    public void deleteBookTest() {
        DataHelper.insertNewBook(bookModel);
        assertTrue(DatabaseHandler.getInstance().deleteBook(bookControllerModel));
    }

    @Test
    public void getBookInfoTest() {
        DataHelper.insertNewBook(bookModel);
        assertNotNull(DataHelper.getBookInfoWithIssueData(id));
    }
}
