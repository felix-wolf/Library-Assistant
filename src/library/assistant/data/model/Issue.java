package library.assistant.data.model;

import java.sql.Timestamp;

public class Issue {
    private final String memberId;
    private final String bookId;
    private int renewCount;
    private final Timestamp timestamp;

    public Issue(String memberId, String bookId, Timestamp timestamp) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.timestamp = timestamp;
        this.renewCount = 0;
    }

    public Issue(String memberId, String bookId, int renewCount, Timestamp timestamp) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.timestamp = timestamp;
        this.renewCount = renewCount;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getBookId() {
        return bookId;
    }

    public int getRenewCount() {
        return renewCount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setRenewCount(int renewCount) {
        this.renewCount = renewCount;
    }
}
