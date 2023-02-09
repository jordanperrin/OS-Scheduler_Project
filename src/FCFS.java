import java.util.LinkedList;

public class FCFS extends PCB{

    //constructor - just calls the parent constructor hints the super() method call
    public FCFS(){
        super();
    }
    public void start() {
        while(!ReadyList.isEmpty()){
            System.out.println("Current State of ReadyList:"+ RQtoString());
            System.out.println("Current State of IO Queue:"+ IOtoString());
            this.currentP = ReadyList.remove(); //take first process of the queue
            this.currentP.markResponse(totalTime); //mark response time
            this.currentP.calculateWaitTime(); //go calculate wait time
            Integer currCPUBurst = this.currentP.runCPU(); //get current CPU Burst
            this.addTimeTotoal(currCPUBurst); //add the Burst to the total time
            System.out.println("[ Excution Time: " + totalTime + "] Process " + this.currentP.getID() + " has ran");
            System.out.print("[Time: " + totalTime + "] Total execution finished: ");
            this.goingtoIO(); //here we go to see if there any IOs left to run, if so we add it to IO queue, if not we add it to finished
            this.checkIOqueue(); //see if we can remove any IOs from IOQueue or see if we have any CPU Idling
        }
        totalTime--;
        this.calaculateAverage(); //calculates AVreage wait, average turnaround and average response
        this.calculateCPUUtil();//calculate cpu utilization
        System.out.println("-------------------------------FCFS Data-------------------------------");
    }


}
