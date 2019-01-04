/**
 * 
 */
package com.tcl.shbc.mina.common;

/**
 * @author sumjohn
 *
 */
class DeviceDataPositionDef {

	private DeviceDataPositionDef()
	{
	}
	
	/**
	 * 控制字的开始位置
	 */
	static final int H_CTRL_START = 0;
	
	/**
	 * 控制字的长度
	 */
	static final int H_CTRL_LEN = 2;
	
	/**
	 * 信息包类型的开始位置
	 */
	static final int H_INFORMATIONTYPE_START = H_CTRL_START + H_CTRL_LEN;
	
	/**
	 * 信息包类型的长度
	 */
	static final int H_INFORMATIONTYPE_LEN = 2;
	
	/**
	 * 信息包接收方NetID的开始位置
	 */
	static final int H_DESTINATIONID_START = H_INFORMATIONTYPE_START + H_INFORMATIONTYPE_LEN;
	
	/**
	 * 信息包接收方NetID的长度
	 */
	static final int H_DESTINATIONID_LEN = 4;
	
	/**
	 * 信息包发送方NetID的开始位置
	 */
	static final int H_SOURCEID_START = H_DESTINATIONID_START + H_DESTINATIONID_LEN;
	
	/**
	 * 信息包发送方NetID的长度
	 */
	static final int H_SOURCEID_LEN = 4;
	
	/**
	 * 序列号的开始位置
	 */
	static final int H_SN_START = H_SOURCEID_START + H_SOURCEID_LEN;
	
	/**
	 * 序列号的长度
	 */
	static final int H_SN_LEN = 4;
	
	/**
	 * 命令字的开始位置
	 */
	static final int H_CMD_START = H_SN_START + H_SN_LEN;
	
	/**
	 * 命令字的长度
	 */
	static final int H_CMD_LEN = 2;
	
	/**
	 * 消息体总数据长度的开始位置
	 */
	static final int H_LEN_START = H_CMD_START + H_CMD_LEN;
	
	/**
	 * 消息体总数据长度的长度
	 */
	static final int H_LEN_LEN = 2;
	
	/**
	 * 消息体总数据的开始位置
	 */
	static final int D_CMD_DATA_START = H_LEN_START + H_LEN_LEN;
	
	
	
	
	
//************************************************************************************	协议第二版本 数据包格式*******************************************************
	
	/**
	 * 控制字的开始位置
	 */
	static final int H_CTRL_START_V2 = 0;
	
	/**
	 * 控制字的长度
	 */
	static final int H_CTRL_LEN_V2 = 2;
	
	
	
	/**
	 * 消息体总数据长度的开始位置
	 */
	static final int H_LEN_START_V2 = H_CTRL_START_V2 + H_CTRL_LEN_V2;
	
	/**
	 * 消息体总数据长度的长度
	 */
	static final int H_LEN_LEN_V2 = 2;
	
	
	/**
	 * 序列号的开始位置
	 */
	static final int H_SN_START_V2 = H_LEN_START_V2 + H_LEN_LEN_V2;
	
	/**
	 * 序列号的长度
	 */
	static final int H_SN_LEN_V2 = 4;
	
	
	/**
	 * 时间戳的开始位置
	 */
	static final int H_TIME_STAMP_START_V2 = H_SN_START_V2 + H_SN_LEN_V2;
	
	/**
	 * 时间戳的长度
	 */
	static final int H_TIME_STAMP_LEN_V2 = 8;
	
	
	
	/**
	 * 信息包类型的开始位置
	 */
	static final int H_INFORMATIONTYPE_START_V2 = H_TIME_STAMP_START_V2 + H_TIME_STAMP_LEN_V2;
	
	/**
	 * 信息包类型的长度
	 */
	static final int H_INFORMATIONTYPE_LEN_V2 = 2;
	
	/**
	 * 信息包接收方NetID的开始位置
	 */
	static final int H_DESTINATIONID_START_V2 = H_INFORMATIONTYPE_START_V2 + H_INFORMATIONTYPE_LEN_V2;
	
	/**
	 * 信息包接收方NetID的长度
	 */
	static final int H_DESTINATIONID_LEN_V2 = 4;
	
	/**
	 * 信息包发送方NetID的开始位置
	 */
	static final int H_SOURCEID_START_V2 = H_DESTINATIONID_START_V2 + H_DESTINATIONID_LEN_V2;
	
	/**
	 * 信息包发送方NetID的长度
	 */
	static final int H_SOURCEID_LEN_V2 = 4;
	
	/**
	 * 命令字的开始位置
	 */
	static final int H_CMD_START_V2 = H_SOURCEID_START_V2 + H_SOURCEID_LEN_V2;
	
	/**
	 * 命令字的长度
	 */
	static final int H_CMD_LEN_V2 = 2;
	
	/**
	 * 消息体总数据的开始位置
	 */
	static final int D_CMD_DATA_START_V2 = H_CMD_START_V2 + H_CMD_LEN_V2;
	
}
