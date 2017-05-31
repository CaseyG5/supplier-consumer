package suppcons;

class MeetingRoom {
    double data;
    int destID = -1;
    boolean dataSupplied = false;

    synchronized void putData(double d, int myID) {
        if(dataSupplied) {          // if data has not been retrieved
            System.out.println("Supplier " + myID + " enters meeting "
                        + "room.");
            System.out.println("Supplier " + myID + " enters "
                + "the waiting room.");
        }
        while(dataSupplied) {
            try {   wait();   }                          // relax in waiting room
            catch(InterruptedException exc) {
                System.out.println("Supplier " + myID + " interrupted.");
            }
        }
        
        System.out.println("* Supplier " + myID + " enters the meeting "
                        + "room / leaves data for consumer " + myID);
        data = d;                   // leave data
        destID = myID;              // for consumer X
        dataSupplied = true;
        System.out.println("Supplier " + myID + " exits meeting room.");
        notifyAll();
    }
    
    synchronized double getData(int myID) {
        if(destID != myID) {        // if data is NOT marked for consumer X
            System.out.println("Consumer " + myID + " enters the "
                        + "meeting room.");
            System.out.println("Consumer " + myID + " enters "
                + "the waiting room.");
        }
        while(!dataSupplied || destID != myID ) {   
            try {
                wait();                             // relax in waiting room
            } catch(InterruptedException exc) {
                System.out.println("Consumer " + myID + " interrupted.");
            }
        }

        System.out.println("* Consumer " + myID + " enter the meeting "
                + "room / removes data.");
        double temp;
        temp = data;                // copy data
        data = 0;                   // and reset fields
        destID = -1;
        dataSupplied = false;
        System.out.println("Consumer " + myID + " exits the "
                        + "meeting room.");
        notifyAll();
        return temp;
    }    
}

class SuppCons {
    public static void main(String[] args) {
        MeetingRoom mr1 = new MeetingRoom();
        Supplier s1 = new Supplier("Supplier 1", 1, mr1);
        Supplier s2 = new Supplier("Supplier 2", 2, mr1);
         Consumer c1 = new Consumer("Consumer 1", 1, mr1);
         Consumer c2 = new Consumer("Consumer 2", 2, mr1);
        Supplier s3 = new Supplier("Supplier 3", 3, mr1);
         Consumer c3 = new Consumer("Consumer 3", 3, mr1);
        Supplier s4 = new Supplier("Supplier 4", 4, mr1);
         Consumer c4 = new Consumer("Consumer 4", 4, mr1);
        Supplier s5 = new Supplier("Supplier 5", 5, mr1);
         Consumer c5 = new Consumer("Consumer 5", 5, mr1);
        Supplier s6 = new Supplier("Supplier 6", 6, mr1);
        Supplier s7 = new Supplier("Supplier 7", 7, mr1);
         Consumer c6 = new Consumer("Consumer 6", 6, mr1);
         Consumer c7 = new Consumer("Consumer 7", 7, mr1);
        Supplier s8 = new Supplier("Supplier 8", 8, mr1);
        Supplier s9 = new Supplier("Supplier 9", 9, mr1);
        Supplier s10 = new Supplier("Supplier 10", 10, mr1);
         Consumer c8 = new Consumer("Consumer 8", 8, mr1);
         Consumer c9 = new Consumer("Consumer 9", 9, mr1);
         Consumer c10 = new Consumer("Consumer 10", 10, mr1);
    }
}
/*
Supplier 1 sleeps for 216ms.
Supplier 2 sleeps for 197ms.
Consumer 1 enters the meeting room.
Consumer 1 enters the waiting room.
Consumer 2 enters the meeting room.
Consumer 2 enters the waiting room.
...                                     // excessive output
Supplier 6 sleeps for 61ms.
Supplier 7 sleeps for 222ms.
Consumer 6 enters the meeting room.
Consumer 6 enters the waiting room.
...
Supplier 6 produces data.
* Supplier 6 enters the meeting room / leaves data for consumer 6
Supplier 6 exits meeting room.
Supplier 6 sleeps for 11ms.
* Consumer 6 enter the meeting room / removes data.
Consumer 6 exits the meeting room.
Consumer 6 sleeps for 842ms.
...
Supplier 9 produces data.
* Supplier 9 enters the meeting room / leaves data for consumer 9
Supplier 9 exits meeting room.
Supplier 9 stopping.
* Consumer 9 enter the meeting room / removes data.
Consumer 9 exits the meeting room.
Consumer 9 sleeps for 910ms.
Consumer 4 stopping.
Consumer 9 stopping.
*/