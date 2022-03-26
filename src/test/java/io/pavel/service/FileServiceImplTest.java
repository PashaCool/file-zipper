package io.pavel.service;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;

class FileServiceImplTest {

    private final FileService fileService = new FileServiceImpl();

    @Test
    void convertMultiPartFileToFile() throws IOException {
        String filename = "input.txt";
        String fileContent = "Test content";
        MultipartFile multipartFile = new MockMultipartFile("file", filename, MediaType.TEXT_PLAIN_VALUE, fileContent.getBytes());

        File file = fileService.convertMultiPartFileToFile(multipartFile);

        assertThat(file)
            .exists()
            .hasFileName(filename)
            .content().isEqualTo(fileContent);
    }
}