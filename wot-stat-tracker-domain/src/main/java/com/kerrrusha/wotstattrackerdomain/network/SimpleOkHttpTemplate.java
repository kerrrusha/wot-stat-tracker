package com.kerrrusha.wotstattrackerdomain.network;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import static java.util.Objects.isNull;

@Slf4j
@RequiredArgsConstructor
public class SimpleOkHttpTemplate implements OkHttpTemplate {

    private final OkHttpClient okHttpClient;

    @Override
    public String get(String url) throws IOException {
        return get(url, Collections.emptyMap());
    }

    @Override
    public String get(String url, Map<String, String> headers) throws IOException {
        log.info("GET - {}", url);
        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(headers))
                .get()
                .build();
        return request(request);
    }

    @Override
    public String request(Request request) throws IOException {
        return doGetStringBodyTemplate(request);
    }

    @Override
    public byte[] requestBinary(Request request) throws IOException {
        return doGetBinaryBodyTemplate(request);
    }

    @Override
    public Response rawRequest(Request request) throws IOException {
        return okHttpClient.newCall(request).execute();
    }

    private String doGetStringBodyTemplate(Request request) throws IOException {
        try (Response response = okHttpClient.newCall(request).execute()) {
            ResponseBody body = response.body();
            if (isNull(body)) {
                throw new IOException("No content returned for " + request.method() + " " + request.url());
            }

            if (!response.isSuccessful()) {
                throw new IOException(response.message(), new IOException(request.method() + " " + request.url() + ": " + response.code()));
            }
            return body.string();
        }
    }

    private byte[] doGetBinaryBodyTemplate(Request request) throws IOException {
        try (Response response = okHttpClient.newCall(request).execute()) {
            ResponseBody body = response.body();
            if (isNull(body))
                throw new IOException("No content returned for " + request.method() + " " + request.url());

            if (!response.isSuccessful())
                throw new IOException(response.message(), new IOException(request.method() + " " + request.url()));
            return body.bytes();
        }
    }

}
