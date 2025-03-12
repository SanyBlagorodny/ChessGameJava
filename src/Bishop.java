public class Bishop extends ChessPiece {
    public Bishop(String color) {
        super(color);
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        // Проверяем, что ход диагональный
        if (Math.abs(toLine - line) != Math.abs(toColumn - column)) {
            return false;
        }

        // Проверяем, что новая позиция на доске и конечная клетка либо пустая, либо с фигурой противника
        if (!checkPos(toLine) || !checkPos(toColumn) ||
                (chessBoard.board[toLine][toColumn] != null &&
                        chessBoard.board[toLine][toColumn].color.equals(this.color))) {
            return false;
        }

        // Определяем направление движения
        int rowStep = (toLine > line) ? 1 : -1;
        int colStep = (toColumn > column) ? 1 : -1;

        // Проверяем, есть ли фигуры на пути (слон не может перепрыгивать)
        int checkLine = line + rowStep;
        int checkColumn = column + colStep;

        while (checkLine != toLine && checkColumn != toColumn) {
            if (chessBoard.board[checkLine][checkColumn] != null) {
                return false;
            }
            checkLine += rowStep;
            checkColumn += colStep;
        }

        return true; // Если дошли сюда, значит ход возможен
    }

    @Override
    public String getSymbol() {
        return "B";
    }

    public boolean checkPos(int pos) {
        return pos >= 0 && pos <= 7;
    }
}
