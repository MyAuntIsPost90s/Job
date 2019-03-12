package com.bs.job.service.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lingshi.valid.ObjectValid;

/**
 * 对象与dto映射转换类
 * 
 * @author caich
 *
 */
public class BeanConvertUtil {

	public interface Formatter {
        Object invoke(Object... obj);
    }

    public static <T> List<T> convert(Collection<?> list, Class<T> tagert) {
        return convert(list, tagert, null);
    }

    public static <T> List<T> convert(Collection<?> list, Class<T> tagert, Formatter format) {
        if (ObjectValid.isNull(list)) {
            return null;
        }
        List<T> result = new ArrayList<T>();
        if (ObjectValid.isNotEmpty(list)) {
            for (Object object : list) {
                T t = convert(object, tagert, format);
                result.add(t);
            }
        }
        return result;
    }

    public static <T> T convert(Object source, Class<T> tagert) {
        return convert(source, tagert, null);
    }

    public static <T> T convert(Object source, Class<T> tagert, Formatter format) {
        if (ObjectValid.isNull(source)) {
            return null;
        }
        try {
            Class<?> sourceClass = source.getClass();
            T result = tagert.newInstance();
            List<Field> targetFileds = getFields(result.getClass());
            Map<String, Field> fieldMap = getFieldMap(getFields(sourceClass));
            for (Field targetFiled : targetFileds) {
                targetFiled.setAccessible(true);
                Object sourceValue = null;
                if (fieldMap.containsKey(targetFiled.getName())) {
                    Field sourceField = fieldMap.get(targetFiled.getName());
                    sourceValue = sourceField.get(source);
                    if (targetFiled.getType().getName().equals(sourceField.getType().getName())) {
                        targetFiled.set(result, sourceValue);
                    }
                }
                doFormat(result, targetFiled, sourceValue, format);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void doFormat(Object obj, Field target, Object sourceValue, Formatter format) throws Exception {
        if (format == null) {
            return;
        }
        Object value = format.invoke(target.getName(), sourceValue);
        if (value != sourceValue) {
            target.set(obj, value);
        }
    }

    private static Map<String, Field> getFieldMap(List<Field> fields) {
        Map<String, Field> fieldMap = new HashMap<String, Field>();
        if (fields != null && fields.size() > 0) {
            for (Field field : fields) {
                if (!Modifier.isFinal(field.getModifiers())) {
                    field.setAccessible(true);
                    fieldMap.put(field.getName(), field);
                }
            }
        }
        return fieldMap;
    }

    private static List<Field> getFields(Class<?> tagert) {
        List<Field> fields = new ArrayList<Field>();
        while (tagert != null) {
            fields.addAll(Arrays.asList(tagert.getDeclaredFields()));
            tagert = tagert.getSuperclass();
        }
        return fields;
    }
}
