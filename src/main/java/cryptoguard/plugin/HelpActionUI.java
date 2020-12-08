package cryptoguard.plugin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HelpActionUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel Title;
    private JTextArea textArea1;
    private JPanel OKButtonArea;
    private JLabel View;
    private JList list1;

    /* What tools are available
     What the tool checks, give info on crypto guard, what it looks for (cryptographic vulnerabilities)

     Quick guide on view summary screen and how to interpret it

     Runs against current active project*/
    public HelpActionUI() {
       /* textArea1.setEditable(false);
        textArea1.setCursor(null);
        textArea1.setOpaque(false);
        textArea1.setFocusable(false);
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);*/
        //textArea1.setCursor(null);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        /*textArea1.setText("The CryptoGuard tool discovers vulnerabilities and cryptographic" +
                        " misuse in Java and Android programs. The tools available are a run and view " +
                " mechanism. CryptoGuard is able to " );*/

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });


        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onOK();
            }
        });

    }

    private void onOK() {
        // add your code here
        dispose();
    }

    public static void main(String[] args) {
        HelpActionUI dialog = new HelpActionUI();
        dialog.setTitle("Help for CryptoGuard Plugin");
        dialog.setSize(400, 600);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - dialog.getWidth())/2;
        int y = (screenSize.height - dialog.getHeight())/2;
        dialog.setLocation(x, y);
        dialog.pack();
        dialog.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
