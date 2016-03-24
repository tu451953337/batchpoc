package batchpoc;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BatchTaskTimer implements Runnable {
	private Logger log = LoggerFactory.getLogger(getClass());

	private String getSTime() {
		String startTime = "";
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			startTime = sim.format(new Date());
		} catch (Exception e) {
			startTime = "0";
		}
		return startTime;
	}

	private String getETime() {
		String endTime = "";
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			endTime = sim.format(new Date());
		} catch (Exception e) {
			endTime = "0";
		}
		return endTime;
	}

	private long getSpentTime(String sT, String eT) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long spentSecs = 0;
		try {
			spentSecs = (sim.parse(eT).getTime() - sim.parse(sT).getTime());
		} catch (Exception e) {
			spentSecs = 0;
		}
		return spentSecs;
	}

	public void run() {
		String st = "";
		String et = "";
		long totalTime = 0;
		try {
			st = getSTime();
			while (BatchTaskQueue.getInstance().taskList.size() > 0) {
			}

		} catch (Exception e) {

		} finally {
			et = getETime();
			totalTime = getSpentTime(st, et);
			log.info(">>>>>>import total spend: " + totalTime + " ms");
		}
	}
}
