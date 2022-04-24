package hw4_20001983_NgoPhuongTrang.bai7;

public class ServiceDesk {
    Customer currentCustomer;
    boolean inService;
    double timeServed;
    int numCusServed;

    public ServiceDesk() {
        reset();
    }

    public void startServe(Customer customer) {
        currentCustomer = customer;
        inService = true;
    }

    public boolean finishServe(double time) {
        if (time == currentCustomer.timeToServe) {
            numCusServed++;
            timeServed += currentCustomer.timeToServe;
            currentCustomer = null;
            inService = false;
            return true;
        }
        return false;
    }

    public void reset() {
        currentCustomer = null;
        inService = false;
        timeServed = 0;
        numCusServed = 0;
    }
}
