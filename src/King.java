public class King extends ChessPiece {
    private boolean hasMoved = false;

    public King(String color) {
        super(color);
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (!checkPos(toLine) || !checkPos(toColumn)) return false;

        if (Math.abs(line - toLine) <= 1 && Math.abs(column - toColumn) <= 1) {
            return chessBoard.board[toLine][toColumn] == null ||
                    !chessBoard.board[toLine][toColumn].getColor().equals(color);
        }

        if (!hasMoved && line == toLine && (toColumn == 2 || toColumn == 6)) {
            return canCastle(chessBoard, column, toColumn);
        }

        return false;
    }

    @Override
    public String getSymbol() {
        return "K";
    }

    public boolean isUnderAttack(ChessBoard chessBoard, int line, int column) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = chessBoard.board[i][j];
                if (piece != null && !piece.getColor().equals(color)) {
                    if (piece.canMoveToPosition(chessBoard, i, j, line, column)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setMoved() {
        this.hasMoved = true;
    }

    private boolean canCastle(ChessBoard chessBoard, int fromColumn, int toColumn) {
        int row = getColor().equals("White") ? 0 : 7;
        int rookColumn = (toColumn == 2) ? 0 : 7; // 0 - длинная рокировка, 7 - короткая

        if (!(chessBoard.board[row][rookColumn] instanceof Rook rook) || rook.hasMoved()) {
            return false;
        }

        int step = (toColumn == 2) ? -1 : 1; // Двигаемся влево или вправо
        for (int i = fromColumn + step; i != rookColumn; i += step) {
            if (chessBoard.board[row][i] != null) {
                return false;
            }
        }

        return !isUnderAttack(chessBoard, row, fromColumn) &&
                !isUnderAttack(chessBoard, row, fromColumn + step) &&
                !isUnderAttack(chessBoard, row, toColumn);
    }

    public boolean checkPos(int pos) {
        return pos >= 0 && pos < 8;
    }
}
