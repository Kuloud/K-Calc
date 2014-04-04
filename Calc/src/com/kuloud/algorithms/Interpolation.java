package com.kuloud.algorithms;

/**
 * 进行插值的类Interpolation
 * 
 * @author 肖中中
 * @version 1.0
 */
public class Interpolation {
	/**
	 * 一元不等距拉格朗日插值
	 * 
	 * @param n
	 *            - 结点的个数
	 * @param x
	 *            - 一维数组，长度为n，存放给定的n个结点的值x(i)， 要求x(0)<x(1)<...<x(n-1)
	 * @param y
	 *            - 一维数组，长度为n，存放给定的n个结点的函数值y(i)， y(i) = f(x(i)), i=0,1,...,n-1
	 * @param t
	 *            - 存放指定的插值点的值
	 * @return double 型，指定的查指点t的函数近似值f(t)
	 */
	public static double getValueLagrange(int n, double[] x, double[] y,
			double t) {
		int i, j, k, m;
		double z, s;

		// 初值
		z = 0.0;

		// 特例处理
		if (n < 1)
			return (z);
		if (n == 1) {
			z = y[0];
			return (z);
		}

		if (n == 2) {
			z = (y[0] * (t - x[1]) - y[1] * (t - x[0])) / (x[0] - x[1]);
			return (z);
		}

		// 一般情况，开始插值
		i = 0;
		while ((x[i] < t) && (i < n))
			i = i + 1;

		// 为在可允许误差范围内简化运算，只运算插值点左右附近三个点
		k = i - 4;
		if (k < 0)
			k = 0;
		m = i + 3;
		if (m > n - 1)
			m = n - 1;

		for (i = k; i <= m; i++) {
			s = 1.0;
			for (j = k; j <= m; j++) {
				if (j != i)
					// 拉格朗日插值公式
					s = s * (t - x[j]) / (x[i] - x[j]);
			}

			z = z + s * y[i];
		}

		return (z);
	}

	/**
	 * 一元等距拉格朗日插值
	 * 
	 * @param n
	 *            - 结点的个数
	 * @param x0
	 *            - 存放等距n个结点中第一个结点的值
	 * @param xStep
	 *            - 等距结点的步长
	 * @param y
	 *            - 一维数组，长度为n，存放给定的n个结点的函数值y(i)， y(i) = f(x(i)), i=0,1,...,n-1
	 * @param t
	 *            - 存放指定的插值点的值
	 * @return double 型，指定的查指点t的函数近似值f(t)
	 */
	public static double getValueLagrange(int n, double x0, double xStep,
			double[] y, double t) {
		int i, j, k, m;
		double z, s, xi, xj;
		double p, q;

		// 初值
		z = 0.0;

		// 特例处理
		if (n < 1)
			return (z);
		if (n == 1) {
			z = y[0];
			return (z);
		}
		if (n == 2) {
			z = (y[1] * (t - x0) - y[0] * (t - x0 - xStep)) / xStep;
			return (z);
		}

		// 开始插值
		if (t > x0) {
			p = (t - x0) / xStep;
			i = (int) p;
			q = (float) i;

			if (p > q)
				i = i + 1;
		} else
			i = 0;

		// 为在可允许误差范围内简化运算，只运算插值点左右附近三个点
		k = i - 4;
		if (k < 0)
			k = 0;
		m = i + 3;
		if (m > n - 1)
			m = n - 1;

		for (i = k; i <= m; i++) {
			s = 1.0;
			xi = x0 + i * xStep;

			for (j = k; j <= m; j++) {
				if (j != i) {
					xj = x0 + j * xStep;
					// 拉格朗日插值公式
					s = s * (t - xj) / (xi - xj);
				}
			}

			z = z + s * y[i];
		}

		return (z);
	}

