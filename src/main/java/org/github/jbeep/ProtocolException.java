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
package org.github.jbeep;

public class ProtocolException extends BeepException {

    private static final long serialVersionUID = -922866540191753742L;

    public ProtocolException(String message, Throwable cause) {
    	super(message, cause);
    }

    public ProtocolException(String message) {
    	super(message);
    }

    public ProtocolException(Throwable cause) {
    	super(cause);
    }

}
