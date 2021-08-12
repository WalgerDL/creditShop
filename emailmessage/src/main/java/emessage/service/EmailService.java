package emessage.service;

public interface EmailService {
    void sendSubjectEmail(String to, String subject, String message);
}
