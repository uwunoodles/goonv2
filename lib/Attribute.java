package org.spongepowered.asm.lib;

public class Attribute {
   public final String type;
   byte[] value;
   Attribute next;

   protected Attribute(String a) {
      a.type = a;
   }

   public boolean isUnknown() {
      return true;
   }

   public boolean isCodeAttribute() {
      return false;
   }

   protected Label[] getLabels() {
      return null;
   }

   protected Attribute read(ClassReader a, int a, int a, char[] a4, int a5, Label[] a6) {
      Attribute a = new Attribute(a.type);
      a.value = new byte[a];
      System.arraycopy(a.b, a, a.value, 0, a);
      return a;
   }

   protected ByteVector write(ClassWriter a1, byte[] a2, int a3, int a4, int a5) {
      ByteVector a = new ByteVector();
      a.data = a.value;
      a.length = a.value.length;
      return a;
   }

   final int getCount() {
      int a = 0;

      for(Attribute a = a; a != null; a = a.next) {
         ++a;
      }

      return a;
   }

   final int getSize(ClassWriter a, byte[] a, int a, int a, int a) {
      Attribute a = a;

      int a;
      for(a = 0; a != null; a = a.next) {
         a.newUTF8(a.type);
         a += a.write(a, a, a, a, a).length + 6;
      }

      return a;
   }

   final void put(ClassWriter a, byte[] a, int a, int a, int a, ByteVector a) {
      for(Attribute a = a; a != null; a = a.next) {
         ByteVector a = a.write(a, a, a, a, a);
         a.putShort(a.newUTF8(a.type)).putInt(a.length);
         a.putByteArray(a.data, 0, a.length);
      }

   }
}
