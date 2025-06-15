package dev.jnic.Keeqam;

public final class F extends E {
   private j c;
   final U O;
   private final K P;
   private final O Q = new O(this, (byte)0);
   private final O R = new O(this, (byte)0);

   public F(j var1, U var2, int var3, int var4, int var5) {
      super(var5);
      this.c = var1;
      this.O = var2;
      this.P = new K(this, var3, var4);
      this.c();
   }

   public final void c() {
      super.c();
      this.P.c();
      this.Q.c();
      this.R.c();
   }

   public final void d() {
      j var1;
      if ((var1 = this.c).s > 0) {
         var1.a(var1.t, var1.s);
      }

      while(true) {
         while((var1 = this.c).p < var1.r) {
            int var8 = this.c.p & this.v;
            int var2;
            int var4;
            int var6;
            int var7;
            if (this.O.a(this.y[this.x.S], var8) == 0) {
               K var9;
               K var18 = var9 = this.P;
               int var10001 = var18.T.c.b(0);
               var4 = var9.T.c.p;
               var2 = var10001;
               K var11 = var18;
               int var14 = var2 >> 8 - var11.L;
               var6 = (var4 & var11.M) << var11.L;
               var2 = var14 + var6;
               p var13 = var9.U[var2];
               var2 = 1;
               if (var13.V.T.x.S < 7) {
                  while((var2 = var2 << 1 | var13.V.T.O.a(var13.N, var2)) < 256) {
                  }
               } else {
                  var4 = var13.V.T.c.b(var13.V.T.w[0]);
                  var14 = 256;

                  do {
                     var6 = (var4 <<= 1) & var14;
                     var7 = var13.V.T.O.a(var13.N, var14 + var6 + var2);
                     var2 = var2 << 1 | var7;
                     var14 &= 0 - var7 ^ ~var6;
                  } while(var2 < 256);
               }

               j var19 = var13.V.T.c;
               byte var10 = (byte)var2;
               j var12 = var19;
               var19.m[var12.p++] = var10;
               if (var12.q < var12.p) {
                  var12.q = var12.p;
               }

               r var15;
               if ((var15 = var13.V.T.x).S <= 3) {
                  var15.S = 0;
               } else if (var15.S <= 9) {
                  var15.S -= 3;
               } else {
                  var15.S -= 6;
               }
            } else {
               int var3;
               r var10000;
               int var17;
               if (this.O.a(this.z, this.x.S) == 0) {
                  var10000 = this.x;
                  var10000.S = var10000.S < 7 ? 7 : 10;
                  this.w[3] = this.w[2];
                  this.w[2] = this.w[1];
                  this.w[1] = this.w[0];
                  var3 = this.Q.c(var8);
                  if ((var2 = this.O.a(this.E[var3 < 6 ? var3 - 2 : 3])) < 4) {
                     this.w[0] = var2;
                  } else {
                     var4 = (var2 >> 1) - 1;
                     this.w[0] = (2 | var2 & 1) << var4;
                     int[] var16;
                     if (var2 < 14) {
                        var16 = this.w;
                        var16[0] |= this.O.b(this.F[var2 - 4]);
                     } else {
                        var16 = this.w;
                        int var10002 = var16[0];
                        var6 = var4 - 4;
                        U var5 = this.O;
                        var7 = 0;

                        do {
                           var5.e();
                           var5.W >>>= 1;
                           var2 = var5.X - var5.W >>> 31;
                           var5.X -= var5.W & var2 - 1;
                           var7 = var7 << 1 | 1 - var2;
                           --var6;
                        } while(var6 != 0);

                        var16[0] = var10002 | var7 << 4;
                        var16 = this.w;
                        var16[0] |= this.O.b(this.G);
                     }
                  }

                  var17 = var3;
               } else {
                  label120: {
                     if (this.O.a(this.A, this.x.S) == 0) {
                        if (this.O.a(this.D[this.x.S], var8) == 0) {
                           var10000 = this.x;
                           var10000.S = var10000.S < 7 ? 9 : 11;
                           var17 = 1;
                           break label120;
                        }
                     } else {
                        if (this.O.a(this.B, this.x.S) == 0) {
                           var3 = this.w[1];
                        } else {
                           if (this.O.a(this.C, this.x.S) == 0) {
                              var3 = this.w[2];
                           } else {
                              var3 = this.w[3];
                              this.w[3] = this.w[2];
                           }

                           this.w[2] = this.w[1];
                        }

                        this.w[1] = this.w[0];
                        this.w[0] = var3;
                     }

                     var10000 = this.x;
                     var10000.S = var10000.S < 7 ? 8 : 11;
                     var17 = this.R.c(var8);
                  }
               }

               var8 = var17;
               this.c.a(this.w[0], var8);
            }
         }

         this.O.e();
         return;
      }
   }
}
