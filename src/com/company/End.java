package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class End {
    public static void end(JFrame frame, String message, TimerClass myTimer, int points) {
        myTimer.stop();

        JFrame endingScreen=new JFrame("Minesweeper");
        endingScreen.setLayout(null);
        endingScreen.setSize(300,200);
        Methods.frameMaster(endingScreen);

        int xFrameOffset=15;
        int yFrameOffset=40;

        int width=200;
        int height=20;
        int posX=(endingScreen.getWidth()-xFrameOffset)/2-width/2;
        int posY=(endingScreen.getHeight()-yFrameOffset)/2-height/2-40;

        JLabel losingTitle=new JLabel(message, SwingConstants.CENTER);
        losingTitle.setBounds(posX,posY,width,height);
        endingScreen.add(losingTitle);

        int buttonWidth=120;
        int buttonHeight=20;
        int buttonPosXOffset=20;
        int buttonPosYOffset=10;
        int buttonPosX=buttonPosXOffset;
        int buttonPosY=posY+height+buttonPosYOffset;

        JButton []buttons=new JButton[2];
        for (int i = 0; i < 2; i++) {
            String text="";
            switch (i){
                case 0:
                    text="Try Again";
                    break;
                case 1:
                    text="Change Mode";
                    break;
            }
            buttons[i]=new JButton(text);
            buttons[i].setBounds(buttonPosX,buttonPosY,buttonWidth,buttonHeight);
            buttons[i].setBackground(Color.WHITE);
            endingScreen.add(buttons[i]);

            buttonPosX=endingScreen.getWidth()-xFrameOffset-buttonWidth-buttonPosX;
        }

        buttons[0].addActionListener(e -> {
            frame.setVisible(false);
            Field.initialiseField(endingScreen);
        });

        buttons[1].addActionListener(e -> {
            frame.setVisible(false);
            Start.start(endingScreen);
        });

        boolean haveWon=false;

        if (message.equals("You won!")){
            haveWon=true;
        }

        if (!haveWon){
            points/=2;
        }


        points -= myTimer.getSecondsPassed() / 10 * 10;
        if(points<0){
            points=0;
        }

        User.setPoints(points);

        posY=buttonPosY+buttonHeight;
        JLabel scoreLabel=new JLabel("You scored: "+points+" points!",SwingConstants.CENTER);
        scoreLabel.setBounds(posX,posY+buttonPosYOffset,width,height);
        endingScreen.add(scoreLabel);

        buttonPosY=posY+height+buttonPosYOffset;
        JButton []scoreButtons=new JButton[2];
        for (int i = 0; i < 2; i++) {
            String textName="";
            switch (i){
                case 0:
                    textName="Save";
                    break;
                case 1:
                    textName="High scores";
                    break;
            }
            scoreButtons[i]=new JButton(textName);
            scoreButtons[i].setBounds(buttonPosX,buttonPosY,buttonWidth,buttonHeight);
            scoreButtons[i].setBackground(Color.WHITE);
            endingScreen.add(scoreButtons[i]);

            buttonPosX=endingScreen.getWidth()-xFrameOffset-buttonWidth-buttonPosX;
        }

        scoreButtons[0].addActionListener(e -> User.save());
        scoreButtons[1].addActionListener(e -> User.showHighScores());

        endingScreen.setVisible(true);
        endingScreen.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }
}
