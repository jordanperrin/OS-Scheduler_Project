import java.util.LinkedList;
import java.util.Queue;

public class MLFQ extends PCB {

    //constructor
    public MLFQ() {
        super();
        this.Queue1 = new LinkedList<>();
        this.Queue2 = new LinkedList<>();
        this.Queue3 = new LinkedList<>();
    }

    public void start() {
        while((!Queue1.isEmpty()) || (!Queue2.isEmpty()) || (!Queue3.isEmpty()) || (!IOqueue.isEmpty())){
            this.runQueue1();
            this.runQueue2();
            this.runQueue3();
            this.checkIOqueue();
        }
        totalTime--;
        this.calaculateAverage();
        this.calculateCPUUtil();
        System.out.println("------------------------------MLFQ Data-------------------------------");
    }

    @Override
    public void checkIOqueue() {
        if(!IOqueue.isEmpty()){
            //same logic as SJF checkIOqueue but with a switch statement
            LinkedList<Process> cleanout = new LinkedList<>();
            for(Process p: IOqueue){
                if(p.IOwait <= this.totalTime){
                    this.ReadyList.add(p);
                    cleanout.add(p);
                }
            }

            //here I added switch statement which when we remove the process from IO queue it adds it back to the Queue that is apporopiate
            for(Process p : cleanout){
                this.IOqueue.remove(p);
                switch (p.currentQueue) {
                    case 1:
                        Queue1.add(p);
                        break;
                    case 2:
                        Queue2.add(p);
                        break;
                    case 3:
                        Queue3.add(p);
                        break;
                }
            }
        }

        //THIS is for CPU Idling
        if(!IOqueue.isEmpty() && Queue1.isEmpty() && Queue2.isEmpty() && Queue3.isEmpty()) {
            Process p = IOqueue.peek();
            p = findLowerIO(p, this.IOqueue);
            if(totalTime < p.IOwait){
                cpu_idle += Math.abs(p.IOwait - totalTime);
                totalTime += Math.abs(p.IOwait - totalTime);
            }
            IOqueue.remove(p);
            switch (p.currentQueue) {
                case 1:
                    Queue1.add(p);
                    break;
                case 2:
                    Queue2.add(p);
                    break;
                case 3:
                    Queue3.add(p);
                    break;
            }
    }

}

    public void runQueue1 () {
            while (!Queue1.isEmpty()) {
                System.out.println();
                System.out.println();
                System.out.println("Q1: " + this.q1_toString() );
                System.out.println("Q2: " + this.q2_toString() );
                System.out.println("Q3: " + this.q3_toString() );
                System.out.println("IO Queue: " + this.IOtoString() );

                this.currentP = Queue1.remove();
                this.currentP.markResponse(totalTime);
                this.currentP.calculateWaitTime();
                if(this.currentP.getCPUBurst() <= TQ_QUEUE1) { //HERE WE CAN RUN THE ENTIRE CPU BURST
                    Integer currCPUBurst = this.currentP.runCPU();
                    this.addTimeTotoal(currCPUBurst);
                    System.out.println("[ Excution Time: " + totalTime + "] Process " + this.currentP.getID() + " has ran");
                    System.out.print("[Time: " + totalTime + "] Total execution finished: ");
                    this.goingtoIO();
                } else { //HERE CPU BURST IS GREATER THAN TQ
                    this.addTimeTotoal(TQ_QUEUE1);
                    Integer currCPULeft = Math.abs(this.currentP.getCPUBurst() - TQ_QUEUE1);
                    this.currentP.setHeadCPUArrList(currCPULeft); //we adjust the current CPU Burst for the process since the entire Burst cant be ran
                    System.out.println("[Te: " + totalTime + "] - Process " + currentP.getID() + " ran");
                    System.out.print("[Te: " +  totalTime + "] Total execution has finished: No");
                    this.currentP.currentQueue = 2; //here we downgrade the process and this data point will be used in switch statement
                    this.Queue2.add(this.currentP); //add it to the lower queue
                }
                this.checkIOqueue();
            }
        }

