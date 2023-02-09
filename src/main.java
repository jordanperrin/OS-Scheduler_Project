public class main {

    public static void main(String[] args) {
        Process p1 = new Process(1);
        Process p2 = new Process(2);
        Process p3 = new Process(3);
        Process p4 = new Process(4);
        Process p5 = new Process(5);
        Process p6 = new Process(6);
        Process p7 = new Process(7);
        Process p8 = new Process(8);

        p1.insertCPU(5,3,5,4,6,4,3,5);
        p1.insertIO(27,31,43,18,22,26,24);

        p2.insertCPU(4,5,7,12,9,4,9,7,8);
        p2.insertIO(48,44,42,37,76,41,31,43);

        p3.insertCPU(8,12,18,14,4,15,14,5,6);
        p3.insertIO(33,41,65,21,61,18,26,31);

        p4.insertCPU(3,4,5,3,4,5,6,5,3);
        p4.insertIO(35,41,45,51,61,54,82,77);

        p5.insertCPU(16,17,5,16,7,13,11,6,3,4);
        p5.insertIO(24,21,36,26,31,28,21,13,11);

        p6.insertCPU(11,4,5,6,7,9,12,15,8);
        p6.insertIO(22,8,10,12,14,18,24,30);

        p7.insertCPU(14,17,11,15,4,7,16,10);
        p7.insertIO(46,41,42,21,32,19,33);

        p8.insertCPU(4,5,6,14,16,6);
        p8.insertIO(14,33,51,73,87);

       FCFS fcfs = new FCFS();
       SJF sjf = new SJF();
       MLFQ mlfq = new MLFQ();

        fcfs.addProcesses(p1,p2,p3,p4,p5,p6,p7,p8);

        fcfs.start();
        fcfs.printAnalytics();

        p1.insertCPU(5,3,5,4,6,4,3,5);
        p1.insertIO(27,31,43,18,22,26,24);

        p2.insertCPU(4,5,7,12,9,4,9,7,8);
        p2.insertIO(48,44,42,37,76,41,31,43);

        p3.insertCPU(8,12,18,14,4,15,14,5,6);
        p3.insertIO(33,41,65,21,61,18,26,31);

        p4.insertCPU(3,4,5,3,4,5,6,5,3);
        p4.insertIO(35,41,45,51,61,54,82,77);

        p5.insertCPU(16,17,5,16,7,13,11,6,3,4);
        p5.insertIO(24,21,36,26,31,28,21,13,11);

        p6.insertCPU(11,4,5,6,7,9,12,15,8);
        p6.insertIO(22,8,10,12,14,18,24,30);

        p7.insertCPU(14,17,11,15,4,7,16,10);
        p7.insertIO(46,41,42,21,32,19,33);

        p8.insertCPU(4,5,6,14,16,6);
        p8.insertIO(14,33,51,73,87);
        sjf.addProcesses(p1,p2,p3,p4,p5,p6,p7,p8);
        sjf.start();
        sjf.printAnalytics();

        p1.insertCPU(5,3,5,4,6,4,3,5);
        p1.insertIO(27,31,43,18,22,26,24);

        p2.insertCPU(4,5,7,12,9,4,9,7,8);
        p2.insertIO(48,44,42,37,76,41,31,43);

        p3.insertCPU(8,12,18,14,4,15,14,5,6);
        p3.insertIO(33,41,65,21,61,18,26,31);

        p4.insertCPU(3,4,5,3,4,5,6,5,3);
        p4.insertIO(35,41,45,51,61,54,82,77);

        p5.insertCPU(16,17,5,16,7,13,11,6,3,4);
        p5.insertIO(24,21,36,26,31,28,21,13,11);
a
        p6.insertCPU(11,4,5,6,7,9,12,15,8);
        p6.insertIO(22,8,10,12,14,18,24,30);

        p7.insertCPU(14,17,11,15,4,7,16,10);
        p7.insertIO(46,41,42,21,32,19,33);

        p8.insertCPU(4,5,6,14,16,6);
        p8.insertIO(14,33,51,73,87);

        mlfq.addProcesses(p1,p2,p3,p4,p5,p6,p7,p8);

        mlfq.start();
        mlfq.printAnalytics();

    }
}
