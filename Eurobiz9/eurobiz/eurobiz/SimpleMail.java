/*
 * Created on Jun 6, 2005
 * http://www.rgagnon.com/javadetails/java-0321.html
 */
package eurobiz;

import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.Properties;

class SimpleMail { 
	

    public static void main(String[] args) throws Exception{
    	Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", "192.168.1.198");
        props.setProperty("mail.user", "problem");
        props.setProperty("mail.password", "problem");

        Session mailSession = Session.getDefaultInstance(props, null);
        Transport transport = mailSession.getTransport();

        MimeMessage message = new MimeMessage(mailSession);
        message.setContent("This is a test", "text/plain");
        message.addRecipient(Message.RecipientType.TO,
             new InternetAddress("problem@montebanato.ro"));

        transport.connect();
        transport.sendMessage(message,
            message.getRecipients(Message.RecipientType.TO));
        transport.close();
        }
	
	public static void sendMsgAttachFile(String _to, String _subject,
		    String _bodyText, String _fileName) {
		  boolean debug = false;
		  Properties props = System.getProperties();
		  Session session = Session.getDefaultInstance(props,null);
		  session.setDebug(debug);
		  try {
		    // create a message
		    Message msg = new MimeMessage(session);

		    // set the from
		    //msg.setFrom(fromAddress);

		    InternetAddress[] address ={new InternetAddress(_to)};
		    msg.setRecipients(Message.RecipientType.TO, address);
		    msg.setSubject(_subject);

		    MimeBodyPart textPart = new MimeBodyPart();
		    textPart.setContent(_bodyText, "text/plain");

		    MimeBodyPart attachFilePart = new MimeBodyPart();
		    FileDataSource fds = new FileDataSource(_fileName);
		    attachFilePart.setDataHandler(new DataHandler(fds));
		    attachFilePart.setFileName(fds.getName());

		    Multipart mp = new MimeMultipart();
		    mp.addBodyPart(textPart);
		    mp.addBodyPart(attachFilePart);

		    msg.setContent(mp);
		    Transport.send(msg);
		    }
		  catch (Exception e) { e.printStackTrace(); }
	}

	protected void saveMailAttachment (javax.mail.internet.MimeMultipart mp) {
		/* How to Use File Streams:	
		 http://java.sun.com/docs/books/tutorial/essential/io/filestreams.html */
		try {
			int n = mp.getCount();
			javax.mail.Part p = mp.getBodyPart (0);
			String disposition = p.getDisposition ();
			if (disposition != null &&
					(disposition.equalsIgnoreCase (javax.mail.Part.ATTACHMENT)
							|| disposition.equalsIgnoreCase (javax.mail.Part.INLINE) ) ) {
				String filename = p.getFileName ();
				File f = new File (filename);
				OutputStream os = new BufferedOutputStream (new FileOutputStream (f) );
				InputStream is = p.getInputStream ();
				int c;
				while ( (c = is.read () ) != -1)
					os.write (c);
					os.close ();
			}
		} catch (Exception e) { }
	}
}
