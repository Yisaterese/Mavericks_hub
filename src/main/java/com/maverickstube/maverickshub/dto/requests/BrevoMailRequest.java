package com.maverickstube.maverickshub.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maverickstube.maverickshub.dto.Recipient;
import com.maverickstube.maverickshub.dto.Sender;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BrevoMailRequest {
    private Sender sender;
    @JsonProperty("to")
    private List<Recipient> recipients;
    private String subject;
    @JsonProperty("htmlContent")
    private String content;


}
