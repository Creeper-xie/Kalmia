package com.github.cao.awa.kalmia.network.packet.inbound.handshake.hello.server;

import com.github.cao.awa.apricot.annotations.auto.Auto;
import com.github.cao.awa.apricot.io.bytes.reader.BytesReader;
import com.github.cao.awa.kalmia.annotations.auto.event.network.NetworkEventTarget;
import com.github.cao.awa.kalmia.annotations.auto.network.unsolve.AutoAllData;
import com.github.cao.awa.kalmia.annotations.auto.network.unsolve.AutoSolvedPacket;
import com.github.cao.awa.kalmia.annotations.crypto.NotDecoded;
import com.github.cao.awa.kalmia.event.kalmiagram.network.inbound.handshake.hello.server.ServerHelloEvent;
import com.github.cao.awa.kalmia.network.handler.handshake.HandshakeHandler;
import com.github.cao.awa.kalmia.network.packet.Packet;
import com.github.cao.awa.modmdo.annotation.platform.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@AutoAllData
@AllArgsConstructor
@Accessors(fluent = true)
@AutoSolvedPacket(id = 3, crypto = true)
@NetworkEventTarget(ServerHelloEvent.class)
public class ServerHelloPacket extends Packet<HandshakeHandler> {
    private String serverName;
    private String serverVersion;
    @NotDecoded
    private byte[] iv;

    @Auto
    @Client
    public ServerHelloPacket(BytesReader reader) {
        super(reader);
    }
}
