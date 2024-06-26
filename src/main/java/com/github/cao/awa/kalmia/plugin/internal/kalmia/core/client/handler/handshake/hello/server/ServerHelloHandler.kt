package com.github.cao.awa.kalmia.plugin.internal.kalmia.core.client.handler.handshake.hello.server

import com.github.cao.awa.apricot.annotations.auto.Auto
import com.github.cao.awa.apricot.util.encryption.Crypto
import com.github.cao.awa.kalmia.annotations.plugin.PluginRegister
import com.github.cao.awa.kalmia.event.kalmiagram.handler.network.inbound.handshake.hello.server.ServerHelloEventHandler
import com.github.cao.awa.kalmia.identity.LongAndExtraIdentity
import com.github.cao.awa.kalmia.mathematic.Mathematics
import com.github.cao.awa.kalmia.network.packet.inbound.handshake.hello.server.ServerHelloPacket
import com.github.cao.awa.kalmia.network.packet.inbound.login.password.LoginWithPasswordPacket
import com.github.cao.awa.kalmia.network.router.kalmia.RequestRouter
import com.github.cao.awa.kalmia.network.router.kalmia.status.RequestState
import com.github.cao.awa.modmdo.annotation.platform.Client
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Auto
@Client
@PluginRegister(name = "kalmia_client")
class ServerHelloHandler : ServerHelloEventHandler {
    companion object {
        private val LOGGER: Logger = LogManager.getLogger("ServerHelloHandler")
    }

    @Auto
    @Client
    override fun handle(router: RequestRouter, packet: ServerHelloPacket) {
        LOGGER.info("Server Hello!")

        if (packet.iv().size == 16) {
            LOGGER.debug("Server IV: ${Mathematics.radix(router.decode(packet.iv()), 36)}")

            router.setIv(router.decode(packet.iv()))
        } else {
            LOGGER.debug("Server IV: DEFAULT IV")

            router.setIv(Crypto.defaultIv())
        }

        // Prepare authed status to enable LoginHandler.
        router.setStates(RequestState.AUTH)

        // TODO Try login (delete it in releases please)
        router.send(LoginWithPasswordPacket(LongAndExtraIdentity.create(0, byteArrayOf(123)), "123456"))
    }
}
