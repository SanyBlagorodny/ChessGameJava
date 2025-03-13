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

        if (Math.abs(toLine - line) != Math.abs(toColumn - column)) {
            return false;
        }

        if (!checkPos(toLine) || !checkPos(toColumn) ||
                (chessBoard.board[toLine][toColumn] != null &&
                        chessBoard.board[toLine][toColumn].color.equals(this.color))) {
            return false;
        }

        int rowStep = (toLine > line) ? 1 : -1;
        int colStep = (toColumn > column) ? 1 : -1;

        int checkLine = line + rowStep;
        int checkColumn = column + colStep;

        while (checkLine != toLine && checkColumn != toColumn) {
            if (chessBoard.board[checkLine][checkColumn] != null) {
                return false;
            }
            checkLine += rowStep;
            checkColumn += colStep;
        }

        return true;
    }

    @Override
    public String getSymbol() {
        return "B";
    }

    public boolean checkPos(int pos) {
        return pos >= 0 && pos <= 7;
    }
}
