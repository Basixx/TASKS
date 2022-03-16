package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
@ExtendWith(MockitoExtension.class)
class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void shouldSendEmail(){
        //Given
        Mail mail = new Mail.MailBuilder().mailTo("test@test.com").subject("Test").message("Test Message").toCc("cc@cc.com").build();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        if(mail.getToCc() != null) {
            mailMessage.setCc(mail.getToCc());
        }
        mailMessage.setText(mail.getMessage());

        //When
        simpleEmailService.send(mail);

        //Then
        verify(javaMailSender, times(1)).send(mailMessage);
    }

    /*@Test
    public void shouldSendEmailWithOptional(){
        //Given
        Mail mail = new Mail.MailBuilder().mailTo("test@test.com").subject("Test").message("Test Message").toCc("cc@cc.com").build();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());

        Optional <String> ccOpt = Optional.ofNullable(mail.getToCc());

        *//*if(ccOpt.isPresent()) {
            mailMessage.setCc(mail.getToCc());
        }*//*

        *//*if(ccOpt.isPresent()) {
            mailMessage.setCc(ccOpt.get());
        }
        mailMessage.setText(mail.getMessage());*//*

        ccOpt.ifPresent(cc -> mailMessage.setCc(cc));
        //albo
        //Optional.ofNullable(mail.getToCc()).ifPresent(cc -> mailMessage.setCc(cc));

        //When
        simpleEmailService.send(mail);

        //Then
        verify(javaMailSender, times(1)).send(mailMessage);
    }*/

}