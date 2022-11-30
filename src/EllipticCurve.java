public class EllipticCurve {
    public long a;
    public long b;
    public long q;
    public long n;
    public Point G;

    public EllipticCurve(long a, long b, long q, long n, Point p) {
        this.a = a;
        this.b = b;
        this.q = q;
        this.n = n;
        G = p;
    }

    public boolean isEqual(Point p1, Point p2){
        return ((p1.x == p2.x) && (p1.y == p2.y));
    }

    public boolean isOnCurve(Point p){
        return (((p.x == -1) && (p.y == -1)) ||
                ((p.x >= 0) && (p.x < q) && (p.y >= 0) && (p.y < q)
                        && ((long)(p.y * p.y) % q == (long)(p.x*p.x*p.x + a* p.x + b) % q)));
    }

    public Point sum(Point p1, Point p2){
        if ((!isOnCurve(p1)) || (!isOnCurve(p2))){
            return null;
        }
        double lambda;
        double nu;
        if (isEqual(p1,p2)){
            if (p1.y == 0){
                return new Point(-1,-1);
            }
            lambda = ((3*Math.pow(p1.x, 2) + a)/((2* p1.y) % q)% q);
            nu = ((-Math.pow(p1.x, 3) + a* p1.x + 2*b)/((2* p1.y) % q)% q);
        }
        else {
            if ((p1.x - p2.x) % q == 0){
                return new Point(-1,-1);
            }
            lambda =  ((p2.y - p1.y)/((p2.x - p1.x) % q)% q);
            nu = ((p1.y* p2.x - p2.y* p1.x)/((p2.x - p1.x) % q)% q);
        }
        long xnew = (long) (lambda*lambda - p1.x - p2.x) % q;
        long ynew = (long) (-Math.pow(lambda,3) + lambda*(p1.x + p2.x) - nu) % q;
        if (xnew < 0) xnew+=q;
        if (ynew < 0) ynew+=q;
        return new Point(xnew, ynew);
    }

    public Point mult(long k, Point p){
        Point res = new Point(p);
        for (long i = 0; i < k; i++) {
            res = sum(res, p);
        }
        //System.out.println(res.x + " " + res.y);
        return res;
    }
}
