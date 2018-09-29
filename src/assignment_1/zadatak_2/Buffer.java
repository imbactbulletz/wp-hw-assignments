package assignment_1.zadatak_2;

public class Buffer {
    private int[] buffer;
    private int writePos;

    public Buffer(int size){
        buffer = new int[size];
        writePos = 0;
    }

    public synchronized void writeToBuffer(int number){
         if(writePos == buffer.length){
             System.out.println("Cannot write. Buffer is full.");
             return;
         }

         buffer[writePos++] = number;
    }

    public void printBuffer(){
        System.out.println("Printing the buffer data:");
        for(int i=0; i<buffer.length; i++){
            if(buffer[i] != 0)
                System.out.println(buffer[i]);
        }
        System.out.println("Done printing data.");
    }
}
