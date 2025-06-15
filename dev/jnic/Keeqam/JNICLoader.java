package dev.jnic.Keeqam;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Objects;

public class JNICLoader extends InputStream {
   public static ByteBuffer z;
   private String V;
   private DataInputStream b;
   private j c;
   private u d;
   private F e;
   private int f;
   private boolean g;
   private boolean h;
   private boolean i;
   private boolean j;
   private IOException k;
   private final byte[] l;

   public JNICLoader(InputStream var1) {
      this(var1, 67108864, (byte[])null);
   }

   public JNICLoader(InputStream var1, int var2, byte[] var3) {
      this(var1, var2, var3, (byte)0);
   }

   private JNICLoader(InputStream var1, int var2, byte[] var3, byte var4) {
      this.f = 0;
      this.g = false;
      this.h = true;
      this.i = true;
      this.j = false;
      this.k = null;
      this.l = new byte[1];
      if (var1 == null) {
         throw new NullPointerException();
      } else {
         this.b = new DataInputStream(var1);
         this.d = new u();
         j var10001 = new j;
         if (var2 >= 4096 && var2 <= 2147483632) {
            var10001.<init>(var2 + 15 & -16, var3);
            this.c = var10001;
            if (var3 != null && var3.length > 0) {
               this.h = false;
            }

         } else {
            throw new IllegalArgumentException();
         }
      }
   }

   public int read() {
      return this.read(this.l, 0, 1) == -1 ? -1 : this.l[0] & 255;
   }

