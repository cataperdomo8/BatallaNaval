package ejemplo2;

import java.util.*;

public class BatallaNavalModelo {
    private final int boardSize = 10;
    private final int numShips = 10;
    private final int[] shipSizes = {1, 1, 1, 1, 2, 2, 3, 3, 3, 4};

    private char[][] boardPlayer1 = new char[boardSize][boardSize];
    private char[][] boardPlayer2 = new char[boardSize][boardSize];

    private List<int[]> player1Ships = new ArrayList<>();
    private List<int[]> player2Ships = new ArrayList<>();

    private Random random = new Random();

    public void initializeBoards() {
        for (int i = 0; i < boardSize; i++) {
            Arrays.fill(boardPlayer1[i], 'O');
            Arrays.fill(boardPlayer2[i], 'O');
        }
    }

    private boolean isOutOfBounds(int x, int y) {
        return x < 0 || x >= boardSize || y < 0 || y >= boardSize;
    }

    private boolean isValidPlacement(char[][] board, int x, int y, int directionX, int directionY, int shipSize) {
        for (int i = 0; i < shipSize; i++) {
            int newX = x + i * directionX;
            int newY = y + i * directionY;
            if (isOutOfBounds(newX, newY) || board[newX][newY] != 'O') {
                return false;
            }
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    int adjX = newX + dx;
                    int adjY = newY + dy;
                    if (!isOutOfBounds(adjX, adjY) && board[adjX][adjY] != 'O') {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void placeShips(char[][] board, List<int[]> playerShips) {
        int[] directionsX = {0, 1}; // Horizontal o Vertical
        int[] directionsY = {1, 0};

        for (int shipSize : shipSizes) {
            while (true) {
                int x = random.nextInt(boardSize);
                int y = random.nextInt(boardSize);
                int direction = random.nextInt(2);
                if (isValidPlacement(board, x, y, directionsX[direction], directionsY[direction], shipSize)) {
                    for (int i = 0; i < shipSize; i++) {
                        int newX = x + i * directionsX[direction];
                        int newY = y + i * directionsY[direction];
                        board[newX][newY] = 'S';
                        playerShips.add(new int[]{newX, newY});
                    }
                    break;
                }
            }
        }
    }

    private boolean isHit(char[][] board, int x, int y) {
        return board[x][y] == 'S';
    }

    private void removeShip(List<int[]> playerShips, int x, int y) {
        Iterator<int[]> iterator = playerShips.iterator();
        while (iterator.hasNext()) {
            int[] ship = iterator.next();
            if (ship[0] == x && ship[1] == y) {
                iterator.remove();
                return;
            }
        }
    }

    public boolean hasRemainingShips(List<int[]> playerShips) {
        return playerShips.size() > 0;
    }

    public char[][] getBoardPlayer1() {
        return boardPlayer1;
    }

    public char[][] getBoardPlayer2() {
        return boardPlayer2;
    }

    public List<int[]> getPlayer1Ships() {
        return player1Ships;
    }

    public List<int[]> getPlayer2Ships() {
        return player2Ships;
    }

    public void initializeGame() {
        initializeBoards();
        placeShips(boardPlayer1, player1Ships);
        placeShips(boardPlayer2, player2Ships);
    }

    public boolean shoot(char[][] board, int x, int y) {
        if (isOutOfBounds(x, y) || board[x][y] == '-' || board[x][y] == 'X') {
            return false;
        }

        if (isHit(board, x, y)) {
            board[x][y] = 'X';
            removeShip(board == boardPlayer1 ? player2Ships : player1Ships, x, y);
        } else {
            board[x][y] = '-';
        }
        return true;
    }
}
