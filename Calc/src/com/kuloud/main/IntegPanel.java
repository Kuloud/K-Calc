package com.kuloud.main;

import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.kuloud.algorithms.CalculateString;
import com.kuloud.algorithms.CheckString;
import com.kuloud.algorithms.Integration;
import com.kuloud.contents.Details;
import com.kuloud.contents.IntegName;
import com.kuloud.contents.LabelText;
import com.kuloud.contents.Prompt;

public class IntegPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel integResultLabel;
	private JLabel accuracyLabel;
	private JLabel methodsLabel;
	private JLabel topLabel;
	private JLabel lowLabel;
	private JLabel funcLabel;
	private JComboBox accuracyComboBox;
	private JButton integCalcButton;
	private JButton integClearButton;
	private JTextField integResultTextField;
	private JTextField lowTextField;
	private JComboBox methodsComboBox;
	private JTextField funcTextField;
	private JTextField topTextField;

	public IntegPanel() {
		super();
		initialize();
	}

	private void initialize() {
		integResultLabel = new JLabel();
		integResultLabel.setBounds(new Rectangle(10, 160, 70, 20));
		integResultLabel.setText(LabelText.RESULTTEXT);
		accuracyLabel = new JLabel();
		accuracyLabel.setBounds(new Rectangle(230, 100, 70, 20));
		accuracyLabel.setText("精确度：");
		methodsLabel = new JLabel();
		methodsLabel.setBounds(new Rectangle(10, 100, 70, 18));
		methodsLabel.setText("积分法：");
		topLabel = new JLabel();
		topLabel.setBounds(new Rectangle(10, 70, 70, 20));
		topLabel.setText("积分上限：");
		lowLabel = new JLabel();
		lowLabel.setBounds(new Rectangle(10, 40, 70, 20));
		lowLabel.setText("积分下限：");
		funcLabel = new JLabel();
		funcLabel.setText("被积函数：");
		funcLabel.setBounds(new Rectangle(10, 10, 70, 20));

		funcTextField = new JTextField();
		funcTextField.setBounds(new Rectangle(90, 9, 310, 22));
		funcTextField.setToolTipText(Prompt.FUNC_TIP);
		lowTextField = new JTextField();
		lowTextField.setBounds(new Rectangle(90, 39, 150, 22));
		lowTextField.setToolTipText(Prompt.MIN_TIP);
		topTextField = new JTextField();
		topTextField.setBounds(new Rectangle(90, 69, 150, 22));
		topTextField.setToolTipText(Prompt.MAX_TIP);
		integResultTextField = new JTextField();
		integResultTextField.setBounds(new Rectangle(90, 159, 150, 22));
		integResultTextField.setEditable(false);

		methodsComboBox = new JComboBox();
		methodsComboBox.setBounds(new Rectangle(90, 99, 120, 22));
		methodsComboBox.setModel(new javax.swing.DefaultComboBoxModel(IntegName
				.toStrArray()));
		accuracyComboBox = new JComboBox();
		accuracyComboBox.setBounds(new Rectangle(300, 99, 100, 22));
		accuracyComboBox.setEditable(true);
		accuracyComboBox.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "1e-4", "1e-6", "1e-8" }));

		integCalcButton = new JButton();
		integCalcButton.setBounds(new Rectangle(100, 130, 60, 20));
		integCalcButton.setText(LabelText.CALC);
		integCalcButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				calcIntegValue();
			}
		});
		integClearButton = new JButton();
		integClearButton.setBounds(new Rectangle(250, 130, 60, 20));
		integClearButton.setText(LabelText.CLEAR);
		integClearButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				clearInteg();
			}
		});

		this.setSize(410, 190);
		this.setLayout(null);
		this.add(funcLabel, null);
		this.add(funcTextField, null);
		this.add(lowLabel, null);
		this.add(lowTextField, null);
		this.add(topLabel, null);
		this.add(topTextField, null);
		this.add(methodsLabel, null);
		this.add(methodsComboBox, null);
		this.add(accuracyLabel, null);
		this.add(accuracyComboBox, null);
		this.add(integCalcButton, null);
		this.add(integClearButton, null);
		this.add(integResultLabel, null);
		this.add(integResultTextField, null);
	}

	protected void clearInteg() {
		funcTextField.setText("");
		lowTextField.setText("");
		topTextField.setText("");
		methodsComboBox.setSelectedIndex(0);
		accuracyComboBox.setSelectedIndex(0);
		integResultTextField.setText("");
	}
	
	protected void calcIntegValue() {
		double a = 0.0, b = 0.0;
		double eps = 1e-4;
		double result = 0.0;

		try {
			a = Double.parseDouble(lowTextField.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "积分下限输入错误，请检查重输。");
			lowTextField.requestFocus();
			lowTextField.selectAll();
		}

		try {
			b = Double.parseDouble(topTextField.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "积分上限输入错误，请检查重输。");
			topTextField.requestFocus();
			topTextField.selectAll();
		}

		try {
			eps = Double.parseDouble(accuracyComboBox.getSelectedItem()
					.toString());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "精确度输入错误，请检查重输。");
			accuracyComboBox.requestFocus();
			accuracyComboBox.setSelectedIndex(0);
		}
		
		Details.ex = funcTextField.getText().replaceAll("\\s+", "");
		if (!CheckString.isFuncRight(Details.ex)) {
			JOptionPane.showMessageDialog(null, "函数输入错误，请检查重输。");
			funcTextField.requestFocus();
			funcTextField.selectAll();
		} else {
			run r = new run();
			switch (methodsComboBox.getSelectedIndex()) {
			case 0:
				result = r.getValueTrapezia(a, b, eps);
				break;
			case 1:
				result = r.getValueSimpson(a, b, eps);
				break;
			case 2:
				result = r.getValueRomberg(a, b, eps);
				break;
			case 3:
				result = r.getValueATrapezia(a, b, 0.0001, eps);
				break;
			case 4:
				result = r.getValueLegdGauss(a, b, eps);
				break;
			}
		}
		integResultTextField.setText(result + "");
	}

	class run extends Integration {
		public double func(double x) {
			double result = new CalculateString().calculateString(Details.ex, x);
			return result;
		}
	}
}
