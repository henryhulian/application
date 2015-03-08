package com.rest.game.business.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.rest.game.business.common.Code;
import com.rest.server.util.CookieUtil;
import com.rest.server.util.TokenUtil;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/captcha")
@Api(value = "/captcha", description = "验证码")
public class CaptchaController {
	
	private static final Log log = LogFactory.getLog(CaptchaController.class);
	
	@Autowired
	private DefaultKaptcha defaultKaptcha;
	
	@Value("#{cacheManager.getCache('checkCode')}")
	private Cache cache;

	@RequestMapping(value = "/image", method = RequestMethod.GET , produces=MediaType.IMAGE_JPEG_VALUE)
	@ApiOperation(value = "用户获取验证码",produces=MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> guest( 	HttpServletRequest request , 
											HttpServletResponse response
																			) throws IOException {
		//生成验证码
		String capText = defaultKaptcha.createText();
		BufferedImage bi = defaultKaptcha.createImage(capText);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( bi , "jpg", baos );
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		
		//把验证码值放入缓存
		String key = TokenUtil.createRandomToken();
		cache.put(key, capText);
		//设置Cookie
		CookieUtil.setCookie(response, "checkCodeKey",
				key, request.getContextPath(), true, -1);
		
		log.trace("cache key:"+key+" captcha:"+capText);
		
		//返回验证码
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		return new ResponseEntity<byte[]>(imageInByte, headers,
				HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ApiOperation(value = "用户核对证码")
	public Code value( HttpServletRequest request ,
										HttpServletResponse response,
										@ApiIgnore @CookieValue("checkCodeKey") String checkCodeKey,
										@RequestParam String checkCode) throws IOException {
		
		ValueWrapper valueWrapper = cache.get(checkCodeKey);
		
		if( valueWrapper!=null && valueWrapper.get().equals(checkCode)){
			return new Code(Code.SUCCESS);
		}else{
			return new Code(Code.CHECK_CODE_NOT_MATCH);
		}
		
		
	}
}
