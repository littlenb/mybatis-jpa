package com.littlenb.mybatisjpa.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author sway.li
 */
public class PersistentUtil {

  public static String getTableName(Class<?> clazz) {
    return getTableName(clazz, true);
  }

  public static String getTableName(Class<?> clazz, boolean camelToUnderline) {
    if (clazz.isAnnotationPresent(Table.class)) {
      Table table = clazz.getAnnotation(Table.class);
      if (!"".equals(table.name().trim())) {
        return table.name();
      }
    }
    String className = clazz.getSimpleName();

    if (!camelToUnderline) {
      return className;
    } else {
      return ColumnNameUtil.camelToUnderline(className);
    }
  }

  public static String getColumnName(Field field) {
    return getColumnName(field, true);
  }

  public static String getColumnName(Field field, boolean camelToUnderline) {
    if (field.isAnnotationPresent(Column.class)) {
      Column column = field.getAnnotation(Column.class);
      if (!"".equals(column.name().trim())) {
        return column.name();
      }
    }
    if (!camelToUnderline) {
      return field.getName();
    } else {
      return ColumnNameUtil.camelToUnderline(field.getName());
    }
  }

  public static String getMappedName(Field field) {
    if (field.isAnnotationPresent(OneToOne.class)) {
      OneToOne one = field.getAnnotation(OneToOne.class);
      if (!one.mappedBy().trim().equals("")) {
        return one.mappedBy();
      }
    }
    if (field.isAnnotationPresent(OneToMany.class)) {
      OneToMany one = field.getAnnotation(OneToMany.class);
      if (!one.mappedBy().trim().equals("")) {
        return one.mappedBy();
      }
    }
    return null;
  }

  public static List<Field> getPersistentFields(Class<?> clazz) {
    List<Field> list = new ArrayList<>();
    Class<?> searchType = clazz;
    while (!Object.class.equals(searchType) && searchType != null) {
      Field[] fields = searchType.getDeclaredFields();
      for (Field field : fields) {
        if (isPersistentField(field)) {
          list.add(field);
        }
      }
      searchType = searchType.getSuperclass();
    }
    return list;
  }

  public static Field getIdField(Class<?> clazz) {
    Class<?> searchType = clazz;
    while (!Object.class.equals(searchType) && searchType != null) {
      Field[] fields = searchType.getDeclaredFields();
      for (Field field : fields) {
        if (field.isAnnotationPresent(Id.class) && isPersistentField(field)) {
          return field;
        }
      }
      searchType = searchType.getSuperclass();
    }
    return null;
  }

  public static boolean insertable(Field field) {
    if (!isPersistentField(field) || isAssociationField(field)) {
      return false;
    }

    if (field.isAnnotationPresent(Column.class)) {
      Column column = field.getAnnotation(Column.class);
      return column.insertable();
    }

    return true;
  }

  public static boolean updatable(Field field) {
    if (!isPersistentField(field) || isAssociationField(field)) {
      return false;
    }

    if (field.isAnnotationPresent(Column.class)) {
      Column column = field.getAnnotation(Column.class);
      return column.updatable();
    }

    return true;
  }

  public static boolean isPersistentField(Field field) {
    return !field.isAnnotationPresent(Transient.class);
  }

  public static boolean isAssociationField(Field field) {
    return field.isAnnotationPresent(OneToOne.class)
        || field.isAnnotationPresent(OneToMany.class)
        || field.isAnnotationPresent(ManyToOne.class)
        || field.isAnnotationPresent(ManyToMany.class);
  }
}
