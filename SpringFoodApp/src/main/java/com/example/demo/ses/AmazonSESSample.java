package com.example.demo.ses;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
// https://docs.aws.amazon.com/ses/latest/DeveloperGuide/send-using-smtp-java.html
public class AmazonSESSample {

    // Replace sender@example.com with your "From" address.
    // This address must be verified.
	@Value("${foodapp.aws.smtp.fromemail}")
    private String FROM;
	@Value("${foodapp.aws.smtp.fromname}")
    private String FROMNAME;
    // Replace recipient@example.com with a "To" address. If your account 
    // is still in the sandbox, this address must be verified.
	@Value("${foodapp.aws.smtp.toemail}")
    private String TO;
    
    // Replace smtp_username with your Amazon SES SMTP user name.
	@Value("${foodapp.aws.smtp.username}")
    private String SMTP_USERNAME;
    
    // Replace smtp_password with your Amazon SES SMTP password.
	@Value("${foodapp.aws.smtp.password}")
    private String SMTP_PASSWORD;
    
    // The name of the Configuration Set to use for this message.
    // If you comment out or remove this variable, you will also need to
    // comment out or remove the header below.
    //static final String CONFIGSET = "ConfigSet";
    
    // Amazon SES SMTP host name. This example uses the US West (Oregon) region.
    // See https://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html#region-endpoints
    // for more information.
	@Value("${foodapp.aws.smtp.host}")
    private String HOST;
    
    // The port you will connect to on the Amazon SES SMTP endpoint. 
    static final int PORT = 587;
    
    static final String SUBJECT = "Amazon SES test (SMTP interface accessed using Java)";
    
    private String body;
    
    

    public String getBody() {
		return body;
	}



	public void setBody(String body) {
		this.body = body;
	}



	public AmazonSESSample(String body) {
		super();
		this.body = body;
	}



	public AmazonSESSample() {
		super();
	}



	public void sendmail(String toemail, String subject, String body) {
try {
    // Create a Properties object to contain connection configuration information.
	Properties props = System.getProperties();
	props.put("mail.transport.protocol", "smtp");
	props.put("mail.smtp.port", PORT); 
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.auth", "true");

    // Create a Session object to represent a mail session with the specified properties. 
	Session session = Session.getDefaultInstance(props);

    // Create a message with the specified information. 
    MimeMessage msg = new MimeMessage(session);
    msg.setFrom(new InternetAddress(FROM,FROMNAME));
    msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toemail));
    msg.setSubject(subject);
    msg.setContent(body,"text/html");
    
    // Add a configuration set header. Comment or delete the 
    // next line if you are not using a configuration set
    //msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);
        
    // Create a transport.
    Transport transport = session.getTransport();
                
    // Send the message.
    try
    {
        System.out.println("Sending...");
        
        // Connect to Amazon SES using the SMTP username and password you specified above.
        transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
    	
        // Send the email.
        transport.sendMessage(msg, msg.getAllRecipients());
        System.out.println("Email sent!");
    }
    catch (Exception ex) {
        System.out.println("The email was not sent.");
        System.out.println("Error message: " + ex.getMessage());
    }
    finally
    {
        // Close and terminate the connection.
        transport.close();
    }
} catch (Exception e) {
	System.out.println("Error from Amazon ses sample  "+e.toString());
}

    }
}
