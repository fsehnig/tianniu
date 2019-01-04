package com.tcl.shbc.mina.codec;

import java.nio.charset.Charset;

import org.apache.mina.filter.codec.textline.TextLineCodecFactory;

import com.tcl.shbc.mina.common.Constants;

/**
 * 
 * @author mengke.xu
 *
 */
public class DeviceCodecFactory extends TextLineCodecFactory {
	
	public DeviceCodecFactory() {
		super(
				Charset.forName("ASCII"),
				Constants.BASE64_PCK_TAIL,
				Constants.BASE64_PCK_TAIL);
		setEncoderMaxLineLength(4096);
		setDecoderMaxLineLength(4096);
				
	}
}
