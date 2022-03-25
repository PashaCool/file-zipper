package io.pavel.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.pavel.repository.RequestRecordRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class CollectClientHostServiceImplTest {

    @InjectMocks
    private CollectClientHostServiceImpl collectClientHostService;
    @Mock
    private RequestRecordRepository requestRecordRepository;

    @Test
    void saveClientHost() {
        String host = "127.0.1.1";

        collectClientHostService.saveClientHost(host);

        verify(requestRecordRepository).saveClientRequest(host);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"    ", "\n", "\t"})
    void notSaveEmptyHost(String host) {

        collectClientHostService.saveClientHost(host);

        verifyNoInteractions(requestRecordRepository);
    }
}