import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PaymentTracker {
    private static int printTimer = 60000; //timer set in ms for payments print
    private static boolean allowCurrencyCheck = true; //allows input to be checked towards listed currencies

    public static void main(String [] args) throws IOException {
        Map<String, Double> listOfCurrencies = new HashMap<>();
        try {
            listOfCurrencies = loadCurrenciesRates(System.getProperty("user.dir") + "/../resources/currenciesRate.txt");
        }catch (FileNotFoundException e){
            allowCurrencyCheck = false;
            System.out.println("Currencies rate file was not found!");
        }
        PaymentsList paymentsList = new PaymentsList(allowCurrencyCheck, listOfCurrencies, printTimer);

        if(args.length != 0){
            try {
                paymentsList.loadFromFile(args[0]);
            }catch (FileNotFoundException e){
                System.out.println("Input file path incorrectly specified!");
            }
        }

        Thread thread1 = new Thread () {
            public void run () {
                while (true) {
                    paymentsList.printPayments();
                    try {
                        Thread.sleep(printTimer);
                    } catch (InterruptedException e) {
                        System.out.println("Thread execution interrupted!");
                    }
                }
            }
        };

        Thread thread2 = new Thread () {
            public void run () {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                while(true) {
                    try {
                        String command = reader.readLine().trim();
                        paymentsList.readPayment(command);
                    } catch (IOException e) {
                        System.out.println("Invalid input!");
                    }
                }
            }
        };

        thread1.start();
        thread2.start();
    }

    private static Map<String, Double> loadCurrenciesRates(String fileName) throws IOException {
        Map<String, Double> listOfCurrencies = new HashMap<>();

        FileInputStream fstream = new FileInputStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String lineString;

        while ((lineString = br.readLine()) != null)   {
            String[] currencyRate = lineString.split(" ");
            listOfCurrencies.put(currencyRate[0].trim(), Double.parseDouble(currencyRate[1].trim()));
        }

        fstream.close();
        return listOfCurrencies;
    }
}
