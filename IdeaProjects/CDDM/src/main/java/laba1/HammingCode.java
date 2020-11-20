package laba1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class HammingCode {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {

        int[][] treatment = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
                {0, 1, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 1, 1, 0, 0, 0, 1, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {1, 0, 1, 0, 0, 1, 0, 0, 0, 0},
                {1, 1, 0, 0, 1, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0, 0, 0}
        };
        int code;
        char[] array;
        int[] word = new int[7];
        int[] message = new int[7];
        int[] syndrome = new int[3];
        int[] medicine = new int[7];
        int counter;
        System.out.println("Введите последовательность из 4х нулей и единиц... ");
        do {
            counter = 0;
            code = enterNumberFromRun();
            array = (code + "").toCharArray();
            if (array.length > 4) {
                System.out.println("Вы ввели последовательность более 4х символов");
                System.out.println("Пожалуйста введите ещё раз... ");
            } else {
                for (char c : array) {
                    if (c == '1' || c == '0') {
                        counter++;
                    }
                }
                if (counter != 4) {
                    System.out.println("Вы ввели неверную последовательность");
                    System.out.println("Пожалуйста введите ещё раз... ");
                }
            }
        } while (counter != 4);

        for (int i = 0; i < 4; i++) {
            word[i] = Integer.parseInt(array[i] + "");
        }

        generateParity(word);
        System.out.println("Сгенерированное сообщение: " + Arrays.toString(word));
        System.out.println("Введите номер символа, на котором произошла ошибка... ");

        do {
            code = enterNumberFromRun();
            if (code >= 1 && code <= 7) {
                break;
            }
            System.out.println("Ошибка, введите номер символа от 1 до 7...");
        } while (true);

        if (word[code - 1] == 1) {
            word[code - 1] = 0;
        } else {
            word[code - 1] = 1;
        }

        System.arraycopy(word, 0, message, 0, word.length);
        generateParity(message);

        for (int i = 0; i < 3; i++) { // Цикл вычисления синдрома
            if (message[i + 4] != word[i + 4]) {
                syndrome[i] = 1;
            }
        }
        System.out.println("Синдром: " + Arrays.toString(syndrome));
        for (int i = 0; i < 8; i++) { // Цикл вычисления вектора ошибки
            if (syndrome[0] == treatment[i][0] && syndrome[1] == treatment[i][1] && syndrome[2] == treatment[i][2]) {
                System.arraycopy(treatment[i], 3, medicine, 0, 7);
                break;
            }
        }

        System.out.println("Что лечим: " + Arrays.toString(word));
        for (int i = 0; i < 7; i++) { // Цикл "лечения"
            word[i] += medicine[i];
            word[i] = word[i] % 2;
        }
        System.out.println("Как лечим: " + Arrays.toString(medicine));
        System.out.println("Результат: " + Arrays.toString(word));

    }

    private static void generateParity(int[] array) { // Генерация чётности
        boolean isItEven;
        isItEven = (array[0] + array[1] + array[2]) % 2 == 0;
        if (isItEven) {
            array[4] = 0;
        } else {
            array[4] = 1;
        }

        isItEven = (array[0] + array[1] + array[3]) % 2 == 0;
        if (isItEven) {
            array[5] = 0;
        } else {
            array[5] = 1;
        }

        isItEven = (array[0] + array[2] + array[3]) % 2 == 0;
        if (isItEven) {
            array[6] = 0;
        } else {
            array[6] = 1;
        }
    }

    public static int enterNumberFromRun() { // Ввод числа с консоли
        try {
            return Integer.parseInt(reader.readLine());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
