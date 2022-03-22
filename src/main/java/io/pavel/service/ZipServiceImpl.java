package io.pavel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ZipServiceImpl implements ZipService {

    private static final String DEFAULT_FILE_NAME = "defaultFileName";
    private static final String CONTENT_TYPE_APPLICATION_ZIP = "application/zip";

    @Override
    public void zipFilesAndWriteToResponse(MultipartFile[] srcFiles, HttpServletResponse response) throws IOException {
        response.setContentType(CONTENT_TYPE_APPLICATION_ZIP);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=compressed.zip");

        try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
            for (MultipartFile srcFile : srcFiles) {
                File file = convertMultiPartFileToFile(srcFile);
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipEntry.setSize(file.length());

                zos.putNextEntry(zipEntry);

                StreamUtils.copy(srcFile.getInputStream(), zos);
                zos.closeEntry();
            }
            zos.finish();
        }
    }

    private File convertMultiPartFileToFile(MultipartFile file) throws IOException {
        File convFile = new File(getFileName(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }

    private String getFileName(String file) {
        return Optional.ofNullable(file)
            .orElse(DEFAULT_FILE_NAME);
    }
}
