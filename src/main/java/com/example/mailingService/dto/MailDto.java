package com.example.mailingService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class MailDto {
    private Long id;
    private String from;  //this is a name
    private String to;      // this is an email address
    private String subject;
    private String content;
    private Long UserId;
    private Status status;

    public enum Status{
        sending,
        sent,
    }
    public MailDto(){}
}
