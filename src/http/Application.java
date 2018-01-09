package http;


import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;


public class Application {
    public static void main(String[] args) throws IOException {

        try (
                CloseableHttpClient client = HttpClients.createDefault();
                CloseableHttpResponse response = client.execute(new HttpGet("https://api.github.com/users/sapega"))
        ) {
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String data = IOUtils.toString(entity.getContent());

                System.out.println("Data: " + data);
            }

            System.out.println();
            for (Header header : response.getAllHeaders()) {
                System.out.println(header.getName() + " : " + header.getValue());
            }

            System.out.println();
            System.out.println("Protocol version: " + response.getProtocolVersion());
            System.out.println("Status code: " + response.getStatusLine().getStatusCode());
            System.out.println("Reason phrase: " + response.getStatusLine().getReasonPhrase());
            System.out.println("Status line: " + response.getStatusLine().toString());
        } catch (Throwable cause) {
            cause.printStackTrace();
        }




        final String username = "usergithub@yandex.by";
        final String password = "password123";

        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.yandex.ru");
        props.put("mail.smtp.socketFactory.port", 587);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", 587);


        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                }
        );
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("usergithub@yandex.by"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("sapeg@bk.ru"));
            message.setSubject("Test");
            message.setText("Use the repo ");
            Transport.send(message);

            System.out.println("The message was sent!");


        } catch (Exception e) {

            System.out.println("Something went wrong " + e);

        }
    }

}



