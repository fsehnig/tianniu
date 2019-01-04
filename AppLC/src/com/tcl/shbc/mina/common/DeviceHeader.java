/**
 * 
 */
package com.tcl.shbc.mina.common;

import java.io.Serializable;

/**
 * @author xumk
 * Control Message Header
 *
 */
public class DeviceHeader implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 950477580130056435L;

    
    /**
     * ������
     */
    private short ctrl;
    
    /**
     * ��Ϣ������
     */
    private short informationType;
    
    /**
     * ��Ϣ����շ�NetID
     */
    private int destinationID;
    
    /**
     * ��Ϣ���ͷ�NetID
     */
    private int sourceID;
    
    /**
     * ���к�
     */
    private int sn;
    
    /**
     * ������
     */
    private short cmd;
    
    /**
     * ��Ϣ������ݳ���?
     */
    private short len;
    
    
    
    /**
	 * 时间�?
	 * v1可以为null
	 * v2中不能为null 
	 */
	private Long timeStamp;
	
	

	/**
	 * @return the ctrl
	 */
	public short getCtrl() {
		return ctrl;
	}

	/**
	 * @param ctrl the ctrl to set
	 */
	public void setCtrl(short ctrl) {
		this.ctrl = ctrl;
	}

	/**
	 * @return the informationType
	 */
	public short getInformationType() {
		return informationType;
	}

	/**
	 * @param informationType the informationType to set
	 */
	public void setInformationType(short informationType) {
		this.informationType = informationType;
	}

	/**
	 * @return the destinationID
	 */
	public int getDestinationID() {
		return destinationID;
	}

	/**
	 * @param destinationID the destinationID to set
	 */
	public void setDestinationID(int destinationID) {
		this.destinationID = destinationID;
	}

	/**
	 * @return the sourceID
	 */
	public int getSourceID() {
		return sourceID;
	}

	/**
	 * @param sourceID the sourceID to set
	 */
	public void setSourceID(int sourceID) {
		this.sourceID = sourceID;
	}

	/**
	 * @return the sn
	 */
	public int getSn() {
		return sn;
	}

	/**
	 * @param sn the sn to set
	 */
	public void setSn(int sn) {
		this.sn = sn;
	}

	/**
	 * @return the cmd
	 */
	public short getCmd() {
		return cmd;
	}

	/**
	 * @param cmd the cmd to set
	 */
	public void setCmd(short cmd) {
		this.cmd = cmd;
	}

	/**
	 * @return the len
	 */
	public short getLen() {
		return len;
	}

	/**
	 * @param len the len to set
	 */
	public void setLen(short len) {
		this.len = len;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

}
