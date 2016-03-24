package batchpoc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BatchTaskQueue {

	public static String[] DUMMY = null;
	private static BatchTaskQueue m_instance = null;
	private final static int TASK_LIST_SIZE = 1000;
	public BlockingQueue<String[]> taskList = new ArrayBlockingQueue(TASK_LIST_SIZE);

	private BatchTaskQueue() {

	}

	synchronized public static BatchTaskQueue getInstance() {
		if (m_instance == null) {
			m_instance = new BatchTaskQueue();
		}
		return m_instance;
	}

}
