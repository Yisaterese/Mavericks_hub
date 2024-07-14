package com.maverickstube.maverickshub.services;

import com.maverickstube.maverickshub.dto.requests.SendMailRequest;

public interface MailService {

    String sendMail(SendMailRequest sendMailRequest);
}
