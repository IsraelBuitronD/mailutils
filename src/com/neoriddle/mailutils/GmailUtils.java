package com.neoriddle.mailutils;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.AddressException;

public class GmailUtils {

    private String  smtpHost = "smtp.gmail.com";
    private int     smtpPort = 587;
    private boolean debug    = false;
    private String  username = null;
    private String  password = null;

    public GmailUtils() {
	// Empty constructor
    }

    public GmailUtils(String username, String password) {
	this.username = username;
	this.password = password;
    }

    public void send(String[] to, String[] cc, String[] bcc, String message, String subject)
	throws AddressException, MessagingException {
	Properties props = new Properties();
	props.put("mail.smtp.host", smtpHost);
	props.put("mail.smtp.port", smtpPort);
	props.put("mail.debug", debug);
	props.put("mail.transport.protocol", "smtp");
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");

	Session session = Session.getInstance(props, 
					      new GMailAuthenticator(username, password));

	Message msg = new MimeMessage(session);
	msg.setFrom(new InternetAddress(this.username));
	msg.setSubject(subject);
	msg.setSentDate(new Date());
	msg.setText(message);

	if(to != null && to.length > 0)
	    msg.setRecipients(Message.RecipientType.TO, toAddress(to));
	if(cc != null && cc.length > 0)
	    msg.setRecipients(Message.RecipientType.CC, toAddress(cc));
	if(bcc != null && bcc.length > 0)
	    msg.setRecipients(Message.RecipientType.BCC, toAddress(bcc));

	Transport.send(msg);
    }

    public static Address[] toAddress(String[] addresses)
	throws AddressException {
	InternetAddress[] iAddress = new InternetAddress[addresses.length];

	for(int i=0; i<addresses.length; i++)
	    iAddress[i] = new InternetAddress(addresses[i]);

	return iAddress;
    }

    private static class GMailAuthenticator extends Authenticator {

	private String username = null;
	private String password = null;

	public GMailAuthenticator(String username, String password) {
	    this.username = username;
	    this.password = password;
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
	    return new PasswordAuthentication(this.username, this.password);
	}
    }
}
