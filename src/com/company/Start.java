package com.company;

import javax.swing.*;

public class Start {
    public static void start(JFrame frame){
        JFrame startingFrame=new JFrame();
        startingFrame.setSize(615,400);
        Methods.frameMaster(startingFrame);

    JLabel beginner= new JLabel("Beginner (10 mines)");
        beginner.setBounds(30,30,140,20);
        startingFrame.add(beginner);

    JLabel intermediate= new JLabel("Intermediate (40 mines)");
        intermediate.setBounds(30,110,140,20);
        startingFrame.add(intermediate);

    JLabel expert= new JLabel("Expert (99 mines)");
        expert.setBounds(30,190,140,20);
        startingFrame.add(expert);

    JRadioButton fB8B8=new JRadioButton("8 X 8");
    JRadioButton fB9B9=new JRadioButton("9 X 9");
    JRadioButton fB10B10=new JRadioButton("10 X 10");
    JRadioButton fI13B15=new JRadioButton("13 X 15");
    JRadioButton fI16B16=new JRadioButton("16 X 16");
    JRadioButton fE30B16=new JRadioButton("30 X 16");

        fB8B8.setBounds(180,30,80,20);
        fB9B9.setBounds(260,30,80,20);
        fB10B10.setBounds(340,30,80,20);
        fI13B15.setBounds(180,110,80,20);
        fI16B16.setBounds(260,110,80,20);
        fE30B16.setBounds(180,190,80,20);

    ButtonGroup buttonGroup=new ButtonGroup();
        buttonGroup.add(fB8B8);
        buttonGroup.add(fB9B9);
        buttonGroup.add(fB10B10);
        buttonGroup.add(fI13B15);
        buttonGroup.add(fI16B16);
        buttonGroup.add(fE30B16);

        startingFrame.add(fB8B8);
        startingFrame.add(fB9B9);
        startingFrame.add(fB10B10);
        startingFrame.add(fI13B15);
        startingFrame.add(fI16B16);
        startingFrame.add(fE30B16);

    JLabel custom= new JLabel("Custom");
        custom.setBounds(30,270,140,20);
        startingFrame.add(custom);

    JTextField widthField =new JTextField();
    JTextField heightField =new JTextField();
    JTextField mineField=new JTextField();

        widthField.setBounds(180,270,80,20);
        heightField.setBounds(260,270,80,20);
        mineField.setBounds(340,270,80,20);

        startingFrame.add(widthField);
        startingFrame.add(heightField);
        startingFrame.add(mineField);

    JButton startButton=new JButton("Start Game");
        startButton.setBounds(30,320,520,20);
        startButton.addActionListener(e -> {
        int [] numbers=Methods.rulesForCustom(widthField,heightField,mineField);

        if (numbers[0]>0&&numbers[1]>0&&numbers[2]>0){
            Field.columns= Integer.parseInt(heightField.getText());
            Field.rows= Integer.parseInt(widthField.getText());
            Field.mines= Integer.parseInt(mineField.getText());
            Field.initialiseField(startingFrame);
        }
        else if(fB8B8.isSelected()){
            Field.columns=8;
            Field.rows=8;
            Field.mines=10;
            Field.initialiseField(startingFrame);
        }else if (fB9B9.isSelected()){
            Field.columns=9;
            Field.rows=9;
            Field.mines=10;
            Field.initialiseField(startingFrame);
        }else if (fB10B10.isSelected()){
            Field.columns=10;
            Field.rows=10;
            Field.mines=10;
            Field.initialiseField(startingFrame);
        }else if (fI13B15.isSelected()){
            Field.columns=15;
            Field.rows=13;
            Field.mines=40;
            Field.initialiseField(startingFrame);
        }else if (fI16B16.isSelected()){
            Field.columns=16;
            Field.rows=16;
            Field.mines=40;
            Field.initialiseField(startingFrame);
        }else if(fE30B16.isSelected()){
            Field.columns=16;
            Field.rows=30;
            Field.mines=99;
            Field.initialiseField(startingFrame);
        }
    });
        startingFrame.add(startButton);

        Methods.overwriteFrame(startingFrame,frame);
    }
}
