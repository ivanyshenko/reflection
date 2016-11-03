package reflection;

public class Person {
    private String name;
    private Integer age;
    private int lovingInt;

    public int getLovingInt() {
        return lovingInt;
    }

    public void setLovingInt(int lovingInt) {
        this.lovingInt = lovingInt;
    }

    public Person(String name, Integer age, int lovingInt) {
        this.name = name;
        this.age = age;
        this.lovingInt = lovingInt;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
