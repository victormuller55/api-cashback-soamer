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

    public static boolean cnpjValido(String cnpj) {
        cnpj = cnpj.replaceAll("[^0-9]", "");

        if (cnpj.length() != 14) {
            return false;
        }

        if (cnpj.matches("(\\d)\\1*")) {
            return false;
        }

        int soma = 0;
        int peso = 2;
        for (int i = 11; i >= 0; i--) {
            int digito = Character.getNumericValue(cnpj.charAt(i));
            soma += digito * peso;
            peso++;
            if (peso == 10) {
                peso = 2;
            }
        }
        int resto = soma % 11;
        int digitoVerificador1 = (resto < 2) ? 0 : (11 - resto);

        soma = 0;
        peso = 2;
        for (int i = 12; i >= 0; i--) {
            int digito = Character.getNumericValue(cnpj.charAt(i));
            soma += digito * peso;
            peso++;
            if (peso == 10) {
                peso = 2;
            }
        }
        resto = soma % 11;
        int digitoVerificador2 = (resto < 2) ? 0 : (11 - resto);

        return (digitoVerificador1 == Character.getNumericValue(cnpj.charAt(12)) &&
                digitoVerificador2 == Character.getNumericValue(cnpj.charAt(13)));
    }
}
