package org.github.jbeep.internal;

import org.github.jbeep.Channel;
import org.github.jbeep.ChannelFilter;
import org.github.jbeep.CloseChannelCallback;
import org.github.jbeep.CloseChannelRequest;
import org.github.jbeep.Message;
import org.github.jbeep.Reply;
import org.github.jbeep.ReplyHandler;
import org.github.jbeep.ChannelFilter.NextFilter;
import org.github.jbeep.internal.session.InternalChannelFilterChain;

public class DefaultChannelFilterChain implements InternalChannelFilterChain {

    private Entry head;

    private Entry tail;

    public DefaultChannelFilterChain(final ChannelFilter headFilter, final ChannelFilter tailFilter) {
    	head = new Entry(headFilter);
    	tail = new Entry(tailFilter);

    	head.next = tail;
    	tail.previous = head;
    }

    private void insertBetween(final Entry previous, final Entry next, final Entry entry) {
    	entry.next = next;
    	entry.previous = previous;
    	next.previous.next = entry;
    	next.previous = entry;
    }

    public void addAfter(final Class<? extends ChannelFilter> after, final ChannelFilter filter) {
    	Entry newEntry = new Entry(filter);

    	Entry entry = head;
    	while (entry != tail) {
    		if (after.isInstance(entry.getFilter())) {
    			break;
    		}
    		entry = entry.next;
    	}

    	if (entry == null) {
    		entry = tail.previous;
    	}

    	insertBetween(entry, entry.next, newEntry);
    }

    public void addBefore(final Class<? extends ChannelFilter> before, final ChannelFilter filter) {
    	Entry newEntry = new Entry(filter);

    	Entry entry = tail;
    	while (entry != head) {
    		if (before.isInstance(entry.getFilter())) {
    			break;
    		}
    		entry = entry.previous;
    	}

    	if (entry == null) {
    		entry = head.next;
    	}

    	insertBetween(entry, entry.next, newEntry);
    }

    public void addFirst(final ChannelFilter filter) {
    	Entry entry = new Entry(filter);
    	insertBetween(head, head.next, entry);
    }

    public void addLast(final ChannelFilter filter) {
    	Entry entry = new Entry(filter);
    	insertBetween(tail.previous, tail, entry);
    }

    public void fireFilterSendMessage(final Message message, final ReplyHandler replyHandler) {
    	callPreviousFilterSendMessage(tail, message, replyHandler);
    }

    private static void callPreviousFilterSendMessage(final Entry entry, final Message message, final ReplyHandler replyHandler) {
    	entry.getFilter().filterSendMessage(entry.getNextFilter(), message, replyHandler);
    }

    public void fireFilterClose(final CloseChannelCallback callback) {
    	callPreviousFilterClose(tail, callback);
    }

    private static void callPreviousFilterClose(final Entry entry, final CloseChannelCallback callback) {
    	entry.getFilter().filterClose(entry.getNextFilter(), callback);
    }

    public void fireFilterChannelOpened(final Channel channel) {
    	callNextFilterChannelOpened(head, channel);
    }

    private static void callNextFilterChannelOpened(final Entry entry, final Channel channel) {
    	entry.getFilter().filterChannelOpened(entry.getNextFilter(), channel);
    }

    public void fireFilterMessageReceived(final Message message, final Reply reply) {
    	callNextFilterMessageReceived(head, message, reply);
    }

    private static void callNextFilterMessageReceived(final Entry entry, final Message message, final Reply reply) {
    	entry.getFilter().filterMessageReceived(entry.getNextFilter(), message, reply);
    }

    public void fireFilterChannelCloseRequested(final CloseChannelRequest request) {
    	callNextFilterChannelCloseRequested(head, request);
    }

    private static void callNextFilterChannelCloseRequested(final Entry entry, final CloseChannelRequest request) {
    	entry.getFilter().filterChannelCloseRequested(entry.getNextFilter(), request);
    }

    public void fireFilterChannelClosed() {
    	callNextFilterChannelClosed(head);
    }

    private static void callNextFilterChannelClosed(final Entry entry) {
    	entry.getFilter().filterChannelClosed(entry.getNextFilter());
    }

