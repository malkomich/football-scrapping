package com.malkomich.football.data.batch.matchDetails;

import com.malkomich.football.data.batch.matchDetails.service.MatchDetailsService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.serviceproxy.ServiceBinder;

import javax.inject.Inject;

public class MatchDetailsVerticle extends AbstractVerticle {
    public static final String QUEUE = "match.details.queue";

    @Inject
    private MatchDetailsService service;

    @Override
    public void start(final Future<Void> startFuture) {
        new ServiceBinder(vertx)
            .setAddress(QUEUE)
            .register(MatchDetailsService.class, service);
        startFuture.complete();
    }

    @Override
    public void stop() {
        service.close();
    }
}
