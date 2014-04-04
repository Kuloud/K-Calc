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
 * 拉格朗日插值模块
 * 
 * @author 肖中中
 * 
 */
public class LaPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel laResultLabel;
	private JLabel laTLabel;
	private JLabel laYLabel;
	private JLabel laXLabel;
	private JTextField laResultTextField;
	private JTextField laYTextField;
	private JTextField laXTextField;
	private JTextField laTTextField;
	private JButton laCalcButton;
	private JButton laClearButton;

	public LaPanel() {
		super();
		initialize();
	}

	protected void calcLaValue() {
		// 拉格朗日插值的运算
		double t = 0.0;
		int i, j, k;
		double result = 0.0;
		double[] x = new double[Def.N];
		double[] y = new double[Def.N];

		try {
			t = Double.parseDouble(laTTextField.getText());
		} catch (NumberFormatException e) {
			// t值输入格式错误
			JOptionPane.showMessageDialog(null, ErrMessage.T_WRONG);
			laTTextField.requestFocus();
			laTTextField.selectAll();
		}

		String xstr = laXTextField.getText();
		String ystr = laYTextField.getText();
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
				// x与y的个数不匹配
				JOptionPane.showMessageDialog(null, ErrMessage.NOT_MATCH);
				laXTextField.requestFocus();
				laXTextField.selectAll();
			} else {
				for (k = 0; k < i - 1; k++) {
					for (int m = k + 1; m < i; m++) {
						if (x[k] == x[m]) {
							// x输入有重复
							JOptionPane.showMessageDialog(null,
									ErrMessage.X_REPEAT);
							laXTextField.requestFocus();
							laXTextField.selectAll();
						}
					}
				}
				result = Interpolation.getValueLagrange(j, x, y, t);
			}
		} else {
			if (j < 2) {
				// x与y的个数不匹配
				JOptionPane.showMessageDialog(null, "输入格式错误，请检查重输。");
				laXTextField.requestFocus();
				laXTextField.selectAll();
			} else {
				result = Interpolation.getValueLagrange(j, x[0], x[1], y, t);
			}
		}

		laResultTextField.setText(result + "");
	}

	protected void clearLa() {
		// 清空所有文本框
		laXTextField.setText("");
		laYTextField.setText("");
		laTTextField.setText("");
		laResultTextField.setText("");
	}

	private void initialize() {
		laResultLabel = new JLabel();
		laResultLabel.setBounds(new Rectangle(10, 130, 100, 20));
		laResultLabel.setText(LabelText.RESULTTEXT);
		laTLabel = new JLabel();
		laTLabel.setBounds(new Rectangle(10, 70, 100, 20));
		laTLabel.setText(LabelText.TTEXT);
		laYLabel = new JLabel();
		laYLabel.setBounds(new Rectangle(10, 40, 100, 20));
		laYLabel.setText(LabelText.YTEXT);
		laXLabel = new JLabel();
		laXLabel.setBounds(new Rectangle(10, 10, 100, 20));
		laXLabel.setText(LabelText.XTEXT);

		laXTextField = new JTextField();
		laXTextField.setBounds(new Rectangle(120, 9, 300, 22));
		laXTextField.setToolTipText(Prompt.X_TIP);
		laYTextField = new JTextField();
		laYTextField.setBounds(new Rectangle(120, 39, 300, 22));
		laYTextField.setToolTipText(Prompt.Y_TIP);
		laTTextField = new JTextField();
		laTTextField.setBounds(new Rectangle(120, 69, 150, 22));
		laTTextField.setToolTipText(Prompt.T_TIP);
		laResultTextField = new JTextField();
		laResultTextField.setBounds(new Rectangle(120, 129, 150, 22));
		laResultTextField.setEditable(false);

		laCalcButton = new JButton();
		laCalcButton.setBounds(new Rectangle(100, 100, 60, 20));
		laCalcButton.setText("运算");
		laCalcButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				calcLaValue();
			}
		});
		laClearButton = new JButton();
		laClearButton.setBounds(new Rectangle(250, 100, 60, 20));
		laClearButton.setText("清除");
		laClearButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				clearLa();
			}
		});

		this.setSize(430, 160);
		this.setLayout(null);
		this.setVisible(true);
		this.add(laXLabel, null);
		this.add(laXTextField, null);
		this.add(laYLabel, null);
		this.add(laYTextField, null);
		this.add(laTLabel, null);
		this.add(laTTextField, null);
		this.add(laCalcButton, null);
		this.add(laClearButton, null);
		this.add(laResultLabel, null);
		this.add(laResultTextField, null);
	}
}
