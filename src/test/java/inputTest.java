import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class inputTest {
    @Test
    public void correctInputTest(){
        String currency = "EUR";
        Integer value = 2254;

        PaymentsList payments = new PaymentsList(false);
        payments.readPayment(currency + " " + value);

        Assert.assertEquals(1, payments.getList().size());
    }

    @Test
    public void incorrectInputTest1(){
        String currency = "hud";
        Integer value = 2254;

        PaymentsList payments = new PaymentsList(false);
        payments.readPayment(currency + " " + value);

        Assert.assertEquals(0, payments.getList().size());
    }

    @Test
    public void incorrectInputTest2(){
        String currency = "ABCDE";
        Integer value = 2254;

        PaymentsList payments = new PaymentsList(false);
        payments.readPayment(currency + " " + value);

        Assert.assertEquals(0, payments.getList().size());
    }


}
