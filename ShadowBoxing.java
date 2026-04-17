import java.util.*;

class Player {
    String name;
    ArrayList<String> moves;
    ArrayList<String> movesLeft;
    boolean isGuesser;
    boolean isComputer;

    static String[] directions = new String[] {"Up", "Down", "Left", "Right"};

    public Player(String name) {
        this.name = name;
        this.isComputer = false;
        this.resetMoves();
    }

    public Player(String name, boolean isComputer) {
        this.name = name;
        this.isComputer = isComputer;
        this.resetMoves();
    }

    public void updateGuesser(boolean isGuesser) {
        this.isGuesser = isGuesser;
     }

     public void addMove(String move) {
        this.moves.add(move);
        this.movesLeft.remove(move);
     }

     public void resetMoves() {
        this.moves = new ArrayList<>();
        this.movesLeft = new ArrayList<>(Arrays.asList(directions));
     }
}

class PlayShadowBoxing {
    Random random;
    Scanner scanner;
    Player player;
    Player computer;
    ArrayList<Player> players;
    int round;
    int strikes;

    static String[] directions = new String[] {"Up", "Down", "Left", "Right"};
    static String[] directionsShort = new String[] {"u", "d", "l", "r"};


    public PlayShadowBoxing(Player player, Player computer) {
        this.random = new Random();
        this.scanner = new Scanner(System.in);

        this.player = player;
        this.computer = computer;
        this.players = new ArrayList<>(Arrays.asList(player, computer));
    }

    public void newGame() {
        getFirstGuesser();
        round = 1;
        playRound();
    }


    public void playRound() {

        int moves = 1;
        while (true) {
            System.out.println("Enter your move (u/d/l/r)");
            String nextMove = scanner.nextLine();

            if (Objects.equals(nextMove, "exit")) break;

            System.out.println("Round: " + round);
            player.addMove(getDirection(nextMove));
            computer.addMove(getCompMove(computer));

            Player guesser = players.get(0);
            Player defender = players.get(1);
            System.out.println(guesser.name + ": " + guesser.moves);
            System.out.println(defender.name + ": " + defender.moves);

            strikes = 0;
            for (int i = 1; i <= moves; i++) {
                if (player.moves.get(i - 1).equals(computer.moves.get(i - 1))) strikes++;
            }

            System.out.printf("Strikes: %s, Moves %s %n%n", strikes, moves);
            if (strikes == moves) {
                moves++;
            }
            else {
                moves = 1;
                swapRoles();
            }

            if (strikes == 3) {
                System.out.printf("%s is the winner! %n", guesser.name);
                System.out.printf("Better luck next time %s %n", defender.name);
                break;
            }

        }
    }

    void getFirstGuesser() {
        int first = random.nextInt(2);
        Player guesser = players.get(first);
        Player defender = players.get(1 - first);

        guesser.updateGuesser(true);
        defender.updateGuesser(false);

        if (first == 1) Collections.rotate(players, 1);
        // System.out.printf("First: %s, guesser: %s, defender: %s %n%n", first, guesser.name, defender.name);

        System.out.printf("%s will guess first, and %s will defend %n", guesser.name, defender.name);
    }

    void swapRoles() {
        Collections.rotate(players, 1);
        Player guesser = players.get(0);
        Player defender = players.get(1);

        guesser.updateGuesser(true);
        defender.updateGuesser(false);
        System.out.printf("%s is now guessing, and %s is defending %n", guesser.name, defender.name);

        guesser.resetMoves();
        defender.resetMoves();
    }

    static String getDirection(String letter) {
        return directions[Arrays.asList(directionsShort).indexOf(letter)];
    }

    String getCompMove(Player player) {
        // We can ues a "Character" array to store all moves
        // String[] directions = {"u", "d", "l", "r"};
        // We generate a random number from 0 to 4 to select a move by index
        return player.movesLeft.get(random.nextInt(player.movesLeft.size()));
    }

}

public class ShadowBoxing {
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) {
        System.out.println("What is your name");
        String playerName = scanner.nextLine();
        Player player = new Player(playerName);
        Player computer = new Player("Computer", true);
        PlayShadowBoxing game = new PlayShadowBoxing(player, computer);
        game.newGame();
    }
}


