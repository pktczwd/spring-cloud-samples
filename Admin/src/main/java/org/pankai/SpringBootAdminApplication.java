package org.pankai;

import de.codecentric.boot.admin.config.EnableAdminServer;
import de.codecentric.boot.admin.notify.MailNotifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableAutoConfiguration
@EnableAdminServer
@EnableDiscoveryClient
public class SpringBootAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAdminApplication.class, args);
    }

    @Bean
    public MailSender mailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setDefaultEncoding("UTF-8");
        sender.getJavaMailProperties().put("mail.smtp.starttls.enable", "true");
        sender.getJavaMailProperties().put("mail.smtp.timeout", TimeUnit.SECONDS.toMillis(5));
        sender.setHost("smtp.163.com");
        sender.setUsername("pktczwd0@163.com");
        sender.setPassword("xxxxxxx");
        sender.setPort(25);
        return sender;
    }

    @Bean
    public MailNotifier mailNotifier(MailSender mailSender) {
        MailNotifier mailNotifier = new MailNotifier(mailSender);
        mailNotifier.setTo(new String[]{"pktczwd@qq.com"});
        mailNotifier.setFrom("pktczwd0@163.com");
        return mailNotifier;
    }

}
