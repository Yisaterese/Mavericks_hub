package com.maverickstube.maverickshub.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Data {
    private Sender sender;
    @JsonProperty("to")
    private List<Recipient> recieverList;
    private String subject;
    @JsonProperty("htmlfile")
    private String content;

}
