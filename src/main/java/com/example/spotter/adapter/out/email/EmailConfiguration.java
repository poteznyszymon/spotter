package com.example.spotter.adapter.out.email;

import com.example.spotter.port.out.EmailPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class EmailConfiguration {

    private final JavaMailSender javaMailSender;

    public EmailConfiguration(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Bean
    public EmailPort emailPort() {
        return new EmailAdapter(javaMailSender);
    }

}
