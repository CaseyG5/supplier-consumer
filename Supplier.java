package suppcons;

public class Supplier implements Runnable {
    private final int ID;
    private double data;
    MeetingRoom room;
    Thread sThr;

    Supplier(String name, int id, MeetingRoom rm) {
        ID = id;
        room = rm;
        sThr = new Thread(this, name);
        sThr.start();
    }

    public void run() {
        int sleeptime;
        short transactions = 0;
        
        while(transactions < 10) {              // 10 transactions then stop
            sleeptime = (int) (Math.random() * 1001);
            try {
                System.out.println(sThr.getName() + " sleeps for " +
                        sleeptime + "ms.");
                Thread.sleep(sleeptime);        // sleep for random period
            } catch(InterruptedException exc) { 
                System.out.println(sThr.getName() + " interrupted.");
            }
            
            data = Math.random() * 100.0;       // create a random number
            System.out.println(sThr.getName() + " produces data.");
            
            // Go to the meeting/waiting room then leave the data
            room.putData(data, ID);
            transactions++;
        }
        System.out.println(sThr.getName() + " stopping.");
    }
}
