package io.pavel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.pavel.utils.Utils;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public File convertMultiPartFileToFile(MultipartFile file) throws IOException {
        String fileName = Utils.getFileName(file.getOriginalFilename());
        File inputFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(inputFile)) {
            fos.write(file.getBytes());
        }
        return inputFile;
    }
}
