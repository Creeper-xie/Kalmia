package com.github.cao.awa.kalmia.translation.network.packet.login.feedback;

import com.alibaba.fastjson2.JSONObject;
import com.github.cao.awa.apricot.annotations.Useless;
import com.github.cao.awa.apricot.annotations.auto.Auto;
import com.github.cao.awa.kalmia.annotations.actor.Getter;
import com.github.cao.awa.kalmia.annotations.auto.network.unsolve.AutoData;
import com.github.cao.awa.kalmia.annotations.inaction.DoNotSet;
import com.github.cao.awa.kalmia.annotations.translation.Translation;
import com.github.cao.awa.kalmia.translation.network.packet.TranslationPacket;

@Translation(type = "login", name = "login_success")
public class TranslationLoginSuccessPacket extends TranslationPacket {
    @AutoData(key = "uid")
    @DoNotSet
    private long uid;
    @AutoData(key = "token")
    @DoNotSet
    private String token;

    @Auto
    @Useless
    public TranslationLoginSuccessPacket(JSONObject json) {

    }

    public TranslationLoginSuccessPacket(long uid, String token) {
        this.uid = uid;
        this.token = token;
    }

    @Getter
    public long uid() {
        return this.uid;
    }

    @Getter
    public String token() {
        return this.token;
    }
}
