package lecture09.slidepuzzlegui;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

public class PuzzleFrame extends JFrame {
	private SlidePuzzleBoard board;
	private PuzzleButton[][] button_board;
	private JButton startButton;
	private boolean isGameActive;
	private boolean isImageMode;
	private String imageFolder; // 图片文件夹路径
	private int buttonWidth, buttonHeight; // 按钮宽高

	public PuzzleFrame(SlidePuzzleBoard b, boolean imageMode, String folderPath) {
		board = b;
		isImageMode = imageMode;
		imageFolder = folderPath; // 图片模式下的文件夹路径
		int size = board.getSize();
		button_board = new PuzzleButton[size][size];
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		JPanel gridPanel = new JPanel(new GridLayout(size, size));
		BufferedImage sampleImage = null;

		if (isImageMode) {
			// 加载示例图片用于计算尺寸
			try {
				String sampleImagePath = imageFolder + "image1.png";
				sampleImage = ImageIO.read(getClass().getResource(sampleImagePath));
			} catch (IOException e) {
				System.err.println("无法加载图片用于计算尺寸！");
				e.printStackTrace();
			}
		}

		// 根据图片或默认大小计算按钮宽高
		if (sampleImage != null) {
			buttonWidth = sampleImage.getWidth() / size;
			buttonHeight = sampleImage.getHeight() / size;
		} else {
			buttonWidth = 100; // 默认宽度
			buttonHeight = 100; // 默认高度
		}

		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				button_board[row][col] = new PuzzleButton(board, this, isImageMode, row, col);
				button_board[row][col].setPreferredSize(new Dimension(buttonWidth, buttonHeight));
				gridPanel.add(button_board[row][col]);
			}
		}

		startButton = new JButton("重新开始");
		startButton.addActionListener(e -> {
			board.shuffle(); // 点击时重新打乱
			isGameActive = true;
			update();
		});

		cp.add(gridPanel, BorderLayout.CENTER);
		cp.add(startButton, BorderLayout.SOUTH);

		isGameActive = true;

		board.shuffle(); // 初始化时打乱棋盘
		setFocusable(true);

		update();
		setTitle(isImageMode ? "拼图游戏 - 图片模式" : "拼图游戏 - 数字模式");
		setVisible(true);
		pack(); // 自动调整窗口大小以适应内容
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	public void update() {
		for (int row = 0; row < board.getSize(); row++) {
			for (int col = 0; col < board.getSize(); col++) {
				PuzzlePiece pp = board.getPuzzlePiece(row, col);
				if (pp != null) {
					if (isImageMode) {
						// 加载图片
						String imagePath = imageFolder + "image" + pp.faceValue() + ".png";
						java.net.URL imgURL = getClass().getResource(imagePath);

						if (imgURL != null) {
							button_board[row][col].setIcon(new ImageIcon(new ImageIcon(imgURL).getImage()
									.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH)));
							button_board[row][col].setText(""); // 隐藏文字
						} else {
							System.err.println("图片未找到: " + imagePath);
							button_board[row][col].setText("X");
						}
					} else {
						button_board[row][col].setIcon(null); // 数字模式
						button_board[row][col].setText(Integer.toString(pp.faceValue()));
					}
				} else {
					if (board.isCompleted()) {
						if (isImageMode) {
							// 拼图完成时补全最后一块
							String imagePath = imageFolder + "image" + (board.getSize() * board.getSize()) + ".png";
							java.net.URL imgURL = getClass().getResource(imagePath);
							if (imgURL != null) {
								button_board[row][col].setIcon(new ImageIcon(new ImageIcon(imgURL).getImage()
										.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH)));
								button_board[row][col].setText(""); // 隐藏文字
							} else {
								System.err.println("最后一块图片未找到: " + imagePath);
								button_board[row][col].setText("Done");
							}
						} else {
							button_board[row][col].setText("Done"); // 数字模式显示 "Done"
						}
						isGameActive = false; // 禁止进一步移动
					} else {
						button_board[row][col].setIcon(null);
						button_board[row][col].setText("");
					}
				}
			}
		}
	}

	public boolean isGameActive() {
		return isGameActive;
	}
}
