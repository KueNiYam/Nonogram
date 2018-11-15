
// IMPORT LIBRARY
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

// NONO CLASS
public class Nono implements NativeKeyListener {

	// PUBLIC STATIC OPERATOR
	public static int op;
	public static boolean endFlag = false;

	// SCREENBOARD CLASS (STATIC)
	private static Board board;

	// CLEAR SCREEN STATIC METHOD
	public static void clrscr() {
		for (int i = 0; i < 40; i++)
			System.out.println("");
		System.out.println("NonoGram Logic Game");
		System.out.println("Move ¡è¡é¡ç¡æ : 8546");
		System.out.println("Toggle on/of : Spcace bar");
		System.out.println("Check 'x' : Left shift");
		System.out.println("Submit : Right shift");
		System.out.println("Reset : Delete");
		System.out.println("Escape : ESC");
		System.out.println("");
		System.out.println("          Copyright 2017");
		
		for(int i=0; i<2;i++)
			System.out.println("");
		
	}

	// CHECK ANSWER FROM USER
	public boolean check() {
		// CHECKING FLAG
		int chk = 1;
		// CHECKING MAP (1&4 EQUALIZATION)
		int[][] chkMap = new int[board.mh][board.mw];
		for (int i = 0; i < board.mh; i++) {
			for (int j = 0; j < board.mw; j++) {
				chkMap[i][j] = board.dpMap[i][j];
				if (board.dpMap[i][j] == 4)
					chkMap[i][j] = 0;
			}
		}
		// CHECKING MAP
		for (int i = 0; i < board.mh; i++) {
			for (int j = 0; j < board.mw; j++) {
				if (chkMap[i][j] != board.answer[i][j])
					chk *= 0;
			}
		}
		// RETURN BOOLEAN CORRECT
		if (chk == 1)
			return true;
		else
			return false;
	}

	// DISPLAY SELECT MENU
	public void selectMode() throws NativeHookException, IOException {
		while (true) {
			if (!GlobalScreen.isNativeHookRegistered()) {
				switch (op) {
				case 0: // RANDOM GAME
					Play p = new Play(board, false);
					p.loop(true);
					break;
				case 1: // RETURN PREVIOUS WORK
					Play p2 = new Play(board, true);
					p2.loop(check());
					break;
				case 2: // REFRESH
					clrscr();
					// DISPLAY SELECT MENU
					createMenu();
					GlobalScreen.addNativeKeyListener(this);
					GlobalScreen.registerNativeHook();
					break;
				case 3: // ANSWER CHECK
					if (check() == true) {
						System.out.println("CONGRATUATION! YOU'RE CORRECT!");
						op = 4;
					}
					else {
						op = 1;
					}	
					break;
				case 4: // QUIT GAME
					System.out.println("\nTHANK YOU FOR PLAYING NONOGRAM!");
					Nono.endFlag = true;
					break;
				default: // LOOP WHILE KEYLISTNER WORK
				}
			}
			if (Nono.endFlag == true)
				break;
		}
	}

	// PROGRAM MAIN METHOD
	public static void main(String[] args) throws NativeHookException, IOException {
		// SCANNER
		Scanner sc = new Scanner(System.in);
		// JNATIVEHOOK LIBRARY LOGGING OFF
		LogManager.getLogManager().reset();
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);
		// NONOGRAM GAME INSTANCE CREATE
		Nono.board = new Board();
		// ASSIGNNING & GENERATING RANDOM MAP BY USER INPUT
		System.out.println("----------------------------------");
		System.out.println("WELCOME TO THE NONO WORLD         ");
		System.out.println("INPUT WIDTH & HEGIHT OF RANDOM MAP");
		System.out.println("----------------------------------");
		System.out.print("WIDTH  : ");
		board.mw = sc.nextInt();
		System.out.print("HEIGHT : ");
		board.mh = sc.nextInt();
		sc.close();
		// SELECT MODE LOOP
		op = 0;
		Nono nono = new Nono();
		nono.selectMode();
	}

	public Nono() {
	}

	// DISPLAY SELECT OPERATOR MENU
	public void createMenu() {
		System.out.println("---------------------------");
		System.out.println("WELCOME TO THE NONO WORLD  ");
		System.out.println("PRESS PLAY MODE            ");
		System.out.println("---------------------------");
		System.out.println("1 : CONTINUE PREVIOUS GAME ");
		System.out.println("2 : QUIT                   ");
		System.out.println("3 : REFRESH                ");
		System.out.println("---------------------------");
	}
	
	// KEYLISTENER
	public void nativeKeyPressed(NativeKeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case NativeKeyEvent.VC_1: // RETURN PREVIOUS WORK
			op = 1;
			try {
				GlobalScreen.removeNativeKeyListener(this);
				GlobalScreen.unregisterNativeHook();
			} catch (NativeHookException e1) {
				System.exit(1);
			}
			break;
		case NativeKeyEvent.VC_2: // QUIT
			endFlag = true;
			try {
				GlobalScreen.removeNativeKeyListener(this);
				GlobalScreen.unregisterNativeHook();
			} catch (NativeHookException e1) {
				System.exit(1);
			}
			break;
		case NativeKeyEvent.VC_3: // REFRESH
			op = 2;
			try {
				GlobalScreen.removeNativeKeyListener(this);
				GlobalScreen.unregisterNativeHook();
			} catch (NativeHookException e1) {
				System.exit(1);
			}
			break;
		default:
		}
	}

	public void nativeKeyReleased(NativeKeyEvent e) {

	}

	public void nativeKeyTyped(NativeKeyEvent e) {

	}
}