package io.pavel.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import io.pavel.service.ZipService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileController.class)
class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ZipService zipService;

    @Test
    void zipFiles() throws Exception {
        String filename = "input.txt";
        String fileContent = "Test content";
        MockMultipartFile multipartFile = new MockMultipartFile("file", filename, MediaType.TEXT_PLAIN_VALUE, fileContent.getBytes());

        mockMvc.perform(multipart("/api/v1/zip").file(multipartFile))
            .andExpect(status().isOk());
    }
}