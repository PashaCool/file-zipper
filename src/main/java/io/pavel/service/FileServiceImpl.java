package io.pavel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

    private static final String DEFAULT_FILE_NAME = "defaultFileName";

    @Override
    public File convertMultiPartFileToFile(MultipartFile file) throws IOException {
        File inputFile = new File(getFileName(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(inputFile)) {
            fos.write(file.getBytes());
        }
        return inputFile;
    }


    private String getFileName(String file) {
        return Optional.ofNullable(file)
            .orElse(DEFAULT_FILE_NAME);
    }
}
