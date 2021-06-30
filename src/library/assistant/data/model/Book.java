package library.assistant.data.model;

/**
 *
 * @author afsal
 */
public class Book {
    String id;
    String title;
    String author;
    String publisher;
    Boolean isAvailable;

    public Book(String id, boolean isAvail) {
        this.id = id;
        this.isAvailable = isAvail;
    }

    public Book(String id, String title, String author, String publisher, Boolean isAvail) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isAvailable = isAvail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Boolean getAvailability() {
        return isAvailable;
    }

    public void setIsAvail(Boolean isAvail) {
        this.isAvailable = isAvail;
    }
    
    
}
