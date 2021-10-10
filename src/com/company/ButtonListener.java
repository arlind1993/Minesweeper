package com.company;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ButtonListener implements MouseListener {
//name 0-1: is it a mine,number that it contains, yPos,xPos, 0-nothing|1-flag|2-questionMark
    JButton actualButton;
    JButton[][] buttons;
    static JButton face;
    JLabel []mineCounter;
    JLabel [][]labels;
    TimerClass myTimer;
    static JFrame actualFrame;

    public ButtonListener(JButton actualButton, JButton[][] buttons, JLabel[] mineCounter,
                          JLabel[][] labels, TimerClass myTimer,JFrame actualFrame){
        this.buttons=buttons;
        this.actualButton=actualButton;
        this.mineCounter=mineCounter;
        this.labels=labels;
        this.myTimer=myTimer;
        ButtonListener.actualFrame=actualFrame;
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println(Field.points);
        int shownCounterMines= Methods.getText7Bit(mineCounter);
        String[] temporary=actualButton.getName().split(",",0);
        //.out.println(Arrays.toString(temporary));
        if (e.getButton()==1){
            if (temporary[4].equals("0")){
                //System.out.println("Clicked");
                if (temporary[1].equals("0")){
                    int actualPosI= Integer.parseInt(temporary[2]);
                    int actualPosJ= Integer.parseInt(temporary[3]);
                    Methods.floodFill(actualPosI,actualPosJ,buttons);
                }else {
                    Field.totalCells--;
                    Field.points+=20;
                }
                actualButton.setVisible(false);
                if (temporary[0].equals("1")) {
                    face.setIcon(Methods.iconsIO("faces", 2));
                    for (JButton[] bs : buttons) {
                        for (JButton b : bs) {
                            b.setVisible(false);
                        }
                    }
                    End.end(actualFrame,"You lost!",myTimer, Field.points);
                }
            }
        }else if (e.getButton()==3){
            //System.out.println("Right Clicked");
            switch (temporary[4]){

                case "0": {
                    if(shownCounterMines>0){
                        temporary[4] = "1";
                        actualButton.setIcon(Methods.iconsIO("flag"));
                        Methods.setText7Bit(mineCounter,--shownCounterMines);
                    }
                    break;
                }
                case "1":{
                    temporary[4]="2";
                    actualButton.setIcon(Methods.iconsIO("question"));
                    break;
                }
                case "2":{
                    temporary[4]="0";
                    actualButton.setIcon(null);
                    Methods.setText7Bit(mineCounter,++shownCounterMines);
                    break;
                }
            }
            String textName=temporary[0]+","+temporary[1]+","+temporary[2]+","+temporary[3]+","+temporary[4];
            //System.out.println(textName+"+++");
            actualButton.setName(textName);
        }
        if (Field.totalCells==Field.mines){
            face.setIcon(Methods.iconsIO("faces",1));
            End.end(actualFrame, "You won!", myTimer,Field.points);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
