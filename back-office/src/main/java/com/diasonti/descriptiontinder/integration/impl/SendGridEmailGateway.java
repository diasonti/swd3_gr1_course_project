package com.diasonti.descriptiontinder.integration.impl;

import com.diasonti.descriptiontinder.data.util.DataFile;
import com.diasonti.descriptiontinder.integration.EmailGateway;
import com.sendgrid.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
@Profile("!dev")
public class SendGridEmailGateway implements EmailGateway {

    private final Logger log = LoggerFactory.getLogger(SendGridEmailGateway.class);

    private final SendGrid gateway;
    private final Email from;

    public SendGridEmailGateway() {
        final String apiKey = Optional.ofNullable(System.getenv("SENDGRID_API_KEY"))
                .orElseThrow(() -> new IllegalStateException("SendGrid api token not found in env variables"));
        this.gateway = new SendGrid(apiKey);
        this.from = new Email("no-reply@diasonti.com");
    }

    @Override
    public void sendEmail(String to, String subject, String text, Iterable<DataFile> attachments) {
        final Email recipient = new Email(to);
        final Content content = new Content("text/html", text);
        final Mail mail = new Mail(from, subject, recipient, content);
        for(DataFile file : attachments) {
            final String filename = file.getFilename();
            final InputStream data = new ByteArrayInputStream(file.getContent());
            mail.addAttachments(new Attachments.Builder(filename, data).build());
        }

        final Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = gateway.api(request);
        } catch (IOException e) {
            log.error("sendEmail error", e);
        }
    }
}
