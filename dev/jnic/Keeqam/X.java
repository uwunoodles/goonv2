package dev.jnic.Keeqam;

abstract class X {
   final int L;
   final int M;

   X(int var1, int var2) {
      this.L = var1;
      this.M = (1 << var2) - 1;
   }
}
