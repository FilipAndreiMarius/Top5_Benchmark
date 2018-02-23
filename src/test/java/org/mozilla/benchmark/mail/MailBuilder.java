package org.mozilla.benchmark.mail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.utils.ErrorManager;
import org.mozilla.benchmark.utils.PropertiesManager;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Session;
import javax.mail.Transport;

/**
 * Created by silviu.checherita on 2/16/2018.
 */
public class MailBuilder {
    private static final Logger logger = LogManager.getLogger(MailBuilder.class.getName());
    private MimeMessage mail;

    public MailBuilder(String mailTitle, String mailContent, String recipients){
        this.mail = createMail(mailTitle, mailContent, recipients);
    }

    public MimeMessage getMail() {
        return mail;
    }

    public void setMail(MimeMessage mail) {
        this.mail = mail;
    }

    private MimeMessage createMail(String mailTitle, String mailContent, String recipients) {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "127.0.0.1");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(PropertiesManager.getEmailUserName(), PropertiesManager.getEmailPassword());
            }
        });

        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(PropertiesManager.getEmailSender()));
        } catch (MessagingException e) {
            logger.error("Email address for sender is incorrect !!!");
        }

        try {
            message.addRecipients(Message.RecipientType.TO, recipients);
        } catch (MessagingException e) {
            logger.error("Email address for recipients is incorrect !!!");
        }
        try {
            message.setSubject(mailTitle);
            message.setContent(mailContent,"text/html");
        } catch (MessagingException e) {
            logger.error("Email address for recipient is incorrect !!!");
        }
        return message;
    }

    public void sendMail() {

        try {
            Transport.send(getMail());
            logger.info("Email sent successfully ! ");
        } catch (MessagingException e) {
            logger.error(String.format("Error sending Email !!! %s", ErrorManager.getErrorMessage(e.getStackTrace())));
        }
    }
}
