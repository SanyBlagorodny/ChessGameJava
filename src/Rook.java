public class Rook extends ChessPiece {

    public Rook(String color) {
        super(color);
    }

    @Override
    public String getColor() {
        return this.color;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (!checkPos(line) || !checkPos(column) || !checkPos(toLine) || !checkPos(toColumn)) {
            return false; // Ход должен быть в пределах доски
        }

        if (line == toLine && column == toColumn) {
            return false; // Ладья не может оставаться на месте
        }

        if (line == toLine || column == toColumn) {
            return isPathClear(chessBoard, line, column, toLine, toColumn);
        }

        return false; // Ладья двигается только по прямым
    }

    @Override
    public String getSymbol() {
        return "R";
    }

    private boolean isPathClear(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        int stepLine = Integer.compare(toLine, line);   // -1, 0 или 1
        int stepColumn = Integer.compare(toColumn, column); // -1, 0 или 1

        int currentLine = line + stepLine;
        int currentColumn = column + stepColumn;

        while (currentLine != toLine || currentColumn != toColumn) {
            if (chessBoard.board[currentLine][currentColumn] != null) {
                return false; // На пути есть препятствие
            }
            currentLine += stepLine;
            currentColumn += stepColumn;
        }

        // Проверяем конечную клетку: если там фигура, она должна быть противника
        return chessBoard.board[toLine][toColumn] == null ||
                !chessBoard.board[toLine][toColumn].getColor().equals(this.color);
    }

    private boolean checkPos(int pos) {
        return pos >= 0 && pos <= 7;
    }
}