    public void fireFilterReceivedRPY(final Message message) {
    	callNextFilterReceivedRPY(head, message);
    }

    private static void callNextFilterReceivedRPY(final Entry entry, final Message message) {
    	entry.getFilter().filterReceivedRPY(entry.getNextFilter(), message);
    }

    public void fireFilterReceivedERR(final Message message) {
    	callNextFilterReceivedERR(head, message);
    }

    private static void callNextFilterReceivedERR(final Entry entry, final Message message) {
    	entry.getFilter().filterReceivedERR(entry.getNextFilter(), message);
    }

    public void fireFilterReceivedANS(final Message message) {
    	callNextFilterReceivedANS(head, message);
    }

    private static void callNextFilterReceivedANS(final Entry entry, final Message message) {
    	entry.getFilter().filterReceivedANS(entry.getNextFilter(), message);
    }

    public void fireFilterReceivedNUL() {
    	callNextFilterReceivedNUL(head);
    }

    private static void callNextFilterReceivedNUL(final Entry entry) {
    	entry.getFilter().filterReceivedNUL(entry.getNextFilter());
    }

    public void fireFilterSendRPY(final Message message) {
    	callPreviousFilterSendRPY(tail, message);
    }

    private static void callPreviousFilterSendRPY(final Entry entry, final Message message) {
    	entry.getFilter().filterSendRPY(entry.getNextFilter(), message);
    }

    public void fireFilterSendERR(final Message message) {
    	callPreviousFilterSendERR(tail, message);
    }

    private static void callPreviousFilterSendERR(final Entry entry, final Message message) {
    	entry.getFilter().filterSendERR(entry.getNextFilter(), message);
    }

    public void fireFilterSendANS(final Message message) {
    	callPreviousFilterSendANS(tail, message);
    }

    private static void callPreviousFilterSendANS(final Entry entry, final Message message) {
    	entry.getFilter().filterSendANS(entry.getNextFilter(), message);
    }

    public void fireFilterSendNUL() {
    	callPreviousFilterSendNUL(tail);
    }

    private static void callPreviousFilterSendNUL(final Entry entry) {
    	entry.getFilter().filterSendNUL(entry.getNextFilter());
    }

    private static class Entry {
    	private Entry next;
    	private Entry previous;
    	private ChannelFilter filter;
    	private NextFilter nextFilter;

    	private Entry(ChannelFilter filter) {
    		this.filter = filter;
    		this.nextFilter = new NextFilter() {
    			public void filterSendMessage(Message message, ReplyHandler replyHandler) {
    				callPreviousFilterSendMessage(previous, message, replyHandler);
    			}
    			public void filterClose(CloseChannelCallback callback) {
    				callPreviousFilterClose(previous, callback);
    			}
    			public void filterChannelOpened(Channel channel) {
    				callNextFilterChannelOpened(next, channel);
    			}
    			public void filterMessageReceived(Message message, Reply reply) {
    				callNextFilterMessageReceived(next, message, reply);
    			}
    			public void filterChannelCloseRequested(CloseChannelRequest request) {
    				callNextFilterChannelCloseRequested(next, request);
    			}
    			public void filterChannelClosed() {
    				callNextFilterChannelClosed(next);
    			}
    			public void filterReceivedRPY(Message message) {
    				callNextFilterReceivedRPY(next, message);
    			}
    			public void filterReceivedERR(Message message) {
    				callNextFilterReceivedERR(next, message);
    			}
    			public void filterReceivedANS(Message message) {
    				callNextFilterReceivedANS(next, message);
    			}
    			public void filterReceivedNUL() {
    				callNextFilterReceivedNUL(next);
    			}
    			public void filterSendRPY(Message message) {
    				callPreviousFilterSendRPY(previous, message);
    			}
    			public void filterSendERR(Message message) {
    				callPreviousFilterSendERR(previous, message);
    			}
    			public void filterSendANS(Message message) {
    				callPreviousFilterSendANS(previous, message);
    			}
    			public void filterSendNUL() {
    				callPreviousFilterSendNUL(previous);
    			}
    		};
    	}

    	private ChannelFilter getFilter() {
    		return filter;
    	}

    	private NextFilter getNextFilter() {
    		return nextFilter;
    	}
    }

}
