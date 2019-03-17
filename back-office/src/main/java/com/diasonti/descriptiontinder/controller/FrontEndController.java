package com.diasonti.descriptiontinder.controller;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class FrontEndController {

    private final Logger log = LoggerFactory.getLogger(FrontEndController.class);

    @GetMapping("/index")
    @ResponseBody
    public ResponseEntity<String> indexPage() {
        final String content = getFileContentString("static/index.html");
        if (content == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(content);
    }

    @GetMapping("/**")
    public String anyPage() {
        return "forward:/index";
    }

    @GetMapping("/css/{fileName}")
    @ResponseBody
    public ResponseEntity<String> css(@PathVariable String fileName) {
        final String content = getFileContentString("static/css/" + fileName);
        if (content == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().contentType(MediaType.valueOf("text/css")).body(content);
    }

    @GetMapping("/js/{fileName}")
    @ResponseBody
    public ResponseEntity<String> js(@PathVariable String fileName) {
        final String content = getFileContentString("static/js/" + fileName);
        if (content == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().contentType(MediaType.valueOf("text/javascript")).body(content);
    }

    @GetMapping("/img/{fileName}")
    public ResponseEntity<byte[]> img(@PathVariable String fileName) {
        final String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);
        final byte[] content = getFileContentBytes("static/img/" + fileName);
        if (content == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().contentType(getImageMediaType(fileExtension)).body(content);
    }

    @GetMapping("/favicon.ico")
    public String favicon() {
        return "favicon.ico";
    }

    private String getFileContentString(final String path) {
        String content;
        try {
            final ClassPathResource resource = new ClassPathResource(path);
            content = IOUtils.toString(resource.getInputStream(), "UTF-8");
        } catch (IOException e) {
            log.error("File loading error", e);
            content = null;
        }
        return content;
    }

    private byte[] getFileContentBytes(final String path) {
        byte[] bytes;
        try {
            final ClassPathResource resource = new ClassPathResource(path);
            bytes = IOUtils.toByteArray(resource.getInputStream());
        } catch (IOException e) {
            log.error("File loading error", e);
            bytes = null;
        }
        return bytes;
    }

    private MediaType getImageMediaType(final String fileExtension) {
        MediaType type;
        switch (fileExtension) {
            case "bmp":
                type = MediaType.valueOf("image/bmp");
                break;
            case "gif":
                type = MediaType.valueOf("image/gif");
                break;
            case "ico":
                type = MediaType.valueOf("image/vnd.microsoft.icon");
                break;
            case "jpeg":
            case "jpg":
                type = MediaType.valueOf("image/jpeg");
                break;
            case "png":
                type = MediaType.valueOf("image/png");
                break;
            case "tif":
            case "tiff":
                type = MediaType.valueOf("image/tiff");
                break;
            case "webp":
                type = MediaType.valueOf("image/webp");
                break;
            case "svg":
                type = MediaType.valueOf("image/svg+xml");
                break;
            default:
                log.warn("File of unknown content requested. Extension: '{}'", fileExtension);
                type = MediaType.APPLICATION_OCTET_STREAM;
        }
        return type;
    }

}
