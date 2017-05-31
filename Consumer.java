package suppcons;

public class Consumer implements Runnable {
    private final int ID;
    private double data;
    MeetingRoom room;
    Thread cThr;

    Consumer(String name, int id, MeetingRoom rm) {
        ID = id;
        room = rm;
        cThr = new Thread(this, name);
        cThr.start();
    }

    public void run() {
        int sleeptime;
        short transactions = 0;
        
        while(transactions < 10) {              // 10 transactions then stop
            
            // Go to the meeting/waiting room then remove the data
            room.getData(ID);
            transactions++;
            
            sleeptime = (int) (Math.random() * 1001);
            try {
                System.out.println(cThr.getName() + " sleeps for " +
                        sleeptime + "ms.");
                Thread.sleep(sleeptime);        // sleep for a random period
            } catch(InterruptedException exc) { 
                System.out.println(cThr.getName() + " interrupted.");
            }
        }
        System.out.println(cThr.getName() + " stopping.");
    }
}
