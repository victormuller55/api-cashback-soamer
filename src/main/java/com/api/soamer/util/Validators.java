package com.api.soamer.util;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validators {
    public static boolean temParametros(Object object, String... requiredFields) {
        for (String fieldName : requiredFields) {
            try {
                Field field = object.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = field.get(object);
                if (value == null || (value instanceof String && ((String) value).isEmpty())) {
                    return false;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                return false;
            }
        }
        return true;
    }

    public static boolean emailValido(String emailUsuario) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");;
            Matcher matcher = pattern.matcher(emailUsuario);
            return matcher.matches();
    }
}
