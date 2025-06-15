package org.spongepowered.asm.lib;

public class ClassWriter extends ClassVisitor {
   public static final int COMPUTE_MAXS = 1;
   public static final int COMPUTE_FRAMES = 2;
   static final int ACC_SYNTHETIC_ATTRIBUTE = 262144;
   static final int TO_ACC_SYNTHETIC = 64;
   static final int NOARG_INSN = 0;
   static final int SBYTE_INSN = 1;
   static final int SHORT_INSN = 2;
   static final int VAR_INSN = 3;
   static final int IMPLVAR_INSN = 4;
   static final int TYPE_INSN = 5;
   static final int FIELDORMETH_INSN = 6;
   static final int ITFMETH_INSN = 7;
   static final int INDYMETH_INSN = 8;
   static final int LABEL_INSN = 9;
   static final int LABELW_INSN = 10;
   static final int LDC_INSN = 11;
   static final int LDCW_INSN = 12;
   static final int IINC_INSN = 13;
   static final int TABL_INSN = 14;
   static final int LOOK_INSN = 15;
   static final int MANA_INSN = 16;
   static final int WIDE_INSN = 17;
   static final int ASM_LABEL_INSN = 18;
   static final int F_INSERT = 256;
   static final byte[] TYPE;
   static final int CLASS = 7;
   static final int FIELD = 9;
   static final int METH = 10;
   static final int IMETH = 11;
   static final int STR = 8;
   static final int INT = 3;
   static final int FLOAT = 4;
   static final int LONG = 5;
   static final int DOUBLE = 6;
   static final int NAME_TYPE = 12;
   static final int UTF8 = 1;
   static final int MTYPE = 16;
   static final int HANDLE = 15;
   static final int INDY = 18;
   static final int HANDLE_BASE = 20;
   static final int TYPE_NORMAL = 30;
   static final int TYPE_UNINIT = 31;
   static final int TYPE_MERGED = 32;
   static final int BSM = 33;
   ClassReader cr;
   int version;
   int index;
   final ByteVector pool;
   Item[] items;
   int threshold;
   final Item key;
   final Item key2;
   final Item key3;
   final Item key4;
   Item[] typeTable;
   private short typeCount;
   private int access;
   private int name;
   String thisName;
   private int signature;
   private int superName;
   private int interfaceCount;
   private int[] interfaces;
   private int sourceFile;
   private ByteVector sourceDebug;
   private int enclosingMethodOwner;
   private int enclosingMethod;
   private AnnotationWriter anns;
   private AnnotationWriter ianns;
   private AnnotationWriter tanns;
   private AnnotationWriter itanns;
   private Attribute attrs;
   private int innerClassesCount;
   private ByteVector innerClasses;
   int bootstrapMethodsCount;
   ByteVector bootstrapMethods;
   FieldWriter firstField;
   FieldWriter lastField;
   MethodWriter firstMethod;
   MethodWriter lastMethod;
   private int compute;
   boolean hasAsmInsns;

   public ClassWriter(int a) {
      super(327680);
      a.index = 1;
      a.pool = new ByteVector();
      a.items = new Item[256];
      a.threshold = (int)(0.75D * (double)a.items.length);
      a.key = new Item();
      a.key2 = new Item();
      a.key3 = new Item();
      a.key4 = new Item();
      a.compute = (a & 2) != 0 ? 0 : ((a & 1) != 0 ? 2 : 3);
   }

   public ClassWriter(ClassReader a, int a) {
      this(a);
      a.copyPool(a);
      a.cr = a;
   }

   public final void visit(int a, int a, String a, String a, String a, String[] a) {
      a.version = a;
      a.access = a;
      a.name = a.newClass(a);
      a.thisName = a;
      if (a != null) {
         a.signature = a.newUTF8(a);
      }

      a.superName = a == null ? 0 : a.newClass(a);
      if (a != null && a.length > 0) {
         a.interfaceCount = a.length;
         a.interfaces = new int[a.interfaceCount];

         for(int a = 0; a < a.interfaceCount; ++a) {
            a.interfaces[a] = a.newClass(a[a]);
         }
      }

   }

