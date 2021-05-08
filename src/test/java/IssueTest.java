package test.java;

import library.assistant.data.model.Book;
import library.assistant.database.DataHelper;
import library.assistant.database.DatabaseHandler;
import library.assistant.ui.listbook.BookListController;
import library.assistant.ui.listmember.MemberListController;
import org.junit.Test;

import static library.assistant.database.SQLStatements.insertIssue;
import static library.assistant.database.SQLStatements.setBookAvailability;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IssueTest {

    final String id = "134565432135";
    final Book bookModel = new Book(id, "",  "", "", true);
    final BookListController.Book bookControllerModel = new BookListController.Book("", id, "", "", true);
    final MemberListController.Member member = new MemberListController.Member("", id, "", "");

    public IssueTest() {
        DataHelper.wipeTable("Issue");
        DataHelper.wipeTable("Member");
        DataHelper.wipeTable("Book");
        System.out.println("Test Database wiped");
    }

    /**
     * had to write plain sql statements as this is what is done in the actual controller
     */
    @Test
    public void bookIssueTest() {
        DataHelper.insertNewBook(bookModel);
        DataHelper.insertNewMember(member);
        assertFalse(DatabaseHandler.getInstance().isBookAlreadyIssued(bookControllerModel));
        assertTrue(DatabaseHandler.getInstance().execAction(setBookAvailability(id, false)));
        assertTrue(DatabaseHandler.getInstance().execAction(insertIssue(id, id)));
        assertTrue(DatabaseHandler.getInstance().isBookAlreadyIssued(bookControllerModel));
        assertTrue(DatabaseHandler.getInstance().isMemberHasAnyBooks(member));
    }



}
