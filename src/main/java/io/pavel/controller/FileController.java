package io.pavel.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.pavel.service.ZipService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/zip")
@RequiredArgsConstructor
public class FileController {

    private final ZipService zipService;

    @PostMapping(produces = "application/zip")
    public void zipFiles(@RequestParam("file") MultipartFile[] files, HttpServletResponse response, HttpServletRequest requests) throws IOException {
        String clientHost = requests.getRemoteHost();
        zipService.zipFilesAndWriteToResponse(files, response, clientHost);
    }
}
