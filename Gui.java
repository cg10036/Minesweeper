
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JOptionPane;

public class Gui{
	private int x, y;
	
	public Gui(int x, int y, int num) {
		this.x = x;
		this.y = y;
		JFrame fr = new JFrame("MineSweeper");
		JPanel pn = new JPanel();
		JButton[] bt = new JButton[x * y];
		
		Engine engine = new Engine(x, y, num);
		
		for(int i = 0;i < bt.length;i++) {
			bt[i] = new JButton();
			bt[i].setName("" + i);
			bt[i].setPreferredSize(new Dimension(30, 30));
			bt[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					int buttonId = Integer.parseInt(((JButton)e.getSource()).getName());
					System.out.println(buttonId);
					switch(e.getButton()) {
					case MouseEvent.BUTTON1:
						// left
						if(!bt[buttonId].isEnabled()) {
							break;
						}
						Block[][] block = engine.getBlocks();
						if(engine.mine(buttonId % x, buttonId / x)) {
							System.out.println("FAILED!!!");
							msgbox("FAILED!!!");
							for(int i = 0;i < y;i++) {
								for(int j = 0;j < x;j++) {
									bt[i * x + j].setEnabled(false);
									if(!block[i][j].isBomb()) {
										engine.mine(j, i);
										bt[i * x + j].setText("" + block[i][j].getNum());
										bt[i * x + j].setEnabled(false);
									} else {
										bt[i * x + j].setText("⬤");
										bt[i * x + j].setEnabled(false);
									}
								}
							}
							pn.validate();
							pn.repaint();
						}
						if(engine.isAllMined()) {
							System.out.println("SUCCESS!!!");
							msgbox("SUCCESS!!!");
							for(int i = 0;i < y;i++) {
								for(int j = 0;j < x;j++) {
									if(engine.isMined(j, i)) {
										bt[i * x + j].setText("" + block[i][j].getNum());
										bt[i * x + j].setEnabled(false);
									} else {
										bt[i * x + j].setText("◯");
										bt[i * x + j].setEnabled(false);
									}
								}
							}
							pn.validate();
							pn.repaint();
						}
						System.out.println((buttonId / x) + "," + (buttonId % x));
						for(int i = 0;i < y;i++) {
							for(int j = 0;j < x;j++) {
								if(engine.isMined(j, i)) {
									bt[i * x + j].setText("" + block[i][j].getNum());
									bt[i * x + j].setEnabled(false);
								}
							}
						}
						pn.validate();
						pn.repaint();
						engine.__DEBUG__PRINT();
						break;
					case MouseEvent.BUTTON3:
						// right
						break;
					default:
						break;
					}
				}
			});
			pn.add(bt[i]);
		}
		//bt[0].setEnabled(false);
		fr.setContentPane(pn);
		fr.setSize(35 * x, 35 * y + 25);
		fr.setVisible(true);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void msgbox(String str) {
		JOptionPane.showMessageDialog(null, str);
	}
}
