package batchpoc;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImportTask implements Runnable {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private BatchDTO taskContext = null;
	private CountDownLatch threadSignal = null;
	BlockingQueue<Map> queue = null;

	public ImportTask(BlockingQueue<Map> queue, BatchDTO taskContext,
			CountDownLatch threadSignal) {
		this.taskContext = taskContext;
		this.threadSignal = threadSignal;
		this.queue = queue;
	}

	public void run() {
		boolean done = false;
		try {
			synchronized (this) {
				while (!done) {
					Map data = (Map) queue.take();
					if (data == EnumerationEnginee.DUMMY) {
						//no data
						queue.put(data);
						done = true;
					} else {
						// if (data != null) {
						for (Iterator it = data.keySet().iterator(); it
								.hasNext();) {
							String key = String.valueOf(it.next());
							System.out.print("import:>>>[" + key + "]  :  ["
									+ data.get(key) + "]");
						}
						System.out.println("\n");						
					}
				}
			}
		} catch (Exception e) {
			logger.error("import file into db error:" + e.getMessage(), e);
			try {
				Thread.interrupted();
			} catch (Exception ie) {
			}
			try {
				queue.put(EnumerationEnginee.DUMMY);
				done = true;
			} catch (Exception ex) {

			}
		} finally {
			threadSignal.countDown();
		}

	}
}
