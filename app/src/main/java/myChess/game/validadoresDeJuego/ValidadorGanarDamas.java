package myChess.game.validadoresDeJuego;

import edu.austral.dissis.chess.gui.PlayerColor;
import myChess.game.Pieza;
import myChess.game.Posicion;
import myChess.game.Tablero;
import myChess.game.User;

import java.util.Map;
import java.util.stream.Collectors;

public class ValidadorGanarDamas implements ValidadorDeJuego{
    @Override
    public ResultSet validarJuego(Posicion posicionInicial, Posicion posicionFinal, Tablero tablero, User usuario) {
        PlayerColor color = usuario.getColor();
        Map<Posicion, Pieza> piezas = tablero.getTodasLasPiezas();
        piezas = piezas.entrySet().stream().filter((entry) -> entry.getValue().getOwner().getColor() == color).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (puedeMover(piezas, tablero)) {
            return new ResultSet(tablero, "Movimiento valido", false, false);
        }
        else {
            return new ResultSet(true, tablero, "Ganaste");
        }
    }

    private boolean puedeMover(Map<Posicion, Pieza> piezas, Tablero tablero) {
        if (piezas.isEmpty()) return false;
        for (Map.Entry<Posicion, Pieza> entry : piezas.entrySet()) {
            Posicion posicion = entry.getKey();
            Pieza pieza = entry.getValue();
            for (int i = 0; i < tablero.getColumnas(); i++) {
                for (int j = 0; j < tablero.getFilas(); j++) {
                    Posicion destino = new Posicion(i, j);
                    if (posicion.equals(destino)) continue;
                    if (!tablero.tienePieza(destino)) {
                        if (pieza.movimientoValido(posicion, destino, tablero)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
