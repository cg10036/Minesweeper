
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Engine {
	Block[][] block;
	Pair size;
	int bomb_num;
	int mined_cnt = 0;
	boolean isFirst = true;
	
	public Engine(int x, int y, int num) {
		renew(x, y, num);
	}
	
	private void renew(int x, int y, int num) {
		block = new Block[y][x];
		size = new Pair(x, y);
		bomb_num = num;
		boolean[] arr = new boolean[x * y];
		for(int i = 0;i < arr.length;i++) {
			if(i < num) {
				arr[i] = true;
			} else {
				arr[i] = false;
			}
		}
		shuffleArray(arr);
		
		for(int i = 0;i < arr.length;i++) {
			block[i / x][i % x] = new Block(arr[i]);
		}
	}
	
	public boolean isAllMined() {
		if(size.first() * size.second() - mined_cnt == bomb_num) {
			return true;
		}
		return false;
	}
	
	public boolean isMineable(int x, int y) {
		try {
			block[y][x].isBomb();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean mine(int _x, int _y) {
		if(block[_y][_x].isBomb()) {
			if(isFirst) {
				renew(size.first(), size.second(), bomb_num);
				return mine(_x, _y);
			} else {
				return true;
			}
		}
		isFirst = false;
		Queue q = new LinkedList();
		boolean[] visit = new boolean[size.first() * size.second()];
		block[_y][_x].setMined(true);
		q.offer(new Pair(_x, _y));
		
		while(!q.isEmpty()) {
			int num = 0;
			Pair pair = (Pair)q.poll();
			int x = pair.first(), y = pair.second();
			
			try {
				block[y][x].isBomb();
			} catch (Exception e) {
				continue;
			}
			
			if(visit[y * size.first() + x]) {
				continue;
			}
			visit[y * size.first() + x] = true;
			mined_cnt++;
			
			int[] arr1 = {-1, 0, 1, -1, 0, 1, -1, 0, 1}; // y
			int[] arr2 = {-1, -1, -1, 0, 0, 0, 1, 1, 1}; // x
			
			block[y][x].setMined(true);
			
			for(int i = 0;i < 9;i++) {
				try {
					if(block[y + arr1[i]][x + arr2[i]].isBomb()) {
						num++;
					}
				} catch (Exception e) {
					
				}
			}
			block[y][x].setNum(num);
			
			if(num == 0) {
				for(int i = 0;i < 9;i++) {
					try {
						block[y + arr1[i]][x + arr2[i]].isBomb();
						q.offer(new Pair(x + arr2[i], y + arr1[i]));
					} catch (Exception e) {
						
					}
				}
			} else {
				for(int i = 0;i < 9;i++) {
					if(arr1[i] != 0 && arr2[i] != 0) {
						continue;
					}
					boolean canMine = true;
					for(int j = 0;j < 9 && canMine;j++) {
						try {
							if(block[y + arr1[i] + arr1[j]][x + arr2[i] + arr2[j]].isBomb()) {
								canMine = false;
							}
						} catch (Exception e) {
							
						}
					}
					if(canMine) {
						q.offer(new Pair(x + arr2[i], y + arr1[i]));
					}
				}
			}
		}
		
//		while(!q.isEmpty()) {
//			int num = 0;
//			Pair pair = (Pair)q.poll();
//			int x = pair.first(), y = pair.second();
//			if(visit[y * size.first() + x]) {
//				continue;
//			}
//			visit[y * size.first() + x] = true;
//			
//			int[] arr1 = {-1, 0, 1, -1, 0, 1, -1, 0, 1}; // y
//			int[] arr2 = {-1, -1, -1, 0, 0, 0, 1, 1, 1}; // x
//			
//			for(int i = 0;i < 9;i++) {
//				try {
//					if(block[y + arr1[i]][x + arr2[i]].isBomb()) {
//						num++;
//					}
//				} catch (Exception e) {
//					
//				}
//			}
//			block[y][x].setNum(num);
//			block[y][x].setMined(true);
//			for(int i = 0;i < 9;i++) {
////				try {
////					if(!block[y + arr1[i]][x + arr2[i]].isBomb()) {
////						q.offer(new Pair(x + arr2[i], y + arr1[i]));
////					}
////				} catch (Exception e) {
////					
////				}
//				boolean bool = false;
//				for(int j = 0;j < 9 && !bool;j++) {
//					try {
//						if(block[y + arr1[i] + arr1[j]][x + arr2[i] + arr2[j]].isBomb()) {
//							bool = true;
//						}
//					} catch (Exception e) {
//						
//					}
//				}
//				if(!bool) {
//					try {
//						block[y + arr1[i]][x + arr2[i]].setMined(true);
//						q.offer(new Pair(y + arr1[i], x + arr2[i]));
//					} catch (Exception e) {
//						
//					}
//				}
//			}
//		}
		
		return false;
	}
	
	public void __DEBUG__PRINT() {
		System.out.print("Y\\X ");
		for(int i = 0;i < size.first();i++) {
			System.out.printf("%2d ", i + 1);
		}
		System.out.println();
		for(int i = 0;i < size.second();i++) {
			System.out.printf(" %2d  ", i + 1);
			for(int j = 0;j < size.first();j++) {
				//System.out.print(block[i][j].isBomb() + "," + block[i][j].getNum() + "," + block[i][j].isMined() + " ");
				if(block[i][j].isBomb()) {
					System.out.print("X  ");
				} else {
					if(block[i][j].isMined()) {
						System.out.print(block[i][j].getNum() + "  ");
					} else {
						System.out.print("X  ");
					}
				}
			}
			System.out.print("\n");
		}
	}
	
	public void shuffleArray(boolean[] arr) {
		Random random = ThreadLocalRandom.current();
		for(int i = arr.length - 1;i > 0;i--) {
			int index = random.nextInt(i + 1);
			boolean a = arr[index];
			arr[index] = arr[i];
			arr[i] = a;
		}
	}
}
