package com.maverickstube.maverickshub.dto.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SendMailRequest {
    private String  recipientMail;
    private String subject;
    private String recipientName;
    private String content;
}
