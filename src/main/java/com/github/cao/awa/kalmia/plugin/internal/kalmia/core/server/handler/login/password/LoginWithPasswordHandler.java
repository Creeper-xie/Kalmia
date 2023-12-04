package com.github.cao.awa.kalmia.plugin.internal.kalmia.core.server.handler.login.password;

import com.github.cao.awa.apricot.annotations.auto.Auto;
import com.github.cao.awa.apricot.identifier.BytesRandomIdentifier;
import com.github.cao.awa.apricot.util.digger.MessageDigger;
import com.github.cao.awa.kalmia.annotations.plugin.PluginRegister;
import com.github.cao.awa.kalmia.attack.exhaustive.ExhaustiveLogin;
import com.github.cao.awa.kalmia.bootstrap.Kalmia;
import com.github.cao.awa.kalmia.event.kalmiagram.handler.network.inbound.login.password.LoginWithPasswordEventHandler;
import com.github.cao.awa.kalmia.login.LoginCommon;
import com.github.cao.awa.kalmia.mathematic.Mathematics;
import com.github.cao.awa.kalmia.network.handler.inbound.AuthedRequestHandler;
import com.github.cao.awa.kalmia.network.packet.inbound.login.password.LoginWithPasswordPacket;
import com.github.cao.awa.kalmia.network.router.kalmia.RequestRouter;
import com.github.cao.awa.kalmia.network.router.kalmia.status.RequestState;
import com.github.cao.awa.kalmia.user.DefaultUser;
import com.github.cao.awa.kalmia.user.User;
import com.github.cao.awa.modmdo.annotation.platform.Server;

import java.util.Arrays;

@Auto
@Server
@PluginRegister(name = "kalmia_core")
public class LoginWithPasswordHandler implements LoginWithPasswordEventHandler {
    @Auto
    @Server
    @Override
    public void handle(RequestRouter router, LoginWithPasswordPacket packet) {
        long uid = packet.uid();
        String password = packet.password();

        // Start login here.
        User user = Kalmia.SERVER.userManager()
                                 .get(uid);

        if (user instanceof DefaultUser usr && usr.password()
                                                  .isSha() && Arrays.equals(usr.password()
                                                                               .password(),
                                                                            Mathematics.toBytes(MessageDigger.digest(password,
                                                                                                                     MessageDigger.Sha3.SHA_512
                                                                                                ),
                                                                                                16
                                                                            )
        ) && ExhaustiveLogin.validate(router)) {
            router.setStates(RequestState.AUTHED);
            ((AuthedRequestHandler) router.getHandler()).setUid(uid);

            byte[] token = BytesRandomIdentifier.create(128);

            LoginCommon.login(
                    packet.uid(),
                    router
            );

            loginSuccess(
                    router,
                    uid,
                    token
            );
        } else {
            loginFailure(
                    router,
                    uid,
                    "login.failure.pwd_or_uid_is_wrong"
            );
        }
    }
}
