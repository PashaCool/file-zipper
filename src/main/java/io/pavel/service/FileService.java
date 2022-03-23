package io.pavel.service;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

/**
 * Interface for converting input MultipartFile to File
 */
public interface FileService {

    File convertMultiPartFileToFile(MultipartFile file) throws IOException;
}
