package com.nighthawk.spring_portfolio.mvc.car;

public class Betting {

    private int points;

    public Betting(int startingPoints) { // method for the actual Betting with parameter being the number of points the user begins with
        this.points = startingPoints;
    }

    public void placeBet(int betAmount, boolean isGuessCorrect) {
        if (betAmount > points) {
            System.out.println("Insufficient points to place bet."); 
            // user cannot bet a number of points greater than the number of points they currently have, ex. can't be 501 points if they have 500 pts 
            return;
        }

        if (isGuessCorrect) { // if the user guesses correctly, they can earn double the points that they bet
            points += betAmount * 2; 
            System.out.println("Correct guess! You won " + (betAmount * 2) + " points.");
        } else {
            points -= betAmount; // if the user guesses correctly, the user loses the number of points that they bet
            System.out.println("Wrong guess! You lost " + betAmount + " points.");
        }

        System.out.println("Current points: " + points);
    }

    public static void main(String[] args) {
        Betting game = new Betting(500); // example call where user starts with 500 points

        // Example calls to placeBet method
        game.placeBet(150, true); 
        game.placeBet(200, false);
    }
}