	/**
	 * 不等距埃尔米特插值
	 * 
	 * @param n
	 *            - 结点的个数
	 * @param x
	 *            - 一维数组，长度为n，存放给定的n个结点的值x(i)
	 * @param y
	 *            - 一维数组，长度为n，存放给定的n个结点的函数值y(i)， y(i) = f(x(i)), i=0,1,...,n-1
	 * @param dy
	 *            - 一维数组，长度为n，存放给定的n个结点的函数导数值y'(i)， y'(i) = f'(x(i)),
	 *            i=0,1,...,n-1
	 * @param t
	 *            - 存放指定的插值点的值
	 * @return double 型，指定的查指点t的函数近似值f(t)
	 */
	public static double getValueHermite(int n, double[] x, double[] y,
			double[] dy, double t) {
		int i, j;
		double z, p, q, s;

		// 初值
		z = 0.0;

		// 循环插值
		for (i = 1; i <= n; i++) {
			s = 1.0;

			for (j = 1; j <= n; j++) {
				if (j != i)
					s = s * (t - x[j - 1]) / (x[i - 1] - x[j - 1]);
			}

			s = s * s;
			p = 0.0;

			for (j = 1; j <= n; j++) {
				if (j != i)
					p = p + 1.0 / (x[i - 1] - x[j - 1]);
			}

			q = y[i - 1] + (t - x[i - 1]) * (dy[i - 1] - 2.0 * y[i - 1] * p);
			z = z + q * s;
		}

		return (z);
	}

	/**
	 * 等距埃尔米特插值
	 * 
	 * @param n
	 *            - 结点的个数
	 * @param x0
	 *            - 存放等距n个结点中第一个结点的值
	 * @param xStep
	 *            - 等距结点的步长
	 * @param y
	 *            - 一维数组，长度为n，存放给定的n个结点的函数值y(i)， y(i) = f(x(i)), i=0,1,...,n-1
	 * @param dy
	 *            - 一维数组，长度为n，存放给定的n个结点的函数导数值y'(i)， y'(i) = f'(x(i)),
	 *            i=0,1,...,n-1
	 * @param t
	 *            - 存放指定的插值点的值
	 * @return double 型，指定的查指点t的函数近似值f(t)
	 */
	public static double getValueHermite(int n, double x0, double xStep,
			double[] y, double[] dy, double t) {
		int i, j;
		double z, s, p, q;

		// 初值
		z = 0.0;

		// 循环插值
		for (i = 1; i <= n; i++) {
			s = 1.0;
			q = x0 + (i - 1) * xStep;

			for (j = 1; j <= n; j++) {
				p = x0 + (j - 1) * xStep;
				if (j != i)
					s = s * (t - p) / (q - p);
			}

			s = s * s;
			p = 0.0;

			for (j = 1; j <= n; j++) {
				if (j != i)
					p = p + 1.0 / (q - (x0 + (j - 1) * xStep));
			}

			q = y[i - 1] + (t - q) * (dy[i - 1] - 2.0 * y[i - 1] * p);
			z = z + q * s;
		}

		return (z);
	}

	/**
	 * 第一种边界条件的三次样条函数插值
	 * 
	 * @param n
	 *            - 节点的个数
	 * @param x
	 *            - 一维数组，长度为n，存放给定的n个结点的值x(i)，n = 0,1,...,n-1
	 * @param y
	 *            - 一维数组，长度为n，存放给定的n个结点的函数值y(i)， y(i) = f(x(i)), i=0,1,...,n-1
	 * @param f1
	 *            - x'(0)的值
	 * @param f2
	 *            - x'(n-1)的值
	 * @param t
	 *            - 存放指定的插值点的值
	 * @return double 型，指定的查指点t的函数近似值f(t)
	 */
	public static double getValueSpline1(int n, double[] x, double[] y,
			double f1, double f2, double t) {
		int i;
		double temp;
		double result;
		double[] a = new double[n];
		double[] m = new double[n];
		double[] d = new double[n];
		double[] r = new double[n - 1];
		double[] u = new double[n - 1];
		double[] h = new double[n - 1];
		double[] c = new double[n - 1];

		for (i = 0; i < n - 1; i++) {
			a[i] = 2.0;
			h[i] = x[i + 1] - x[i];
			c[i] = (y[i + 1] - y[i]) / h[i];
		}
		a[n - 1] = 2.0;
		r[0] = u[n - 2] = 1.0;
		for (i = 0; i < n - 2; i++) {
			u[i] = h[i] / (h[i] + h[i + 1]);
			r[i + 1] = 1 - u[i];
			d[i + 1] = 6 * (c[i + 1] - c[i]) / (h[i + 1] + h[i]);
		}
		d[0] = 6 * (c[0] - f1) / h[0];
		d[n - 1] = 6 * (f2 - c[n - 2]) / h[n - 2];

		for (i = 0; i < n - 1; i++) {
			temp = -u[i] / a[i];
			a[i + 1] += r[i] * temp;
			d[i + 1] += d[i] * temp;
		}
		for (i = n - 2; i >= 0; i--) {
			d[i] -= d[i + 1] * r[i] / a[i + 1];
			m[i] = d[i] / a[i];
		}
		m[n - 1] = d[n - 1] / a[n - 1];
		i = n - 1;
		while (t < x[i])
			i--;
		double a1 = x[i + 1] - t, a2 = t - x[i];
		result = spE(m[i], a1, m[i + 1], a2, y[i], y[i + 1], h[i]);
		return result;
	}

