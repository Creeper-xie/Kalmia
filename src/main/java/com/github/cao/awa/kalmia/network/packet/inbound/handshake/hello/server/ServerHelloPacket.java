package com.github.cao.awa.kalmia.network.packet.inbound.handshake.hello.server;

import com.github.cao.awa.apricot.annotation.auto.Auto;
import com.github.cao.awa.apricot.io.bytes.reader.BytesReader;
import com.github.cao.awa.apricot.util.digger.MessageDigger;
import com.github.cao.awa.apricot.util.encryption.Crypto;
import com.github.cao.awa.kalmia.annotation.auto.network.unsolve.AutoData;
import com.github.cao.awa.kalmia.annotation.auto.network.unsolve.AutoSolvedPacket;
import com.github.cao.awa.kalmia.annotation.crypto.CryptoEncoded;
import com.github.cao.awa.kalmia.annotation.crypto.NotDecoded;
import com.github.cao.awa.kalmia.mathematic.Mathematics;
import com.github.cao.awa.kalmia.network.handler.handshake.HandshakeHandler;
import com.github.cao.awa.kalmia.network.packet.Packet;
import com.github.cao.awa.kalmia.network.packet.inbound.login.sign.LoginWithSignPacket;
import com.github.cao.awa.kalmia.network.router.RequestRouter;
import com.github.cao.awa.kalmia.network.router.status.RequestState;
import com.github.cao.awa.modmdo.annotation.platform.Client;
import com.github.cao.awa.modmdo.annotation.platform.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@AutoSolvedPacket(3)
public class ServerHelloPacket extends Packet<HandshakeHandler> {
    private static final Logger LOGGER = LogManager.getLogger("ServerHello");

    @AutoData
    @NotDecoded
    private byte[] testKey;
    @AutoData
    private byte[] testSha;
    @AutoData
    @NotDecoded
    private byte[] iv;

    @Server
    public ServerHelloPacket(@CryptoEncoded byte[] testKey, byte[] testSha, @CryptoEncoded byte[] iv) {
        try {
            this.testKey = testKey;
            this.testSha = testSha;
            this.iv = iv;
        } catch (Exception e) {
            //TODO
            throw new RuntimeException(e);
        }
    }

    @Auto
    @Client
    public ServerHelloPacket(BytesReader reader) {
        super(reader);
    }

    public byte[] getTestKey() {
        return this.testKey;
    }

    public byte[] getTestSha() {
        return this.testSha;
    }

    @Client
    @Override
    public void inbound(RequestRouter router, HandshakeHandler handler) {
        LOGGER.info("Server Hello!");

        byte[] provideCipher = router.decode(this.testKey);

        LOGGER.debug("Server Sent Hello: " + Mathematics.radix(MessageDigger.digest(provideCipher,
                                                                                    MessageDigger.Sha3.SHA_512
                                                               ),
                                                               16,
                                                               36
        ));
        LOGGER.debug("Server Provide Hello: " + Mathematics.radix(this.testSha,
                                                                  36
        ));

        if (this.iv.length == 16) {
            LOGGER.debug("Server IV: " + Mathematics.radix(router.decode(this.iv),
                                                           36
            ));

            router.setIv(router.decode(this.iv));
        } else {
            LOGGER.debug("Server IV: DEFAULT IV");

            router.setIv(Crypto.defaultIv());
        }

        // Prepare authed status to enable LoginHandler.
        router.setStatus(RequestState.AUTH);

        // TODO
        //     Try login(will delete in releases).
//        router.send(new LoginWithPasswordPacket(1,
//                                                "123456".getBytes()
//        ));

        router.send(new LoginWithSignPacket(1));
    }
}
