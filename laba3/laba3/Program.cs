namespace laba3
{
    class Laba
    {
        static void generation(int n, int diap)
        {
            decimal[] arr = new decimal[n];
            Random rand = new Random();
            for (int i = 0; i < arr.Length; i++)
                arr[i] = (decimal)(rand.Next(2 * diap * 1000) / 1000.0 - diap);

            for (int i = 0; i < arr.Length; i++) // сортировка
                for (int j = i; j < arr.Length; j++)
                    if (Math.Abs(arr[i]) < Math.Abs(arr[j]))
                        (arr[i], arr[j]) = (arr[j], arr[i]);

            Matrix lambd = new Matrix(arr); // матрица со собственными значениями
            Matrix w = new Matrix(n, 1);
            for (int i = 0; i < w.rows; i++) // генерация вектора
                w.matrix[i, 0] = (decimal)(rand.Next(2 * diap * 1000) / 1000.0 - diap);
            w.Normalizing(); // нормализация вектора

            for (int i = 0; i < arr.Length; i++)
                arr[i] = 1;
            Matrix E = new Matrix(arr);

            Matrix H = E - 2 * (w * w.Transp()); // матрицы собственных векторов
            Matrix A = H * lambd * H;

            using (StreamWriter sw = new StreamWriter("data.txt")) // запись в файл
            {
                sw.WriteLine(n); // размерность
                sw.WriteLine();

                for (int i = 0; i < n; i++)
                {
                    for (int j = 0; j < n; j++)
                        sw.Write(string.Format("{0:F30}", A.matrix[i, j]) + ' ');
                    sw.WriteLine();
                }
                sw.WriteLine()
                    ;
                for (int i = 0; i < n; i++) // собственные значения
                {
                    sw.Write(string.Format("{0:F30}", lambd.matrix[i, i]) + ' ');
                }
                sw.WriteLine('\n');

                for (int i = 0; i < n; i++) // собственные вектора
                {
                    for (int j = 0; j < n; j++)
                        sw.Write(string.Format("{0:F30}", H.matrix[i, j]) + ' ');
                    sw.WriteLine();
                }
                sw.Close();
            }
        }

        static bool solution(int it_count, decimal eps)
        {
            using (StreamReader sr = new StreamReader("data.txt")) // чтение с файла
            {
                using (StreamWriter sw = new StreamWriter("res.txt")) // нужно только часть информации, поэтому сразу записываем в результат остальное
                {
                    int n = Convert.ToInt32(sr.ReadLine()); // размерность
                    string nums;
                    string[] nums_arr;
                    sr.ReadLine();

                    Matrix A = new Matrix(n); // матрица
                    for (int i = 0; i < n; i++)
                    {
                        nums = sr.ReadLine();
                        nums_arr = nums.Split();
                        for (int j = 0; j < n; j++)
                            A.matrix[i, j] = decimal.Parse(nums_arr[j]);
                    }
                    sr.ReadLine();

                    decimal lambda_n; // собственное значение
                    nums = sr.ReadLine();
                    nums_arr = nums.Split();
                    lambda_n = decimal.Parse(nums_arr[0]);
                    sw.WriteLine(nums_arr[1]);
                    sr.ReadLine();
                    sw.WriteLine();

                    Matrix x_n = new Matrix(n, 1); // собственный вектор
                    nums = sr.ReadLine();
                    nums_arr = nums.Split();
                    for (int i = 0; i < n; i++)
                        x_n.matrix[i, 0] = decimal.Parse(nums_arr[i]);

                    nums = sr.ReadLine();
                    sw.WriteLine(nums);
                    sw.WriteLine();

                    A = A - lambda_n * (x_n * x_n.Transp());

                    Matrix v = new Matrix();
                    int it = 0;
                    Matrix prev_v = new Matrix();
                    decimal acc = 0;
                    Matrix prev_lambda = new Matrix();
                    Matrix lambda = new Matrix();

                    // обработка
                    while (it < it_count && (eps < acc || it <= 1))
                    {
                        v = x_n;
                        v.Normalizing();

                        x_n = A * v;

                        lambda = v.Transp() * x_n;

                        if (it != 0)
                        {
                            acc = 0;
                            for (int i = 0; i < v.cols; i++)
                                acc = Math.Max(acc, Math.Abs(prev_v.matrix[0, i] - v.matrix[0, i]));
                            acc = Math.Max(acc, Math.Abs(lambda.matrix[0, 0] - prev_lambda.matrix[0, 0]));
                        }

                        prev_lambda = lambda;
                        prev_v = v;
                        it++;
                    }

                    sw.WriteLine(string.Format("{0:F30}", lambda.matrix[0, 0]) + "\n");

                    for (int i = 0; i < n; i++)
                        sw.Write(string.Format("{0:F30}", v.matrix[i, 0]) + " ");
                    sw.WriteLine("\n");

                    sw.WriteLine(it + "\n");

                    decimal r = 0M;
                    Matrix delt = (A * x_n) - (x_n * lambda);
                    for (int i = 0; i < n; i++)
                        r = Math.Max(Math.Abs(delt.matrix[i, 0]), r);
                    sw.WriteLine(string.Format("{0:F30}", r) + "\n");

                    sw.Close();
                }

                sr.Close();
            }
            return true;
        }

        public static decimal sqrt(decimal n, decimal eps = 0.0M)
        {

            decimal current = (decimal)Math.Sqrt((double)n), previous;
            do
            {
                previous = current;
                if (previous == 0.0M) return 0;
                current = (previous + n / previous) / 2;
            } while (Math.Abs(previous - current) > eps);
            return current;
        }

        public static void test(int n, int diap, int it, decimal eps)
        {
            generation(n, diap);
            solution(it, eps);

            using (StreamReader sr = new StreamReader("res.txt"))
            {
                decimal lambda = decimal.Parse(sr.ReadLine());
                sr.ReadLine();

                string str = sr.ReadLine();
                string[] strs = str.Split();
                Matrix vec = new Matrix(1, n);
                for (int i = 0; i < n; i++)
                    vec.matrix[0, i] = Math.Abs(decimal.Parse(strs[i]));
                sr.ReadLine();

                lambda -= decimal.Parse(sr.ReadLine());
                lambda = Math.Abs(lambda);
                sr.ReadLine();

                str = sr.ReadLine();
                strs = str.Split();
                for (int i = 0; i < n; i++)
                    vec.matrix[0, i] -= Math.Abs(decimal.Parse(strs[i]));
                sr.ReadLine();

                decimal vec_acc = 0;
                for (int i = 0; i < n; i++)
                    vec_acc = Math.Max(vec_acc, vec.matrix[0, i]);

                int it_t = Convert.ToInt32(sr.ReadLine());
                sr.ReadLine();

                decimal r = decimal.Parse(sr.ReadLine());

                using (StreamWriter sw = new StreamWriter("acc_res.txt",true))
                {
                    sw.WriteLine(n + " " + diap + " " + string.Format("{0:E2}", eps) + " " + string.Format("{0:E2}", lambda) + " " + string.Format("{0:E2}", vec_acc) + " " + string.Format("{0:E2}", r) + " " + it_t);
                    sw.Close();
                }
            }
        }

        public static void Main(string[] args)
        {
            test(3, 10, 50, 1E-5M);
        }
    }
}