package com.maverickstube.maverickshub.services;

import com.maverickstube.maverickshub.dto.Recipient;
import com.maverickstube.maverickshub.dto.Sender;
import com.maverickstube.maverickshub.dto.requests.BrevoMailRequest;
import com.maverickstube.maverickshub.dto.requests.SendMailRequest;
import com.maverickstube.maverickshub.dto.response.BrevoMailResponse;
import com.maverickstube.maverickshub.security.config.MailConfig;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;

@Service
@AllArgsConstructor
public class MavericksHubMailService implements MailService{
    private final MailConfig mailConfig;

    @Override
    public String sendMail(SendMailRequest sendMailRequest){
        RestTemplate restTemplate = new RestTemplate();
        String url = mailConfig.getMailApiUrl();
        BrevoMailRequest request = new BrevoMailRequest();
        request.setSubject(sendMailRequest.getSubject());
        request.setSender(new Sender());
        request.setRecipients(
                List.of(new Recipient(
                sendMailRequest.getRecipientName(),sendMailRequest.getRecipientMail())));
        request.setContent(sendMailRequest.getContent());
        HttpHeaders headers  = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key",mailConfig.getMailApiKey());
        headers.set("accept",APPLICATION_JSON.toString());
        RequestEntity<?>httpRequest = new RequestEntity<>(request,headers, HttpMethod.POST, URI.create(url));
       ResponseEntity<BrevoMailResponse> response =  restTemplate.postForEntity(url,httpRequest, BrevoMailResponse.class);

       if(response.getBody() != null && response.getStatusCode()== HttpStatus.valueOf(201))
           return "mail sent successfully";
       else throw new RuntimeException("mail sending failed");

    }
}
