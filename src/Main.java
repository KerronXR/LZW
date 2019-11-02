package Main;
// Roman Gehtberg.

public class Main {
	public static void main(String[] args){
		View v = new View("LZW");
		CompressDecompress lzw = new CompressDecompress(v);
		Worker w = new Worker(lzw);
		Controller c = new Controller(v ,w);
		c.initController();
		Thread c1 = new Thread(w);
		c1.start();
	}

}

