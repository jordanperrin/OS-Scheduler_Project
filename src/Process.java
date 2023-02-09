import java.lang.reflect.Array;
import java.util.*;
public class Process {

    //constructor
    public Process(int id){
        this.CPU = new ArrayList<>();
        this.IO = new ArrayList<>();
        this.id = id;
        this.responded = false;
        this.responseTime = 0;
        this.departTime = 0;
        this.waitTime = 0;
        this.IOwait = 0;
        this.CPUtimestart = 0;
        this.currentQueue = 1;
    }

    public void insertCPU(int...burst){
        Integer track = 0;
        for(int i : burst){
            this.CPU.add(i);
        }
    }

    public void insertIO(int...burst){
        for(int i : burst){
            this.IO.add(i);
        }
    }
    public int getCPUBurst() {
        return CPU.get(0);
    }

    public int getIOBurst(){
        return IO.get(0);
    }

    public int runCPU(){
        int i = this.getCPUBurst();
        this.decrementCPU();
        return i;
    }

    public int runIO(){
        int i = this.getIOBurst();
        this.decrementIO();
        return i;
    }

    public void decrementCPU(){
        this.CPU.remove(0);
    }
    public void decrementIO(){
        this.IO.remove(0);
    }

    public boolean isBurstEmpty(){
        Integer check = this.CPU.size();
        if(check.equals(0))
            return true;
        return false;
    }

    public boolean isIOEmpty(){
        Integer check = this.IO.size();
        if(check.equals(0))
            return true;
        return false;
    }

    public int getID(){
        return this.id;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }

    public void setDepartTime(int departTime) {
        this.departTime = departTime;
    }

    public boolean hasResponded(){
        if(this.responded)
            return true;
        return false;
    }
    public void setResponseFlag(){
        this.responded = true;
    }

    public void printDepartResponse(){
        System.out.println("Process " + this.id + "   Response Time: " +this.responseTime + "   Turnaround Time: " + this.departTime +
                "   Wait Time: " + this.waitTime);
    }

    public void markResponse(int time){ //this will run every loop, but once it has responded the flag will be false and will contine to be false
        this.CPUtimestart = time;
        if(!this.hasResponded()){
        this.setResponseTime(time);
        this.setResponseFlag();
        }
    }

    public void calculateWaitTime(){
        this.waitTime += Math.abs(this.CPUtimestart - this.IOwait); //take the time CPU started excuting subtracted by the time it finished IO
    }

    public void setHeadCPUArrList(Integer CPUleft ){
        this.CPU.set(0,CPUleft); // we use this for MLFQ so if entire CPU burst doesnt run we can change accordingly
    }
    public Integer responseTime;
    public Integer departTime;
    public Integer IOwait ;
    public Integer CPUtimestart;
    private boolean responded;
    private ArrayList<Integer> CPU;
    private ArrayList<Integer> IO;
    private Integer id;
    public Integer waitTime;

    public Integer currentQueue;

}
