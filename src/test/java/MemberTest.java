package test.java;

import library.assistant.data.model.Book;
import library.assistant.database.DataHelper;
import library.assistant.database.DatabaseHandler;
import library.assistant.ui.listmember.MemberListController;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MemberTest {
    final String id = "134565432135";
    final MemberListController.Member member = new MemberListController.Member("", id, "", "");

    public MemberTest() {
        // WIPE DATABASE
        DataHelper.wipeTable("Issue");
        DataHelper.wipeTable("Member");
        DataHelper.wipeTable("Book");
        System.out.println("Test Database wiped");
    }

    @Test
    public void insertMemberTest() {
        assertTrue(DataHelper.insertNewMember(member));
        assertTrue(DataHelper.isMemberExists(id));
    }

    /**
     * Test member exists function
     */
    @Test
    public void memberDoesExistTest() {
        DataHelper.insertNewMember(member);
        assertTrue(DataHelper.isMemberExists(id));
    }

    /**
     * Test member deletion
     */
    @Test
    public void deleteMemberTest() {
        DataHelper.insertNewMember(member);
        assertTrue(DatabaseHandler.getInstance().deleteMember(member));
    }

    /**
     * test if a member has books
     */
    @Test
    public void memberBooksTest() {
        DataHelper.insertNewMember(member);
        assertFalse(DatabaseHandler.getInstance().isMemberHasAnyBooks(member));
        DataHelper.insertNewBook(new Book(id, "", "", "", true));
        DatabaseHandler.getInstance().execAction("INSERT INTO ISSUE(memberID,bookID) VALUES ('" + id + "', '" + id + "')");
        assertTrue(DatabaseHandler.getInstance().isMemberHasAnyBooks(member));

    }

}