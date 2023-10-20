package com.github.cao.awa.kalmia.framework.serialize.bytes.type.array;

import com.github.cao.awa.apricot.io.bytes.reader.BytesReader;
import com.github.cao.awa.kalmia.annotation.auto.serializer.AutoBytesSerializer;
import com.github.cao.awa.kalmia.framework.serialize.bytes.ByteSerializeFramework;
import com.github.cao.awa.kalmia.framework.serialize.bytes.BytesSerializer;
import com.github.cao.awa.kalmia.mathematic.base.SkippedBase256;
import com.github.cao.awa.viburnum.util.bytes.BytesUtil;

/**
 * The serializer used to array of {@link Byte} in {@link ByteSerializeFramework}.<br>
 *
 * @author cao_awa
 * @author 草二号机
 * @see BytesSerializer
 * @see ByteSerializeFramework
 * @see Byte
 * @since 1.0.0
 */
@AutoBytesSerializer(value = 201, target = {byte[].class, Byte[].class})
public class BytesByteArraySerializer implements BytesSerializer<byte[]> {
    @Override
    public byte[] serialize(byte[] bytes) {
        return BytesUtil.concat(
                SkippedBase256.intToBuf(bytes.length),
                bytes
        );
    }

    @Override
    public byte[] deserialize(BytesReader reader) {
        return reader.read(SkippedBase256.readInt(reader));
    }
}