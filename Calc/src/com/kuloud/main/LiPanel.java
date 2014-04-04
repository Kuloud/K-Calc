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

public class LiPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel liResultLabel;
	private JLabel liTLabel;
	private JLabel liYLabel;
	private JLabel liXLabel;
	private JTextField liResultTextField;
	private JTextField liTTextField;
	private JTextField liXTextField;
	private JTextField liYTextField;
	private JButton liCalcButton;
	private JButton liClearButton;

	public LiPanel() {
		super();
		initialize();
	}

	protected void calcLiValue() {
		double t = 0.0;
		int i, j, k;
		double result = 0.0;
		double[] x = new double[Def.N];
		double[] y = new double[Def.N];

		try {
			t = Double.parseDouble(liTTextField.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, ErrMessage.T_WRONG);
			liTTextField.requestFocus();
			liTTextField.selectAll();
		}

		String xstr = liXTextField.getText();
		String ystr = liYTextField.getText();
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
				liXTextField.requestFocus();
				liXTextField.selectAll();
			} else {
				for (k = 0; k < i - 1; k++) {
					for (int m = k + 1; m < i; m++) {
						if (x[k] == x[m]) {
							JOptionPane.showMessageDialog(null,
									ErrMessage.X_REPEAT);
							liXTextField.requestFocus();
							liXTextField.selectAll();
						}
					}
				}
				result = Interpolation.getValueLinear(j, x, y, t);
			}
		} else {
			if (j < 2) {
				// x与y的个数不匹配
				JOptionPane.showMessageDialog(null, "输入格式错误，请检查重输。");
				liXTextField.requestFocus();
				liXTextField.selectAll();
			} else {
				result = Interpolation.getValueLinear(j, x[0], x[1], y, t);
			}
		}
		liResultTextField.setText(result + "");
	}

	protected void clearLi() {
		liXTextField.setText("");
		liYTextField.setText("");
		liTTextField.setText("");
		liResultTextField.setText("");
	}

	private void initialize() {
		liResultLabel = new JLabel();
		liResultLabel.setBounds(new Rectangle(10, 130, 100, 20));
		liResultLabel.setText(LabelText.RESULTTEXT);
		liTLabel = new JLabel();
		liTLabel.setBounds(new Rectangle(10, 70, 100, 20));
		liTLabel.setText(LabelText.TTEXT);
		liYLabel = new JLabel();
		liYLabel.setBounds(new Rectangle(10, 40, 100, 20));
		liYLabel.setText(LabelText.YTEXT);
		liXLabel = new JLabel();
		liXLabel.setBounds(new Rectangle(10, 10, 100, 20));
		liXLabel.setText(LabelText.XTEXT);
		

		liXTextField = new JTextField();
		liXTextField.setBounds(new Rectangle(120, 9, 300, 22));
		liXTextField.setToolTipText(Prompt.X_TIP);
		liYTextField = new JTextField();
		liYTextField.setBounds(new Rectangle(120, 39, 300, 22));
		liTTextField = new JTextField();
		liYTextField.setToolTipText(Prompt.Y_TIP);
		liTTextField.setBounds(new Rectangle(120, 69, 150, 22));
		liTTextField.setToolTipText(Prompt.T_TIP);
		liResultTextField = new JTextField();
		liResultTextField.setBounds(new Rectangle(120, 129, 150, 22));
		liResultTextField.setEditable(false);

		liCalcButton = new JButton();
		liCalcButton.setBounds(new Rectangle(100, 100, 60, 20));
		liCalcButton.setText(LabelText.CALC);
		liCalcButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				calcLiValue();
			}
		});
		liClearButton = new JButton();
		liClearButton.setBounds(new Rectangle(250, 100, 60, 20));
		liClearButton.setText(LabelText.CLEAR);
		liClearButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				clearLi();
			}
		});

		this.setSize(430, 160);
		this.setLayout(null);
		this.setVisible(true);
		this.add(liXLabel, null);
		this.add(liXTextField, null);
		this.add(liYLabel, null);
		this.add(liYTextField, null);
		this.add(liTLabel, null);
		this.add(liTTextField, null);
		this.add(liCalcButton, null);
		this.add(liClearButton, null);
		this.add(liResultLabel, null);
		this.add(liResultTextField, null);
	}
}