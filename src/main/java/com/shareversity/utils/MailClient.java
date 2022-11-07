package com.shareversity.utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

public class MailClient {
	private static final String EMAIL = "princyverma589@gmail.com";
	private static final String PASS = "fzjuolfnnhdbxqrc";

	public static void sendRegistrationEmail(String firstName, String toEmail, String securityCode) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
		props.put("mail.smtp.port", "587"); //TLS Port
		props.put("mail.smtp.auth", "true"); //enable authentication
		props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

		//create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(EMAIL, PASS);
			}
		};
		Session session = Session.getInstance(props, auth);

		sendEmail(session, toEmail,"Confirmation code for Shareversity Account",
				"Hello " + firstName + "! \n \n" +
						"You recently registered for Shareversity. To complete your registration, " +
						"please confirm your account.\n: " + securityCode + "\n" +
						"Thanks," + "\n" +
				"Shareversity Team");
	}

	private static boolean sendEmail(Session session, String toEmail, String subject, String body){
		try {
			MimeMessage msg = new MimeMessage(session);

			msg.setSubject(subject);

			msg.setText(body);

			msg.setSentDate(new Date());

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			Transport.send(msg);
			
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
}


