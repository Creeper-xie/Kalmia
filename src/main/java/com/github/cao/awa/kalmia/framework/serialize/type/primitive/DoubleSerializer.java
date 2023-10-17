package com.github.cao.awa.kalmia.framework.serialize.type.primitive;

import com.github.cao.awa.apricot.io.bytes.reader.BytesReader;
import com.github.cao.awa.kalmia.annotation.auto.serializer.AutoSerializer;
import com.github.cao.awa.kalmia.framework.serialize.BytesSerializer;

import java.nio.ByteBuffer;

@AutoSerializer(value = 7, target = {Double.class, double.class})
public class DoubleSerializer implements BytesSerializer<Double> {
    @Override
    public byte[] serialize(Double d) {
        return ByteBuffer.allocate(8)
                         .putDouble(d)
                         .array();
    }

    @Override
    public Double deserialize(BytesReader reader) {
        return ByteBuffer.wrap(reader.read(8))
                         .getDouble();
    }
}