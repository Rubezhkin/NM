package com.example;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println(calculate("D:/код/Лабы/ЧМ/laba5/demo/src/main/resources/input.txt", "D:/код/Лабы/ЧМ/laba5/demo/src/main/resources/output.txt"));
    }

    public static int calculate(String input, String output) throws Exception{
        Scanner reader = new Scanner(new File(input));
        FormulaTree f = new FormulaTree(reader.nextLine().split(" "));
        double A, B, C, Yc;
        A = reader.nextDouble();
        B = reader.nextDouble();
        C = reader.nextDouble();
        Yc = reader.nextDouble();
        double hMin = reader.nextDouble();
        double eps = reader.nextDouble();
        double y = Yc;
        double y1, y2;
        double k1,k2,k3;
        double h = Math.abs(A-B)/10;

        PrintWriter writer = new PrintWriter(output);

        int countDots = 0, countBadDots = 0, countMinDots = 0;
        if(A==C){
            double x = A;
            while(B-(x+h)>= hMin){
                k1 = h*f.calculate(x, y);
                k2 = h*f.calculate(x+h/2, y + k1/2);
                y1 = y + k2;

                k3 = h*f.calculate(x+h, y-k1+2*k2);

                if((k1-2*k2+k3)/6>eps){
                    if(h==hMin){
                        y = y1;
                        x += h;
                        System.out.println(x + " " + y + " " + (k1-2*k2+k3)/6 + "  " + h);
                        countBadDots++;
                        countMinDots++;
                    }
                    else
                        h = Math.min(h/2, hMin);
                }
                else{
                    y = y1;
                    x += h;
                    System.out.println(x + " " + y + " " + (k1-2*k2+k3)/6 + "  " + h);
                    countDots++;
                    if(h==hMin)
                        countMinDots++;
                    
                    h*=2;
                }
            }
            if(B-x >= 2*hMin){
                h = (B-hMin)-x;

                k1 = h*f.calculate(x, y);
                k2 = h*f.calculate(x+h/2, y + k1/2);
                y1 = y + k2;

                k3 = h*f.calculate(x+h, y-k1+2*k2);
                y2 = y + (k1+4*k2+k3)/6;

                if((k1-2*k2+k3)/6>eps)
                    countBadDots++;
                countMinDots+=2;
                countDots +=2;

                y = y1;
                x += h;
                
                System.out.println(x + " " + y + " " + (k1-2*k2+k3)/6 + "  " + h);
                h = hMin;
                
                k1 = h*f.calculate(x, y);
                k2 = h*f.calculate(x+h/2, y + k1/2);
                y1 = y + k2;

                k3 = h*f.calculate(x+h, y-k1+2*k2);
                y2 = y + (k1+4*k2+k3)/6;

                y = y1;
                x += h;
                
                System.out.println(x + " " + y + " " + (k1-2*k2+k3)/6 + "  " + h);

               if((k1-2*k2+k3)/6>eps)
                    countBadDots++;
                System.out.println(countDots + " " + countBadDots + " " + countMinDots);
                if((k1-2*k2+k3)/6>eps)
                    return 1;
            }
            else if(B-x <= 1.5*hMin){
                h = B-x;

                k1 = h*f.calculate(x, y);
                k2 = h*f.calculate(x+h/2, y + k1/2);
                y1 = y + k2;

                k3 = h*f.calculate(x+h, y-k1+2*k2);
                y2 = y + (k1+4*k2+k3)/6;

                countMinDots++;
                countDots++;

                y = y1;
                x += h;
                h = hMin;
                System.out.println(x + " " + y + " " + (k1-2*k2+k3)/6 + "  " + h);
                if((k1-2*k2+k3)/6>eps)
                    countBadDots++;
                System.out.println(countDots + " " + countBadDots + " " + countMinDots);
                if((k1-2*k2+k3)/6>eps)
                    return 1;
            }
            else{
                h = (B-x)/2;
                k1 = h*f.calculate(x, y);
                k2 = h*f.calculate(x+h/2, y + k1/2);
                y1 = y + k2;

                k3 = h*f.calculate(x+h, y-k1+2*k2);
                y2 = y + (k1+4*k2+k3)/6;

                if((k1-2*k2+k3)/6>eps)
                    countBadDots++;
                countMinDots+=2;
                countDots +=2;

                y = y1;
                x += h;
                System.out.println(x + " " + y + " " + (k1-2*k2+k3)/6 + "  " + h);

                k1 = h*f.calculate(x, y);
                k2 = h*f.calculate(x+h/2, y + k1/2);
                y1 = y + k2;

                k3 = h*f.calculate(x+h, y-k1+2*k2);
                y2 = y + (k1+4*k2+k3)/6;
                y = y1;
                x += h;
                System.out.println(x + " " + y + " " + (k1-2*k2+k3)/6 + "  " + h);
                if((k1-2*k2+k3)/6>eps)
                    countBadDots++;
                System.out.println(countDots + " " + countBadDots + " " + countMinDots);
                if((k1-2*k2+k3)/6>eps)
                    return 1;
            }
        }
        else{
            double x = B;
            while((x-h)-A>= hMin){
                k1 = h*f.calculate(x, y);
                k2 = h*f.calculate(x-h/2, y - k1/2);
                y1 = y - k2;
        
                k3 = h*f.calculate(x-h, y+k1-2*k2);
                y2 = y - (k1+4*k2+k3)/6;
        
                if((k1-2*k2+k3)/6>eps){
                    if(h==hMin){
                        y = y1;
                        x -= h;
                        System.out.println(x + " " + y + " " + (k1-2*k2+k3)/6 + "  " + h);
                        countBadDots++;
                        countMinDots++;
                    }
                    else
                        h = Math.min(h/2, hMin);
                }
                else{
                    y = y1;
                    x -= h;
                    System.out.println(x + " " + y + " " + (k1-2*k2+k3)/6 + "  " + h);
                    countDots++;
                    if(h==hMin)
                        countMinDots++; 
                    h*=2;
                }
            }
            if(x-A >= 2*hMin){
                h = x-(A+hMin);

                k1 = h*f.calculate(x, y);
                k2 = h*f.calculate(x-h/2, y - k1/2);
                y1 = y - k2;

                k3 = h*f.calculate(x-h, y+k1-2*k2);
                y2 = y - (k1+4*k2+k3)/6;

                if((k1-2*k2+k3)/6>eps)
                    countBadDots++;
                countMinDots+=2;
                countDots +=2;

                y = y1;
                x -= h;
                h = hMin;
                System.out.println(x + " " + y + " " + (k1-2*k2+k3)/6 + "  " + h);

                k1 = h*f.calculate(x, y);
                k2 = h*f.calculate(x-h/2, y - k1/2);
                y1 = y + k2;

                k3 = h*f.calculate(x-h, y+k1-2*k2);
                y2 = y - (k1+4*k2+k3)/6;
                y = y1;
                x -= h;
                System.out.println(x + " " + y + " " + (k1-2*k2+k3)/6 + "  " + h);
                if((k1-2*k2+k3)/6>eps)
                    countBadDots++;
                System.out.println(countDots + " " + countBadDots + " " + countMinDots);
                if((k1-2*k2+k3)/6>eps)
                    return 1;
            }
            else if(x-A <= 1.5*hMin){
                h = x-A;

                k1 = h*f.calculate(x, y);
                k2 = h*f.calculate(x-h/2, y - k1/2);
                y1 = y - k2;

                k3 = h*f.calculate(x-h, y+k1-2*k2);
                y2 = y - (k1+4*k2+k3)/6;

                countMinDots++;
                countDots++;

                y = y1;
                x -= h;
                h = hMin;
                System.out.println(x + " " + y + " " + (k1-2*k2+k3)/6 + "  " + h);
                if((k1-2*k2+k3)/6>eps)
                    countBadDots++;
                System.out.println(countDots + " " + countBadDots + " " + countMinDots);
                if((k1-2*k2+k3)/6>eps)
                    return 1;
            }
            else{
                h = (x-A)/2;
                k1 = h*f.calculate(x, y);
                k2 = h*f.calculate(x-h/2, y - k1/2);
                y1 = y - k2;

                k3 = h*f.calculate(x-h, y+k1-2*k2);
                y2 = y - (k1+4*k2+k3)/6;

                if((k1-2*k2+k3)/6>eps)
                    countBadDots++;
                countMinDots+=2;
                countDots +=2;

                y = y1;
                x -= h;
                System.out.println(x + " " + y + " " + (k1-2*k2+k3)/6 + "  " + h);

                k1 = h*f.calculate(x, y);
                k2 = h*f.calculate(x-h/2, y - k1/2);
                y1 = y - k2;

                k3 = h*f.calculate(x-h, y+k1-2*k2);
                y2 = y - (k1+4*k2+k3)/6;
                y = y1;
                x -= h;
                System.out.println(x + " " + y + " " + (k1-2*k2+k3)/6 + "  " + h);
                if((k1-2*k2+k3)/6>eps)
                    countBadDots++;
                System.out.println(countDots + " " + countBadDots + " " + countMinDots);
                if((k1-2*k2+k3)/6>eps)
                    return 1;
            }
       
        }
        return 0;
    }
}
