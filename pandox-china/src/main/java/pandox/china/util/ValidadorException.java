package pandox.china.util;

import java.util.List;

public class ValidadorException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private List<String> erros;

	public ValidadorException(List<String> erros) {
		super();
		this.erros = erros;
	}

	public List<String> getErros() {
		return erros;
	}
}
