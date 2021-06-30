package library.assistant.database;

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
     * builds the sql statement for getting all rows from member table
     * @return the sql statement as a string
     */
    public static String getAllFromMembers() {
        return "SELECT * FROM MEMBER";
    }

    /**
     * builds the sql statement for getting all rows from book table
     * @return the sql statement as a string
     */
    public static String getAllFromBooks() {
        return "SELECT * FROM BOOK";
    }

    /**
     * builds the sql statement for getting a book by its id
     * @param id the id of the book
     * @return the sql statement as a string
     */
    public static String getBookById(String id) {
        return "SELECT * FROM BOOK WHERE id = '" + id + "'";
    }

    /**
     * builds the sql statement for getting the number of existing books
     * @return the sql statement as a string
     */
    public static String getNumberOfBooks() {
        return "SELECT COUNT(*) FROM BOOK";
    }

    /**
     * builds the sql statement for getting the number of existing issues
     * @return the sql statement as a string
     */
    public static String getNumberOfIssues() {
        return "SELECT COUNT(*) FROM ISSUE";
    }

    /**
     * builds the sql statement for getting the number of existing issues with distinct memberIds
     * @return the sql statement as a string
     */
    public static String getNumberOfDistinctIssuesByMemberId() {
        return "SELECT COUNT(DISTINCT memberID) FROM ISSUE";
    }

    /**
     * builds the sql statement for getting the number of existing members
     * @return the sql statement as a string
     */
    public static String getNumberOfMembers() {
        return "SELECT COUNT(*) FROM MEMBER";
    }

    /**
     * builds the sql statement for getting all existing issues by memberId
     * @return the sql statement as a string
     */
    public static String getNumberOfIssuesByMemberId() {
        return "SELECT COUNT(*) FROM ISSUE WHERE memberID=?";
    }

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
     * builds the sql statement for getting an issue by its id
     * @param id the bookid of the book associated with the issue
     * @return the sql statement as a string
     */
    public static String getIssueById(String id) {
        return "SELECT * FROM ISSUE WHERE bookid = '" + id + "'";
    }

    /**
     * builds the sql statement for updating a member.
     * @return the sql statement as a string
     */
    public static String updateMember() {
        return "UPDATE MEMBER SET NAME=?, EMAIL=?, MOBILE=? WHERE ID=?";
    }

    /**
     * builds the sql statement for updating a book.
     * @return the sql statement as a string
     */
    public static String updateBook() {
        return "UPDATE BOOK SET TITLE=?, AUTHOR=?, PUBLISHER=? WHERE ID=?";
    }

    /**
     * builds the sql statement for deleting a book by its id
     * @param bookId the id of the book to be deleted
     * @return the sql statement as a string
     */
    public static String deleteBookById(String bookId) {
        return "DELETE FROM BOOK WHERE ID='" + bookId + "'";
    }

    /**
     * builds the sql statement for deleting a member by its id.
     * @return the sql statement as a string
     */
    public static String deleteMemberById() {
        return "DELETE FROM MEMBER WHERE id = ?";
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
     * builds the sql statement for checking if a book is already issued
     * @return the sql statement as a string
     */
    public static String bookIsAlreadyIssued() {
        return "SELECT COUNT(*) FROM ISSUE WHERE bookid=?";
    }



    /**
     * builds the sql statement for deleting an issue by its bookId
     * @param bookId id of the book associated with the issue
     * @return the sql statement as a string
     */
    public static String deleteIssueById(String bookId) {
        return "DELETE FROM ISSUE WHERE BOOKID='" + bookId + "'";
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

}