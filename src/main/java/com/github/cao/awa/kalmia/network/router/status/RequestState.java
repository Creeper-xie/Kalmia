package com.github.cao.awa.kalmia.network.router.status;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

public enum RequestState {
    HELLO, AUTH, AUTHED, DISABLED;

    private static final Set<RequestState> ALL = new ImmutableSet.Builder<RequestState>().add(HELLO,
                                                                                              AUTH,
                                                                                              AUTHED,
                                                                                              DISABLED
                                                                                         )
                                                                                         .build();

    public static Set<RequestState> all() {
        return ALL;
    }
}