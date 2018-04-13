package WattFarmSource;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Graphic {
	public static void main(String[] args) {
		JFrame jframe = new JFrame("Graphic Window");
        
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setPreferredSize(new Dimension(400, 300));
        jframe.pack();
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
	}
}
