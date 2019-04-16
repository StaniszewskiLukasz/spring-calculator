package pl.sda.springcalculator;

import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CalculatorDAO {

    Map<String, Long> resultsCache = new LinkedHashMap<>();

    public boolean checkIfExist(String valueToCalculate) {
        return resultsCache.containsKey(valueToCalculate);
    }

    public Long giveValue(String valueToCalculate) {
        return resultsCache.get(valueToCalculate);
    }

    public void addToCache(String valueToCalculate, Long result) {
        resultsCache.put(valueToCalculate, result);
    }

    public List<String> historyListCreator(String currentQuery) {
        return resultsCache
                .entrySet()
                .stream()
                .filter(e->!e.equals(currentQuery))
                .map(e->e.getKey() + "=" + e.getValue())
                .collect(Collectors.toList());
    }
}
