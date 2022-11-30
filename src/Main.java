import java.util.Scanner;

public class Main {
    public static int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71,
            73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179,
            181, 191, 193, 197, 199, 211, 223, 227, 229};

    public static boolean isPrime(int x){
        for (int i = 0; i < 50; i++) {
            if (x == primes[i]){
                return true;
            }
            if (x < primes[i]){
                break;
            }
        }
        return false;
    }

    public static int gcd(int n1, int n2) {
        if (n2 == 0) {
            return n1;
        }
        return gcd(n2, n1 % n2);
    }

    public static int f(int x,int n){
        return (x * x + 1) % n;
    }

    public static void roPollardFactorisation(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть число n:");
        int n = scanner.nextInt();
        int x = (int)(Math.random() % n);
        int y = x;
        int d = 1;
        while (d == 1){
            x = f(x,n)%n;
            y = f(f(y,n),n)%n;
            d = gcd(n,Math.abs(x-y));
        }
        if (d != n)
            System.out.println(d + "  " + n/d);
        else
            System.out.println("Нетривіальних дільників немає");
    }

    public static void steps(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть число n:");
        int n = scanner.nextInt();
        System.out.println("Введіть число альфа:");
        int a = scanner.nextInt()%n;
        System.out.println("Введіть число бета:");
        int b = scanner.nextInt()%n;
        int m = (int)Math.sqrt(n);
        if (Math.sqrt(n) > 0) m++;
        int[] arr = new int[m];
        arr[0] = 1;
        for (int j = 1; j < m; j++) {
            arr[j] = (arr[j-1]*a)%n;
        }
        int am = (int) (Math.pow(a,m)%n);
        int ai;
        for (int i = 0; i < m; i++) {
            ai = (int) (Math.pow(am,i)%n);
            for (int j = 0; j < m; j++) {
                if (ai*arr[j]%n == b){
                    System.out.println("x = " + (i*m+j));
                    return;
                }
            }
        }
        System.out.println("х не знайдено");
    }

    public static void euler(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть число n:");
        int n = scanner.nextInt();
        int res = n;
        if (n < 1) {
            System.out.println("не існує");
            return;
        }
        for (int i = 0; i < 50; i++) {
            if (n % primes[i] == 0){
                while (n % primes[i] == 0){
                    n /= primes[i];
                }
                res -= res/primes[i];
            }
            if (n == 1){
                System.out.println(res);
                return;
            }
        }
    }

    public static void mobius(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть число n:");
        int n = scanner.nextInt();
        if (n == 1) {
            System.out.println(1);
            return;
        }
        int s = 0;
        for (int i = 0; i < 50; i++) {
            if (n % primes[i] == 0){
                n /= primes[i];
                if (n % primes[i] == 0){
                    System.out.println(0);
                    return;
                }
                s++;
            }
            if (n == 1) break;
        }
        System.out.println((int)Math.pow(-1, s));
    }

    public static void legendreWrap(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть число а:");
        int a = scanner.nextInt();
        System.out.println("Введіть число р:");
        int p = scanner.nextInt();
        System.out.println(legendre(a,p));
    }

    public static Integer legendre(int a, int p){
        if ((p < 3) || !(isPrime(p))) {
            return null;
        }
        if (a % p == 0)
            return 0;
        if (Math.pow(a, ((p-1)/2) % p) % p == 1)
            return 1;
        return -1;
    }

    public static void jacobi(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть число а:");
        int a = scanner.nextInt();
        System.out.println("Введіть число m:");
        int m = scanner.nextInt();
        if ((m < 3) || (m % 2 == 0)) {
            System.out.println("не існує");
            return;
        }
        int res = 1;
        for (int i = 0; i < 50; i++) {
            if (m % primes[i] == 0){
                while (m % primes[i] == 0){
                    m /= primes[i];
                    res *= legendre(a, primes[i]);
                }
            }
            if (m == 1){
                System.out.println(res);
                return;
            }
        }
    }

    public static void chipolla(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть число n:");
        int n = scanner.nextInt();
        System.out.println("Введіть число р:");
        int p = scanner.nextInt();
        if (legendre(n,p) != 1){
            System.out.println("коренів нема");
            return;
        }
        int a = 0;
        while (legendre(a * a - n, p) != -1){
            a++;
        }
        int b = 1;
        int a1 = a;
        int b1 = b;
        int sqrt = a * a - n;
        for (int i = 1; i < (p+1)/2; i++) {
            int t = a1;
            a1 = (a1*a + b1*b*sqrt) % p;
            b1 = (t * b + b1 * a) % p;
        }
        if (a1<0) a1 += p;
        System.out.println("корені рівняння");
        System.out.println(a1 + " " + ((-a1 + p) % p));
    }

    public static void millerRabin(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть число а:");
        int a = scanner.nextInt();
        if (gcd(a,6) > 1){
            System.out.println("не відповідає умовам");
            return;
        }
        int n = 0;
        int q = a-1;
        while (q % 2 == 0){
            q/=2;
            n++;
        }
        int N = 200;
        for (int i = N; i > 0; i--) {
            int k = (int)(Math.random() % (a - 2)) + 2;
            int b = (int) (Math.pow(k,q) % a);
            if ((b != 1) && (b != a - 1)){
                for (int j = 0; j < n; j++) {
                    b = (b*b)%a;
                    if (b == a - 1) break;
                }
                System.out.println(a + " є складеним");
                return;
            }
        }
        System.out.println(a + " є простим з ймовiрнiстю ≥ " + (1-Math.pow(4,1 - N)));
    }

    public static void elGamal(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть число а:");
        long a = scanner.nextInt();
        System.out.println("Введіть число b:");
        long b = scanner.nextInt();
        System.out.println("Введіть число q:");
        long q = scanner.nextInt();
        System.out.println("Введіть число n:");
        long n = scanner.nextInt();
        System.out.println("Введіть x генератора:");
        long gx = scanner.nextInt();
        System.out.println("Введіть y генератора:");
        long gy = scanner.nextInt();
        EllipticCurve curve = new EllipticCurve(a,b,q,n,new Point(gx, gy));
        long k = (long) (1 + (Math.random() % (n-1)));
        Point Y = new Point(curve.mult(k,curve.G));
        //System.out.println(Y.x + " " + Y.y + " " + curve.isOnCurve(Y));

        System.out.println("Введіть цифру, яку треба передати:");
        long x = scanner.nextInt()%q;
        long y = (long) Math.sqrt(x*x*x + a* x + b)%q;
        Point M = new Point(x,y);
        //System.out.println(M.x + " " + M.y);
        long r = (long) (1 + (Math.random() % (n-1)));
        //System.out.println(r);
        Point d = new Point(curve.mult(r,Y));
        Point g = new Point(curve.mult(r,curve.G));
        Point h = new Point(curve.sum(M, d));
//        System.out.println(h.x + " " + h.y);

        Point s = new Point(curve.mult(k,g));
//        System.out.println(s.x + " " + s.y);
        if (s.y != -1) s.y = -s.y + curve.q;
//        System.out.println(s.x + " " + s.y);
        Point m = new Point(curve.sum(s, h));
        System.out.println(m.x);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. ро-алгоритм Полларда(факторизація) \n" +
                    "2. Великий крок - малий крок \n" +
                    "3. Ейлера \n" +
                    "4. Мьобіуса \n" +
                    "5. Лежандра \n" +
                    "6. Якобі \n" +
                    "7. Чіполли \n" +
                    "8. Міллера-Рабіна \n" +
                    "9. Ель-Гамаля");
            System.out.println("Виберіть алгоритм:");
            int a = scanner.nextInt();
            switch (a) {
                case 1:
                    roPollardFactorisation();
                case 2:
                    steps();
                case 3:
                    euler();
                case 4:
                    mobius();
                case 5:
                    legendreWrap();
                case 6:
                    jacobi();
                case 7:
                    chipolla();
                case 8:
                    millerRabin();
                case 9:
                    elGamal();
                default:
                    break;
            }
        }
    }
}