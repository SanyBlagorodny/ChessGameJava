public class Rook extends ChessPiece {
    private boolean hasMoved = false;

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
            return false;
        }

        if (line == toLine && column == toColumn) {
            return false;
        }

        if (line == toLine || column == toColumn) {
            return isPathClear(chessBoard, line, column, toLine, toColumn);
        }

        return false;
    }

    @Override
    public String getSymbol() {
        return "R";
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setMoved() {
        this.hasMoved = true;
    }

    private boolean isPathClear(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        int stepLine = Integer.compare(toLine, line);   // -1, 0 или 1
        int stepColumn = Integer.compare(toColumn, column); // -1, 0 или 1

        int currentLine = line + stepLine;
        int currentColumn = column + stepColumn;

        while (currentLine != toLine || currentColumn != toColumn) {
            if (chessBoard.board[currentLine][currentColumn] != null) {
                return false;
            }
            currentLine += stepLine;
            currentColumn += stepColumn;
        }

        return chessBoard.board[toLine][toColumn] == null ||
                !chessBoard.board[toLine][toColumn].getColor().equals(this.color);
    }

    private boolean checkPos(int pos) {
        return pos >= 0 && pos <= 7;
    }
}
