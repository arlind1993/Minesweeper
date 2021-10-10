package com.company;

import javax.swing.*;
import java.awt.*;

public class Methods {
    public static void frameMaster(JFrame frame){
        frame.setLayout(null);
        frame.setTitle("Minesweeper");

        Dimension screenSize= Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight=screenSize.height;
        int screenWidth=screenSize.width;
        int frameHeight=frame.getHeight();
        int frameWidth=frame.getWidth();
        frame.setLocation(screenWidth/2-frameWidth/2,screenHeight/2-frameHeight/2);
        //frame.setBackground(Color.DARK_GRAY);
    }

    public static ImageIcon iconsIO(String name){
        ImageIcon empty=new ImageIcon("icons/empty.png");
        ImageIcon flag=new ImageIcon("icons/flag.png");
        ImageIcon mine=new ImageIcon("icons/mine.png");
        ImageIcon question=new ImageIcon("icons/question.png");

        switch (name){
            case "empty":
                return empty;
            case "flag":
                return flag;
            case "mine":
                return mine;
            case "question":
                return question;
            default:
                return null;
        }

    }

    public static ImageIcon iconsIO(String name, int index){
        ImageIcon [] numbers=new ImageIcon[9];
        for (int i = 0; i < 9; i++) {
            numbers[i]=new ImageIcon("icons/numbers/"+(i+1)+".png");
        }
        ImageIcon[] faces=new ImageIcon[4];

        for (int i = 0; i < 4; i++) {
            String filepath="icons/faces/";
            switch (i) {
                case 0:
                    filepath += "smiley";
                    break;
                case 1:
                    filepath += "smug";
                    break;
                case 2:
                    filepath += "ded";
                    break;
                case 3:
                    filepath += "omgash";
                    break;
            }

            filepath+=".png";
            faces[i]=new ImageIcon(filepath);
        }

        ImageIcon[] bitDisplay=new ImageIcon[10];
        for (int i = 0; i < 10; i++) {
            bitDisplay[i]=new ImageIcon("icons/bitDisplay/"+(i)+".png");
        }

        switch (name){
            case "numbers":{
                return numbers[index];
            }
            case "faces":{
                return faces[index];
            }
            case  "bitDisplay":{
                return bitDisplay[index] ;  
            }
            default:
                return null;
        }
    }

    public static void countFromBombs(int i, int j, JButton[][] buttons) {
        count(i-1, j-1,buttons);
        count(i-1, j,buttons);
        count(i-1, j+1,buttons);

        count(i, j-1,buttons);
        count(i, j+1,buttons);

        count(i+1, j-1,buttons);
        count(i+1, j,buttons);
        count(i+1, j+1,buttons);
    }

    private static void count(int i, int j, JButton[][] buttons) {

        if (i<0 || j<0||i>Field.columns-1 ||j>Field.rows-1){
            return;
        }
        String []temporary= buttons[i][j].getName().split(",",0);
        int numberStored= Integer.parseInt(temporary[1]);
        numberStored++;
        temporary[1]= String.valueOf(numberStored);
        String result=temporary[0]+","+temporary[1]+",";
        buttons[i][j].setName(result);
    }

    public static void printNumbers(JLabel[][] labels, JButton[][] buttons) {

        for (int i = 0; i < Field.columns; i++) {
            for (int j = 0; j < Field.rows; j++) {
                String[] temporary = buttons[i][j].getName().split(",", 0);
                int numberStored = Integer.parseInt(temporary[1]);
                //System.out.println(numberStored);
                if (temporary[0].equals("0")) {
                    if (numberStored > 0) {
                        labels[i][j].setIcon(Methods.iconsIO("numbers",numberStored-1));
                    }
                }
            }
        }
    }

