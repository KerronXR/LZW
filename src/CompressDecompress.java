package Main;
//Roman Gehtberg. 	ID# 324306935
//Yacov Mateo 	ID# 204238687
import java.awt.Color;
import java.io.*;
import java.util.*;

public class CompressDecompress {

	public View view;

	public CompressDecompress(View view) {
		this.view = view;
	}

	public void Compress(String in1, String out1) throws IOException {
		view.updateBar(0);
		view.label3.setText("Compressing...");
		view.label3.setForeground(Color.black);
		FileInputStream fInput = new FileInputStream(in1);
		FileOutputStream fOutput = new FileOutputStream(out1);
		long fileSize = new File(in1).length();
		byte[] allBytes = new byte[(int) fileSize];
		char[] uncompressed = new char[(int) fileSize];
		fInput.read(allBytes);
		for (int i = 0; i < fileSize; i++) {
			uncompressed[i] = (char) (allBytes[i] & 0xFF);
		}

		// Build the dictionary.
		int dSize = 256;
		long x = uncompressed.length, y = 0;
		long percent; 
		List<Integer> encoded = new ArrayList<Integer>();
		Map<String, Integer> dict = new HashMap<String, Integer>();
		for (int i = 0; i < 256; i++)
			dict.put("" + (char) i, i);
		String s = "";
		for (char l : uncompressed) {
			y++; // out ProgressBar
			percent = (y * 50) / x; // out ProgressBar max halfway job (y*50%)
			view.updateBar((int) percent); 

			String sc = s + l;
			if (dict.containsKey(sc))
				s = sc;
			else {
				encoded.add(dict.get(s));
				dict.put(sc, dSize++);
				s = "" + l;
			}
		}
		// Output the code for w.
		if (s != "")
			encoded.add(dict.get(s));

		x = encoded.size(); // out ProgressBar
		fOutput.write("LZW".getBytes());
		StringBuffer toWrite = new StringBuffer();
		int i;
		for (i = 0; i < encoded.size(); i++) {
			percent = 50 + ((i * 50) / x); // out ProgressBar second half 50%-100%
			view.updateBar((int) percent); // out ProgressBar
			if (encoded.get(i) != null) {
				if ((encoded.get(i) > 255) && (encoded.get(i) <= 65535)) {
					toWrite.append("1");
					toWrite.append(String.format("%8s", Integer.toBinaryString(encoded.get(i) >>> 8)).replace(' ', '0'));
					toWrite.append("0");
					toWrite.append(
							String.format("%8s", Integer.toBinaryString(encoded.get(i) & 0xFF)).replace(' ', '0'));
				} else if (encoded.get(i) > 65535) {
					toWrite.append("1");
					toWrite.append(
							String.format("%8s", Integer.toBinaryString(encoded.get(i) >>> 16)).replace(' ', '0'));
					toWrite.append("1");
					toWrite.append(String.format("%8s", Integer.toBinaryString((encoded.get(i) >>> 8) & 0xFF))
							.replace(' ', '0'));
					toWrite.append("0");
					toWrite.append(
							String.format("%8s", Integer.toBinaryString(encoded.get(i) & 0xFF)).replace(' ', '0'));
				} else {
					toWrite.append("0");
					toWrite.append(
							String.format("%8s", Integer.toBinaryString(encoded.get(i) & 0xFF)).replace(' ', '0'));
				}
			} else {
				toWrite.append("000000000");
			}
		}

		// fill the space with '0' to make the string length dividable by 8(byte size)
		int iLeftovers = toWrite.length() % 8;
		for (i = 0; i < 8 - iLeftovers; i++) {
			toWrite.append('0');
		}

		// add number of zeroes added to the end of file
		toWrite.append(String.format("%8s", Integer.toBinaryString((8 - iLeftovers) & 0xFF)).replace(' ', '0'));

		// write to file
		int h, b = 0;
		byte[] toFile = new byte[toWrite.length() / 8];
		for (h = 7; h < toWrite.length(); h = h + 8) {
			toFile[b] = (byte) (Integer.parseInt(toWrite.substring(h - 7, h + 1), 2));
			b++;
		}
		fOutput.write(toFile);
		fInput.close();
		fOutput.close();
		view.updateBar(100);
		view.label3.setText("Done :)");
		view.label3.setForeground(Color.green);
	}

