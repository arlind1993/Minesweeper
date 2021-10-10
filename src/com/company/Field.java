package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Field {
    static int columns;
    static int rows;
    static int mines;
    static int totalCells;
    static int points;
    public static void initialiseField(JFrame frame){
        totalCells=rows*columns;
        points=0;
        JFrame fieldFrame=new JFrame();

        int frameXOffsets=15;
        int frameYOffsets=40;

        int initialPosX=0;
        int initialPosY=40;
        int xPos=initialPosX;
        int yPos=initialPosY;
        int buttonWidth=20;
        int buttonHeight=20;

        int barFaceHeight=40;



        fieldFrame.setSize(rows*buttonWidth+frameXOffsets, columns*buttonHeight+barFaceHeight+frameYOffsets);
        Methods.frameMaster(fieldFrame);



        JLabel top =new JLabel();
        top.setBounds(0,0,rows*buttonWidth, barFaceHeight);
        top.setBackground(Color.DARK_GRAY);
        top.setBorder(BorderFactory.createRaisedBevelBorder());
        fieldFrame.add(top);

        int counterWidth=14;
        int counterHeight=24;
        int xCounterOffset=10;
        int xCounterPos=xCounterOffset;
        int yCounterPos=top.getHeight()/2-counterHeight/2;


        JLabel[] mineCounter=new JLabel[3];
        Methods.counterMaster(mineCounter,fieldFrame, counterWidth, counterHeight, xCounterPos, yCounterPos);
        Methods.setText7Bit(mineCounter,mines);

        xCounterPos=top.getWidth()-3*counterWidth-xCounterOffset;


        JLabel[] timerCounter=new JLabel[3];

        Methods.counterMaster(timerCounter,fieldFrame, counterWidth, counterHeight, xCounterPos, yCounterPos);
        Methods.setText7Bit(timerCounter,0);


        TimerClass myTimer=new TimerClass(timerCounter);
        myTimer.start();

        JButton face=new JButton();
        face.setIcon(Methods.iconsIO("faces",0));
        face.setSize(26,26);
        face.setLocation(top.getWidth()/2-face.getWidth()/2,top.getHeight()/2-face.getHeight()/2);
        face.setBorder(BorderFactory.createRaisedBevelBorder());
        face.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
                face.setIcon(Methods.iconsIO("faces",3));
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                face.setIcon(Methods.iconsIO("faces",0));
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        fieldFrame.add(face);

        ButtonListener.face=face;

        JButton [][]button=new JButton[columns][rows];
        JLabel [][]labels=new JLabel[columns][rows];

        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                button[i][j] = new JButton();
                button[i][j].setBackground(Color.LIGHT_GRAY);
                button[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
                button[i][j].setName("0,0,");

                button[i][j].setBounds(xPos, yPos, buttonWidth, buttonHeight);

                //
                fieldFrame.add(button[i][j]);

                labels[i][j]=new JLabel(Methods.iconsIO("empty"));
                labels[i][j].setBounds(xPos, yPos, buttonWidth, buttonHeight);
                fieldFrame.add(labels[i][j]);

                xPos+=buttonWidth;
            }
            yPos+=buttonHeight;
            xPos=initialPosX;
        }

        int occupiedSpots=0;
        JLabel []minesI=new JLabel[mines];
        while (occupiedSpots<mines){
            int i= (int) (Math.random()*columns);
            int j= (int) (Math.random()*rows);

            String []temporary=button[i][j].getName().split(",",0);

            if (temporary[0].equals("0")){
                System.out.println("Mine placed: pos(x"+(j+1)+";y"+(i+1)+")");
                temporary[0]="1";
                button[i][j].setName(temporary[0]+","+temporary[1]+",");
                labels[i][j].setVisible(false);
                minesI[occupiedSpots]=new JLabel(Methods.iconsIO("mine"));
                minesI[occupiedSpots].setName(i+","+j);
                minesI[occupiedSpots].setBounds(button[i][j].getBounds());
                fieldFrame.add(minesI[occupiedSpots]);

                Methods.countFromBombs(i,j,button);

                occupiedSpots++;
            }
        }
        Methods.printNumbers(labels,button);

        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                button[i][j].setName(button[i][j].getName()+i+","+j+",0");
                button[i][j].addMouseListener(new ButtonListener(button[i][j],button,mineCounter,labels,myTimer,fieldFrame));

                //fieldFrame.add(button[i][j]);
            }
        }
        Methods.overwriteFrame(fieldFrame,frame);
    }


}
