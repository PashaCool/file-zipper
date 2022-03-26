package io.pavel.repository;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RequestRecordRepositoryImpl implements RequestRecordRepository {

    private static final String UPDATE_HOST_COUNT = "update zipper.zip_hosts set day_count = day_count+1 where client_host=:client_host and " +
        "upload_date=:upload_date";
    private static final String SQL_INSERT = "INSERT INTO zipper.zip_hosts(id_key, client_host, upload_date, day_count) VALUES(:id_key, :client_host, " +
        ":upload_date, :day_count)";
    private static final String ID_KEY = "id_key";
    private static final String CLIENT_HOST = "client_host";
    private static final String UPLOAD_DATE = "upload_date";
    private static final String DAY_COUNT = "day_count";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Async
    @Override
    public void saveClientRequest(String host) {
        LocalDate today = LocalDate.now();
        int updatedRecords = jdbcTemplate.update(UPDATE_HOST_COUNT, Map.of(CLIENT_HOST, host, UPLOAD_DATE, today));
        if (updatedRecords != 0) {
            log.info("Update counter for exists host: {}", host);
        } else {
            log.info("Insert new record for host: {}", host);
            insertNewRecord(host, today);
        }
    }

    private void insertNewRecord(String host, LocalDate today) {
        Map<String, Object> params = Map.ofEntries(
            Map.entry(ID_KEY, UUID.randomUUID()),
            Map.entry(CLIENT_HOST, host),
            Map.entry(UPLOAD_DATE, today),
            Map.entry(DAY_COUNT, 1)
        );
        jdbcTemplate.update(SQL_INSERT, params);
    }
}
