package com.malkomich.football.data.batch.repository;

import com.malkomich.football.data.batch.repository.service.FootballRepository;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.client.WebClient;
import io.vertx.serviceproxy.ServiceBinder;

import javax.inject.Inject;

public class RepositoryVerticle extends AbstractVerticle {

    public static final String QUEUE = "repository.queue";

    @Inject
    private FootballRepository repository;

    @Override
    public void start(final Future<Void> startFuture) {
        new ServiceBinder(vertx)
            .setAddress(QUEUE)
            .register(FootballRepository.class, repository);
        startFuture.complete();
    }

    @Override
    public void stop() {
        repository.close();
    }
}
