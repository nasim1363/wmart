/*
 * Created on 2003/06/28
 *
 */
package org.wmart.core;

import java.io.*;
import java.util.*;

/**
 * 現在のサーバーの状態に応じて，指定されたSVMPコマンドが実行できるかチェックするクラスです．
 * 指定されたSVMPコマンドが実行できるかは，resources/SVMP.csvに記述されています．
 * 
 * @author 小野　功
 */
public class WCmdExecutableChecker {

	/**
	 * キーは”コマンド名：状態：SUフラグ”，値は”TRUE(実行可能)/FALSE(実行不可能)” ただし，SUフラグは，"Su":スーパーユーザー，"Non-Su":スーパーユーザー以外
	 */
	private HashMap fCmdExecutableTable;

	/** エラーメッセージ */
	private String fErrorMessage;

	/**
	 * コンストラクタ
	 */
	public WCmdExecutableChecker() {
		fCmdExecutableTable = new HashMap();
	}

	/**
	 * ストリームから情報を読み込む
	 * 
	 * @param br
	 *            入力ストリーム
	 * @throws IOException
	 */
	public void readFrom(BufferedReader br) throws IOException {
		br.readLine(); // numerical header
		String header = br.readLine();
		StringTokenizer st = new StringTokenizer(header, ",");
		st.nextToken();
		ArrayList statusArray = new ArrayList();
		while (st.hasMoreTokens()) {
			statusArray.add(st.nextToken());
		}
		String line = null;
		while ((line = br.readLine()) != null) {
			st = new StringTokenizer(line, ",");
			String cmdName = st.nextToken();
			int su = Integer.parseInt(st.nextToken());
			int index = 0;
			while (st.hasMoreTokens()) {
				boolean cmdExecutableFlag = false;
				if (Integer.parseInt(st.nextToken()) == 1) {
					cmdExecutableFlag = true;
				}
				String key = cmdName + ":" + (String) statusArray.get(index) + ":";
				if (su == 1) {
					fCmdExecutableTable.put(key + "Su", new Boolean(cmdExecutableFlag));
				} else if (su == 0) {
					fCmdExecutableTable.put(key + "Non-Su", new Boolean(cmdExecutableFlag));
				} else {
					System.err.println("Error in UCmdExecutableCheker.readFrom");
					System.exit(5);
				}
				++index;
			}
		}
	}

	/**
	 * statusの状態で，userIDをもつユーザーが，cmdNameを実行できるかちぇっくする．
	 * 
	 * @param cmdName
	 *            コマンド名
	 * @param status
	 *            サーバー状態
	 * @param userID
	 *            ユーザーID
	 * @return true:実行可能，false:実行不可能
	 */
	public boolean isExecutable(String cmdName, WServerStatus status, int userID) {
		String key = cmdName + ":" + status.getStateString() + ":";
		Boolean result = null;
		if (userID == WMart.SU_ID) {
			result = (Boolean) fCmdExecutableTable.get(key + "Su");
			if (result == null) {
				result = (Boolean) fCmdExecutableTable.get(key + "Non-Su");
				if (result == null) {
					System.err.print("Can't find " + cmdName + ":" + status.getStateString() + ":"
						+ userID);
					System.err.println(" in WCmdExecutableChecker.isExecutable");
					System.exit(5);
					return false;
				}
			}
			if (result.booleanValue() == false) {
				fErrorMessage = "SERVER STATE IS " + status.getStateString();
			}
			return result.booleanValue();
		} else {
			result = (Boolean) fCmdExecutableTable.get(key + "Non-Su");
			if (result == null) {
				Boolean suResult = (Boolean) fCmdExecutableTable.get(key + "Su");
				if (suResult != null) {
					fErrorMessage = "THIS COMMAND CAN USE ONLY SU";
					return false;
				} else {
					System.err.print("Can't find " + cmdName + ":" + status.getStateString() + ":"
						+ userID);
					System.err.println(" in WCmdExecutableChecker.isExecutable");
					System.exit(5);
					return false;
				}
			} else {
				if (result.booleanValue() == false) {
					fErrorMessage = "SERVER STATE IS " + status.getStateString();
				}
				return result.booleanValue();
			}
		}
	}

	/**
	 * エラーメッセージを返す．
	 * 
	 * @return エラーメッセージ
	 */
	public String getErrorMessage() {
		return fErrorMessage;
	}

	/**
	 * 内部状態を標準出力に出力する．
	 */
	public void printOn() {
		Iterator itr = fCmdExecutableTable.keySet().iterator();
		while (itr.hasNext()) {
			String key = (String) itr.next();
			Boolean value = (Boolean) fCmdExecutableTable.get(key);
			System.out.println(key + "=" + value);
		}
		// System.out.println(fCmdExecutableTable.toString());
	}

	/**
	 * テストメソッド
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("usage: java WCmdExecutableChecker csvFile");
			System.exit(1);
		}
		String filename = args[0];
		WCmdExecutableChecker chk = new WCmdExecutableChecker();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(
				filename)));
			chk.readFrom(br);
			br.close();
			BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
			String line = null;
			while (true) {
				System.out.print("cmdName status id>");
				line = br2.readLine();
				if (line.equals("exit")) {
					break;
				}
				if (line.equals("print")) {
					chk.printOn();
				} else {
					StringTokenizer st = new StringTokenizer(line);
					String cmdName = st.nextToken();
					WServerStatus status = new WServerStatusMaster();
					status.setState(Integer.parseInt(st.nextToken()));
					int id = Integer.parseInt(st.nextToken());
					boolean flag = chk.isExecutable(cmdName, status, id);
					System.out.println(cmdName + ":" + status.getStateString() + ":" + id + "="
						+ flag);
					if (!flag) {
						System.out.println(chk.getErrorMessage());
					}
				}
				System.out.println("cmdName status id>");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(5);
		}
	}
}
