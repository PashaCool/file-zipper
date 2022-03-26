package io.pavel.utils;

import java.net.InetSocketAddress;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import static org.assertj.core.api.Assertions.assertThat;

class UtilsTest {

    @Test
    void testGetClientHost() {
        HttpHeaders httpHeaders = new HttpHeaders();
        String hostname = "0.0.0.1";
        httpHeaders.setHost(new InetSocketAddress(hostname, 8080));

        String clientHost = Utils.getClientHost(httpHeaders);

        assertThat(clientHost).isEqualTo(hostname);
    }

    @Test
    void testEmptyHost() {
        HttpHeaders httpHeaders = new HttpHeaders();

        String clientHost = Utils.getClientHost(httpHeaders);

        assertThat(clientHost).isEmpty();
    }

    @Test
    void testGetFileName() {
        String fileName = "file.tmp";

        assertThat(Utils.getFileName(fileName)).isEqualTo(fileName);
    }

    @Test
    void testGetFileNameFromNull() {
        assertThat(Utils.getFileName(null)).isEqualTo("defaultFileName");
    }
}