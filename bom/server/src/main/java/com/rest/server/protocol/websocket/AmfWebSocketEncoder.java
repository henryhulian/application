package com.rest.server.protocol.websocket;

import flex.messaging.io.SerializationContext;
import flex.messaging.io.amf.Amf3Output;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.rest.server.protocol.VideoGameDataPackage;


@Component
@Sharable
public class AmfWebSocketEncoder extends MessageToMessageEncoder<VideoGameDataPackage>{
	
	private static final Log log = LogFactory.getLog(AmfWebSocketEncoder.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, VideoGameDataPackage msg,
			List<Object> out) throws Exception {
		
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
		Amf3Output amf3Output = new Amf3Output(SerializationContext.getSerializationContext());  
		try {
			amf3Output.setOutputStream(outStream);  
			amf3Output.writeObject(msg);  
			
			byte content[] = outStream.toByteArray();  
			ByteBuf byteBuf = ctx.channel().alloc().buffer(content.length);
			byteBuf.writeBytes(content);
			WebSocketFrame socketFrame = new BinaryWebSocketFrame(byteBuf);
			out.add(socketFrame);
			log.debug(msg);
		}finally{
			amf3Output.close();
		}
	}

}
