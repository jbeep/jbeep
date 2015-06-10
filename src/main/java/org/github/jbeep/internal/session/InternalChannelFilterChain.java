package org.github.jbeep.internal.session;

import org.github.jbeep.Channel;
import org.github.jbeep.ChannelFilterChain;
import org.github.jbeep.CloseChannelCallback;
import org.github.jbeep.CloseChannelRequest;
import org.github.jbeep.Message;
import org.github.jbeep.Reply;
import org.github.jbeep.ReplyHandler;

public interface InternalChannelFilterChain extends ChannelFilterChain {

    // --> filtering Channel methods <--

    void fireFilterSendMessage(Message message, ReplyHandler replyHandler);

    void fireFilterClose(CloseChannelCallback callback);

    // --> filtering ChannelHandler methods <--

    void fireFilterChannelOpened(Channel channel);

    void fireFilterMessageReceived(Message message, Reply reply);

    void fireFilterChannelCloseRequested(CloseChannelRequest request);

    void fireFilterChannelClosed();

    // --> filtering ReplyHandler methods <--

    void fireFilterReceivedRPY(Message message);

    void fireFilterReceivedERR(Message message);

    void fireFilterReceivedANS(Message message);

    void fireFilterReceivedNUL();

    // --> filtering Reply methods <--

    void fireFilterSendRPY(Message message);

    void fireFilterSendERR(Message message);

    void fireFilterSendANS(Message message);

    void fireFilterSendNUL();

}
