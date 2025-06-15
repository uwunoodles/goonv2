package dev.jnic.Keeqam;

import java.util.Arrays;

abstract class E {
   final int v;
   final int[] w = new int[4];
   final r x = new r();
   final short[][] y = new short[12][16];
   final short[] z = new short[12];
   final short[] A = new short[12];
   final short[] B = new short[12];
   final short[] C = new short[12];
   final short[][] D = new short[12][16];
   final short[][] E = new short[4][64];
   final short[][] F = new short[][]{new short[2], new short[2], new short[4], new short[4], new short[8], new short[8], new short[16], new short[16], new short[32], new short[32]};
   final short[] G = new short[16];

   E(int var1) {
      this.v = (1 << var1) - 1;
   }

   void c() {
      this.w[0] = 0;
      this.w[1] = 0;
      this.w[2] = 0;
      this.w[3] = 0;
      this.x.S = 0;

      int var1;
      for(var1 = 0; var1 < this.y.length; ++var1) {
         Arrays.fill(this.y[var1], (short)1024);
      }

      Arrays.fill(this.z, (short)1024);
      Arrays.fill(this.A, (short)1024);
      Arrays.fill(this.B, (short)1024);
      Arrays.fill(this.C, (short)1024);

      for(var1 = 0; var1 < this.D.length; ++var1) {
         Arrays.fill(this.D[var1], (short)1024);
      }

      for(var1 = 0; var1 < this.E.length; ++var1) {
         Arrays.fill(this.E[var1], (short)1024);
      }

      for(var1 = 0; var1 < this.F.length; ++var1) {
         Arrays.fill(this.F[var1], (short)1024);
      }

      Arrays.fill(this.G, (short)1024);
   }
}
