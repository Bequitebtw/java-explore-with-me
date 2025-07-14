package ru.practicum.ewm.dto;

import jakarta.servlet.http.HttpServletRequest;

public class RequestUtils {
    private final String appName;

    public RequestUtils(String appName) {
        this.appName = appName;
    }

    public EndpointHitRequest createHit(HttpServletRequest endpointHitRequest) {

        String remoteAddr = endpointHitRequest.getRemoteAddr();
        String requestURI = endpointHitRequest.getRequestURI();

        return new EndpointHitRequest(appName, requestURI, remoteAddr);
    }
}
