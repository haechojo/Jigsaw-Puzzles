package lecture09.slidepuzzlegui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PuzzleButton extends JButton implements ActionListener {
	private SlidePuzzleBoard board;
	private PuzzleFrame frame;
	private boolean isImageMode;
	private int row, col; // 按钮在棋盘中的位置

	public PuzzleButton(SlidePuzzleBoard b, PuzzleFrame f, boolean imageMode, int r, int c) {
		board = b;
		frame = f;
		isImageMode = imageMode;
		row = r;
		col = c;

		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 检查游戏是否仍然有效
		if (!frame.isGameActive()) {
			return;
		}
		// 调用棋盘的移动逻辑
		if (board.move(row, col)) {
			frame.update(); // 更新显示
		}
	}
}
