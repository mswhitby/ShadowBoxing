import java.util.*;

class Const {
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    static String[] directions = new String[] {"Up", "Down", "Left", "Right"};
    static String[] directionsShort = new String[] {"u", "d", "l", "r"};
}


class Player {
    String name;
    String nextMove;
    ArrayList<String> moves;
    ArrayList<String> movesLeft;
    boolean isGuesser;
    boolean isComputer;

    public Player(String name) {
        this.name = name;
        this.isComputer = false;
        this.resetMoves();
    }

    public void updateGuesser(boolean isGuesser) {
        this.isGuesser = isGuesser;
    }

    static String getDirection(String letter) {
        return Const.directions[Arrays.asList(Const.directionsShort).indexOf(letter)];
    }

    public void getNextMove() {
        String nextMove = Const.scanner.nextLine();
        this.nextMove = getDirection(nextMove);
    }

     public void addMove(String move) {
        this.moves.add(move);
        this.movesLeft.remove(move);
     }

     public void resetMoves() {
        this.moves = new ArrayList<>();
        this.movesLeft = new ArrayList<>(Arrays.asList(Const.directions));
     }
}

class ComputerPlayer extends Player {

    public ComputerPlayer(String name) {
        super(name);
    }

    @Override
    public void getNextMove() {
        if (movesLeft.size() == 4) {
            int val = Const.random.nextInt(100);
            if (val <= 70) {
                String[] upDown = new String[] {"Up", "Down"};
                this.nextMove = upDown[Const.random.nextInt(2)];
            }

            else {
                String[] leftRight = new String[] {"Left", "Right"};
                this.nextMove = leftRight[Const.random.nextInt(2)];
            }
        } else {
            this.nextMove = movesLeft.get(Const.random.nextInt(movesLeft.size()));
        }
    }
}

class PlayShadowBoxing {
    Random random;
    Scanner scanner;
    Player player1;
    Player player2;
    ArrayList<Player> players;
    int round;
    int strikes;

    public PlayShadowBoxing(Player player1, Player player2) {
        this.random = Const.random;
        this.scanner = Const.scanner;

        this.player1 = player1;
        this.player2 = player2;
        this.players = new ArrayList<>(Arrays.asList(player1, player2));
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

            player1.getNextMove();
            player2.getNextMove();

            if (Objects.equals(player1.nextMove, "exit") || Objects.equals(player2.nextMove, "exit")) break;

            System.out.println("Round: " + round);
            player1.addMove(player1.nextMove);
            player2.addMove(player2.nextMove);

            Player guesser = players.get(0);
            Player defender = players.get(1);
            System.out.println(guesser.name + ": " + guesser.moves);
            System.out.println(defender.name + ": " + defender.moves);

            strikes = 0;
            for (int i = 1; i <= moves; i++) {
                if (player1.moves.get(i - 1).equals(player2.moves.get(i - 1))) strikes++;
            }

            System.out.printf("Strikes: %s, Moves %s %n%n", strikes, moves);
            if (strikes == moves) {
                moves++;
            } else {
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
}

public class ShadowBoxing {
    static Scanner scanner = Const.scanner;

    public static void main(String[] args) {
        System.out.println("What is your name");
        String playerName = scanner.nextLine();
        Player player = new Player(playerName);
        Player computer = new ComputerPlayer("Computer");
        PlayShadowBoxing game = new PlayShadowBoxing(player, computer);
        game.newGame();
    }
}


