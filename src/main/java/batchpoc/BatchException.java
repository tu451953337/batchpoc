package batchpoc;

public class BatchException extends Exception {

	private Object obj;
	private String message;

	public BatchException() {
		super();
	}

	public BatchException(String msg) {
		super(msg);
	}

	public BatchException(String msg, Throwable t) {
		super(msg, t);
	}

	public BatchException(Object obj, String msg) {
		this.obj = obj;
		this.message = msg;
	}

}
