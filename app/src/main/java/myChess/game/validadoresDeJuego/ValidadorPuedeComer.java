package myChess.game.validadoresDeJuego;

import edu.austral.dissis.chess.gui.PlayerColor;
import myChess.game.Pieza;
import myChess.game.Posicion;
import myChess.game.Tablero;
import myChess.game.User;

public class ValidadorPuedeComer implements ValidadorDeJuego{
    @Override
    public ResultSet validarJuego(Posicion posicionInicial, Posicion posicionFinal, Tablero tablero, User usuario) {
        int diffX = Math.abs(posicionFinal.getX() - posicionInicial.getX());
        if (diffX > 1) {
            return new ResultSet(tablero,"movimiento valido", false, false);
        }
        else {
            return verSiPuedoComer(tablero, usuario.getColor());
        }
    }

    public static ResultSet verSiPuedoComer(Tablero tablero, PlayerColor color) {
        for (int i = 0; i < tablero.getColumnas(); i++) {
            for (int j = 0; j < tablero.getFilas(); j++) {
                Posicion origen = new Posicion(i, j);
                if (tablero.tienePieza(origen) && tablero.obtenerPieza(origen).getOwner().getColor() == color) {
                    ResultSet resultSet = todasLasPosiciones(origen, tablero);
                    if (resultSet != null) {
                        return resultSet;
                    }
                }
            }
        }
        return new ResultSet(tablero, "Movimiento valido", false, false);
    }

    private static ResultSet todasLasPosiciones(Posicion inicio, Tablero tablero) {
        for (int i = 0; i < tablero.getColumnas(); i++) {
            for (int j = 0; j < tablero.getFilas(); j++) {
                Posicion destino = new Posicion(i, j);
                if (inicio.equals(destino)) continue;
                if (!tablero.tienePieza(destino)) {
                    Pieza pieza = tablero.obtenerPieza(inicio);
                    if (pieza.movimientoValido(inicio, destino, tablero) && Math.abs(inicio.getX() - destino.getX()) > 1) {
                        return new ResultSet(tablero, "Movimiento invalido, puedo comer", false, true);
                    }
                }
            }
        }
        return null;
    }
}
