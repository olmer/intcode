import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class Aoc2015 {
    public static void main(String[] args) {
        var raw = Arrays.stream("20,0,1,11,6,3".split(",")).map(Integer::valueOf).toArray(Integer[]::new);

        var cache = new HashMap<Integer, SizedStack<Integer>>();
        int currentStep;
        int lastNumber = 0;
        for (currentStep = 0; currentStep < raw.length; currentStep++) {
            if (!cache.containsKey(raw[currentStep])) {
                cache.put(raw[currentStep], new SizedStack<>(2));
            }
            cache.get(raw[currentStep]).push(currentStep + 1);
            lastNumber = raw[currentStep];
        }

        while (currentStep < 30000000) {
            currentStep++;

            if (cache.get(lastNumber).size() <= 1) {
                lastNumber = 0;
            } else {
                lastNumber = cache.get(lastNumber).get(1) - cache.get(lastNumber).get(0);
            }

            if (!cache.containsKey(lastNumber)) {
                cache.put(lastNumber, new SizedStack<>(2));
            }

            cache.get(lastNumber).push(currentStep);
        }

        System.out.println(cache.keySet().size());
        System.out.println(lastNumber);
    }

    static class SizedStack<T> extends Stack<T> {
        private final int maxSize;

        public SizedStack(int size) {
            super();
            this.maxSize = size;
        }

        @Override
        public T push(T object) {
            //If the stack is too big, remove elements until it's the right size.
            while (this.size() >= maxSize) {
                this.remove(0);
            }
            return super.push(object);
        }
    }
}
