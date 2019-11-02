package Main;
//Roman Gehtberg. 	ID# 324306935
//Yacov Mateo 	ID# 204238687
import java.io.IOException;

import javax.swing.JOptionPane;

public class Worker implements Runnable {

	public CompressDecompress job;
	public View view;
	public int chooseJob;
	public String[] In1;
	public String CompressName;
	public String DecompressName;
	public String[] Out1;

	public Worker(CompressDecompress job) {
		this.job = job;
		chooseJob = 0;
		In1 = new String[2];
		In1[0] = "";
		In1[1] = ""; 
		Out1 = new String[2];
		Out1[0] = ""; 
		Out1[1] = ""; 
	}

	@Override
	public void run() {
		long lTime;
		boolean isDone = false;
		while (true) {
			try {
				Thread.sleep(200);
				switch (chooseJob) {
				case 0: {
					break;
				}
				case 1: {
					/* Compression */
					lTime = System.nanoTime();
					System.out.println("Compessing " + In1[0] + " to a " + Out1[0]);
					try {
						job.Compress(In1[0], Out1[0]);
					} catch (IOException e) {
						e.printStackTrace();
						String out = e.toString();
						JOptionPane.showMessageDialog(null, out);	
					}
					System.out.println("Compession done, Time: " + ((System.nanoTime() - lTime) / 10e8) + " seconds");
					chooseJob = 0; // job is done
					break;
				}
				case 2: {
					/* Decompression */
					lTime = System.nanoTime();
					System.out.println("Decompessing " + In1[1] + " to a " + Out1[1]);
					try {
						isDone = job.Decompress(In1[1], Out1[1]);
						if (isDone==false) 
							JOptionPane.showMessageDialog(null, "File "+ In1[1] + "\n is not LZW Compressed");
					} catch (IOException e) {
						e.printStackTrace();
						String out = e.toString();
						JOptionPane.showMessageDialog(null, out);	
					}
					System.out.println("Decompession done, Time: " + ((System.nanoTime() - lTime) / 10e8 + " seconds"));
					chooseJob = 0; // job is done
					break;
				}
				default:
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				String out = e.toString();
				JOptionPane.showMessageDialog(null, out);	
			} // imitate car taking time to reach
		}
	}
}