   public int read(byte[] var1, int var2, int var3) {
      if (var2 >= 0 && var3 >= 0 && var2 + var3 >= 0 && var2 + var3 <= var1.length) {
         if (var3 == 0) {
            return 0;
         } else if (this.b == null) {
            throw new IOException();
         } else if (this.k != null) {
            throw this.k;
         } else if (this.j) {
            return -1;
         } else {
            try {
               int var4 = 0;

               u var13;
               do {
                  do {
                     if (var3 <= 0) {
                        return var4;
                     }

                     int var6;
                     int var7;
                     DataInputStream var14;
                     if (this.f == 0) {
                        if ((var6 = this.b.readUnsignedByte()) == 0) {
                           this.j = true;
                           this.b();
                        } else {
                           if (var6 < 224 && var6 != 1) {
                              if (this.h) {
                                 throw new IOException();
                              }
                           } else {
                              this.i = true;
                              this.h = false;
                              j var8;
                              (var8 = this.c).o = 0;
                              var8.p = 0;
                              var8.q = 0;
                              var8.r = 0;
                              var8.m[var8.n - 1] = 0;
                           }

                           if (var6 >= 128) {
                              this.g = true;
                              this.f = (var6 & 31) << 16;
                              this.f += this.b.readUnsignedShort() + 1;
                              var7 = this.b.readUnsignedShort() + 1;
                              int var9;
                              if (var6 >= 192) {
                                 this.i = false;
                                 if ((var6 = this.b.readUnsignedByte()) > 224) {
                                    throw new IOException();
                                 }

                                 var9 = var6 / 45;
                                 int var10 = (var6 -= var9 * 9 * 5) / 9;
                                 if ((var6 -= var10 * 9) + var10 > 4) {
                                    throw new IOException();
                                 }

                                 this.e = new F(this.c, this.d, var6, var10, var9);
                              } else {
                                 if (this.i) {
                                    throw new IOException();
                                 }

                                 if (var6 >= 160) {
                                    this.e.c();
                                 }
                              }

                              var14 = this.b;
                              u var15 = this.d;
                              if (var7 < 5) {
                                 throw new IOException();
                              }

                              if (var14.readUnsignedByte() != 0) {
                                 throw new IOException();
                              }

                              var15.X = var14.readInt();
                              var15.W = -1;
                              var9 = var7 - 5;
                              var15.p = var15.m.length - var9;
                              var14.readFully(var15.m, var15.p, var9);
                           } else {
                              if (var6 > 2) {
                                 throw new IOException();
                              }

                              this.g = false;
                              this.f = this.b.readUnsignedShort() + 1;
                           }
                        }

                        if (this.j) {
                           if (var4 == 0) {
                              return -1;
                           }

                           return var4;
                        }
                     }

                     int var5 = Math.min(this.f, var3);
                     j var12;
                     j var10000;
                     int var16;
                     if (!this.g) {
                        var10000 = this.c;
                        var7 = var5;
                        var14 = this.b;
                        var12 = var10000;
                        var16 = Math.min(var10000.n - var12.p, var7);
                        var14.readFully(var12.m, var12.p, var16);
                        var12.p += var16;
                        if (var12.q < var12.p) {
                           var12.q = var12.p;
                        }
                     } else {
                        var10000 = this.c;
                        var6 = var5;
                        var12 = var10000;
                        if (var10000.n - var12.p <= var6) {
                           var12.r = var12.n;
                        } else {
                           var12.r = var12.p + var6;
                        }

                        this.e.d();
                     }

                     var16 = (var12 = this.c).p - var12.o;
                     if (var12.p == var12.n) {
                        var12.p = 0;
                     }

                     System.arraycopy(var12.m, var12.o, var1, var2, var16);
                     var12.o = var12.p;
                     var2 += var16;
                     var3 -= var16;
                     var4 += var16;
                     this.f -= var16;
                  } while(this.f != 0);
               } while((var13 = this.d).p == var13.m.length && var13.X == 0 && this.c.s <= 0);

               throw new IOException();
            } catch (IOException var11) {
               this.k = var11;
               throw var11;
            }
         }
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public int available() {
      if (this.b == null) {
         throw new IOException("closed");
      } else if (this.k != null) {
         throw this.k;
      } else {
         return this.g ? this.f : Math.min(this.f, this.b.available());
      }
   }

   private void b() {
      if (this.c != null) {
         this.c = null;
         this.d = null;
      }

   }

   public void close() {
      if (this.b != null) {
         this.b();

         try {
            this.b.close();
         } finally {
            this.b = null;
         }

      }
   }

   public static void init() {
   }

   static {
      z = ByteBuffer.allocateDirect(21945).order(ByteOrder.LITTLE_ENDIAN);
      String var0 = System.getProperty("os.name").toLowerCase();
      String var1 = System.getProperty("os.arch").toLowerCase();
      long var2 = 0L;
      long var4 = 0L;
      if (var0.contains("win") && (var1.equals("x86_64") || var1.equals("amd64"))) {
         var2 = 0L;
         var4 = 233472L;
         z.putInt(-2090054399);
         z.putInt(252784672);
         z.putInt(-902411130);
         z.putInt(-240459329);
         z.putInt(979217585);
         z.putInt(-2042942794);
         z.putInt(1597771894);
         z.putInt(354250008);
      }

      if (var0.contains("lin") && var1.equals("aarch64")) {
         var2 = 1429518L;
         var4 = 1764190L;
         z.putInt(-949954848);
         z.putInt(-786284058);
         z.putInt(-2106071000);
         z.putInt(-966466165);
         z.putInt(-1495410287);
         z.putInt(1548006760);
         z.putInt(1233274564);
         z.putInt(-1482162499);
      }

      if (var0.contains("win") && var1.equals("aarch64")) {
         var2 = 233472L;
         var4 = 488960L;
         z.putInt(1413110203);
         z.putInt(-521272800);
         z.putInt(-1581980504);
         z.putInt(-1930053281);
         z.putInt(-2087472199);
         z.putInt(-1636428074);
         z.putInt(1671028152);
         z.putInt(114552093);
      }

      if (var0.contains("mac") && (var1.equals("x86_64") || var1.equals("amd64"))) {
         var2 = 488960L;
         var4 = 825270L;
         z.putInt(-599145452);
         z.putInt(-485444618);
         z.putInt(77813469);
         z.putInt(397754302);
         z.putInt(1457526645);
         z.putInt(219654951);
         z.putInt(-1074060422);
         z.putInt(853014691);
      }

      if (var0.contains("lin") && (var1.equals("x86_64") || var1.equals("amd64"))) {
         var2 = 1107678L;
         var4 = 1429518L;
         z.putInt(-1586677700);
         z.putInt(-1743379023);
         z.putInt(-1969708086);
         z.putInt(1450267314);
         z.putInt(-2041990097);
         z.putInt(315297757);
         z.putInt(-1874563177);
         z.putInt(1367606881);
      }

      if (var0.contains("mac") && var1.equals("aarch64")) {
         var2 = 825270L;
         var4 = 1107678L;
         z.putInt(-16747517);
         z.putInt(-38258493);
         z.putInt(-1952779962);
         z.putInt(44601386);
         z.putInt(36027496);
         z.putInt(553384835);
         z.putInt(279978299);
         z.putInt(371871481);
      }

      if (var2 == var4) {
         throw new UnsatisfiedLinkError("Platform not supported: " + var0 + "/" + var1);
      } else {
         File var6;
         try {
            var6 = File.createTempFile("lib", (String)null);
            var6.deleteOnExit();
            if (!var6.exists()) {
               throw new IOException();
            }
         } catch (IOException var19) {
            throw new UnsatisfiedLinkError("Failed to create temp file");
         }

         byte[] var7 = new byte[2048];

         try {
            JNICLoader var8 = new JNICLoader((InputStream)Objects.requireNonNull(JNICLoader.class.getResourceAsStream("/dev/jnic/lib/b3c38411-ae5e-4cc5-8bed-0e282db1c2bd.dat")));

            try {
               FileOutputStream var9 = new FileOutputStream(var6);

               try {
                  long var10;
                  long var12;
                  for(var10 = 0L; var10 < var2; var10 += var12) {
                     var12 = var8.skip(var2 - var10);
                     if (var12 <= 0L) {
                        throw new IOException("failed to skip: " + var10);
                     }
                  }

                  while(var10 < var4) {
                     int var20 = var8.read(var7, 0, (int)Math.min((long)var7.length, var4 - var10));
                     var9.write(var7, 0, var20);
                     var10 += (long)var20;
                  }
               } catch (Throwable var16) {
                  try {
                     var9.close();
                  } catch (Throwable var15) {
                     var16.addSuppressed(var15);
                  }

                  throw var16;
               }

               var9.close();
            } catch (Throwable var17) {
               try {
                  var8.close();
               } catch (Throwable var14) {
                  var17.addSuppressed(var14);
               }

               throw var17;
            }

            var8.close();
         } catch (IOException var18) {
            throw new UnsatisfiedLinkError("Failed to extract file: " + var18.getMessage());
         }

         z.putInt(540793316);
         z.putInt(-553519602);
         z.putInt(-347692928);
         z.putInt(-996697842);
         z.putInt(-1511351623);
         System.load(var6.getAbsolutePath());
      }
   }
}
