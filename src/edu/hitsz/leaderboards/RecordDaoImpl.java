package edu.hitsz.leaderboards;

import edu.hitsz.application.Main;
import ui.RecordsTable;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * RecordDao接口的具体实现类
 * @author leng
 */
public class RecordDaoImpl implements RecordDao {
    private List<PlayerRecord> playerRecords;

    RecordsTable recordsTable;
    String filename;


    public RecordDaoImpl() {
        playerRecords = new ArrayList<PlayerRecord>();

        switch (Main.difficulty) {
            case 1:
                filename = "src/edu/hitsz/leaderboards/easyPlayerRecords.dat";
                break;
            case 2:
                filename = "src/edu/hitsz/leaderboards/normalPlayerRecords.dat";
                break;
            case 3:
                filename = "src/edu/hitsz/leaderboards/difficultyPlayerRecords.dat";
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + Main.difficulty);
        }

        readRecords();
    }

    @Override
    public void doAdd(PlayerRecord record) {
        playerRecords.add(record);
        saveRecords();
    }

    @Override
    public List<PlayerRecord> getAllRecords() {
        return playerRecords;
    }

    @Override
    public void doDeleteByName(String playerName) {
        for (PlayerRecord playerRecord : playerRecords) {
            if (playerRecord.getPlayerName().equals(playerName)) {
                playerRecords.remove(playerRecord);
                saveRecords();
                return;
            }
        }
    }

    @Override
    public void doDeleteByRanking(int ranking) {
        for (PlayerRecord playerRecord : playerRecords) {
            if (playerRecord.getRanking() == ranking) {
                playerRecords.remove(playerRecord);
                saveRecords();
                return;
            }
        }
        recordsTable.updateData();


    }

    @Override
    public void printAll() {
        sortByScore();
    }


    public void addAfterEnd(PlayerRecord playerRecord) {
        sortByScore();
        //todo 打印历史记录排行榜

        RecordsTable recordsTable = new RecordsTable();
        Main.cardPanel.add(recordsTable.getMainPanel());
        Main.cardLayout.next(Main.cardPanel);

        //todo 弹出弹窗 问是否要加入排行榜 问名字
        String username = (String) JOptionPane.showInputDialog(null, "请输入名字记录得分:\n", "输入", JOptionPane.PLAIN_MESSAGE, null, null, "username");
        playerRecord.setPlayerName(username);
        if (username==null){
            //todo 不添加该条数据
        }else{
            //todo 添加该条数据
            this.doAdd(playerRecord);
            recordsTable.updateData();
        }
    }

    private void sortByScore() {
        playerRecords = playerRecords.stream().sorted((r1, r2) -> r2.getScore() - r1.getScore()).collect(Collectors.toList());

        playerRecords.stream().forEach(record -> record.setRanking(playerRecords.indexOf(record) + 1));
    }

    private void saveRecords() {
        sortByScore();

        // 创建序列化流
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(filename));
            // 写出对象
            oos.writeObject(playerRecords);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // 释放资源
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void readRecords() {
        // 反序列化
        ObjectInputStream ois = null;
        try {
            File file = new File(filename);
            if (file.length() != 0 && file.exists()) {
                ois = new ObjectInputStream(new FileInputStream(filename));
                // 读取对象,强转为ArrayList类型
                playerRecords = (ArrayList<PlayerRecord>) ois.readObject();
            }else if (!file.exists()){
                boolean newFile = file.createNewFile();
                System.out.println(newFile);
                System.out.println(file.exists());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }




}
