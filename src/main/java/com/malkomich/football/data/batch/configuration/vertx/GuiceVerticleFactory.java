package com.malkomich.football.data.batch.configuration.vertx;

import com.google.common.base.Preconditions;
import com.google.inject.Injector;
import io.vertx.core.Verticle;
import io.vertx.core.spi.VerticleFactory;
import lombok.val;

public class GuiceVerticleFactory implements VerticleFactory {

    public static final String GUICE_PREFIX = "java-guice";
    private final Injector injector;

    public GuiceVerticleFactory(final Injector injector) {
        this.injector = Preconditions.checkNotNull(injector);
    }

    @Override
    public String prefix() {
        return GUICE_PREFIX;
    }

    @Override
    public Verticle createVerticle(final String verticleName, final ClassLoader classLoader) throws Exception {
        val verticleBaseName = VerticleFactory.removePrefix(verticleName);

        final Class clazz = classLoader.loadClass(verticleBaseName);

        return (Verticle) this.injector.getInstance(clazz);
    }
}
