package com.github.cao.awa.kalmia.client;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.github.cao.awa.apricot.resource.loader.ResourceLoader;
import com.github.cao.awa.apricot.util.collection.ApricotCollectionFactor;
import com.github.cao.awa.apricot.util.io.IOUtil;
import com.github.cao.awa.kalmia.config.kalmiagram.client.bootstrap.ClientBootstrapConfig;
import com.github.cao.awa.kalmia.constant.KalmiaConstant;
import com.github.cao.awa.kalmia.env.KalmiaEnv;
import com.github.cao.awa.kalmia.message.display.DisplayMessage;
import com.github.cao.awa.kalmia.message.manage.MessageManager;
import com.github.cao.awa.kalmia.network.io.client.KalmiaClientNetworkIo;
import com.github.cao.awa.kalmia.network.router.kalmia.RequestRouter;
import com.github.cao.awa.kalmia.session.manage.SessionManager;
import com.github.cao.awa.kalmia.user.manage.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.List;
import java.util.function.Consumer;

public class KalmiaClient {
    private static final Logger LOGGER = LogManager.getLogger("KalmiaClient");
    public static ClientBootstrapConfig clientBootstrapConfig;
    private final ClientBootstrapConfig bootstrapConfig;
    private final MessageManager messageManager;
    private final UserManager userManager;
    private final SessionManager sessionManager;
    private Consumer<RequestRouter> activeCallback;
    private RequestRouter router;

    public KalmiaClient(ClientBootstrapConfig config) {
        try {
            this.bootstrapConfig = config;

            this.messageManager = new MessageManager("data/client/msg");
            this.userManager = new UserManager("data/client/usr");
            this.sessionManager = new SessionManager("data/client/session");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ClientBootstrapConfig bootstrapConfig() {
        return this.bootstrapConfig;
    }

    public UserManager userManager() {
        return this.userManager;
    }

    public MessageManager messageManager() {
        return this.messageManager;
    }

    public SessionManager sessionManager() {
        return this.sessionManager;
    }

    public static void setupBootstrapConfig() throws Exception {
        prepareConfig();

        clientBootstrapConfig = ClientBootstrapConfig.read(
                JSONObject.parse(
                        IOUtil.read(new FileReader(KalmiaConstant.CLIENT_CONFIG_PATH))
                ),
                KalmiaEnv.DEFAULT_CLIENT_BOOTSTRAP_CONFIG
        );

        rewriteConfig(clientBootstrapConfig);
    }

    public static void rewriteConfig(ClientBootstrapConfig bootstrapConfig) throws Exception {
        LOGGER.info("Rewriting client config");

        IOUtil.write(new FileWriter(KalmiaConstant.CLIENT_CONFIG_PATH),
                     bootstrapConfig.toJSON()
                                    .toString(JSONWriter.Feature.PrettyFormat)
        );
    }

    public static void prepareConfig() throws Exception {
        LOGGER.info("Preparing client config");

        File configFile = new File(KalmiaConstant.CLIENT_CONFIG_PATH);

        configFile.getParentFile()
                  .mkdirs();

        if (! configFile.isFile()) {
            IOUtil.write(
                    new FileWriter(configFile),
                    IOUtil.read(
                            new InputStreamReader(
                                    ResourceLoader.get(KalmiaConstant.CLIENT_DEFAULT_CONFIG_PATH)
                            )
                    )
            );
        }
    }

    public RequestRouter router() {
        return this.router;
    }

    public void router(RequestRouter router) {
        this.router = router;
    }

    public KalmiaClient activeCallback(Consumer<RequestRouter> activeCallback) {
        this.activeCallback = activeCallback;
        return this;
    }

    public Consumer<RequestRouter> activeCallback() {
        return this.activeCallback;
    }

    public void disconnect() {
        this.router.disconnect();
    }

    public boolean useEpoll() {
        return this.bootstrapConfig.clientNetwork()
                                   .useEpoll();
    }

    public void connect() throws Exception {
        new KalmiaClientNetworkIo(this).connect(this.bootstrapConfig.clientNetwork());
    }

    public List<Long> sessionIds() {
        return userManager().sessionListeners(router().uid());
    }

    public List<DisplayMessage> getMessages(long sessionId, long startSelect, long endSelect) {
        List<DisplayMessage> messages = ApricotCollectionFactor.arrayList();

        messageManager()
                .operation(sessionId,
                           startSelect,
                           endSelect,
                           (seq, msg) -> {
                               messages.add(
                                       new DisplayMessage(
                                               sessionId,
                                               seq,
                                               msg.display()
                                       )
                               );
                           }
                );
        return messages;
    }

    public DisplayMessage getMessages(long sessionId, long messageSeq) {
        return new DisplayMessage(
                sessionId,
                messageSeq,
                messageManager()
                        .get(
                                sessionId,
                                messageSeq
                        )
                        .display()
        );
    }
}
