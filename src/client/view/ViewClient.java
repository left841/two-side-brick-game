package client.view;

import client.model.BModelClient;
import client.model.IModelClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ViewClient extends JFrame implements IObserver {
    IModelClient m;

    private JPanel contentPane;
    private JButton joinButton;
    private JPanel mainPane;
    private JPanel bottomPane;
    private javax.swing.JLabel[][] field;

    public ViewClient() {
        initComponents();
    }

    private void initComponents() {

        m = BModelClient.model();
        m.addObserver(this);

        int cellWidth = 16;
        int cellHeight = 16;
        int width = 30;
        int height = 30;
        ImageIcon blackCell = new ImageIcon(
                new ImageIcon("src/resources/blackCell.png").getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT));
        ImageIcon whiteCell = new ImageIcon(
                new ImageIcon("src/resources/whiteCell.png").getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT));
        mainPane.setSize(width * cellWidth, height * cellHeight);
        mainPane.setLayout(new java.awt.GridBagLayout());

        field = new javax.swing.JLabel[height][width];

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                GridBagConstraints c = new GridBagConstraints();
                c.gridx = i;
                c.gridy = j;
                field[i][j] = new javax.swing.JLabel();
                field[i][j].setIcon(blackCell);
                mainPane.add(field[i][j], c);
            }
        }

        setContentPane(contentPane);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onExit();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onExit();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        joinButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                m.join();
            }
        });
    }

    private void onExit() {
        dispose();
    }

    @Override
    public void refresh() {
        System.out.println(m.getRoomNumber());
        joinButton.setText(m.getRoomNumber() == -1 ? "Join" : "Connected");
    }
}
