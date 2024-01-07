package com.masaGreen.presta.services;

import com.masaGreen.presta.models.entities.AppUser;
import com.masaGreen.presta.repositories.AppUserRepository;
import com.masaGreen.presta.utils.Utils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    @Value("${spring.mail.username}")
    private String sender;

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public void sendEmailVerificationRequest(AppUser appUser) {
        Context context = new Context();
        String link = Utils.BASE_URL+"/v1/app-user/validate-app-user/" + appUser.getValidationString();
        context.setVariable("name", appUser.getFirstName());
        context.setVariable("link", link);
        String content = templateEngine.process("verify_user_email.html", context);
        try {
            sendMailContent(content, appUser.getEmail(), "Verify Email");
        } catch (MessagingException ex) {
            log.error("error sending verification request email {}", ex.getMessage());

        }
    }

    public void sendPinInfoEmail(String email, String pin, String firstName) {
        Context context = new Context();

        context.setVariable("name", firstName);
        context.setVariable("pin", pin);
        String content = templateEngine.process("pin_info_email.html", context);
        try {
            sendMailContent(content, email, "Customer Pin");
        } catch (MessagingException ex) {
            log.info("error sending pin email {}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }


    private void sendMailContent(String content, String email, String subject) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(sender);
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(content, true);
        javaMailSender.send(message);
    }
}
