package Main;
// Roman Gehtberg. 	ID# 324306935
// Yacov Mateo 	ID# 204238687

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

