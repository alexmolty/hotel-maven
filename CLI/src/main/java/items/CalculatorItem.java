package items;

import cli.InputOutput;
import cli.Item;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class CalculatorItem implements Item {
    private final InputOutput inOut;


    private final Map<String, BiFunction<Double, Double, Double>> operations = new LinkedHashMap<>();


    public CalculatorItem(InputOutput inOut) {
        this.inOut = inOut;


        operations.put("+", (a, b) -> a + b);
        operations.put("-", (a, b) -> a - b);
        operations.put("*", (a, b) -> a * b);
        operations.put("/", (a, b) -> a / b);
    }


    @Override
    public String displayName() {
        return "Calculator";
    }


    @Override
    public void perform() {
        Double firstNumber = inOut.inputDouble("Enter first number");
        if (firstNumber == null) {
            return;
        }


        String operation = inOut.inputString("Enter operation", operations.keySet().stream().toList());
        if (operation == null) {
            return;
        }


        Double secondNumber = inOut.inputDouble("Enter second number");
        if (secondNumber == null) {
            return;
        }


        if (operation.equals("/") && secondNumber == 0) {
            inOut.outputLine("Division by zero is not allowed");
            return;
        }


        Double result = operations.get(operation).apply(firstNumber, secondNumber);
        inOut.outputLine("Result: " + result);
    }
}
