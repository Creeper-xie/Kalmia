package com.github.cao.awa.kalmia.event.network.inbound.login.password;

import com.github.cao.awa.kalmia.event.network.NetworkEvent;
import com.github.cao.awa.kalmia.network.packet.inbound.login.password.LoginWithPasswordPacket;
import com.github.cao.awa.kalmia.network.router.RequestRouter;

public class LoginWithPasswordEvent extends NetworkEvent<LoginWithPasswordPacket> {
    public LoginWithPasswordEvent(RequestRouter router, LoginWithPasswordPacket packet) {
        super(router,
              packet
        );
    }
}