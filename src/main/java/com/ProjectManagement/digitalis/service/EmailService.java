package com.ProjectManagement.digitalis.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender javaMailSender;

    @Value("${sendMail.mail}")
    private String mail;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String to, String subject, String content) {
        logger.info("Tentative d'envoi d'email à : {} avec le sujet : {}", to, subject);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        simpleMailMessage.setFrom(mail);

        try {
            javaMailSender.send(simpleMailMessage);
            logger.info("Email envoyé avec succès à : {}", to);
        } catch (Exception e) {
            logger.error("Échec de l'envoi de l'email à {} avec le sujet {}. Erreur : {}", to, subject, e.getMessage());
        }
    }
}
