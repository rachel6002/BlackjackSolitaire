import java.util.ArrayList;
import java.util.Scanner;

public class BlackjackSolitaire {

	private Deck gameDeck = new Deck();

	private static String[][] grid; // new grid with 4 rows and 5 columns
	private ArrayList<Card> discardedCards; // array to keep track of cards discarded
	private Card[] playedCards = new Card[16]; // array to keep track of cards played to calculate score
	private ArrayList<Integer> playedSlots; // array to keep track of selected slots

	private int gameRound; // counts game rounds to see if game has ended

	// initializing grid
	public BlackjackSolitaire() {
		String[][] grid = new String[4][5];
		this.grid = grid;
		grid[0][0] = "1";
		grid[0][1] = "2";
		grid[0][2] = "3";
		grid[0][3] = "4";
		grid[0][4] = "5";
		grid[1][0] = "6";
		grid[1][1] = "7";
		grid[1][2] = "8";
		grid[1][3] = "9";
		grid[1][4] = "10";
		grid[2][0] = " ";
		grid[2][1] = "11";
		grid[2][2] = "12";
		grid[2][3] = "13";
		grid[2][4] = " ";
		grid[3][0] = " ";
		grid[3][1] = "14";
		grid[3][2] = "15";
		grid[3][3] = "16";
		grid[3][4] = " ";
	}

