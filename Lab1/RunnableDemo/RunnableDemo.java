/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab1.RunnableDemo;

/**
 *
 * @author KEHOEJ
 */
class RunnableDemo implements Runnable {
   private Thread t;
   private String threadName;
   
   RunnableDemo( String name) {
      threadName = name;
      System.out.println("Creating " +  threadName );
      /**
       * Prints out creating message
       */
   }
   
   @Override
   public void run() {
      System.out.println("Running " +  threadName );
      /**
       * Prints out running message and thread name
       */
      try {
         for(int i = 4; i > 0; i--) {
            System.out.println("Thread: " + threadName + ", " + i);
            // Let the thread sleep for a while.
            Thread.sleep(50);
         }
      } catch (InterruptedException e) {
         System.out.println("Thread " +  threadName + " interrupted.");
         /**
          * Prints out thread has been interupted
          */
      }
      System.out.println("Thread " +  threadName + " exiting.");
      /**
       * Prints out exiting thread message
       */
   }
   
   public void start () {
      System.out.println("Starting " +  threadName );
      if (t == null) {
         t = new Thread (this, threadName);
         t.start ();
      }
   }
}
