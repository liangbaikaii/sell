package com.lq.sell.utils;

import java.util.UUID;

public class UniqueKey {

    public synchronized static String generateUniqueKey() {
        return UUID.randomUUID().toString().substring(15,30);
    }
}
