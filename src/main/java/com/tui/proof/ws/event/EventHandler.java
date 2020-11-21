package com.tui.proof.ws.event;

public interface EventHandler {

    void onEvent(Event event);
    Class<? extends Event> getEventType();

}
