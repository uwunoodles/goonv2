package org.spongepowered.asm.util;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class VersionNumber implements Comparable<VersionNumber>, Serializable {
   private static final long serialVersionUID = 1L;
   public static final VersionNumber NONE = new VersionNumber();
   private static final Pattern PATTERN = Pattern.compile("^(\\d{1,5})(?:\\.(\\d{1,5})(?:\\.(\\d{1,5})(?:\\.(\\d{1,5}))?)?)?(-[a-zA-Z0-9_\\-]+)?$");
   private final long value;
   private final String suffix;

   private VersionNumber() {
      a.value = 0L;
      a.suffix = "";
   }

   private VersionNumber(short[] a) {
      this(a, (String)null);
   }

   private VersionNumber(short[] a, String a) {
      a.value = pack(a);
      a.suffix = a != null ? a : "";
   }

   private VersionNumber(short a, short a, short a, short a) {
      this(a, a, a, a, (String)null);
   }

   private VersionNumber(short a, short a, short a, short a, String a) {
      a.value = pack(a, a, a, a);
      a.suffix = a != null ? a : "";
   }

   public String toString() {
      short[] a = unpack(a.value);
      return String.format("%d.%d%3$s%4$s%5$s", a[0], a[1], (a.value & 2147483647L) > 0L ? String.format(".%d", a[2]) : "", (a.value & 32767L) > 0L ? String.format(".%d", a[3]) : "", a.suffix);
   }

   public int compareTo(VersionNumber a) {
      if (a == null) {
         return 1;
      } else {
         long a = a.value - a.value;
         return a > 0L ? 1 : (a < 0L ? -1 : 0);
      }
   }

   public boolean equals(Object a) {
      if (!(a instanceof VersionNumber)) {
         return false;
      } else {
         return ((VersionNumber)a).value == a.value;
      }
   }

   public int hashCode() {
      return (int)(a.value >> 32) ^ (int)(a.value & 4294967295L);
   }

   private static long pack(short... a) {
      return (long)a[0] << 48 | (long)a[1] << 32 | (long)(a[2] << 16) | (long)a[3];
   }

   private static short[] unpack(long a) {
      return new short[]{(short)((int)(a >> 48)), (short)((int)(a >> 32 & 32767L)), (short)((int)(a >> 16 & 32767L)), (short)((int)(a & 32767L))};
   }

   public static VersionNumber parse(String a) {
      return parse(a, NONE);
   }

   public static VersionNumber parse(String a, String a) {
      return parse(a, parse(a));
   }

   private static VersionNumber parse(String a, VersionNumber a) {
      if (a == null) {
         return a;
      } else {
         Matcher a = PATTERN.matcher(a);
         if (!a.matches()) {
            return a;
         } else {
            short[] a = new short[4];

            for(int a = 0; a < 4; ++a) {
               String a = a.group(a + 1);
               if (a != null) {
                  int a = Integer.parseInt(a);
                  if (a > 32767) {
                     throw new IllegalArgumentException("Version parts cannot exceed 32767, found " + a);
                  }

                  a[a] = (short)a;
               }
            }

            return new VersionNumber(a, a.group(5));
         }
      }
   }
}
