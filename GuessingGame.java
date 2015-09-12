//Kristy Carpenter, Computer Science II, Fall 2014, Section C (4th period)
//Assignment 5--Guessing Game option
//
//This program plays a guessing game with the user. After a brief introduction, it chooses a random
//integer within a certain range, then prompts the user for guesses. If the user's guess is not
//correct, they are told if the actual number is higher or lower. If the user's guess is not in the
//correct range, or if it's not an integer, then the program tells them that they need to guess an
//integer within the correct range, and prompts them to guess again. Once the user guesses
//correctly, they have the option to play again or stop. After they stop, the program produces
//statistics from their games. This program utilizes while loops, parameters, if/else statements
//different methods, and a scanner (as well as other techniques) to efficiently accomplish this.

//assumptions: the user will eventually make a good guess (an integer in the correct range), and
//the user will eventually guess correctly without making more than 1 million guesses. Anything
//that starts with a y means an affirmative answer to a question and anything that doesn't start
//with a y is not.

import java.util.*;

public class GuessingGame
{ 
   /**
     *this is the constant for max number chosen
     */
   public static final int MAX = 100;


   /**
     *This is where the program begins
     *@param args - the arguments to the program
     */
   public static void main(String[]args)
   {
      introHaiku();
      Scanner scanner = new Scanner(System.in);
      int nGuesses = game(scanner);
      int nGames = 1;
      int nAllGuesses = nGuesses;
      int bestGameGuess = nGuesses;
      char yOrN = playAgainCheck(nGuesses, scanner);
      while (yOrN == 'Y' || yOrN == 'y')
      {
         int tempGuessesFromLatestGame = game(scanner);
         if (tempGuessesFromLatestGame < bestGameGuess)
         {
            bestGameGuess = tempGuessesFromLatestGame;
         }
         nAllGuesses += tempGuessesFromLatestGame;
         nGames++;
         yOrN = playAgainCheck(nGuesses, scanner);
      }
      stats(nGames, nAllGuesses, bestGameGuess);
   }

   /**
     *This method prints the introductory haiku
     */
   public static void introHaiku()
   {
      System.out.println("Like foxes hunting");
      System.out.println("Pursue my numbers like mice--");
      System.out.println("Find answer to win");
   }

   /**
     *This method plays the guessing game with the user
     *
     *@param scanner - the scanner to take user input
     *@return nGuesses - the number of guesses it took to guess the answer for this game
     */
   public static int game(Scanner scanner)
   {  
      System.out.println("I'm thinking of a number between 1 and " + MAX + "...");
      Random r = new Random();
      int number = r.nextInt(MAX) + 1;
      //System.out.println(number); //FOR TESTING ONLY
      int guess = -100;
      boolean isEverythingOkay = false;
      int nGuesses = 0;
      while (guess != number)
      {
         System.out.print("Your guess? ");
         while (!isEverythingOkay)
         {
            while (!scanner.hasNextInt())
            {
               System.out.println("Please guess an integer between 1 and " + MAX + " (inclusive)");
               scanner.nextLine();
            }
            guess = scanner.nextInt();
            if (guess > MAX || guess < 1)
            {
               System.out.println("Please guess an integer between 1 and " + MAX + " (inclusive)");
            }
            else
            {
               isEverythingOkay = true;
               nGuesses++;
            }
            scanner.nextLine();
         } 
         if (guess > number)
         {
            System.out.println("It's lower.");
         }
         else if (guess < number)
         {
            System.out.println("It's higher.");
         }
         isEverythingOkay = false;
      }
      if (nGuesses != 1)
      {
         System.out.println("You got it right in " + nGuesses + " guesses!");
      }
      else
      {
         System.out.println("You got it right in 1 guess!");
      }
      return nGuesses;
   }
  
   /**
     *This method checks to see if the user wants to play again. If the user types anything that
     *starts with y, it is assumed that they want to play again.
     *
     *@param nGuesses - the total number of guesses made in the previous game
     *@param scanner - the scanner used to read the user's input
     *@return yOrN - the character that determines whether or not the user wants to play again
     */
   public static char playAgainCheck(int nGuesses, Scanner scanner)
   {
      System.out.print("Do you want to play again? ");
      String response = scanner.nextLine();
      System.out.println("");
      char yOrN = response.charAt(0);
      return yOrN;
   }
   
   /**
     *this method prints the statistics of all the games the user has played. It computes and prints
     *the total number of games played, the total number of guesses made, the average number of
     *guesses per game, and the least number of guesses taken to win a game
     *
     *@param nGames - the number of games that have been played
     *@param nAllGuesses - the total number of guesses that have been made
     *@param bestGameGuess - the number of guesses made in the best game
     */
   public static void stats(int nGames, int nAllGuesses, int bestGameGuess)
   {
      System.out.println("Overall results:");
      System.out.println("Total games \t= " + nGames);
      System.out.println("Total guesses \t= " + nAllGuesses);
      double averageGuesses = ((double) nAllGuesses) / ((double) nGames);
      System.out.printf("Guesses/game \t= %.1f\n", averageGuesses);
      System.out.println("Best game \t= " + bestGameGuess);
   }
}

/* Test plan
   guesses:                                                       what happens:
   an integer in the correct range higher than the number         tell the user the number is loewr
   an integer in the correct range lower than the number          tell the user the number is higher
   an integer outside the range                                   tell the user their guess is out of the range and make them guess again
   something not an integer (string, character, double, etc.)     tell the user their guess must be an integer and make them guess again
   the integer chosen by the program randomly                     tell the user they are correct and them them the nubmer of guesses it took them to get it right
   
   response to "Do you want to play again?"                       what happens:
   a string starting with y (yes, yeah, yea, etc.)                the game method is called so the user can play again
   a string starting with Y (YESS, Yo, Yep, etc.)                 the game method is called so the user can play again
   anything else(no, sure, 2, 3.1415926535, etc.)                 the stats method is called, the user doesn't play again
*/