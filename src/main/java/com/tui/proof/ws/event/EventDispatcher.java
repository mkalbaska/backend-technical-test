package com.tui.proof.ws.event;

import org.springframework.context.ApplicationEvent;

public interface EventDispatcher {

    void dispatch(ApplicationEvent event);
}