	public void printGrid() { // to print grid every time user plays
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 5; j++) { // printing with formatting
				if (grid[i][j].length() == 2) {
					System.out.printf(grid[i][j] + "  ");
				} else if (grid[i][j].length() == 1) {
					System.out.printf(grid[i][j] + "   ");
				} else {
					System.out.printf(grid[i][j] + " ");
				}

			}
			System.out.printf("\n");
		}
	}

	public void updateGrid(int userInput, String nextCard) { // next card will be called from nextCard() method in
																// updates grid and add played card to list of cards
																// played for final score calculation
		if (userInput == 1) {
			grid[0][0] = nextCard;
		} else if (userInput == 2) {
			grid[0][1] = nextCard;
		} else if (userInput == 3) {
			grid[0][2] = nextCard;
		} else if (userInput == 4) {
			grid[0][3] = nextCard;
		} else if (userInput == 5) {
			grid[0][4] = nextCard;
		} else if (userInput == 6) {
			grid[1][0] = nextCard;
		} else if (userInput == 7) {
			grid[1][1] = nextCard;
		} else if (userInput == 8) {
			grid[1][2] = nextCard;
		} else if (userInput == 9) {
			grid[1][3] = nextCard;
		} else if (userInput == 10) {
			grid[1][4] = nextCard;
		} else if (userInput == 11) {
			grid[2][1] = nextCard;
		} else if (userInput == 12) {
			grid[2][2] = nextCard;
		} else if (userInput == 13) {
			grid[2][3] = nextCard;
		} else if (userInput == 14) {
			grid[3][1] = nextCard;
		} else if (userInput == 15) {
			grid[3][2] = nextCard;
		} else if (userInput == 16) {
			grid[3][3] = nextCard;
		}

		if (userInput > 0 && userInput < 17) { // save value in playedCards Array for final scoring unless

			int playedCardIdx = userInput - 1; // finds index in playedCards array to store
												// value - userInput - 1
			playedCards[playedCardIdx] = gameDeck.get(0); // Update playedCards array with first card in
															// corresponding index, e.g. card from location 1 will be
															// stored in index 0
		}
	}

	public boolean checkGrid(int userInput, ArrayList<Integer> playedSlots) { // check if userInput is valid

		if (userInput < 0 || userInput > 16) {
			return false;
		}
		for (int i = 0; i < playedSlots.size(); i++) {
			if (userInput == playedSlots.get(i)) {
				return false;
			}
		}
		return true;
	}

	public int scoreGrid(int sum, int count) {

		int score = 0;

		if (sum == 21 && count == 2) { // sum is the total sum in row/col; count is count of locations in row/column in
										// grid
			score += 10;
		} else if (sum == 21 && count > 2 && count <= 5) {
			score += 7;
		} else if (sum == 20) {
			score += 5;
		} else if (sum == 19) {
			score += 4;
		} else if (sum == 18) {
			score += 3;
		} else if (sum == 17) {
			score += 2;
		} else if (sum <= 16) {
			score += 1;
		}
		return score;
	}

	public int rankScore(int rank) {
		int runningScore = 0;
		if (rank > 10 && rank < 14) { // cards J-K will be 10
			runningScore = 10;

		} else if (rank < 11 && rank > 0) { // cards 2-10 will be its value // Ace is 1 by default
			runningScore += rank;
		}
		return runningScore;
	}

	public int aceCount(int[] comboIdx) {
		int aceCount = 0;

		for (int i = 0; i < comboIdx.length; i++) {
			int rank = getPlayed(comboIdx[i]);
			if (rank == 1) {
				aceCount++;// counts number of aces in combinations
			}
		}
		return aceCount;
	}

	public int withAce(int[] comboIdx) { // calculates score for those combinations with ace
		int runningSum = 0;
		for (int i = 0; i < comboIdx.length; i++) {
			runningSum += rankScore(getPlayed(comboIdx[i]));
		}
		if (runningSum <= 11) { // before turning ace into 11, check if sum is smaller or equal to 11 (i.e.
			// 21-10 since ace is 1 by default). if yes, add 10 to turn into 11
			runningSum += 10;
		}
		return runningSum;
	}

	public int woAce(int[] comboIdx) { // calculates score for those combinations without ace
		int runningSum = 0;
		for (int i = 0; i < comboIdx.length; i++) {
			runningSum += rankScore(getPlayed(comboIdx[i]));
		}
		return runningSum;
	}

	public int score() {
		int score = 0; // return sum;
		int hand1 = 0; // individual hand score
		int hand2 = 0;
		int hand3 = 0;
		int hand4 = 0;
		int hand5 = 0;
		int hand6 = 0;
		int hand7 = 0;
		int hand8 = 0;
		int hand9 = 0;

		int[] combo1 = { 0, 1, 2, 3, 4 };// Slots 1-5
		int[] combo2 = { 5, 6, 7, 8, 9 };// Slots 6-10
		int[] combo3 = { 10, 11, 12 };// Slots 11-13
		int[] combo4 = { 13, 14, 15 };// Slots 14-16
		int[] combo5 = { 0, 5 }; // slots 1,6
		int[] combo6 = { 1, 6, 10, 13 }; // slots 2,7,11,14
		int[] combo7 = { 2, 7, 11, 14 }; // slots 3,8,12,15
		int[] combo8 = { 3, 8, 12, 15 }; // slots 4,9,13,16
		int[] combo9 = { 4, 9 }; // slots 5,10

		if (aceCount(combo1) == 0) { // Slots 1-5
			hand1 += woAce(combo1);
		} else if (aceCount(combo1) > 0) {
			hand1 += withAce(combo1);
		}

		if (aceCount(combo2) == 0) { // Slots 6-10
			hand2 += woAce(combo2);
		} else if (aceCount(combo2) > 0) {
			hand2 += withAce(combo2);
		}
		if (aceCount(combo3) == 0) { // Slots 11-13
			hand3 += woAce(combo3);
		} else if (aceCount(combo3) > 0) {
			hand3 += withAce(combo3);
		}
		if (aceCount(combo4) == 0) { // Slots 14-16
			hand4 += woAce(combo4);
		} else if (aceCount(combo4) > 0) {
			hand4 += withAce(combo4);
		}
		if (aceCount(combo5) > 0) { // slots 1,6
			hand5 += withAce(combo5); // sum to 12 if 2 aces
		} else if (aceCount(combo5) == 0) {
			hand5 += woAce(combo5);
		}

		if (aceCount(combo6) == 0) { // slots 2,7,11,14
			hand6 += woAce(combo6);
		} else if (aceCount(combo6) > 0) {
			hand6 += withAce(combo6);
		}
		if (aceCount(combo7) == 0) { // slots 3,8,12,15
			hand7 += woAce(combo7);
		} else if (aceCount(combo7) > 0) {
			hand7 += withAce(combo7);
		}

		if (aceCount(combo8) == 0) { // slots 4,9,13,16
			hand8 += woAce(combo8);
		} else if (aceCount(combo8) > 0) {
			hand8 += withAce(combo8);
		}

		if (aceCount(combo9) > 0) { // slots 5,10
			hand9 += withAce(combo9); // sum to 12 if 2 aces
		} else if (aceCount(combo9) == 0) {
			hand9 += woAce(combo9);
		}

		score = scoreGrid(hand1, 5) + scoreGrid(hand2, 5) + scoreGrid(hand3, 3) + scoreGrid(hand4, 3)
				+ scoreGrid(hand5, 2) + scoreGrid(hand6, 4) + scoreGrid(hand7, 4) + scoreGrid(hand8, 4)
				+ scoreGrid(hand9, 2); // 9 combinations
		return score;
	}

	public boolean gameEnded() { // returns true if game has ended

		return gameRound >= 16;
	}

	public void play() {
		// creating discarded deck in play
		ArrayList<Card> discardedCards = new ArrayList<Card>();
		this.discardedCards = discardedCards;
		ArrayList<Integer> playedSlots = new ArrayList<Integer>();
		this.playedSlots = playedSlots; // keep track of played slots
		gameDeck.shuffle(); // shuffle deck
		playedSlots.add(100);// just a placeholder for for loop to run

		Scanner scnr = new Scanner(System.in); // scanner for user input

		System.out.println("Game Start"); // game start

		while (!gameEnded()) {
			printGrid(); // prints grid

			String cardNum = gameDeck.nextCard(); // draws first card from gameDeck

			System.out.println("Current card in deck: " + cardNum
					+ ". Please enter location number <1-16> to place card or enter <0> to Discard");

			int input = scnr.nextInt();

			if (!checkGrid(input, playedSlots)) {
				System.out.println("Wrong input. Please reselect location or enter <0> to Discard.");
				continue; // if not true then re-loop
			}

			if (input != 0) {
				updateGrid(input, gameDeck.nextCard()); // updates grid
				gameDeck.removeCard(); // removes first card
				playedSlots.add(input); // add slot numbers played to array list
				gameRound++; // increment game round
			}

			if (input == 0) {
				if (discardedCards.size() < 4) { // max 4 cards to dispose
					discardedCards.add(gameDeck.get(0)); // add disposed card to discarded deck
					gameDeck.removeCard(); // removes first card
				} else if (discardedCards.size() == 4) { // if already more than 4 cards disposed
					System.out.println(
							"You have reached the maximum number of cards to discard. Please select a location number.");
					continue; // if already discarded four cards then re-loop
				}
			}
		}
		printGrid(); // updates grid the final time
		int finalScore = score(); // calculate and print score
		System.out.println("Game Over! You scored " + finalScore + " points.");
	}

	public int getPlayed(int i) { // for getting rank in played card stack
		return playedCards[i].getRank();
	}
}
