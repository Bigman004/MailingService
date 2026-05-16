package com.example.mailingService.controller;

import com.example.mailingService.dto.MailDto;
import com.example.mailingService.dto.OrderDto;
import com.example.mailingService.dto.PasswordLinkDto;
import com.example.mailingService.repository.MailingRepository;
import com.example.mailingService.service.MailingService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MailController {
    @Autowired
    private MailingService mailingService;
    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to mailing service!";

    }
    @GetMapping("/send_mail")
    public ResponseEntity<?> sendMail(){
        return new ResponseEntity<>(MailDto.builder().build(), HttpStatus.OK);
    }

    @PostMapping("/send_mail")
    public ResponseEntity<?> sendMail(@RequestBody MailDto mailDto){
        try{
            mailingService.sendHtmlMail(mailDto);
            return new ResponseEntity<>("mail has been sent to server", HttpStatus.OK);
        }
        catch(MessagingException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PostMapping("/send_reciept")
    public ResponseEntity<?> sendReciept(@RequestBody OrderDto orderDto){
        try{
            mailingService.sendReciept(orderDto);
            return new ResponseEntity<>("receipt has been sent to recipient", HttpStatus.OK);
        }
        catch(MessagingException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/send_password_link")
    public ResponseEntity<?> sendPasswordLink(@RequestBody PasswordLinkDto request){
        try {
            mailingService.sendResetLink(request.getLink(), request.getEmail());
            return new ResponseEntity<>("password link has been sent to server", HttpStatus.OK);
        } catch (MessagingException e) {
            log.error("Sending password link failed", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
