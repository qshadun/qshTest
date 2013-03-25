import java.util.Arrays;
import java.util.List;


public class SortTest {
	private static int[] bubbleSort(int[] a) {
		boolean changed = true;
		for (int i = a.length - 1; i > 0 && changed; i--){
			changed = false;
			for (int j = 0; j < i; j++) {
				if (a[j] > a[j + 1]) {
					swap(a, j, j + 1);
					if(! changed)
						changed = true;
				}
			}
		}
		return a;
	}
	
	private static int[] insertSort(int[] a) {
		for (int i = 1; i < a.length; i++) {
			int v = a[i];
			int j = i - 1;
			for (; j >= 0 && a[j] > v; j--) {
				 a[j + 1] = a[j];
			}
			a[j + 1] = v;
		}
		return a;
	}
	
	// a in-place implementation
	private static int[] quickSort(int[] a, int left, int right) {
		if (left < right) {
			int pivotIndex = partition(a, left, right, left);
			quickSort(a, left, pivotIndex - 1);
			quickSort(a, pivotIndex + 1, right);
		}
		return a;
	}
	private static int partition(int[] a, int left, int right, int pivot) {
		int index = left;
		swap(a, pivot, right); // put pivot to the last element so it doesn't get in the way
		for (int i = left; i < right; i++) {
			if (a[i] < a[right]){
				swap(a, i, index);
				index++;
			}
		}
		swap(a, index, right); // put pivot in place
		return index;
	}
	
	private static void swap(int[] a, int first, int second) {
		if (first != second) {
			int temp = a[first];
			a[first] = a[second];
			a[second] = temp;
		}
	}
	
	private static int[] mergeSort(int[] a) {
		if (a.length > 1) {
			int m = a.length / 2;
			int[] left = new int[m];
			int[] right = new int[a.length - m];
			for (int i = 0; i < m; i++) 
				left[i] = a[i];
			for (int i = m; i < a.length; i++)
				right[i - m] = a[i];
			a = merge(mergeSort(left), mergeSort(right));
		}
		return a;
	}
	private static int[] merge(int[] l, int[] r) {
		int[] result = new int[l.length + r.length];
		int i = 0, j = 0;
		for (; i < l.length && j < r.length;) {
			if (l[i] < r[j]) {
				result[i + j] = l[i];
				i++;
			} else {
				result[i + j] = r[j];
				j++;
			}
		}
		while (i < l.length) {
			result[i + j] = l[i];
			i++;
		}
		while (j < r.length) {
			result[i + j] = r[j];
			j++;
		}
		return result;
	}
	
	private static <T extends Comparable<T>> List<T> insertSort(List<T> ls) {
		for (int i = 1; i < ls.size(); i++) {
			T v = ls.get(i);
			int j = i - 1;
			for (; j >= 0 && ls.get(j).compareTo(v) > 0; j--) {
				 ls.set(j + 1, ls.get(j));
			}
			ls.set(j + 1, v);
		}
		return ls;
	}
	
	public static void main(String[] args) {
		int[] a = {};
		System.out.println(Arrays.toString(bubbleSort(a)));
		int[] b = {5};
		System.out.println(Arrays.toString(bubbleSort(b)));
		int[] c = {3,7,5,2,9,3};
		System.out.println(Arrays.toString(bubbleSort(c)));

		int[] e = {};
		System.out.println(Arrays.toString(insertSort(e)));
		int[] f = {5};
		System.out.println(Arrays.toString(insertSort(f)));
		int[] g = {3,7,5,2,9,3};
		System.out.println(Arrays.toString(insertSort(g)));

		int[] h = {};
		System.out.println(Arrays.toString(quickSort(h, 0, h.length - 1)));
		int[] i = {5};
		System.out.println(Arrays.toString(quickSort(i, 0, i.length - 1)));
		int[] j = {3,7,5,2,9,3};
		System.out.println(Arrays.toString(quickSort(j, 0, j.length - 1)));
		
		System.out.println("Merge sort: ");
		int[] k = {};
		System.out.println(Arrays.toString(mergeSort(k)));
		int[] l = {5};
		System.out.println(Arrays.toString(mergeSort(l)));
		int[] m = {3,7,5,2,9,3,1};
		System.out.println(Arrays.toString(mergeSort(m)));


		Integer[] aa = {3,7,5,2,9,3};
		List<Integer> ls = Arrays.asList(aa);
		System.out.println(insertSort(ls));
	}
	
}
