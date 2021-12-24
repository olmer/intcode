public class Aoc2124 {
    public static void main(String[] args) throws Exception {
        //w[0] = w[13] - b[13] - c[0]
        var a = new int[]{1, 1, 1, 1, 26, 26, 1, 1, 26, 26, 1, 26, 26, 26};
        var b = new int[]{13, 12, 12, 10, -11, -13, 15, 10, -2, -6, 14, 0, -15, -4};
        var c = new int[]{8, 13, 8, 10, 12, 1, 13, 5, 10, 3, 2, 2, 12, 7};
        var answer = 13621111481315L;
        var z = 0L;
        for (var i = 0; i < a.length; i++) {
            // w = i-th digit of number
            var w = (long)(answer / Math.pow(10, 13 - i)) % 10;
            var x = (z % 26) + b[i];
            z /= a[i];
            if (x != w) {
                z *= 26;
                z += w + c[i];
            }
        }
        System.out.println(z == 0 ? "Success" : "Fail");
        System.out.println(answer);
    }
}

/*
w = input

x = z
x = x % 26
z = z / 1
x += 13
x = x != w ? 1 : 0
y = 25 * x
z = z * (y + 1)
y = x * (w + 8)
z += y
 */

/*
inp w
mul x 0
add x z
mod x 26    26  26  26  26  26  26  26  26  26  26  26  26  26
div z 1     1   1   1   26  26  1   1   26  26  1   26  26  26
add x 13    12  12  10  -11 -13 15  10  -2  -6  14  0   -15 -4
eql x w
eql x 0
mul y 0
add y 25
mul y x
add y 1
mul z y
mul y 0
add y w
add y 8     13  8   10  12  1   13  5   10  3   2   2   12  7
mul y x
add z y


z == -y

 */


