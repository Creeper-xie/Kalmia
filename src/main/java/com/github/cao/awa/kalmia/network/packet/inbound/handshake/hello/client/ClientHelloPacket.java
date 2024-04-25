package com.github.cao.awa.kalmia.network.packet.inbound.handshake.hello.client;

import com.github.cao.awa.apricot.annotations.auto.Auto;
import com.github.cao.awa.apricot.io.bytes.reader.BytesReader;
import com.github.cao.awa.kalmia.annotations.auto.event.network.NetworkEventTarget;
import com.github.cao.awa.kalmia.annotations.auto.network.unsolve.AutoAllData;
import com.github.cao.awa.kalmia.annotations.auto.network.unsolve.AutoSolvedPacket;
import com.github.cao.awa.kalmia.env.KalmiaPreSharedCipher;
import com.github.cao.awa.kalmia.event.kalmiagram.network.inbound.handshake.hello.client.ClientHelloEvent;
import com.github.cao.awa.kalmia.network.handler.handshake.HandshakeHandler;
import com.github.cao.awa.kalmia.network.packet.Packet;
import com.github.cao.awa.kalmia.protocol.RequestProtocol;
import com.github.cao.awa.modmdo.annotation.platform.Client;
import com.github.cao.awa.modmdo.annotation.platform.Server;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@AutoAllData
@AllArgsConstructor
@Accessors(fluent = true)
@AutoSolvedPacket(id = 0, crypto = false)
@NetworkEventTarget(ClientHelloEvent.class)
public class ClientHelloPacket extends Packet<HandshakeHandler> {
    private RequestProtocol majorProtocol;
    private String clientVersion;
    private String expectCipherField;

    @Client
    public ClientHelloPacket(RequestProtocol majorProtocol, String clientVersion) {
        this(majorProtocol,
             clientVersion,
             KalmiaPreSharedCipher.expectCipherField
        );
    }

    @Auto
    @Server
    public ClientHelloPacket(BytesReader reader) {
        super(reader);
    }
}
