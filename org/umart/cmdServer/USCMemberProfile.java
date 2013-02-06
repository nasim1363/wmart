package org.umart.cmdServer;

import java.text.*;
import java.util.*;

import org.umart.cmdCore.*;
import org.umart.serverNet.*;


public class USCMemberProfile extends UCMemberProfileCore implements IServerCmd {

  /** このコマンドを実行したエージェント */
  private UAgentForNetworkClient fAgent;

  /** サーバーへの参照 */
  UMartNetwork fUMart;

  /** コンストラクタ */
  public USCMemberProfile() {
    super();
    fAgent = null;
    fUMart = null;
  }

  public void setConnection(UAgentForNetworkClient agent, UMartNetwork umart) {
    fAgent = agent;
    fUMart = umart;
  }

  public UCommandStatus doIt() {
    try {
      int userID = fAgent.getLoginStatus().getUserID();
      fData.clear();
      fCommandStatus = fUMart.doMemberProfile(fData, userID, fTargetUserId);
      if (fCommandStatus.getStatus()) {
        fAgent.sendMessage("+ACCEPT");
        fAgent.sendMessage(fData.get(UCMemberProfileCore.STRING_LOGIN_NAME).
                           toString()); // 会員名
        fAgent.sendMessage(fData.get(UCMemberProfileCore.STRING_PASSWORD).
                           toString()); // パスワード
        fAgent.sendMessage(fData.get(UCMemberProfileCore.STRING_ATTRIBUTE).
                           toString()); // エージェント属性(Human or Machine)
        fAgent.sendMessage(fData.get(UCMemberProfileCore.STRING_CONNECTION).
                           toString()); // コネクション(Remote or Local)
        fAgent.sendMessage(UCMemberProfileCore.arrayListToString( (ArrayList)
            fData.get(UCMemberProfileCore.ARRAY_LIST_ACCESS))); // アクセス制限
        fAgent.sendMessage(fData.get(UCMemberProfileCore.STRING_REAL_NAME).
                           toString()); // 実際の名前
        fAgent.sendMessage(UCMemberProfileCore.arrayListToString( (ArrayList)
            fData.get(UCMemberProfileCore.ARRAY_LIST_SYSTEM_PARAMETERS))); // システムパラメータ
        fAgent.sendMessage(fData.get(UCMemberProfileCore.INT_SEED).toString()); // 乱数の種
        fAgent.sendMessage(fData.get(UCMemberProfileCore.LONG_INITIAL_CASH).
                           toString()); // 初期資産
        fAgent.sendMessage(fData.get(UCMemberProfileCore.LONG_TRADING_UNIT).
                           toString()); // 取引単位
        fAgent.sendMessage(fData.get(UCMemberProfileCore.LONG_FEE_PER_UNIT).
                           toString()); // 単位取引あたりの手数料
        fAgent.sendMessage(fData.get(UCMemberProfileCore.LONG_MARGIN_RATE).
                           toString()); // 証拠金率
        fAgent.sendMessage(fData.get(UCMemberProfileCore.LONG_MAX_LOAN).
                           toString()); // 借り入れ限度額
        DecimalFormat numFormat = new DecimalFormat();
        numFormat.setMaximumFractionDigits(4);
        String tmp = numFormat.format( ( (Double) fData.get(UCMemberProfileCore.
            DOUBLE_INTEREST)).doubleValue());
        fAgent.sendMessage(fData.get(UCMemberProfileCore.DOUBLE_INTEREST).
                           toString()); // 借り入れ金利
        fAgent.sendMessage(fData.get(UCMemberProfileCore.INT_STATUS).toString()); // 取引可能(+1),取引不可能(-1)
        fAgent.sendMessage(fData.get(UCMemberProfileCore.INT_NO_OF_LOGIN_AGENTS).
                           toString()); // ログイン中のエージェント数
        HashMap yesterdayBalance = (HashMap) fData.get(UCMemberProfileCore.
            HASHMAP_YESTERDAY_BALANCE); // 前日収支
        fAgent.sendMessage(yesterdayBalance.get(UCMemberProfileCore.
                                                LONG_INITIAL_CASH).toString()); // 初期所持金
        fAgent.sendMessage(yesterdayBalance.get(UCMemberProfileCore.LONG_LOAN).
                           toString()); // 借入金
        fAgent.sendMessage(yesterdayBalance.get(UCMemberProfileCore.
                                                LONG_UNREALIZED_PROFIT).
                           toString()); // 未実現損益
        fAgent.sendMessage(yesterdayBalance.get(UCMemberProfileCore.LONG_MARGIN).
                           toString()); // 預託証拠金
        fAgent.sendMessage(yesterdayBalance.get(UCMemberProfileCore.
                                                LONG_SUM_OF_FEE).toString()); // 総支払い手数料
        fAgent.sendMessage(yesterdayBalance.get(UCMemberProfileCore.
                                                LONG_SUM_OF_INTEREST).toString()); // 総支払い金利
        fAgent.sendMessage(yesterdayBalance.get(UCMemberProfileCore.LONG_CASH).
                           toString()); // 保有現金
        fAgent.sendMessage(yesterdayBalance.get(UCMemberProfileCore.LONG_PROFIT).
                           toString()); // 実現損益
        HashMap todayBalance = (HashMap) fData.get(UCMemberProfileCore.
            HASHMAP_TODAY_BALANCE); // 当日収支
        fAgent.sendMessage(todayBalance.get(UCMemberProfileCore.
                                            LONG_INITIAL_CASH).toString()); // 初期所持金
        fAgent.sendMessage(todayBalance.get(UCMemberProfileCore.LONG_LOAN).
                           toString()); // 借入金
        fAgent.sendMessage(todayBalance.get(UCMemberProfileCore.
                                            LONG_UNREALIZED_PROFIT).toString()); // 未実現損益
        fAgent.sendMessage(todayBalance.get(UCMemberProfileCore.LONG_MARGIN).
                           toString()); // 預託証拠金
        fAgent.sendMessage(todayBalance.get(UCMemberProfileCore.LONG_SUM_OF_FEE).
                           toString()); // 総支払い手数料
        fAgent.sendMessage(todayBalance.get(UCMemberProfileCore.
                                            LONG_SUM_OF_INTEREST).toString()); // 総支払い金利
        fAgent.sendMessage(todayBalance.get(UCMemberProfileCore.LONG_CASH).
                           toString()); // 保有現金
        fAgent.sendMessage(todayBalance.get(UCMemberProfileCore.LONG_PROFIT).
                           toString()); // 実現損益
        HashMap position = (HashMap) fData.get(UCMemberProfileCore.
                                               HASHMAP_POSITION); // ポジション
        fAgent.sendMessage(position.get(UCMemberProfileCore.
            LONG_SUM_OF_SELL_POSITIONS_UNTIL_YESTERDAY).toString()); // 前日までの売ポジションの合計
        fAgent.sendMessage(position.get(UCMemberProfileCore.
            LONG_SUM_OF_BUY_POSITIONS_UNTIL_YESTERDAY).toString()); // 前日までの買ポジションの合計
        fAgent.sendMessage(position.get(UCMemberProfileCore.
                                        LONG_TODAY_SELL_POSITIONS).toString()); // 当日の売ポジションの合計
        fAgent.sendMessage(position.get(UCMemberProfileCore.
                                        LONG_TODAY_BUY_POSITIONS).toString()); // 当日の買ポジションの合計
      } else {
        fAgent.sendMessage("+ERROR " + fCommandStatus.getErrorCode());
        fAgent.sendMessage(fCommandStatus.getErrorMessage());
      }
    } catch (Exception e) {
      fAgent.sendMessage("+ERROR " + ICommand.INVALID_ARGUMENTS);
      fAgent.sendMessage("USAGE: MEMBERPROFILE");
    }
    fAgent.flushMessage();
    return fCommandStatus;
  }

}
