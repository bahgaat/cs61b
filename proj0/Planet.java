public class Planet{
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    public static double G = 6.67 * Math.pow(10, -11);


    public Planet(double xP, double yP, double xV, double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }


    public Planet(Planet p){
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }


    public double calcDistance(Planet p){
        /* calculate the distance between two planets. */
        double dx = p.xxPos - xxPos;
        double dy = p.yyPos - yyPos;
        double r_square = (dx * dx) + (dy * dy);
        double r = Math.sqrt(r_square);
        return r;
    }


    public double calcForceExertedBy(Planet p){
        /* calculate the force exerted on this planet by the given planet(p). */
        double r_square = Math.pow(calcDistance(p), 2);
        double F = (G * mass * p.mass) / r_square;
        return F;
    }


    public double calcForceExertedByX(Planet p){
        /* calculate the force exerted on the x direction. */
        double dx = p.xxPos - xxPos;
        double Fx = (calcForceExertedBy(p) * dx) / calcDistance(p);
        return Fx;
    }


    public double calcForceExertedByY(Planet p){
        /* calculate the force exerted on the y direction. */
        double dy = p.yyPos - yyPos;
        double Fy = (calcForceExertedBy(p) * dy) / calcDistance(p);
        return Fy;
    }


    public double calcNetForceExertedByX(Planet[] allPlanets){
        /* calculate the net x force exerted by all the planets in the array upon the current planet. */
        double Fnetx = 0;
        for (int i = 0; i < allPlanets.length; i = i + 1) {
            if (this.equals(allPlanets[i])){
                continue;
            }
            Fnetx = calcForceExertedByX(allPlanets[i]) + Fnetx;
        }
        return Fnetx;
    }


    public double calcNetForceExertedByY(Planet[] allPlanets){
        /* calculate the net x force exerted by all the planets in the array upon the current planet. */
        double Fnety = 0;
        for (int i = 0; i < allPlanets.length; i = i + 1) {
            if (this.equals(allPlanets[i])){
                continue;
            }
            Fnety = calcForceExertedByY(allPlanets[i]) + Fnety;
        }
        return Fnety;
    }


    public void update(double dt, double fx, double fy){
        /* determines how much the forces exerted on the planet will cause that planet to accelerate,
        and the resulting change in the planet’s velocity and position in a small period of time dt. */
        double ax = fx / mass;
        double ay = fy / mass;
        xxVel = xxVel + dt * ax;
        yyVel = yyVel + dt * ay;
        xxPos = xxPos  + dt * xxVel;
        yyPos = yyPos + dt * yyVel;
    }


    public void draw(){
        /* using the stdDraw library to draw the planet in its given position.*/
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
        StdDraw.show();
    }
}
