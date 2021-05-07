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
    Book bookModel = new Book(id, "Aula International 1", "Jaime Corpas", "Klett", true);
    BookListController.Book bookControllerModel = new BookListController.Book("Aula International 1", id, "Jaime Corpas", "Klett", true);

    MemberListController.Member member = new MemberListController.Member("", id, "", "");

    public DataHelperTest() {
        if (DataHelper.isBookExists(id)) {
            DatabaseHandler.getInstance().deleteBook(bookControllerModel);
            System.out.println("Deleted Test Book");
        }
        if (DataHelper.isMemberExists(id)) {
            System.out.println("Deleted Test Member");
            DatabaseHandler.getInstance().deleteMember(member);
        }
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