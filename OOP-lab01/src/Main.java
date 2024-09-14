
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Dmitrii Buzhinskii 3312
 * @version 1.00
 *
 */

public class Main {

    /**
     *
     * A method for generating 8 random numbers up to 100, filling an array with them and sorting it
     *
     */

    public static void main(String[] args) {

        int[] array = new int[8];
        Random rand = new Random();

        for(int i = 0; i < array.length; i++){
            array[i] = rand.nextInt(100);
        }

        System.out.println("Оригинальный массив: " + Arrays.toString(array));

        for(int i = 0; i < array.length - 1; i++){
            for(int j = i + 1; j < array.length; j++){
                if(array[i] > array[j]){
                    int tmp = array[i];
                    array[i] = array[j];
                    array[j] = tmp;
                }
            }
        }
        System.out.println("Отсортированный по возрастанию массив: " + Arrays.toString(array));

        for(int i = 0; i < array.length - 1; i++){
            for(int j = i + 1; j < array.length; j++){
                if(array[i] < array[j]){
                    int tmp = array[i];
                    array[i] = array[j];
                    array[j] = tmp;
                }
            }
        }
        System.out.println("Отсортированный по убыванию массив: " + Arrays.toString(array));
    }
}