	public boolean Decompress(String in1, String out1) throws IOException {
		view.updateBar(0);
		view.label3.setText("Decompressing...");
		view.label3.setForeground(Color.black);
		@SuppressWarnings("resource")
		FileInputStream fInput = new FileInputStream(in1);
		@SuppressWarnings("resource")
		FileOutputStream fOutput = new FileOutputStream(out1);
		long fileSize = new File(in1).length();
		byte[] allBytes = new byte[(int) fileSize];
		List<Integer> compressed = new ArrayList<Integer>();
		fInput.read(allBytes);

		if ((char) allBytes[0] != 'L' || (char) allBytes[1] != 'Z' || (char) allBytes[2] != 'W') {
			fInput.close();
			fOutput.close();
			view.updateBar(0);
			view.label3.setText("Error!");
			view.label3.setForeground(Color.red);
			return false;
		}

		// Transfer all bytes to a String sequences of bits
		long x = allBytes.length-3; // out ProgressBar
		long percent; // out ProgressBar
		StringBuffer codeString = new StringBuffer();
		int i, j;
		for (j = 3; j < allBytes.length; j++) {
			percent = ((j-3) * 40) / x; // out ProgressBar max 40% job (j*40%)
			view.updateBar((int) percent); // out ProgressBar
			codeString.append(String.format("%8s", Integer.toBinaryString(allBytes[j] & 0xFF)).replace(' ', '0'));
		}

		// Delete 'filling' zeroes from the end
		// allBytes[allBytes.length-1] has a 'filling' zeroes number
		for (i = 1; i <= allBytes[allBytes.length - 1] + 8; i++) {
			codeString.deleteCharAt(codeString.length() - 1);
		}
		
		// Recover the Integers from bit stream
		x = codeString.length(); // out ProgressBar
		StringBuffer s = new StringBuffer();
		int g=0;
		for (i = 0; i < codeString.length();) {
			g++;
			percent = 40 + ((g * 20) / x); // out ProgressBar second half 40-60%
			view.updateBar((int) percent); // out ProgressBar
			if (codeString.charAt(i) == '1') {
				if (codeString.charAt(i + 9) == '1') {
					s.append(codeString.substring(i + 1, i + 9));
					s.append(codeString.substring(i + 10, i + 18));
					s.append(codeString.substring(i + 19, i + 27));
					compressed.add(Integer.parseInt(s.substring(0, 24), 2));
					s.delete(0, 24);
					i = i + 27;
				} else {
					s.append(codeString.substring(i + 1, i + 9));
					s.append(codeString.substring(i + 10, i + 18));
					compressed.add(Integer.parseInt(s.substring(0, 16), 2));
					s.delete(0, 16);
					i = i + 18;
				}
			} else {
				compressed.add(Integer.parseInt(codeString.substring(i + 1, i + 9), 2));
				i = i + 9;
			}
		}
		
		// Build the dictionary.
		int dSize = 256, y=0;
		String f = "" + (char) (int) compressed.remove(0);
		StringBuilder decoded = new StringBuilder(f);
		x = compressed.size(); // out ProgressBar
		Map<Integer, String> dict = new HashMap<Integer, String>();
		for (i = 0; i < 256; i++)
			dict.put(i, "" + (char) i);
		for (int l : compressed) {
			y++;
			percent = 60 + ((y * 40) / x); // out ProgressBar third half 60-100%
			view.updateBar((int) percent); // out ProgressBar
			String entry;
			if (dict.containsKey(l))
				entry = dict.get(l);
			else if (l == dSize)
				entry = f + f.charAt(0);
			else
				throw new IllegalArgumentException("Bad Compression");
			decoded.append(entry);
			// Add w+entry[0] to the dictionary.
			dict.put(dSize++, f + entry.charAt(0));
			f = entry;
		}
		
		// Output
		byte[] toWrite = new byte[decoded.length()];
		for (i = 0; i < decoded.length(); i++) {
			toWrite[i] = (byte) decoded.charAt(i);
		}
		fOutput.write(toWrite);
		fInput.close();
		fOutput.close();
		view.updateBar(100);
		view.label3.setText("Done :)");
		view.label3.setForeground(Color.green);
		return true;
	}

}
