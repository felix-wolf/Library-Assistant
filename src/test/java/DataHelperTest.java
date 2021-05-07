package test.java;

import library.assistant.data.model.Book;
import library.assistant.database.DataHelper;
import library.assistant.database.DatabaseHandler;
import library.assistant.ui.listbook.BookListController;
import library.assistant.ui.listmember.MemberListController;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DataHelperTest {
    final String id = "134565432135";
    final Book bookModel = new Book(id, "",  "", "", true);
    final BookListController.Book bookControllerModel = new BookListController.Book("", id, "", "", true);

    final MemberListController.Member member = new MemberListController.Member("", id, "", "");

    public DataHelperTest() {
        // WIPE DATABASE
        DataHelper.wipeTable("Member");
        DataHelper.wipeTable("BOOK");
        DataHelper.wipeTable("MAIL_SERVER_INFO");
        DataHelper.wipeTable("ISSUE");
    }

    @Test
    public void addBookTest() {
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

    @Test
    public void addMemberTest() {
        assertTrue(DataHelper.insertNewMember(member));
        assertTrue(DataHelper.isMemberExists(id));
    }

    @Test
    public void bookMemberTest() {
        DataHelper.insertNewMember(member);
        assertTrue(DataHelper.isMemberExists(id));
    }

    @Test
    public void deleteMemberTest() {
        DataHelper.insertNewMember(member);
        assertTrue(DatabaseHandler.getInstance().deleteMember(member));
    }

}