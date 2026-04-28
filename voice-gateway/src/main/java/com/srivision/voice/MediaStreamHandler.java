package com.srivision.voice;

import org.result4j.Result;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MediaStreamHandler implements WebSocketHandler {

    private final AudioPipeline pipeline;

    public MediaStreamHandler(AudioPipeline pipeline) {
        this.pipeline = pipeline;
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        Flux<WebSocketMessage> outbound = session.receive()
                .flatMap(msg -> processEvent(msg.getPayloadAsText()))
                .map(session::textMessage);
        return session.send(outbound);
    }

    private Mono<String> processEvent(String payload) {
        return Mono.just(payload)
                .map(pipeline::extractAndTransform)
                .flatMap(this::unwrapResult);
    }

    private Mono<String> unwrapResult(Result<String, PipelineError> result) {
        return result.map(Mono::just).orElseGet(Mono::empty);
    }
}
