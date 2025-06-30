package com.juaracoding.pcmspringboot4.config;

import com.juaracoding.pcmspringboot4.security.Crypto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:smtp.properties")
public class SMTPConfig {
    private static String emailUsername;
    private static String emailPassword;
    private static String emailHost;
    private static String emailPort;
    private static String emailPortSSL;
    private static String emailPortTLS;
    private static String emailPortAuth;
    private static String emailStartTLSEnable;
    private static String emailSMTPSocketFactory;
    private static String emailSMTPTimeout;

    public static String getEmailUsername() {
        return emailUsername;
    }

    @Value("${email.username}")
    private void setEmailUsername(String emailUsername) {
        this.emailUsername = Crypto.performDecrypt(emailUsername);
    }

    public static String getEmailPassword() {
        return emailPassword;
    }

    @Value("${email.password}")
    private void setEmailPassword(String emailPassword) {
        this.emailPassword = Crypto.performDecrypt(emailPassword);
    }

    public static String getEmailHost() {
        return emailHost;
    }

    @Value("${email.host}")
    private void setEmailHost(String emailHost) {
        this.emailHost = Crypto.performDecrypt(emailHost);
    }

    public static String getEmailPort() {
        return emailPort;
    }

    @Value("${email.port}")
    private void setEmailPort(String emailPort) {
        this.emailPort = emailPort;
    }

    public static String getEmailPortSSL() {
        return emailPortSSL;
    }

    @Value("${email.port.ssl}")
    private void setEmailPortSSL(String emailPortSSL) {
        this.emailPortSSL = emailPortSSL;
    }

    public static String getEmailPortTLS() {
        return emailPortTLS;
    }

    @Value("${email.port.tls}")
    private void setEmailPortTLS(String emailPortTLS) {
        this.emailPortTLS = emailPortTLS;
    }


    public static String getEmailPortAuth() {
        return emailPortAuth;
    }

    @Value("${email.auth}")
    private void setEmailPortAuth(String emailPortAuth) {
        this.emailPortAuth = emailPortAuth;
    }

    public static String getEmailStartTLSEnable() {
        return emailStartTLSEnable;
    }

    @Value("${email.starttls.enable}")
    private void setEmailStartTLSEnable(String emailStartTLSEnable) {
        this.emailStartTLSEnable = emailStartTLSEnable;
    }

    public static String getEmailSMTPSocketFactory() {
        return emailSMTPSocketFactory;
    }

    @Value("${email.smtp.socket.factory.class}")
    private void setEmailSMTPSocketFactory(String emailSMTPSocketFactory) {
        this.emailSMTPSocketFactory = emailSMTPSocketFactory;
    }

    public static String getEmailSMTPTimeout() {
        return emailSMTPTimeout;
    }

    @Value("${email.smtp.timeout}")
    private void setEmailSMTPTimeout(String emailSMTPTimeout) {
        this.emailSMTPTimeout = emailSMTPTimeout;
    }
}