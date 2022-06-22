package g56080.atl.sortingRace.model;

import java.util.Comparator;
import java.util.function.Supplier;

public class Util{

    private Util(){}

    public static int exec(Runnable proc){
        long start_time = System.nanoTime();
        proc.run();
        long end_time = System.nanoTime();
        return Math.round((end_time - start_time) / 1_000_000);
    }

    public static <T> void fillWith(T[] array, Supplier<T> rng){
        for(int i = 0; i < array.length; i++){
            array[i] = rng.get();
        }
    }

    public static <T> int insertionSort(T[] array, Comparator<T> comp){
        int counter = 1; /* init of i */
        for(int i = 1; i < array.length; i++){
            counter += 2; /* condition + inc */
            counter += 3; /* init of key and j + indexation */
            T key = array[i];
            int j = i - 1;
            while(j > -1 && comp.compare(array[j], key) > 0){
                counter += 6; /* comparison method + condition + 2 indexations + dec + assignation*/ 
                array[j + 1] = array[j];
                j--;
            }

            counter += 2; /* indexation + assignation */
            array[j + 1] = key;
        }

        return counter;
    }

    public static <T> int bubbleSort(T[] array, Comparator<T> comp){
        int counter = 1; /* init of i */
        for(int i = 0; i < array.length; i++){
            counter += 2; /* condition + incrementation */
            counter += 1; /* init of j */
            for(int j = 0; j < array.length - i - 1; j++){
                counter += 2; /* condition + incrementation */
                counter += 4; /* 2 indexations + comparison method + condition */
                if(comp.compare(array[j], array[j + 1]) > 0){
                    swap(array, j , j + 1);
                    counter += 7; /* int of tmp + 4 indexations + 2 assignments */
                }
            }
        }

        return counter;
    }

    public static <T> int mergeSort(T[] array, Comparator<T> comp){
        int counter = 1; /* method call */
        Mutable<Integer> mut = new Mutable<>(counter);
        mergeSort_imp(array, comp, 0, array.length - 1, mut);
        return mut.value();
    }

    private static <T> void mergeSort_imp(T[] array, Comparator<T> comp, int left, int right, Mutable<Integer> mut){
        mut.setValue(mut.value() + 1); /* condition */
        if(left < right){
            mut.setValue(mut.value() + 4); /* init of mid + 3 method calls */
            int mid = (left + right) / 2;
            
            mergeSort_imp(array, comp, left, mid, mut);
            mergeSort_imp(array, comp, mid + 1, right, mut);

            merge(array, comp, left, mid, right, mut);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> void merge(T[] array, Comparator<T> comp, int left, int mid, int right, Mutable<Integer> mut){
        mut.setValue(mut.value() + 2); /* left and right size init */
        int leftSize = mid - left + 1;
        int rightSize = right - mid;

        mut.setValue(mut.value() + 2); /* left and right tmp init */
        Object[] tmpLeft = new Object[leftSize];
        Object[] tmpRight = new Object[rightSize];

        mut.setValue(mut.value() + 2); /* init of i two times */ 
        for(int i = 0; i < leftSize; i++){
            tmpLeft[i] = array[left + i];
            mut.setValue(mut.value() + 3); /* 2 indexations + assignment */
        }
        for(int i = 0; i < rightSize; i++){
            tmpRight[i] = array[mid + 1 + i];
            mut.setValue(mut.value() + 3); /* 2 indexations + assignment */
        }

        mut.setValue(mut.value() + 3); /* init of i, j and k */
        int i = 0, j = 0;
        int k = left;
        while(i < leftSize && j < rightSize){
            mut.setValue(mut.value() + 6); /* condition + 2 indexations + 2 assignments + incrementation of k */
            T leftValue = (T) tmpLeft[i];
            T rightValue = (T) tmpRight[j];

            mut.setValue(mut.value() + 2); /* comparison method + condition */
            if(comp.compare(leftValue, rightValue) < 0){
                array[k] = leftValue;
                i++;
                mut.setValue(mut.value() + 3); /* indexation + assignment + incrementation */
            } else{
                array[k] = rightValue;
                j++;
                mut.setValue(mut.value() + 3); /* indexation + assignment + incrementation */
            }

            k++;
        }

        while(i < leftSize){
            array[k] = (T) tmpLeft[i];
            i++;
            k++;
            mut.setValue(mut.value() + 5); /* 2 indexations + assignment + 2 incrementations */
        }

        while(j < rightSize){
            array[k] = (T) tmpRight[j];
            j++;
            k++;
            mut.setValue(mut.value() + 5); /* 2 indexations + assignment + 2 incrementations */
        }
    }

    public static <T> void swap(T[] array, int startIndex, int endIndex){
        T tmp = array[startIndex];
        array[startIndex] = array[endIndex];
        array[endIndex] = tmp;
    }
}

