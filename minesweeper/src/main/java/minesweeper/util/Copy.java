package minesweeper.util;

import java.lang.annotation.*;
/**
 * @author godghdai
 * #Description Copy
 * #Date: 2020/9/27 16:14
 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Copy {
    String value() default "";
}
