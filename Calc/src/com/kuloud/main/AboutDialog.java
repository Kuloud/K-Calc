package com.kuloud.main;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JDialog;

import com.kuloud.contents.Details;

import java.awt.Dimension;

public class AboutDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel aboutContentPane = null;
	private JLabel emailLabel;
	private JLabel versionLabel;
	private JLabel vendorLabel;
	private JLabel titleLabel;
	private JScrollPane detailsScrollPane;
	private JTextPane detailsTextPane; 

	public AboutDialog(Frame owner) {
		super(owner);
		initialize();
	}

	private void initialize() {
		this.setPreferredSize(new Dimension(390, 260));
		this.setTitle("关于");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/Info.png")));
		this.setContentPane(getAboutContentPane());
		this.setResizable(false);
	}

	private JPanel getAboutContentPane() {
		if (aboutContentPane == null) {
			emailLabel = new JLabel();
			emailLabel.setBounds(new Rectangle(10, 200, 200, 20));
			emailLabel.setText("邮箱：plextyboy@gmail.com");
			versionLabel = new JLabel();
			versionLabel.setBounds(new Rectangle(10, 180, 150, 20));
			versionLabel.setText("版本：1.0");
			vendorLabel = new JLabel();
			vendorLabel.setBounds(new Rectangle(10, 160, 150, 20));
			vendorLabel.setText("作者：肖中中");
			titleLabel = new JLabel();
			titleLabel.setText("数值方法计算器");
			titleLabel.setBounds(new Rectangle(10, 10, 112, 30));
			titleLabel.setFont(new Font("Dialog", Font.BOLD, 12));
			aboutContentPane = new JPanel();
			aboutContentPane.setLayout(null);
			aboutContentPane.add(vendorLabel, null);
			aboutContentPane.add(versionLabel, null);
			aboutContentPane.add(emailLabel, null);
			aboutContentPane.add(getDetailsScrollPane(), null);
			aboutContentPane.add(titleLabel, null);
		}
		return aboutContentPane;
	}

	private JScrollPane getDetailsScrollPane() {
		if (detailsScrollPane == null) {
			detailsScrollPane = new JScrollPane();
			detailsScrollPane.setBorder(null);
			detailsScrollPane.setViewportView(getDetailsTextPane());
			detailsScrollPane.setBounds(new Rectangle(10, 40, 360, 120));
		}
		return detailsScrollPane;
	}

	private JTextPane getDetailsTextPane() {
		if (detailsTextPane == null) {
			detailsTextPane = new JTextPane();
			detailsTextPane.setBackground(new Color(238, 238, 238));
			detailsTextPane.setEditable(false);
			detailsTextPane.setText(Details.INTRO);
		}
		return detailsTextPane;
	}
}
