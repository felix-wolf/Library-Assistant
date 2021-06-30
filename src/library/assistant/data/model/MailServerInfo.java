package library.assistant.data.model;

import java.util.Objects;

/**
 * @author afsal
 */
public class MailServerInfo {

    private final String mailServer;
    private final Integer port;
    private final String emailId;
    private final String password;
    private final Boolean sslEnabled;
    
    public MailServerInfo(String mailServer, Integer port, String emailID, String password, Boolean sslEnabled) {
        this.mailServer = mailServer;
        this.port = port;
        this.emailId = emailID;
        this.password = password;
        this.sslEnabled = sslEnabled;
    }

    public String getMailServer() {
        return mailServer;
    }

    public Integer getPort() {
        return port;
    }

    public String getEmailID() {
        return emailId;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getSslEnabled() {
        return sslEnabled;
    }
    
    @Override
    public String toString() {
        return String.format("%s:%d @ %s", mailServer, port, emailId);
    }

    public boolean validate() {
        boolean flag = mailServer == null || mailServer.isEmpty() || port == null || emailId == null || emailId.isEmpty() || password.isEmpty();
        return !flag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailServerInfo that = (MailServerInfo) o;
        return Objects.equals(mailServer, that.mailServer) && Objects.equals(port, that.port) && Objects.equals(emailId, that.emailId) && Objects.equals(password, that.password) && Objects.equals(sslEnabled, that.sslEnabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mailServer, port, emailId, password, sslEnabled);
    }
}
