public class Board {
	// ANSWER LOGIC MAP
	int[][] answer;

	// WIDTH & HEIGHT SIZE OF LOGIC MAP
	int mw, mh;

	// CHART OF QUESTION ROW & COL
	String[][] quesR;
	String[][] quesC;

	// MAP FOR DISPLAY
	int[][] dpMap;

	// LOCATION OF CURSOR
	int curX, curY;

	// SCR + MAP FOR DISPLAY
	int[][] fdpMap;

	// SCREENBOARD
	String[][] scr;

	// CONSTRUCTOR
	public Board() {
	}

	// ASSIGN SIZE
	public void assign() {
		dpMap = new int[mh][mw];
		fdpMap = new int[mh][mw];
		answer = new int[mh][mw];

		setCursor(0, 0);
		extMapR();
		extMapC();

		scr = new String[mh + quesC.length][mw + quesR[0].length];
	}

	// EXTRACT QUESTION ROW LINE
	public void extMapR() {
		int[] count = new int[mh];
		int[][] rowcount = new int[mh][mw];
		int[][] rowcount2 = new int[mh][mw];
		String[][] strrow = new String[mh][mw];
		boolean flag = false;
		for (int fix = 0; fix < mh; fix++) {
			int temp = 0;

			for (int j = 0; j < mw; j++) {
				if (answer[fix][j] == 1 && flag == false) {
					temp++;
					rowcount[fix][temp]++;
					flag = true;
					count[fix]++;
				} else if (answer[fix][j] == 1 && flag == true) {
					rowcount[fix][temp]++;
					count[fix]++;
				} else if (answer[fix][j] == 0 || answer[fix][j] == 4) {
					flag = false;
				}
			}

			for (int i = 0; i < mw - 1; i++) {
				rowcount[fix][i] = rowcount[fix][i + 1];
			}

			temp = 0;
			flag = false;
		}
		for (int i = 0; i < mh; i++) {
			int temp2 = count[i];
			while (temp2 != 0) {
				if (rowcount[i][0] == 0) {
					break;
				}
				temp2--;
				int temp = rowcount[i][0];

				for (int j = 2; j < rowcount[0].length; j++) {
					rowcount2[i][j - 2] = rowcount2[i][j];
				}
				for (int j = 1; j < mw; j++) {
					rowcount[i][j - 1] = rowcount[i][j];
				}
				rowcount2[i][rowcount2[0].length - 1] = temp;
			}
		}
		for (int i = 0; i < mh; i++) {
			for (int j = 0; j < mw; j++) {
				strrow[i][j] = String.valueOf(rowcount2[i][j]);
			}
		}
		quesR = new String[strrow.length][strrow[0].length];
		for (int i = 0; i < mh; i++) {
			for (int j = 0; j < mw; j++) {
				quesR[i][j] = strrow[i][j];
			}
		}
	}

	// EXTRACT QUESTION COLUMN LINE
	public void extMapC() {
		int[] count = new int[mw];
		int[][] colcount = new int[mw][mh];
		int[][] colcount2 = new int[mw][mh];
		String[][] strtmp = new String[mw][mh];
		String[][] strcol = new String[mh][mw];
		boolean flag = false;
		for (int fix = 0; fix < mw; fix++) {
			int temp = 0;
			for (int j = 0; j < mh; j++) {
				if (answer[j][fix] == 1 && flag == false) {
					temp++;
					colcount[fix][temp]++;
					flag = true;
					count[fix]++;
				} else if (answer[j][fix] == 1 && flag == true) {
					colcount[fix][temp]++;
					count[fix]++;
				} else if (answer[j][fix] == 0) {
					flag = false;
				}
			}
			for (int i = 0; i < mh - 1; i++) {
				colcount[fix][i] = colcount[fix][i + 1];
			}
			temp = 0;
			flag = false;
		}
		for (int i = 0; i < mw; i++) {
			int temp2 = count[i];
			while (temp2 != 0) {
				if (colcount[i][0] == 0) {
					break;
				}
				temp2--;
				int temp = colcount[i][0];

				for (int j = 2; j < colcount[0].length; j++) {
					colcount2[i][j - 2] = colcount2[i][j];
				}
				for (int j = 1; j < mh; j++) {
					colcount[i][j - 1] = colcount[i][j];
				}
				colcount2[i][colcount2[0].length - 1] = temp;
			}
		}
		for (int i = 0; i < mw; i++) {
			for (int j = 0; j < mh; j++) {
				strtmp[i][j] = String.valueOf(colcount2[i][j]);
			}
		}
		for (int i = 0; i < mw; i++) {
			for (int j = mh - 1; j >= 0; j--) {
				strcol[j][i] = strtmp[i][j];
			}
		}
		quesC = new String[strcol.length][strcol[0].length];
		for (int i = 0; i < mh; i++) {
			for (int j = 0; j < mw; j++) {
				quesC[i][j] = strcol[i][j];
			}
		}
	}

	// SET CURSOR
	public void setCursor(int x, int y) {
		if (x >= 0 && x < this.mw && y >= 0 && y < this.mh) {
			curX = x;
			curY = y;
		}
	}

	// TOGGLE ON & OFF (PARAMETER m ~~~~ 0 : CHK , 1 : X)
	public void toggle(int m) {
		switch (m) {
		case 0:
			switch (dpMap[curY][curX]) {
			case 0:
				dpMap[curY][curX] = 1;
				break;
			case 1:
				dpMap[curY][curX] = 0;
				break;
			default:
			}
			break;
		case 1:
			switch (dpMap[curY][curX]) {
			case 0:
				dpMap[curY][curX] = 4;
				break;
			case 4:
				dpMap[curY][curX] = 0;
				break;
			default:
			}
			break;
		default:
		}
	}

}