    public static void floodFill(int i, int j, JButton[][] buttons) {

        if (i<0 || j<0||i>Field.columns-1 ||j>Field.rows-1){
            return;
        }

        String[] temporary = buttons[i][j].getName().split(",", 0);
        if(temporary[0].equals("0")){

            if(buttons[i][j].isVisible()){
                Field.totalCells--;
                Field.points+=20;
                buttons[i][j].setVisible(false);
                if (Integer.parseInt(temporary[1])>0){
                    return;
                }


                floodFill(i+1,j,buttons);
                floodFill(i-1,j,buttons);
                floodFill(i,j+1,buttons);
                floodFill(i,j-1,buttons);

                floodFill(i-1,j-1,buttons);
                floodFill(i-1,j+1,buttons);
                floodFill(i+1,j-1,buttons);
                floodFill(i+1,j+1,buttons);

            }
        }
    }

    public static void setText7Bit(JLabel[] counter,int number) {
        if (number<=999){
            int n100=number/100;
            int n10=number%100/10;
            int n1=number%100%10;
            counter[0].setIcon(Methods.iconsIO("bitDisplay",n100));
            counter[1].setIcon(Methods.iconsIO("bitDisplay",n10));
            counter[2].setIcon(Methods.iconsIO("bitDisplay",n1));
            counter[0].setName(String.valueOf(n100));
            counter[1].setName(String.valueOf(n10));
            counter[2].setName(String.valueOf(n1));
        }
    }
    public static int getText7Bit(JLabel[] counter) {
        int number;
        int n100= Integer.parseInt(counter[0].getName());
        int n10= Integer.parseInt(counter[1].getName());
        int n1= Integer.parseInt(counter[2].getName());

        number=n100*100+n10*10+n1;

        return number;
    }

    public static void counterMaster(JLabel[] counter, JFrame fieldFrame,
                                     int counterWidth, int counterHeight, int xCounterPos, int yCounterPos) {
        for (int i = 0; i < 3; i++) {
            counter[i]=new JLabel();
            counter[i].setBounds(xCounterPos, yCounterPos,counterWidth,counterHeight);
            fieldFrame.add(counter[i]);

            xCounterPos+=counterWidth;
        }
    }

    public static void overwriteFrame(JFrame wantedToBeReplacedFrame, JFrame newFrame){
        wantedToBeReplacedFrame.setVisible(false);
        wantedToBeReplacedFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        newFrame.setVisible(false);
        newFrame=wantedToBeReplacedFrame;
        newFrame.setVisible(true);
    }

    public static boolean containsNumbers(String text){
        boolean isNumber=true;

        for (int i = 0; i < text.length(); i++) {
            if (!(text.charAt(i) >= '0' && text.charAt(i) <= '9')) {
                isNumber=false;
                break;
            }
        }
        return isNumber;
    }
    public static int[] rulesForCustom(JTextField widthField, JTextField heightField, JTextField mineField){
        int widthNumber = 0;
        int heightNumber = 0;
        int mineNumber = 0;

        if ((!(widthField.getText().equals(""))) &&
                (!(heightField.getText().equals(""))) &&
                (!(mineField.getText().equals("")))) {

            String widthText = widthField.getText();
            String heightText = heightField.getText();
            String mineText = mineField.getText();

            boolean isWidthNumber = Methods.containsNumbers(widthText);
            boolean isHeightNumber = Methods.containsNumbers(heightText);
            boolean isMineNumber = Methods.containsNumbers(mineText);

            int wNumber=0;
            int hNumber=0;
            int mNumber=0;

            if (isWidthNumber) {
                wNumber = Integer.parseInt(widthText);
            }
            if (isHeightNumber) {
                hNumber = Integer.parseInt(heightText);
            }
            if (isMineNumber) {
                mNumber = Integer.parseInt(mineText);
            }

            if ((wNumber < (((Toolkit.getDefaultToolkit().getScreenSize().width-15) / 20))) &&
                    (hNumber < (((Toolkit.getDefaultToolkit().getScreenSize().height-40 )/ 20))) &&
                    (mNumber < (wNumber * hNumber * 2/10))){

                widthNumber=wNumber;
                heightNumber=hNumber;
                mineNumber=mNumber;
            }
        }
        int[] numbers =new int[3];
        numbers[0]=widthNumber;
        numbers[1]=heightNumber;
        numbers[2]=mineNumber;
        return numbers;
    }
}
