package batchpoc;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class EnumerationEnginee implements Runnable {
	protected String fileName = "";
	protected String colNames = "";
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	protected boolean skipHeader = true;
	protected BatchDTO taskContext = null;
	protected static Map DUMMY = new HashMap();
	protected BlockingQueue<Map> queue = null;

	public EnumerationEnginee(BlockingQueue<Map> queue, String fileName,
			String colNames, boolean skipHeader, BatchDTO taskContext) {
		this.fileName = fileName;
		this.colNames = colNames;
		this.skipHeader = skipHeader;
		this.taskContext = taskContext;
		this.queue = queue;
	}

	public abstract void enumerate(String fileName, String strKeys)
			throws Exception;

	public abstract void run();

}
