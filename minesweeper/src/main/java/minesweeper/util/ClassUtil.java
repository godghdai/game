package minesweeper.util;

import java.lang.reflect.Field;

/**
 * @author godghdai
 * #Description ClassUtil
 * #Date: 2020/9/27 16:14
 */
public class ClassUtil {
    /**
     * 根据Copy注解拷贝实例属性值
     * @param toObj
     * @param fromObj
     */
    public static void copyProperty(Object toObj,Object fromObj) {
        Field[] fields = toObj.getClass().getDeclaredFields();
        for (Field toField : fields) {
            Copy[] copyInfo = toField.getAnnotationsByType(Copy.class);
            if (copyInfo.length == 1) {
                try {
                    Field fromField = fromObj.getClass().getDeclaredField(toField.getName());
                    fromField.setAccessible(true);
                    toField.setAccessible(true);
                    toField.set(toObj, fromField.get(fromObj));
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }
    }
}