   public final void visitSource(String a, String a) {
      if (a != null) {
         a.sourceFile = a.newUTF8(a);
      }

      if (a != null) {
         a.sourceDebug = (new ByteVector()).encodeUTF8(a, 0, Integer.MAX_VALUE);
      }

   }

   public final void visitOuterClass(String a, String a, String a) {
      a.enclosingMethodOwner = a.newClass(a);
      if (a != null && a != null) {
         a.enclosingMethod = a.newNameType(a, a);
      }

   }

   public final AnnotationVisitor visitAnnotation(String a, boolean a) {
      ByteVector a = new ByteVector();
      a.putShort(a.newUTF8(a)).putShort(0);
      AnnotationWriter a = new AnnotationWriter(a, true, a, a, 2);
      if (a) {
         a.next = a.anns;
         a.anns = a;
      } else {
         a.next = a.ianns;
         a.ianns = a;
      }

      return a;
   }

   public final AnnotationVisitor visitTypeAnnotation(int a, TypePath a, String a, boolean a) {
      ByteVector a = new ByteVector();
      AnnotationWriter.putTarget(a, a, a);
      a.putShort(a.newUTF8(a)).putShort(0);
      AnnotationWriter a = new AnnotationWriter(a, true, a, a, a.length - 2);
      if (a) {
         a.next = a.tanns;
         a.tanns = a;
      } else {
         a.next = a.itanns;
         a.itanns = a;
      }

      return a;
   }

   public final void visitAttribute(Attribute a) {
      a.next = a.attrs;
      a.attrs = a;
   }

   public final void visitInnerClass(String a, String a, String a, int a) {
      if (a.innerClasses == null) {
         a.innerClasses = new ByteVector();
      }

      Item a = a.newClassItem(a);
      if (a.intVal == 0) {
         ++a.innerClassesCount;
         a.innerClasses.putShort(a.index);
         a.innerClasses.putShort(a == null ? 0 : a.newClass(a));
         a.innerClasses.putShort(a == null ? 0 : a.newUTF8(a));
         a.innerClasses.putShort(a);
         a.intVal = a.innerClassesCount;
      }

   }

   public final FieldVisitor visitField(int a, String a, String a, String a, Object a) {
      return new FieldWriter(a, a, a, a, a, a);
   }

   public final MethodVisitor visitMethod(int a, String a, String a, String a, String[] a) {
      return new MethodWriter(a, a, a, a, a, a, a.compute);
   }

   public final void visitEnd() {
   }

