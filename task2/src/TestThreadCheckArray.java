import java.util.Scanner;

/**
 * Runs a two-thread check over an integer array read from standard input.
 * Reads: array size n, n integers for the array, and a target value b.
 * Builds a SharedData(array, b), starts two ThreadCheckArray workers on it,
 * waits for completion, and prints three rows: indices (I), values (A), and result flags (C).
 */
public class TestThreadCheckArray {

    /**
     * Program entry point. Reads input, creates SharedData, starts two worker threads,
     * waits for them to finish, and prints the results. If no solution is found
     * (flag is false), prints "Sorry" and exits.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            Thread thread1, thread2;

            System.out.println("Enter array size");
            int num  = input.nextInt();

            int [] array = new int[num];
            System.out.println("Enter numbers for array");
            for (int index = 0; index < num; index++)
                array[index] = input.nextInt();

            System.out.println("Enter number");
            num = input.nextInt(); // target value b

            SharedData sd = new SharedData(array, num);

            thread1 = new Thread(new ThreadCheckArray(sd), "thread1");
            thread2 = new Thread(new ThreadCheckArray(sd), "thread2");
            thread1.start();
            thread2.start();

            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!sd.getFlag()) {
                System.out.println("Sorry");
                return;
            }

            System.out.println("Solution for b : " + sd.getB() + ",n = " + sd.getArray().length);

            System.out.print("I:    ");
            for (int index = 0; index < sd.getArray().length ; index++)
                System.out.print(index + "    ");
            System.out.println();

            System.out.print("A:    ");
            for (int value : sd.getArray()) {
                System.out.print(value);
                int counter = 5;
                int tmp = value;
                while (true) {
                    tmp = tmp / 10;
                    counter--;
                    if (tmp == 0) break;
                }
                for (int i = 0; i < counter; i++)
                    System.out.print(" ");
            }
            System.out.println();

            System.out.print("C:    ");
            for (boolean bit : sd.getWinArray())
                System.out.print(bit ? "1    " : "0    ");
        }
    }
}

