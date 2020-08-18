
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JOptionPane;

public class Gui{
	private int x, y;
	
	private Engine engine;
	private JButton[] bt;
	private JPanel pn;
	private JFrame fr;
	
	private void paint() {
		for(int i = 0;i < y;i++) {
			for(int j = 0;j < x;j++) {
				if(engine.isFlag(j, i)) {
					bt[i * x + j].setText("▲");
				} else if(engine.isMined(j, i)) {
					bt[i * x + j].setText("" + engine.getNum(j, i));
					bt[i * x + j].setEnabled(false);
				} else {
					bt[i * x + j].setText("");
				}
			}
		}
		pn.validate();
		pn.repaint();
	}
	
	private void mine(int buttonId) {
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
					} else {
						bt[i * x + j].setText("⬤");
					}
				}
			}
			pn.validate();
			pn.repaint();
		} else if(engine.isAllMined()) {
			System.out.println("SUCCESS!!!");
			msgbox("SUCCESS!!!");
			for(int i = 0;i < y;i++) {
				for(int j = 0;j < x;j++) {
					bt[i * x + j].setEnabled(false);
					if(engine.isMined(j, i)) {
						bt[i * x + j].setText("" + block[i][j].getNum());
					} else {
						bt[i * x + j].setText("◯");
					}
				}
			}
			pn.validate();
			pn.repaint();
		} else {
			paint();
		}
	}
	
	public Gui(int x, int y, int num) {
		this.x = x;
		this.y = y;
		fr = new JFrame("MineSweeper");
		pn = new JPanel();
		bt = new JButton[x * y];
		
		engine = new Engine(x, y, num);
		
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
					case MouseEvent.BUTTON2:
						// middle
						if(!engine.isMined(buttonId % x, buttonId / x)) {
							return;
						}
						int[] arr1 = {-1, 0, 1, -1, 0, 1, -1, 0, 1}; // y
						int[] arr2 = {-1, -1, -1, 0, 0, 0, 1, 1, 1}; // x
						int cnt = 0;
						for(int i = 0;i < 9;i++) {
							try {
								if(engine.isFlag(buttonId % x + arr2[i],  buttonId / x + arr1[i])) {
									cnt++;
								}
							} catch(Exception ex) {

							}
						}
						System.out.println(cnt);
						if(cnt == engine.getNum(buttonId % x, buttonId / x)) {
							for(int i = 0;i < 9;i++) {
								try {
									if(!engine.isMined(buttonId % x + arr2[i], buttonId / x + arr1[i]) && !engine.isFlag(buttonId % x + arr2[i],  buttonId / x + arr1[i])) {
										mine(buttonId % x + arr2[i] + (buttonId / x + arr1[i]) * x);
									}
								} catch(Exception ex) {
									
								}
							}
						}
						break;
					case MouseEvent.BUTTON3:
						// right
						if(engine.isFlag(buttonId % x, buttonId / x)) {
							engine.setFlag(buttonId % x, buttonId / x, false);
						} else if(!engine.isMined(buttonId % x, buttonId / x)) {
							engine.setFlag(buttonId % x, buttonId / x, true);
						}
						paint();
						break;
					case MouseEvent.BUTTON1:
						// left
						if(!bt[buttonId].isEnabled()) {
							break;
						}
						mine(buttonId);
						System.out.println((buttonId / x) + "," + (buttonId % x));
						engine.__DEBUG__PRINT();
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
