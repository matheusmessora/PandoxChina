package pandox.china.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public abstract class Message {

	private static Logger log = Logger.getLogger(Message.class);

	public List<String> messages;

	public boolean hasMessage;

	// CSS class to be rendered in <div> element
	private String cssClass;

	public Message(List<String> messages, String cssClass) {
		super();
		if (messages == null || messages.isEmpty()) {
			String msg = "Mensagem vazia.";
			log.error(msg);
			throw new IllegalArgumentException(msg);
		}

		this.messages = messages;
		this.cssClass = cssClass;
		this.hasMessage = true;
	}
	
	public Message(String message, String cssClass) {
		super();
		if (StringUtils.isBlank(message)) {
			String msg = "Mensagem vazia.";
			log.error(msg);
			throw new IllegalArgumentException(msg);
		}
		
		this.messages = new ArrayList<String>();
		this.messages.add(message);
		this.cssClass = cssClass;
		this.hasMessage = true;
	}

	public void clear() {
		this.hasMessage = false;
		this.messages.clear();
	}

	public void addMessage(String msg) {
		messages.add(msg);
		this.hasMessage = true;
	}

	@Override
	public String toString() {
		return "Message: " + messages + "";
	}

	public boolean hasMessage() {
		return hasMessage;
	}

	public void setHasMessage(boolean hasMessage) {
		this.hasMessage = hasMessage;
	}

	public String getCssClass() {
		return cssClass;
	}

	public List<String> getMessages() {
		return messages;
	}
}
