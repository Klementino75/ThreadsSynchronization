import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {
        final int maxThreads = 1000;
        final String letter = "RLRFR";

        for (int i = 0; i < maxThreads; i++) {
            new Thread(() -> {
                String letters = generateRoute(letter, 100);
                int frequency = (int) letters.chars().filter(ch -> ch == 'R').count();
                synchronized (sizeToFreq){
                    if(sizeToFreq.containsKey(frequency)){
                        sizeToFreq.put(frequency, sizeToFreq.get(frequency) + 1);
                    } else {
                        sizeToFreq.put(frequency, 1);
                    }
                }
            }).start();
        }

        Map.Entry<Integer, Integer> max = sizeToFreq.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();
        System.out.println("Самое частое количество повторений " + max.getKey() +
                " (встретилось " + max.getValue() + " раз)");
        System.out.println("Другие размеры:");
        sizeToFreq.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(e -> System.out.println(" - " + e.getKey()  + " (" + e.getValue() + " раз)"));
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}