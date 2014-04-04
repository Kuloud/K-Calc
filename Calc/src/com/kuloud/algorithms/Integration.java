package com.kuloud.algorithms;

/**
 * 计算数值积分的类 Integration
 * 
 * @author 肖中中
 * @version 1.0
 */
public abstract class Integration {
	/**
	 * 抽象函数：计算积分函数值，必须在派生类中覆盖该函数
	 * 
	 * @param x
	 *            - 函数变量
	 * @return double型，对应的函数值
	 */
	public abstract double func(double x);

	/**
	 * 基本构造函数
	 */
	public Integration() {
	}

	/**
	 * 变步长梯形求积法
	 * 
	 * 调用时，须覆盖计算函数f(x)值的虚函数double func(double x)
	 * 
	 * @param a
	 *            - 积分下限
	 * @param b
	 *            - 积分上限，要求b>a
	 * @param eps
	 *            - 积分精度要求
	 * @return double 型，积分值
	 */
	public double getValueTrapezia(double a, double b, double eps) {
		int n, k;
		double fa, fb, h, t1, p, s, x, t = 0.0;

		// 积分区间端点的函数值
		fa = func(a);
		fb = func(b);

		// 迭代初值
		n = 1;
		h = b - a;
		t1 = h * (fa + fb) * 0.5;
		p = eps + 1.0;

		// 迭代计算
		while (p >= eps) {
			s = 0.0;
			for (k = 0; k <= n - 1; k++) {
				x = a + (k + 0.5) * h;
				s = s + func(x);
			}

			t = (t1 + h * s) * 0.5;
			p = Math.abs(t1 - t);
			t1 = t;
			n = n + n;
			h = h * 0.5;
		}

		return (t);
	}

	/**
	 * 变步长辛卜生求积法
	 * 
	 * 调用时，须覆盖计算函数f(x)值的虚函数double func(double x)
	 * 
	 * @param a
	 *            - 积分下限
	 * @param b
	 *            - 积分上限，要求b>a
	 * @param eps
	 *            - 积分精度要求
	 * @return double 型，积分值
	 */
	public double getValueSimpson(double a, double b, double eps) {
		int n, k;
		double h, t1, t2, s1, s2 = 0, ep, p, x;

		// 计算初值
		n = 1;
		h = b - a;
		t1 = h * (func(a) + func(b)) * 0.5;
		s1 = t1;
		ep = eps + 1.0;

		// 迭代计算
		while (ep >= eps) {
			p = 0.0;
			for (k = 0; k <= n - 1; k++) {
				x = a + (k + 0.5) * h;
				p = p + func(x);
			}

			t2 = (t1 + h * p) * 0.5;
			s2 = (4.0 * t2 - t1) / 3.0;
			ep = Math.abs(s2 - s1);
			t1 = t2;
			s1 = s2;
			n = n + n;
			h = h * 0.5;
		}

		return (s2);
	}

	/**
	 * 自适应梯形求积法
	 * 
	 * 调用时，须覆盖计算函数f(x)值的虚函数double func(double x)
	 * 
	 * @param a
	 *            - 积分下限
	 * @param b
	 *            - 积分上限，要求b>a
	 * @param d
	 *            - 对积分区间进行分割的最小步长，当子区间的宽度 小于d时，即使没有满足精度要求，也不再往下进行分割
	 * @param eps
	 *            - 积分精度要求
	 * @return double 型，积分值
	 */
	public double getValueATrapezia(double a, double b, double d, double eps) {
		double h, f0, f1, t0, z;
		double[] t = new double[2];

		// 迭代初值
		h = b - a;
		t[0] = 0.0;
		f0 = func(a);
		f1 = func(b);
		t0 = h * (f0 + f1) * 0.5;

		// 递归计算
		ppp(a, b, h, f0, f1, t0, eps, d, t);

		z = t[0];

		return (z);
	}

	/**
	 * 内部函数
	 */
	private void ppp(double x0, double x1, double h, double f0, double f1,
			double t0, double eps, double d, double[] t) {
		double x, f, t1, t2, p, g, eps1;

		x = x0 + h * 0.5;
		f = func(x);
		t1 = h * (f0 + f) * 0.25;
		t2 = h * (f + f1) * 0.25;
		p = Math.abs(t0 - (t1 + t2));

		if ((p < eps) || (h < 2.0 * d)) {
			t[0] = t[0] + (t1 + t2);
			return;
		} else {
			g = h * 0.5;
			eps1 = eps / 1.4;
			// 递归
			ppp(x0, x, g, f0, f, t1, eps1, d, t);
			ppp(x, x1, g, f, f1, t2, eps1, d, t);
			return;
		}
	}

	/**
	 * 龙贝格求积法
	 * 
	 * 调用时，须覆盖计算函数f(x)值的虚函数double func(double x)
	 * 
	 * @param a
	 *            - 积分下限
	 * @param b
	 *            - 积分上限，要求b>a
	 * @param eps
	 *            - 积分精度要求
	 * @return double 型，积分值
	 */
	public double getValueRomberg(double a, double b, double eps) {
		int m, n, i, k;
		double h, ep, p, x, s, q = 0;
		double[] y = new double[10];

		// 迭代初值
		h = b - a;
		y[0] = h * (func(a) + func(b)) * 0.5;
		m = 1;
		n = 1;
		ep = eps + 1.0;

		// 迭代计算
		while ((ep >= eps) && (m <= 9)) {
			p = 0.0;
			for (i = 0; i <= n - 1; i++) {
				x = a + (i + 0.5) * h;
				p = p + func(x);
			}

			p = (y[0] + h * p) * 0.5;
			s = 1.0;
			for (k = 1; k <= m; k++) {
				s = 4.0 * s;
				q = (s * p - y[k - 1]) / (s - 1.0);
				y[k - 1] = p;
				p = q;
			}

			ep = Math.abs(q - y[m - 1]);
			m = m + 1;
			y[m - 1] = q;
			n = n + n;
			h = h * 0.5;
		}

		return (q);
	}

	/**
	 * 勒让德－高斯求积法
	 * 
	 * 调用时，须覆盖计算函数f(x)值的虚函数double func(double x)
	 * 
	 * @param a
	 *            - 积分下限
	 * @param b
	 *            - 积分上限，要求b>a
	 * @param eps
	 *            - 积分精度要求
	 * @return double 型，积分值
	 */
	public double getValueLegdGauss(double a, double b, double eps) {
		int m, i, j;
		double s, p, ep, h, aa, bb, w, x, g = 0;

		// 勒让德－高斯求积系数
		double[] t = { -0.9061798459, -0.5384693101, 0.0, 0.5384693101,
				0.9061798459 };
		double[] c = { 0.2369268851, 0.4786286705, 0.5688888889, 0.4786286705,
				0.2369268851 };

		// 迭代初值
		m = 1;
		h = b - a;
		s = Math.abs(0.001 * h);
		p = 1.0e+35;
		ep = eps + 1.0;

		// 迭代计算
		while ((ep >= eps) && (Math.abs(h) > s)) {
			g = 0.0;
			for (i = 1; i <= m; i++) {
				aa = a + (i - 1.0) * h;
				bb = a + i * h;
				w = 0.0;

				for (j = 0; j <= 4; j++) {
					x = ((bb - aa) * t[j] + (bb + aa)) * 0.5;
					w = w + func(x) * c[j];
				}

				g = g + w;
			}

			g = g * h * 0.5;
			ep = Math.abs(g - p) / (1.0 + Math.abs(g));
			p = g;
			m = m + 1;
			h = (b - a) / m;
		}

		return (g);
	}
}
