public class Pawn extends ChessPiece {
    public Pawn(String color) {
        super(color);
    }

    @Override
    public String getColor() {
        return this.color;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (!checkPos(toLine) || !checkPos(toColumn)) {
            return false;
        }

        if (line == toLine && column == toColumn) {
            return false;
        }

        int direction = getColor().equals("White") ? 1 : -1;
        int deltaLine = toLine - line;
        int deltaColumn = Math.abs(toColumn - column);

        if (deltaLine == direction && deltaColumn == 0) {
            return chessBoard.board[toLine][toColumn] == null;
        }

        if (deltaLine == 2 * direction && deltaColumn == 0) {
            int startRow = getColor().equals("White") ? 1 : 6;
            if (line == startRow && chessBoard.board[toLine][toColumn] == null && chessBoard.board[line + direction][column] == null) {
                return true;
            }
        }

        if (deltaLine == direction && deltaColumn == 1) {
            return chessBoard.board[toLine][toColumn] != null &&
                    !chessBoard.board[toLine][toColumn].getColor().equals(this.color);
        }

        return false;
    }

    @Override
    public String getSymbol() {
        return "P";
    }

    public boolean checkPos(int pos) {
        return pos >= 0 && pos < 8;
    }
}
