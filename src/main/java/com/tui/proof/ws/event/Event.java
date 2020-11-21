package com.tui.proof.ws.event;

public interface Event {

    Class<? extends Event> getType();
}
