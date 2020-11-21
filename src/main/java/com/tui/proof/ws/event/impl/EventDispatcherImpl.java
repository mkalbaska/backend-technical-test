package com.tui.proof.ws.event.impl;

import com.tui.proof.ws.event.Event;
import com.tui.proof.ws.event.EventDispatcher;
import com.tui.proof.ws.event.EventHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class EventDispatcherImpl implements EventDispatcher {

    private final List<EventHandler> eventHandlers;

    @Override
    public void dispatch(Event event) {
        CompletableFuture.runAsync(() -> eventHandlers.stream()
                .filter(it -> it.getEventType().equals(event.getType()))
                .forEach(eventHandler -> eventHandler.onEvent(event)));
    }
}