   public byte[] toByteArray() {
      if (a.index > 65535) {
         throw new RuntimeException("Class file too large!");
      } else {
         int a = 24 + 2 * a.interfaceCount;
         int a = 0;

         FieldWriter a;
         for(a = a.firstField; a != null; a = (FieldWriter)a.fv) {
            ++a;
            a += a.getSize();
         }

         int a = 0;

         MethodWriter a;
         for(a = a.firstMethod; a != null; a = (MethodWriter)a.mv) {
            ++a;
            a += a.getSize();
         }

         int a = 0;
         if (a.bootstrapMethods != null) {
            ++a;
            a += 8 + a.bootstrapMethods.length;
            a.newUTF8("BootstrapMethods");
         }

         if (a.signature != 0) {
            ++a;
            a += 8;
            a.newUTF8("Signature");
         }

         if (a.sourceFile != 0) {
            ++a;
            a += 8;
            a.newUTF8("SourceFile");
         }

         if (a.sourceDebug != null) {
            ++a;
            a += a.sourceDebug.length + 6;
            a.newUTF8("SourceDebugExtension");
         }

         if (a.enclosingMethodOwner != 0) {
            ++a;
            a += 10;
            a.newUTF8("EnclosingMethod");
         }

         if ((a.access & 131072) != 0) {
            ++a;
            a += 6;
            a.newUTF8("Deprecated");
         }

         if ((a.access & 4096) != 0 && ((a.version & '\uffff') < 49 || (a.access & 262144) != 0)) {
            ++a;
            a += 6;
            a.newUTF8("Synthetic");
         }

         if (a.innerClasses != null) {
            ++a;
            a += 8 + a.innerClasses.length;
            a.newUTF8("InnerClasses");
         }

         if (a.anns != null) {
            ++a;
            a += 8 + a.anns.getSize();
            a.newUTF8("RuntimeVisibleAnnotations");
         }

         if (a.ianns != null) {
            ++a;
            a += 8 + a.ianns.getSize();
            a.newUTF8("RuntimeInvisibleAnnotations");
         }

         if (a.tanns != null) {
            ++a;
            a += 8 + a.tanns.getSize();
            a.newUTF8("RuntimeVisibleTypeAnnotations");
         }

         if (a.itanns != null) {
            ++a;
            a += 8 + a.itanns.getSize();
            a.newUTF8("RuntimeInvisibleTypeAnnotations");
         }

         if (a.attrs != null) {
            a += a.attrs.getCount();
            a += a.attrs.getSize(a, (byte[])null, 0, -1, -1);
         }

         a += a.pool.length;
         ByteVector a = new ByteVector(a);
         a.putInt(-889275714).putInt(a.version);
         a.putShort(a.index).putByteArray(a.pool.data, 0, a.pool.length);
         int a = 393216 | (a.access & 262144) / 64;
         a.putShort(a.access & ~a).putShort(a.name).putShort(a.superName);
         a.putShort(a.interfaceCount);

         int a;
         for(a = 0; a < a.interfaceCount; ++a) {
            a.putShort(a.interfaces[a]);
         }

         a.putShort(a);

         for(a = a.firstField; a != null; a = (FieldWriter)a.fv) {
            a.put(a);
         }

         a.putShort(a);

         for(a = a.firstMethod; a != null; a = (MethodWriter)a.mv) {
            a.put(a);
         }

         a.putShort(a);
         if (a.bootstrapMethods != null) {
            a.putShort(a.newUTF8("BootstrapMethods"));
            a.putInt(a.bootstrapMethods.length + 2).putShort(a.bootstrapMethodsCount);
            a.putByteArray(a.bootstrapMethods.data, 0, a.bootstrapMethods.length);
         }

         if (a.signature != 0) {
            a.putShort(a.newUTF8("Signature")).putInt(2).putShort(a.signature);
         }

         if (a.sourceFile != 0) {
            a.putShort(a.newUTF8("SourceFile")).putInt(2).putShort(a.sourceFile);
         }

         if (a.sourceDebug != null) {
            a = a.sourceDebug.length;
            a.putShort(a.newUTF8("SourceDebugExtension")).putInt(a);
            a.putByteArray(a.sourceDebug.data, 0, a);
         }

         if (a.enclosingMethodOwner != 0) {
            a.putShort(a.newUTF8("EnclosingMethod")).putInt(4);
            a.putShort(a.enclosingMethodOwner).putShort(a.enclosingMethod);
         }

         if ((a.access & 131072) != 0) {
            a.putShort(a.newUTF8("Deprecated")).putInt(0);
         }

         if ((a.access & 4096) != 0 && ((a.version & '\uffff') < 49 || (a.access & 262144) != 0)) {
            a.putShort(a.newUTF8("Synthetic")).putInt(0);
         }

         if (a.innerClasses != null) {
            a.putShort(a.newUTF8("InnerClasses"));
            a.putInt(a.innerClasses.length + 2).putShort(a.innerClassesCount);
            a.putByteArray(a.innerClasses.data, 0, a.innerClasses.length);
         }

         if (a.anns != null) {
            a.putShort(a.newUTF8("RuntimeVisibleAnnotations"));
            a.anns.put(a);
         }

         if (a.ianns != null) {
            a.putShort(a.newUTF8("RuntimeInvisibleAnnotations"));
            a.ianns.put(a);
         }

         if (a.tanns != null) {
            a.putShort(a.newUTF8("RuntimeVisibleTypeAnnotations"));
            a.tanns.put(a);
         }

         if (a.itanns != null) {
            a.putShort(a.newUTF8("RuntimeInvisibleTypeAnnotations"));
            a.itanns.put(a);
         }

         if (a.attrs != null) {
            a.attrs.put(a, (byte[])null, 0, -1, -1, a);
         }

         if (a.hasAsmInsns) {
            a.anns = null;
            a.ianns = null;
            a.attrs = null;
            a.innerClassesCount = 0;
            a.innerClasses = null;
            a.firstField = null;
            a.lastField = null;
            a.firstMethod = null;
            a.lastMethod = null;
            a.compute = 1;
            a.hasAsmInsns = false;
            (new ClassReader(a.data)).accept(a, 264);
            return a.toByteArray();
         } else {
            return a.data;
         }
      }
   }

