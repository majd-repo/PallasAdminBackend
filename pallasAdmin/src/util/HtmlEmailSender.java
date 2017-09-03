package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class HtmlEmailSender {

	private static String FROM_EMAIL = null;
	static Properties prop = new Properties();
	static InputStream input = null;

	public static void sendHtmlEmail(Session session, String subject,
			String content, String recipientEmail) throws AddressException,
			MessagingException {

		try {
			input = HtmlEmailSender.class.getClassLoader().getResourceAsStream(
					"config.properties");
			// load a properties file
			prop.load(input);
			FROM_EMAIL = prop.getProperty("FROM_EMAIL");

			// creates a new e-mail message
			Message msg = new MimeMessage(session);
			msg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(recipientEmail));
			msg.setSubject(subject);
			msg.setFrom(new InternetAddress(FROM_EMAIL, "Pallas Ops Center"));
			msg.setSentDate(new Date());
			// set plain text message
			msg.setContent(content, "text/html");
			// sends the e-mail
			Transport.send(msg);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
