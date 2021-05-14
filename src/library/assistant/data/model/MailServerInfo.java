package library.assistant.data.model;

import java.util.Objects;

/**
 * @author afsal
 */
public class MailServerInfo {

    private final String mailServer;
    private final Integer port;
    private final String emailID;
    private final String password;
    private final Boolean sslEnabled;
    
    public MailServerInfo(String mailServer, Integer port, String emailID, String password, Boolean sslEnabled) {
        this.mailServer = mailServer;
        this.port = port;
        this.emailID = emailID;
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
        return emailID;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getSslEnabled() {
        return sslEnabled;
    }
    
    @Override
    public String toString() {
        return String.format("%s:%d @ %s", mailServer, port, emailID);
    }

    public boolean validate() {
        boolean flag = mailServer == null || mailServer.isEmpty() || port == null || emailID == null || emailID.isEmpty() || password.isEmpty();
        return !flag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailServerInfo that = (MailServerInfo) o;
        return Objects.equals(mailServer, that.mailServer) && Objects.equals(port, that.port) && Objects.equals(emailID, that.emailID) && Objects.equals(password, that.password) && Objects.equals(sslEnabled, that.sslEnabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mailServer, port, emailID, password, sslEnabled);
    }
}
