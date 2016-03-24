package batchpoc;

import java.text.SimpleDateFormat;

public class GuidByRandom {
	private static int cnt = 0;

	public static synchronized String getGUID() throws Exception {
		StringBuffer code = new StringBuffer();
		try {
			java.util.Date dt = new java.util.Date(System.currentTimeMillis());
			SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");//format system time 
			String randomCode = fmt.format(dt);
			cnt = (cnt + 1) % 10000; // You are free the set %100 to
			// 1000,100000
			code.append(randomCode).append(cnt);
			return code.toString();
		} catch (Exception e) {
			throw new Exception("createFileName error:" + e.getMessage(), e);
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println(getGUID());
	}
}
