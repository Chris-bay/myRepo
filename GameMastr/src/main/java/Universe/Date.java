package Universe;

public class Date {

    private static final int MITTELREICH = 993;
    private static final int HORASREICH = -1491;
    private static final int AL_ANFA = 686;
    private static final int ARANIEN = 995;
    private static final int NOSTRIA_ANDERGAST = -854;
    private static final int none = 0;
    private static final int erster = 0;
    private static final int zweiter = 1;

    Integer day;
    Month month;
    Integer year;
    Time time;

    enum Month{
        PRAIOS,
        RONDRA,
        EFFERD,
        TRAVIA,
        BORON,
        HESINDE,
        FIRUN,
        TSA,
        PHEX,
        PERAINE,
        INGERIMM,
        RAHJA;
    }

    enum Hour{
        PRAIOS,
        RONDRA,
        EFFERD,
        TRAVIA,
        BORON,
        HESINDE,
        FIRUN,
        TSA,
        PHEX,
        PERAINE,
        INGERIMM,
        RAHJA;
    }

    public class Time{
        Hour hour;
        int offset;
        Integer minute;
        Integer second;

        Time(Hour hour, int offset, Integer minute, Integer second){
            this.hour = hour;
            this.offset = offset;
            this.minute = minute;
            this.second = second;
        }
    }

    Date(Integer day, Month month, Integer year, int offset){
        if (day > 30) day = 30;
        if (day < 0 ) day = 0;
        this.day = day;
        this.month = month;
        this.year = year + offset;
        this.time = new Time(Hour.PRAIOS, erster, 0, 0);
    }

    void addTime(Integer day, Month month, Integer year){

    }

}
