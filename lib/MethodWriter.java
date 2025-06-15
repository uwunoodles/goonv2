package org.spongepowered.asm.lib;

class MethodWriter extends MethodVisitor {
   static final int ACC_CONSTRUCTOR = 524288;
   static final int SAME_FRAME = 0;
   static final int SAME_LOCALS_1_STACK_ITEM_FRAME = 64;
   static final int RESERVED = 128;
   static final int SAME_LOCALS_1_STACK_ITEM_FRAME_EXTENDED = 247;
   static final int CHOP_FRAME = 248;
   static final int SAME_FRAME_EXTENDED = 251;
   static final int APPEND_FRAME = 252;
   static final int FULL_FRAME = 255;
   static final int FRAMES = 0;
   static final int INSERTED_FRAMES = 1;
   static final int MAXS = 2;
   static final int NOTHING = 3;
   final ClassWriter cw;
   private int access;
   private final int name;
   private final int desc;
   private final String descriptor;
   String signature;
   int classReaderOffset;
   int classReaderLength;
   int exceptionCount;
   int[] exceptions;
   private ByteVector annd;
   private AnnotationWriter anns;
   private AnnotationWriter ianns;
   private AnnotationWriter tanns;
   private AnnotationWriter itanns;
   private AnnotationWriter[] panns;
   private AnnotationWriter[] ipanns;
   private int synthetics;
   private Attribute attrs;
   private ByteVector code = new ByteVector();
   private int maxStack;
   private int maxLocals;
   private int currentLocals;
   private int frameCount;
   private ByteVector stackMap;
   private int previousFrameOffset;
   private int[] previousFrame;
   private int[] frame;
   private int handlerCount;
   private Handler firstHandler;
   private Handler lastHandler;
   private int methodParametersCount;
   private ByteVector methodParameters;
   private int localVarCount;
   private ByteVector localVar;
   private int localVarTypeCount;
   private ByteVector localVarType;
   private int lineNumberCount;
   private ByteVector lineNumber;
   private int lastCodeOffset;
   private AnnotationWriter ctanns;
   private AnnotationWriter ictanns;
   private Attribute cattrs;
   private int subroutines;
   private final int compute;
   private Label labels;
   private Label previousBlock;
   private Label currentBlock;
   private int stackSize;
   private int maxStackSize;

   MethodWriter(ClassWriter a, int a, String a, String a, String a, String[] a, int a) {
      super(327680);
      if (a.firstMethod == null) {
         a.firstMethod = a;
      } else {
         a.lastMethod.mv = a;
      }

      a.lastMethod = a;
      a.cw = a;
      a.access = a;
      if ("<init>".equals(a)) {
         a.access |= 524288;
      }

      a.name = a.newUTF8(a);
      a.desc = a.newUTF8(a);
      a.descriptor = a;
      a.signature = a;
      int a;
      if (a != null && a.length > 0) {
         a.exceptionCount = a.length;
         a.exceptions = new int[a.exceptionCount];

         for(a = 0; a < a.exceptionCount; ++a) {
            a.exceptions[a] = a.newClass(a[a]);
         }
      }

      a.compute = a;
      if (a != 3) {
         a = Type.getArgumentsAndReturnSizes(a.descriptor) >> 2;
         if ((a & 8) != 0) {
            --a;
         }

         a.maxLocals = a;
         a.currentLocals = a;
         a.labels = new Label();
         Label var10000 = a.labels;
         var10000.status |= 8;
         a.visitLabel(a.labels);
      }

   }

   public void visitParameter(String a, int a) {
      if (a.methodParameters == null) {
         a.methodParameters = new ByteVector();
      }

      ++a.methodParametersCount;
      a.methodParameters.putShort(a == null ? 0 : a.cw.newUTF8(a)).putShort(a);
   }

