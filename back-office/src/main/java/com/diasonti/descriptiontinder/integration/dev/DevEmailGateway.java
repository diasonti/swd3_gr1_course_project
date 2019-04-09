package com.diasonti.descriptiontinder.integration.dev;

import com.diasonti.descriptiontinder.data.util.DataFile;
import com.diasonti.descriptiontinder.integration.EmailGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class DevEmailGateway implements EmailGateway {

    private final Logger log = LoggerFactory.getLogger(DevEmailGateway.class);

    @Override
    public void sendEmail(String to, String subject, String text, Iterable<DataFile> attachments) {
        final StringBuilder attachSB = new StringBuilder("[");
        for(DataFile file : attachments) {
            attachSB.append(file.getFilename()).append(", ");
        }
        attachSB.append("]");
        log.info("Email \"sent\" to: '{}', subject: '{}', text: '{}', attachments: '{}'",
                to, subject, text, attachSB.toString().replace(", ]", "]"));
    }
}
