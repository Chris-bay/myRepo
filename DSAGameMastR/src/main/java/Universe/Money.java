package Universe;

public class Money {
    private Integer Dukaten;
    private Integer Silber;
    private Integer Heller;
    private Integer Kreuzer;

    /**
     * Creates a new Money instance from given values and converts it incase something is wrong
     * (each Value should be between 0 and 9).
     * @param k
     */
    public Money(Integer d, Integer s, Integer h, Integer k){
        setFromKreuzer(k + 10 * (h + 10 * (s + 10 * d)));
    }

    /**
     * Creates a new Money instance from smallest currency
     * @param k
     */
    public Money(Integer k){
        setFromKreuzer(k);
    }

    public final static Money none = new Money(0,0,0,0);

    /**
     * Used to add a constant Money value to this Money object.
     * It simply creates a dummy object and calls the other overloaded function with this dummy object.
     * @param d Dukaten
     * @param s Silber
     * @param h Heller
     * @param k Kreuzer
     */
    public void addMoney(Integer d, Integer s, Integer h, Integer k){
        this.addMoney(new Money(d,s,h,k));
    }

    /***
     * @param money
     * Converts given Money and own Money in the smallest Currency,
     * adds it together and convert it back again.
     */
    public void addMoney(Money money){
        Integer allToKreuzer = this.convertInKreuzer() + money.convertInKreuzer();

        this.setFromKreuzer(allToKreuzer);
    }

    /**
     * Converts given Money and own Money in the smallest Currency,
     * subtract the given Money and converts it back again.
     * Should the result < 0 the money will be set to 0;
     * @param money
     */
    public void substractMoney(Money money){
        Integer allToKreuzer = this.convertInKreuzer() - money.convertInKreuzer();
        if (allToKreuzer > 0){
            this.setFromKreuzer(allToKreuzer);
        }else{
            this.setFromKreuzer(0);
        }
    }

    /**
     * This Function converts the Money in its class to the lowest Value.
     * Since every Value = 10 * the lower Value, its quite easy to convert everything
     * this function is mainly used in the add function.
     * @return calculated Value
     */
    public Integer convertInKreuzer(){
        return this.Kreuzer + 10 * (this.Heller + 10 * (this.Silber + 10 * this.Dukaten));
    }

    /**
     * Converts an Amount of lowest Currency in the other values and spits it out as a Money object.
     * Each Value = 10* the One Tier lower Value.
     * eg.: 1 Dukate = 10 Silber and so on.
     * @param allInKreuzer
     * @return
     */
    public Money convertFromKreuzer(Integer allInKreuzer){

        /*
        System.out.println(allToKreuzer % 10);
        System.out.println((allToKreuzer % 100) / 10);
        System.out.println((allToKreuzer % 1000) / 100);
        System.out.println((allToKreuzer - (allToKreuzer % 1000))/1000);*/

        Money retMoney = Money.none;
        retMoney.Kreuzer = allInKreuzer % 10;
        retMoney.Heller  = ((allInKreuzer % 100)  - (allInKreuzer % 10))/10;
        retMoney.Silber  = ((allInKreuzer % 1000) - (allInKreuzer % 100))/100;
        retMoney.Dukaten = (allInKreuzer - (allInKreuzer % 1000))/1000;
        return retMoney;
    }

    /**
     * Similar to convertFromKreuzer
     * Sets current Currency values according to a given value of the lowest currency.
     * @param allInKreuzer
     */
    public void setFromKreuzer(Integer allInKreuzer){

        /*
        System.out.println(allToKreuzer % 10);
        System.out.println((allToKreuzer % 100) / 10);
        System.out.println((allToKreuzer % 1000) / 100);
        System.out.println((allToKreuzer - (allToKreuzer % 1000))/1000);*/

        this.Kreuzer = allInKreuzer % 10;
        this.Heller  = ((allInKreuzer % 100)  - (allInKreuzer % 10))/10;
        this.Silber  = ((allInKreuzer % 1000) - (allInKreuzer % 100))/100;
        this.Dukaten = (allInKreuzer - (allInKreuzer % 1000))/1000;
    }

    public Integer getDukaten() {
        return Dukaten;
    }

    public void setDukaten(Integer dukaten) {
        Dukaten = dukaten;
    }

    public Integer getSilber() {
        return Silber;
    }

    public void setSilber(Integer silber) {
        Silber = silber;
    }

    public Integer getHeller() {
        return Heller;
    }

    public void setHeller(Integer heller) {
        Heller = heller;
    }

    public Integer getKreuzer() {
        return Kreuzer;
    }

    public void setKreuzer(Integer kreuzer) {
        Kreuzer = kreuzer;
    }

    @Override
    public String toString() {
        return this.Dukaten + "|" + this.Silber + "|" + this.Heller + "|" + this.Kreuzer;
    }
}
