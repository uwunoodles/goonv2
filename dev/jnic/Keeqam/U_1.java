package dev.jnic.Keeqam;

public abstract class U {
   public int W = 0;
   public int X = 0;

   public abstract void e();

   public final int a(short[] var1, int var2) {
      this.e();
      short var3 = var1[var2];
      int var4 = (this.W >>> 11) * var3;
      byte var5;
      if ((this.X ^ Integer.MIN_VALUE) < (var4 ^ Integer.MIN_VALUE)) {
         this.W = var4;
         var1[var2] = (short)(var3 + (2048 - var3 >>> 5));
         var5 = 0;
      } else {
         this.W -= var4;
         this.X -= var4;
         var1[var2] = (short)(var3 - (var3 >>> 5));
         var5 = 1;
      }

      return var5;
   }

   public final int a(short[] var1) {
      int var2 = 1;

      while((var2 = var2 << 1 | this.a(var1, var2)) < var1.length) {
      }

      return var2 - var1.length;
   }

   public final int b(short[] var1) {
      int var2 = 1;
      int var3 = 0;
      int var4 = 0;

      do {
         int var5 = this.a(var1, var2);
         var2 = var2 << 1 | var5;
         var4 |= var5 << var3++;
      } while(var2 < var1.length);

      return var4;
   }
}
