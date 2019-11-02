package Main;
//Roman Gehtberg. 	ID# 324306935
//Yacov Mateo 	ID# 204238687
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Controller {

	private View view;
	private Worker worker;

	public Controller(View v, Worker w) {
		view = v;
		worker = w;
	}

	public void initController() {
		view.openButton1.addActionListener(e -> chooseFileComp());
		view.openButton2.addActionListener(e -> saveFileComp());
		view.openButton3.addActionListener(e -> startCompress());
		view.openButton4.addActionListener(e -> chooseFileDeComp());
		view.openButton5.addActionListener(e -> saveFileDecomp());
		view.openButton6.addActionListener(e -> startDecompress());
	}

	public void chooseFileComp() {
		view.label3.setText("");
		String path;
		int result = view.fileChooser1.showOpenDialog(view.openButton1);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = view.fileChooser1.getSelectedFile();
			path = selectedFile.getAbsolutePath();
			worker.CompressName = selectedFile.getName();
			view.label1.setText(path);
			view.label1.setForeground(Color.black);
			worker.In1[0] = path;
		}
	}

	public void saveFileComp() {
		view.label3.setText("");
		String path;
		view.fileChooser2.setSelectedFile(new File(worker.CompressName + ".lzw"));
		view.fileChooser2.setFileFilter(new FileNameExtensionFilter("lzw file", "lzw"));
		int result = view.fileChooser2.showSaveDialog(view.openButton1);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = view.fileChooser2.getSelectedFile();
			path = selectedFile.getAbsolutePath();
			view.label2.setText(path);
			view.label2.setForeground(Color.black);
			worker.Out1[0] = path;
		}
	}

	public void startCompress() {
		if (worker.In1[0] != "" && worker.Out1[0] != "") {
			worker.chooseJob = 1;
		} else {
			JOptionPane.showMessageDialog(null, "Files not chosen");
		}
	}

	public void chooseFileDeComp() {
		view.label3.setText("");
		String path;
		view.fileChooser3.setFileFilter(new FileNameExtensionFilter("lzw file", "lzw"));
		int result = view.fileChooser3.showOpenDialog(view.openButton1);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = view.fileChooser3.getSelectedFile();
			path = selectedFile.getAbsolutePath();
			worker.DecompressName = selectedFile.getName().substring(0, selectedFile.getName().lastIndexOf('.'));
			view.label4.setText(path);
			view.label4.setForeground(Color.black);
			worker.In1[1] = path;
		}
	}

	public void saveFileDecomp() {
		view.label3.setText("");
		String path;
		view.fileChooser4.setSelectedFile(new File(worker.DecompressName));
		int result = view.fileChooser4.showSaveDialog(view.openButton1);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = view.fileChooser4.getSelectedFile();
			path = selectedFile.getAbsolutePath();
			view.label5.setText(path);
			view.label5.setForeground(Color.black);
			worker.Out1[1] = path;
		}
	}

	public void startDecompress() {
		if (worker.In1[1] != "" && worker.Out1[1] != "") {
			worker.chooseJob = 2;
		} else {
			JOptionPane.showMessageDialog(null, "Files not chosen");
		}
	}

}
