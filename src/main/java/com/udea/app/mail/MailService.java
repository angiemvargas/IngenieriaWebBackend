package com.udea.app.mail;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service("EmailService")
public class MailService {

    @Value("${spring.mail.username}")
    private String mailServerUsername;

    @Autowired
    JavaMailSender emailSender;

    public void sendSimpleMessage( String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailServerUsername);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            emailSender.send(message);
        } catch (MailException exception) {
            log.info("Ocurrio un error al enviar correo a {}", to);
        }
    }

}