	/**
	 * 第一种边界条件的三次样条函数插值
	 * 
	 * @param n
	 *            - 节点的个数
	 * @param x
	 *            - 一维数组，长度为n，存放给定的n个结点的值x(i)，n = 0,1,...,n-1
	 * @param y
	 *            - 一维数组，长度为n，存放给定的n个结点的函数值y(i)， y(i) = f(x(i)), i=0,1,...,n-1
	 * @param f1
	 *            - S'(0)的值
	 * @param f2
	 *            - S'(n-1)的值
	 * @param t
	 *            - 存放指定的插值点的值
	 * @return double 型，指定的查指点t的函数近似值f(t)
	 */
	public static double getValueSpline1(int n, double x0, double xStep,
			double[] y, double f1, double f2, double t) {
		int i;
		double temp;
		double result;
		double[] a = new double[n];
		double[] m = new double[n];
		double[] d = new double[n];
		double[] r = new double[n - 1];
		double[] u = new double[n - 1];
		double[] c = new double[n - 1];

		for (i = 0; i < n - 1; i++) {
			a[i] = 2.0;
			c[i] = (y[i + 1] - y[i]) / xStep;
		}
		a[n - 1] = 2.0;
		r[0] = u[n - 2] = 1.0;
		for (i = 0; i < n - 2; i++) {
			u[i] = 0.5;
			r[i + 1] = 0.5;
			d[i + 1] = 3 * (c[i + 1] - c[i]) / xStep;
		}
		d[0] = 6 * (c[0] - f1) / xStep;
		d[n - 1] = 6 * (f2 - c[n - 2]) / xStep;

		for (i = 0; i < n - 1; i++) {
			temp = -u[i] / a[i];
			a[i + 1] += r[i] * temp;
			d[i + 1] += d[i] * temp;
		}
		for (i = n - 2; i >= 0; i--) {
			d[i] -= d[i + 1] * r[i] / a[i + 1];
			m[i] = d[i] / a[i];
		}
		m[n - 1] = d[n - 1] / a[n - 1];
		i = n - 1;
		while (t < x0 + i * xStep) {
			i--;
		}

		double a1 = x0 + (i + 1) * xStep - t, a2 = t - x0 - i * xStep;
		result = (m[i] * a1 * a1 * a1 + m[i + 1] * a2 * a2 * a2
				+ (6 * y[i] - m[i] * xStep * xStep) * a1 + (6 * y[i + 1] - m[i + 1]
				* xStep * xStep)
				* a2)
				/ 6 / xStep;

		return result;
	}

	/**
	 * 第二种边界条件的三次样条函数插值
	 * 
	 * @param n
	 *            - 结点的个数
	 * @param x
	 *            - 一维数组，长度为n，存放给定的n个结点的值x(i)
	 * @param y
	 *            - 一维数组，长度为n，存放给定的n个结点的函数值y(i)， y(i) = f(x(i)), i=0,1,...,n-1
	 * @param f1
	 *            - S"(0)的值
	 * @param f2
	 *            - S"(n-1)的值
	 * @param t
	 *            - 存放指定的插值点的值
	 * @return double 型，指定的查指点t的函数近似值f(t)
	 */
	public static double getValueSpline2(int n, double[] x, double[] y,
			double f1, double f2, double t) {
		int i;
		double temp;
		double result;
		double[] a = new double[n];
		double[] m = new double[n];
		double[] d = new double[n];
		double[] r = new double[n - 1];
		double[] u = new double[n - 1];
		double[] h = new double[n - 1];
		double[] c = new double[n - 1];

		for (i = 0; i < n - 1; i++) {
			a[i] = 2.0;
			h[i] = x[i + 1] - x[i];
			c[i] = (y[i + 1] - y[i]) / h[i];
		}
		a[n - 1] = 2.0;
		r[0] = u[n - 2] = 0.0;
		for (i = 0; i < n - 2; i++) {
			u[i] = h[i] / (h[i] + h[i + 1]);
			r[i + 1] = 1 - u[i];
			d[i + 1] = 6 * (c[i + 1] - c[i]) / (h[i + 1] + h[i]);
		}
		d[0] = f1 + f1;
		d[n - 1] = f2 + f2;

		for (i = 0; i < n - 1; i++) {
			temp = -u[i] / a[i];
			a[i + 1] += r[i] * temp;
			d[i + 1] += d[i] * temp;
		}

		m[0] = f1;
		m[n - 1] = f2;
		for (i = n - 2; i > 0; i--) {
			d[i] -= d[i + 1] * r[i] / a[i + 1];
			m[i] = d[i] / a[i];
		}

		i = n - 1;
		while (t < x[i])
			i--;
		double a1 = x[i + 1] - t, a2 = t - x[i];
		result = spE(m[i], a1, m[i + 1], a2, y[i], y[i + 1], h[i]);
		return result;
	}

