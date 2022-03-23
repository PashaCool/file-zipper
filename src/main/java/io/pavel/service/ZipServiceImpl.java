package io.pavel.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZipServiceImpl implements ZipService {

    private static final String CONTENT_TYPE_APPLICATION_ZIP = "application/zip";
    private final FileService fileService;

    @Override
    public void zipFilesAndWriteToResponse(MultipartFile[] srcFiles, HttpServletResponse response) throws IOException {
        response.setContentType(CONTENT_TYPE_APPLICATION_ZIP);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=compressed.zip");

        try (
            ServletOutputStream outputStream = response.getOutputStream();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            ZipOutputStream zos = new ZipOutputStream(bufferedOutputStream)) {

            zos.setLevel(Deflater.BEST_COMPRESSION);
            for (MultipartFile srcFile : srcFiles) {
                File file = fileService.convertMultiPartFileToFile(srcFile);
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipEntry.setSize(file.length());

                zos.putNextEntry(zipEntry);

                StreamUtils.copy(srcFile.getInputStream(), zos);
                zos.closeEntry();
            }
            zos.finish();
        }
    }
}
