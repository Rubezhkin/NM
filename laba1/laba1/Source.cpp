#include <fstream>
#include <iostream>
#include <Windows.h>
#include <string>
using namespace std;



bool solution(int N = 10, bool rand = 0)
{
	string filename;

	if (rand)
		filename = "rand_data.txt";
	else
		filename = "data.txt";

	fstream in(filename);

	long double** a = new long double* [N - 1];
	long double** b = new long double* [N];
	long double** c = new long double* [N - 1];
	long double* A = new long double[N - 2];
	long double* B = new long double[N - 2];
	long double* C = new long double[N - 3];

	long double* p = new long double[N];
	long double* q = new long double[N];
	long double* f = new long double[N];

	long double* ft = new long double[N];

	long double R;

	ft[0] = 0;
	for (int i = 0; i < N; i++)
	{
		in >> p[i];
		ft[0] += p[i];
	}

	ft[1] = 0;
	for (int i = 0; i < N; i++)
	{
		in >> q[i];
		ft[1] += q[i];
	}

	b[0] = &p[0];
	c[0] = &p[1];

	a[0] = &q[0];
	b[1] = &q[1];
	c[1] = &q[2];

	for (int i = 2; i < N - 1; i++)
	{
		for (int j = 0; j < i - 1; j++)
			in >> R;
		in >> A[i - 2] >> B[i - 2] >> C[i - 2];
		ft[i] = A[i - 2] + B[i - 2] + C[i - 2];

		a[i - 1] = &A[i - 2];
		b[i] = &B[i - 2];
		c[i] = &C[i - 2];
		for (int j = i + 2; j < N; j++)
			in >> R;
	}
	for (int i = 0; i < N - 2; i++)
		in >> R;

	in >> A[N - 3] >> B[N - 3];
	ft[N - 1] = A[N - 3] + B[N - 3];

	a[N - 2] = &A[N - 3];
	b[N - 1] = &B[N - 3];


	for (int i = 0; i < N; i++)
		in >> f[i];

	long double* x = new long double[N];
	if (rand)
		for (int i = 0; i < N; i++)
			in >> x[i];
	in.close();
	bool flag = false;
	for (int i = N - 1; i > 1; i--)
	{
		if (*b[i] == 0)
			flag = true;
		else if (!flag)
		{
			*a[i - 1] = *a[i - 1]/ *b[i];
			f[i] = f[i] / *b[i];
			ft[i] = ft[i] / *b[i];
			*b[i] = 1;
			
			*b[i - 1] = *b[i - 1] - *c[i - 1] * *a[i - 1];
			f[i - 1] = f[i - 1] - *c[i - 1] * f[i];
			ft[i - 1] = ft[i - 1] - *c[i - 1] * ft[i];
			*c[i - 1] = 0;
			
			q[i - 1] = q[i - 1] - q[i] * *a[i - 1];
			f[1] = f[1] - q[i] * f[i];
			ft[1] = ft[1] - q[i] * ft[i];
			q[i] = 0;

			p[i - 1] = p[i - 1] - p[i] * *a[i - 1];
			f[0] = f[0] - p[i] * f[i];
			ft[0] = ft[0] - p[i] * ft[i];
			p[i] = 0;
		}
	}
	if (!flag)
	{
		if (p[0] == 0)
			flag = 1;

		p[1] = p[1] /p[0];
		f[0] = f[0] /p[0];
		ft[0] = ft[0] /p[0];
		p[0] = 1;
	
		q[1] = q[1] - q[0] * p[1];
		f[1] = f[1] - q[0] * f[0];
		ft[1] = ft[1] - q[0] * ft[0];
		q[0] = 0;
	}
	if (!flag)
	{
		if (q[1] == 0)
			flag = 1;
		
		f[1] = f[1] /q[1];
		ft[1] = ft[1] /q[1];
		q[1] = 1;
		
		f[0] = f[0] - f[1] * p[1];
		ft[0] = ft[0] - ft[1] * p[1];
		p[1] = 0;
	}
	if (!flag)
		for (int i = 1; i < N - 1; i++)
		{
			f[i + 1] = f[i + 1] - *a[i] * f[i];
			ft[i + 1] = ft[i + 1] - *a[i] * ft[i];
			*a[i] = 0;
		}
	if (!flag)
	{
		ofstream out("res.txt", ios::trunc);
		out.precision(20);
		for (int i = 0; i < N; i++)
			out << f[i] << " ";
		out << "\n";
		if (rand)
		{
			for (int i = 0; i < N; i++)
				out << x[i] << " ";
			out << "\n";
		}
		for (int i = 0; i < N; i++)
			out << ft[i] << " ";
		out.close();
	}
	else
	{
		ofstream out("res.txt", ios::trunc);
		out << "Нет Решения!";
		out.close();
	}
	return !flag;
}

