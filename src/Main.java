import java.util.Scanner;

public class Main {
    public static ChessBoard buildBoard() {
        ChessBoard board = new ChessBoard("White");

        board.board[0][0] = new Rook("White");
        board.board[0][1] = new Horse("White");
        board.board[0][2] = new Bishop("White");
        board.board[0][3] = new Queen("White");
        board.board[0][4] = new King("White");
        board.board[0][5] = new Bishop("White");
        board.board[0][6] = new Horse("White");
        board.board[0][7] = new Rook("White");
        for (int i = 0; i < 8; i++) {
            board.board[1][i] = new Pawn("White");
        }

        board.board[7][0] = new Rook("Black");
        board.board[7][1] = new Horse("Black");
        board.board[7][2] = new Bishop("Black");
        board.board[7][3] = new Queen("Black");
        board.board[7][4] = new King("Black");
        board.board[7][5] = new Bishop("Black");
        board.board[7][6] = new Horse("Black");
        board.board[7][7] = new Rook("Black");
        for (int i = 0; i < 8; i++) {
            board.board[6][i] = new Pawn("Black");
        }
        return board;
    }

    public static void main(String[] args) {
        ChessBoard board = buildBoard();
        Scanner scanner = new Scanner(System.in);

        System.out.println("""
                Чтобы проверить игру, вводите команды:
                'exit' - выход
                'replay' - перезапуск игры
                'castling kingside' - короткая рокировка
                'castling queenside' - длинная рокировка
                'move a2 a4' - перемещение фигуры
                """);

        board.printBoard();

        while (true) {
            System.out.println("Введите команду:");
            String s = scanner.nextLine().trim().toLowerCase();

            if (s.equals("exit")) break;
            else if (s.equals("replay")) {
                board = buildBoard();
                board.printBoard();
            } else if (s.startsWith("move")) {
                String[] parts = s.split(" ");
                if (parts.length != 3) {
                    System.out.println("Неверный формат. Используйте: move a2 a4");
                    continue;
                }

                try {
                    int startLine = Integer.parseInt(parts[1].substring(1)) - 1;
                    int startColumn = parts[1].charAt(0) - 'a';
                    int toLine = Integer.parseInt(parts[2].substring(1)) - 1;
                    int toColumn = parts[2].charAt(0) - 'a';

                    ChessPiece piece = board.board[startLine][startColumn];

                    if (piece == null || !piece.getColor().equals(board.nowPlayer)) {
                        System.out.println("Сейчас ход " + board.nowPlayer + ". Нельзя двигать чужие фигуры!");
                        continue;
                    }

                    if (board.moveToPosition(startLine, startColumn, toLine, toColumn)) {
                        System.out.println("Фигура успешно передвинута.");
                        board.printBoard();
                    } else {
                        System.out.println("Ход невозможен.");
                    }
                } catch (Exception e) {
                    System.out.println("Ошибка: " + e.getMessage());
                }
            } else if (s.startsWith("castling")) {
                if (board.castling(s.equals("castling kingside"))) {
                    System.out.println("Рокировка выполнена.");
                    board.printBoard();
                } else {
                    System.out.println("Рокировка невозможна.");
                }
            } else {
                System.out.println("Неизвестная команда.");
            }
        }

        scanner.close();
    }
}
