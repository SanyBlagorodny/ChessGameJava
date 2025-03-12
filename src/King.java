public class King extends ChessPiece {

    public King(String color) {
        super(color);
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        // Проверяем, что конечная позиция в пределах доски
        if (!checkPos(toLine) || !checkPos(toColumn)) return false;

        // Проверяем, что король двигается максимум на 1 клетку в любую сторону
        if (Math.abs(line - toLine) > 1 || Math.abs(column - toColumn) > 1) return false;

        // Проверяем, что король не попадает под шах
        if (isUnderAttack(chessBoard, toLine, toColumn)) return false;

        // Проверяем, что конечная клетка либо пуста, либо занята фигурой противника
        return chessBoard.board[toLine][toColumn] == null ||
                !chessBoard.board[toLine][toColumn].getColor().equals(color);
    }

    @Override
    public String getSymbol() {
        return "K";
    }

    public boolean isUnderAttack(ChessBoard chessBoard, int line, int column) {
        // Проверяем все клетки доски
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = chessBoard.board[i][j];
                if (piece != null && !piece.getColor().equals(color)) {
                    // Если фигура противника может атаковать данную клетку – она под атакой
                    if (piece.canMoveToPosition(chessBoard, i, j, line, column)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkPos(int pos) {
        return pos >= 0 && pos < 8;
    }
}
