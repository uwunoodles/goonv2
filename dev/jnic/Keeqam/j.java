package dev.jnic.Keeqam;

import java.io.IOException;

public final class j {
   public final byte[] m;
   public final int n;
   public int o = 0;
   public int p = 0;
   public int q = 0;
   public int r = 0;
   public int s = 0;
   public int t = 0;
   // $FF: synthetic field
   private static boolean u = !j.class.desiredAssertionStatus();

   public j(int var1, byte[] var2) {
      this.n = var1;
      this.m = new byte[this.n];
      if (var2 != null) {
         this.p = Math.min(var2.length, var1);
         this.q = this.p;
         this.o = this.p;
         System.arraycopy(var2, var2.length - this.p, this.m, 0, this.p);
      }

   }

   public final int b(int var1) {
      int var2 = this.p - var1 - 1;
      if (var1 >= this.p) {
         var2 += this.n;
      }

      return this.m[var2] & 255;
   }

   public final void a(int var1, int var2) {
      if (var1 >= 0 && var1 < this.q) {
         int var3 = Math.min(this.r - this.p, var2);
         this.s = var2 - var3;
         this.t = var1;
         byte[] var10002;
         int var4;
         if ((var2 = this.p - var1 - 1) < 0) {
            if (!u && this.q != this.n) {
               throw new AssertionError();
            }

            var2 += this.n;
            var4 = Math.min(this.n - var2, var3);
            if (!u && var4 > var1 + 1) {
               throw new AssertionError();
            }

            var10002 = this.m;
            System.arraycopy(var10002, var2, var10002, this.p, var4);
            this.p += var4;
            var2 = 0;
            if ((var3 -= var4) == 0) {
               return;
            }
         }

         if (!u && var2 >= this.p) {
            throw new AssertionError();
         } else if (!u && var3 <= 0) {
            throw new AssertionError();
         } else {
            do {
               var4 = Math.min(var3, this.p - var2);
               var10002 = this.m;
               System.arraycopy(var10002, var2, var10002, this.p, var4);
               this.p += var4;
            } while((var3 -= var4) > 0);

            if (this.q < this.p) {
               this.q = this.p;
            }

         }
      } else {
         throw new IOException();
      }
   }
}
