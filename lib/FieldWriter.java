package org.spongepowered.asm.lib;

final class FieldWriter extends FieldVisitor {
   private final ClassWriter cw;
   private final int access;
   private final int name;
   private final int desc;
   private int signature;
   private int value;
   private AnnotationWriter anns;
   private AnnotationWriter ianns;
   private AnnotationWriter tanns;
   private AnnotationWriter itanns;
   private Attribute attrs;

   FieldWriter(ClassWriter a, int a, String a, String a, String a, Object a) {
      super(327680);
      if (a.firstField == null) {
         a.firstField = a;
      } else {
         a.lastField.fv = a;
      }

      a.lastField = a;
      a.cw = a;
      a.access = a;
      a.name = a.newUTF8(a);
      a.desc = a.newUTF8(a);
      if (a != null) {
         a.signature = a.newUTF8(a);
      }

      if (a != null) {
         a.value = a.newConstItem(a).index;
      }

   }

   public AnnotationVisitor visitAnnotation(String a, boolean a) {
      ByteVector a = new ByteVector();
      a.putShort(a.cw.newUTF8(a)).putShort(0);
      AnnotationWriter a = new AnnotationWriter(a.cw, true, a, a, 2);
      if (a) {
         a.next = a.anns;
         a.anns = a;
      } else {
         a.next = a.ianns;
         a.ianns = a;
      }

      return a;
   }

   public AnnotationVisitor visitTypeAnnotation(int a, TypePath a, String a, boolean a) {
      ByteVector a = new ByteVector();
      AnnotationWriter.putTarget(a, a, a);
      a.putShort(a.cw.newUTF8(a)).putShort(0);
      AnnotationWriter a = new AnnotationWriter(a.cw, true, a, a, a.length - 2);
      if (a) {
         a.next = a.tanns;
         a.tanns = a;
      } else {
         a.next = a.itanns;
         a.itanns = a;
      }

      return a;
   }

   public void visitAttribute(Attribute a) {
      a.next = a.attrs;
      a.attrs = a;
   }

   public void visitEnd() {
   }

   int getSize() {
      int a = 8;
      if (a.value != 0) {
         a.cw.newUTF8("ConstantValue");
         a += 8;
      }

      if ((a.access & 4096) != 0 && ((a.cw.version & '\uffff') < 49 || (a.access & 262144) != 0)) {
         a.cw.newUTF8("Synthetic");
         a += 6;
      }

      if ((a.access & 131072) != 0) {
         a.cw.newUTF8("Deprecated");
         a += 6;
      }

      if (a.signature != 0) {
         a.cw.newUTF8("Signature");
         a += 8;
      }

      if (a.anns != null) {
         a.cw.newUTF8("RuntimeVisibleAnnotations");
         a += 8 + a.anns.getSize();
      }

      if (a.ianns != null) {
         a.cw.newUTF8("RuntimeInvisibleAnnotations");
         a += 8 + a.ianns.getSize();
      }

      if (a.tanns != null) {
         a.cw.newUTF8("RuntimeVisibleTypeAnnotations");
         a += 8 + a.tanns.getSize();
      }

      if (a.itanns != null) {
         a.cw.newUTF8("RuntimeInvisibleTypeAnnotations");
         a += 8 + a.itanns.getSize();
      }

      if (a.attrs != null) {
         a += a.attrs.getSize(a.cw, (byte[])null, 0, -1, -1);
      }

      return a;
   }

   void put(ByteVector a) {
      int a = true;
      int a = 393216 | (a.access & 262144) / 64;
      a.putShort(a.access & ~a).putShort(a.name).putShort(a.desc);
      int a = 0;
      if (a.value != 0) {
         ++a;
      }

      if ((a.access & 4096) != 0 && ((a.cw.version & '\uffff') < 49 || (a.access & 262144) != 0)) {
         ++a;
      }

      if ((a.access & 131072) != 0) {
         ++a;
      }

      if (a.signature != 0) {
         ++a;
      }

      if (a.anns != null) {
         ++a;
      }

      if (a.ianns != null) {
         ++a;
      }

      if (a.tanns != null) {
         ++a;
      }

      if (a.itanns != null) {
         ++a;
      }

      if (a.attrs != null) {
         a += a.attrs.getCount();
      }

      a.putShort(a);
      if (a.value != 0) {
         a.putShort(a.cw.newUTF8("ConstantValue"));
         a.putInt(2).putShort(a.value);
      }

      if ((a.access & 4096) != 0 && ((a.cw.version & '\uffff') < 49 || (a.access & 262144) != 0)) {
         a.putShort(a.cw.newUTF8("Synthetic")).putInt(0);
      }

      if ((a.access & 131072) != 0) {
         a.putShort(a.cw.newUTF8("Deprecated")).putInt(0);
      }

      if (a.signature != 0) {
         a.putShort(a.cw.newUTF8("Signature"));
         a.putInt(2).putShort(a.signature);
      }

      if (a.anns != null) {
         a.putShort(a.cw.newUTF8("RuntimeVisibleAnnotations"));
         a.anns.put(a);
      }

      if (a.ianns != null) {
         a.putShort(a.cw.newUTF8("RuntimeInvisibleAnnotations"));
         a.ianns.put(a);
      }

      if (a.tanns != null) {
         a.putShort(a.cw.newUTF8("RuntimeVisibleTypeAnnotations"));
         a.tanns.put(a);
      }

      if (a.itanns != null) {
         a.putShort(a.cw.newUTF8("RuntimeInvisibleTypeAnnotations"));
         a.itanns.put(a);
      }

      if (a.attrs != null) {
         a.attrs.put(a.cw, (byte[])null, 0, -1, -1, a);
      }

   }
}
