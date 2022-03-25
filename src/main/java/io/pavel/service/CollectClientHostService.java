package io.pavel.service;

/**
 * Service save client hosts per request
 */
public interface CollectClientHostService {

    void saveClientHost(String host);
}
