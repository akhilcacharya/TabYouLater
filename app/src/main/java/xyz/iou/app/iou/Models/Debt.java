package xyz.iou.app.iou.Models;

/**
 * Created by akhilacharya on 10/10/15.
 */
public class Debt {
    private Person creditor;
    private Person debtor;
    private long amountOwed;

    public Person getCreditor() {
        return creditor;
    }

    public void setCreditor(Person creditor) {
        this.creditor = creditor;
    }

    public Person getDebtor() {
        return debtor;
    }

    public void setDebtor(Person debtor) {
        this.debtor = debtor;
    }

    public long getAmountOwed() {
        return amountOwed;
    }

    public void setAmountOwed(long amountOwed) {
        this.amountOwed = amountOwed;
    }
}
