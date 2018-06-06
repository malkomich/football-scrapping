package com.malkomich.football.data.batch.scrapping.service;

import com.malkomich.football.data.batch.rest.domain.WebServiceException;
import com.malkomich.football.data.batch.scrapping.domain.ScrappingFields;
import com.malkomich.football.data.batch.scrapping.domain.ScrappingRequest;
import com.malkomich.football.data.batch.scrapping.domain.ScrappingResponse;
import com.malkomich.football.data.batch.util.schema.Field;
import com.malkomich.football.data.batch.util.schema.Type;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.webfolder.ui4j.api.browser.Page;
import io.webfolder.ui4j.api.browser.PageConfiguration;
import io.webfolder.ui4j.api.dom.Element;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.webfolder.ui4j.api.browser.BrowserFactory.getWebKit;

@Slf4j
@Builder
public class ScrappingServiceImpl implements ScrappingService {

    @Override
    public ScrappingService execute(final ScrappingRequest request,
                                    final Handler<AsyncResult<ScrappingResponse>> handler) {
        try (final Page page = getWebKit().navigate(request.getUrl())) {
            final ScrappingResponse response = ScrappingResponse.builder()
                .entryFields(executeScrap(page, request))
                .build();
            handler.handle(Future.succeededFuture(response));
        } catch (final WebServiceException error) {
            handler.handle(Future.failedFuture(error));
        }
        return this;
    }

    private List<ScrappingFields> executeScrap(final Page page,
                                               final ScrappingRequest request) {
        return page.getDocument()
            .queryAll(request.getContainerSelector())
            .stream()
            .map(element -> scrapContainer(element, request))
            .collect(Collectors.toList());
    }

    private ScrappingFields scrapContainer(final Element container, final ScrappingRequest request) {
        final Map<String, String> fields = new HashMap<>();
        request.getFieldSelectors()
            .forEach(field -> getValue(field, container)
                .ifPresent(value -> fields.put(field.getSelector(), value)));
        return ScrappingFields.builder()
            .fields(fields)
            .build();
    }

    private Optional<String> getValue(final Field field, final Element container) {
        return container.query(field.getSelector())
            .map(element -> extractValue(element, field.getType()))
            .filter(Optional::isPresent)
            .map(Optional::get);
    }

    private Optional<String> extractValue(final Element element, final Type type) {
        if (Type.TEXT.equals(type)) {
            return element.getText();
        }
        if (Type.HREF.equals(type)) {
            return element.getAttribute("href");
        }
        return Optional.empty();
    }

    @Override
    public void close() {
        getWebKit().shutdown();
    }
}
