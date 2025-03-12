public class ChessBoard {
    public ChessPiece[][] board = new ChessPiece[8][8]; // Двумерный массив для хранения шахматных фигур
    String nowPlayer; // Переменная для отслеживания текущего игрока

    public ChessBoard(String nowPlayer) {
        this.nowPlayer = nowPlayer; // Устанавливаем, кто ходит первым (обычно "White")
    }

    public boolean moveToPosition(int startLine, int startColumn, int endLine, int endColumn) {
        // Проверяем, что координаты находятся в допустимых границах (0-7)
        if (!checkPos(startLine) || !checkPos(startColumn) || !checkPos(endLine) || !checkPos(endColumn)) {
            return false;
        }

        ChessPiece piece = board[startLine][startColumn]; // Получаем фигуру с начальной позиции
        if (piece == null) {
            System.out.println("Выбранная клетка пуста.");
            return false;
        }

        // Вывод информации о фигуре для отладки
        System.out.println("Фигура: " + piece.getSymbol() + ", Цвет: " + piece.getColor());

        // Проверяем, что ходит именно тот игрок, чья сейчас очередь
        if (!nowPlayer.equals(piece.getColor())) {
            System.out.println("Сейчас ходят " + nowPlayer + ". Нельзя двигать чужие фигуры!");
            return false;
        }

        // Проверяем, может ли фигура сделать такой ход
        if (!piece.canMoveToPosition(this, startLine, startColumn, endLine, endColumn)) {
            return false;
        }

        // Проверяем, не стоит ли на целевой клетке фигура того же цвета
        ChessPiece targetPiece = board[endLine][endColumn];
        if (targetPiece != null && targetPiece.getColor().equals(piece.getColor())) {
            return false;
        }

        // Выводим информацию о перемещении фигуры
        System.out.println("Перемещаем " + piece.getSymbol() + " с " + (char) ('a' + startColumn) + (8 - startLine) +
                " на " + (char) ('a' + endColumn) + (8 - endLine));

        // Перемещаем фигуру
        board[endLine][endColumn] = piece;
        board[startLine][startColumn] = null;

        // Меняем ход игрока
        nowPlayer = nowPlayer.equals("White") ? "Black" : "White";
        System.out.println("Следующий ход: " + nowPlayer);
        return true;
    }

    public boolean checkPos(int pos) {
        return pos >= 0 && pos <= 7; // Проверка, что координата в пределах доски
    }

    public void printBoard() {
        System.out.println("Turn " + nowPlayer);
        System.out.println();
        System.out.println("Player 2 (Black)");
        System.out.println();

        // Вывод координатных обозначений
        System.out.println("    a     b    c    d    e    f    g    h");
        System.out.println("  ┌────┬────┬────┬────┬────┬────┬────┬────┐");

        // Вывод доски построчно
        for (int i = 7; i >= 0; i--) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < 8; j++) {
                System.out.print("│ ");
                ChessPiece piece = board[i][j];
                if (piece == null) {
                    // Отображение черных и белых клеток
                    if ((i + j) % 2 == 0) {
                        System.out.print("  ");
                    } else {
                        System.out.print("██");
                    }
                } else {
                    // Вывод символа фигуры с указанием ее цвета
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

        // Вывод нижней границы доски
        System.out.println("  └────┴────┴────┴────┴────┴────┴────┴────┘");
        System.out.println("    a     b    c    d    e    f    g    h");
        System.out.println();
        System.out.println("Player 1 (White)");
        System.out.println();
    }
}
