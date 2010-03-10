
package J4D;

import java.util.Scanner;

class DaysInEachMonth {

    public static void main(String args[]) {
        Scanner myScanner = new Scanner(System.in);
        int month, numberOfDays = 0;
        boolean isLeapYear;

        System.out.print("Which month? ");
        month = myScanner.nextInt();

        switch (month) {
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
            numberOfDays = 31;
            break;

        case 4:
        case 6:
        case 9:
        case 11:
            numberOfDays = 30;
            break;

        case 2:
            System.out.print("Leap year (true/false)? ");
            isLeapYear = myScanner.nextBoolean();
            if (isLeapYear) {
               numberOfDays = 29;
            } else {
               numberOfDays = 28;
            }
        }

        System.out.print(numberOfDays);
        System.out.println(" days");
    }
}

