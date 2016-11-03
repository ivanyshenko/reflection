package reflection;

public class Main {
    public static void main(String[] args) {
        Person alice = new Person("alice", 12, 1234);
        Person bob = new Person("bob", 22, 2134);

        System.out.println(alice.getName() + ": " + alice.getAge() + " " + alice.getLovingInt());
        System.out.println(bob.getName() + ": " + bob.getAge() + " " + bob.getLovingInt());

        BeanUtils.assign(bob, alice);

        System.out.println(alice.getName() + ": " + alice.getAge() + " " + alice.getLovingInt());
        System.out.println(bob.getName() + ": " + bob.getAge() + " " + bob.getLovingInt());

    }
}
