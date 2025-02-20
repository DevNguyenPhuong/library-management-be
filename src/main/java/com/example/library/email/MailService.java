package com.example.library.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MailService {
    JavaMailSender emailSender;
    SpringTemplateEngine templateEngine;

    @NonFinal
    @Value("${spring.mail.username}")
    protected String senderEmail;


    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    public void sendFineNotification(String toEmail, String name, int fineAmount , int newDeposit) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(senderEmail);
            helper.setTo(toEmail);
            helper.setSubject("Library Fine Notice - Balance Deduction");

            Context context = new Context();
            context.setVariable("name", name);
            context.setVariable("fineAmount", fineAmount);
            context.setVariable("newDeposit", newDeposit);
            context.setVariable("currentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));

            String htmlContent = templateEngine.process("fine-notification", context);
            helper.setText(htmlContent, true);

            emailSender.send(message);
            log.info("Fine notification email sent successfully to: {}", toEmail);

        } catch (MessagingException e) {
            log.error("Failed to send fine notification email to: {}", toEmail, e);
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public void SendPasswordNotification(String toEmail, String id, String name, String password) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(senderEmail);
            helper.setTo(toEmail);
            helper.setSubject("Library credentials Notice - Password receive");

            Context context = new Context();
            context.setVariable("id", id);
            context.setVariable("name", name);
            context.setVariable("password", password);

            String htmlContent = templateEngine.process("password-notification", context);
            helper.setText(htmlContent, true);

            emailSender.send(message);
            log.info("Password notification email sent successfully to: {}", toEmail);

        } catch (MessagingException e) {
            log.error("Failed to send password notification email to: {}", toEmail, e);
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
