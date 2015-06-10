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

import java.net.SocketAddress;

import org.github.jbeep.Initiator;
import org.github.jbeep.SessionHandler;
import org.github.jbeep.internal.util.Assert;

import org.apache.mina.core.service.IoConnector;

public class MinaInitiator extends AbstractMinaPeer implements Initiator {

    private final IoConnector connector;

    public MinaInitiator(IoConnector connector) {
    	Assert.notNull("connector", connector);
    	this.connector = connector;
    }

    public void connect(SocketAddress address, SessionHandler handler) {
    	connector.connect(address);
    }

}
