package com.game.minesweeper;

import com.game.minesweeper.gui.JNumberField;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author godghdai
 * #Description ConfigDialog
 * #Date: 2020/9/28 02:59
 */
public class ConfigDialog extends JDialog{
    private JPanel mainPanel;
    private GridBagLayout gridBagLayout;
    private GridBagConstraints constraints;
    private int rowIndex=0;
    private GameControl control = null;
    public ConfigDialog(Component parent){
        setTitle("设置");
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        setSize(300,170);
        setLocationRelativeTo(parent);
        setResizable(false);
        setLayout(new BorderLayout(5,0));

        gridBagLayout=new GridBagLayout();
        constraints=new GridBagConstraints();
        constraints.fill= GridBagConstraints.BOTH;
        constraints.gridheight=1;
        constraints.insets=new Insets(0,10,4,10);

        mainPanel=new JPanel();
        mainPanel.setLayout(gridBagLayout);

        createRowControl("行数",10);
        createRowControl("列数",20);
        createRowControl("雷数",20);
        add(mainPanel,"Center");

        createButtonsPanel();
    }

    private void createButtonsPanel(){

        JPanel buttonPanel=new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton btnYes=new JButton("确定");
        btnYes.addActionListener(e -> {
            Map<String,Integer> nameValueMap=new HashMap<>();
            for (Component component : mainPanel.getComponents()) {
                if(component instanceof JNumberField){
                    JNumberField numberField=(JNumberField) component;
                    nameValueMap.put(numberField.getLabelName(),numberField.getValue());
                }
            }
            if(control!=null){
                if(control.onModifySetting(nameValueMap.get("行数"),nameValueMap.get("列数"),nameValueMap.get("雷数"))){
                    dispose();
                }
            }

        });
        JButton btnCancel=new JButton("取消");
        btnCancel.addActionListener(e -> dispose());
        buttonPanel.add(btnYes);
        buttonPanel.add(btnCancel);
        add(buttonPanel,"South");
    }

    private void createRowControl(String labelName,int defaultValue){
        JLabel jLabel=new JLabel(labelName+":");
        JNumberField textField=new JNumberField(labelName);
        textField.setText(Integer.toString(defaultValue));
        constraints.gridx=0;
        constraints.gridy=rowIndex;
        constraints.gridwidth=1;
        constraints.weightx=0;
        gridBagLayout.setConstraints(jLabel, constraints);
        constraints.gridx=1;
        constraints.gridy=rowIndex;
        constraints.gridwidth=2;
        constraints.weightx=1;
        gridBagLayout.setConstraints(textField, constraints);
        mainPanel.add(jLabel);
        mainPanel.add(textField);
        rowIndex++;
    }

    public void setControl(GameControl control) {
        this.control = control;
    }

    public void display(){
        setVisible(true);
    }
}
