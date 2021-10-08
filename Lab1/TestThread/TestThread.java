/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab1.TestThread;

/**
 *
 * @author KEHOEJ
 * 
 * Create two threads and run them.
 */
public class TestThread {
    public static void main(String args[]) {
      ThreadDemo T1 = new ThreadDemo( "Thread-1");
      T1.start();
      ThreadDemo T2 = new ThreadDemo( "Thread-2");
      T2.start();

        /**
         * Creates two instances of threads and runs them
         */
   } 
}
