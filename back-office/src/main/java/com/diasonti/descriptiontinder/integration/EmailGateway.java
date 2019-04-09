package com.diasonti.descriptiontinder.integration;

import com.diasonti.descriptiontinder.data.util.DataFile;

public interface EmailGateway {

    void sendEmail(String to, String subject, String text, Iterable<DataFile> attachments);

}
