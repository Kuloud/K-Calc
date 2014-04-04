package com.kuloud.main;

import java.awt.Rectangle;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.kuloud.algorithms.Interpolation;
import com.kuloud.contents.Def;
import com.kuloud.contents.ErrMessage;
import com.kuloud.contents.LabelText;
import com.kuloud.contents.Prompt;
/**
 * 牛顿插值公式模块
 * @author 肖中中
 *
 */
public class NePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel neResultLabel;
	private JLabel neTLabel;
	private JLabel neYLabel;
	private JLabel neXLabel;
	private JTextField neResultTextField;
	private JTextField neYTextField;
	private JTextField neXTextField;
	private JTextField neTTextField;
	private JButton neCalcButton;
	private JButton neClearButton;

	public NePanel() {
		super();
		initialize();
	}

	protected void calcNeValue() {
		// 牛顿插值公式运算
		double t = 0;
		int i, j, k;
		double result = 0.0;
		double[] x = new double[Def.N];
		double[] y = new double[Def.N];

		try {
			t = Double.parseDouble(neTTextField.getText());
		} catch (NumberFormatException e) {
			// t值输入格式错误
			JOptionPane.showMessageDialog(null, ErrMessage.T_WRONG);
			neTTextField.requestFocus();
			neTTextField.selectAll();
		}

		String xstr = neXTextField.getText();
		String ystr = neYTextField.getText();
		Scanner xs = new Scanner(xstr);
		Scanner ys = new Scanner(ystr);

		k = 0;
		while (ys.hasNextDouble()) {
			y[k] = ys.nextDouble();
			k++;
		}
		j = k;

		k = 0;
		while (xs.hasNextDouble()) {
			x[k] = xs.nextDouble();
			k++;
		}
		i = k;

		if (i != 2) {
			if (i != j) {
				JOptionPane.showMessageDialog(null, ErrMessage.NOT_MATCH);
				neXTextField.requestFocus();
				neXTextField.selectAll();
			} else {
				for (k = 0; k < i - 1; k++) {
					for (int m = k + 1; m < i; m++) {
						if (x[k] == x[m]) {
							JOptionPane.showMessageDialog(null,
									ErrMessage.NOT_MATCH);
							neXTextField.requestFocus();
							neXTextField.selectAll();
						}
					}
				}
				result = Interpolation.getValueNewton(j, x, y, t);
			}
		} else {
			if (j < 2) {
				// x与y的个数不匹配
				JOptionPane.showMessageDialog(null, "输入格式错误，请检查重输。");
				neXTextField.requestFocus();
				neXTextField.selectAll();
			} else {
				result = Interpolation.getValueNewton(j, x[0], x[1], y, t);
			}
		}

		neResultTextField.setText(result + "");
	}

	protected void clearNe() {
		// 清空所有文本框
		neXTextField.setText("");
		neYTextField.setText("");
		neTTextField.setText("");
		neResultTextField.setText("");
	}

	private void initialize() {
		neResultLabel = new JLabel();
		neResultLabel.setBounds(new Rectangle(10, 130, 100, 20));
		neResultLabel.setText(LabelText.RESULTTEXT);
		neTLabel = new JLabel();
		neTLabel.setBounds(new Rectangle(10, 70, 100, 20));
		neTLabel.setText(LabelText.TTEXT);
		neYLabel = new JLabel();
		neYLabel.setBounds(new Rectangle(10, 40, 100, 20));
		neYLabel.setText(LabelText.YTEXT);
		neXLabel = new JLabel();
		neXLabel.setBounds(new Rectangle(10, 10, 100, 20));
		neXLabel.setText(LabelText.XTEXT);

		neTTextField = new JTextField();
		neTTextField.setBounds(new Rectangle(120, 69, 150, 22));
		neTTextField.setToolTipText(Prompt.T_TIP);
		neResultTextField = new JTextField();
		neResultTextField.setBounds(new Rectangle(120, 129, 150, 22));
		neResultTextField.setEditable(false);
		neXTextField = new JTextField();
		neXTextField.setBounds(new Rectangle(120, 9, 300, 22));
		neXTextField.setToolTipText(Prompt.X_TIP);
		neYTextField = new JTextField();
		neYTextField.setBounds(new Rectangle(120, 39, 300, 22));
		neYTextField.setToolTipText(Prompt.Y_TIP);

		neCalcButton = new JButton();
		neCalcButton.setBounds(new Rectangle(100, 100, 60, 20));
		neCalcButton.setText(LabelText.CALC);
		neCalcButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				calcNeValue();
			}
		});
		neClearButton = new JButton();
		neClearButton.setBounds(new Rectangle(250, 100, 60, 20));
		neClearButton.setText(LabelText.CLEAR);
		neClearButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				clearNe();
			}
		});

		this.setSize(430, 160);
		this.setLayout(null);
		this.setVisible(true);
		this.add(neXLabel, null);
		this.add(neXTextField, null);
		this.add(neYLabel, null);
		this.add(neYTextField, null);
		this.add(neTLabel, null);
		this.add(neTTextField, null);
		this.add(neCalcButton, null);
		this.add(neClearButton, null);
		this.add(neResultLabel, null);
		this.add(neResultTextField, null);
	}
}