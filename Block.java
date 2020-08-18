
public class Block implements Bomb {
	private boolean bomb;
	private boolean mined = false;
	private boolean flag = false;
	private int num;
	
	public Block(boolean bomb) {
		this.bomb = bomb;
	}
	
	public boolean isFlag() {
		return flag;
	}
	
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public boolean isMined() {
		return mined;
	}
	
	public void setMined(boolean mined) {
		this.mined = mined;
	}
	
	@Override
	public boolean isBomb() {
		return bomb;
	}

	@Override
	public void setBomb(boolean bomb) {
		this.bomb = bomb;
	}
	
	@Override
	public int getNum() {
		return num;
	}
	
	@Override
	public void setNum(int num) {
		this.num = num;
	}
}
