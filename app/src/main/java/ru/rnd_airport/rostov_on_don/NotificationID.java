package ru.rnd_airport.rostov_on_don;

import java.util.concurrent.atomic.AtomicInteger;

public class NotificationID {

    private final static AtomicInteger c = new AtomicInteger(0);

    public static int getID() {
        return c.incrementAndGet();
    }
}