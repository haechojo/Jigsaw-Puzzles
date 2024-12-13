package lecture09.slidepuzzlegui;

import javax.swing.*;

public class PuzzleStarter {
    public static void main(String[] args) {
        // 显示模式选择对话框
        String[] modeOptions = {"数字模式", "图片模式"};
        int modeChoice = JOptionPane.showOptionDialog(
                null,
                "请选择游戏模式：",
                "选择模式",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                modeOptions,
                modeOptions[0]
        );

        boolean isImageMode = modeChoice == 1; // 判断是否选择图片模式

        int boardSize = 3; // 默认大小

        // 数字模式：选择难易度
        if (!isImageMode) {
            String[] difficultyOptions = {"简单 (3x3)", "中等 (4x4)", "困难 (5x5)"};
            int difficultyChoice = JOptionPane.showOptionDialog(
                    null,
                    "请选择游戏难易度：",
                    "选择难易度",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    difficultyOptions,
                    difficultyOptions[0]
            );

            switch (difficultyChoice) {
                case 0 -> boardSize = 3; // 简单
                case 1 -> boardSize = 4; // 中等
                case 2 -> boardSize = 5; // 困难
                default -> {
                    JOptionPane.showMessageDialog(null, "未选择难易度，程序将退出！");
                    System.exit(0);
                }
            }
        } else {
            // 图片模式：选择图片
            String[] imageOptions = {"图片 1", "图片 2", "图片 3", "图片 4", "图片 5", "图片 6", "图片 7", "图片 8", "图片 9"};
            int imageChoice = JOptionPane.showOptionDialog(
                    null,
                    "请选择要使用的图片：",
                    "选择图片",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    imageOptions,
                    imageOptions[0]
            );

            if (imageChoice < 0) {
                JOptionPane.showMessageDialog(null, "未选择图片，程序将退出！");
                System.exit(0); // 如果未选择图片，则退出程序
            }

            String selectedFolder = "/images/" + (imageChoice + 1) + "/"; // 对应图片文件夹
            new PuzzleFrame(new SlidePuzzleBoard(3), true, selectedFolder); // 固定图片模式为 3x3
            return;
        }

        // 初始化数字模式游戏
        new PuzzleFrame(new SlidePuzzleBoard(boardSize), false, null);
    }
}
