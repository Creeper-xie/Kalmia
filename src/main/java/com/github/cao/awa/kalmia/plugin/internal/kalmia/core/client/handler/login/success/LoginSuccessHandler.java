package com.github.cao.awa.kalmia.plugin.internal.kalmia.core.client.handler.login.success;

import com.github.cao.awa.apricot.annotation.auto.Auto;
import com.github.cao.awa.kalmia.annotation.plugin.PluginRegister;
import com.github.cao.awa.kalmia.event.handler.network.inbound.login.success.LoginSuccessEventHandler;
import com.github.cao.awa.kalmia.mathematic.Mathematics;
import com.github.cao.awa.kalmia.message.plains.PlainsMessage;
import com.github.cao.awa.kalmia.network.count.TrafficCount;
import com.github.cao.awa.kalmia.network.packet.Packet;
import com.github.cao.awa.kalmia.network.packet.inbound.login.success.LoginSuccessPacket;
import com.github.cao.awa.kalmia.network.packet.inbound.message.select.SelectMessagePacket;
import com.github.cao.awa.kalmia.network.packet.inbound.message.send.SendMessagePacket;
import com.github.cao.awa.kalmia.network.router.RequestRouter;
import com.github.cao.awa.kalmia.network.router.status.RequestState;
import com.github.cao.awa.modmdo.annotation.platform.Client;

@Auto
@Client
@PluginRegister(name = "kalmia_core")
public class LoginSuccessHandler implements LoginSuccessEventHandler {
    @Auto
    @Client
    @Override
    public void handle(RequestRouter router, LoginSuccessPacket packet) {
        System.out.println("---Login success---");
        System.out.println("UID: " + packet.uid());
        System.out.println("Token: " + Mathematics.radix(packet.token(),
                                                         36
        ));

        router.setUid(packet.uid());

        router.setStates(RequestState.AUTHED);

//        EntrustEnvironment.thread(() -> {
//                              for (int i = 0; i < 500; i++) {
//                                  router.send(new SendMessageRequest(123,
//                                                                     BytesRandomIdentifier.create(16),
//                                                                     new PlainMessage("fuck cao_awa " + i + " times",
//                                                                                      this.uid
//                                                                     ).toBytes()
//                                  ));
//                              }
//                          })
//                          .start();

        TrafficCount.show();

//        router.send(new SelectMessagePacket(123456,
//                                            0,
//                                            200
//        ));

//        router.send(new RequestDuetSessionPacket(2));

        router.send(new SendMessagePacket(
                114514,
                new PlainsMessage("test",
                                  packet.uid()
                ),
                Packet.createReceipt()
        ));

//        for (int i = 0; i < 100; i++) {
//            router.send(new SendMessagePacket(0,
//                                              new PlainsMessage(" awa: " + i,
//                                                                packet.uid()
//                                              ),
//                                              Packet.createReceipt()
//            ));
//        }

        router.send(new SelectMessagePacket(0,
                                            0,
                                            114514
        ));

//        // TODO Test only
//        router.send(new DeleteMessageRequest(123,
//                                             2
//        ));
//        router.send(new DisableInstanceRequest());
    }
}
