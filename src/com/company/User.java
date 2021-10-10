package com.company;

import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.util.List;

public class User {

    static private int points;
    static private String name;
    private static List<String[]> data;

    public static void setPoints(int points) {
        User.points = points;
    }

    public static void setName(String name) {
        User.name = name;
    }

    public static int getPoints() {
        return points;
    }

    public static String getName() {
        return name;
    }

    public static void save() {
        JFrame saveScreen=new JFrame("Minesweeper");
        saveScreen.setLayout(null);
        saveScreen.setSize(300,200);
        Methods.frameMaster(saveScreen);


        int xFrameOffset=15;
        int yFrameOffset=40;

        int width=200;
        int height=20;
        int posX=(saveScreen.getWidth()-xFrameOffset)/2-width/2;
        int posY=(saveScreen.getHeight()-yFrameOffset)/2-height/2-40;

        JTextField nameField=new JTextField();
        nameField.setHorizontalAlignment(SwingConstants.CENTER);
        nameField.setBounds(posX,posY,width,height);
        saveScreen.add(nameField);

        JLabel label=new JLabel(String.valueOf(points),SwingConstants.CENTER);
        label.setBounds(posX,posY+=height+10,width,height);
        saveScreen.add(label);

        JButton button=new JButton("Save");
        button.setBounds(posX,posY+height+10,width,height);
        button.addActionListener(e -> {
            setName(nameField.getText());
            try {
                intoDatabase(getName(),getPoints());
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        saveScreen.add(button);

        saveScreen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        saveScreen.setVisible(true);
    }
    static public void showHighScores(){
        try {
            databaseIntoJava();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    static public Connection database() throws SQLException {
        String pass="!Schu3l3r3"; //DB Passwort
        String user="1920_3b_arlfra"; //DB Benutzername
        String server = "htl-server.com";
        String db="1920_3b_arlfra_java";
        String url="jdbc:mysql://"+server+"/"+db;
        return DriverManager.getConnection(url,user,pass);
    }

    static public void databaseIntoJava() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=database();
        ResultSet res=con.createStatement().executeQuery("SELECT * FROM `score` ORDER BY `points` DESC ;");

        List<String[]> data=new ArrayList<>();
        while (res.next()){
            int id=res.getInt("id");
            String name=res.getString("name");
            int points=res.getInt("points");
            String []row=new String[3];
            row[0]=String.valueOf(id);
            row[1]=name;
            row[2]=String.valueOf(points);
            data.add(row);
        }
        User.data=data;

        String [] visibleTableColumnNames={"Name", "Point"};
        String [][] visibleTableCells=new String[User.data.size()][2];
        for (int i = 0; i < User.data.size(); i++) {
            for (int j = 0; j < 3; j++) {
                if (j==0){
                    continue;
                }
                visibleTableCells[i][j-1]=User.data.get(i)[j];
            }
        }

        for (int i = 0; i < visibleTableCells.length; i++) {
            for (int j = 0; j < visibleTableCells[0].length; j++) {
                System.out.print( visibleTableCells[i][j]+". ");
            }
            System.out.println();
        }

        JFrame listScreen=new JFrame("Minesweeper");
        listScreen.setLayout(null);
        listScreen.setSize(300,200);
        Methods.frameMaster(listScreen);

        int xOffset=15;
        int yOffset=40;
        int posX=15;
        int posY=10;
        int tableWidth=listScreen.getWidth()-xOffset-posX*2;
        int tableHeight=listScreen.getHeight()-yOffset-posY*2;

        JTable table=new JTable(visibleTableCells,visibleTableColumnNames);
        table.setBounds(posX,posY, tableWidth,tableHeight);




        JScrollPane scrollPane=new JScrollPane(table);
        scrollPane.setBounds(posX,posY, tableWidth,tableHeight);
        listScreen.add(scrollPane);

        listScreen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        listScreen.setVisible(true);
    }
    static public void intoDatabase(String nameColumn, int pointsColumn) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=database();
        con.createStatement().executeUpdate("INSERT INTO `score` (`id`, `name`, `points`) VALUES " +
                "((SELECT count(`id`)+1 from `score` as s), '"+nameColumn+"', '"+pointsColumn+"');");
    }
}
