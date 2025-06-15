package dev.jnic.Keeqam;

import java.io.IOException;

public final class u extends U {
   public final byte[] m = new byte['\ufffb'];
   public int p;

   public u() {
      this.p = this.m.length;
   }

   public final void e() {
      if ((this.W & -16777216) == 0) {
         try {
            this.X = this.X << 8 | this.m[this.p++] & 255;
            this.W <<= 8;
         } catch (ArrayIndexOutOfBoundsException var1) {
            throw new IOException();
         }
      }
   }
}
