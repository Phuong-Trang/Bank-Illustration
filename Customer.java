package hw4_20001983_NgoPhuongTrang.bai7;

public class Customer {
    int num;
    String request;
    double timeToServe;

    final String[] REQUESTS = {"Transfer money","Receive money", "Print financial report", "Get a loan"};
    final double[] TIME = {0.25, 0.25, 1, 0.5};

    public Customer(int num) {
        this.num = num;
        int i = (int) (Math.random() * 4);
        request = REQUESTS[i];
        timeToServe = TIME[i];
    }
}
