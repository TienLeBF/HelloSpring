package org.o7planning.thymeleaf.model;

public class Person {
    private String firstName;
    private String lastName;
    private short age;
    private boolean sex;
    private String email;
    private String address;

    public Person() {
    }

    public Person(String firstName, String lastName, short age, boolean sex, String email, String address) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.sex = sex;
        this.email = email;
        this.address = address;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public short getAge() {
        return this.age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public boolean isSex() {
        return this.sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