   Item newConstItem(Object a) {
      int a;
      if (a instanceof Integer) {
         a = (Integer)a;
         return a.newInteger(a);
      } else if (a instanceof Byte) {
         a = ((Byte)a).intValue();
         return a.newInteger(a);
      } else if (a instanceof Character) {
         int a = (Character)a;
         return a.newInteger(a);
      } else if (a instanceof Short) {
         a = ((Short)a).intValue();
         return a.newInteger(a);
      } else if (a instanceof Boolean) {
         a = (Boolean)a ? 1 : 0;
         return a.newInteger(a);
      } else if (a instanceof Float) {
         float a = (Float)a;
         return a.newFloat(a);
      } else if (a instanceof Long) {
         long a = (Long)a;
         return a.newLong(a);
      } else if (a instanceof Double) {
         double a = (Double)a;
         return a.newDouble(a);
      } else if (a instanceof String) {
         return a.newString((String)a);
      } else if (a instanceof Type) {
         Type a = (Type)a;
         int a = a.getSort();
         if (a == 10) {
            return a.newClassItem(a.getInternalName());
         } else {
            return a == 11 ? a.newMethodTypeItem(a.getDescriptor()) : a.newClassItem(a.getDescriptor());
         }
      } else if (a instanceof Handle) {
         Handle a = (Handle)a;
         return a.newHandleItem(a.tag, a.owner, a.name, a.desc, a.itf);
      } else {
         throw new IllegalArgumentException("value " + a);
      }
   }

   public int newConst(Object a) {
      return a.newConstItem(a).index;
   }

   public int newUTF8(String a) {
      a.key.set(1, a, (String)null, (String)null);
      Item a = a.get(a.key);
      if (a == null) {
         a.pool.putByte(1).putUTF8(a);
         a = new Item(a.index++, a.key);
         a.put(a);
      }

      return a.index;
   }

   Item newClassItem(String a) {
      a.key2.set(7, a, (String)null, (String)null);
      Item a = a.get(a.key2);
      if (a == null) {
         a.pool.put12(7, a.newUTF8(a));
         a = new Item(a.index++, a.key2);
         a.put(a);
      }

      return a;
   }

   public int newClass(String a) {
      return a.newClassItem(a).index;
   }

   Item newMethodTypeItem(String a) {
      a.key2.set(16, a, (String)null, (String)null);
      Item a = a.get(a.key2);
      if (a == null) {
         a.pool.put12(16, a.newUTF8(a));
         a = new Item(a.index++, a.key2);
         a.put(a);
      }

      return a;
   }

   public int newMethodType(String a) {
      return a.newMethodTypeItem(a).index;
   }

   Item newHandleItem(int a, String a, String a, String a, boolean a) {
      a.key4.set(20 + a, a, a, a);
      Item a = a.get(a.key4);
      if (a == null) {
         if (a <= 4) {
            a.put112(15, a, a.newField(a, a, a));
         } else {
            a.put112(15, a, a.newMethod(a, a, a, a));
         }

         a = new Item(a.index++, a.key4);
         a.put(a);
      }

      return a;
   }

   /** @deprecated */
   @Deprecated
   public int newHandle(int a, String a, String a, String a) {
      return a.newHandle(a, a, a, a, a == 9);
   }

   public int newHandle(int a, String a, String a, String a, boolean a) {
      return a.newHandleItem(a, a, a, a, a).index;
   }

