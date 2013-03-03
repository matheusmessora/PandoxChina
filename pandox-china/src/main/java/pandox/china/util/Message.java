package pandox.china.util;

import org.springframework.util.StringUtils;

public abstract class Message {
	
	public String message;
	
	public boolean hasMessage;
	
	// CSS class to be rendered in <div> element
	private String cssClass;

	public Message(String msg, String cssClass) {
		super();
		this.message = msg;
		this.cssClass = cssClass;
		this.hasMessage = true;
	}
	
	public void clear(){
		this.hasMessage = false;
		this.message = null;
	}

	@Override
	public String toString() {
		return "Message: " + message + "";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
		if(StringUtils.hasText(message)){
			hasMessage = true;
		}
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
}
