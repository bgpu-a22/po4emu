package ru.bgpu.annotationlk;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppConfigWorker {

    private static Logger logger = Logger.getLogger(AppConfigWorker.class.getName());

    public static void configProcessing(String prefix, String filePropName) {

        FieldAnnotationsScanner scanner = new FieldAnnotationsScanner();    

        File prop = new File(filePropName);
        if(prop.isFile()) {
            try {
                Properties properties = new Properties();
                properties.load(new FileInputStream(prop));

                reflections.getFieldsAnnotatedWith(AppConfig.class).forEach(
                        field -> {

                            String value = properties.getProperty(
                                    field.getName(),
                                    field.getAnnotation(AppConfig.class).defValue()
                            );
                            Object targetValue = null;

                            // Обработка типов поля
                            if (field.getType().equals(String.class)) {
                                targetValue = value; // Просто присваиваем строку
                            } else if (field.getType().equals(Integer.class)) {
                                targetValue = Integer.valueOf(value); // Преобразуем строку в объект Integer
                            } else if (field.getType().equals(int.class)) {
                                targetValue = Integer.parseInt(value); // Преобразуем строку в примитив int
                            } else if (field.getType().equals(Float.class)) {
                                targetValue = Float.valueOf(value); // Преобразуем строку в объект Float
                            } else if (field.getType().equals(float.class)) {
                                targetValue = Float.parseFloat(value); // Преобразуем строку в примитив float
                            } else if (field.getType().equals(Double.class)) {
                                targetValue = Double.valueOf(value); // Преобразуем строку в объект Double
                            } else if (field.getType().equals(double.class)) {
                                targetValue = Double.parseDouble(value); // Преобразуем строку в примитив double
                            } else if (field.getType().equals(String[].class)) {
                                targetValue = value.split(","); // Разбиваем строку по запятой на массив строк
                            } else if (field.getType().equals(Integer[].class)) {
                                String[] values = value.split(","); // Разбиваем строку на части
                                Integer[] array = new Integer[values.length]; // Создаем массив объектов Integer
                                for (int i = 0; i < values.length; i++) {
                                    array[i] = Integer.valueOf(values[i].trim()); // Преобразуем каждую часть в Integer
                                }
                                targetValue = array; // Присваиваем готовый массив
                            } else if (field.getType().equals(int[].class)) {
                                String[] values = value.split(","); // Разбиваем строку на части
                                int[] array = new int[values.length]; // Создаем массив примитивов int
                                for (int i = 0; i < values.length; i++) {
                                    array[i] = Integer.parseInt(values[i].trim()); // Преобразуем каждую часть в int
                                }
                                targetValue = array; // Присваиваем готовый массив
                            } else if (field.getType().equals(Float[].class)) {
                                String[] values = value.split(","); // Разбиваем строку на части
                                Float[] array = new Float[values.length]; // Создаем массив объектов Float
                                for (int i = 0; i < values.length; i++) {
                                    array[i] = Float.valueOf(values[i].trim()); // Преобразуем каждую часть в Float
                                }
                                targetValue = array; // Присваиваем готовый массив
                            } else if (field.getType().equals(float[].class)) {
                                String[] values = value.split(","); // Разбиваем строку на части
                                float[] array = new float[values.length]; // Создаем массив примитивов float
                                for (int i = 0; i < values.length; i++) {
                                    array[i] = Float.parseFloat(values[i].trim()); // Преобразуем каждую часть в float
                                }
                                targetValue = array; // Присваиваем готовый массив
                            } else if (field.getType().equals(Double[].class)) {
                                String[] values = value.split(","); // Разбиваем строку на части
                                Double[] array = new Double[values.length]; // Создаем массив объектов Double
                                for (int i = 0; i < values.length; i++) {
                                    array[i] = Double.valueOf(values[i].trim()); // Преобразуем каждую часть в Double
                                }
                                targetValue = array; // Присваиваем готовый массив
                            } else if (field.getType().equals(double[].class)) {
                                String[] values = value.split(","); // Разбиваем строку на части
                                double[] array = new double[values.length]; // Создаем массив примитивов double
                                for (int i = 0; i < values.length; i++) {
                                    array[i] = Double.parseDouble(values[i].trim()); // Преобразуем каждую часть в double
                                }
                                targetValue = array; // Присваиваем готовый массив
                            }

                            try {
                                field.setAccessible(true);
                                field.set(field.getDeclaringClass(), targetValue);
                                field.setAccessible(false);
                            } catch (IllegalAccessException e) {
                                logger.log(
                                        Level.WARNING,
                                        "error set "+field.getDeclaringClass().getName()
                                                +"."+field.getName()+" "+value
                                );
                            }
                        }
                );
            } catch (Exception e) {
                logger.log(Level.WARNING, "error load properties", e);
            }
        } else {
            logger.log(Level.WARNING, "config file not found");
        }
    }
}