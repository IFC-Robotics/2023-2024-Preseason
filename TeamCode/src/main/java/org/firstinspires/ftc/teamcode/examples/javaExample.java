package org.firstinspires.ftc.teamcode.examples;

public class javaExample {

    // variables

    int integer = 5; // any integer (whole number)
    float shortDecimal = (float) 9.824; // any decimal, up to 7 decimal digits
    double longDecimal = 2.50341282125; // any decimal, up to 15 decimal digits
    boolean tf = true; // true or false
    char letter = 'a'; // a letter (must have singular quotation mark)
    String word = "hello"; // a word (must have double quotation marks)
    String[] fruits = {"apple", "orange", "grape"}; // an array; the beginning type is the type of every element of the array

    // public = other classes can use the variable
    // static = the variable belongs to the class, not each instance of the class
    // final  = the variable is constant (can't be reassigned)

    public static final String name = "Charlie";

    // methods (aka functions)

    public String sayHello(String name) {

        // if statement

        if (true) {
            // ...
        }

        // for loops

        for (String fruit : fruits) {
            System.out.println(name + "'s favorite fruit is " + fruit);
        }

        // while loops

        while (name.length() > 7) {
            System.out.println("You have a long name");
        }

        return "Hello" + name;

    }

}