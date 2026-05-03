package com.example.mailingService.repository;

import com.example.mailingService.model.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailingRepository extends JpaRepository<Mail, Long> {

}
