package test.java;

import library.assistant.data.model.Book;
import library.assistant.database.DataHelper;
import library.assistant.database.DatabaseHandler;
import library.assistant.ui.listbook.BookListController;
import library.assistant.ui.listmember.MemberListController;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static library.assistant.database.SQLStatements.*;
import static org.junit.Assert.*;

public class IssueTest {

    // generic id
    final String id = "134565432135";
    // test book
    final Book bookModel = new Book(id, "",  "", "", true);
    // test book of a different type that is equals in values for bookModel
    final BookListController.Book bookControllerModel = new BookListController.Book("", id, "", "", true);
    // test member
    final MemberListController.Member member = new MemberListController.Member("", id, "", "");

    /**
     * wipes the database before a new test run
     */
    public IssueTest() {
        DataHelper.wipeTable("Issue");
        DataHelper.wipeTable("Member");
        DataHelper.wipeTable("Book");
        System.out.println("Test Database wiped");
    }

    /**
     * tests issue insertion
     */
    @Test
    public void insertIssueTest() {
        DataHelper.insertNewBook(bookModel);
        DataHelper.insertNewMember(member);
        assertTrue(DatabaseHandler.getInstance().execAction(insertIssue(id, id)));
    }

    /**
     * tests issue deletion
     */
    @Test
    public void deleteIssueTest() {
        DataHelper.insertNewBook(bookModel);
        DataHelper.insertNewMember(member);
        DatabaseHandler.getInstance().execAction(insertIssue(id, id));
        assertTrue(DatabaseHandler.getInstance().execAction(deleteIssueById(id)));
    }

    /**
     * tests member has books functionality
     */
    @Test
    public void memberHasBooksTest() {
        DataHelper.insertNewBook(bookModel);
        DataHelper.insertNewMember(member);
        DatabaseHandler.getInstance().execAction(insertIssue(id, id));
        assertTrue(DatabaseHandler.getInstance().isMemberHasAnyBooks(member));
        DatabaseHandler.getInstance().execAction(deleteIssueById(id));
        assertFalse(DatabaseHandler.getInstance().isMemberHasAnyBooks(member));
    }

    /**
     * tests bookIsAlreadyIssuedTest
     */
    @Test
    public void bookIsAlreadyIssuedTest() {
        DataHelper.insertNewBook(bookModel);
        DataHelper.insertNewMember(member);
        assertFalse(DatabaseHandler.getInstance().isBookAlreadyIssued(bookControllerModel));
        DatabaseHandler.getInstance().execAction(insertIssue(id, id));
        assertTrue(DatabaseHandler.getInstance().isBookAlreadyIssued(bookControllerModel));
        DatabaseHandler.getInstance().execAction(deleteIssueById(id));
        assertFalse(DatabaseHandler.getInstance().isBookAlreadyIssued(bookControllerModel));
    }

    /**
     * tests bookAvailability
     */
    @Test
    public void bookAvailability() {
        DataHelper.insertNewBook(bookModel);
        DataHelper.insertNewMember(member);
        DatabaseHandler.getInstance().execAction(insertIssue(id, id));
        assertTrue(bookModel.getAvailability());
        DatabaseHandler.getInstance().execAction(setBookAvailability(id, false));
        ResultSet info = DataHelper.getBookInfoWithIssueData(id);
        assert info != null;
        try {
            info.next();
            String author = info.getString("author");
            assertEquals(author, bookModel.getAuthor());
            boolean isAvailable = info.getBoolean("isAvail");
            assertFalse(isAvailable);
        } catch (SQLException exception) {
            System.out.println("ERROR: " + exception.getLocalizedMessage());
            fail();
        }
    }



}
