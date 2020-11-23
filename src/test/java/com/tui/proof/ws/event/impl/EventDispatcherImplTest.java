package com.tui.proof.ws.event.impl;

import com.tui.proof.MainApplication;
import com.tui.proof.ws.event.EventDispatcher;
import com.tui.proof.ws.event.impl.event.DeleteBookingEvent;
import com.tui.proof.ws.event.impl.handler.DeleteBookingEventHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MainApplication.class)
class EventDispatcherImplTest {

    @Autowired
    private EventDispatcher testSubject;
    @MockBean
    private DeleteBookingEventHandler eventHandler;

    @Test
    void dispatch() throws InterruptedException {
        testSubject.dispatch(new DeleteBookingEvent(1L));
        Thread.sleep(5000); //wait for dispatch to be compeleted
        Mockito.verify(eventHandler, Mockito.times(1)).onApplicationEvent(any(DeleteBookingEvent.class));
    }
}