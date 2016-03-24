package batchpoc;

import java.util.Date;

import util.Constants;

public class TestImpLogfile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final String fileName = "C:\\logs\\b2buser.log";
		// final String fileName = "d:/test_big.xlsx";
		try {
			GuidCreator myGUID = new GuidCreator();
			BatchDTO taskContext = new BatchDTO();
			String batchId = myGUID.createNewGuid(GuidCreator.AfterMD5);
			taskContext.setPkBtTaskId(batchId);
			taskContext.setTaskName(BatchTask.TXT_IMP_EXP);
			taskContext.setTaskDesc(fileName);
			taskContext.setCommitedBy("unittest");
			taskContext.setStatus(BatchTask.TASK_RUNNING);
			taskContext.setCommitedTime(new Date());
			taskContext.setBatchId(batchId);
			taskContext.setHeadSkip(true);
			BatchImportExec task = new BatchImportExec(
					Constants.ENUMERATION_TXT_TASK, fileName, "", taskContext);
			task.doBatch();
			// if (data != null && data.size() > 0) {
			// for (int i = 0; i < data.size(); i++) {
			// System.out.println("rows: " + i + "=====" + data.get(i));
			// }
			// }
			// BatchImportExec task = new BatchImportExec(
			// Constants.ENUMERATION_EXCEL_TASK, fileName, "", taskContext);
			// task.doBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
