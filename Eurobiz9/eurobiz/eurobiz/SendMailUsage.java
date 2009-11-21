/*
 * Created on Jun 6, 2005
 * http://www.vipan.com/htdocs/javamail.html
 * Eurobiz9 : 2009-10 subnet.61
 */
package eurobiz;
import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SendMailUsage {
  public SendMailUsage() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void sendMyMail(String attachment, String sDepo){
    	// String to   = "problem@montebanato.ro";
        //String sAlex = "Alex";
        String to   = sDepo + "@montebanato.ro";
        //String to   = sAlex + "@montebanato.ro";
        // String cc   = "alex@montebanato.ro";
        String cc   = "problem@montebanato.ro";
        String from = "teleit@montebanato.ro";
        // String host = "gate.montebanato.ro";
        String host = "192.168.61.198";
        // Create properties for the Session
        Properties props = new Properties();
        // If using static Transport.send(),
        // need to specify the mail server here
        props.put("mail.host", host);
        // To see what is going on behind the scene
        props.put("mail.debug", "true");
        // Get a session
        Session session = Session.getInstance(props);
        try {
            // Get a Transport object to send e-mail
            Transport bus = session.getTransport("smtp");
            // Connect only once here
            // Transport.send() disconnects after each send
            // Usually, no username and password is required for SMTP
            bus.connect();
            //bus.connect("smtpserver.yourisp.net", "username", "password");
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            InternetAddress[] addresscc = {new InternetAddress(cc)};
            msg.setRecipients(Message.RecipientType.TO, address);
            // Parse a comma-separated list of email addresses. Be strict.
            // msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(to, true));
            msg.setRecipients(Message.RecipientType.CC, addresscc);
            // Parse comma/space-separated list. Cut some slack.
            //msg.setRecipients(Message.RecipientType.BCC,
            //                    InternetAddress.parse(to, false));

            msg.setSubject("Teletransmisie Resita - " + sDepo );
            msg.setSentDate(new Date());

            // Set message content and send
            //setTextContent(msg);
            //msg.saveChanges();
            //bus.sendMessage(msg, address);

            //setMultipartContent(msg);
            //msg.saveChanges();
            //bus.sendMessage(msg, address);

            setFileAsAttachment(msg, attachment);
            msg.saveChanges();
            bus.sendMessage(msg, address);
            bus.sendMessage(msg, addresscc);

            //setHTMLContent(msg);
            //msg.saveChanges();
            //bus.sendMessage(msg, address);

            bus.close();

        }
        catch (MessagingException mex) {
            // Prints all nested (chained) exceptions as well
            mex.printStackTrace();
            // How to access nested exceptions
            while (mex.getNextException() != null) {
                // Get next exception in chain
                Exception ex = mex.getNextException();
                ex.printStackTrace();
                if (!(ex instanceof MessagingException)) break;
                else mex = (MessagingException)ex;
            }
        }
    }



	public static void main(String[] args) {
    	// String attachment=args[0];

	}

	// A simple, single-part text/plain e-mail.
    public static void setTextContent(Message msg) throws MessagingException {
            // Set message content
            String mytxt = "This is a test of sending a " +
                            "plain text e-mail through Java.\n" +
                            "Here is line 2.";
            msg.setText(mytxt);

            // Alternate form
            msg.setContent(mytxt, "text/plain");

    }

    // A simple multipart/mixed e-mail. Both body parts are text/plain.
    public static void setMultipartContent(Message msg) throws MessagingException {
        // Create and fill first part
        MimeBodyPart p1 = new MimeBodyPart();
        p1.setText("This is part one of a test multipart e-mail.");

        // Create and fill second part
        MimeBodyPart p2 = new MimeBodyPart();
        // Here is how to set a charset on textual content
        p2.setText("This is the second part", "us-ascii");

        // Create the Multipart.  Add BodyParts to it.
        Multipart mp = new MimeMultipart();
        mp.addBodyPart(p1);
        mp.addBodyPart(p2);

        // Set Multipart as the message's content
        msg.setContent(mp);
    }

    // Set a file as an attachment.  Uses JAF FileDataSource.
    public static void setFileAsAttachment(Message msg, String filename)
             throws MessagingException {

        // Create and fill first part
        MimeBodyPart p1 = new MimeBodyPart();
        //p1.setText("This is part one of a test multipart e-mail." +
        //            "The second part is file as an attachment");
         p1.setText(" Teletransmisiile catre sediu NUMAI pe adresa teleit@montebanato.ro" );

        // Create second part
        MimeBodyPart p2 = new MimeBodyPart();

        // Put a file in the second part
        FileDataSource fds = new FileDataSource(filename);
        p2.setDataHandler(new DataHandler(fds));
        p2.setFileName(fds.getName());

        // Create the Multipart.  Add BodyParts to it.
        Multipart mp = new MimeMultipart();
        mp.addBodyPart(p1);
        mp.addBodyPart(p2);

        // Set Multipart as the message's content
        msg.setContent(mp);
    }

    // Set a single part html content.
    // Sending data of any type is similar.
    public static void setHTMLContent(Message msg) throws MessagingException {

        String html = "<html><head><title>" +
                        msg.getSubject() +
                        "</title></head><body><h1>" +
                        msg.getSubject() +
                        "</h1><p>This is a test of sending an HTML e-mail" +
                        " through Java.</body></html>";

        // HTMLDataSource is an inner class
        msg.setDataHandler(new DataHandler(new HTMLDataSource(html)));
    }

  private void jbInit() throws Exception {
  }

  /*
     * Inner class to act as a JAF datasource to send HTML e-mail content
     */
    static class HTMLDataSource implements DataSource {
        private String html;

        public HTMLDataSource(String htmlString) {
            html = htmlString;
        }

        // Return html string in an InputStream.
        // A new stream must be returned each time.
        public InputStream getInputStream() throws IOException {
            if (html == null) throw new IOException("Null HTML");
            return new ByteArrayInputStream(html.getBytes());
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("This DataHandler cannot write HTML");
        }

        public String getContentType() {
            return "text/html";
        }

        public String getName() {
            return "JAF text/html dataSource to send e-mail only";
        }
    }

} //End of class

