import Universe.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class MoneyTest{
    @Test
    public void testMoney() {
        Money money1 = new Money(0, 0, 1, 1);
        Money money2 = new Money(10, 1, 9, 18);
        Money money3 = new Money(10,2,1,9);
        Assertions.assertEquals(money2.addMoney(money1), money3);
        Assertions.assertEquals(money3.substractMoney(money1), money2);
        money2.substractMoney(money1);
        System.out.println(money2);
    }
}
