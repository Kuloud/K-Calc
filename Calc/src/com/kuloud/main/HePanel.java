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

public class HePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel heResultLabel;
	private JLabel heTLabel;
	private JLabel heDYLabel;
	private JLabel heYLabel;
	private JLabel heXLabel;
	private JTextField heTTextField;
	private JTextField heXTextField;
	private JTextField heYTextField;
	private JTextField heResultTextField;
	private JTextField heDYTextField;
	private JButton heClearButton;
	private JButton heCalcButton;

	public HePanel() {
		super();
		initialize();
	}

	protected void calcHeValue() {
		double t = 0.0;
		int i, j, k, m;
		double result = 0.0;
		double[] x = new double[Def.N];
		double[] y = new double[Def.N];
		double[] dy = new double[Def.N];

		try {
			t = Double.parseDouble(heTTextField.getText());
		} catch (NumberFormatException e) {
			// t值输入格式错误
			JOptionPane.showMessageDialog(null, ErrMessage.T_WRONG);
			heTTextField.requestFocus();
			heTTextField.selectAll();
		}

		String xstr = heXTextField.getText();
		String ystr = heYTextField.getText();
		String dystr = heDYTextField.getText();
		Scanner xs = new Scanner(xstr);
		Scanner ys = new Scanner(ystr);
		Scanner dys = new Scanner(dystr);

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

		k = 0;
		while (dys.hasNextDouble()) {
			dy[k] = dys.nextDouble();
			k++;
		}
		m = k;

		if (i != 2) {
			if (i != j || i != m || j != m) {
				// x与y的个数不匹配
				JOptionPane.showMessageDialog(null, ErrMessage.NOT_MATCH);
				heXTextField.requestFocus();
				heXTextField.selectAll();
			} else {
				for (k = 0; k < i - 1; k++) {
					for (int n = k + 1; n < i; n++) {
						if (x[k] == x[n]) {
							// x输入有重复
							JOptionPane.showMessageDialog(null,
									ErrMessage.X_REPEAT);
							heXTextField.requestFocus();
							heXTextField.selectAll();
						}
					}
				}
				result = Interpolation.getValueHermite(j, x, y, dy, result);
			}
		} else {
			if (j < 2) {
				// x与y的个数不匹配
				JOptionPane.showMessageDialog(null, "输入格式错误，请检查重输。");
				heXTextField.requestFocus();
				heXTextField.selectAll();
			} else {
				result = Interpolation.getValueHermite(j, x[0], x[1], y, dy, t);
			}
		}

		heResultTextField.setText(result + "");
	}

	protected void clearHe() {
		heXTextField.setText("");
		heYTextField.setText("");
		heDYTextField.setText("");
		heTTextField.setText("");
		heResultTextField.setText("");
	}

	private void initialize() {
		heResultLabel = new JLabel();
		heResultLabel.setBounds(new Rectangle(10, 160, 100, 20));
		heResultLabel.setText(LabelText.RESULTTEXT);
		heTLabel = new JLabel();
		heTLabel.setBounds(new Rectangle(10, 100, 100, 20));
		heTLabel.setText(LabelText.TTEXT);
		heDYLabel = new JLabel();
		heDYLabel.setBounds(new Rectangle(10, 70, 100, 20));
		heDYLabel.setText(LabelText.DYTEXT);
		heYLabel = new JLabel();
		heYLabel.setBounds(new Rectangle(10, 40, 100, 20));
		heYLabel.setText(LabelText.YTEXT);
		heXLabel = new JLabel();
		heXLabel.setBounds(new Rectangle(10, 10, 100, 20));
		heXLabel.setText(LabelText.XTEXT);

		heXTextField = new JTextField();
		heXTextField.setBounds(new Rectangle(120, 9, 300, 22));
		heYTextField = new JTextField();
		heYTextField.setBounds(new Rectangle(120, 39, 300, 22));
		heDYTextField = new JTextField();
		heDYTextField.setBounds(new Rectangle(120, 69, 300, 22));
		heTTextField = new JTextField();
		heTTextField.setBounds(new Rectangle(120, 99, 150, 22));
		heResultTextField = new JTextField();
		heResultTextField.setBounds(new Rectangle(120, 159, 150, 22));
		heResultTextField.setEditable(false);

		heCalcButton = new JButton();
		heCalcButton.setBounds(new Rectangle(100, 130, 60, 20));
		heCalcButton.setText(LabelText.CALC);
		heCalcButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				calcHeValue();
			}
		});
		heClearButton = new JButton();
		heClearButton.setBounds(new Rectangle(250, 130, 60, 20));
		heClearButton.setText(LabelText.CLEAR);
		heClearButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				clearHe();
			}
		});

		this.setSize(430, 190);
		this.setLayout(null);
		this.setVisible(true);
		this.add(heXLabel, null);
		this.add(heXTextField, null);
		this.add(heYLabel, null);
		this.add(heYTextField, null);
		this.add(heDYLabel, null);
		this.add(heDYTextField, null);
		this.add(heTLabel, null);
		this.add(heTTextField, null);
		this.add(heCalcButton, null);
		this.add(heClearButton, null);
		this.add(heResultLabel, null);
		this.add(heResultTextField, null);
	}
}
