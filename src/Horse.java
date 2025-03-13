public class Horse extends ChessPiece {
    public Horse(String color) {
        super(color);
    }

    @Override
    public String getColor() {
        return this.color;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {

        if (!checkPos(toLine) || !checkPos(toColumn)) return false;

        int dx = Math.abs(toLine - line);
        int dy = Math.abs(toColumn - column);
        if (!((dx == 2 && dy == 1) || (dx == 1 && dy == 2))) return false;

        if (chessBoard.board[toLine][toColumn] != null &&
                chessBoard.board[toLine][toColumn].color.equals(this.color)) {
            return false;
        }

        return true;
    }

    @Override
    public String getSymbol() {
        return "H";
    }

    public boolean checkPos(int pos) {
        return pos >= 0 && pos <= 7;
    }
}
