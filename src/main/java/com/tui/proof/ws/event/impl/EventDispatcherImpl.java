package com.tui.proof.ws.event.impl;

import com.tui.proof.ws.event.EventDispatcher;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class EventDispatcherImpl implements EventDispatcher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void dispatch(ApplicationEvent event) {
        CompletableFuture.runAsync(() -> applicationEventPublisher.publishEvent(event));
    }
}
