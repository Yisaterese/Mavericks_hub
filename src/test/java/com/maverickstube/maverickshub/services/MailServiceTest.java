package com.maverickstube.maverickshub.services;

import com.maverickstube.maverickshub.dto.requests.SendMailRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class MailServiceTest {
    @Autowired
    MailService mailService;

    @Test
    void sendEmailTest() {
        String email = "teresejosephyisa@gmail.com";
        SendMailRequest sendMailRequest = new SendMailRequest();
        sendMailRequest.setRecipientMail(email);
        sendMailRequest.setSubject("Test Mail");
        sendMailRequest.setRecipientName("Joseph");
        sendMailRequest.setContent("<p>This is a test email.</p>");
        String response = mailService.sendMail(sendMailRequest);
        assertThat(response).isNotNull();
        assertThat(response).containsIgnoringCase("success");
    }


}
