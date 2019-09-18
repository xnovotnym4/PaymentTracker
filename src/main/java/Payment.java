public class Payment {
    private String currency;
    private Integer value;

    public Payment(){

    }

    public Payment(String currency, Integer value){
        this.currency = currency;
        this.value = value;
    }

    public void printPayment(){
        System.out.println(currency + " " + value);
    }

    public String getCurrency() {
        return currency;
    }

    public Integer getValue() {
        return value;
    }
}
