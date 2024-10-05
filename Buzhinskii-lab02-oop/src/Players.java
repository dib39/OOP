import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class Players {
    private JFrame playerList;
    private DefaultTableModel model;
    private JButton save, print, add, delete;
    private JToolBar toolBar;
    private JScrollPane scroll;
    private JTable players;
    private JComboBox player;
    private JTextField playerName;
    private JButton filter;

    public static void main(String[] args){
        //Создание и отображение экранной формы
        new Players().show();
    };

    public void show(){
        playerList = new JFrame("Список игроков");
        playerList.setSize(600, 400);
        playerList.setLocation(200, 200);
        playerList.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Создание кнопок и прикрепление иконок
        save = new JButton(new ImageIcon("src/img/save.png"));
        print = new JButton(new ImageIcon("src/img/print.png"));
        add = new JButton(new ImageIcon("src/img/add.png"));
        delete = new JButton(new ImageIcon("src/img/delete.png"));

        //Настройка подсказок для кнопок
        save.setToolTipText("Save list of players");
        print.setToolTipText("Print");
        add.setToolTipText("Add");
        delete.setToolTipText("Delete");

        //Добавление кнопок на панель инструментов
        toolBar = new JToolBar("Tools");
        toolBar.add(save);
        toolBar.add(add);
        toolBar.add(delete);
        toolBar.add(print);

        //Размещение панели инструментов
        playerList.setLayout(new BorderLayout());
        playerList.add(toolBar, BorderLayout.NORTH);
        //Создание таблицы с данными
        String[] columns = { "Player", "Number", "Position"};
        String[][] data = {{ "Cristiano Ronaldo", "7", "forward"}, { "Endrick Felipe", "16", "forward"}};
        model = new DefaultTableModel(data, columns);
        players = new JTable(model);
        scroll = new JScrollPane(players);

        //Размещение таблицы с данными.
        playerList.add(scroll, BorderLayout.CENTER);

        //Подготовка компонентов поиска
        player = new JComboBox(new String[]{"Player", "Cristiano Ronaldo", "Endrick Felipe"});
        playerName = new JTextField("Player name");
        playerName.setBorder(BorderFactory.createLineBorder(Color.black));
        filter = new JButton("Search");

        //Добавление компонентов на панель
        JPanel filterPanel = new JPanel();
        filterPanel.add(player);
        filterPanel.add(playerName);
        filterPanel.add(filter);
        //Размещение панели поиска внизу окна.
        playerList.add(filterPanel, BorderLayout.SOUTH);

        //Визуализация экранной формы
        playerList.setVisible(true);
    }

}
