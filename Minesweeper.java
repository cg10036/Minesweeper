
import java.util.Scanner;

public class Minesweeper {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		int x, y, num;
		System.out.print("Input size: ");
		x = scanner.nextInt();
		y = x;
		System.out.print("Input mine num: ");
		num = scanner.nextInt();
		
//		Engine engine = new Engine(x, y, num);
		
		Gui gui = new Gui(x, y, num);
		
//		while(true) {
//			int _x, _y;
//			engine.__DEBUG__PRINT();
//			System.out.print("Input x y: ");
//			_x = scanner.nextInt() - 1;
//			_y = scanner.nextInt() - 1;
//			if(engine.isMineable(_x, _y)) {
//				if(engine.mine(_x,  _y)) {
//					System.out.println("FAILED!!!!");
//					break;
//				}
//				if(engine.isAllMined()) {
//					System.out.println("SUCCESS!!!");
//					break;
//				}
//			} else {
//				System.out.println("WRONG PLACE!!!");
//			}
//		}
		
		//engine.mine(1, 1);
		//engine.__DEBUG__PRINT();
	}

}
