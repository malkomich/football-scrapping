package com.malkomich.football.data.batch.matches;

import com.malkomich.football.data.batch.matches.service.MatchService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.serviceproxy.ServiceBinder;

import javax.inject.Inject;

public class MatchVerticle extends AbstractVerticle {
    public static final String QUEUE = "matches.queue";

    @Inject
    private MatchService service;

    @Override
    public void start(final Future<Void> startFuture) {
        new ServiceBinder(vertx)
            .setAddress(QUEUE)
            .register(MatchService.class, service);
        startFuture.complete();
    }

    @Override
    public void stop() {
        service.close();
    }
}
