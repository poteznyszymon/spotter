package com.example.spotter.port.out;

public interface EmailPort {
    void sendPlainText(String to, String subject, String text);
    void sendHtml(String to, String subject, String htmlBody);
}
