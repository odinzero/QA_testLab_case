package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import models.PriceData;

public class utils {

    public static String calculateDiscountPrice(String regularPrice, String discount) {

        String value_Price = regularPrice.substring(regularPrice.length() - 2);

        double num_Price = Double.parseDouble(regularPrice.substring(0, regularPrice.length() - 1).replace(",", "."));
        double num_discount = Double.parseDouble(discount.substring(0, discount.length() - 1).replace(",", "."));

        double res = num_Price * ((100 - Math.abs(num_discount)) / 100);
        //round to format #.##
        res = round2(res);

        String result = String.valueOf(res).replace(".", ",").concat(value_Price);

        return result;
    }

    // round to format #.##
    public static Double round2(Double val) {
        return new BigDecimal(val.toString()).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public static void rsort(List<PriceData> list) {
        list.sort((lhs, rhs) -> {
            if (lhs.getRegularPrice().equals(rhs.getRegularPrice())) {
                return rhs.getDiscount().compareTo(lhs.getDiscount());
            } else {
                return rhs.getRegularPrice().compareTo(lhs.getRegularPrice());
            }
        });
    }

    public static void logsToFile(String text) {
                
        try (FileWriter fw = new FileWriter("..//src/test/resources/log.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.println(text);
            out.close();
        } catch (IOException e) {
            System.out.println("write logs to log.txt error: " + e);
        }
    }
    
    public static void writeToFileAndConsole(String message) {
        utils.logsToFile(message);
        System.out.println(message);
    }

}
