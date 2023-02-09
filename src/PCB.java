import java.text.DecimalFormat;
import java.util.*;
public class PCB {
    //constructor
    public PCB(){
        this.ReadyList = new LinkedList<Process>();
        currentP = null;
        this.totalTime = 0;
        this.cpu_idle = 0;
        totalTime = 0;
        this.finishedQueue = new LinkedList<>();
        this.IOqueue = new LinkedList<>();
        this.StoreAllP = new LinkedList<>();
        this.AvgWaitTime = 0;
        this.AvgTurnaroundTime = 0;
        this.AvgResponseTime = 0;
        this.CPU_Util = 0;
    }
    public void addTimeTotoal(int time){
        this.totalTime += time;
    }
    public void addProcesses(Process...process){
        for(Process p : process){
            this.ReadyList.add(p);
            this.StoreAllP.add(p);
        }
    }
    public void printTime(){
        System.out.println(totalTime);
    }

    public void calculateCPUUtil(){
        this.CPU_Util = (double) ((this.totalTime - this.cpu_idle) / (this.totalTime))*100 ;
        //use the formula to compute the CPU utilization
        }

    public void calaculateAverage(){
        double i = 0.0;
        double sumWait = 0.0;
        double sumTurn = 0.0;
        double sumResp = 0.0;
        for(Process p: StoreAllP){
            i++;
           sumWait += p.waitTime;
           sumTurn += p.departTime;
           sumResp += p.responseTime;
        }

        this.AvgWaitTime = sumWait/i;
        this.AvgTurnaroundTime = sumTurn/i;
        this.AvgResponseTime = sumResp/i;
    }

    public void printAnalytics(){

        for(Process p : StoreAllP){
            p.printDepartResponse();
        }

        System.out.println("Total time: " + totalTime);
        System.out.printf("Average wait time: %.2f \n", this.AvgWaitTime);
        System.out.printf("Average turnaround time: %.2f \n", this.AvgTurnaroundTime);
        System.out.printf("Average response time: %.2f \n", this.AvgResponseTime);
        System.out.printf("CPU Utilization: %.2f \n", this.CPU_Util);

        System.out.println("-------------------------------------------------------------------------");
    }

    //finds teh lowest IO
    public static Process findLowerIO(Process p, Queue<Process>IOqueue){
        for(Process temp: IOqueue){
            if(temp.IOwait < p.IOwait)
                p = temp;
        }
        return p;
    }

    public void checkIOqueue(){
        if(ReadyList.isEmpty() && (!IOqueue.isEmpty())){
            Process p = IOqueue.peek();
            p = findLowerIO(p, this.IOqueue); //we need to find lowest IO because this will be IO that finsihes the soonest
            if(totalTime < p.IOwait){ //this is for when CPU is not running and we are waiting on IO to run again
                cpu_idle += p.IOwait - totalTime;
                totalTime += p.IOwait - totalTime;
            }
            this.ReadyList.add(p);
            IOqueue.remove(p);
        }
    }

    public void goingtoIO(){
        try{
            this.currentP.IOwait = totalTime + this.currentP.runIO(); //IO wait is the time where IO will stop running
            System.out.println("No");
            this.IOqueue.add(this.currentP);
        }catch(IndexOutOfBoundsException e){ //IF THERE ARE NO MORE IO WE GET AN EXCEPTION
            this.finishedQueue.add(this.currentP); //if there are no more IOs then the process is done
            this.currentP.setDepartTime(totalTime - 1);
            System.out.println("Yes");
            if(this.currentP.getID() != 1)
                this.currentP.waitTime--;
        }finally{System.out.println();}
    }

    public String RQtoString(){
        if (this.ReadyList.isEmpty()) return "Empty";
        StringBuilder sb = new StringBuilder();
        for(Process p : this.ReadyList) {
            sb.append("P" + p.getID() + "(" + p.getCPUBurst() + ") ");
        }
        return sb.toString();
    }

    public String IOtoString(){
        if (this.IOqueue.isEmpty()) return "Empty";
        StringBuilder sb = new StringBuilder();
        for(Process p : this.IOqueue) {
            sb.append("P" + p.getID() + "(" + Math.abs(p.IOwait - totalTime) + ") ");
        }
        return sb.toString();
    }



    protected Queue<Process> ReadyList;
    protected Process currentP;
    protected Integer totalTime;
    public double cpu_idle;

    public double AvgWaitTime;
    public double AvgTurnaroundTime;
    public double AvgResponseTime;

    public double CPU_Util;

    public  Queue<Process> finishedQueue;
    public  Queue<Process> IOqueue;
    public Queue<Process> StoreAllP;


}
