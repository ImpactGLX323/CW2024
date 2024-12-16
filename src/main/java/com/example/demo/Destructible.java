package com.example.demo;

/**
 * The {@code Destructible} interface defines the contract for objects that can take damage and be destroyed.
 * Any class implementing this interface should provide the behavior for taking damage and destruction.
 */
public interface Destructible {

    /**
     * Method to apply damage to an object. The specific implementation will define how damage is taken.
     */
    void takeDamage();

    /**
     * Method to destroy the object. The specific implementation will define the destruction process.
     */
    void destroy();
}
