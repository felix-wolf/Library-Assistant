package library.assistant.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import library.assistant.ui.listbook.BookListController.Book;
import library.assistant.ui.listmember.MemberListController;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class DatabaseHandler {

    private final static Logger LOGGER = LogManager.getLogger(DatabaseHandler.class.getName());

    private static DatabaseHandler handler = null;

    private static final String DB_URL_LIVE = "jdbc:log4jdbc:derby://localhost:1527/database;create=true";
    private static final String DB_URL_TEST = "jdbc:derby://localhost:1527/database_test;create=true";
    private static final String DRIVER_LIVE = "net.sf.log4jdbc.DriverSpy";
    private static final String DRIVER_TEST = "org.apache.derby.jdbc.EmbeddedDriver";
    private static Connection conn = null;
    private static Statement stmt = null;

    static {
        createConnection();
        inflateDB();
    }

    private DatabaseHandler() {
    }

    public static DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
        }
        return handler;
    }

    public static boolean isJUnitTest() {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element.getClassName().startsWith("org.junit.")) {
                System.out.println("is junit test: TRUE");
                return true;
            }
        }
        System.out.println("is junit test: FALSE");
        return false;
    }

    private static void inflateDB() {
        List<String> tableData = new ArrayList<>();
        try {
            Set<String> loadedTables = getDBTables();
            System.out.println("Already loaded tables " + loadedTables);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(DatabaseHandler.class.getResourceAsStream("/resources/database/tables.xml"));
            NodeList nList = doc.getElementsByTagName("table-entry");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                Element entry = (Element) nNode;
                String tableName = entry.getAttribute("name");
                String query = entry.getAttribute("col-data");
                if (!loadedTables.contains(tableName.toLowerCase())) {
                    tableData.add(String.format("CREATE TABLE %s (%s)", tableName, query));
                }
            }
            if (tableData.isEmpty()) {
                System.out.println("Tables are already loaded");
            }
            else {
                System.out.println("Inflating new tables.");
                createTables(tableData);
            }
        }
        catch (Exception ex) {
            LOGGER.log(Level.ERROR, "{}", ex.toString());
        }
    }

    private static void createConnection() {
        try {
            Class.forName(isJUnitTest() ? DRIVER_TEST : DRIVER_LIVE).newInstance();
            conn = DriverManager.getConnection(isJUnitTest() ? DB_URL_TEST : DB_URL_LIVE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cant load database", "Database Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

    }

    private static Set<String> getDBTables() throws SQLException {
        Set<String> set = new HashSet<>();
        DatabaseMetaData dbmeta = conn.getMetaData();
        readDBTable(set, dbmeta);
        return set;
    }

    private static void readDBTable(Set<String> set, DatabaseMetaData dbmeta) throws SQLException {
        ResultSet rs = dbmeta.getTables(null, null, null, new String[]{"TABLE"});
        while (rs.next()) {
            set.add(rs.getString("TABLE_NAME").toLowerCase());
        }
    }

    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        }
        catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        }
        return result;
    }

    public boolean execAction(String qu) {
        try {
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return false;
        }
    }

    public boolean deleteBook(Book book) {
        try {
            String deleteStatement = SQLStatements.deleteBookById(book.getId());
            PreparedStatement stmt = conn.prepareStatement(deleteStatement);
            int res = stmt.executeUpdate();
            if (res == 1) {
                return true;
            }
        }
        catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex.toString());
        }
        return false;
    }

    public boolean isBookAlreadyIssued(Book book) {
        try {
            String checkstmt = SQLStatements.bookIsAlreadyIssued();
            PreparedStatement stmt = conn.prepareStatement(checkstmt);
            stmt.setString(1, book.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println(count);
                return (count > 0);
            }
        }
        catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex.toString());
        }
        return false;
    }

    public boolean deleteMember(MemberListController.Member member) {
        try {
            String deleteStatement = SQLStatements.deleteMemberById();
            PreparedStatement stmt = conn.prepareStatement(deleteStatement);
            stmt.setString(1, member.getId());
            int res = stmt.executeUpdate();
            if (res == 1) {
                return true;
            }
        }
        catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex.toString());
        }
        return false;
    }

    public boolean isMemberHasAnyBooks(MemberListController.Member member) {
        try {
            String checkstmt = SQLStatements.getNumberOfIssuesByMemberId();
            PreparedStatement stmt = conn.prepareStatement(checkstmt);
            stmt.setString(1, member.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println(count);
                return (count > 0);
            }
        }
        catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex.toString());
        }
        return false;
    }

    public boolean updateBook(Book book) {
        try {
            String update = SQLStatements.updateBook();
            PreparedStatement stmt = conn.prepareStatement(update);
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getPublisher());
            stmt.setString(4, book.getId());
            int res = stmt.executeUpdate();
            return (res > 0);
        }
        catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex.toString());
        }
        return false;
    }

    public boolean updateMember(MemberListController.Member member) {
        try {
            String update = SQLStatements.updateMember();
            PreparedStatement stmt = conn.prepareStatement(update);
            stmt.setString(1, member.getName());
            stmt.setString(2, member.getEmail());
            stmt.setString(3, member.getMobile());
            stmt.setString(4, member.getId());
            int res = stmt.executeUpdate();
            return (res > 0);
        }
        catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex.toString());
        }
        return false;
    }

    public static void main(String[] args) {
        DatabaseHandler.getInstance();
    }

    public ObservableList<PieChart.Data> getBookGraphStatistics() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        try {
            String qu1 = SQLStatements.getNumberOfBooks();
            String qu2 = SQLStatements.getNumberOfIssues();
            ResultSet rs = execQuery(qu1);
            if (rs.next()) {
                int count = rs.getInt(1);
                data.add(new PieChart.Data("Total Books (" + count + ")", count));
            }
            rs = execQuery(qu2);
            if (rs.next()) {
                int count = rs.getInt(1);
                data.add(new PieChart.Data("Issued Books (" + count + ")", count));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public ObservableList<PieChart.Data> getMemberGraphStatistics() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        try {
            String qu1 = SQLStatements.getNumberOfMembers();
            String qu2 = SQLStatements.getNumberOfDistinctIssuesByMemberId();
            ResultSet rs = execQuery(qu1);
            if (rs.next()) {
                int count = rs.getInt(1);
                data.add(new PieChart.Data("Total Members (" + count + ")", count));
            }
            rs = execQuery(qu2);
            if (rs.next()) {
                int count = rs.getInt(1);
                data.add(new PieChart.Data("Active (" + count + ")", count));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    private static void createTables(List<String> tableData) throws SQLException {
        Statement statement = conn.createStatement();
        statement.closeOnCompletion();
        for (String command : tableData) {
            System.out.println(command);
            statement.addBatch(command);
        }
        statement.executeBatch();
    }

    public Connection getConnection() {
        return conn;
    }
}
