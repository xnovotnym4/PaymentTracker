import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PaymentsList {
    private List<Payment> list;
    private boolean allowCurrencyCheck;
    private Map<String, Double> listOfCurrencies;
    private Integer printTimer;

    public PaymentsList(){
        list = new ArrayList<>();
    }

    public PaymentsList(boolean allowCurrencyCheck){
        list = new ArrayList<>();
        this.allowCurrencyCheck = allowCurrencyCheck;
    }

    public PaymentsList(boolean allowCurrencyCheck, Map<String, Double> listOfCurrencies, Integer printTimer){
        list = new ArrayList<>();
        this.allowCurrencyCheck = allowCurrencyCheck;
        this.listOfCurrencies = listOfCurrencies;
        this.printTimer = printTimer;
    }

    public void readPayment(String command){
        try {
            if (command.equals("quit")){
                System.exit(0);
            }
            String[] payment = command.split(" ");
            if (!payment[0].matches("[A-Z]+") || payment[0].length() != 3 || !payment[1].matches("-?[0-9]+")){
                throw new Exception("Invalid input format!");
            }
            if(allowCurrencyCheck){
                if(listOfCurrencies.get(payment[0]) == null && !payment[0].equals("USD")){
                    throw new Exception("Specified currency is not allowed!");
                }
            }
            list.add(new Payment(payment[0], Integer.parseInt(payment[1])));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void printPayments(){
        try {
            Map<String,Integer> groupedPayments = list.stream().collect(Collectors.groupingBy(Payment::getCurrency, Collectors.summingInt(Payment::getValue)));

            DecimalFormat df = new DecimalFormat("#.###");
            df.setRoundingMode(RoundingMode.CEILING);

            System.out.println("-------");
            for (Map.Entry<String, Integer> entry : groupedPayments.entrySet()) {
                if(entry.getValue() != 0) {
                    Double rateToUsd = listOfCurrencies.get(entry.getKey());
                    if (rateToUsd == null){
                        System.out.println(entry.getKey() + " " + entry.getValue());
                    }else{
                        System.out.println(entry.getKey() + " " + entry.getValue() + " (USD " + df.format(entry.getValue() * rateToUsd) + ")");
                    }

                }
            }
            System.out.println("-------");
        } catch (Exception e) {
            System.out.println("Payments printout failed!");
        }
    }

    public void loadFromFile(String fileName) throws IOException {
        FileInputStream fstream = new FileInputStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String lineString;

        while ((lineString = br.readLine()) != null)   {
            try {
                String[] payment = lineString.split(" ");
                list.add(new Payment(payment[0].trim(), Integer.parseInt(payment[1].trim())));
            }catch (Exception e){
                System.out.println("Invalid format of payment in input file: '" + lineString + "'");
            }
        }

        fstream.close();
    }

    public List<Payment> getList() {
        return list;
    }
}
