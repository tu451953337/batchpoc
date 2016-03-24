package batchpoc;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

import javax.jms.Destination;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BatchImportExec extends BatchTask {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final int TASK_THREAD = 50;
	private final int TASK_LIST_SIZE = 5000;
	private String fileType;
	private String fileName;
	private String colNames;
	private File file;

	public BatchImportExec(String fileType, String fileName, String colNames,
			BatchDTO taskContext) {
		super(taskContext);
		this.fileType = fileType;
		this.fileName = fileName;
		this.colNames = colNames;
	}

	public void doBatch() {
		// logger.info(">>>>>>>>>>doBatch");
		importFile(fileType, fileName, colNames, taskContext);
	}

	public void importFile(String fileType, String fileName, String colNames,
			BatchDTO taskContext) {
		long startGenTime = 0;
		long endGenTime = 0;
		try {
			startGenTime = System.currentTimeMillis();
			logger.info(">>>>>>>>>>start importing");
			BlockingQueue<Map> queue = new ArrayBlockingQueue<Map>(
					TASK_LIST_SIZE);
			// BlockingQueue<String[]> queue =
			// BatchTaskQueue.getInstance().taskList;
			EnumerationEnginee enumerator = EnumerationEngineeFactory
					.getInstance(queue, fileType, fileName, colNames,
							taskContext.isHeadSkip(), taskContext);
			if (enumerator != null) {
				new Thread(enumerator).start();
				CountDownLatch threadSignal = new CountDownLatch(TASK_THREAD);
				ImportTask importTask = new ImportTask(queue, taskContext,
						threadSignal);
				for (int i = 0; i < TASK_THREAD; i++) {
					new Thread(importTask).start();
				}
				threadSignal.await();
			}
		} catch (Exception e) {
			logger.error("import file error: " + e.getMessage(), e);
		} finally {
			endGenTime = System.currentTimeMillis();
			logger.info("total spent>>>>" + (endGenTime - startGenTime) + "ms");
		}
	}
}
