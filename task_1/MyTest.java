package T1.homework.task_1;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyTest {
    String name() default "";
    int priority() default 5;
}
