//package edu.ser222.m02_01;
import java.util.Random;
/**
 * (basic description of the program or class)
 * 
 * Completion time: (estimation of hours spent on this program)
 *
 * @author Robert, Acuna, Sedgewick
 * @version 1.0
 */


public class CompletedBenchmarkTool implements BenchmarkTool {
    
    /***************************************************************************
     * START - SORTING UTILITIES, DO NOT MODIFY (FROM SEDGEWICK)               *
     **************************************************************************/
    
    public static void insertionSort(Comparable[] a) {
        int N = a.length;
        
        for (int i = 1; i < N; i++)
        {
            // Insert a[i] among a[i-1], a[i-2], a[i-3]... ..          
            for (int j = i; j > 0 && less(a[j], a[j-1]); j--)
                exch(a, j, j-1);
        }
    }
    
    
    public static void shellsort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        
        while (h < N/3) h = 3*h + 1; // 1, 4, 13, 40, 121, 364, 1093, ...
        
        while (h >= 1) {
            // h-sort the array.
            for (int i = h; i < N; i++) {
                // Insert a[i] among a[i-h], a[i-2*h], a[i-3*h]... .
                for (int j = i; j >= h && less(a[j], a[j-h]); j -= h)
                exch(a, j, j-h);
            }
            h = h/3;
        }
    }
    
    
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
    
    
    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i]; a[i] = a[j]; a[j] = t;
    }
    
    /***************************************************************************
     * END - SORTING UTILITIES, DO NOT MODIFY                                  *
     **************************************************************************/

    //TODO: implement interface methods.
    public Integer[] generateTestDataBinary(int size) {
    	Integer[] data = new Integer[size];
    	int half = size/2;
    	
    	for (int i = 0; i < half; i++) {
    		data[i] = 0;
    	}
    	
    	for (int i = half; i < size; i++) {
    		data[i] = 1;
    	}
    	
    	return data;
    }
    
    public Integer[] generateTestDataHalves(int size) {
    	Integer[] data = new Integer[size];
    	int half = size/2;
    	int value = 0;
    	int index = 0;
    	
    	while (half != 1) {
    		for (int i = index; i < (size - half); i++) {
    			data[i] = value; 
    			index = i; 
    		}
    		
    		half = half/2; 
    		index++; 
    		value++; 
    	}
    	
    	data[index] = value;
    	++index; ++value;
    	data[index] = value;
    	
    	return data;
    }
    
    public Integer[] generateTestDataHalfRandom(int size) {
    	Integer[] data = new Integer[size];
    	Random random = new Random();
    	int half = size/2;
    	
    	for (int i = 0; i < half; i++) {
    		data[i] = 0;
    	}
    	
    	for (int i = half; i < size; i++) {
    		data[i] = random.nextInt(Integer.MAX_VALUE);
    	}
    	
    	return data;
    }
    
    public double computeDoublingFormula(double t1, double t2) {
    	double b = Math.log(t2/t1) / Math.log(2);
    	return b;
    }
    
    public double benchmarkInsertionSort(Integer[] small, Integer[] large) {
    	double run1, run2, result;
    	
    	Stopwatch stopwatch1 = new Stopwatch();
    	insertionSort(small);
    	run1 = stopwatch1.elapsedTime();
    	
    	Stopwatch stopwatch2 = new Stopwatch();
    	insertionSort(large);
    	run2 = stopwatch2.elapsedTime();
    	
    	result = computeDoublingFormula(run1, run2);
    	return result;
    }
    
    public double benchmarkShellsort(Integer[] small, Integer[] large) {
    	double run1, run2, result;
    	
    	Stopwatch stopwatch1 = new Stopwatch();
    	shellsort(small);
    	run1 = stopwatch1.elapsedTime();
    	
    	Stopwatch stopwatch2 = new Stopwatch();
    	shellsort(large);
    	run2 = stopwatch2.elapsedTime();
    	
    	result = computeDoublingFormula(run1, run2);
    	return result;
    }
    
    public void runBenchmarks(int size) {
    	Integer[] dataset1a = generateTestDataBinary(size);
    	Integer[] dataset1b = generateTestDataBinary(size*2);
    	Integer[] dataset2a = generateTestDataHalves(size);
    	Integer[] dataset2b = generateTestDataHalves(size*2);
    	Integer[] dataset3a = generateTestDataHalfRandom(size);
    	Integer[] dataset3b = generateTestDataHalfRandom(size*2);
    	
    	double insertionResult1, insertionResult2, insertionResult3;
    	double shellResult1, shellResult2, shellResult3;
    	
    	insertionResult1 = benchmarkInsertionSort(dataset1a, dataset1b);
    	insertionResult2 = benchmarkInsertionSort(dataset2a, dataset2b);
    	insertionResult3 = benchmarkInsertionSort(dataset3a, dataset3b);
    	
    	shellResult1 = benchmarkShellsort(dataset1a, dataset1b);
    	shellResult2 = benchmarkShellsort(dataset2a, dataset2b);
    	shellResult3 = benchmarkShellsort(dataset3a, dataset3b);
    	
    	System.out.println("        Insertion     Shellsort");
		System.out.printf("Bin         %5.3f     %5.3f%n", insertionResult1, shellResult1);
		System.out.printf("Half        %5.3f     %5.3f%n", insertionResult2, shellResult2);
		System.out.printf("RanInt      %5.3f     %5.3f%n", insertionResult3, shellResult3);
    }
    
    

    public static void main(String args[]) {
        BenchmarkTool me = new CompletedBenchmarkTool();
        int size = 4096*2;
        
        //NOTE: feel free to change size here. all other code must go in the
        //      methods.
        
        me.runBenchmarks(size);
    }
}