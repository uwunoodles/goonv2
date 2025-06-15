package org.spongepowered.asm.mixin.transformer.debug;

import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RuntimeDecompilerAsync extends RuntimeDecompiler implements Runnable, UncaughtExceptionHandler {
   private final BlockingQueue<File> queue = new LinkedBlockingQueue();
   private final Thread thread;
   private boolean run = true;

   public RuntimeDecompilerAsync(File a) {
      super(a);
      a.thread = new Thread(a, "Decompiler thread");
      a.thread.setDaemon(true);
      a.thread.setPriority(1);
      a.thread.setUncaughtExceptionHandler(a);
      a.thread.start();
   }

   public void decompile(File a) {
      if (a.run) {
         a.queue.offer(a);
      } else {
         super.decompile(a);
      }

   }

   public void run() {
      while(a.run) {
         try {
            File a = (File)a.queue.take();
            super.decompile(a);
         } catch (InterruptedException var2) {
            a.run = false;
         } catch (Exception var3) {
            var3.printStackTrace();
         }
      }

   }

   public void uncaughtException(Thread a1, Throwable a) {
      a.logger.error("Async decompiler encountered an error and will terminate. Further decompile requests will be handled synchronously. {} {}", new Object[]{a.getClass().getName(), a.getMessage()});
      a.flush();
   }

   private void flush() {
      a.run = false;

      File a;
      while((a = (File)a.queue.poll()) != null) {
         a.decompile(a);
      }

   }
}
