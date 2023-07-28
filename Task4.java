package smr2DZ;
/*
* Разработайте программу, которая выбросит Exception, когда
* пользователь вводит пустую строку. Пользователю должно
* показаться сообщение, что пустые строки вводить нельзя.*/

import java.util.Scanner;

public class Task4 {
    public static void main(String[] args) {
        Task4 task4 = new Task4();
        try {
            System.out.println(task4.inputString());
        } catch (EmptyStringEntry e) {
            //e.printStackTrace();
            System.out.println(e);
        }
    }
    public String inputString() throws EmptyStringEntry {
        System.out.print("Введите строку: ");
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        if (str.length() > 0) {
            return str;
        }
        else {
            throw new EmptyStringEntry("Пустые строки вводить нельзя!");
        }
    }
    public class EmptyStringEntry extends Exception {
        public EmptyStringEntry(String message) {
            super(message);
        }
    }
}

