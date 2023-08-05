package com.kerrrusha.wotstattrackerweb.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Service that allows you to async check for appearance of some result with timeout and after every N-seconds
 * @param <T> - input parameter type
 * @param <D> - result parameter type
 */
@Slf4j
public abstract class AbstractSingleThreadAwaitService<T, D> {

    @Value("${data.update.post.check.millis}")
    private Integer checkUpdatedDataEveryMillis;

    @Value("${data.update.post.timeout.seconds}")
    private Integer dataUpdateTimeoutSeconds;

    public D awaitResult(T input) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        CompletableFuture<D> future = new CompletableFuture<>();

        executor.submit(() -> {
            while (!future.isDone()) {
                Optional<D> resultOptional = lookupForResult(input);
                if (resultOptional.isPresent()) {
                    future.complete(resultOptional.get());
                    break;
                }

                try {
                    TimeUnit.MILLISECONDS.sleep(checkUpdatedDataEveryMillis);
                } catch (InterruptedException ignored) {}
            }
        });

        D result = null;
        try {
            result = future.get(dataUpdateTimeoutSeconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.warn("#awaitResult - {}", e.toString());
        } finally {
            executor.shutdownNow();
        }

        log.debug("#awaitResult - awaited such result: {}", result);
        return result;
    }

    protected abstract Optional<D> lookupForResult(T input);

}
