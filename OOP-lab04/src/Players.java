import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Players {
    private JFrame playerList;
    private DefaultTableModel model, modelGamesDate, modelGamesResults;
    private JButton save, print, add, delete, buttonPlayer, buttonGamesDate, buttonGamesResult;
    private JToolBar toolBar, choosePanel;
    private JScrollPane scroll, scrollGamesDate, scrollGamesResults;
    private JTable players, gamesDate, gamesResults;
    private JComboBox player;
    private JTextField playerName;
    private JButton filter;
    private Toolkit toolkit;

    public static void main(String[] args){
        //Создание и отображение экранной формы
        new Players().show();
    };

    public void show(){
        //Создание вспомогательной переменной для отображения окна
        toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();


        //Создание главного окна
        playerList = new JFrame("Список игроков");
        playerList.setBounds(dimension.width/2 - 300, dimension.height/2-200, 600, 400);
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



        //Добавление реакции на действия для кнопок

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(playerList, "Проверка работоспособности кнопки Save");
            }
        });

        //Добавление кнопок на панель инструментов
        toolBar = new JToolBar("Tools");
        toolBar.add(save);
        toolBar.add(add);
        toolBar.add(delete);
        toolBar.add(print);

        //Размещение панели инструментов
        playerList.setLayout(new BorderLayout());
        playerList.add(toolBar, BorderLayout.NORTH);

        //Создание таблицы с данными об игроках
        String[] columnsPlayers = { "Player", "Number", "Position"};
        String[][] dataPlayers = {{ "Cristiano Ronaldo", "7", "forward"}, { "Endrick Felipe", "16", "forward"}};
        model = new DefaultTableModel(dataPlayers, columnsPlayers);
        players = new JTable(model);
        scroll = new JScrollPane(players);

        //Создание таблицы с датами игр
        String[] columnsGamesDate = { "Date", "Opponent"};
        String[][] dataGamesDate = {{ "19.10.2020", "CSKA"}, { "24.08.2023", "LOKO"}};
        modelGamesDate = new DefaultTableModel(dataGamesDate, columnsGamesDate);
        gamesDate = new JTable(modelGamesDate);
        scrollGamesDate = new JScrollPane(gamesDate);

        //Создание таблицы с результатами игр
        String[] columnsGamesResults = {"Date", "Player", "Scores"};
        String[][] dataGamesResults = {{"19.10.2020", "Cristiano Ronaldo", "4"}, {"24.08.2023", "Endrick Felipe", "2"}};
        modelGamesResults = new DefaultTableModel(dataGamesResults, columnsGamesResults);
        gamesResults = new JTable(modelGamesResults);
        scrollGamesResults = new JScrollPane(gamesResults);

        //Создание кнопок выбора таблицы
        buttonPlayer = new JButton("Players");
        buttonGamesResult = new JButton("Games Results");
        buttonGamesDate = new JButton("Games Date");

        //buttonPlayer.setFont(new Font("Candara", Font.BOLD, 20));

        //Создание подсказок для кнопок
        buttonPlayer.setToolTipText("Show players table");
        buttonGamesResult.setToolTipText("Show games results");
        buttonGamesDate.setToolTipText("Show dates of games");

        //Размещение таблицы с данными.
        playerList.add(scroll, BorderLayout.CENTER);

        //Добавление реакции на действия для кнопок
        buttonPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerList.add(scroll, BorderLayout.CENTER);
                playerList.setVisible(true);
            }
        });

        buttonGamesDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerList.add(scrollGamesDate, BorderLayout.CENTER);
                playerList.setVisible(true);
            }
        });

        buttonGamesResult.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerList.add(scrollGamesResults, BorderLayout.CENTER);
                playerList.setVisible(true);
            }
        });

        //Размещение кнопок в окне
        choosePanel = new JToolBar();
        choosePanel.setOrientation(SwingConstants.VERTICAL);
        choosePanel.add(buttonPlayer);
        choosePanel.add(buttonGamesDate);
        choosePanel.add(buttonGamesResult);

        playerList.add(choosePanel, BorderLayout.WEST);


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

        filter.addActionListener (new ActionListener()
        {
            public void actionPerformed (ActionEvent event)
            {
                try{ checkName(playerName);
                }
                catch(NullPointerException ex){
                    JOptionPane.showMessageDialog(playerList, ex.toString());
                }
                catch(MyException myEx){
                    JOptionPane.showMessageDialog(null, myEx.getMessage());
                }
            }
        });
    }

    private class MyException extends Exception{
        public MyException(){
            super("Некорректное имя игрока!");
        }
    }
    private void checkName(JTextField playerName) throws MyException, NullPointerException{
        String searchName = playerName.getText();
        if(searchName.contains("Player name")) throw new MyException();
        if (searchName.length() == 0) throw new NullPointerException();
    }

}

