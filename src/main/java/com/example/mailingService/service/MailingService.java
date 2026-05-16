package com.example.mailingService.service;

import com.example.mailingService.dto.MailDto;
import com.example.mailingService.dto.OrderDto;
import com.example.mailingService.model.Mail;
import com.example.mailingService.repository.MailingRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;

@Service
public class MailingService {

    private MailingRepository mailingRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    public MailingService(MailingRepository mailingRepository) {
        this.mailingRepository = mailingRepository;
    }
    public void sendHtmlMail(MailDto mail)
    throws MessagingException
    {
        Context context = new Context();
        Mail newMail = mailBuilder(mail);
        context.setVariable("content", newMail.getContent());
        context.setVariable("subject", newMail.getSubject());
        context.setVariable("to", newMail.getReceiver());
        context.setVariable("timeStamp", newMail.getTimestamp().toString());
        String html = templateEngine.process("mailingTemplate", context);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(mail.getTo());
        helper.setSubject(mail.getSubject());
        helper.setText(html, true);
        mailSender.send(mimeMessage);
        mailingRepository.save(newMail);

    }
    public void sendReciept(OrderDto order)
    throws MessagingException
    {
     Context context = new Context();
     context.setVariable("receipt", order);
     String html = templateEngine.process("recieptTemplate", context);
     MimeMessage mimeMessage = mailSender.createMimeMessage();
     MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
     helper.setTo(order.getEmail());
     helper.setSubject("A spark payment");
     helper.setText(html, true);
     mailSender.send(mimeMessage);
     mailingRepository.save(
             receiptMailBuilder(order)
     );
    }
    public void sendResetLink(String link, String email) throws MessagingException{
        Context  context = new Context();
        context.setVariable("resetLink", link);
        String html = templateEngine.process("passwordReset", context);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(email);
        helper.setSubject("Reset link");
        helper.setText(html, true);
        mailSender.send(mimeMessage);
        mailingRepository.save(
                Mail.builder()
                        .status("sent")
                        .content("PASSWORD RESET")
                        .timestamp(LocalDateTime.now())
                        .build()
        );


    }
    public static Mail mailBuilder(MailDto mailDto)
    {
        return Mail.builder()
                .content(mailDto.getContent())
                .receiver(mailDto.getTo())
                .sender(mailDto.getFrom())
                .timestamp(LocalDateTime.now())
                .build();

    }
    public static Mail receiptMailBuilder(OrderDto order)
    {
        return Mail.builder()
                .content(order.getCode().toString())
                .receiver(order.getEmail())
                .sender("A spark tech.")
                .timestamp(LocalDateTime.now())
                .build();
    }


}
