public class ChessBoard {
    public ChessPiece[][] board = new ChessPiece[8][8];
    String nowPlayer;

    public ChessBoard(String nowPlayer) {
        this.nowPlayer = nowPlayer;
    }

    public boolean moveToPosition(int startLine, int startColumn, int endLine, int endColumn) {
        if (!checkPos(startLine) || !checkPos(startColumn) || !checkPos(endLine) || !checkPos(endColumn)) {
            return false;
        }

        ChessPiece piece = board[startLine][startColumn];
        if (piece == null) {
            System.out.println("Выбранная клетка пуста.");
            return false;
        }

        System.out.println("Фигура: " + piece.getSymbol() + ", Цвет: " + piece.getColor());

        if (!nowPlayer.equals(piece.getColor())) {
            System.out.println("Сейчас ходит " + nowPlayer + ". Нельзя двигать чужие фигуры!");
            return false;
        }

        if (!piece.canMoveToPosition(this, startLine, startColumn, endLine, endColumn)) {
            return false;
        }

        ChessPiece targetPiece = board[endLine][endColumn];
        if (targetPiece != null && targetPiece.getColor().equals(piece.getColor())) {
            return false;
        }

        System.out.println("Перемещаем " + piece.getSymbol() + " с " + (char) ('a' + startColumn) + (8 - startLine) +
                " на " + (char) ('a' + endColumn) + (8 - endLine));

        board[endLine][endColumn] = piece;
        board[startLine][startColumn] = null;

        if (piece instanceof King) {
            ((King) piece).setMoved();
        } else if (piece instanceof Rook) {
            ((Rook) piece).setMoved();
        }

        nowPlayer = nowPlayer.equals("White") ? "Black" : "White";
        System.out.println("Следующий ход: " + nowPlayer);
        return true;
    }

    public boolean castling(boolean kingside) {
        int row = nowPlayer.equals("White") ? 0 : 7;
        int kingCol = 4;
        int rookCol = kingside ? 7 : 0;
        int kingNewCol = kingside ? 6 : 2;
        int rookNewCol = kingside ? 5 : 3;

        if (!(board[row][kingCol] instanceof King king) || king.hasMoved()) {
            System.out.println("Рокировка невозможна: король уже двигался.");
            return false;
        }

        if (!(board[row][rookCol] instanceof Rook rook) || rook.hasMoved()) {
            System.out.println("Рокировка невозможна: ладья уже двигалась.");
            return false;
        }

        int step = kingside ? 1 : -1;
        for (int i = kingCol + step; i != rookCol; i += step) {
            if (board[row][i] != null) {
                System.out.println("Рокировка невозможна: между королем и ладьей есть фигуры.");
                return false;
            }
        }

        board[row][kingCol] = null;
        board[row][rookCol] = null;
        board[row][kingNewCol] = king;
        board[row][rookNewCol] = rook;

        king.setMoved();
        rook.setMoved();

        nowPlayer = nowPlayer.equals("White") ? "Black" : "White";
        System.out.println("Рокировка выполнена.");
        return true;
    }

    public boolean checkPos(int pos) {
        return pos >= 0 && pos <= 7;
    }

    public void printBoard() {
        System.out.println("Turn " + nowPlayer);
        System.out.println();
        System.out.println("Player 2 (Black)");
        System.out.println();

        System.out.println("    a     b    c    d    e    f    g    h");
        System.out.println("  ┌────┬────┬────┬────┬────┬────┬────┬────┐");

        for (int i = 7; i >= 0; i--) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < 8; j++) {
                System.out.print("│ ");
                ChessPiece piece = board[i][j];
                if (piece == null) {
                    if ((i + j) % 2 == 0) {
                        System.out.print("  ");
                    } else {
                        System.out.print("██");
                    }
                } else {
                    String colorSymbol = piece.getColor().substring(0, 1).toLowerCase();
                    System.out.print(piece.getSymbol() + colorSymbol);
                }
                System.out.print(" ");
            }
            System.out.println("│ " + (i + 1));

            if (i > 0) {
                System.out.println("  ├────┼────┼────┼────┼────┼────┼────┼────┤");
            }
        }

        System.out.println("  └────┴────┴────┴────┴────┴────┴────┴────┘");
        System.out.println("    a     b    c    d    e    f    g    h");
        System.out.println();
        System.out.println("Player 1 (White)");
        System.out.println();
    }
}
