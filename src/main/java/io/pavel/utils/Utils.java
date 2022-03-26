package io.pavel.utils;

import java.net.InetSocketAddress;
import java.util.Optional;

import org.springframework.http.HttpHeaders;

import liquibase.repackaged.org.apache.commons.lang3.StringUtils;

public final class Utils {

    private static final String DEFAULT_FILE_NAME = "defaultFileName";

    private Utils() {}

    public static String getClientHost(HttpHeaders headers) {
        return Optional.ofNullable(headers.getHost())
            .map(InetSocketAddress::getHostString)
            .orElse(StringUtils.EMPTY);
    }

    public static String getFileName(String file) {
        return Optional.ofNullable(file)
            .orElse(DEFAULT_FILE_NAME);
    }
}
