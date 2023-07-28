package smr2DZ;

import java.util.InputMismatchException;
import java.util.Scanner;
/* Реализуйте метод, который запрашивает у пользователя ввод
   дробного числа (типа float), и возвращает введенное значение.
   Ввод текста вместо числа не должно приводить к падению приложения,
   вместо этого, необходимо повторно запросить у пользователя ввод данных. */
public class Task1 {
    public static void main(String[] args) {
        System.out.println(inputNumber());
    }
    public static float inputNumber() {
        System.out.print("Введите дробное число: ");
        Scanner sc = new Scanner(System.in);
        float num;
        try {
            num = sc.nextFloat();
        } catch (InputMismatchException e) {
            System.out.print("Вы ввели не дробное число, повторите ввод дробного числа: ");
            num = inputNumber();
        }
        sc.close();
        return num;
    }
}
