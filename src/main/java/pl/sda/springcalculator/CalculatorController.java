package pl.sda.springcalculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CalculatorController {

    @Autowired
    CalculatorService calculatorService;

    @RequestMapping(value = "/calculate", method = RequestMethod.GET)
    //akcja request "calculate" uruchami metodę GET
    public String showForm(@RequestParam(required = false) String valueToCalculate, Model model){
        Long value = calculatorService.calculate(valueToCalculate);
        //ta linia oblicza
        model.addAttribute("result", value);
        //ta linia pokazuje wynik na stronie
        model.addAttribute("historyList", calculatorService.useCreator(valueToCalculate));
        //ta linia wyświetla listę historycznych obliczeń
        return "calculatorForm";
    }

}
