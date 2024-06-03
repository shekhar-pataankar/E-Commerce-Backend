package com.EmailService.Consumer;

import com.EmailService.EmailUtils.EmailUtil;
import com.EmailService.dtos.SendEmailMessageDto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Service
public class SendEmailConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(
            id = "sendEmailServieConsumerGroup",
            topics = {"sendEmail"}
    )
    public void handleSendEmail(String sendEmailDto) throws Exception{

        SendEmailMessageDto message = objectMapper.readValue(sendEmailDto, SendEmailMessageDto.class);

        System.out.println("got the send email message");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("shekharpatankar72@gmail.com", "pgkeirnrwofbdyrq");
            }
        };
        Session session = Session.getInstance(props, auth);

        EmailUtil.sendEmail(session, message.getTo(), message.getSubject(), message.getBody());

    }
}
