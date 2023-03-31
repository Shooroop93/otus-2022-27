import org.junit.Before;
import org.junit.Test;
import ru.sergeev.ATM;
import ru.sergeev.Banknote;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ATMTests {
    private ATM atm;

    @Before
    public void setUp() {
        atm = new ATM();
    }

    @Test
    public void testDeposit() {
        atm.deposit(new Banknote(50));
        atm.deposit(new Banknote(100));
        atm.deposit(new Banknote(100));

        assertEquals(atm.getBalance(), 250);
    }

    @Test
    public void testWithdrawSuccess() {
        atm.deposit(new Banknote(100));
        atm.deposit(new Banknote(100));
        atm.deposit(new Banknote(50));

        Map<Integer, Integer> result = atm.withdraw(150);

        assertEquals(result.size(), 2);
        assertEquals(result.get(100).intValue(), 1);
        assertEquals(result.get(50).intValue(), 1);
        assertEquals(atm.getBalance(), 100);
    }

    @Test(expected = RuntimeException.class)
    public void testWithdrawNotEnoughMoney() {
        atm.deposit(new Banknote(100));

        atm.withdraw(150);
    }

    @Test(expected = RuntimeException.class)
    public void testWithdrawNotDispensable() {
        atm.deposit(new Banknote(100));
        atm.deposit(new Banknote(50));

        atm.withdraw(80);
    }
}