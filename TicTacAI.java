import java.util.*;
public class TicTacAI {
    static private String choice = "";
    static private char[][] board = {{'_', '_', '_'},
                                     {'_', '_', '_'},
                                     {'_', '_', '_'}};

    private static boolean firstMove = true; // X moves first

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Tic-Tac-Toe! Enter 1 if you would like to go first or 2 if you would like to go second.");
        int x = sc.nextInt();

        display(board);

        if(x  ==  1) {
            System.out.println("Enter row and col");
            board[sc.nextInt()][sc.nextInt()] = 'o';
            display(board);
            firstMove = false;
        }
        
        while(true) {
            applyMinMax(board);
            board[choice.charAt(0) - 48][choice.charAt(1) - 48] = 'x';
            display(board);

            if(win(board, true)) {
                System.out.println("Computer won!");
                break;
            }

            if(filled(board)) {
                System.out.println("It's a draw!");
                break;
            }

            System.out.println("Enter row and col");
            board[sc.nextInt()][sc.nextInt()] = 'o';
            display(board);

            if(filled(board)) {
                System.out.println("It's a draw!");
                break;
            }
       }
    }

    private static void display(char[][] board) {
        System.out.println("Current board:");
        for (int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++)
                System.out.print(board[i][j] + " ");
            System.out.println();
        }
    }

    private static int score(char board[][], boolean turn) { //turn is true if current turn is of O
        if (win(board, turn))
            return turn ? 10 : -10;
        else
            return 0;
    }

    private static boolean win(char board[][], boolean turn) {
        char tmp = turn ? 'x' : 'o';
        if (board[0][0] == tmp) {
            if ((board[0][1]  ==  board[0][0]&&board[0][1] == board[0][2])||(board[0][0] == board[1][0]&&board[0][0] == board[2][0])||(board[0][0] == board[1][1]&&board[0][0] == board[2][2]))
                return true;
        }
        if (board[0][2] == tmp) {
            if ((board[0][2] == board[1][2]&&board[0][2] == board[2][2])||(board[0][2] == board[1][1]&&board[0][2] == board[2][0]))
                return true;
        }
        if (board[0][1] == tmp) {
            if (board[0][1] == board[1][1]&&board[1][1] == board[2][1])
                return true;
        }
        if (board[1][0] == tmp) {
            if (board[1][0] == board[1][1]&&board[1][1] == board[1][2])
                return true;
        }
        if (board[2][2] == tmp) {
            if (board[2][2] == board[2][1]&&board[1][1] == board[2][0])
                return true;
        }

        return false;
    }

    private static boolean isTurn(char board[][]) { //return true if x played last
        int x = 0, o = 0;
        for(char a[]:board)
            for(char b:a) {
                if (b == 'x')
                    x++;
                else if (b == 'o')
                    o++;
            }
        return firstMove ? x > o :x == o;
    }

    private static int applyMinMax(char board[][]) {
        int tmp = score(board,isTurn(board));
        if(tmp != 0 || filled(board))
            return tmp;
        else {
            char pos[][] = board.clone();
            List<String> moves = new ArrayList<>();
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    if (pos[i][j] == '_')
                        moves.add(i + "" + j);
            int scores[] = new int[moves.size()];
            for (int i = 0;i < scores.length; i++) {
                pos[moves.get(i).charAt(0) - 48][moves.get(i).charAt(1) - 48] = isTurn(board) ? 'o' : 'x';
                scores[i] = applyMinMax(pos);
                pos[moves.get(i).charAt(0) - 48][moves.get(i).charAt(1) - 48] = '_';
            }
            String tmp2 = findVal(scores);
            if(!isTurn(board)) {
                choice = moves.get(tmp2.charAt(0)-48);
                return scores[tmp2.charAt(0)-48];
            } else {
                choice = moves.get(tmp2.charAt(1)-48);
                return scores[tmp2.charAt(1)-48];
            }
        }
    }
    
    private static String findVal(int numbers[]) {
        int smallest = numbers[0], largest = numbers[0], t1 = 0, t2 = 0;
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] > largest) {
                largest = numbers[i];
                t1 = i;
            } else if (numbers[i] < smallest) {
                smallest = numbers[i];
                t2 = i;
            }
        }
        return t1 + "" + t2;
    }

    private static boolean filled(char board[][]) {
        for (char a[]:board)
            for (char b:a)
                if (b == '_')
                    return false;
        return true;
    }
}