void generate(int N, int diap)
{
	long double* A = new long double[N - 2];
	long double* B = new long double[N - 2];
	long double* C = new long double[N - 3];
	long double* p = new long double[N];
	long double* q = new long double[N];
	long double* f = new long double[N];
	long double* x = new long double[N];
	srand(GetTickCount64());
	f[0] = 0;
	for (int i = 0; i < N; i++)
		x[i] = rand() % (2 * diap + 1000 + 1) / 1000.0 - diap;
	for (int i = 0; i < N; i++)
	{
		p[i] = rand() % (2 * diap * 1000 + 1) / 1000.0 - diap;
		f[0] = f[0] + p[i] * x[i];
	}

	f[1] = 0;
	for (int i = 0; i < N; i++)
	{
		q[i] = rand() % (2 * diap * 1000 + 1) / 1000.0 - diap;
		f[1] = f[1] + q[i] * x[i];
	}

	for (int i = 0; i < N - 3; i++)
	{
		A[i] = rand() % (2 * diap * 1000 + 1) / 1000.0 - diap;
		B[i] = rand() % (2 * diap * 1000 + 1) / 1000.0 - diap;
		C[i] = rand() % (2 * diap * 1000 + 1) / 1000.0 - diap;
		f[i + 2] = A[i] * x[i + 1] + B[i] * x[i + 2] + C[i] * x[i + 3];
	}

	A[N - 3] = rand() % (2 * diap * 1000 + 1) / 1000.0 - diap;
	B[N - 3] = rand() % (2 * diap * 1000 + 1) / 1000.0 - diap;
	f[N - 1] = A[N - 3] * x[N - 2] + B[N - 3] * x[N - 1];

	ofstream file("rand_data.txt", ios::trunc);
	file.precision(20);
	for (int i = 0; i < N; i++)
		file << p[i] << " ";
	file << "\n";

	for (int i = 0; i < N; i++)
		file << q[i] << " ";
	file << "\n";

	for (int i = 2; i < N - 1; i++)
	{
		for (int j = 0; j < i - 1; j++)
			file << 0 << " ";

		file << A[i - 2] << " " << B[i - 2] << " " << C[i - 2] << " ";

		for (int j = i + 2; j < N; j++)
			file << 0 << " ";

		file << "\n";
	}
	for (int i = 0; i < N - 2; i++)
		file << 0 << " ";

	file << A[N - 3] << " " << B[N - 3] << "\n";
	for (int i = 0; i < N; i++)
		file << f[i] << "\n";

	for (int i = 0; i < N; i++)
		file << x[i] << " ";
	file.close();
}

void acc_test(bool rand)
{
	string res;
	int N = 10;
	if (rand)
	{
		int diap;
		cout << "Введите размерность, диапозон: ";
		cin >> N >> diap;
		bool flag;
		do
		{
			generate(N, diap);
			flag = solution(N, rand);
		} while (!flag);
		res = to_string(N) + " " + to_string(diap) + " ";
	}
	else
	{
		solution();
		res = "Пример ";
	}
	fstream file("res.txt");
	long double acc = 0;
	long double f;
	long double delt = 0.0;

	if (!rand)
		for (int i = 0; i < N; i++)
			file >> f;
	else
	{
		long double* xd = new long double[N];
		for (int i = 0; i < N; i++)
			file >> xd[i];
		long double* x = new long double[N];
		for (int i = 0; i < N; i++)
			file >> x[i];

		for (int i = 0; i < N; i++)
			delt = max(abs((x[i] - xd[i]) / max(1,x[i])), delt);
	}
	for (int i = 0; i < N; i++)
	{
		file >> f;
		acc = max(abs(f - 1.0), acc);
	}
	ofstream out("acc_test.txt", ios::app);
	out.precision(20);
	out << res;
	if (rand)
		out << delt << " ";
	out << acc << "\n";
	out.close();
}

void main()
{
	SetConsoleOutputCP(1251);

	cout << "0)Выполнить пример\n1)Решить случайную матрицу\n";
	bool rand;
	cin >> rand;
	acc_test(rand);
}

