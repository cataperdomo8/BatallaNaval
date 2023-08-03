package ejemplo2;

import java.util.*;

public class BatallaNavalVista {
    private static final int boardSize = 10;

    public static void printBoard(char[][] board) {
        for (char[] row : board) {
            System.out.println(Arrays.toString(row).replaceAll("[,\\[\\]]", "").replace(" ", ""));
        }
    }

    public static int[] getShotCoordinates() {
        Scanner scanner = new Scanner(System.in);
        int[] coordinates = new int[2];
        System.out.print("Ingresa la fila (0-9): ");
        coordinates[0] = scanner.nextInt();
        System.out.print("Ingresa la columna (0-9): ");
        coordinates[1] = scanner.nextInt();
        return coordinates;
    }

    public static void displayMessage(String message) {
        System.out.println(message);
    }
}

