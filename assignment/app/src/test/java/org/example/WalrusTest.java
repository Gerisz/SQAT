/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example;

import org.example.values.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class WalrusTest {
    private Walrus walrus;
    private int amountToEat;

    public WalrusTest(int amountToEat) {
        this.amountToEat = amountToEat;
    }

    /**
     * Defines all values being tested in walrusStomachCapacity().
     * @return int[] of all values for amountToEat, being used in walrusStomachCapacity()
     */
    @DataPoints
    public static int[] data() {
        return new int[] { 1, 10, 100 };
    }

    /**
     * Always reset the walrus to its original state,
     * before executing a new unit test/theory.
     */
    @Before
    public void init() {
        walrus = new Walrus();
    }

    /**
     * Test if the walrus can eat certain amounts of food,
     * without throwing an Exception,
     * specified in the data() static function's return value.
     */
    @Theory
    public void walrusStomachCapacity() {
        FeedsWalrus feeder = new FeedsWalrus();
        for (int i = 0; i < amountToEat; i++) {
            feeder.feed(walrus, new CannedWalrusFood(new WalrusFood()));
        }
    }

    /**
     * Test if the walrus eating one and not eating one food,
     * then their stomach will contain only the stomach being eaten,
     * and won't contain the one not being eaten.
     */
    @Test
    public void getsRightFood() {
        WalrusFood food1 = new WalrusFood();
        CannedWalrusFood cannedFood1 = new CannedWalrusFood(food1);
        WalrusFood food2 = new WalrusFood();
        FeedsWalrus feeder = new FeedsWalrus();

        feeder.feed(walrus, cannedFood1);

        assertTrue("Food given was not eaten.", walrus.hasEaten(food1));
        assertFalse("Food not given was eaten.", walrus.hasEaten(food2));
    }

    /**
     * Test if food being canned,
     * then opened will contained the same food,
     * before it got canned.
     */
    @Test
    public void canReturnsFood() {
        WalrusFood food = new WalrusFood();
        CannedWalrusFood cannedFood = new CannedWalrusFood(food);
        OpensCan canOpener = new OpensCan();

        var openedFood = canOpener.open(cannedFood);

        assertEquals("Food must not change from canning, then opening it.",
                food, openedFood);
    }

    /**
     * Check if the walrus can eat food via a feeder,
     * or add the food directly to their stomach.
     */
    @Test
    public void multipleWaysToEat() {
        WalrusFood uncannedFood = new WalrusFood();
        CannedWalrusFood cannedFood = new CannedWalrusFood(uncannedFood);
        WalrusFood food = new WalrusFood();
        FeedsWalrus feeder = new FeedsWalrus();

        feeder.feed(walrus, cannedFood);
        walrus.addToStomach(food);

        assertTrue("Walrus' stomach must contain food being fed to them.",
                walrus.hasEaten(uncannedFood));
        assertTrue("Walrus' stomach must contain food being added to their stomach.",
                walrus.hasEaten(food));
    }

    /**
     * Test if walrus won't accept non-walrus food.
     * 
     * In this instance we try to feed them null,
     * making their stomach contain a null.
     */
    @Test
    public void acceptNonWalrusFood() {
        CannedWalrusFood nonWalrusFood = new CannedWalrusFood(null);
        FeedsWalrus feeder = new FeedsWalrus();

        feeder.feed(walrus, nonWalrusFood);

        assertTrue("Walrus' stomach must contain non-walrus food being eaten by them.",
                walrus.hasEaten(null));
    }
}
