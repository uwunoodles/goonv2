package org.spongepowered.asm.util;

public class ReEntranceLock {
   private final int maxDepth;
   private int depth = 0;
   private boolean semaphore = false;

   public ReEntranceLock(int a) {
      a.maxDepth = a;
   }

   public int getMaxDepth() {
      return a.maxDepth;
   }

   public int getDepth() {
      return a.depth;
   }

   public ReEntranceLock push() {
      ++a.depth;
      a.checkAndSet();
      return a;
   }

   public ReEntranceLock pop() {
      if (a.depth == 0) {
         throw new IllegalStateException("ReEntranceLock pop() with zero depth");
      } else {
         --a.depth;
         return a;
      }
   }

   public boolean check() {
      return a.depth > a.maxDepth;
   }

   public boolean checkAndSet() {
      return a.semaphore |= a.check();
   }

   public ReEntranceLock set() {
      a.semaphore = true;
      return a;
   }

   public boolean isSet() {
      return a.semaphore;
   }

   public ReEntranceLock clear() {
      a.semaphore = false;
      return a;
   }
}
