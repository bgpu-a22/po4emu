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
        Reflections reflections = new Reflections(prefix, Scanners.FieldsAnnotated);

        File prop = new File(filePropName);
        if (prop.isFile()) {
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

                            if (field.getType().equals(String.class)) {
                                targetValue = value;
                            } else if (field.getType().equals(Integer.class)) {
                                targetValue = Integer.valueOf(value);
                            } else if (field.getType().equals(int.class)) {
                                targetValue = Integer.parseInt(value);
                            } else if (field.getType().equals(Float.class)) {
                                targetValue = Float.valueOf(value);
                            } else if (field.getType().equals(float.class)) {
                                targetValue = Float.parseFloat(value);
                            } else if (field.getType().equals(Double.class)) {
                                targetValue = Double.valueOf(value);
                            } else if (field.getType().equals(double.class)) {
                                targetValue = Double.parseDouble(value);
                            } else if (field.getType().equals(String[].class)) {
                                targetValue = value.split(",");
                            } else if (field.getType().equals(Integer[].class)) {
                                String[] values = value.split(",");
                                Integer[] array = new Integer[values.length];
                                for (int i = 0; i < values.length; i++) {
                                    array[i] = Integer.valueOf(values[i].trim());
                                }
                                targetValue = array;
                            } else if (field.getType().equals(int[].class)) {
                                String[] values = value.split(",");
                                int[] array = new int[values.length];
                                for (int i = 0; i < values.length; i++) {
                                    array[i] = Integer.parseInt(values[i].trim());
                                }
                                targetValue = array;
                            } else if (field.getType().equals(Float[].class)) {
                                String[] values = value.split(",");
                                Float[] array = new Float[values.length];
                                for (int i = 0; i < values.length; i++) {
                                    array[i] = Float.valueOf(values[i].trim());
                                }
                                targetValue = array;
                            } else if (field.getType().equals(float[].class)) {
                                String[] values = value.split(",");
                                float[] array = new float[values.length];
                                for (int i = 0; i < values.length; i++) {
                                    array[i] = Float.parseFloat(values[i].trim());
                                }
                                targetValue = array;
                            } else if (field.getType().equals(Double[].class)) {
                                String[] values = value.split(",");
                                Double[] array = new Double[values.length];
                                for (int i = 0; i < values.length; i++) {
                                    array[i] = Double.valueOf(values[i].trim());
                                }
                                targetValue = array;
                            } else if (field.getType().equals(double[].class)) {
                                String[] values = value.split(",");
                                double[] array = new double[values.length];
                                for (int i = 0; i < values.length; i++) {
                                    array[i] = Double.parseDouble(values[i].trim());
                                }
                                targetValue = array;
                            }

                            try {
                                field.setAccessible(true);
                                field.set(field.getDeclaringClass(), targetValue);
                                field.setAccessible(false);
                            } catch (IllegalAccessException e) {
                                logger.log(
                                        Level.WARNING,
                                        "error set " + field.getDeclaringClass().getName()
                                                + "." + field.getName() + " " + value
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