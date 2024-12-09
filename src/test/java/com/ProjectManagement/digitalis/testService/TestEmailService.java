package com.ProjectManagement.digitalis.testService;

import com.ProjectManagement.digitalis.service.EmailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.verify;

/**
 * @author Aguibou sow
 * @date 2024-12-05 16:13
 * @package com.ProjectManagement.digitalis.testService
 * @project digitalis
 */
public class TestEmailService {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailService emailService;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void tesEmailSentSuccess(){

        String to = "test@gmail.com";
        String subject = "sujet";
        String content = "message";


        emailService.sendMail(to,subject,content);

        ArgumentCaptor<SimpleMailMessage> messageCapture = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(javaMailSender).send(messageCapture.capture());

        SimpleMailMessage sendMsg = messageCapture.getValue();

        Assertions.assertEquals(to, sendMsg.getTo()[0]);
        Assertions.assertEquals(subject, sendMsg.getSubject());
        Assertions.assertEquals(content, sendMsg.getText());

    }

}
