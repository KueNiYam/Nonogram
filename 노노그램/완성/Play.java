import java.io.IOException;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class Play implements NativeKeyListener {
	Board board;

	// CONSTRUCTOR
	public Play(Board board, boolean b) {
		// ASSIGN BOARD OBJECT
		this.board = board;

		// CHECK BOOLEAN EXIST TEMP MAP
		if (b == false) {
			// IF NOT EXIST, LOAD CLEAR MAP (ASSIGN & FILL 0)
			board.assign();
			genRMap();
			reset();
		}
	}

	// MAP CLEAR
	public void reset() {
		for (int i = 0; i < board.mh; i++) {
			for (int j = 0; j < board.mw; j++) {
				board.dpMap[i][j] = 0;
			}
		}
	}

	// GENERATING RANDOM MAP & SETTING WIDTH, HEIGHT
	public void genRMap() {
		for (int i = 0; i < board.mh; i++) {
			for (int j = 0; j < board.mw; j++) {
				board.answer[i][j] = (int) (Math.random() * 2);
			}
		}
	}

	// DISPLAY
	public void display() {
		Nono.clrscr();
		for (int i = 0; i < board.mh; i++) {
			for (int j = 0; j < board.mw; j++) {
				board.fdpMap[i][j] = board.dpMap[i][j];
			}
		}
		// USER CURSOR
		if (board.fdpMap[board.curY][board.curX] == 0)
			board.fdpMap[board.curY][board.curX] = 2;
		else if (board.fdpMap[board.curY][board.curX] == 1)
			board.fdpMap[board.curY][board.curX] = 3;
		else if (board.fdpMap[board.curY][board.curX] == 4)
			board.fdpMap[board.curY][board.curX] = 5;
		// EMPTY SPACE
		for (int r = 0; r < board.scr.length - board.mh; r++) {
			for (int c = 0; c < board.scr[0].length - board.mw; c++) {
				board.scr[r][c] = " ";
			}
		}
		// LOGIC QUESTION CHART PART
		for (int r = 0; r < board.scr.length - board.mh; r++) {
			for (int c = board.scr[0].length - board.mw; c < board.scr[0].length; c++) {
				if (String.valueOf(board.quesC[r][c - board.scr[r].length + board.mw]).equals("0"))
					board.scr[r][c] = "X";
				else
					board.scr[r][c] = board.quesC[r][c - board.scr[r].length + board.mw];
			}
		}
		for (int r = board.scr.length - board.mh; r < board.scr.length; r++) {
			for (int c = 0; c < board.scr[0].length - board.mw; c++) {
				if (String.valueOf(board.quesR[r - board.scr.length + board.mh][c]).equals("0"))
					board.scr[r][c] = "X";
				else
					board.scr[r][c] = board.quesR[r - this.board.scr.length + board.mh][c];
			}
		}
		// LOGIC MAP PART
		for (int r = board.scr.length - board.mh; r < board.scr.length; r++) {
			for (int c = board.scr[0].length - board.mw; c < board.scr[0].length; c++) {
				switch (board.fdpMap[r - board.scr.length + board.mh][c - board.scr[r].length + board.mw]) {
				case 0:
					board.scr[r][c] = "¡à";
					break;
				case 1:
					board.scr[r][c] = "¡á";
					break;
				case 2:
					board.scr[r][c] = "¡Û";
					break;
				case 3:
					board.scr[r][c] = "¡Ü";
					break;
				case 4:
					board.scr[r][c] = "X";
					break;
				case 5:
					board.scr[r][c] = "¡î";
					break;
				default:
				}
			}
		}
		// DISPLAY SCREEN BUFFER
		for (int r = 0; r < board.scr.length; r++) {
			for (int c = 0; c < board.scr[0].length; c++) {
				System.out.print(board.scr[r][c]);
			}
			System.out.println();
		}
	}

	// LOOP
	public void loop(boolean bChk) throws NativeHookException, IOException {
		board.extMapR();
		board.extMapC();
		display();
		if(bChk == false)
			System.out.println("\nYOU'RE INCORRECT!");
		GlobalScreen.registerNativeHook();
		GlobalScreen.addNativeKeyListener(this);
	}
	
	// KEYLISTENER
	public void nativeKeyPressed(NativeKeyEvent e) {
		switch (e.getKeyCode()) {
		case NativeKeyEvent.VC_KP_8: // UP
			board.setCursor(board.curX, board.curY - 1);
			break;
		case NativeKeyEvent.VC_KP_5: // DOWN
			board.setCursor(board.curX, board.curY + 1);
			break;
		case NativeKeyEvent.VC_KP_4: // LEFT
			board.setCursor(board.curX - 1, board.curY);
			break;
		case NativeKeyEvent.VC_KP_6: // RIGHT
			board.setCursor(board.curX + 1, board.curY);
			break;
		case NativeKeyEvent.VC_SPACE: // TOGGLE CHECK
			board.toggle(0);
			break;
		case NativeKeyEvent.VC_SHIFT_L: // TOGGLE X
			board.toggle(1);
			break;
		case NativeKeyEvent.VC_SHIFT_R: // CHECK
			break;
		case NativeKeyEvent.VC_DELETE: // RESET
			reset();
			break;
		case NativeKeyEvent.VC_ESCAPE: // QUIT
			break;
		default:
		}
	}

	public void nativeKeyReleased(NativeKeyEvent e) {
		board.extMapR();
		board.extMapC();
		display();
		switch (e.getKeyCode()) {
		case NativeKeyEvent.VC_SHIFT_R:
			Nono.op = 3;
			try {
				GlobalScreen.removeNativeKeyListener(this);
				GlobalScreen.unregisterNativeHook();
			} catch (NativeHookException e1) {
				System.exit(1);
			}
			break;
		case NativeKeyEvent.VC_ESCAPE:
			Nono.op = 4;
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

	public void nativeKeyTyped(NativeKeyEvent e) {

	}
}
