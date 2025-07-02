import javax.swing.*;
import java.awt.*;

public class SudokuSolver extends JFrame {
    private static final int SIZE = 9;
    private JTextField[][] boxes = new JTextField[SIZE][SIZE];
    private JButton solve, clear;

    public SudokuSolver() {
        setTitle("Sudoku Solver");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(SIZE, SIZE));
        Font font = new Font("Arial", Font.BOLD, 20);

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JTextField box = new JTextField();
                box.setHorizontalAlignment(JTextField.CENTER);
                box.setFont(font);
                boxes[row][col] = box;
                gridPanel.add(box);
            }
        }

        JPanel buttonPanel = new JPanel();
        solve = new JButton("Solve");
        clear = new JButton("Clear");

        solve.addActionListener(e -> solveSudoku());
        clear.addActionListener(e -> clearBoard());

        buttonPanel.add(solve);
        buttonPanel.add(clear);

        add(gridPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void solveSudoku() {
        int[][] board = new int[SIZE][SIZE];

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                String text = boxes[row][col].getText();
                if (!text.isEmpty()) {
                    try {
                        board[row][col] = Integer.parseInt(text);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Invalid input at (" + (row + 1) + "," + (col + 1) + ")");
                        return;
                    }
                }
            }
        }

        if (solveBoard(board)) {
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    boxes[row][col].setText(String.valueOf(board[row][col]));
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No solution found!");
        }
    }

    private void clearBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                boxes[row][col].setText("");
            }
        }
    }

    private boolean isValid(int[][] board, int row, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num)
                return false;
        }

        int boxRow = row - row % 3;
        int boxCol = col - col % 3;

        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                if (board[i][j] == num)
                    return false;
            }
        }

        return true;
    }

    private boolean solveBoard(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num;
                            if (solveBoard(board))
                                return true;
                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SudokuSolver::new);
    }
}

