package batchpoc;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

import util.Constants;

public class EnumerationEngineeFactory {

	public static EnumerationEnginee getInstance(BlockingQueue<Map> queue,
			String type, String fileName, String colNames, boolean skipHeader,
			BatchDTO taskContext) {
		EnumerationEnginee task = null;
		if (type.equals(Constants.ENUMERATION_TXT_TASK)) {
			return new TxtEnumerationTask(queue, fileName, colNames,
					skipHeader, taskContext);
		} else if (type.equals(Constants.ENUMERATION_EXCEL_TASK)) {
			return new XLSEnumerationTask(queue, fileName, colNames,
					skipHeader, taskContext);
		}
		return task;
	}
}
