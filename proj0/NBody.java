public class NBody{
    public static void main(String[] args){
        /* this allows for animation. */
        StdDraw.enableDoubleBuffering();

        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius_of_universe = readRadius(filename);
        Planet[] allPlanets = readPlanets(filename);
        double diameter_of_universe = radius_of_universe * 2;


        /* using stdraw library to draw the image "starfield.jpg" we first set the scale of the universe and then draw it and show it. */
        StdDraw.setScale(- radius_of_universe, radius_of_universe);
        StdDraw.clear();
        StdDraw.picture(0, 0, "images/starfield.jpg");
        StdDraw.show();

        /* draw each planet in allPlanets array. */
        for (int i = 0; i < allPlanets.length; i = i + 1){
            allPlanets[i].draw();
        }

        /* create an animation. */
        for (double time = 0; time < T; time = time + dt){
            double[] xForces = new double[allPlanets.length];
            double[] yForces = new double[allPlanets.length];
            StdDraw.picture(0, 0, "images/starfield.jpg");

            /* calcuate the net x and y forces of each planet and store them in xForces and yForces arrays. */
            for (int y = 0; y < allPlanets.length; y = y + 1){
                xForces[y] = allPlanets[y].calcNetForceExertedByX(allPlanets);
                yForces[y] = allPlanets[y].calcNetForceExertedByY(allPlanets);
            }

            /* update each planet position and draw it after the update. */
            for (int x = 0; x < xForces.length; x = x + 1){
                allPlanets[x].update(dt, xForces[x], yForces[x]);
                allPlanets[x].draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }

        /* print out the final state of the universe in the same format as the input. */
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
            planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }


    public static double readRadius(String file){
        /* returns the radius of the universe in the given file. */
        In in = new In(file);

        /* Every time you call a read method from the In class,
    		 * it reads the next thing from the file, assuming it is
    		 * of the specified type. */

        int numb_of_planets = in.readInt();
        double radius_of_universe = in.readDouble();
        return radius_of_universe;
    }


    public static Planet[] readPlanets(String file){
        /* returns an array of the planets in the given file. */
        In in = new In(file);
        int numb_of_planets = in.readInt();
        Planet[] all_planets = new Planet[numb_of_planets];
        double radius_of_universe = in.readDouble();
        int i = 0;

        while (numb_of_planets != 0){
            Planet planet = new Planet(in.readDouble() , in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
            all_planets[i] = planet;
            i = i + 1;
            numb_of_planets = numb_of_planets - 1;
        }
        return all_planets;
    }
}
