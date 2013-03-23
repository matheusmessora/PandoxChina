package pandox.china.util;

import java.util.List;

public class ErrorMessage extends Message {

	public ErrorMessage(String msg) {
		super(msg, "alert-error");
	}
	
	public ErrorMessage(List<String> errors) {
		super(errors, "alert-error");
	}
}
