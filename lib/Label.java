package org.spongepowered.asm.lib;

public class Label {
   static final int DEBUG = 1;
   static final int RESOLVED = 2;
   static final int RESIZED = 4;
   static final int PUSHED = 8;
   static final int TARGET = 16;
   static final int STORE = 32;
   static final int REACHABLE = 64;
   static final int JSR = 128;
   static final int RET = 256;
   static final int SUBROUTINE = 512;
   static final int VISITED = 1024;
   static final int VISITED2 = 2048;
   public Object info;
   int status;
   int line;
   int position;
   private int referenceCount;
   private int[] srcAndRefPositions;
   int inputStackTop;
   int outputStackMax;
   Frame frame;
   Label successor;
   Edge successors;
   Label next;

   public int getOffset() {
      if ((a.status & 2) == 0) {
         throw new IllegalStateException("Label offset position has not been resolved yet");
      } else {
         return a.position;
      }
   }

   void put(MethodWriter a1, ByteVector a, int a, boolean a) {
      if ((a.status & 2) == 0) {
         if (a) {
            a.addReference(-1 - a, a.length);
            a.putInt(-1);
         } else {
            a.addReference(a, a.length);
            a.putShort(-1);
         }
      } else if (a) {
         a.putInt(a.position - a);
      } else {
         a.putShort(a.position - a);
      }

   }

   private void addReference(int a, int a) {
      if (a.srcAndRefPositions == null) {
         a.srcAndRefPositions = new int[6];
      }

      if (a.referenceCount >= a.srcAndRefPositions.length) {
         int[] a = new int[a.srcAndRefPositions.length + 6];
         System.arraycopy(a.srcAndRefPositions, 0, a, 0, a.srcAndRefPositions.length);
         a.srcAndRefPositions = a;
      }

      a.srcAndRefPositions[a.referenceCount++] = a;
      a.srcAndRefPositions[a.referenceCount++] = a;
   }

   boolean resolve(MethodWriter a1, int a, byte[] a) {
      boolean a = false;
      a.status |= 2;
      a.position = a;
      int a = 0;

      while(true) {
         while(a < a.referenceCount) {
            int a = a.srcAndRefPositions[a++];
            int a = a.srcAndRefPositions[a++];
            int a;
            if (a >= 0) {
               a = a - a;
               if (a < -32768 || a > 32767) {
                  int a = a[a - 1] & 255;
                  if (a <= 168) {
                     a[a - 1] = (byte)(a + 49);
                  } else {
                     a[a - 1] = (byte)(a + 20);
                  }

                  a = true;
               }

               a[a++] = (byte)(a >>> 8);
               a[a] = (byte)a;
            } else {
               a = a + a + 1;
               a[a++] = (byte)(a >>> 24);
               a[a++] = (byte)(a >>> 16);
               a[a++] = (byte)(a >>> 8);
               a[a] = (byte)a;
            }
         }

         return a;
      }
   }

   Label getFirst() {
      return a.frame == null ? a : a.frame.owner;
   }

   boolean inSubroutine(long a) {
      if ((a.status & 1024) != 0) {
         return (a.srcAndRefPositions[(int)(a >>> 32)] & (int)a) != 0;
      } else {
         return false;
      }
   }

   boolean inSameSubroutine(Label a) {
      if ((a.status & 1024) != 0 && (a.status & 1024) != 0) {
         for(int a = 0; a < a.srcAndRefPositions.length; ++a) {
            if ((a.srcAndRefPositions[a] & a.srcAndRefPositions[a]) != 0) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   void addToSubroutine(long a, int a) {
      if ((a.status & 1024) == 0) {
         a.status |= 1024;
         a.srcAndRefPositions = new int[a / 32 + 1];
      }

      int[] var10000 = a.srcAndRefPositions;
      var10000[(int)(a >>> 32)] |= (int)a;
   }

   void visitSubroutine(Label a, long a, int a) {
      Label a = a;

      while(true) {
         Label a;
         Edge a;
         while(true) {
            if (a == null) {
               return;
            }

            a = a;
            a = a.next;
            a.next = null;
            if (a != null) {
               if ((a.status & 2048) == 0) {
                  a.status |= 2048;
                  if ((a.status & 256) != 0 && !a.inSameSubroutine(a)) {
                     a = new Edge();
                     a.info = a.inputStackTop;
                     a.successor = a.successors.successor;
                     a.next = a.successors;
                     a.successors = a;
                  }
                  break;
               }
            } else if (!a.inSubroutine(a)) {
               a.addToSubroutine(a, a);
               break;
            }
         }

         for(a = a.successors; a != null; a = a.next) {
            if (((a.status & 128) == 0 || a != a.successors.next) && a.successor.next == null) {
               a.successor.next = a;
               a = a.successor;
            }
         }
      }
   }

   public String toString() {
      return "L" + System.identityHashCode(a);
   }
}
