//used to resolve https://inginious.info.ucl.ac.be/course/LINFO1121-QCM/Part5Heap

package strings;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class heap {

    PriorityQueue<Integer> queue;

    heap(PriorityQueue<Integer> queue) {
        this.queue = queue;
    }

    public static void main(String[] args) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        heap a = new heap(maxHeap);
        a.queue.add(5);
        a.queue.add(1);
        a.queue.add(9);
        a.queue.add(3);
        a.queue.add(8);
        a.queue.add(6);
        a.queue.add(4);
        System.out.println(a.queue);
    }
}

