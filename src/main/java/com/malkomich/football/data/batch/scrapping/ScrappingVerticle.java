package com.malkomich.football.data.batch.scrapping;

import com.malkomich.football.data.batch.scrapping.service.ScrappingService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.serviceproxy.ServiceBinder;

import javax.inject.Inject;

public class ScrappingVerticle extends AbstractVerticle {

    public static final String QUEUE = "verticles.queue";

    @Inject
    private ScrappingService service;

    @Override
    public void start(final Future<Void> startFuture) {
        new ServiceBinder(vertx)
            .setAddress(QUEUE)
            .register(ScrappingService.class, service);
        startFuture.complete();
    }

    @Override
    public void stop() {
        service.close();
    }
}
