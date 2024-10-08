import java.util.Arrays;
/**
 * Write a description of class Test here.
 * https://www.geeksforgeeks.org/class-getmethods-method-in-java-with-examples/
 * https://www.geeksforgeeks.org/java-lang-class-class-java-set-1/
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Test{
    public static void testAll(){
        System.out.println(Arrays.toString(Test.class.getMethods()));
        
    }
}