   Item newInvokeDynamicItem(String a, String a, Handle a, Object... a) {
      ByteVector a = a.bootstrapMethods;
      if (a == null) {
         a = a.bootstrapMethods = new ByteVector();
      }

      int a = a.length;
      int a = a.hashCode();
      a.putShort(a.newHandle(a.tag, a.owner, a.name, a.desc, a.isInterface()));
      int a = a.length;
      a.putShort(a);

      for(int a = 0; a < a; ++a) {
         Object a = a[a];
         a ^= a.hashCode();
         a.putShort(a.newConst(a));
      }

      byte[] a = a.data;
      int a = 2 + a << 1;
      a &= Integer.MAX_VALUE;
      Item a = a.items[a % a.items.length];

      int a;
      label44:
      while(a != null) {
         if (a.type == 33 && a.hashCode == a) {
            a = a.intVal;
            int a = 0;

            while(true) {
               if (a >= a) {
                  break label44;
               }

               if (a[a + a] != a[a + a]) {
                  a = a.next;
                  break;
               }

               ++a;
            }
         } else {
            a = a.next;
         }
      }

      if (a != null) {
         a = a.index;
         a.length = a;
      } else {
         a = a.bootstrapMethodsCount++;
         a = new Item(a);
         a.set(a, a);
         a.put(a);
      }

      a.key3.set(a, a, a);
      a = a.get(a.key3);
      if (a == null) {
         a.put122(18, a, a.newNameType(a, a));
         a = new Item(a.index++, a.key3);
         a.put(a);
      }

      return a;
   }

   public int newInvokeDynamic(String a, String a, Handle a, Object... a) {
      return a.newInvokeDynamicItem(a, a, a, a).index;
   }

   Item newFieldItem(String a, String a, String a) {
      a.key3.set(9, a, a, a);
      Item a = a.get(a.key3);
      if (a == null) {
         a.put122(9, a.newClass(a), a.newNameType(a, a));
         a = new Item(a.index++, a.key3);
         a.put(a);
      }

      return a;
   }

   public int newField(String a, String a, String a) {
      return a.newFieldItem(a, a, a).index;
   }

   Item newMethodItem(String a, String a, String a, boolean a) {
      int a = a ? 11 : 10;
      a.key3.set(a, a, a, a);
      Item a = a.get(a.key3);
      if (a == null) {
         a.put122(a, a.newClass(a), a.newNameType(a, a));
         a = new Item(a.index++, a.key3);
         a.put(a);
      }

      return a;
   }

   public int newMethod(String a, String a, String a, boolean a) {
      return a.newMethodItem(a, a, a, a).index;
   }

   Item newInteger(int a) {
      a.key.set(a);
      Item a = a.get(a.key);
      if (a == null) {
         a.pool.putByte(3).putInt(a);
         a = new Item(a.index++, a.key);
         a.put(a);
      }

      return a;
   }

   Item newFloat(float a) {
      a.key.set(a);
      Item a = a.get(a.key);
      if (a == null) {
         a.pool.putByte(4).putInt(a.key.intVal);
         a = new Item(a.index++, a.key);
         a.put(a);
      }

      return a;
   }

   Item newLong(long a) {
      a.key.set(a);
      Item a = a.get(a.key);
      if (a == null) {
         a.pool.putByte(5).putLong(a);
         a = new Item(a.index, a.key);
         a.index += 2;
         a.put(a);
      }

      return a;
   }

   Item newDouble(double a) {
      a.key.set(a);
      Item a = a.get(a.key);
      if (a == null) {
         a.pool.putByte(6).putLong(a.key.longVal);
         a = new Item(a.index, a.key);
         a.index += 2;
         a.put(a);
      }

      return a;
   }

   private Item newString(String a) {
      a.key2.set(8, a, (String)null, (String)null);
      Item a = a.get(a.key2);
      if (a == null) {
         a.pool.put12(8, a.newUTF8(a));
         a = new Item(a.index++, a.key2);
         a.put(a);
      }

      return a;
   }

   public int newNameType(String a, String a) {
      return a.newNameTypeItem(a, a).index;
   }

   Item newNameTypeItem(String a, String a) {
      a.key2.set(12, a, a, (String)null);
      Item a = a.get(a.key2);
      if (a == null) {
         a.put122(12, a.newUTF8(a), a.newUTF8(a));
         a = new Item(a.index++, a.key2);
         a.put(a);
      }

      return a;
   }

