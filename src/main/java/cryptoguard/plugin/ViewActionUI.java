package cryptoguard.plugin;

import frontEnd.MessagingSystem.routing.structure.Default.Issue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;

public class ViewActionUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextPane projectName;
    private JTextPane numErrors;
    private JComboBox typeList;
    private JComboBox idList;
    private JTextArea textArea1;
    private JTextArea textField1;
    private List<Issue> errorList;


    public ViewActionUI(String name, List<Issue> eList) {
        errorList = eList;
        projectName.setText(name);
        this.numErrors.setText(String.valueOf(eList.size()));
        HashMap<Integer, Integer> typeMap = new HashMap<Integer, Integer>();
        for (Issue i : eList) {
            if (!typeMap.containsKey(i.getRuleNumber())) {
                typeMap.put(i.getRuleNumber(), i.getRuleNumber());
                typeList.addItem(i.getRuleNumber());
            }
        }
        for (Issue i : eList) {
            if (i.getRuleNumber() == (int)typeList.getSelectedItem()) {
                idList.addItem(i.getId());
            }
        }
        for (Issue i : eList) {
            if (i.getId() == (String)idList.getSelectedItem()) {
                textArea1.setText(i.getDescription());
                textField1.setText(i.getSeverity());
            }
        }
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        typeList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idList.removeAllItems();
                for (Issue i : errorList) {
                    if (i.getRuleNumber() == (int)typeList.getSelectedItem()) {
                        idList.addItem(i.getId());
                    }
                }
                for (Issue i : errorList) {
                    if (i.getId() == (String)idList.getSelectedItem()) {
                        textArea1.setText(i.getDescription());
                        textField1.setText(i.getSeverity());
                    }
                }
                rePack();
            }
        });

        idList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Issue i : errorList) {
                    if (i.getId() == (String)idList.getSelectedItem()) {
                        textArea1.setText(i.getDescription());
                        textField1.setText(i.getSeverity());
                    }
                }
                rePack();
            }
        });

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onOK();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void rePack() {
        this.pack();
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    public static void main(String name, List<Issue> errorList) {
        ViewActionUI dialog = new ViewActionUI(name, errorList);
        dialog.setTitle("Summary of Crypto Errors");
        dialog.setSize(400, 400);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - dialog.getWidth())/2;
        int y = (screenSize.height - dialog.getHeight())/2;
        dialog.setLocation(x, y);
        dialog.pack();
        dialog.setVisible(true);
        //System.exit(0);
    }
}
