package pl.sda.springcalculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CalculatorService {

    @Autowired
    CalculatorDAO calculatorDAO;

    public Long calculate(String valueToCalculate) {

        if (calculatorDAO.checkIfExist(valueToCalculate)) {
            return calculatorDAO.giveValue(valueToCalculate);
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Pattern pattern = Pattern.compile("(\\d)(\\D)(\\d)");
        Matcher matcher = pattern.matcher(valueToCalculate);
        matcher.matches();
        String group1 = matcher.group(1);
        String operand = matcher.group(2);
        String group3 = matcher.group(3);

        Long result = switches(group1, operand, group3);

        if (result != null) {
            calculatorDAO.addToCache(valueToCalculate, result);
            return result;
        }
        return null;
    }

    private Long switches(String group1, String operand, String group3) {
        switch (operand) {
            case "+":
                return Long.valueOf(group1) + Long.valueOf(group3);
            case "-":
                return Long.valueOf(group1) - Long.valueOf(group3);
            case "*":
                return Long.valueOf(group1) * Long.valueOf(group3);
            case "/":
            case "\\":
                //można podać pod sobą kilka casów do jednego działąnia
                //podajemy bachslash i slash na wypadek gdyby ktoś się pomylił i źle wpisał
                return Long.valueOf(group1) / Long.valueOf(group3);
        }
        return null;
    }

    public List<String> useCreator(String valueToCalculate) {
        return calculatorDAO.historyListCreator(valueToCalculate);
    }
}
