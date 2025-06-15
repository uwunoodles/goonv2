package org.spongepowered.asm.lib;

final class Item {
   int index;
   int type;
   int intVal;
   long longVal;
   String strVal1;
   String strVal2;
   String strVal3;
   int hashCode;
   Item next;

   Item() {
   }

   Item(int a) {
      a.index = a;
   }

   Item(int a, Item a) {
      a.index = a;
      a.type = a.type;
      a.intVal = a.intVal;
      a.longVal = a.longVal;
      a.strVal1 = a.strVal1;
      a.strVal2 = a.strVal2;
      a.strVal3 = a.strVal3;
      a.hashCode = a.hashCode;
   }

   void set(int a) {
      a.type = 3;
      a.intVal = a;
      a.hashCode = Integer.MAX_VALUE & a.type + a;
   }

   void set(long a) {
      a.type = 5;
      a.longVal = a;
      a.hashCode = Integer.MAX_VALUE & a.type + (int)a;
   }

   void set(float a) {
      a.type = 4;
      a.intVal = Float.floatToRawIntBits(a);
      a.hashCode = Integer.MAX_VALUE & a.type + (int)a;
   }

   void set(double a) {
      a.type = 6;
      a.longVal = Double.doubleToRawLongBits(a);
      a.hashCode = Integer.MAX_VALUE & a.type + (int)a;
   }

   void set(int a, String a, String a, String a) {
      a.type = a;
      a.strVal1 = a;
      a.strVal2 = a;
      a.strVal3 = a;
      switch(a) {
      case 7:
         a.intVal = 0;
      case 1:
      case 8:
      case 16:
      case 30:
         a.hashCode = Integer.MAX_VALUE & a + a.hashCode();
         return;
      case 12:
         a.hashCode = Integer.MAX_VALUE & a + a.hashCode() * a.hashCode();
         return;
      default:
         a.hashCode = Integer.MAX_VALUE & a + a.hashCode() * a.hashCode() * a.hashCode();
      }
   }

   void set(String a, String a, int a) {
      a.type = 18;
      a.longVal = (long)a;
      a.strVal1 = a;
      a.strVal2 = a;
      a.hashCode = Integer.MAX_VALUE & 18 + a * a.strVal1.hashCode() * a.strVal2.hashCode();
   }

   void set(int a, int a) {
      a.type = 33;
      a.intVal = a;
      a.hashCode = a;
   }

   boolean isEqualTo(Item a) {
      switch(a.type) {
      case 1:
      case 7:
      case 8:
      case 16:
      case 30:
         return a.strVal1.equals(a.strVal1);
      case 2:
      case 9:
      case 10:
      case 11:
      case 13:
      case 14:
      case 15:
      case 17:
      case 19:
      case 20:
      case 21:
      case 22:
      case 23:
      case 24:
      case 25:
      case 26:
      case 27:
      case 28:
      case 29:
      default:
         return a.strVal1.equals(a.strVal1) && a.strVal2.equals(a.strVal2) && a.strVal3.equals(a.strVal3);
      case 3:
      case 4:
         return a.intVal == a.intVal;
      case 5:
      case 6:
      case 32:
         return a.longVal == a.longVal;
      case 12:
         return a.strVal1.equals(a.strVal1) && a.strVal2.equals(a.strVal2);
      case 18:
         return a.longVal == a.longVal && a.strVal1.equals(a.strVal1) && a.strVal2.equals(a.strVal2);
      case 31:
         return a.intVal == a.intVal && a.strVal1.equals(a.strVal1);
      }
   }
}
