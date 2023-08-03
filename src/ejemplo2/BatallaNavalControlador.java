package ejemplo2;

public class BatallaNavalControlador {
    private BatallaNavalModelo modelo;
    private boolean isPlayer1Turn;

    public BatallaNavalControlador() {
        this.modelo = new BatallaNavalModelo();
        this.isPlayer1Turn = true;
    }

    public void playGame() {
        modelo.initializeGame();
        BatallaNavalVista.displayMessage("¡Bienvenido a Batalla Naval!");
        while (true) {
            char[][] currentBoard = isPlayer1Turn ? modelo.getBoardPlayer2() : modelo.getBoardPlayer1();
            BatallaNavalVista.printBoard(currentBoard);

            int[] coordinates = BatallaNavalVista.getShotCoordinates();
            int x = coordinates[0];
            int y = coordinates[1];

            boolean isHit = modelo.shoot(currentBoard, x, y);
            if (isHit) {
                BatallaNavalVista.displayMessage("¡Acertaste! Has hundido un barco.");
            } else {
                BatallaNavalVista.displayMessage("Agua... Sigue intentando.");
            }

            if (!modelo.hasRemainingShips(isPlayer1Turn ? modelo.getPlayer2Ships() : modelo.getPlayer1Ships())) {
                BatallaNavalVista.displayMessage(isPlayer1Turn ? "¡Jugador 1 ha ganado!" : "¡Jugador 2 ha ganado!");
                break;
            }

            isPlayer1Turn = !isPlayer1Turn;
        }
    }

    public static void main(String[] args) {
        BatallaNavalControlador juego = new BatallaNavalControlador();
        juego.playGame();
    }
}
