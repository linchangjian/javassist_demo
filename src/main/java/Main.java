import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import javassist.expr.NewExpr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

public class Main extends SuperMain2{
    public static void main(String [] arg) throws NotFoundException, IOException, CannotCompileException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

    }

    public void callmain(){
        System.out.println(":hhhhhhh");
    }
    @Override
    public void printCall(){
        super.printCall();
        System.out.println("Main printCall");
    }

}
