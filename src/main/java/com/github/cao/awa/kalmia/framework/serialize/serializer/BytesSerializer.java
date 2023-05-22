package com.github.cao.awa.kalmia.framework.serialize.serializer;

import com.github.cao.awa.apricot.annotation.auto.Auto;
import com.github.cao.awa.apricot.io.bytes.reader.BytesReader;
import com.github.cao.awa.kalmia.env.KalmiaEnv;
import com.github.zhuaidadaya.rikaishinikui.handler.universal.entrust.EntrustEnvironment;

@Auto
public interface BytesSerializer<T> {
    @Auto
    byte[] serialize(T t) throws Exception;

    @Auto
    T deserialize(BytesReader reader) throws Exception;

    T initializer();

    default Class<T>[] target() {
        return EntrustEnvironment.cast(KalmiaEnv.serializeFramework.target(this));
    }

    default long id() {
        return KalmiaEnv.serializeFramework.id(this);
    }
}
