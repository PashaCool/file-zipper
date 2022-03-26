package io.pavel.service;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ZipServiceImplTest {

    @InjectMocks
    private ZipServiceImpl zipService;
    @Mock
    private FileService fileService;
    @Mock
    private CollectClientHostService collectClientHostService;

    @Test
    void zipFilesAndWriteToResponse() throws IOException {
        //given
        String filename = "input.txt";
        String fileContent = "Test content";
        MultipartFile multipartFile = new MockMultipartFile("file", filename, MediaType.TEXT_PLAIN_VALUE, fileContent.getBytes());

        HttpServletResponse response = new MockHttpServletResponse();

        String host = "127.0.0.1";
        when(fileService.convertMultiPartFileToFile(multipartFile)).thenReturn(new File(filename));

        //when
        zipService.zipFilesAndWriteToResponse(new MultipartFile[]{multipartFile}, response, host);

        //then
        verify(collectClientHostService).saveClientHost(host);
        verify(fileService).convertMultiPartFileToFile(multipartFile);

        assertEquals(200, response.getStatus());
        assertEquals("application/zip", response.getContentType());
    }
}