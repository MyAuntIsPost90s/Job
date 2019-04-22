package com.bs.job;

import javax.annotation.Resource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import lingshi.gateway.filter.EncodeFilter;
import lingshi.gateway.filter.TokenCheckFilter;

@Configuration
@MapperScan(basePackages = "com.bs.**.dao")
@ImportResource(locations = { "classpath:/spring/applicationContext.xml" })
public class AppStartConfig {
	
	@Resource
	private Environment environment;

	/**
	 * 配置编码过滤器
	 * 
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<EncodeFilter> encodeFilterRegistration() {
		FilterRegistrationBean<EncodeFilter> registration = new FilterRegistrationBean<EncodeFilter>();
		registration.setFilter(new EncodeFilter());
		registration.addUrlPatterns("/*");
		registration.addInitParameter("charset", environment.getProperty("filter.charset"));
		registration.setName("EncodeFilter");
		return registration;
	}

	/**
	 * 配置token校验过滤器
	 * 
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<TokenCheckFilter> tokenCheckFilterRegistration() {
		FilterRegistrationBean<TokenCheckFilter> registration = new FilterRegistrationBean<TokenCheckFilter>();
		registration.setFilter(new TokenCheckFilter());
		registration.setName("TokenCheckFilter");
		registration.addUrlPatterns("/app/*","/sys/*","/base/*");
		registration.addInitParameter("isCross", environment.getProperty("filter.isCross"));
		registration.addInitParameter("allowPath", environment.getProperty("filter.allowPath"));
		return registration;
	}
	
	@Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
