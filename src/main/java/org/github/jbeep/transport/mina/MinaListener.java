/*
 *  Copyright 2006 Simon Raess
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.github.jbeep.transport.mina;

import java.io.IOException;
import java.net.SocketAddress;

import org.github.jbeep.ChannelFilterChainBuilder;
import org.github.jbeep.Listener;
import org.github.jbeep.SessionHandler;
import org.github.jbeep.SessionHandlerFactory;
import org.github.jbeep.internal.util.Assert;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class MinaListener extends AbstractMinaPeer implements Listener {

    private static final String KEY = "beep.transport";

    private final IoAcceptor acceptor;

    public MinaListener(IoAcceptor acceptor) {
    	Assert.notNull("acceptor", acceptor);
    	this.acceptor = acceptor;
    }

    public void bind(SocketAddress address, SessionHandlerFactory factory) throws IOException {
    	Assert.notNull("factory", factory);
    	acceptor.bind(address);
    }

    public void unbind(SocketAddress address) {
    	acceptor.unbind(address);
    }

    public static class BEEPIoHandler extends IoHandlerAdapter {

    	private final SessionHandlerFactory factory;

    	private final ChannelFilterChainBuilder filterChainBuilder;

    	public BEEPIoHandler(SessionHandlerFactory factory, ChannelFilterChainBuilder builder) {
    		this.factory = factory;
    		this.filterChainBuilder = builder;
    	}

    	@Override
    	public void sessionOpened(IoSession session) throws Exception {
    		SessionHandler handler = factory.createSessionHandler();
    		MinaTransport transport = new MinaTransport(false, handler, filterChainBuilder);
    		session.setAttribute(KEY, transport);
    		transport.sessionOpened(session);
    	}

    	@Override
    	public void messageReceived(IoSession session, Object message) throws Exception {
    		MinaTransport transport = (MinaTransport) session.getAttribute(KEY);
    		transport.messageReceived(session, message);
    	}

    	@Override
    	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
    		MinaTransport transport = (MinaTransport) session.getAttribute(KEY);
    		if (transport != null) {
    			transport.exceptionCaught(session, cause);
    		} else {
    			cause.printStackTrace();
    		}
    	}

    	@Override
    	public void sessionClosed(IoSession session) throws Exception {
    		MinaTransport transport = (MinaTransport) session.removeAttribute(KEY);
    		transport.sessionClosed(session);
    	}

    }

}
