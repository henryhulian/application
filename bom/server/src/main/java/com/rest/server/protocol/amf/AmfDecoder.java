package com.rest.server.protocol.amf;

import flex.messaging.io.SerializationContext;
import flex.messaging.io.amf.Amf3Input;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.rest.server.protocol.VideoGameDataPackage;


@Component
@Sharable
public class AmfDecoder extends MessageToMessageDecoder<ByteBuf> {
	
	private static final Log log = LogFactory.getLog(AmfDecoder.class);

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		
		/*read content*/
		byte[] content = new byte[in.readableBytes()];
		in.readBytes(content);

		/*decode amf3*/
		Amf3Input amf3Input = new Amf3Input(
				SerializationContext.getSerializationContext());
		try {
			amf3Input.setInputStream(new ByteArrayInputStream(content));
			out.add((VideoGameDataPackage)amf3Input.readObject());
			log.debug(out);
		} finally{
			amf3Input.close();
		}
	}

}
