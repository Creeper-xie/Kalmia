package com.github.cao.awa.kalmia.framework.serialize.type.primitive;

import com.github.cao.awa.apricot.io.bytes.reader.BytesReader;
import com.github.cao.awa.kalmia.annotation.auto.serializer.AutoSerializer;
import com.github.cao.awa.kalmia.framework.serialize.BytesSerializer;

@AutoSerializer(value = 1, target = {Byte.class, byte.class})
public class ByteSerializer implements BytesSerializer<Byte> {
    @Override
    public byte[] serialize(Byte b) {
        return new byte[]{b};
    }

    @Override
    public Byte deserialize(BytesReader reader) {
        return reader.read();
    }
}