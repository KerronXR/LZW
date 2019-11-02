package Main;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

public class View {
	public JFrame frame;
	public static final long serialVersionUID = 1L;
	public JFileChooser fileChooser1;
	public JFileChooser fileChooser2;
	public JFileChooser fileChooser3;
	public JFileChooser fileChooser4;
	public JProgressBar progressBar;
	public JButton openButton1;
	public JButton openButton2;
	public JButton openButton3;
	public JButton openButton4;
	public JButton openButton5;
	public JButton openButton6;
	public JLabel label1;
	public JLabel label2;
	public JLabel label4;
	public JLabel label5;
	public JLabel label3;
	public int mode;
	public String textField1;
	public String textField2;
	public String textField3;
	public static final int MODE_OPEN = 1;
	public static final int MODE_SAVE = 2;
	public int BarMinimum = 0;
	public int BarMaximum = 100;
	public String compOpenPass;
	public String compSavePass;
	public String decompOpenPass;
	public String decompSavePass;

	public View(String title) {
		frame = new JFrame(title);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenX = screenSize.width;
		int screenY = screenSize.height;
		Container cp = frame.getContentPane();
		cp.setLayout(null);
		fileChooser1 = new JFileChooser();
		fileChooser1.setCurrentDirectory(new File(System.getProperty("user.dir")));
		fileChooser2 = new JFileChooser();
		fileChooser2.setCurrentDirectory(new File(System.getProperty("user.dir")));
		fileChooser3 = new JFileChooser();
		fileChooser3.setCurrentDirectory(new File(System.getProperty("user.dir")));
		fileChooser4 = new JFileChooser();
		fileChooser4.setCurrentDirectory(new File(System.getProperty("user.dir")));
		progressBar = new JProgressBar();
		progressBar.setBounds(20, 250, 600, 30);
		progressBar.setMinimum(BarMinimum);
		progressBar.setMaximum(BarMaximum);

		label1 = new JLabel("File not chosen");
		label2 = new JLabel("File not chosen");
		label4 = new JLabel("File not chosen");
		label5 = new JLabel("File not chosen");
		label3 = new JLabel("");

		openButton1 = new JButton("Choose a file to compress");
		openButton2 = new JButton("Choose a destination file");
		openButton3 = new JButton("Compress!");
		openButton4 = new JButton("Choose a file to decompress");
		openButton5 = new JButton("Choose a destination file");
		openButton6 = new JButton("Decompress!");

		openButton1.setBounds(20, 25, screenX / 8, 40);
		openButton1.setFont(new Font("Arial", Font.PLAIN, screenX / 110));
		label1.setBounds(30, 70, screenX / 8, 20);
		label1.setForeground(Color.red);

		openButton2.setBounds(screenX / 8 + 40, 25, screenX / 8, 40);
		openButton2.setFont(new Font("Arial", Font.PLAIN, screenX / 110));
		label2.setBounds(screenX / 8 + 50, 70, screenX / 8, 20);
		label2.setForeground(Color.red);

		openButton3.setBounds((screenX / 8) * 2 + 60, 20, screenX / 11, 50);
		openButton3.setFont(new Font("Arial", Font.PLAIN, screenX / 110));

		openButton4.setBounds(20, 120, screenX / 8, 40);
		openButton4.setFont(new Font("Arial", Font.PLAIN, screenX / 110));
		label4.setBounds(30, 160, screenX / 8, 20);
		label4.setForeground(Color.red);

		openButton5.setBounds(screenX / 8 + 40, 120, screenX / 8, 40);
		openButton5.setFont(new Font("Arial", Font.PLAIN, screenX / 110));
		label5.setBounds(screenX / 8 + 50, 160, screenX / 8, 20);
		label5.setForeground(Color.red);

		openButton6.setBounds((screenX / 8) * 2 + 60, 115, screenX / 11, 50);
		openButton6.setFont(new Font("Arial", Font.PLAIN, screenX / 110));

		label3.setBounds(640, 255, screenX / 7, 20);
		label3.setFont(new Font("Arial", Font.PLAIN, screenX / 120));
		label3.setForeground(Color.green);

		cp.add(openButton1);
		cp.add(openButton2);
		cp.add(openButton3);
		cp.add(openButton4);
		cp.add(openButton5);
		cp.add(openButton6);
		cp.add(label1);
		cp.add(label2);
		cp.add(label4);
		cp.add(label5);
		cp.add(label3);
		cp.add(progressBar);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 350);
		int windowX = Math.max(0, (screenX - 800) / 2);
		int windowY = Math.max(0, (screenY - 500) / 2);

		frame.setLocation(windowX, windowY);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public void updateBar(int newValue) {
		progressBar.setValue(newValue);
	}


}
