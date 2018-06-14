package com.malkomich.football.data.batch.leagues;

import com.malkomich.football.data.batch.leagues.service.LeaguesService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.serviceproxy.ServiceBinder;

import javax.inject.Inject;

public class LeaguesVerticle extends AbstractVerticle {
    public static final String QUEUE = "leagues.queue";

    @Inject
    private LeaguesService service;

    @Override
    public void start(final Future<Void> startFuture) {
        new ServiceBinder(vertx)
            .setAddress(QUEUE)
            .register(LeaguesService.class, service);
        startFuture.complete();
    }

    @Override
    public void stop() {
        service.close();
    }
}
