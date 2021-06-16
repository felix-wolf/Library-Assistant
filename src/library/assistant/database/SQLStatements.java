package library.assistant.database;

import java.util.ArrayList;

/**
 * This class aggregates *nearly* all sql statements
 * that where used throughout the application.
 * This then improves the writing of database test cases,
 * as no copy-pasting is needed
 *
 * @author felixwolf
 */
public class SQLStatements {

    /**
     * builds the sql statement for getting a member by its id
     * @param memberId the id of the member to get
     * @return the sql statement as a string
     */
    public static String getMemberById(String memberId) {
        return "SELECT * FROM MEMBER WHERE id = '" + memberId + "'";
    }

    /**
     * builds the sql statement for inserting an issue
     * @param memberId id of the member associated with the issue
     * @param bookId id of the book associated with the issue
     * @return the sql statement as a string
     */
    public static String insertIssue(String memberId, String bookId) {
        return "INSERT INTO ISSUE(memberID,bookID) VALUES ('" + memberId + "', '" + bookId + "')";
    }

    /**
     * builds the sql statement for updating an issue.
     * the issue is inferred by its associated book.
     * @param bookId id of the book associated with the issue
     * @return the sql statement as a string
     */
    public static String updateIssue(String bookId) {
        return "UPDATE ISSUE SET issueTime = CURRENT_TIMESTAMP, renew_count = renew_count+1 WHERE BOOKID = '" + bookId + "'";
    }

    /**
     * builds the sql statement for deleting a book by its id
     * @param bookId the id of the book to be deleted
     * @return the sql statement as a string
     */
    public static String deleteBookById(String bookId) {
        return "DELETE FROM BOOK WHERE ID = '" + bookId + "'";
    }

    /**
     * builds the sql statement for setting the availability of a book
     * @param bookId id of the book to be adapted
     * @param isAvailable true if the book is now available again
     * @return the sql statement as a string
     */
    public static String setBookAvailability(String bookId, boolean isAvailable) {
        return "UPDATE BOOK SET isAvail = " + isAvailable + " WHERE id = '" + bookId + "'";
    }

    /**
     * builds the sql statement for deleting an issue by its bookId
     * @param bookId id of the book associated with the issue
     * @return the sql statement as a string
     */
    public static String deleteIssueById(String bookId) {
        return "DELETE FROM ISSUE WHERE BOOKID = '" + bookId + "'";
    }

    /**
     * builds the sql statement for getting an issue by its bookId
     * @param bookId id of the book associated with the issue
     * @return the sql statement as a string
     */
    public static String getIssueByBookId(String bookId) {
        return "SELECT ISSUE.bookID, ISSUE.memberID, ISSUE.issueTime, ISSUE.renew_count,\n"
                + "MEMBER.name, MEMBER.mobile, MEMBER.email,\n"
                + "BOOK.title, BOOK.author, BOOK.publisher\n"
                + "FROM ISSUE\n"
                + "LEFT JOIN MEMBER\n"
                + "ON ISSUE.memberID=MEMBER.ID\n"
                + "LEFT JOIN BOOK\n"
                + "ON ISSUE.bookID=BOOK.ID\n"
                + "WHERE ISSUE.bookID='" + bookId + "'";
    }

    public static String getBookTrigger() {
        return "CREATE TRIGGER updateBookTimeStamp\n" +
                "AFTER UPDATE OF TITLE, AUTHOR, PUBLISHER, ISAVAIL ON BOOK\n" +
                "REFERENCING OLD AS EXISTING\n" +
                "FOR EACH ROW MODE DB2SQL\n" +
                "UPDATE BOOK SET updated_at = CURRENT_TIMESTAMP\n" +
                "WHERE ID = EXISTING.ID";
    }


    public static String getIssueTrigger() {
        return "CREATE TRIGGER updateIssueTimeStamp\n" +
                "AFTER UPDATE OF memberId, issueTime, renew_count ON ISSUE\n" +
                "REFERENCING OLD AS EXISTING\n" +
                "FOR EACH ROW MODE DB2SQL\n" +
                "UPDATE ISSUE SET updated_at = CURRENT_TIMESTAMP\n" +
                "WHERE BOOKID = EXISTING.BOOKID";
    }

    public static String getMemberTrigger() {
        return "CREATE TRIGGER updateMemberTimeStamp\n" +
                "AFTER UPDATE OF name, mobile, email ON MEMBER\n" +
                "REFERENCING OLD AS EXISTING\n" +
                "FOR EACH ROW MODE DB2SQL\n" +
                "UPDATE MEMBER SET updated_at = CURRENT_TIMESTAMP\n" +
                "WHERE id = EXISTING.id";
    }

    public static String getMailTrigger() {
        return "CREATE TRIGGER updateMailTimeStamp\n" +
                "AFTER UPDATE OF server_name, server_port, user_email, user_password, ssl_enabled ON MAIL_SERVER_INFO\n" +
                "REFERENCING OLD AS EXISTING\n" +
                "FOR EACH ROW MODE DB2SQL\n" +
                "UPDATE MAIL_SERVER_INFO SET updated_at = CURRENT_TIMESTAMP\n" +
                "WHERE server_name = EXISTING.server_name";
    }

    public static ArrayList<String> getTrigger() {
        ArrayList<String> arr = new ArrayList<>();
        arr.add(getMemberTrigger());
        arr.add(getBookTrigger());
        arr.add(getIssueTrigger());
        arr.add(getMailTrigger());
        return arr;
    }
}