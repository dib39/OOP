import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;

public class Players {
    private JFrame playerList;
    private DefaultTableModel model, modelGamesDate, modelGamesResults;
    private JButton save, print, add, delete, open, buttonPlayer, buttonGamesDate, buttonGamesResult;
    private JToolBar toolBar, choosePanel;
    private JScrollPane scroll, scrollGamesDate, scrollGamesResults;
    private JTable players, gamesDate, gamesResults;
    private JComboBox player;
    private JTextField playerName;
    private JButton filter;
    private Toolkit toolkit;

    public static void main(String[] args){
        new Players().show();
    }

    public void show(){
        toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();

        playerList = new JFrame("Список игроков");
        playerList.setBounds(dimension.width/2 - 300, dimension.height/2 - 200, 600, 400);
        playerList.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        save = new JButton(new ImageIcon("src/img/save.png"));
        print = new JButton(new ImageIcon("src/img/print.png"));
        add = new JButton(new ImageIcon("src/img/add.png"));
        delete = new JButton(new ImageIcon("src/img/delete.png"));
        open = new JButton(new ImageIcon("src/img/open.png"));

        save.setToolTipText("Save list of players to XML");
        print.setToolTipText("Print");
        add.setToolTipText("Add");
        delete.setToolTipText("Delete");
        open.setToolTipText("Load list of players from XML");

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToXML();
            }
        });

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFromXML();
            }
        });

        toolBar = new JToolBar("Tools");
        toolBar.add(save);
        toolBar.add(add);
        toolBar.add(delete);
        toolBar.add(print);
        toolBar.add(open);

        playerList.setLayout(new BorderLayout());
        playerList.add(toolBar, BorderLayout.NORTH);

        String[] columnsPlayers = { "Player", "Number", "Position"};
        String[][] dataPlayers = {{ "Cristiano Ronaldo", "7", "forward"}, { "Endrick Felipe", "16", "forward"}};
        model = new DefaultTableModel(dataPlayers, columnsPlayers);
        players = new JTable(model);
        scroll = new JScrollPane(players);

        String[] columnsGamesDate = { "Date", "Opponent"};
        String[][] dataGamesDate = {{ "19.10.2020", "CSKA"}, { "24.08.2023", "LOKO"}};
        modelGamesDate = new DefaultTableModel(dataGamesDate, columnsGamesDate);
        gamesDate = new JTable(modelGamesDate);
        scrollGamesDate = new JScrollPane(gamesDate);

        String[] columnsGamesResults = {"Date", "Player", "Scores"};
        String[][] dataGamesResults = {{"19.10.2020", "Cristiano Ronaldo", "4"}, {"24.08.2023", "Endrick Felipe", "2"}};
        modelGamesResults = new DefaultTableModel(dataGamesResults, columnsGamesResults);
        gamesResults = new JTable(modelGamesResults);
        scrollGamesResults = new JScrollPane(gamesResults);

        buttonPlayer = new JButton("Players");
        buttonGamesResult = new JButton("Games Results");
        buttonGamesDate = new JButton("Games Date");

        buttonPlayer.setToolTipText("Show players table");
        buttonGamesResult.setToolTipText("Show games results");
        buttonGamesDate.setToolTipText("Show dates of games");

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

        choosePanel = new JToolBar();
        choosePanel.setOrientation(SwingConstants.VERTICAL);
        choosePanel.add(buttonPlayer);
        choosePanel.add(buttonGamesDate);
        choosePanel.add(buttonGamesResult);

        playerList.add(choosePanel, BorderLayout.WEST);

        player = new JComboBox(new String[]{"Player", "Cristiano Ronaldo", "Endrick Felipe"});
        playerName = new JTextField("Player name");
        playerName.setBorder(BorderFactory.createLineBorder(Color.black));
        filter = new JButton("Search");

        JPanel filterPanel = new JPanel();
        filterPanel.add(player);
        filterPanel.add(playerName);
        filterPanel.add(filter);

        playerList.add(filterPanel, BorderLayout.SOUTH);
        playerList.setVisible(true);

        filter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    checkName(playerName);
                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(playerList, ex.toString());
                } catch (MyException myEx) {
                    JOptionPane.showMessageDialog(null, myEx.getMessage());
                }
            }
        });
    }

    private void saveToXML() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // Создаем корневой элемент <players>
            Element rootElement = doc.createElement("players");
            doc.appendChild(rootElement);

            // Проходим по всем строкам таблицы и сохраняем данные в XML
            for (int i = 0; i < model.getRowCount(); i++) {
                Element player = doc.createElement("player");
                player.setAttribute("name", model.getValueAt(i, 0).toString());
                player.setAttribute("number", model.getValueAt(i, 1).toString());
                player.setAttribute("position", model.getValueAt(i, 2).toString());
                rootElement.appendChild(player);
            }

            // Записываем данные в XML файл
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("players.xml"));

            transformer.transform(source, result);
            JOptionPane.showMessageDialog(playerList, "Данные успешно сохранены в файл players.xml");

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(playerList, "Ошибка при сохранении данных в XML файл.");
        }
    }

    private void loadFromXML() {
        try {
            File xmlFile = new File("players.xml");
            if (!xmlFile.exists()) {
                JOptionPane.showMessageDialog(playerList, "Ошибка: XML файл players.xml не найден.");
                return;
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("player");

            // Добавляем данные из XML файла в таблицу, не очищая существующие строки
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nNode;
                    String name = element.getAttribute("name");
                    String number = element.getAttribute("number");
                    String position = element.getAttribute("position");

                    // Добавляем строку с данными из XML в таблицу
                    model.addRow(new Object[]{name, number, position});
                }
            }

            JOptionPane.showMessageDialog(playerList, "Данные успешно загружены из файла players.xml");

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(playerList, "Ошибка при загрузке данных из XML файла.");
        }
    }








    private class MyException extends Exception {
        public MyException() {
            super("Некорректное имя игрока!");
        }
    }

    private void checkName(JTextField playerName) throws MyException, NullPointerException{
        String searchName = playerName.getText();
        if(searchName.contains("Player name")) throw new MyException();
        if (searchName.length() == 0) throw new NullPointerException();
    }

}
