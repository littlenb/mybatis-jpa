package com.mybatis.jpa.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @author svili
 */
public class FieldReflectUtil {

    /**
     * Set the field value to target object.
     *
     * @param target obj
     * @param name   field name
     * @throws Exception IllegalArgumentException, IllegalAccessException
     */
    public static <T> void setFieldValue(T target, String name, Object value) throws Exception {
        if (target == null) {
            throw new IllegalArgumentException("Target must not be null");
        }

        Field field = findField(target.getClass(), name);

        makeAccessible(field);

        field.set(target, value);
    }

    /**
     * Get the field value from target object.
     *
     * @param target obj
     * @param name   field name
     * @return value
     * @throws Exception IllegalArgumentException, IllegalAccessException
     */
    public static <T> Object getFieldValue(T target, String name) throws Exception {
        if (target == null) {
            throw new IllegalArgumentException("Target must not be null");
        }

        Field field = findField(target.getClass(), name);

        if (field == null) {
            throw new IllegalArgumentException("Can not find field " + name + " from " + target.getClass());
        }

        makeAccessible(field);

        return field.get(target);
    }

    /**
     * Attempt to find a field on the supplied Class with the
     * supplied {@code annotationType}. Searches all superclasses up to Object.
     *
     * @param clazz          the class to introspect
     * @param annotationType the annotation type of the field
     * @return Field or null
     */
    public static List<Field> findFields(Class<?> clazz, Class<? extends Annotation> annotationType) {
        List<Field> list = new ArrayList<>();
        Class<?> searchType = clazz;
        while (!Object.class.equals(searchType) && searchType != null) {
            Field[] fields = searchType.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(annotationType)) {
                    list.add(field);
                }
            }
            searchType = searchType.getSuperclass();
        }
        return list;
    }

    /**
     * copy from org.springframework.util.ReflectionUtils
     */
    public static Field findField(Class<?> clazz, String name) {
        return findField(clazz, name, null);
    }

    /**
     * copy from org.springframework.util.ReflectionUtils
     */
    public static Field findField(Class<?> clazz, String name, Class<?> type) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class must not be null");
        }
        if (name == null && type == null) {
            throw new IllegalArgumentException("Either name or type of the field must be specified");
        }
        Class<?> searchType = clazz;
        while (Object.class != searchType && searchType != null) {
            Field[] fields = searchType.getDeclaredFields();
            for (Field field : fields) {
                if ((name == null || name.equals(field.getName())) &&
                        (type == null || type.equals(field.getType()))) {
                    return field;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }

    /**
     * copy from org.springframework.util.ReflectionUtils
     */
    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) ||
                !Modifier.isPublic(field.getDeclaringClass().getModifiers()) ||
                Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

}
