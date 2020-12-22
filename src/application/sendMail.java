package application;

import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

public class sendMail {
	public static boolean sendmail(String msg,String email, String reason) {
 		Properties props=new Properties();
         props.put("mail.smtp.host","smtp.gmail.com");
         props.put("mail.smtp.port",465);
         props.put("mail.smtp.user","avbank2@gmail.com"); // your email id
         props.put("mail.smtp.auth",true);
         props.put("mail.smtp.starttls.enable",true);
         props.put("mail.smtp.debug",true);
         props.put("mail.smtp.socketFactory.port",465);
         props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
         props.put("mail.smtp.socketFactory.fallback",false); 
         
         try {             
                 Session session = Session.getDefaultInstance(props, null);
                 session.setDebug(true);
                 MimeMessage message = new MimeMessage(session);
                 message.setText(msg);	// msg send to the email id
                 message.setSubject("AV Bank ... Bank of Benifits - "+reason);
                 message.setFrom(new InternetAddress("avbank2@gmail.com"));
                 message.addRecipient(RecipientType.TO, new InternetAddress(email.trim()));		// email of the reciever
                 message.saveChanges();
                 try
                 {
                 Transport transport = session.getTransport("smtp");
                 transport.connect("smtp.gmail.com","avbank2@gmail.com","Logitech@2");
                 transport.sendMessage(message, message.getAllRecipients());
                 transport.close();
                               
                  
                 }catch(Exception e)
                 {
                     JOptionPane.showMessageDialog(null,"Check your Internet Connection");
                     return false;
                 }              
                 
         } catch (Exception e) {
             e.printStackTrace();  
             JOptionPane.showMessageDialog(null,e);
             
         }
         return true;

 	}
}
