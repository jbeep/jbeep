package org.github.jbeep.internal;

import org.github.jbeep.ChannelHandler;
import org.github.jbeep.Reply;
import org.github.jbeep.ReplyHandler;
import org.github.jbeep.internal.management.CloseCallback;

public class FilterChainTargetHolder {

    private static final ThreadLocal<ChannelHandler> CHANNEL_HANDLER_HOLDER = new ThreadLocal<ChannelHandler>();

    private static final ThreadLocal<Reply> REPLY_HOLDER = new ThreadLocal<Reply>();

    private static final ThreadLocal<ReplyHandler> REPLY_HANDLER_HOLDER = new ThreadLocal<ReplyHandler>();

    private static final ThreadLocal<CloseCallback> CLOSE_CALLBACK_HOLDER = new ThreadLocal<CloseCallback>();

    public static void setReply(Reply reply) {
        REPLY_HOLDER.set(reply);
    }

    /**
     * getReply().
     *
     * @return Reply
     */
    public static Reply getReply() {
        return REPLY_HOLDER.get();
    }

    public static void setReplyHandler(final ReplyHandler target) {
        REPLY_HANDLER_HOLDER.set(target);
    }

    /**
     * getReplyHandler().
     *
     * @return ReplyHandler.
     */
    public static ReplyHandler getReplyHandler() {
        return REPLY_HANDLER_HOLDER.get();
    }

    public static void setCloseCallback(final CloseCallback target) {
        CLOSE_CALLBACK_HOLDER.set(target);
    }

    /**
     * getCloseCallback().
     *
     * @return CloseCallback
     */
    public static CloseCallback getCloseCallback() {
        return CLOSE_CALLBACK_HOLDER.get();
    }

    public static void setChannelHandler(final ChannelHandler target) {
        CHANNEL_HANDLER_HOLDER.set(target);
    }

    /**
     * getChannelHandler().
     *
     * @return ChannelHandler
     */
    public static ChannelHandler getChannelHandler() {
        return CHANNEL_HANDLER_HOLDER.get();
    }

}
