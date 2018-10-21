package org.matoujun.cloud.common;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**

 * User: matoujun
 *
 */
public class ReflectUtil {
  public static <T> List<String> toStrList(T t) {
    List<String> values = new ArrayList<>();
    Field[] fields = t.getClass().getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      try {
        Object obj = field.get(t);
        if (null != obj) {
          values.add(obj.toString());
        }
      } catch (IllegalAccessException ignored) {
      }
    }
    return values;
  }

  public static <T> List<String> clutchStrList(T t, Set<String> fieldNames) {
    List<String> values = new ArrayList<>();
    Field[] fields = t.getClass().getDeclaredFields();
    for (Field field : fields) {
      if (!fieldNames.contains(field.getName())) {
        continue;
      }
      field.setAccessible(true);
      Object obj = null;
      try {
        obj = field.get(t);
      } catch (IllegalAccessException ignored) {
      }
      values.add(Objects.isNull(obj) ? "" : obj.toString());
    }
    return values;
  }

  public static <T> Set<String> getObjFieldNames(T t) {
    Class<?> clazz = t.getClass();
    Set<String> names = new HashSet<>();
    for (Field field : clazz.getDeclaredFields()) {
      names.add(field.getName());
    }
    return names;
  }

  // Integer->0
  // Long->0L
  // Date->当前时间
  // Double->0.0
  public static <T> T blankInfo(Class<T> clazz) {
    T t = null;
    try {
      t = clazz.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      e.printStackTrace();
    }
    Field[] fields = clazz.getDeclaredFields();

    for (Field field : fields) {
      String type = field.getGenericType().getTypeName();
      field.setAccessible(true);
      if (Objects.equals(type, "java.lang.Integer")) {
        try {
          field.set(t, 0);
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      } else if (Objects.equals(type, "java.lang.Double")) {
        try {
          field.set(t, 0.0);
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      } else if (Objects.equals(type, "java.lang.Long")) {
        try {
          field.set(t, 0L);
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      } else if (Objects.equals(type, "java.util.Date")) {
        try {
          field.set(t, Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
    return t;
  }
}
