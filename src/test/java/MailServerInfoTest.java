package test.java;

import library.assistant.data.model.MailServerInfo;
import library.assistant.database.DataHelper;
import org.junit.Test;

import static org.junit.Assert.*;

public class MailServerInfoTest {

    final MailServerInfo mailInfo = new MailServerInfo("", 1, "", "", true);

    public MailServerInfoTest() {
        DataHelper.wipeTable("MAIL_SERVER_INFO");
        System.out.println("Mail Table of Test Database wiped");
    }

    @Test
    public void addMailServerInfoTest() {
        assertTrue(DataHelper.updateMailServerInfo(mailInfo));
    }

    @Test
    public void loadMailServerInfoTest() {
        DataHelper.updateMailServerInfo(mailInfo);
        MailServerInfo loadedInfo = DataHelper.loadMailServerInfo();
        assertEquals(mailInfo, loadedInfo);
    }

    /**
     * mail info only valid with non empty values
     */
    @Test
    public void validMailInfoTest() {
        MailServerInfo info = mailInfo;
        assertFalse(info.validate());
        info = new MailServerInfo("test", 123, "123", "123", true);
        assertTrue(info.validate());
    }

}
