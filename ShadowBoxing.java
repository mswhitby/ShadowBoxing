import java.util.*;

class Player {
    String name;
    ArrayList<String> moves;
    ArrayList<String> movesLeft;
    boolean isGuesser;
    boolean isComputer;

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
        this.movesLeft = new ArrayList<>();
        this.movesLeft.add("Up");
        this.movesLeft.add("Down");
        this.movesLeft.add("Left");
        this.movesLeft.add("Right");
     }
}

class PlayShadowBoxing {
    Random random;
    Scanner scanner;
    Player player1;
    Player player2;
    int round;
    int strikes;


    public PlayShadowBoxing(Player player1, Player player2) {
        this.random = new Random();
        this.scanner = new Scanner(System.in);
        this.player1 = player1;
        this.player2 = player2;
        this.round = 1;

        newGame();
    }

    public void newGame() {
        getFirstGuesser();
        playRound();
    }


    public void playRound() {

        while (true) {
            System.out.println("Enter your move (u/d/l/r)");
            String nextMove = scanner.nextLine();

            if (Objects.equals(nextMove, "exit")) break;

            System.out.println("Round: " + round);
            player1.addMove(getDirection(nextMove));
            player2.addMove(getCompMove(player2));

            System.out.println(player1.name + ": " + player1.moves);
            System.out.println(player2.name + ": " + player2.moves);

            strikes = 0;
            for (int i = 1; i <= round; i++) {

                if (player1.moves.get(round - 1).equals(player2.moves.get(round - 1))) strikes++;
            }

            System.out.println("Strikes: " + strikes);
            if (strikes == round) {
                round++;
            }
            else {
                round = 1;
                player1.resetMoves();;
                player2.resetMoves();
            }









        }
    }

    void getFirstGuesser() {
        Player[] players = {player1, player2};
        int first = random.nextInt(2);
        Player guesser = players[first];
        Player defender = players[1 - first];

        guesser.updateGuesser(true);
        defender.updateGuesser(false);
    }

    static String getDirection(String d) {
        switch (d.toLowerCase()) {
            case "u": return "Up";
            case "d": return "Down";
            case "l": return "Left";
            case "r": return "Right";
            default: return null;
        }
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
    }
}


