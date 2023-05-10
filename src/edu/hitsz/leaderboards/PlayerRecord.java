package edu.hitsz.leaderboards;



import edu.hitsz.application.Main;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 玩家的游戏记录
 * @author leng
 */
public class PlayerRecord implements Serializable {
    private static final long serialVersionUID =-2740839109752637650L;
    /**
     * 游戏难度
     */
    private int difficulty;

    /**
     * 玩家名次
     */
    private int ranking;

    /**
     * 玩家名
     */
    private String playerName;


    /**
     * 玩家得分
     */
    private int score;

    /**
     * 记录时间:Date
     */
    private Date recordTime;

    public PlayerRecord(int difficulty,int score, Date recordTime) {
        this.difficulty = difficulty;
        this.score = score;
        this.recordTime = recordTime;
    }

    /**
     * 获取
     * @return ranking
     */
    public int getRanking() {
        return ranking;
    }

    /**
     * 设置
     * @param ranking 排名
     */
    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    /**
     * 获取
     * @return playerName
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * 设置
     * @param playerName 玩家名
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * 获取
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * 获取
     * @return recordTime
     */
    public Date getRecordTime() {
        return recordTime;
    }


    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        return Main.getDifficultyStr(Main.difficulty)+": " + score + ", " + format.format(recordTime);
    }
}
