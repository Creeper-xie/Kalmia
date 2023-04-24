package com.github.cao.awa.kalmia.network.packet;

import com.github.cao.awa.kalmia.network.router.RequestRouter;
import com.github.cao.awa.viburnum.util.bytes.BytesUtil;

public abstract class WritablePacket extends Packet {
    private static final byte[] RECEIPT = new byte[]{- 1};

    public abstract byte[] data();

    public abstract byte[] id();

    public byte[] encode(RequestRouter router) {
        return router.encode(BytesUtil.concat(id(),
                                              receipt(),
                                              data()
        ));
    }

    public byte[] decode(RequestRouter router) {
        return router.decode(data());
    }

    public byte[] receipt() {
        return RECEIPT;
    }
}
