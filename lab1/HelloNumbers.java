public class HelloNumbers {
    public static void main(String[] args) {
        int x = 0;
        int result = 0;
        while (x < 10) {
            result = result + x;
            System.out.print(result + " ");
            x = x + 1;
        }
    }
}
