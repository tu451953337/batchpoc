package webpoc;

import javax.jms.Connection;

public class JMSConnectionMgr {
	private static ThreadLocal tl = new ThreadLocal();
	private static Connection conn = null;

	public static void createConnection() throws Exception {
		if (tl.get() == null) {
			conn = JMSConnection.getInstance().getConnection()
					.createConnection();
			// conn.start();
			tl.set(conn);
		}
	}

	public static Connection getConnection() throws Exception {
		return (Connection) tl.get();
	}

	public static void endConnection() {
		try {
			((Connection) tl.get()).stop();
		} catch (Exception e) {
		}
		try {
			((Connection) tl.get()).close();
		} catch (Exception e) {
		}
		tl.set(null);
	}

}
