package com.example.enicarthage.demo_proj_A;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmailToStudent(String to, String subject, String messageContent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);

        String htmlContent = buildAcademicEmailTemplate(subject, messageContent);
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

    public void sendEmailToStudentWithAttachment(String to, String subject, String messageContent, MultipartFile pdfFile) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8"); // true enables multipart

        helper.setTo(to);
        helper.setSubject(subject);

        String htmlContent = buildAcademicEmailTemplate(subject, messageContent);
        helper.setText(htmlContent, true);

        if (pdfFile != null && !pdfFile.isEmpty()) {
            helper.addAttachment(pdfFile.getOriginalFilename(), pdfFile);
        }

        mailSender.send(message);
    }

    private String buildAcademicEmailTemplate(String subject, String content) {
        return """
            <!DOCTYPE html>
            <html lang="fr">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>${subject}</title>
                <style>
                    @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap');
                    
                    body {
                        font-family: 'Roboto', sans-serif;
                        background-color: #f5f7fa;
                        margin: 0;
                        padding: 0;
                        color: #333;
                        line-height: 1.6;
                    }
                    
                    .email-container {
                        max-width: 600px;
                        margin: 0 auto;
                        background: white;
                        border-radius: 8px;
                        overflow: hidden;
                        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                        border-top: 4px solid #2c5aa0;
                    }
                    
                    .header {
                        padding: 25px;
                        text-align: center;
                        background-color: #2c5aa0;
                        color: white;
                    }
                    
                    .logo {
                        font-size: 24px;
                        font-weight: 700;
                        margin-bottom: 5px;
                    }
                    
                    .institution {
                        font-size: 14px;
                        opacity: 0.9;
                    }
                    
                    .content {
                        padding: 30px;
                    }
                    
                    h1 {
                        color: #2c5aa0;
                        font-size: 20px;
                        margin-top: 0;
                        margin-bottom: 20px;
                        font-weight: 600;
                    }
                    
                    p {
                        font-size: 15px;
                        margin-bottom: 20px;
                    }
                    
                    .message-box {
                        background-color: #f8f9fa;
                        border-left: 3px solid #2c5aa0;
                        padding: 15px;
                        margin: 25px 0;
                        border-radius: 0 4px 4px 0;
                        font-size: 15px;
                    }
                    
                    .footer {
                        padding: 20px;
                        text-align: center;
                        background-color: #f8f9fa;
                        color: #666;
                        font-size: 12px;
                        border-top: 1px solid #e9ecef;
                    }
                    
                    .signature {
                        margin-top: 30px;
                        font-style: italic;
                        color: #555;
                    }
                </style>
            </head>
            <body>
                <div class="email-container">
                    <div class="header">
                        <div class="logo">EnicarCerts</div>
                        <div class="institution">École Nationale d'Ingénieurs de Carthage</div>
                    </div>
                    
                    <div class="content">
                        <h1>${subject}</h1>
                        
                        <p>Cher(e) étudiant(e),</p>
                        
                        <div class="message-box">
                            ${content}
                        </div>
                        
                        <p>Veuillez trouver ci-dessus le message de votre enseignant.</p>
                        
                        <div class="signature">
                            <p>Cordialement,</p>
                            <p>L'équipe pédagogique EnicarCerts</p>
                        </div>
                    </div>
                    
                    <div class="footer">
                        © 2025 EnicarCerts - Tous droits réservés.<br>
                        <small>BP 676, Charguia II, 2035 Tunis Carthage</small>
                    </div>
                </div>
            </body>
            </html>
            """.replace("${subject}", subject)
                .replace("${content}", content);
    }
}