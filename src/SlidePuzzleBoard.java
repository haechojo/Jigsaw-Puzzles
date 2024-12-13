package lecture09.slidepuzzlegui;

public class SlidePuzzleBoard {
	private PuzzlePiece[][] board;
	private int size;
	private int emptyRow, emptyCol; // 空白块的位置

	public SlidePuzzleBoard(int s) {
		size = s;
		board = new PuzzlePiece[size][size];
		int number = 1;
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				if (row == size - 1 && col == size - 1) {
					board[row][col] = null; // 最后一块为空
					emptyRow = row;
					emptyCol = col;
				} else {
					board[row][col] = new PuzzlePiece(number++);
				}
			}
		}
	}

	public PuzzlePiece getPuzzlePiece(int row, int col) {
		return board[row][col];
	}

	public boolean move(int row, int col) {
		if ((Math.abs(row - emptyRow) == 1 && col == emptyCol) ||
				(Math.abs(col - emptyCol) == 1 && row == emptyRow)) {
			board[emptyRow][emptyCol] = board[row][col];
			board[row][col] = null;
			emptyRow = row;
			emptyCol = col;
			return true;
		}
		return false;
	}

	public void shuffle() {
		for (int i = 0; i < size * size * 10; i++) {
			int randomRow = (int) (Math.random() * size);
			int randomCol = (int) (Math.random() * size);
			move(randomRow, randomCol);
		}
	}

	public int getSize() {
		return size;
	}

	public boolean isCompleted() {
		int number = 1;
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				if (row == size - 1 && col == size - 1) break;
				if (board[row][col] == null || board[row][col].faceValue() != number++) {
					return false;
				}
			}
		}
		return true;
	}
}
