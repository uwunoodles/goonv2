package org.spongepowered.asm.mixin.struct;

import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;
import org.spongepowered.asm.util.Bytecode;

public abstract class MemberRef {
   private static final int[] H_OPCODES = new int[]{0, 180, 178, 181, 179, 182, 184, 183, 183, 185};

   public abstract boolean isField();

   public abstract int getOpcode();

   public abstract void setOpcode(int var1);

   public abstract String getOwner();

   public abstract void setOwner(String var1);

   public abstract String getName();

   public abstract void setName(String var1);

   public abstract String getDesc();

   public abstract void setDesc(String var1);

   public String toString() {
      String a = Bytecode.getOpcodeName(a.getOpcode());
      return String.format("%s for %s.%s%s%s", a, a.getOwner(), a.getName(), a.isField() ? ":" : "", a.getDesc());
   }

   public boolean equals(Object a) {
      if (!(a instanceof MemberRef)) {
         return false;
      } else {
         MemberRef a = (MemberRef)a;
         return a.getOpcode() == a.getOpcode() && a.getOwner().equals(a.getOwner()) && a.getName().equals(a.getName()) && a.getDesc().equals(a.getDesc());
      }
   }

   public int hashCode() {
      return a.toString().hashCode();
   }

   static int opcodeFromTag(int a) {
      return a >= 0 && a < H_OPCODES.length ? H_OPCODES[a] : 0;
   }

   static int tagFromOpcode(int a) {
      for(int a = 1; a < H_OPCODES.length; ++a) {
         if (H_OPCODES[a] == a) {
            return a;
         }
      }

      return 0;
   }

   public static final class Handle extends MemberRef {
      private org.spongepowered.asm.lib.Handle handle;

      public Handle(org.spongepowered.asm.lib.Handle a) {
         a.handle = a;
      }

      public org.spongepowered.asm.lib.Handle getMethodHandle() {
         return a.handle;
      }

      public boolean isField() {
         switch(a.handle.getTag()) {
         case 1:
         case 2:
         case 3:
         case 4:
            return true;
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
            return false;
         default:
            throw new MixinTransformerError("Invalid tag " + a.handle.getTag() + " for method handle " + a.handle + ".");
         }
      }

      public int getOpcode() {
         int a = MemberRef.opcodeFromTag(a.handle.getTag());
         if (a == 0) {
            throw new MixinTransformerError("Invalid tag " + a.handle.getTag() + " for method handle " + a.handle + ".");
         } else {
            return a;
         }
      }

      public void setOpcode(int a) {
         int a = MemberRef.tagFromOpcode(a);
         if (a == 0) {
            throw new MixinTransformerError("Invalid opcode " + Bytecode.getOpcodeName(a) + " for method handle " + a.handle + ".");
         } else {
            boolean a = a == 9;
            a.handle = new org.spongepowered.asm.lib.Handle(a, a.handle.getOwner(), a.handle.getName(), a.handle.getDesc(), a);
         }
      }

      public String getOwner() {
         return a.handle.getOwner();
      }

      public void setOwner(String a) {
         boolean a = a.handle.getTag() == 9;
         a.handle = new org.spongepowered.asm.lib.Handle(a.handle.getTag(), a, a.handle.getName(), a.handle.getDesc(), a);
      }

      public String getName() {
         return a.handle.getName();
      }

      public void setName(String a) {
         boolean a = a.handle.getTag() == 9;
         a.handle = new org.spongepowered.asm.lib.Handle(a.handle.getTag(), a.handle.getOwner(), a, a.handle.getDesc(), a);
      }

      public String getDesc() {
         return a.handle.getDesc();
      }

      public void setDesc(String a) {
         boolean a = a.handle.getTag() == 9;
         a.handle = new org.spongepowered.asm.lib.Handle(a.handle.getTag(), a.handle.getOwner(), a.handle.getName(), a, a);
      }
   }

   public static final class Field extends MemberRef {
      private static final int OPCODES = 183;
      public final FieldInsnNode insn;

      public Field(FieldInsnNode a) {
         a.insn = a;
      }

      public boolean isField() {
         return true;
      }

      public int getOpcode() {
         return a.insn.getOpcode();
      }

      public void setOpcode(int a) {
         if ((a & 183) == 0) {
            throw new IllegalArgumentException("Invalid opcode for field instruction: 0x" + Integer.toHexString(a));
         } else {
            a.insn.setOpcode(a);
         }
      }

      public String getOwner() {
         return a.insn.owner;
      }

      public void setOwner(String a) {
         a.insn.owner = a;
      }

      public String getName() {
         return a.insn.name;
      }

      public void setName(String a) {
         a.insn.name = a;
      }

      public String getDesc() {
         return a.insn.desc;
      }

      public void setDesc(String a) {
         a.insn.desc = a;
      }
   }

   public static final class Method extends MemberRef {
      private static final int OPCODES = 191;
      public final MethodInsnNode insn;

      public Method(MethodInsnNode a) {
         a.insn = a;
      }

      public boolean isField() {
         return false;
      }

      public int getOpcode() {
         return a.insn.getOpcode();
      }

      public void setOpcode(int a) {
         if ((a & 191) == 0) {
            throw new IllegalArgumentException("Invalid opcode for method instruction: 0x" + Integer.toHexString(a));
         } else {
            a.insn.setOpcode(a);
         }
      }

      public String getOwner() {
         return a.insn.owner;
      }

      public void setOwner(String a) {
         a.insn.owner = a;
      }

      public String getName() {
         return a.insn.name;
      }

      public void setName(String a) {
         a.insn.name = a;
      }

      public String getDesc() {
         return a.insn.desc;
      }

      public void setDesc(String a) {
         a.insn.desc = a;
      }
   }
}