   public AnnotationVisitor visitAnnotationDefault() {
      a.annd = new ByteVector();
      return new AnnotationWriter(a.cw, false, a.annd, (ByteVector)null, 0);
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

   public AnnotationVisitor visitParameterAnnotation(int a, String a, boolean a) {
      ByteVector a = new ByteVector();
      if ("Ljava/lang/Synthetic;".equals(a)) {
         a.synthetics = Math.max(a.synthetics, a + 1);
         return new AnnotationWriter(a.cw, false, a, (ByteVector)null, 0);
      } else {
         a.putShort(a.cw.newUTF8(a)).putShort(0);
         AnnotationWriter a = new AnnotationWriter(a.cw, true, a, a, 2);
         if (a) {
            if (a.panns == null) {
               a.panns = new AnnotationWriter[Type.getArgumentTypes(a.descriptor).length];
            }

            a.next = a.panns[a];
            a.panns[a] = a;
         } else {
            if (a.ipanns == null) {
               a.ipanns = new AnnotationWriter[Type.getArgumentTypes(a.descriptor).length];
            }

            a.next = a.ipanns[a];
            a.ipanns[a] = a;
         }

         return a;
      }
   }

   public void visitAttribute(Attribute a) {
      if (a.isCodeAttribute()) {
         a.next = a.cattrs;
         a.cattrs = a;
      } else {
         a.next = a.attrs;
         a.attrs = a;
      }

   }

   public void visitCode() {
   }

   public void visitFrame(int a, int a, Object[] a, int a, Object[] a) {
      if (a.compute != 0) {
         if (a.compute == 1) {
            if (a.currentBlock.frame == null) {
               a.currentBlock.frame = new CurrentFrame();
               a.currentBlock.frame.owner = a.currentBlock;
               a.currentBlock.frame.initInputFrame(a.cw, a.access, Type.getArgumentTypes(a.descriptor), a);
               a.visitImplicitFirstFrame();
            } else {
               if (a == -1) {
                  a.currentBlock.frame.set(a.cw, a, a, a, a);
               }

               a.visitFrame(a.currentBlock.frame);
            }
         } else {
            int a;
            int a;
            if (a == -1) {
               if (a.previousFrame == null) {
                  a.visitImplicitFirstFrame();
               }

               a.currentLocals = a;
               a = a.startFrame(a.code.length, a, a);

               for(a = 0; a < a; ++a) {
                  if (a[a] instanceof String) {
                     a.frame[a++] = 24117248 | a.cw.addType((String)a[a]);
                  } else if (a[a] instanceof Integer) {
                     a.frame[a++] = (Integer)a[a];
                  } else {
                     a.frame[a++] = 25165824 | a.cw.addUninitializedType("", ((Label)a[a]).position);
                  }
               }

               for(a = 0; a < a; ++a) {
                  if (a[a] instanceof String) {
                     a.frame[a++] = 24117248 | a.cw.addType((String)a[a]);
                  } else if (a[a] instanceof Integer) {
                     a.frame[a++] = (Integer)a[a];
                  } else {
                     a.frame[a++] = 25165824 | a.cw.addUninitializedType("", ((Label)a[a]).position);
                  }
               }

               a.endFrame();
            } else {
               if (a.stackMap == null) {
                  a.stackMap = new ByteVector();
                  a = a.code.length;
               } else {
                  a = a.code.length - a.previousFrameOffset - 1;
                  if (a < 0) {
                     if (a == 3) {
                        return;
                     }

                     throw new IllegalStateException();
                  }
               }

               label88:
               switch(a) {
               case 0:
                  a.currentLocals = a;
                  a.stackMap.putByte(255).putShort(a).putShort(a);

                  for(a = 0; a < a; ++a) {
                     a.writeFrameType(a[a]);
                  }

                  a.stackMap.putShort(a);

                  for(a = 0; a < a; ++a) {
                     a.writeFrameType(a[a]);
                  }
                  break;
               case 1:
                  a.currentLocals += a;
                  a.stackMap.putByte(251 + a).putShort(a);
                  a = 0;

                  while(true) {
                     if (a >= a) {
                        break label88;
                     }

                     a.writeFrameType(a[a]);
                     ++a;
                  }
               case 2:
                  a.currentLocals -= a;
                  a.stackMap.putByte(251 - a).putShort(a);
                  break;
               case 3:
                  if (a < 64) {
                     a.stackMap.putByte(a);
                  } else {
                     a.stackMap.putByte(251).putShort(a);
                  }
                  break;
               case 4:
                  if (a < 64) {
                     a.stackMap.putByte(64 + a);
                  } else {
                     a.stackMap.putByte(247).putShort(a);
                  }

                  a.writeFrameType(a[0]);
               }

               a.previousFrameOffset = a.code.length;
               ++a.frameCount;
            }
         }

         a.maxStack = Math.max(a.maxStack, a);
         a.maxLocals = Math.max(a.maxLocals, a.currentLocals);
      }
   }

   public void visitInsn(int a) {
      a.lastCodeOffset = a.code.length;
      a.code.putByte(a);
      if (a.currentBlock != null) {
         if (a.compute != 0 && a.compute != 1) {
            int a = a.stackSize + Frame.SIZE[a];
            if (a > a.maxStackSize) {
               a.maxStackSize = a;
            }

            a.stackSize = a;
         } else {
            a.currentBlock.frame.execute(a, 0, (ClassWriter)null, (Item)null);
         }

         if (a >= 172 && a <= 177 || a == 191) {
            a.noSuccessor();
         }
      }

   }

   public void visitIntInsn(int a, int a) {
      a.lastCodeOffset = a.code.length;
      if (a.currentBlock != null) {
         if (a.compute != 0 && a.compute != 1) {
            if (a != 188) {
               int a = a.stackSize + 1;
               if (a > a.maxStackSize) {
                  a.maxStackSize = a;
               }

               a.stackSize = a;
            }
         } else {
            a.currentBlock.frame.execute(a, a, (ClassWriter)null, (Item)null);
         }
      }

      if (a == 17) {
         a.code.put12(a, a);
      } else {
         a.code.put11(a, a);
      }

   }

   public void visitVarInsn(int a, int a) {
      a.lastCodeOffset = a.code.length;
      int a;
      if (a.currentBlock != null) {
         if (a.compute != 0 && a.compute != 1) {
            if (a == 169) {
               Label var10000 = a.currentBlock;
               var10000.status |= 256;
               a.currentBlock.inputStackTop = a.stackSize;
               a.noSuccessor();
            } else {
               a = a.stackSize + Frame.SIZE[a];
               if (a > a.maxStackSize) {
                  a.maxStackSize = a;
               }

               a.stackSize = a;
            }
         } else {
            a.currentBlock.frame.execute(a, a, (ClassWriter)null, (Item)null);
         }
      }

      if (a.compute != 3) {
         if (a != 22 && a != 24 && a != 55 && a != 57) {
            a = a + 1;
         } else {
            a = a + 2;
         }

         if (a > a.maxLocals) {
            a.maxLocals = a;
         }
      }

      if (a < 4 && a != 169) {
         if (a < 54) {
            a = 26 + (a - 21 << 2) + a;
         } else {
            a = 59 + (a - 54 << 2) + a;
         }

         a.code.putByte(a);
      } else if (a >= 256) {
         a.code.putByte(196).put12(a, a);
      } else {
         a.code.put11(a, a);
      }

      if (a >= 54 && a.compute == 0 && a.handlerCount > 0) {
         a.visitLabel(new Label());
      }

   }

   public void visitTypeInsn(int a, String a) {
      a.lastCodeOffset = a.code.length;
      Item a = a.cw.newClassItem(a);
      if (a.currentBlock != null) {
         if (a.compute != 0 && a.compute != 1) {
            if (a == 187) {
               int a = a.stackSize + 1;
               if (a > a.maxStackSize) {
                  a.maxStackSize = a;
               }

               a.stackSize = a;
            }
         } else {
            a.currentBlock.frame.execute(a, a.code.length, a.cw, a);
         }
      }

      a.code.put12(a, a.index);
   }

   public void visitFieldInsn(int a, String a, String a, String a) {
      a.lastCodeOffset = a.code.length;
      Item a = a.cw.newFieldItem(a, a, a);
      if (a.currentBlock != null) {
         if (a.compute != 0 && a.compute != 1) {
            int a;
            label77: {
               char a = a.charAt(0);
               switch(a) {
               case 178:
                  a = a.stackSize + (a != 'D' && a != 'J' ? 1 : 2);
                  break label77;
               case 179:
                  a = a.stackSize + (a != 'D' && a != 'J' ? -1 : -2);
                  break label77;
               case 180:
                  a = a.stackSize + (a != 'D' && a != 'J' ? 0 : 1);
                  break label77;
               }

               a = a.stackSize + (a != 'D' && a != 'J' ? -2 : -3);
            }

            if (a > a.maxStackSize) {
               a.maxStackSize = a;
            }

            a.stackSize = a;
         } else {
            a.currentBlock.frame.execute(a, 0, a.cw, a);
         }
      }

      a.code.put12(a, a.index);
   }

   public void visitMethodInsn(int a, String a, String a, String a, boolean a) {
      a.lastCodeOffset = a.code.length;
      Item a = a.cw.newMethodItem(a, a, a, a);
      int a = a.intVal;
      if (a.currentBlock != null) {
         if (a.compute != 0 && a.compute != 1) {
            if (a == 0) {
               a = Type.getArgumentsAndReturnSizes(a);
               a.intVal = a;
            }

            int a;
            if (a == 184) {
               a = a.stackSize - (a >> 2) + (a & 3) + 1;
            } else {
               a = a.stackSize - (a >> 2) + (a & 3);
            }

            if (a > a.maxStackSize) {
               a.maxStackSize = a;
            }

            a.stackSize = a;
         } else {
            a.currentBlock.frame.execute(a, 0, a.cw, a);
         }
      }

      if (a == 185) {
         if (a == 0) {
            a = Type.getArgumentsAndReturnSizes(a);
            a.intVal = a;
         }

         a.code.put12(185, a.index).put11(a >> 2, 0);
      } else {
         a.code.put12(a, a.index);
      }

   }

   public void visitInvokeDynamicInsn(String a, String a, Handle a, Object... a) {
      a.lastCodeOffset = a.code.length;
      Item a = a.cw.newInvokeDynamicItem(a, a, a, a);
      int a = a.intVal;
      if (a.currentBlock != null) {
         if (a.compute != 0 && a.compute != 1) {
            if (a == 0) {
               a = Type.getArgumentsAndReturnSizes(a);
               a.intVal = a;
            }

            int a = a.stackSize - (a >> 2) + (a & 3) + 1;
            if (a > a.maxStackSize) {
               a.maxStackSize = a;
            }

            a.stackSize = a;
         } else {
            a.currentBlock.frame.execute(186, 0, a.cw, a);
         }
      }

      a.code.put12(186, a.index);
      a.code.putShort(0);
   }

   public void visitJumpInsn(int a, Label a) {
      boolean a = a >= 200;
      a = a ? a - 33 : a;
      a.lastCodeOffset = a.code.length;
      Label a = null;
      if (a.currentBlock != null) {
         Label var10000;
         if (a.compute == 0) {
            a.currentBlock.frame.execute(a, 0, (ClassWriter)null, (Item)null);
            var10000 = a.getFirst();
            var10000.status |= 16;
            a.addSuccessor(0, a);
            if (a != 167) {
               a = new Label();
            }
         } else if (a.compute == 1) {
            a.currentBlock.frame.execute(a, 0, (ClassWriter)null, (Item)null);
         } else if (a == 168) {
            if ((a.status & 512) == 0) {
               a.status |= 512;
               ++a.subroutines;
            }

            var10000 = a.currentBlock;
            var10000.status |= 128;
            a.addSuccessor(a.stackSize + 1, a);
            a = new Label();
         } else {
            a.stackSize += Frame.SIZE[a];
            a.addSuccessor(a.stackSize, a);
         }
      }

      if ((a.status & 2) != 0 && a.position - a.code.length < -32768) {
         if (a == 167) {
            a.code.putByte(200);
         } else if (a == 168) {
            a.code.putByte(201);
         } else {
            if (a != null) {
               a.status |= 16;
            }

            a.code.putByte(a <= 166 ? (a + 1 ^ 1) - 1 : a ^ 1);
            a.code.putShort(8);
            a.code.putByte(200);
         }

         a.put(a, a.code, a.code.length - 1, true);
      } else if (a) {
         a.code.putByte(a + 33);
         a.put(a, a.code, a.code.length - 1, true);
      } else {
         a.code.putByte(a);
         a.put(a, a.code, a.code.length - 1, false);
      }

      if (a.currentBlock != null) {
         if (a != null) {
            a.visitLabel(a);
         }

         if (a == 167) {
            a.noSuccessor();
         }
      }

   }

   public void visitLabel(Label a) {
      ClassWriter var10000 = a.cw;
      var10000.hasAsmInsns |= a.resolve(a, a.code.length, a.code.data);
      if ((a.status & 1) == 0) {
         if (a.compute == 0) {
            Label var2;
            if (a.currentBlock != null) {
               if (a.position == a.currentBlock.position) {
                  var2 = a.currentBlock;
                  var2.status |= a.status & 16;
                  a.frame = a.currentBlock.frame;
                  return;
               }

               a.addSuccessor(0, a);
            }

            a.currentBlock = a;
            if (a.frame == null) {
               a.frame = new Frame();
               a.frame.owner = a;
            }

            if (a.previousBlock != null) {
               if (a.position == a.previousBlock.position) {
                  var2 = a.previousBlock;
                  var2.status |= a.status & 16;
                  a.frame = a.previousBlock.frame;
                  a.currentBlock = a.previousBlock;
                  return;
               }

               a.previousBlock.successor = a;
            }

            a.previousBlock = a;
         } else if (a.compute == 1) {
            if (a.currentBlock == null) {
               a.currentBlock = a;
            } else {
               a.currentBlock.frame.owner = a;
            }
         } else if (a.compute == 2) {
            if (a.currentBlock != null) {
               a.currentBlock.outputStackMax = a.maxStackSize;
               a.addSuccessor(a.stackSize, a);
            }

            a.currentBlock = a;
            a.stackSize = 0;
            a.maxStackSize = 0;
            if (a.previousBlock != null) {
               a.previousBlock.successor = a;
            }

            a.previousBlock = a;
         }

      }
   }

   public void visitLdcInsn(Object a) {
      a.lastCodeOffset = a.code.length;
      Item a = a.cw.newConstItem(a);
      int a;
      if (a.currentBlock != null) {
         if (a.compute != 0 && a.compute != 1) {
            if (a.type != 5 && a.type != 6) {
               a = a.stackSize + 1;
            } else {
               a = a.stackSize + 2;
            }

            if (a > a.maxStackSize) {
               a.maxStackSize = a;
            }

            a.stackSize = a;
         } else {
            a.currentBlock.frame.execute(18, 0, a.cw, a);
         }
      }

      a = a.index;
      if (a.type != 5 && a.type != 6) {
         if (a >= 256) {
            a.code.put12(19, a);
         } else {
            a.code.put11(18, a);
         }
      } else {
         a.code.put12(20, a);
      }

   }

   public void visitIincInsn(int a, int a) {
      a.lastCodeOffset = a.code.length;
      if (a.currentBlock != null && (a.compute == 0 || a.compute == 1)) {
         a.currentBlock.frame.execute(132, a, (ClassWriter)null, (Item)null);
      }

      if (a.compute != 3) {
         int a = a + 1;
         if (a > a.maxLocals) {
            a.maxLocals = a;
         }
      }

      if (a <= 255 && a <= 127 && a >= -128) {
         a.code.putByte(132).put11(a, a);
      } else {
         a.code.putByte(196).put12(132, a).putShort(a);
      }

   }

   public void visitTableSwitchInsn(int a, int a, Label a, Label... a) {
      a.lastCodeOffset = a.code.length;
      int a = a.code.length;
      a.code.putByte(170);
      a.code.putByteArray((byte[])null, 0, (4 - a.code.length % 4) % 4);
      a.put(a, a.code, a, true);
      a.code.putInt(a).putInt(a);

      for(int a = 0; a < a.length; ++a) {
         a[a].put(a, a.code, a, true);
      }

      a.visitSwitchInsn(a, a);
   }

   public void visitLookupSwitchInsn(Label a, int[] a, Label[] a) {
      a.lastCodeOffset = a.code.length;
      int a = a.code.length;
      a.code.putByte(171);
      a.code.putByteArray((byte[])null, 0, (4 - a.code.length % 4) % 4);
      a.put(a, a.code, a, true);
      a.code.putInt(a.length);

      for(int a = 0; a < a.length; ++a) {
         a.code.putInt(a[a]);
         a[a].put(a, a.code, a, true);
      }

      a.visitSwitchInsn(a, a);
   }

   private void visitSwitchInsn(Label a, Label[] a) {
      if (a.currentBlock != null) {
         int a;
         if (a.compute == 0) {
            a.currentBlock.frame.execute(171, 0, (ClassWriter)null, (Item)null);
            a.addSuccessor(0, a);
            Label var10000 = a.getFirst();
            var10000.status |= 16;

            for(a = 0; a < a.length; ++a) {
               a.addSuccessor(0, a[a]);
               var10000 = a[a].getFirst();
               var10000.status |= 16;
            }
         } else {
            --a.stackSize;
            a.addSuccessor(a.stackSize, a);

            for(a = 0; a < a.length; ++a) {
               a.addSuccessor(a.stackSize, a[a]);
            }
         }

         a.noSuccessor();
      }

   }

   public void visitMultiANewArrayInsn(String a, int a) {
      a.lastCodeOffset = a.code.length;
      Item a = a.cw.newClassItem(a);
      if (a.currentBlock != null) {
         if (a.compute != 0 && a.compute != 1) {
            a.stackSize += 1 - a;
         } else {
            a.currentBlock.frame.execute(197, a, a.cw, a);
         }
      }

      a.code.put12(197, a.index).putByte(a);
   }

   public AnnotationVisitor visitInsnAnnotation(int a, TypePath a, String a, boolean a) {
      ByteVector a = new ByteVector();
      a = a & -16776961 | a.lastCodeOffset << 8;
      AnnotationWriter.putTarget(a, a, a);
      a.putShort(a.cw.newUTF8(a)).putShort(0);
      AnnotationWriter a = new AnnotationWriter(a.cw, true, a, a, a.length - 2);
      if (a) {
         a.next = a.ctanns;
         a.ctanns = a;
      } else {
         a.next = a.ictanns;
         a.ictanns = a;
      }

      return a;
   }

   public void visitTryCatchBlock(Label a, Label a, Label a, String a) {
      ++a.handlerCount;
      Handler a = new Handler();
      a.start = a;
      a.end = a;
      a.handler = a;
      a.desc = a;
      a.type = a != null ? a.cw.newClass(a) : 0;
      if (a.lastHandler == null) {
         a.firstHandler = a;
      } else {
         a.lastHandler.next = a;
      }

      a.lastHandler = a;
   }

   public AnnotationVisitor visitTryCatchAnnotation(int a, TypePath a, String a, boolean a) {
      ByteVector a = new ByteVector();
      AnnotationWriter.putTarget(a, a, a);
      a.putShort(a.cw.newUTF8(a)).putShort(0);
      AnnotationWriter a = new AnnotationWriter(a.cw, true, a, a, a.length - 2);
      if (a) {
         a.next = a.ctanns;
         a.ctanns = a;
      } else {
         a.next = a.ictanns;
         a.ictanns = a;
      }

      return a;
   }

   public void visitLocalVariable(String a, String a, String a, Label a, Label a, int a) {
      if (a != null) {
         if (a.localVarType == null) {
            a.localVarType = new ByteVector();
         }

         ++a.localVarTypeCount;
         a.localVarType.putShort(a.position).putShort(a.position - a.position).putShort(a.cw.newUTF8(a)).putShort(a.cw.newUTF8(a)).putShort(a);
      }

      if (a.localVar == null) {
         a.localVar = new ByteVector();
      }

      ++a.localVarCount;
      a.localVar.putShort(a.position).putShort(a.position - a.position).putShort(a.cw.newUTF8(a)).putShort(a.cw.newUTF8(a)).putShort(a);
      if (a.compute != 3) {
         char a = a.charAt(0);
         int a = a + (a != 'J' && a != 'D' ? 1 : 2);
         if (a > a.maxLocals) {
            a.maxLocals = a;
         }
      }

   }

   public AnnotationVisitor visitLocalVariableAnnotation(int a, TypePath a, Label[] a, Label[] a, int[] a, String a, boolean a) {
      ByteVector a = new ByteVector();
      a.putByte(a >>> 24).putShort(a.length);

      int a;
      for(a = 0; a < a.length; ++a) {
         a.putShort(a[a].position).putShort(a[a].position - a[a].position).putShort(a[a]);
      }

      if (a == null) {
         a.putByte(0);
      } else {
         a = a.b[a.offset] * 2 + 1;
         a.putByteArray(a.b, a.offset, a);
      }

      a.putShort(a.cw.newUTF8(a)).putShort(0);
      AnnotationWriter a = new AnnotationWriter(a.cw, true, a, a, a.length - 2);
      if (a) {
         a.next = a.ctanns;
         a.ctanns = a;
      } else {
         a.next = a.ictanns;
         a.ictanns = a;
      }

      return a;
   }

   public void visitLineNumber(int a, Label a) {
      if (a.lineNumber == null) {
         a.lineNumber = new ByteVector();
      }

      ++a.lineNumberCount;
      a.lineNumber.putShort(a.position);
      a.lineNumber.putShort(a);
   }

   public void visitMaxs(int a, int a) {
      Handler a;
      Label a;
      Label a;
      Label a;
      int a;
      Edge a;
      Label a;
      if (a.compute == 0) {
         for(a = a.firstHandler; a != null; a = a.next) {
            a = a.start.getFirst();
            a = a.handler.getFirst();
            a = a.end.getFirst();
            String a = a.desc == null ? "java/lang/Throwable" : a.desc;
            a = 24117248 | a.cw.addType(a);

            for(a.status |= 16; a != a; a = a.successor) {
               a = new Edge();
               a.info = a;
               a.successor = a;
               a.next = a.successors;
               a.successors = a;
            }
         }

         Frame a = a.labels.frame;
         a.initInputFrame(a.cw, a.access, Type.getArgumentTypes(a.descriptor), a.maxLocals);
         a.visitFrame(a);
         int a = 0;
         a = a.labels;

         while(a != null) {
            a = a;
            a = a.next;
            a.next = null;
            a = a.frame;
            if ((a.status & 16) != 0) {
               a.status |= 32;
            }

            a.status |= 64;
            a = a.inputStack.length + a.outputStackMax;
            if (a > a) {
               a = a;
            }

            for(a = a.successors; a != null; a = a.next) {
               Label a = a.successor.getFirst();
               boolean a = a.merge(a.cw, a.frame, a.info);
               if (a && a.next == null) {
                  a.next = a;
                  a = a;
               }
            }
         }

         for(a = a.labels; a != null; a = a.successor) {
            a = a.frame;
            if ((a.status & 32) != 0) {
               a.visitFrame(a);
            }

            if ((a.status & 64) == 0) {
               Label a = a.successor;
               int a = a.position;
               int a = (a == null ? a.code.length : a.position) - 1;
               if (a >= a) {
                  a = Math.max(a, 1);

                  int a;
                  for(a = a; a < a; ++a) {
                     a.code.data[a] = 0;
                  }

                  a.code.data[a] = -65;
                  a = a.startFrame(a, 0, 1);
                  a.frame[a] = 24117248 | a.cw.addType("java/lang/Throwable");
                  a.endFrame();
                  a.firstHandler = Handler.remove(a.firstHandler, a, a);
               }
            }
         }

         a = a.firstHandler;

         for(a.handlerCount = 0; a != null; a = a.next) {
            ++a.handlerCount;
         }

         a.maxStack = a;
      } else if (a.compute == 2) {
         for(a = a.firstHandler; a != null; a = a.next) {
            a = a.start;
            a = a.handler;

            for(a = a.end; a != a; a = a.successor) {
               Edge a = new Edge();
               a.info = Integer.MAX_VALUE;
               a.successor = a;
               if ((a.status & 128) == 0) {
                  a.next = a.successors;
                  a.successors = a;
               } else {
                  a.next = a.successors.next.next;
                  a.successors.next.next = a;
               }
            }
         }

         int a;
         if (a.subroutines > 0) {
            a = 0;
            a.labels.visitSubroutine((Label)null, 1L, a.subroutines);

            for(a = a.labels; a != null; a = a.successor) {
               if ((a.status & 128) != 0) {
                  a = a.successors.next.successor;
                  if ((a.status & 1024) == 0) {
                     ++a;
                     a.visitSubroutine((Label)null, (long)a / 32L << 32 | 1L << a % 32, a.subroutines);
                  }
               }
            }

            for(a = a.labels; a != null; a = a.successor) {
               if ((a.status & 128) != 0) {
                  for(a = a.labels; a != null; a = a.successor) {
                     a.status &= -2049;
                  }

                  a = a.successors.next.successor;
                  a.visitSubroutine(a, 0L, a.subroutines);
               }
            }
         }

         a = 0;
         a = a.labels;

         while(a != null) {
            a = a;
            a = a.next;
            int a = a.inputStackTop;
            a = a + a.outputStackMax;
            if (a > a) {
               a = a;
            }

            a = a.successors;
            if ((a.status & 128) != 0) {
               a = a.next;
            }

            for(; a != null; a = a.next) {
               a = a.successor;
               if ((a.status & 8) == 0) {
                  a.inputStackTop = a.info == Integer.MAX_VALUE ? 1 : a + a.info;
                  a.status |= 8;
                  a.next = a;
                  a = a;
               }
            }
         }

         a.maxStack = Math.max(a, a);
      } else {
         a.maxStack = a;
         a.maxLocals = a;
      }

   }

   public void visitEnd() {
   }

   private void addSuccessor(int a, Label a) {
      Edge a = new Edge();
      a.info = a;
      a.successor = a;
      a.next = a.currentBlock.successors;
      a.currentBlock.successors = a;
   }

   private void noSuccessor() {
      if (a.compute == 0) {
         Label a = new Label();
         a.frame = new Frame();
         a.frame.owner = a;
         a.resolve(a, a.code.length, a.code.data);
         a.previousBlock.successor = a;
         a.previousBlock = a;
      } else {
         a.currentBlock.outputStackMax = a.maxStackSize;
      }

      if (a.compute != 1) {
         a.currentBlock = null;
      }

   }

   private void visitFrame(Frame a) {
      int a = 0;
      int a = 0;
      int a = 0;
      int[] a = a.inputLocals;
      int[] a = a.inputStack;

      int a;
      int a;
      for(a = 0; a < a.length; ++a) {
         a = a[a];
         if (a == 16777216) {
            ++a;
         } else {
            a += a + 1;
            a = 0;
         }

         if (a == 16777220 || a == 16777219) {
            ++a;
         }
      }

      for(a = 0; a < a.length; ++a) {
         a = a[a];
         ++a;
         if (a == 16777220 || a == 16777219) {
            ++a;
         }
      }

      int a = a.startFrame(a.owner.position, a, a);

      for(a = 0; a > 0; --a) {
         a = a[a];
         a.frame[a++] = a;
         if (a == 16777220 || a == 16777219) {
            ++a;
         }

         ++a;
      }

      for(a = 0; a < a.length; ++a) {
         a = a[a];
         a.frame[a++] = a;
         if (a == 16777220 || a == 16777219) {
            ++a;
         }
      }

      a.endFrame();
   }

   private void visitImplicitFirstFrame() {
      int a = a.startFrame(0, a.descriptor.length() + 1, 0);
      if ((a.access & 8) == 0) {
         if ((a.access & 524288) == 0) {
            a.frame[a++] = 24117248 | a.cw.addType(a.cw.thisName);
         } else {
            a.frame[a++] = 6;
         }
      }

      int a = 1;

      while(true) {
         int a = a;
         switch(a.descriptor.charAt(a++)) {
         case 'B':
         case 'C':
         case 'I':
         case 'S':
         case 'Z':
            a.frame[a++] = 1;
            break;
         case 'D':
            a.frame[a++] = 3;
            break;
         case 'E':
         case 'G':
         case 'H':
         case 'K':
         case 'M':
         case 'N':
         case 'O':
         case 'P':
         case 'Q':
         case 'R':
         case 'T':
         case 'U':
         case 'V':
         case 'W':
         case 'X':
         case 'Y':
         default:
            a.frame[1] = a - 3;
            a.endFrame();
            return;
         case 'F':
            a.frame[a++] = 2;
            break;
         case 'J':
            a.frame[a++] = 4;
            break;
         case 'L':
            while(a.descriptor.charAt(a) != ';') {
               ++a;
            }

            a.frame[a++] = 24117248 | a.cw.addType(a.descriptor.substring(a + 1, a++));
            break;
         case '[':
            while(a.descriptor.charAt(a) == '[') {
               ++a;
            }

            if (a.descriptor.charAt(a) == 'L') {
               ++a;

               while(a.descriptor.charAt(a) != ';') {
                  ++a;
               }
            }

            int var10001 = a++;
            ++a;
            a.frame[var10001] = 24117248 | a.cw.addType(a.descriptor.substring(a, a));
         }
      }
   }

   private int startFrame(int a, int a, int a) {
      int a = 3 + a + a;
      if (a.frame == null || a.frame.length < a) {
         a.frame = new int[a];
      }

      a.frame[0] = a;
      a.frame[1] = a;
      a.frame[2] = a;
      return 3;
   }

   private void endFrame() {
      if (a.previousFrame != null) {
         if (a.stackMap == null) {
            a.stackMap = new ByteVector();
         }

         a.writeFrame();
         ++a.frameCount;
      }

      a.previousFrame = a.frame;
      a.frame = null;
   }

   private void writeFrame() {
      int a = a.frame[1];
      int a = a.frame[2];
      if ((a.cw.version & '\uffff') < 50) {
         a.stackMap.putShort(a.frame[0]).putShort(a);
         a.writeFrameTypes(3, 3 + a);
         a.stackMap.putShort(a);
         a.writeFrameTypes(3 + a, 3 + a + a);
      } else {
         int a = a.previousFrame[1];
         int a = 255;
         int a = 0;
         int a;
         if (a.frameCount == 0) {
            a = a.frame[0];
         } else {
            a = a.frame[0] - a.previousFrame[0] - 1;
         }

         if (a == 0) {
            a = a - a;
            switch(a) {
            case -3:
            case -2:
            case -1:
               a = 248;
               a = a;
               break;
            case 0:
               a = a < 64 ? 0 : 251;
               break;
            case 1:
            case 2:
            case 3:
               a = 252;
            }
         } else if (a == a && a == 1) {
            a = a < 63 ? 64 : 247;
         }

         if (a != 255) {
            int a = 3;

            for(int a = 0; a < a; ++a) {
               if (a.frame[a] != a.previousFrame[a]) {
                  a = 255;
                  break;
               }

               ++a;
            }
         }

         switch(a) {
         case 0:
            a.stackMap.putByte(a);
            break;
         case 64:
            a.stackMap.putByte(64 + a);
            a.writeFrameTypes(3 + a, 4 + a);
            break;
         case 247:
            a.stackMap.putByte(247).putShort(a);
            a.writeFrameTypes(3 + a, 4 + a);
            break;
         case 248:
            a.stackMap.putByte(251 + a).putShort(a);
            break;
         case 251:
            a.stackMap.putByte(251).putShort(a);
            break;
         case 252:
            a.stackMap.putByte(251 + a).putShort(a);
            a.writeFrameTypes(3 + a, 3 + a);
            break;
         default:
            a.stackMap.putByte(255).putShort(a).putShort(a);
            a.writeFrameTypes(3, 3 + a);
            a.stackMap.putShort(a);
            a.writeFrameTypes(3 + a, 3 + a + a);
         }

      }
   }

   private void writeFrameTypes(int a, int a) {
      for(int a = a; a < a; ++a) {
         int a = a.frame[a];
         int a = a & -268435456;
         if (a == 0) {
            int a = a & 1048575;
            switch(a & 267386880) {
            case 24117248:
               a.stackMap.putByte(7).putShort(a.cw.newClass(a.cw.typeTable[a].strVal1));
               break;
            case 25165824:
               a.stackMap.putByte(8).putShort(a.cw.typeTable[a].intVal);
               break;
            default:
               a.stackMap.putByte(a);
            }
         } else {
            StringBuilder a = new StringBuilder();
            a >>= 28;

            while(a-- > 0) {
               a.append('[');
            }

            if ((a & 267386880) == 24117248) {
               a.append('L');
               a.append(a.cw.typeTable[a & 1048575].strVal1);
               a.append(';');
            } else {
               switch(a & 15) {
               case 1:
                  a.append('I');
                  break;
               case 2:
                  a.append('F');
                  break;
               case 3:
                  a.append('D');
                  break;
               case 4:
               case 5:
               case 6:
               case 7:
               case 8:
               default:
                  a.append('J');
                  break;
               case 9:
                  a.append('Z');
                  break;
               case 10:
                  a.append('B');
                  break;
               case 11:
                  a.append('C');
                  break;
               case 12:
                  a.append('S');
               }
            }

            a.stackMap.putByte(7).putShort(a.cw.newClass(a.toString()));
         }
      }

   }

   private void writeFrameType(Object a) {
      if (a instanceof String) {
         a.stackMap.putByte(7).putShort(a.cw.newClass((String)a));
      } else if (a instanceof Integer) {
         a.stackMap.putByte((Integer)a);
      } else {
         a.stackMap.putByte(8).putShort(((Label)a).position);
      }

   }

   final int getSize() {
      if (a.classReaderOffset != 0) {
         return 6 + a.classReaderLength;
      } else {
         int a = 8;
         if (a.code.length > 0) {
            if (a.code.length > 65535) {
               throw new RuntimeException("Method code too large!");
            }

            a.cw.newUTF8("Code");
            a += 18 + a.code.length + 8 * a.handlerCount;
            if (a.localVar != null) {
               a.cw.newUTF8("LocalVariableTable");
               a += 8 + a.localVar.length;
            }

            if (a.localVarType != null) {
               a.cw.newUTF8("LocalVariableTypeTable");
               a += 8 + a.localVarType.length;
            }

            if (a.lineNumber != null) {
               a.cw.newUTF8("LineNumberTable");
               a += 8 + a.lineNumber.length;
            }

            if (a.stackMap != null) {
               boolean a = (a.cw.version & '\uffff') >= 50;
               a.cw.newUTF8(a ? "StackMapTable" : "StackMap");
               a += 8 + a.stackMap.length;
            }

            if (a.ctanns != null) {
               a.cw.newUTF8("RuntimeVisibleTypeAnnotations");
               a += 8 + a.ctanns.getSize();
            }

            if (a.ictanns != null) {
               a.cw.newUTF8("RuntimeInvisibleTypeAnnotations");
               a += 8 + a.ictanns.getSize();
            }

            if (a.cattrs != null) {
               a += a.cattrs.getSize(a.cw, a.code.data, a.code.length, a.maxStack, a.maxLocals);
            }
         }

         if (a.exceptionCount > 0) {
            a.cw.newUTF8("Exceptions");
            a += 8 + 2 * a.exceptionCount;
         }

         if ((a.access & 4096) != 0 && ((a.cw.version & '\uffff') < 49 || (a.access & 262144) != 0)) {
            a.cw.newUTF8("Synthetic");
            a += 6;
         }

         if ((a.access & 131072) != 0) {
            a.cw.newUTF8("Deprecated");
            a += 6;
         }

         if (a.signature != null) {
            a.cw.newUTF8("Signature");
            a.cw.newUTF8(a.signature);
            a += 8;
         }

         if (a.methodParameters != null) {
            a.cw.newUTF8("MethodParameters");
            a += 7 + a.methodParameters.length;
         }

         if (a.annd != null) {
            a.cw.newUTF8("AnnotationDefault");
            a += 6 + a.annd.length;
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

         int a;
         if (a.panns != null) {
            a.cw.newUTF8("RuntimeVisibleParameterAnnotations");
            a += 7 + 2 * (a.panns.length - a.synthetics);

            for(a = a.panns.length - 1; a >= a.synthetics; --a) {
               a += a.panns[a] == null ? 0 : a.panns[a].getSize();
            }
         }

         if (a.ipanns != null) {
            a.cw.newUTF8("RuntimeInvisibleParameterAnnotations");
            a += 7 + 2 * (a.ipanns.length - a.synthetics);

            for(a = a.ipanns.length - 1; a >= a.synthetics; --a) {
               a += a.ipanns[a] == null ? 0 : a.ipanns[a].getSize();
            }
         }

         if (a.attrs != null) {
            a += a.attrs.getSize(a.cw, (byte[])null, 0, -1, -1);
         }

         return a;
      }
   }

   final void put(ByteVector a) {
      int a = true;
      int a = 917504 | (a.access & 262144) / 64;
      a.putShort(a.access & ~a).putShort(a.name).putShort(a.desc);
      if (a.classReaderOffset != 0) {
         a.putByteArray(a.cw.cr.b, a.classReaderOffset, a.classReaderLength);
      } else {
         int a = 0;
         if (a.code.length > 0) {
            ++a;
         }

         if (a.exceptionCount > 0) {
            ++a;
         }

         if ((a.access & 4096) != 0 && ((a.cw.version & '\uffff') < 49 || (a.access & 262144) != 0)) {
            ++a;
         }

         if ((a.access & 131072) != 0) {
            ++a;
         }

         if (a.signature != null) {
            ++a;
         }

         if (a.methodParameters != null) {
            ++a;
         }

         if (a.annd != null) {
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

         if (a.panns != null) {
            ++a;
         }

         if (a.ipanns != null) {
            ++a;
         }

         if (a.attrs != null) {
            a += a.attrs.getCount();
         }

         a.putShort(a);
         int a;
         if (a.code.length > 0) {
            a = 12 + a.code.length + 8 * a.handlerCount;
            if (a.localVar != null) {
               a += 8 + a.localVar.length;
            }

            if (a.localVarType != null) {
               a += 8 + a.localVarType.length;
            }

            if (a.lineNumber != null) {
               a += 8 + a.lineNumber.length;
            }

            if (a.stackMap != null) {
               a += 8 + a.stackMap.length;
            }

            if (a.ctanns != null) {
               a += 8 + a.ctanns.getSize();
            }

            if (a.ictanns != null) {
               a += 8 + a.ictanns.getSize();
            }

            if (a.cattrs != null) {
               a += a.cattrs.getSize(a.cw, a.code.data, a.code.length, a.maxStack, a.maxLocals);
            }

            a.putShort(a.cw.newUTF8("Code")).putInt(a);
            a.putShort(a.maxStack).putShort(a.maxLocals);
            a.putInt(a.code.length).putByteArray(a.code.data, 0, a.code.length);
            a.putShort(a.handlerCount);
            if (a.handlerCount > 0) {
               for(Handler a = a.firstHandler; a != null; a = a.next) {
                  a.putShort(a.start.position).putShort(a.end.position).putShort(a.handler.position).putShort(a.type);
               }
            }

            a = 0;
            if (a.localVar != null) {
               ++a;
            }

            if (a.localVarType != null) {
               ++a;
            }

            if (a.lineNumber != null) {
               ++a;
            }

            if (a.stackMap != null) {
               ++a;
            }

            if (a.ctanns != null) {
               ++a;
            }

            if (a.ictanns != null) {
               ++a;
            }

            if (a.cattrs != null) {
               a += a.cattrs.getCount();
            }

            a.putShort(a);
            if (a.localVar != null) {
               a.putShort(a.cw.newUTF8("LocalVariableTable"));
               a.putInt(a.localVar.length + 2).putShort(a.localVarCount);
               a.putByteArray(a.localVar.data, 0, a.localVar.length);
            }

            if (a.localVarType != null) {
               a.putShort(a.cw.newUTF8("LocalVariableTypeTable"));
               a.putInt(a.localVarType.length + 2).putShort(a.localVarTypeCount);
               a.putByteArray(a.localVarType.data, 0, a.localVarType.length);
            }

            if (a.lineNumber != null) {
               a.putShort(a.cw.newUTF8("LineNumberTable"));
               a.putInt(a.lineNumber.length + 2).putShort(a.lineNumberCount);
               a.putByteArray(a.lineNumber.data, 0, a.lineNumber.length);
            }

            if (a.stackMap != null) {
               boolean a = (a.cw.version & '\uffff') >= 50;
               a.putShort(a.cw.newUTF8(a ? "StackMapTable" : "StackMap"));
               a.putInt(a.stackMap.length + 2).putShort(a.frameCount);
               a.putByteArray(a.stackMap.data, 0, a.stackMap.length);
            }

            if (a.ctanns != null) {
               a.putShort(a.cw.newUTF8("RuntimeVisibleTypeAnnotations"));
               a.ctanns.put(a);
            }

            if (a.ictanns != null) {
               a.putShort(a.cw.newUTF8("RuntimeInvisibleTypeAnnotations"));
               a.ictanns.put(a);
            }

            if (a.cattrs != null) {
               a.cattrs.put(a.cw, a.code.data, a.code.length, a.maxLocals, a.maxStack, a);
            }
         }

         if (a.exceptionCount > 0) {
            a.putShort(a.cw.newUTF8("Exceptions")).putInt(2 * a.exceptionCount + 2);
            a.putShort(a.exceptionCount);

            for(a = 0; a < a.exceptionCount; ++a) {
               a.putShort(a.exceptions[a]);
            }
         }

         if ((a.access & 4096) != 0 && ((a.cw.version & '\uffff') < 49 || (a.access & 262144) != 0)) {
            a.putShort(a.cw.newUTF8("Synthetic")).putInt(0);
         }

         if ((a.access & 131072) != 0) {
            a.putShort(a.cw.newUTF8("Deprecated")).putInt(0);
         }

         if (a.signature != null) {
            a.putShort(a.cw.newUTF8("Signature")).putInt(2).putShort(a.cw.newUTF8(a.signature));
         }

         if (a.methodParameters != null) {
            a.putShort(a.cw.newUTF8("MethodParameters"));
            a.putInt(a.methodParameters.length + 1).putByte(a.methodParametersCount);
            a.putByteArray(a.methodParameters.data, 0, a.methodParameters.length);
         }

         if (a.annd != null) {
            a.putShort(a.cw.newUTF8("AnnotationDefault"));
            a.putInt(a.annd.length);
            a.putByteArray(a.annd.data, 0, a.annd.length);
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

         if (a.panns != null) {
            a.putShort(a.cw.newUTF8("RuntimeVisibleParameterAnnotations"));
            AnnotationWriter.put(a.panns, a.synthetics, a);
         }

         if (a.ipanns != null) {
            a.putShort(a.cw.newUTF8("RuntimeInvisibleParameterAnnotations"));
            AnnotationWriter.put(a.ipanns, a.synthetics, a);
         }

         if (a.attrs != null) {
            a.attrs.put(a.cw, (byte[])null, 0, -1, -1, a);
         }

      }
   }
}
