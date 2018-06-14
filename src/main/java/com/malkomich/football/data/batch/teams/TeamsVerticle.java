package com.malkomich.football.data.batch.teams;

import com.malkomich.football.data.batch.teams.service.TeamsService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.serviceproxy.ServiceBinder;

import javax.inject.Inject;

public class TeamsVerticle extends AbstractVerticle {
    public static final String QUEUE = "teams.queue";

    @Inject
    private TeamsService service;

    @Override
    public void start(final Future<Void> startFuture) {
        new ServiceBinder(vertx)
            .setAddress(QUEUE)
            .register(TeamsService.class, service);
        startFuture.complete();
    }

    @Override
    public void stop() {
        service.close();
    }
}
