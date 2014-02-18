package to.dogemypho;

public class DogeText {
	private String text;
	private int x;
	private int y;
	private int width;
	private int height;
	
	public DogeText(String text, int x, int y, int width, int height) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public String getText() {
		return text;
	}
	
	public int getLeft() {
		return x;
	}
	
	public int getRight() {
		return x + width;
	}
	
	public int getTop() {
		return y;
	}
	
	public int getBottom() {
		return y + height;
	}
}
