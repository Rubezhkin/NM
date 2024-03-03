class Laba4
{
    public static void solution(decimal[] x, decimal[] y, decimal xx)
    {
        for (int j = 0; j < 3; j++)// поиск ближайщих к xx точек
            if (Math.Abs(x[j] - xx) > Math.Abs(x[j + 1] - xx))
            {
                (x[j], x[j + 1]) = (x[j + 1], x[j]);
                (y[j], y[j + 1]) = (y[j + 1], y[j]);
            }

        if (x[0] != x[1] && x[0] != x[2] && x[1] != x[2] && x[0] != x[3] && x[1] != x[3] && x[2] != x[3])
        {
            for (int i = 0; i < 3; i++)  // сортируем точки по x
                for (int j = 0; j < 2; j++)
                    if (x[j] > x[j + 1])
                    {
                        (x[j], x[j + 1]) = (x[j + 1], x[j]);
                        (y[j], y[j + 1]) = (y[j + 1], y[j]);
                    }

            Console.WriteLine("Status: 0");
            //x[3] = xx;
            decimal yy;
            decimal f01, f12, f02;
            f01 = (y[1] - y[0]) / (x[1] - x[0]); // строим многочлен и считаем yy
            f12 = (y[2] - y[1]) / (x[2] - x[1]);
            f02 = (f12 - f01) / (x[2] - x[0]);
            yy = y[0] + (xx - x[0]) * f01 + (xx - x[0]) * (xx - x[1]) * f02;
            Console.WriteLine("YY: " + yy);

            for (int i = 0; i < 4; i++)  // сортируем точки по x
                for (int j = 0; j < 3; j++)
                    if (x[j] > x[j + 1])
                    {
                        (x[j], x[j + 1]) = (x[j + 1], x[j]);
                        (y[j], y[j + 1]) = (y[j + 1], y[j]);
                    }

            decimal f23, f13, f03, yy4, eps;
            f01 = (y[1] - y[0]) / (x[1] - x[0]);
            f12 = (y[2] - y[1]) / (x[2] - x[1]);
            f23 = (y[3] - y[2]) / (x[3] - x[2]);
            f02 = (f12 - f01) / (x[2] - x[0]);    
            f13 = (f23 - f12) / (x[3] - x[1]);
            f03 = (f13 - f02) / (x[3] - x[0]);


            yy4 = y[0] + (xx - x[0]) * f01 + (xx - x[1]) * (xx - x[0]) * f02 + (xx - x[0]) * (xx - x[1]) * (xx - x[2]) * f03;
            eps = Math.Abs(yy4 - yy);
            Console.WriteLine("EPS: " + eps);

        }
        else
        {
            Console.WriteLine("Status: 1");
        }
    }
    public static void Main(string[] args)
    {
        string str;
        string[] str1;
        str = Console.ReadLine();
        str1 = str.Split(' ');
        decimal[] x = new decimal[4];
        for (int i = 0; i < 4; i++)
            x[i] = Convert.ToDecimal(str1[i]);

        str = Console.ReadLine();
        str1 = str.Split(' ');
        decimal[] y = new decimal[4];
        for (int i = 0; i < 4; i++)
            y[i] = Convert.ToDecimal(str1[i]);

        decimal xx = Convert.ToDecimal(Console.ReadLine());

        solution(x, y, xx);
    }
}