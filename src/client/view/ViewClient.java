package client.view;

import client.model.BModelClient;
import client.model.IModelClient;

import javax.swing.*;
import java.awt.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class ViewClient extends JPanel implements IObserver {
    IModelClient m;

    private javax.swing.JLabel[][] field;

    ImageIcon blackCell = null;
    ImageIcon whiteCell = null;

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

        try {
            blackCell = new ImageIcon(
                    new ImageIcon(Paths.get(this.getClass().getResource("../../resources/blackCell.png").toURI()).toString())
                    .getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT));
            whiteCell = new ImageIcon(
                    new ImageIcon(Paths.get(this.getClass().getResource("../../resources/whiteCell.png").toURI()).toString())
                    .getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT));
        } catch (URISyntaxException e) {
            System.out.println("Could no find resource");
        }
        setSize(width * cellWidth, height * cellHeight);
        setMaximumSize(new Dimension(width * cellWidth, height * cellHeight));
        setMinimumSize(new Dimension(width * cellWidth, height * cellHeight));
        setLayout(new GridBagLayout());

        field = new javax.swing.JLabel[height][width];

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                GridBagConstraints c = new GridBagConstraints();
                c.gridx = i;
                c.gridy = j;
                field[i][j] = new javax.swing.JLabel();
                field[i][j].setIcon(blackCell);
                add(field[i][j], c);
            }
        }
    }

    @Override
    public void refresh() {
        System.out.println(m.getRoomNumber());
    }
}