   int addType(String a) {
      a.key.set(30, a, (String)null, (String)null);
      Item a = a.get(a.key);
      if (a == null) {
         a = a.addType(a.key);
      }

      return a.index;
   }

   int addUninitializedType(String a, int a) {
      a.key.type = 31;
      a.key.intVal = a;
      a.key.strVal1 = a;
      a.key.hashCode = Integer.MAX_VALUE & 31 + a.hashCode() + a;
      Item a = a.get(a.key);
      if (a == null) {
         a = a.addType(a.key);
      }

      return a.index;
   }

   private Item addType(Item a1) {
      ++a.typeCount;
      Item a = new Item(a.typeCount, a.key);
      a.put(a);
      if (a.typeTable == null) {
         a.typeTable = new Item[16];
      }

      if (a.typeCount == a.typeTable.length) {
         Item[] a = new Item[2 * a.typeTable.length];
         System.arraycopy(a.typeTable, 0, a, 0, a.typeTable.length);
         a.typeTable = a;
      }

      a.typeTable[a.typeCount] = a;
      return a;
   }

   int getMergedType(int a, int a) {
      a.key2.type = 32;
      a.key2.longVal = (long)a | (long)a << 32;
      a.key2.hashCode = Integer.MAX_VALUE & 32 + a + a;
      Item a = a.get(a.key2);
      if (a == null) {
         String a = a.typeTable[a].strVal1;
         String a = a.typeTable[a].strVal1;
         a.key2.intVal = a.addType(a.getCommonSuperClass(a, a));
         a = new Item(0, a.key2);
         a.put(a);
      }

      return a.intVal;
   }

   protected String getCommonSuperClass(String a, String a) {
      ClassLoader a = a.getClass().getClassLoader();

      Class a;
      Class a;
      try {
         a = Class.forName(a.replace('/', '.'), false, a);
         a = Class.forName(a.replace('/', '.'), false, a);
      } catch (Exception var7) {
         throw new RuntimeException(var7.toString());
      }

      if (a.isAssignableFrom(a)) {
         return a;
      } else if (a.isAssignableFrom(a)) {
         return a;
      } else if (!a.isInterface() && !a.isInterface()) {
         do {
            a = a.getSuperclass();
         } while(!a.isAssignableFrom(a));

         return a.getName().replace('.', '/');
      } else {
         return "java/lang/Object";
      }
   }

   private Item get(Item a) {
      Item a;
      for(a = a.items[a.hashCode % a.items.length]; a != null && (a.type != a.type || !a.isEqualTo(a)); a = a.next) {
      }

      return a;
   }

   private void put(Item a) {
      int a;
      if (a.index + a.typeCount > a.threshold) {
         a = a.items.length;
         int a = a * 2 + 1;
         Item[] a = new Item[a];

         Item a;
         for(int a = a - 1; a >= 0; --a) {
            for(Item a = a.items[a]; a != null; a = a) {
               int a = a.hashCode % a.length;
               a = a.next;
               a.next = a[a];
               a[a] = a;
            }
         }

         a.items = a;
         a.threshold = (int)((double)a * 0.75D);
      }

      a = a.hashCode % a.items.length;
      a.next = a.items[a];
      a.items[a] = a;
   }

   private void put122(int a, int a, int a) {
      a.pool.put12(a, a).putShort(a);
   }

   private void put112(int a, int a, int a) {
      a.pool.put11(a, a).putShort(a);
   }

   static {
      byte[] a = new byte[220];
      String a = "AAAAAAAAAAAAAAAABCLMMDDDDDEEEEEEEEEEEEEEEEEEEEAAAAAAAADDDDDEEEEEEEEEEEEEEEEEEEEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAANAAAAAAAAAAAAAAAAAAAAJJJJJJJJJJJJJJJJDOPAAAAAAGGGGGGGHIFBFAAFFAARQJJKKSSSSSSSSSSSSSSSSSS";

      for(int a = 0; a < a.length; ++a) {
         a[a] = (byte)(a.charAt(a) - 65);
      }

      TYPE = a;
   }
}
