package edu.bbte.idde.bvim2209.spring.backend.services;

import edu.bbte.idde.bvim2209.spring.backend.model.User;
import edu.bbte.idde.bvim2209.spring.exceptions.TooManyRequestsException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final UserService userService;

    @Autowired
    public EmailService(JavaMailSender mailSender, UserService userService) {
        this.mailSender = mailSender;
        this.userService = userService;
    }

    public void sendPasswordResetEmail(String email, String token) throws MessagingException {
        User user = userService.getByEmail(email);
        Instant lastPasswordReset = user.getLastChangedPassword();
        if (lastPasswordReset == null || Duration.between(lastPasswordReset, Instant.now()).toSeconds() >= 1) {
            user.setLastChangedPassword(Instant.now());
            userService.updateUser(user);
            String resetLink = "http://localhost:5173/change-password?token=" + token;
            String subject = "Password Reset Request";
            String content = "<p>Hello,</p>"
                    + "<p>You requested to reset your password. Click the link below:</p>"
                    + "<p><a href=\"" + resetLink + "\">Reset Password</a></p>"
                    + "<p>If you did not request this, ignore this email.</p>";
            sendEmail(email, subject, content);
        } else {
            throw new TooManyRequestsException("You can only send one password reset request per day.");
        }
    }

    public void sendEmail(String to, String subject, String content) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content, true);
        mimeMessageHelper.setFrom("ballavalentin18@gmail.com");

        mailSender.send(mimeMessage);

    }
}
