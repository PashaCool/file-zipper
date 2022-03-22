package io.pavel.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

/**
 * Service for zipping files and write to a response
 */
public interface ZipService {

    void zipFilesAndWriteToResponse(MultipartFile[] srcFiles, HttpServletResponse response) throws IOException;
}
