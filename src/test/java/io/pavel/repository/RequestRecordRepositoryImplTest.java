package io.pavel.repository;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@JdbcTest
class RequestRecordRepositoryImplTest {

    private RequestRecordRepository requestRecordRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        requestRecordRepository = new RequestRecordRepositoryImpl(jdbcTemplate);
    }

    @Test
    void insertNewRecord() {
        //given
        String host = "127.0.0.1";

        //when
        requestRecordRepository.saveClientRequest(host);

        //then
        List<ClientHost> clientHosts = jdbcTemplate.query("select * from zipper.zip_hosts", (rs, rowNum) ->
            new ClientHost(rs.getString(1), rs.getString(2), rs.getDate(3).toLocalDate(), rs.getInt(4))
        );

        assertThat(clientHosts).hasSize(1);

        ClientHost cLientHost = clientHosts.get(0);
        assertThat(cLientHost.uuid).isNotNull();
        assertThat(cLientHost.host).isEqualTo(host);
        assertThat(cLientHost.day).isEqualTo(LocalDate.now());
        assertThat(cLientHost.count).isEqualTo(1);
    }

    @Test
    void updateExistingHostRecord() {
        //given
        String host = "127.0.0.1";
        LocalDate today = LocalDate.now();
        Map<String, Object> params = Map.ofEntries(
            Map.entry("id_key", UUID.randomUUID()),
            Map.entry("client_host", host),
            Map.entry("upload_date", today),
            Map.entry("day_count", 1)
        );
        jdbcTemplate.update("INSERT INTO zipper.zip_hosts(id_key, client_host, upload_date, day_count) VALUES(:id_key, :client_host, " +
            ":upload_date, :day_count)", params);

        //when
        requestRecordRepository.saveClientRequest(host);

        //then
        List<ClientHost> clientHosts = jdbcTemplate.query("select id_key, client_host, upload_date, day_count from zipper.zip_hosts",
            (rs, rowNum) -> new ClientHost(rs.getString(1), rs.getString(2), rs.getDate(3).toLocalDate(), rs.getInt(4))
        );

        assertThat(clientHosts).hasSize(1);

        ClientHost cLientHost = clientHosts.get(0);
        assertThat(cLientHost.uuid).isNotNull();
        assertThat(cLientHost.host).isEqualTo(host);
        assertThat(cLientHost.day).isEqualTo(LocalDate.now());
        assertThat(cLientHost.count).isEqualTo(2);
    }

    @Test
    @DisplayName("Insert record for the same host, but another day")
    void insertRecordForSameHost() {
        //given
        String host = "127.0.0.1";
        LocalDate previousZippedDay = LocalDate.of(2022, Month.MARCH, 20);
        Map<String, Object> params = Map.ofEntries(
            Map.entry("id_key", UUID.randomUUID()),
            Map.entry("client_host", host),
            Map.entry("upload_date", previousZippedDay),
            Map.entry("day_count", 1)
        );
        jdbcTemplate.update("INSERT INTO zipper.zip_hosts(id_key, client_host, upload_date, day_count) VALUES(:id_key, :client_host, " +
            ":upload_date, :day_count)", params);

        //when
        requestRecordRepository.saveClientRequest(host);

        //then
        List<ClientHost> clientHosts = jdbcTemplate.query("select id_key, client_host, upload_date, day_count from zipper.zip_hosts",
            (rs, rowNum) -> new ClientHost(rs.getString(1), rs.getString(2), rs.getDate(3).toLocalDate(), rs.getInt(4))
        );

        assertThat(clientHosts).hasSize(2)
            .contains(
                new ClientHost(UUID.randomUUID().toString(), host, previousZippedDay, 1),
                new ClientHost(UUID.randomUUID().toString(), host, LocalDate.now(), 1));
    }

    @AllArgsConstructor
    @Getter
    @EqualsAndHashCode(exclude = {"uuid"})
    private static class ClientHost {
        private String uuid;
        private String host;
        private LocalDate day;
        private int count;
    }
}