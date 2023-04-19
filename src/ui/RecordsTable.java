package ui;

import edu.hitsz.application.Main;
import edu.hitsz.leaderboards.PlayerRecord;
import edu.hitsz.leaderboards.RecordDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author leng
 */
public class RecordsTable {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JScrollPane jScrollPane;
    private JTable scoreTable;
    private JLabel headerLabel;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JFrame frame;
    DefaultTableModel model = null;

    public RecordsTable() {
        headerLabel.setText("难度："+Main.getDifficultyStr());

        setModel();
        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int[] selectedRows = scoreTable.getSelectedRows();

                int row = scoreTable.getSelectedRow();
                if (row != -1) {
                    Object[] options = {"是", "否", "取消"};
                    int response = JOptionPane.showOptionDialog(deleteButton, "是否确认删除选中的游戏记录", "选择", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);


                    if (response == 0) {
                        //todo 删除相关的操作
                        setModel();
                        for (int i = selectedRows.length - 1; i >= 0; i--) {
                            model.removeRow(selectedRows[i]);
                            //todo 删除数据文件中的该行记录  从0开始的
                            RecordDaoImpl recordDao = new RecordDaoImpl();
                            recordDao.doDeleteByRanking(selectedRows[i] + 1);
                        }

                        updateData();

                    }
                }
            }
        });

        //scoreTable// 设置table内容居中
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();

        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        scoreTable.setDefaultRenderer(Object.class, tcr);
    }

    private DefaultTableModel setModel() {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");

        List<PlayerRecord> playerRecords = new RecordDaoImpl().getAllRecords();
        String[] columnName = {"名次", "玩家名", "得分", "记录时间"};
        String[][] tableData = new String[playerRecords.size()][4];
        for (int i = 0; i < playerRecords.size(); i++) {
            PlayerRecord playerRecord = playerRecords.get(i);
            tableData[i][0] = String.valueOf(playerRecord.getRanking());
            tableData[i][1] = playerRecord.getPlayerName();
            tableData[i][2] = String.valueOf(playerRecord.getScore());
            tableData[i][3] = format.format(playerRecord.getRecordTime());
        }
        //表格模型
        model = new DefaultTableModel(tableData, columnName) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        //从表格模型那里获取数据
        scoreTable.setModel(model);

        jScrollPane.setViewportView(scoreTable);
//        Jtale表格与数据通过TableModel分离，JTable并不存储自己的数据，而是从表格模型那里获取它的数据
        return model;
    }



    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void updateData() {
        setModel();
    }


}


