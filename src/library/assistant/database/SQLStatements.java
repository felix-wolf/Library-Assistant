package library.assistant.database;

public class SQLStatements {

    // SQL Queries, in global variables for testing
    public static String getMemberById(String memberId) {
        return "SELECT * FROM MEMBER WHERE id = '" + memberId + "'";
    }

    public static String insertIssue(String memberId, String bookId) {
         return "INSERT INTO ISSUE(memberID,bookID) VALUES ('" + memberId + "', '" + bookId + "')";
    }

    public static String updateIssue(String bookId) {
        return "UPDATE ISSUE SET issueTime = CURRENT_TIMESTAMP, renew_count = renew_count+1 WHERE BOOKID = '" + bookId + "'";
    }

    public static String deleteBookById(String bookId) {
        return "DELETE FROM BOOK WHERE ID = " + bookId;
    }

    public static String setBookAvailability(String bookId, boolean isAvailable) {
        return "UPDATE BOOK SET isAvail = " + isAvailable + " WHERE id = '" + bookId + "'";
    }

    public static String deleteIssueById(String bookId) {
        return "DELETE FROM ISSUE WHERE BOOKID = '" + bookId + "'";
    }

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

}
