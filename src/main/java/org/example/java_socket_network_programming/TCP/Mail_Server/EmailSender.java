package org.example.java_socket_network_programming.TCP.Mail_Server;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import java.util.Scanner;

public class EmailSender {
    private String Server_Email;
    private String Server_Password;
    private Properties properties;
    private Session session;

    public EmailSender() {
        this.Server_Email = Dotenv.configure().filename(".env").load().get("EMAIL");
        this.Server_Password = Dotenv.configure().filename(".env").load().get("PASSWORD");
        this.properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", Dotenv.configure().filename(".env").load().get("MAIL_SERVICE"));
        properties.put("mail.smtp.port", Dotenv.configure().filename(".env").load().get("MAIL_PORT"));
        properties.put("mail.debug", "true");
    }

    public String getServer_Password() {
        return Server_Password;
    }
    public String getServer_Email() {
        return Server_Email;
    }
    public Properties getProperties() {
        return properties;
    }
    public Session getSession() {
        return session;
    }


    public void ActivateSendingSession(){
        this.session = Session.getInstance(getProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(getServer_Email(),getServer_Password());
            }
        });
    }

    public void SendEmail (String clientEmail, String emailSubject, String emailContent){
        try {
            // Create a message
            Message message = new MimeMessage(getSession());
            message.setFrom(new InternetAddress(getServer_Email()));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(clientEmail)
            );
            message.setSubject(emailSubject);
            message.setText(emailContent);

            // Send the message
            Transport.send(message);
            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        EmailSender emailSender = new EmailSender();
        emailSender.ActivateSendingSession();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter client email : ");
        String clientEmail = scanner.nextLine();
        System.out.println("Enter your subject : ");
        String emailSubject = scanner.nextLine();
        System.out.println("Enter your content : ");
        String emailContent = scanner.nextLine();

        emailSender.SendEmail(clientEmail,emailSubject,emailContent);


    }
}


