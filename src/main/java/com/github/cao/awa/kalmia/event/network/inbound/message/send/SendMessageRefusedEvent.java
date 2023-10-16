package com.github.cao.awa.kalmia.event.network.inbound.message.send;

import com.github.cao.awa.kalmia.event.network.NetworkEvent;
import com.github.cao.awa.kalmia.network.packet.inbound.message.send.SendMessageRefusedPacket;
import com.github.cao.awa.kalmia.network.router.RequestRouter;

public class SendMessageRefusedEvent extends NetworkEvent<SendMessageRefusedPacket> {
    public SendMessageRefusedEvent(RequestRouter router, SendMessageRefusedPacket packet) {
        super(router,
              packet
        );
    }
}
