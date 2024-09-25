package bouncing_balls;


import java.awt.*;
import java.util.Random;

/**
 * The physics model.
 *
 * This class is where you should implement your bouncing balls model.
 *
 * The code has intentionally been kept as simple as possible, but if you wish, you can improve the design.
 *
 * @author Simon Robillard
 *
 */
class Model {

	double areaWidth, areaHeight;

	Ball [] balls;

	Model(double width, double height) {
		areaWidth = width;
		areaHeight = height;

		// Initialize the model with a few balls
		balls = new Ball[2];
		// 2D
/*
		balls[0] = new Ball(width / 3, height * 0.9, 1.2, 1.6, 0.2, Color.BLUE);
		balls[1] = new Ball(2 * width / 3, height * 0.7, -0.6, 0.6, 0.3, Color.RED);
*/

		// 1D
		balls[0] = new Ball(width / 3, height * 0, 2, 0, 0.2, Color.BLUE);
		balls[1] = new Ball(2 * width / 3, height * 0, -1, 0, 0.3, Color.RED);
	}

	void step(double deltaT) {
		// TODO this method implements one step of simulation with a step deltaT
		float g = -9.8f; // acceleration of gravity
		double collisionMargin = 0.01; // to avoid overlapping

		for (Ball b : balls) {

			b.collisionTimer += deltaT;

			// detect collision with the border
			if (b.x - collisionMargin < b.radius || b.x + collisionMargin > areaWidth - b.radius) {
				b.vx *= -1; // change direction of ball
			}
			if (b.y - collisionMargin < b.radius || b.y + collisionMargin > areaHeight - b.radius) {
				b.vy *= -1;
			}

			// compute new position according to the speed of the ball
			b.x += deltaT * b.vx;
			b.y += deltaT * b.vy;

			// apply gravitation. v(t + deltaT) = v(t) + v'(deltaT) where v' = g = -9.8
			b.vy = b.vy + g*deltaT;

			// handle collisions with the other ball(s?)
			handleTwoBallsColliding(b, collisionMargin, deltaT);
		}
	}

	private void handleTwoBallsColliding(Ball b, double collisionMargin, double collisionTimer) {
		for(Ball b2 : balls){
			if (b == b2) break;

			// The two balls collide when the sum of their radii are equal to the length of the hypotenuse
			// of the right triangle where distance between x coordinates is the first leg
			// and the distance between y coordinates is the second leg.
			// the hypotenuse and the sum of radii are both squared in order to not having to take the costly square root...
			if (b.collisionTimer > collisionTimer*2){
				double distanceSquared = Math.pow((b2.x - b.x),2) + Math.pow((b2.y - b.y), 2);
				if (distanceSquared <= Math.pow(b.radius + b2.radius, 2) + collisionMargin){
					transferMomentum2D(b, b2);
					b.collisionTimer = 0;
				}
			}
		}
	}

	protected void transferMomentum2D(Ball b1, Ball b2) {
		double m1 = b1.radius;
		double m2 = b2.radius;
		double u1 = b1.vx;
		double u2 = b2.vx;
		b1.vx = (m1*u1 + 2*m2*u2 -m2*u1)/(m1 + m2);
		b2.vx = (2*m1*u1 + m2*u2 - m1*u2)/(m1 + m2);
	}


	protected static double[] rectToPolar(double x, double y) {
		double angle = Math.atan2(y,x); // radians
		double radius = Math.sqrt(x*x+y*y);
		return new double[]{angle, radius};
	}

	protected static double[] polarToRect(double radius, double angle) {
		double x = radius*Math.cos(angle);
		double y = radius*Math.sin(angle);
		return new double[]{x,y};
	}

	/**
	 * Simple inner class describing balls.
	 */
	class Ball {
		/*Collision timer increases with deltaT on each step. If this number is above a certain threshold
		 * the collision will register and this timer will be set to 0. An ugly bug fix...
		 * */
		double collisionTimer = 0;
		Color color;

		Ball(double x, double y, double vx, double vy, double r, Color c) {
			this.x = x;
			this.y = y;
			this.vx = vx;
			this.vy = vy;
			this.radius = r;
			this.color = c;
		}

		/**
		 * Position, speed, and radius of the ball. You may wish to add other attributes.
		 */
		double x, y, vx, vy, radius;


	}
}