    public void runQueue2 () {
        while (!Queue2.isEmpty() && Queue1.isEmpty()){
            System.out.println();
            System.out.println();
            System.out.println("Q1: " + this.q1_toString() );
            System.out.println("Q2: " + this.q2_toString() );
            System.out.println("Q3: " + this.q3_toString() );
            System.out.println("IO Queue: " + this.IOtoString() );
            this.currentP = Queue2.remove();
            this.currentP.markResponse(totalTime);
            this.currentP.calculateWaitTime();
            if (this.currentP.getCPUBurst() <= TQ_QUEUE2) { //HERE WE CAN RUN THE ENTIRE CPU BURST
                Integer currCPUBurst = this.currentP.runCPU();
                this.addTimeTotoal(currCPUBurst);
                System.out.println("[ Excution Time: " + totalTime + "] Process " + this.currentP.getID() + " has ran");
                System.out.print("[Time: " + totalTime + "] Total execution finished: ");
                this.goingtoIO();
            } else { //HERE CPU BURST IS GREATER THAN TQ
                this.addTimeTotoal(TQ_QUEUE2);
                Integer currCPULeft = Math.abs(this.currentP.getCPUBurst() - TQ_QUEUE2);
                this.currentP.setHeadCPUArrList(currCPULeft);
                System.out.println("[Te: " + totalTime + "] - Process " + currentP.getID() + " ran");
                System.out.print("[Te: " +  totalTime + "] Total execution has finished: No");
                Queue3.add(this.currentP);
                this.currentP.currentQueue = 3; //here we update the current queue since it will be downgraded once more
            }
            this.checkIOqueue();
        }
    }

    public void runQueue3 () {
        //Here the logic is the same FCFS while loop
        while (!Queue3.isEmpty() && Queue2.isEmpty() && Queue1.isEmpty()){
            System.out.println();
            System.out.println();
            System.out.println("Q1: " + this.q1_toString() );
            System.out.println("Q2: " + this.q2_toString() );
            System.out.println("Q3: " + this.q3_toString() );
            System.out.println("IO Queue: " + this.IOtoString() );
            this.currentP = Queue3.remove();
            this.currentP.markResponse(totalTime);
            System.out.println("[ Excution Time: " + totalTime + "] Process " + this.currentP.getID() + " has ran");
            System.out.print("[Time: " + totalTime + "] Total execution finished: ");
            this.currentP.calculateWaitTime();
            Integer currCPUBurst = this.currentP.runCPU();
            this.addTimeTotoal(currCPUBurst);
            this.goingtoIO();
            this.checkIOqueue();
            }
        }


        //here we overrided the addProcess from parent PCB class to add to the queue1
    @Override
    public void addProcesses (Process...process){
        for (Process p : process) {
            this.Queue1.add(p);
            this.StoreAllP.add(p);
        }
    }

    private String q1_toString() {
        if (this.Queue1.isEmpty()) return "Empty";

        StringBuilder sb = new StringBuilder();
        for(Process p : this.Queue1) {
            sb.append("P" + p.getID() + "(" + p.getCPUBurst() + ") ");
        }
        return sb.toString();
    }

    private String q2_toString() {
        if (this.Queue2.isEmpty()) return "Empty";

        StringBuilder sb = new StringBuilder();
        for(Process p : this.Queue2) {
            sb.append("P" + p.getID() + "(" + p.getCPUBurst() + ") ");
        }
        return sb.toString();
    }

    private String q3_toString() {
        if (this.Queue3.isEmpty()) return "Empty";

        StringBuilder sb = new StringBuilder();
        for(Process p : this.Queue3) {
            sb.append("P" + p.getID() + "(" + p.getCPUBurst() + ") ");
        }
        return sb.toString();
    }

    public Queue<Process> Queue1;
    public Queue<Process> Queue2;
    public Queue<Process> Queue3;

    private final Integer TQ_QUEUE1 = 5;
    private final Integer TQ_QUEUE2 = 10;

}

