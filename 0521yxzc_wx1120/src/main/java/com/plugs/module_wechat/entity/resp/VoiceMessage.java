package com.plugs.module_wechat.entity.resp;


import com.plugs.module_wechat.entity.resp.type.Voice;

/**
 * 声音消息
 * 
 * @author Administrator
 *
 */
public class VoiceMessage extends BaseMessage {

	private Voice voice;

	public Voice getVoice() {
		return voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
	}

}
