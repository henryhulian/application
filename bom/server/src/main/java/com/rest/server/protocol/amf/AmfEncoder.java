package com.rest.server.protocol.amf;

import flex.messaging.io.SerializationContext;
import flex.messaging.io.amf.Amf3Output;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.ByteArrayOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.rest.server.protocol.VideoGameDataPackage;


@Component
@Sharable
public class AmfEncoder extends MessageToByteEncoder<VideoGameDataPackage>{
	
	private static final Log log = LogFactory.getLog(AmfEncoder.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, VideoGameDataPackage msg,
			ByteBuf out) throws Exception {
		
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
		Amf3Output amf3Output = new Amf3Output(SerializationContext.getSerializationContext());  
		try {
			amf3Output.setOutputStream(outStream);  
			amf3Output.writeObject(msg);  
			
			byte content[] = outStream.toByteArray();  
			out.writeInt(content.length);
			out.writeBytes(content);
			log.debug(msg);
		}finally{
			amf3Output.close();
		}
	}

}
