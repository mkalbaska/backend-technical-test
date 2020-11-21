package com.tui.proof.ws.event.impl;

import com.tui.proof.ws.event.Event;
import com.tui.proof.ws.event.EventDispatcher;
import com.tui.proof.ws.event.EventHandler;
import com.tui.proof.ws.event.FlightWasShownEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class EventDispatcherImplTest {

    @Mock
    private EventHandler eventHandler;

    @Test
    void dispatch() throws InterruptedException {
        Mockito.<Class<?>>when(eventHandler.getEventType()).thenReturn(FlightWasShownEvent.class);
        EventDispatcher testSubject = new EventDispatcherImpl(Collections.singletonList(eventHandler));
        testSubject.dispatch(new FlightWasShownEvent(null));
        Thread.sleep(5000); //wait for dispatch to be compeleted
        Mockito.verify(eventHandler, Mockito.times(1)).getEventType();
        Mockito.verify(eventHandler, Mockito.times(1)).onEvent(any(Event.class));
    }
}