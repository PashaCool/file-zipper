package io.pavel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.pavel.repository.RequestRecordRepository;
import liquibase.repackaged.org.apache.commons.lang3.StringUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollectClientHostServiceImpl implements CollectClientHostService {

    private final RequestRecordRepository requestRecordRepository;

    @Override
    @Transactional
    public void saveClientHost(String host) {
        if (StringUtils.isNotBlank(host)) {
            requestRecordRepository.saveClientRequest(host);
        }
    }
}
