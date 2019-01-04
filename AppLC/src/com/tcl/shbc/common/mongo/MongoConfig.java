/**
 * 
 */
package com.tcl.shbc.common.mongo;

/**
 * @author zsj
 *
 */
public class MongoConfig {
	String host;
    int port;
    String username;
    String password;
    int connectionsPerHost = 50;
    int socketTimeOut = 300000;
    int threadsAllowedToBlockForConnectionMultiplier = 500;
    String defaultDatebase;
	public void setHost(String host) {
		this.host = host;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setConnectionsPerHost(int connectionsPerHost) {
		this.connectionsPerHost = connectionsPerHost;
	}
	public void setSocketTimeOut(int socketTimeOut) {
		this.socketTimeOut = socketTimeOut;
	}
	public void setThreadsAllowedToBlockForConnectionMultiplier(
			int threadsAllowedToBlockForConnectionMultiplier) {
		this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
	}
	public void setDefaultDatebase(String defaultDatebase) {
		this.defaultDatebase = defaultDatebase;
	}
}
