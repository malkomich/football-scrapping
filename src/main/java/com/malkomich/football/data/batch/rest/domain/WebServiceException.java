package com.malkomich.football.data.batch.rest.domain;

import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.Getter;

public class WebServiceException extends RuntimeException {
    private static final String EXCEPTION_HANDLING_ERROR = "Server exception handling error";
    @Getter
    private Integer status;

    public WebServiceException(final Integer status, final String message) {
        super(message);
        this.status = status;
    }

    public static WebServiceException getRestException(final Throwable throwable) {
        if (throwable instanceof WebServiceException) {
            return (WebServiceException) throwable;
        }
        return new WebServiceException(HttpResponseStatus.INTERNAL_SERVER_ERROR.code(), EXCEPTION_HANDLING_ERROR);
    }
}
