using System.ComponentModel.Design;
using System.Text;
using System.Transactions;

class Laba2
{
    public static bool solution()
    {
        using (StreamReader sr = new StreamReader("D:\\код\\Лабы\\ЧМ\\laba2\\laba2\\data.txt"))
        {
            string nums = sr.ReadLine();
            string[] nums_arr = nums.Split(' ');
            int N, L;
            N = int.Parse(nums_arr[0]);
            L = int.Parse(nums_arr[1]);
            double[][] matrix = new double[N][];
            for (int i = 0; i < N; i++)
            {
                matrix[i] = new double[Math.Min(L, N - i)];
                nums = sr.ReadLine();
                nums_arr = nums.Split();
                for (int j = 0; j < Math.Min(L, N - i); j++)
                    matrix[i][j] = double.Parse(nums_arr[j]);
            }
            double[] f = new double[N];
            nums = sr.ReadLine();
            nums_arr = nums.Split(' ');
            for (int i = 0; i < N; i++)
                f[i] = double.Parse(nums_arr[i]);
            //double[] x_t = new double[N];
            //nums = sr.ReadLine();
            //nums_arr = nums.Split(' ');
            //for (int i = 0; i < N; i++)
            //    x_t[i] = double.Parse(nums_arr[i]);
            bool flag = true;
            for (int i = 0; i < N && flag; i++)
            {
                for (int ki = i - 1, kj = 1; ki >= 0 && kj < L; ki--, kj++)
                    matrix[i][0] -= matrix[ki][kj] * matrix[ki][kj];
                if (matrix[i][0] < 0)
                    flag = false;
                else if (flag)
                {
                    matrix[i][0] = Math.Sqrt(matrix[i][0]);
                    for (int j = 1; j < Math.Min(L, N - i); j++)
                    {
                        for (int k = i - 1, ki = 1, kj = j + 1; k >= 0 && kj < L; k--, kj++, ki++)
                            matrix[i][j] -= matrix[k][ki] * matrix[k][kj];
                        matrix[i][j] /= matrix[i][0];
                    }
                }
            }
            if (flag)
            {
                for (int i = 0; i < N; i++)
                {
                    for (int ki = i - 1, kj = 1; ki >= 0 && kj < L; ki--, kj++)
                        f[i] -= f[ki] * matrix[ki][kj];
                    f[i] /= matrix[i][0];
                }
                for (int i = N - 1; i >= 0; i--)
                {
                    for (int j = i + 1; j < Math.Min(L+i, N); j++)
                        f[i] -= f[j] * matrix[i][j - i];
                    f[i] /= matrix[i][0];
                }
            }
            using (StreamWriter sw = new StreamWriter("D:\\код\\Лабы\\ЧМ\\laba2\\laba2\\result.txt"))
            {
                if (flag)
                    for (int i = 0; i < N; i++)
                    {
                        sw.Write(f[i]);
                        sw.Write(' ');
                    }
                else
                    sw.WriteLine("error");
                sw.Close();
            }
            sr.Close();
            return flag;
        }
    }
    public static void Main(String[] args)
    {
        solution();
    }
}