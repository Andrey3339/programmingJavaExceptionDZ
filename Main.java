import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        List<String> resultDataList;
        String inputPrompt  = "Введите данные в произвольном порядке, разделенные пробелом: " +
                "Фамилия Имя Отчество, дата рождения, номер телефона, пол: ";
        try {
            resultDataList = resultStringFormation(inputUserData(inputPrompt));
            System.out.println(resultDataList);
            writeDataForFile(resultDataList);
        } catch (MyFIOException | MyPhoneNumberException | MyGenderException | MyBirthDayException | MyFileDataPathException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void writeDataForFile(List<String> resultDataList) throws MyFileDataPathException {
        String filePath = null;
        try {
            filePath = "src/" + resultDataList.get(0) + ".txt";
        } catch (Exception e) {
            throw new MyFileDataPathException();
        }
        try (FileWriter writer = new FileWriter(filePath, true)) {

            for (int i = 0; i < resultDataList.size(); i++) {
                writer.write("<");
                writer.write(resultDataList.get(i));
                writer.write(">");
            }
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<String> resultStringFormation(String rawStringData) {

        String regex1 = "[а-яёА-ЯЁ]+";
        String regex2 = "\\b\\d{2}\\.\\d{2}\\.\\d{4}\\b";
        String regex3 = "\\+?[1-9]\\d{0,2}[-.\\s]?\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{2}[-.\\s]?\\d{2}";
        String regex4 = "\\b[mfMF]\\b";

        List<String> resultDataArray = new LinkedList<>();

        Pattern pattern = Pattern.compile(regex1);
        Matcher matcher = pattern.matcher(rawStringData);

        while(matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            resultDataArray.add(rawStringData.substring(start, end));
        }
        if (resultDataArray.size() != 3) {
            throw new MyFIOException();
        }
        pattern = Pattern.compile(regex2);
        matcher = pattern.matcher(rawStringData);

        if (matcher.find()) {
            String birthDay = matcher.group();
            String[] validBirthDay = birthDay.split("\\.");
            if (Integer.parseInt(validBirthDay[0]) > 0 && Integer.parseInt(validBirthDay[0]) < 32
                    && Integer.parseInt(validBirthDay[1]) > 0 && Integer.parseInt(validBirthDay[1]) < 13
                    && Integer.parseInt(validBirthDay[2]) > 1900 && Integer.parseInt(validBirthDay[2]) < LocalDate.now().getYear()) {
                resultDataArray.add(birthDay);
            }
            else {
                throw new MyBirthDayException();
            }
        } else {
            throw new MyBirthDayException();
            //System.out.println("Дата рождения не найдена");
        }
        pattern = Pattern.compile(regex3);
        matcher = pattern.matcher(rawStringData);

        if (matcher.find()) {
            String phoneNumber = matcher.group();
            resultDataArray.add(phoneNumber);
        } else {
            throw new MyPhoneNumberException();
            //System.out.println("Номер телефона не найден");
        }
        pattern = Pattern.compile(regex4);
        matcher = pattern.matcher(rawStringData);

        if (matcher.find()) {
            String gender = matcher.group();
            resultDataArray.add(gender);
        } else {
            throw new MyGenderException();
            //System.out.println("Пол не найден");
        }
        return resultDataArray;
    }
    public static String inputUserData(String inputPrompt) {
        Scanner sc = new Scanner(System.in);
        System.out.println(inputPrompt);
        String rawStringData = sc.nextLine();
        return rawStringData;
    }
}
class MyFIOException extends IllegalArgumentException {
    MyFIOException() {
        super("Неправильный ввод фамилии и/или имени и/или отчества.");
    }
}
class MyBirthDayException extends IllegalArgumentException {
    MyBirthDayException() {
        super("Неправильный ввод даты рождения.");
    }
}
class MyPhoneNumberException extends IllegalArgumentException {
    MyPhoneNumberException() {
        super("Неправильный ввод номера телефона.");
    }
}
class MyGenderException extends IllegalArgumentException {
    MyGenderException() {
        super("Неправильный ввод пола.");
    }
}
class MyFileDataPathException extends IOException {
    MyFileDataPathException() {
        super("Невозможно создать путь к файлу с данными.");
    }
}