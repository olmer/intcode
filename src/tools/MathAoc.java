package tools;

import java.util.Arrays;
import java.util.List;

public class MathAoc {
  public static long lcm(long number1, long number2) {
    if (number1 == 0 || number2 == 0) {
      return 0;
    }
    long absNumber1 = java.lang.Math.abs(number1);
    long absNumber2 = java.lang.Math.abs(number2);
    long absHigherNumber = java.lang.Math.max(absNumber1, absNumber2);
    long absLowerNumber = java.lang.Math.min(absNumber1, absNumber2);
    long lcm = absHigherNumber;
    while (lcm % absLowerNumber != 0) {
      lcm += absHigherNumber;
    }
    return lcm;
  }

  public static List<Double> gaussianElimination(double[][] matrix) {
    int rows = matrix.length;
    int cols = matrix[0].length - 1; // Exclude the augmented column

    for (int i = 0; i < rows; i++) {
      // Find pivot (largest number) for this column
      int pivotRow = i;
      for (int j = i + 1; j < rows; j++) {
        if (Math.abs(matrix[j][i]) > Math.abs(matrix[pivotRow][i])) {
          pivotRow = j;
        }
      }

      // Swap rows
      double[] temp = matrix[i];
      matrix[i] = matrix[pivotRow];
      matrix[pivotRow] = temp;

      // Make the pivot element 1
      double pivot = matrix[i][i];
      for (int j = i; j <= cols; j++) {
        matrix[i][j] /= pivot;
      }

      // Eliminate other rows
      for (int j = 0; j < rows; j++) {
        if (j != i) {
          double factor = matrix[j][i];
          for (int k = i; k <= cols; k++) {
            matrix[j][k] -= factor * matrix[i][k];
          }
        }
      }
    }
    //Take rightmost column which contains the answer
    return Arrays.stream(matrix).map(e -> -e[e.length - 1]).toList();
  }
}