	public static double getValueSpline2(int n, double x, double xStep,
			double[] y, double f1, double f2, double t) {
		int i;
		double temp;
		double result;
		double[] a = new double[n];
		double[] m = new double[n];
		double[] d = new double[n];
		double[] r = new double[n - 1];
		double[] u = new double[n - 1];
		double[] c = new double[n - 1];

		for (i = 0; i < n - 1; i++) {
			a[i] = 2.0;
			c[i] = (y[i + 1] - y[i]) / xStep;
		}
		a[n - 1] = 2.0;
		r[0] = u[n - 2] = 0.0;
		for (i = 0; i < n - 2; i++) {
			u[i] = 0.5;
			r[i + 1] = 0.5;
			d[i + 1] = 3 * (c[i + 1] - c[i]) / xStep;
		}
		d[0] = f1 + f1;
		d[n - 1] = f2 + f2;

		for (i = 0; i < n - 1; i++) {
			temp = -u[i] / a[i];
			a[i + 1] += r[i] * temp;
			d[i + 1] += d[i] * temp;
		}

		m[0] = f1;
		m[n - 1] = f2;
		for (i = n - 2; i > 0; i--) {
			d[i] -= d[i + 1] * r[i] / a[i + 1];
			m[i] = d[i] / a[i];
		}

		i = n - 1;
		while (t < x + i * xStep)
			i--;
		double a1 = x + (i + 1) * xStep - t, a2 = t - x - i * xStep;
		result = (m[i] * a1 * a1 * a1 + m[i + 1] * a2 * a2 * a2
				+ (6 * y[i] - m[i] * xStep * xStep) * a1 + (6 * y[i + 1] - m[i + 1]
				* xStep * xStep)
				* a2)
				/ 6 / xStep;

		return result;
	}

	public static double getValueSpline3(int n, double x, double xStep,
			double[] y, double t) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 第三种边界条件的三次样条函数插值
	 * 
	 * @param n
	 *            - 结点的个数
	 * @param x
	 *            - 一维数组，长度为n，存放给定的n个结点的值x(i)
	 * @param y
	 *            - 一维数组，长度为n，存放给定的n个结点的函数值y(i)， y(i) = f(x(i)), i=0,1,...,n-1
	 * @return
	 */
	public static double getValueSpline3(int n, double[] x, double[] y, double t) {
//		int i;
//		double temp;
		double result = 0.0;
//		double[] a = new double[n];
//		double[] m = new double[n];
//		double[] d = new double[n];
//		double[] r = new double[n];
//		double[] u = new double[n];
//		double[] h = new double[n - 1];
//		double[] c = new double[n - 1];
//
//		for (i = 0; i < n - 1; i++) {
//			a[i] = 2.0;
//			h[i] = x[i + 1] - x[i];
//			c[i] = (y[i + 1] - y[i]) / h[i];
//		}
//		a[n - 1] = 2.0;
//		for (i = 0; i < n - 2; i++) {
//			r[i] = h[0] / (h[i] + h[0]);
//			u[i] = 1 - r[i];
//			d[i] = 6 * (c[i + 1] - c[i]) / (h[i] + h[0]);
//		}
		// d[0] = 6 * (c[0] - f1) / h[0];
		// d[n - 1] = 6 * (f2 - c[n - 2]) / h[n - 2];
		//
		// for (i = 0; i < n - 1; i++) {
		// temp = -u[i] / a[i];
		// a[i + 1] += r[i] * temp;
		// d[i + 1] += d[i] * temp;
		// }
		// for (i = n - 2; i >= 0; i--) {
		// d[i] -= d[i + 1] * r[i] / a[i + 1];
		// m[i] = d[i] / a[i];
		// }
		// m[n - 1] = d[n - 1] / a[n - 1];
		// i = n - 1;
		// while (t < x[i])
		// i--;
		// double a1 = x[i + 1] - t, a2 = t - x[i];
		// result = (m[i] * a1 * a1 * a1 + m[i + 1] * a2 * a2 * a2
		// + (6 * y[i] - m[i] * h[i] * h[i]) * a1 + (6 * y[i + 1] - m[i + 1]
		// * h[i] * h[i])
		// * a2)
		// / 6 / h[i];

		return result;
	}

