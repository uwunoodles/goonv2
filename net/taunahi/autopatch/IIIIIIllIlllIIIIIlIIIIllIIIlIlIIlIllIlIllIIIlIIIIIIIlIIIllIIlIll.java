package net.taunahi.autopatch;

import java.util.Iterator;

class IIIIIIllIlllIIIIIlIIIIllIIIlIlIIlIllIlIllIIIlIIIIIIIlIIIllIIlIll implements Iterator<Integer> {
   private int lIIllIlllIllIIlIIllIlIlIIIlIIIlIllllIlllIIIlIlIIllIIlllIIlllIlll;
   private int lllllIIlllllIlIllIlIIIlIIIlIlllIlIIlIlIIIlIIIIIlIlIIllIlllIIlIII;
   final IlIlIlIlIIIlIlIIlIIlIIIlllIIlIIIlIlllIIIlIlllIllIIIIllIllIIIllll lIIIIIllIlIlllllllIIIIIIllllIlllIllIllllIlllIlIIIIIIlIllllIllllI;

   IIIIIIllIlllIIIIIlIIIIllIIIlIlIIlIllIlIllIIIlIIIIIIIlIIIllIIlIll(IlIlIlIlIIIlIlIIlIIlIIIlllIIlIIIlIlllIIIlIlllIllIIIIllIllIIIllll a) {
      a.lIIIIIllIlIlllllllIIIIIIllllIlllIllIllllIlllIlIIIIIIlIllllIllllI = a;
      a.lIIllIlllIllIIlIIllIlIlIIIlIIIlIllllIlllIIIlIlIIllIIlllIIlllIlll = 0;
      a.lllllIIlllllIlIllIlIIIlIIIlIlllIlIIlIlIIIlIIIIIlIlIIllIlllIIlIII = a.lIIIIIllIlIlllllllIIIIIIllllIlllIllIllllIlllIlIIIIIIlIllllIllllI.IlIIIllIlIlIllIIIIlIllIllllIIIIlIllllllIlIllIlIIlIlllIIIlIlIllIl.length();
   }

   public boolean hasNext() {
      boolean var10000;
      try {
         if (a.lIIllIlllIllIIlIIllIlIlIIIlIIIlIllllIlllIIIlIlIIllIIlllIIlllIlll < a.lllllIIlllllIlIllIlIIIlIIIlIlllIlIIlIlIIIlIIIIIlIlIIllIlllIIlIII) {
            var10000 = true;
            return var10000;
         }
      } catch (UnsupportedOperationException var1) {
         throw a(var1);
      }

      var10000 = false;
      return var10000;
   }

   public Integer IIllllIllIlIIlIIIIllIIlIllllIIlIlIllIllIIlllIIlIIllIIIlIIIIllIll(Object[] a1) {
      int a = a.lIIIIIllIlIlllllllIIIIIIllllIlllIllIllllIlllIlIIIIIIlIllllIllllI.IlIIIllIlIlIllIIIIlIllIllllIIIIlIllllllIlIllIlIIlIlllIIIlIlIllIl.codePointAt(a.lIIllIlllIllIIlIIllIlIlIIIlIIIlIllllIlllIIIlIlIIllIIlllIIlllIlll);
      a.lIIllIlllIllIIlIIllIlIlIIIlIIIlIllllIlllIIIlIlIIllIIlllIIlllIlll += Character.charCount(a);
      return a;
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }

   private static UnsupportedOperationException a(UnsupportedOperationException var0) {
      return var0;
   }
}
