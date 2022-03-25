package io.pavel.utils;

import java.net.InetSocketAddress;
import java.util.Optional;

import org.springframework.http.HttpHeaders;

import liquibase.repackaged.org.apache.commons.lang3.StringUtils;

public final class Utils {

    private Utils() {}

    public static String getClientHost(HttpHeaders headers) {
        return Optional.ofNullable(headers.getHost())
            .map(InetSocketAddress::getHostString)
            .orElse(StringUtils.EMPTY);
    }
}
