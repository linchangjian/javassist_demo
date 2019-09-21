import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Test {
    public static void main(String [] arg) throws NotFoundException, IOException, CannotCompileException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        ClassPool pool = new ClassPool(true);

        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Aniu\\Downloads\\apkinject\\build\\classes\\java\\main\\Main.class");

        CtClass main = pool.makeClass(fileInputStream);
        System.out.println(main.getSuperclass());
        System.out.println(main.getMethods());
        for (CtMethod method : main.getMethods()) {
            System.out.println(method.getName());
        }

        FileInputStream superFile = new FileInputStream("C:\\Users\\Aniu\\Downloads\\apkinject\\build\\classes\\java\\main\\SuperMain.class");

        CtClass targetSuperCls = pool.makeClass(superFile);
        if (main.isFrozen()) {
            main.defrost();
        }
        main.setSuperclass(targetSuperCls);
        System.out.println("=====set super class after====");
        for (CtMethod method : main.getMethods()) {
            System.out.println(method.getName());
        }

        CtMethod[] declaredMethods = main.getDeclaredMethods();
        for (CtMethod declaredMethod : declaredMethods) {
            declaredMethod.instrument(new ExprEditor(){
                @Override
                public void edit(MethodCall call) throws CannotCompileException {
                    if (call.isSuper()){
                        try {
                            if (call.getMethod().getReturnType().getName() == "void") {
                                System.out.println("replace call.getMethod():"+call.getMethod());
                                call.replace("{super." + call.getMethodName() + "($$);}");
                            } else {
                                System.out.println("replace call.getMethod():"+call.getMethod());

                                call.replace("{$_ = super."+ call.getMethodName() + "($$);}");
                            }
                        } catch (NotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
        }

        main.writeFile("C:\\Users\\Aniu\\Downloads\\apkinject\\build\\classes\\java\\main\\");
        Class aClass = main.toClass();
        Main o = (Main)aClass.newInstance();
        o.printCall();
        if (main.isFrozen()) {
            main.defrost();
        }
        for (CtMethod declaredMethod : main.getMethods()) {
            declaredMethod.instrument(new ExprEditor(){
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    super.edit(m);
                    System.out.println("==> "+m.isSuper());
                }
            });
        }

    }

    public void callmain(){
        System.out.println(":hhhhhhh");
    }
    public void printCall(){
        System.out.println("Main printCall");
    }
}
