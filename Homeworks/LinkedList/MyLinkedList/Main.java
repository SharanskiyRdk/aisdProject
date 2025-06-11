package MyLinkedList;

public class Main {
    public static void main(String[] args) {
        MyList<String> lst = new MyLinkedList<String>();
        lst.add("Hello");
        lst.add("World");
        lst.add("Java");

        lst.remove("Java");
        System.out.println(lst.get(0));
    }
}
