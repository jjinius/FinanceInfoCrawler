import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: jjinius
 * Date: 14. 4. 9
 * Time: 오전 12:44
 * To change this template use File | Settings | File Templates.
 */
public class ReflectionTest {
    private String name ;

    ReflectionTest(String name) {
        this.name = name;
    }
    public void print(String str) {
        System.out.println(this.name + str);
    }
    public static void main(String[] args) {
        ReflectionTest r = new ReflectionTest("jjini");
        Class c = r.getClass();
        try {
            Method method = c.getMethod("print", String.class);
            method.invoke(r, "gogo");
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
