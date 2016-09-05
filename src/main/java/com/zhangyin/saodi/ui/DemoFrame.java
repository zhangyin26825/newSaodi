package com.zhangyin.saodi.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DemoFrame extends JFrame {

	JPanel panel;

	public DemoFrame(JPanel panel) throws HeadlessException {
		super();
		this.panel = panel;
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension dimension = kit.getScreenSize();
		int windowWidth = 700;
		int windowHeight = 500;
		this.setBounds(0, 0, windowWidth, windowHeight);
		int screenWidth = dimension.width; // 获取屏幕的宽
		int screenHeight = dimension.height; // 获取屏幕的高
		this.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);
		this.add(panel, BorderLayout.CENTER);
	}

}
