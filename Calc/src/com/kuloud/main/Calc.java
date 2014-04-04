package com.kuloud.main;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.SwingUtilities;
import java.awt.Point;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JDialog;
import java.awt.Toolkit;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

import com.kuloud.contents.Details;
import com.kuloud.contents.InterName;
import com.kuloud.contents.Prompt;

public class Calc {

	/**
     * 启动程序
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable()  {
            @Override
            public void run() {
                Calc application = new Calc();
                application.getCalcFrame().setVisible(true);
            }
        });
    }
    
    private ButtonGroup ButtonGroup = new ButtonGroup();
    private JFrame CalcFrame = null;
    private JPanel mainContentPanel = null;
    private JMenuBar mainJMenuBar = null;
    private JMenu fileMenu = null;
    private JMenu viewMenu = null;
    private JMenu helpMenu = null;
    private JMenuItem exitMenuItem = null;
    private JMenuItem aboutMenuItem = null;
    private JMenu interMenu = null;
    private JRadioButtonMenuItem laRadioButtonMenuItem = null;
    private JRadioButtonMenuItem neRadioButtonMenuItem = null;
    private JRadioButtonMenuItem heRadioButtonMenuItem = null;
    private JRadioButtonMenuItem liRadioButtonMenuItem = null;
    private JRadioButtonMenuItem spRadioButtonMenuItem = null;
    private JLabel welcomeLabel = null;
    private JMenuItem integMenuItem = null;
	private JMenuItem getAboutMenuItem() {
        if (aboutMenuItem == null) {
            aboutMenuItem = new JMenuItem();
            aboutMenuItem.setText("关于");
            aboutMenuItem.addActionListener(new ActionListener()  {

                @Override
				public void actionPerformed(ActionEvent e) {
                    JDialog aboutDialog = new AboutDialog(CalcFrame);
                    aboutDialog.pack();
                    Point loc = getCalcFrame().getLocation();
                    loc.translate(20, 20);
                    aboutDialog.setLocation(loc);
                    aboutDialog.setVisible(true);
                }
            });
        }
        return aboutMenuItem;
    }

    private JFrame getCalcFrame() {
        if (CalcFrame == null) {
            CalcFrame = new JFrame();
            CalcFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            CalcFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(
                    getClass().getResource(
                    "/Calculator.png")));
            CalcFrame.setResizable(false);
            CalcFrame.setJMenuBar(getMainJMenuBar());
            CalcFrame.setSize(300, 200);
            CalcFrame.setContentPane(getMainContentPanel());
            CalcFrame.setTitle("计算器");
            int w = Details.SCREENWIDTH / 2 - 150;
            int h = Details.SCREENHEIGHT / 2 - 100;
            CalcFrame.setLocation(w, h);
        }
        return CalcFrame;
    }

    private JMenu getViewMenu() {
        if (viewMenu == null) {
            viewMenu = new JMenu();
            viewMenu.setText("查看");
            viewMenu.add(getInterMenu());
            viewMenu.add(getIntegMenuItem());
        }
        return viewMenu;
    }

    private JMenuItem getExitMenuItem() {
        if (exitMenuItem == null) {
            exitMenuItem = new JMenuItem();
            exitMenuItem.setText("退出");
            exitMenuItem.addActionListener(new ActionListener()  {
                @Override
				public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
        }
        return exitMenuItem;
    }

    private JMenu getFileMenu() {
        if (fileMenu == null) {
            fileMenu = new JMenu();
            fileMenu.setText("文件");
            fileMenu.add(getExitMenuItem());
        }
        return fileMenu;
    }

    private JMenu getHelpMenu() {
        if (helpMenu == null) {
            helpMenu = new JMenu();
            helpMenu.setText("帮助");
            helpMenu.add(getAboutMenuItem());
        }
        return helpMenu;
    }

    private JRadioButtonMenuItem getHeRadioButtonMenuItem() {
        if (heRadioButtonMenuItem == null) {
            heRadioButtonMenuItem = new JRadioButtonMenuItem();
            heRadioButtonMenuItem.setText(InterName.埃尔米特插值.name());
            heRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener()  {

                @Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
                    getSelectedPanel(InterName.埃尔米特插值, mainContentPanel);
                }
            });
        }
        return heRadioButtonMenuItem;
    }

    private JMenu getInterMenu() {
        if (interMenu == null) {
            interMenu = new JMenu();
            interMenu.setText("插值法");
            interMenu.add(getLaRadioButtonMenuItem());
            interMenu.add(getNeRadioButtonMenuItem());
            interMenu.add(getHeRadioButtonMenuItem());
            interMenu.add(getLiRadioButtonMenuItem());
            interMenu.add(getSpRadioButtonMenuItem());

            ButtonGroup.add(laRadioButtonMenuItem);
            ButtonGroup.add(neRadioButtonMenuItem);
            ButtonGroup.add(heRadioButtonMenuItem);
            ButtonGroup.add(liRadioButtonMenuItem);
            ButtonGroup.add(spRadioButtonMenuItem);
        }
        return interMenu;
    }

    private JRadioButtonMenuItem getLaRadioButtonMenuItem() {
        if (laRadioButtonMenuItem == null) {
            laRadioButtonMenuItem = new JRadioButtonMenuItem();
            laRadioButtonMenuItem.setText(InterName.拉格朗日插值.name());
            laRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener()  {
				public void actionPerformed(java.awt.event.ActionEvent e) {
                    getSelectedPanel(InterName.拉格朗日插值, mainContentPanel);
                }
            });
        }
        return laRadioButtonMenuItem;
    }

    private JRadioButtonMenuItem getLiRadioButtonMenuItem() {
        if (liRadioButtonMenuItem == null) {
            liRadioButtonMenuItem = new JRadioButtonMenuItem();
            liRadioButtonMenuItem.setText(InterName.分段低次插值.name());
            liRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener()  {
				public void actionPerformed(java.awt.event.ActionEvent e) {
                    getSelectedPanel(InterName.分段低次插值, mainContentPanel);
                }
            });
        }
        return liRadioButtonMenuItem;
    }

    private JPanel getMainContentPanel() {
        if (mainContentPanel == null) {
            welcomeLabel = new JLabel();
            welcomeLabel.setIcon(new ImageIcon(getClass().getResource("/Prompt.png")));
            welcomeLabel.setText(Details.WELCOME);
            welcomeLabel.setToolTipText(Prompt.WELCOME_TIP);
            welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
            mainContentPanel = new JPanel();
            mainContentPanel.setLayout(new BorderLayout());
            mainContentPanel.add(welcomeLabel, BorderLayout.CENTER);
        }
        return mainContentPanel;
    }

    private JMenuBar getMainJMenuBar() {
        if (mainJMenuBar == null) {
            mainJMenuBar = new JMenuBar();
            mainJMenuBar.add(getFileMenu());
            mainJMenuBar.add(getViewMenu());
            mainJMenuBar.add(getHelpMenu());
        }
        return mainJMenuBar;
    }

    private JRadioButtonMenuItem getNeRadioButtonMenuItem() {
        if (neRadioButtonMenuItem == null) {
            neRadioButtonMenuItem = new JRadioButtonMenuItem();
            neRadioButtonMenuItem.setText(InterName.牛顿插值.name());
            neRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener()  {
				public void actionPerformed(java.awt.event.ActionEvent e) {
                    getSelectedPanel(InterName.牛顿插值, mainContentPanel);
                }
            });
        }
        return neRadioButtonMenuItem;
    }

    private JRadioButtonMenuItem getSpRadioButtonMenuItem() {
        if (spRadioButtonMenuItem == null) {
            spRadioButtonMenuItem = new JRadioButtonMenuItem();
            spRadioButtonMenuItem.setText(InterName.三次样条插值.name());
            spRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener()  {
				public void actionPerformed(java.awt.event.ActionEvent e) {
                    getSelectedPanel(InterName.三次样条插值, mainContentPanel);
                }
            });
        }
        return spRadioButtonMenuItem;
    }

	private JMenuItem getIntegMenuItem() {
        if (integMenuItem == null) {
            integMenuItem = new JMenuItem();
            integMenuItem.setText("积分法");
            integMenuItem.addActionListener(new java.awt.event.ActionListener()  {
				public void actionPerformed(java.awt.event.ActionEvent e) {
                    showIntegPanelOn(mainContentPanel);
                    ButtonGroup.clearSelection();
                }
            });
        }
        return integMenuItem;
    }

    protected void showIntegPanelOn(JPanel owner) {
    	owner.removeAll();
    	owner.add(getIntegPanel());
    }

    protected void getSelectedPanel(InterName name, JPanel owner) {
        owner.removeAll();
        switch (name) {
            case 拉格朗日插值:
                owner.add(getLaPanel());
                break;
            case 牛顿插值:
                owner.add(getNePanel());
                break;
            case 埃尔米特插值:
                owner.add(getHePanel());
                break;
            case 分段低次插值:
                owner.add(getLiPanel());
                break;
            case 三次样条插值:
                owner.add(getSpPanel());
                break;
            default:
                break;
        }
    }

    private JPanel getIntegPanel() {
        CalcFrame.setTitle("计算器-积分法");
        CalcFrame.setSize(420, 245);
        return new IntegPanel();
    }

    private JPanel getSpPanel() {
        CalcFrame.setTitle("计算器-插值法-" + InterName.三次样条插值.name());
        CalcFrame.setSize(440, 265);
        return new SpPanel();
    }

    private JPanel getLiPanel() {
        CalcFrame.setTitle("计算器-插值法-" + InterName.分段低次插值.name());
        CalcFrame.setSize(440, 215);
        return new LiPanel();
    }

    private JPanel getLaPanel() {
        CalcFrame.setTitle("计算器-插值法-" + InterName.拉格朗日插值.name());
        CalcFrame.setSize(440, 215);
        return new LaPanel();
    }

    private JPanel getNePanel() {
        CalcFrame.setTitle("计算器-插值法-" + InterName.牛顿插值.name());
        CalcFrame.setSize(440, 215);
        return new NePanel();
    }

    private JPanel getHePanel() {
        CalcFrame.setTitle("计算器-插值法-" + InterName.埃尔米特插值.name());
        CalcFrame.setSize(440, 245);
        return new HePanel();
    }
}