	private static double spE(double d, double a1, double e, double a2,
			double f, double g, double h) {
		return (d * a1 * a1 * a1 + e * a2 * a2 * a2 + (6 * f - d * h * h) * a1 + (6 * g - e
				* h * h)
				* a2)
				/ 6 / h;
	}

	/**
	 * 不等距牛顿差值
	 * 
	 * @param n
	 *            - 结点的个数
	 * @param x
	 *            - 一维数组，长度为n，存放给定的n个结点的值x(i)
	 * @param y
	 *            - 一维数组，长度为n，存放给定的n个结点的函数值y(i)， y(i) = f(x(i)), i=0,1,...,n-1
	 * @param t
	 * @return
	 */
	public static double getValueNewton(int n, double[] x, double[] y, double t) {
		double p = 0;
		double m = 1;
		int i, j;

		for (i = 0; i < n; ++i) {
			for (j = 0; j < i; ++j) {
				m *= t - x[j];
			}
			p += y[0] * m;
			for (j = 0; j < n - i - 1; ++j) {
				y[j] = (y[j] - y[j + 1]) / (y[j] - y[j + 1 + i]);
			}
		}
		return p;
	}

	/**
	 * 等距牛顿差值
	 * 
	 * @param n
	 *            - 结点的个数
	 * @param x0
	 * @param xStep
	 * @param y
	 *            - 一维数组，长度为n，存放给定的n个结点的函数值y(i)， y(i) = f(x(i)), i=0,1,...,n-1
	 * @param t
	 * @return
	 */
	public static double getValueNewton(int n, double x0, double xStep,
			double[] y, double t) {
		double p = 0;
		double m = 1;
		int i, j;

		for (i = 0; i < n; i++) {
			for (j = 0; j < i; j++) {
				m *= t - x0 - j * xStep;
			}
			p += y[0] * m;
			for (j = 0; j < n - i - 1; ++j) {
				y[j] = (y[j + 1] - y[j]) / ((i + 1) * xStep);
			}
		}
		return p;
	}

	/**
	 * 不等距分段低次插值
	 * 
	 * @param n
	 *            - 结点的个数
	 * @param x
	 *            - 一维数组，长度为n，存放给定的n个结点的值x(i)
	 * @param y
	 *            - 一维数组，长度为n，存放给定的n个结点的函数值y(i)， y(i) = f(x(i)), i=0,1,...,n-1
	 * @param t
	 * @return
	 */
	public static double getValueLinear(int n, double[] x, double[] y, double t) {
		double p = 0;
		int i = 0;

		if (t >= x[0] && t <= x[n - 1]) {
			while (t >= x[i] && i != n) {
				i++;
			}
			p = (y[i] * (t - x[i - 1]) - y[i - 1] * (t - x[i]))
					* (x[i] - x[i - 1]);
		}

		return p;
	}

	/**
	 * 等距分段低次插值
	 * 
	 * @param n
	 *            - 结点的个数
	 * @param x0
	 * @param xStep
	 * @param y
	 * @param t
	 * @return
	 */
	public static double getValueLinear(int n, double x0, double xStep,
			double[] y, double t) {
		double p = 0;
		int i = 0;

		if (t >= x0 && t <= x0 + (n - 1) * xStep) {
			while (t >= x0 + i * xStep && i != n) {
				i++;
			}
			p = (y[i] * (t - x0 - xStep * (i - 1)) - y[i - 1]
					* (t - x0 - i * xStep))
					* xStep;
		}

		return p;
	}
}