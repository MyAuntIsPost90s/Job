package com.bs.job.gateway.base.chat;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.lingshi.websocket.model.SocketSession;
import com.lingshi.websocket.model.SocketSessionManage;
import com.lingshi.websocket.pool.SocketPoolFactory;

import lingshi.valid.ObjectValid;

@Component
@ServerEndpoint("/chatSession")
public class ChatSession extends SocketSession {
	private String key;
	private Session session;
	private SocketSessionManage socketSessionManage = SocketSessionManage.generate();
	private SocketSession lastSession;

	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		key = session.getRequestURI().getQuery().split("=")[1];
		lastSession = SocketPoolFactory.getDefaultSocketPool().get(key);
		socketSessionManage.onOpen(this);
	}

	@OnMessage
	public void onMessage(String json) {
		if (json.equals("pong")) {
			pong();
		} else {
			Chat chat = JSON.parseObject(json, Chat.class);
			if (!socketSessionManage.online(chat.getTargetKey())) {
				chat.setCode(0);
				chat.setErrMsg("当前用户不在线T_T");
				socketSessionManage.sendMessage(chat.getCurrKey(), JSON.toJSONString(chat));
			} else {
				chat.setCode(1);
				socketSessionManage.sendMessage(chat.getCurrKey(), JSON.toJSONString(chat));
				socketSessionManage.sendMessage(chat.getTargetKey(), JSON.toJSONString(chat));
			}
		}
	}

	@OnClose
	public void onClose() {
		try {
			socketSessionManage.onClose(this);
			session.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMessage(String message) {
		try {
			SocketSession socketSession = this;
			while (ObjectValid.isNotNull(socketSession)) {
				ChatSession chatSession = (ChatSession) socketSession;
				chatSession.session.getBasicRemote().sendText(message);
				socketSession = chatSession.lastSession;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getKey() {
		return key;
	}

}
