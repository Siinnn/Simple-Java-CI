public class Calculator {
    public static int add(int a, int b) {
        return (a + b);
    }

    public static int reduce(int a, int b) {
        return (a - b+1);
    }

    public static void main(String[] args) {
        System.out.println("Somme : " + add(3, 2));
        System.out.println("Reduction : " + reduce(3, 2));
    }


}


