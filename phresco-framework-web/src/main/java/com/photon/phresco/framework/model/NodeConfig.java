/*
 * ###
 * Framework Web Archive
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
package com.photon.phresco.framework.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class NodeConfig {

    private String proxy;
    private int maxSession;
    private int port;
    private String host;
    private boolean register;
    private int registerCycle;
    private int hubPort;
    private String hubHost;

    public NodeConfig() {
        
    }
    
    public String getProxy() {
        return proxy;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }
    
    public int getMaxSession() {
        return maxSession;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }
    
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

    public int getRegisterCycle() {
        return registerCycle;
    }

    public void setRegisterCycle(int registerCycle) {
        this.registerCycle = registerCycle;
    }

    public int getHubPort() {
        return hubPort;
    }

    public void setHubPort(int hubPort) {
        this.hubPort = hubPort;
    }

    public String getHubHost() {
        return hubHost;
    }

    public void setHubHost(String hubHost) {
        this.hubHost = hubHost;
    }

    public String toString() {
        return new ToStringBuilder(this,
                ToStringStyle.DEFAULT_STYLE)
                .append("proxy", getProxy())
                .append("maxSession", getMaxSession())
                .append("port", getPort())
                .append("host", getHost())
                .append("register", isRegister())
                .append("registerCycle", getRegisterCycle())
                .append("hubPort", getHubPort())
                .append("hubHost", getHubHost())
                .toString();
    }
}