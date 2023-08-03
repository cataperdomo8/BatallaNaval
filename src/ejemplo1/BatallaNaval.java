package ejemplo1;

import java.util.*;

class BatallaNaval {
    private final int boardSize = 10;
    private final int numShips = 10;
    private final int[] shipSizes = {1, 1, 1, 1, 2, 2, 3, 3, 3, 4};

    private char[][] boardPlayer1 = new char[boardSize][boardSize];
    private char[][] boardPlayer2 = new char[boardSize][boardSize];

    private List<int[]> player1Ships = new ArrayList<>();
    private List<int[]> player2Ships = new ArrayList<>();

    private Random random = new Random();

    private void initializeBoards() {
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

    private boolean hasRemainingShips(List<int[]> playerShips) {
        return playerShips.size() > 0;
    }

    private void printBoard(char[][] board) {
        for (char[] row : board) {
            System.out.println(Arrays.toString(row).replaceAll("[,\\[\\]]", "").replace(" ", ""));
        }
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("¡Bienvenido a Batalla Naval!");

        initializeBoards();
        placeShips(boardPlayer1, player1Ships);
        placeShips(boardPlayer2, player2Ships);

        int currentPlayer = 1;
        while (true) {
            System.out.printf("Turno del Jugador %d%n", currentPlayer);
            if (currentPlayer == 1) {
                System.out.println("Tablero Jugador 2:");
                printBoard(boardPlayer2);
            } else {
                System.out.println("Tablero Jugador 1:");
                printBoard(boardPlayer1);
            }

            int x, y;
            while (true) {
                System.out.print("Ingresa la fila (0-9): ");
                x = scanner.nextInt();
                System.out.print("Ingresa la columna (0-9): ");
                y = scanner.nextInt();
                if (isOutOfBounds(x, y)) {
                    System.out.println("Coordenadas fuera del rango. Intenta nuevamente.");
                } else if (currentPlayer == 1 && boardPlayer2[x][y] != 'O') {
                    System.out.println("Ya has disparado en esta posición. Intenta en otra.");
                } else if (currentPlayer == 2 && boardPlayer1[x][y] != 'O') {
                    System.out.println("Ya has disparado en esta posición. Intenta en otra.");
                } else {
                    break;
                }
            }

            char[][] board;
            List<int[]> playerShips;
            if (currentPlayer == 1) {
                board = boardPlayer2;
                playerShips = player2Ships;
            } else {
                board = boardPlayer1;
                playerShips = player1Ships;
            }

            if (isHit(board, x, y)) {
                System.out.println("¡Acertaste! Has hundido un barco.");
                board[x][y] = 'X';
                removeShip(playerShips, x, y);
            } else {
                System.out.println("Agua... Sigue intentando.");
                board[x][y] = '-';
            }

            if (!hasRemainingShips(player1Ships)) {
                System.out.println("¡Jugador 2 ha ganado!");
                break;
            } else if (!hasRemainingShips(player2Ships)) {
                System.out.println("¡Jugador 1 ha ganado!");
                break;
            }

            currentPlayer = 3 - currentPlayer;
        }
        scanner.close();
    }

    public static void main(String[] args) {
        BatallaNaval juego = new BatallaNaval();
        juego.playGame();
    }
}

