package com.malkomich.football.data.batch.ranks;

import com.malkomich.football.data.batch.ranks.service.RanksService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.serviceproxy.ServiceBinder;

import javax.inject.Inject;

public class RanksVerticle extends AbstractVerticle {
    public static final String QUEUE = "ranks.queue";

    @Inject
    private RanksService service;

    @Override
    public void start(final Future<Void> startFuture) {
        new ServiceBinder(vertx)
            .setAddress(QUEUE)
            .register(RanksService.class, service);
        startFuture.complete();
    }

    @Override
    public void stop() {
        service.close();
    }
}
