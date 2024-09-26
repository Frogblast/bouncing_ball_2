package bouncing_balls;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static bouncing_balls.Model.*;
import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    final double EPSILON = 1e-9;

    Model.Ball b1;
    Model.Ball b2;

/*    @BeforeEach
    void setUp() {
        // Initialize balls before each test
        b1 = new Model.Ball(100, 0, -2, 0, 0.2, Color.BLUE);
        b2 = new Model.Ball(100, 0, 3, 0, 0.2, Color.RED);
    }*/

    @Test
    void testEqualMassesOppositeVelocities() {
        b1 = new Model.Ball(100, 0, -2, 0, 0.2, Color.BLUE);
        b2 = new Model.Ball(100, 0, 3, 0, 0.2, Color.RED);

        transferMomentum2D(b1, b2);
        assertEquals(3, b1.vx, 0.001);
        assertEquals(-2, b2.vx, 0.001);
    }

    @Test
    void testStationarySecondBall() {
        // Reinitialize b2 to be stationary
        b1 = new Model.Ball(100, 0, 0, 0, 0.2, Color.BLUE);
        b2 = new Model.Ball(100, 0, 1, 0, 0.2, Color.RED);

        transferMomentum2D(b1, b2);
        assertEquals(1.666, b1.vx, 0.001);
        assertEquals(3.333, b2.vx, 0.001);
    }


    @Test
    void test_rectToPolar_coordinates10() {
        double[] result = rectToPolar(1, 0);
        double a_result = result[0];
        double r_result = result[1];
        assertEquals(0, a_result, EPSILON);
        assertEquals(1, r_result, EPSILON);
    }

    @Test
    void test_rectToPolar_coordinates01() {
        double[] result = rectToPolar(0, 1);
        double a_result = result[0];
        double r_result = result[1];
        assertEquals(Math.toRadians(90), a_result, EPSILON);
        assertEquals(1, r_result);
    }

    @Test
    void test_rectToPolar_coordinates11() {
        double[] result = rectToPolar(1, 1);
        double a_result = result[0];
        double r_result = result[1];
        assertEquals(Math.toRadians(45), a_result, EPSILON);
        assertEquals(Math.sqrt(2), r_result);
    }

    @Test
    void test_rectToPolar_coordinates23() {
        double[] result = rectToPolar(2, 3);
        double a_result = result[0];
        double r_result = result[1];
        assertEquals(Math.toRadians(56.30993247), a_result, EPSILON);
        assertEquals(Math.sqrt(13), r_result);
    }

    @Test
    void test_rectToPolar_coordinates_one_is_negative() {
        double[] result = rectToPolar(-1, 1);
        double a_result = result[0];
        double r_result = result[1];
        assertEquals(Math.toRadians(135), a_result, EPSILON);
        assertEquals(Math.sqrt(2), r_result);
    }
    @Test
    void test_polarToRect_r1_a_90() {
        double[] xy = polarToRect(1, Math.toRadians(90));
        double resultX = xy[0];
        double resultY = xy[1];
        assertEquals(0, resultX, EPSILON);
        assertEquals(1, resultY, EPSILON);
    }



}