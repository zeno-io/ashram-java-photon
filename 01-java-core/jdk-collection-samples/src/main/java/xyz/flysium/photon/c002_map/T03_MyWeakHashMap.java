/*
 * MIT License
 *
 * Copyright (c) 2020 SvenAugustus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package xyz.flysium.photon.c002_map;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import xyz.flysium.photon.c002_map.T03_MyWeakHashMap.MyNotCorrectWeakHashMap.MyEntry0;
import xyz.flysium.photon.c002_map.T03_MyWeakHashMap.MySampleWeakHashMap.MyEntry;

/**
 * sample implementations for WeakHashMap
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class T03_MyWeakHashMap {

    public static void main(String[] args) throws Throwable {
        Object o = new Object();
        final int index = hash(o) & 15;

        MyNotCorrectWeakHashMap notCorrectWeakHashMap = new MyNotCorrectWeakHashMap(16);
        notCorrectWeakHashMap.put(1, "1");
        notCorrectWeakHashMap.put(128, "128");
        notCorrectWeakHashMap.put(o, "o");

        MySampleWeakHashMap sampleWeakHashMap = new MySampleWeakHashMap(16);
        sampleWeakHashMap.put(1, "1");
        sampleWeakHashMap.put(128, "128");
        sampleWeakHashMap.put(o, "o");

        WeakHashMap<Object, Object> jdkMap = new WeakHashMap<Object, Object>(16);
        jdkMap.put(1, "1");
        jdkMap.put(128, "128");
        jdkMap.put(o, "o");

        System.out.println(
            "MyNotCorrectWeakHashMap o -> " + getEntryForMyNotCorrectWeakHashMap(notCorrectWeakHashMap, index).get());
        System.out.println("MySampleWeakHashMap o -> " + getEntryForMySampleWeakHashMap(sampleWeakHashMap, index));
        System.out.println("WeakHashMap o -> " + getEntryForWeakHashMap(jdkMap, index));

        o = null;
        System.gc();
        TimeUnit.SECONDS.sleep(1);
        // TODO TO invoke WeakHashMap#expungeStaleEntries (value = null ) help GC
        jdkMap.get(null);

        System.out.println("after gc MyNotCorrectWeakHashMap 1 -> " + notCorrectWeakHashMap.get(1));
        System.out.println("after gc MySampleWeakHashMap  1 -> " + sampleWeakHashMap.get(1));
        System.out.println("after gc WeakHashMap  1 -> " + jdkMap.get(1));
        System.out.println("after gc MyNotCorrectWeakHashMap  128 -> " + notCorrectWeakHashMap.get(128));
        System.out.println("after gc MySampleWeakHashMap  128 -> " + sampleWeakHashMap.get(128));
        System.out.println("after gc WeakHashMap  128 -> " + jdkMap.get(128));
        System.out.println(
            "after gc MyNotCorrectWeakHashMap o -> " + getEntryForMyNotCorrectWeakHashMap(notCorrectWeakHashMap, index)
                .get());
        System.out
            .println("after gc MySampleWeakHashMap o -> " + getEntryForMySampleWeakHashMap(sampleWeakHashMap, index));
        System.out.println("after gc WeakHashMap o -> " + getEntryForWeakHashMap(jdkMap, index));
    }

    static WeakReference<MyEntry0> getEntryForMyNotCorrectWeakHashMap(MyNotCorrectWeakHashMap m, int index)
        throws IllegalAccessException, NoSuchFieldException {
        VarHandle varHandle = MethodHandles.privateLookupIn(MyNotCorrectWeakHashMap.class, MethodHandles.lookup())
            .findVarHandle(MyNotCorrectWeakHashMap.class, "tab", WeakReference[].class);

        WeakReference<MyEntry0>[] tab = (WeakReference<MyEntry0>[]) varHandle.get(m);
        return tab[index];
    }

    static MyEntry getEntryForMySampleWeakHashMap(MySampleWeakHashMap m, int index)
        throws IllegalAccessException, NoSuchFieldException {
        VarHandle varHandle = MethodHandles.privateLookupIn(MySampleWeakHashMap.class, MethodHandles.lookup())
            .findVarHandle(MySampleWeakHashMap.class, "tab", MyEntry[].class);

        MyEntry[] tab = (MyEntry[]) varHandle.get(m);
        return tab[index];
    }

    static Entry<?, ?> getEntryForWeakHashMap(WeakHashMap<?, ?> m, int index)
        throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        //        Class<?> weakHashMapEntryClass = Class.forName("java.util.WeakHashMap$Entry");
        //        VarHandle varHandle = MethodHandles.privateLookupIn(WeakHashMap.class, MethodHandles.lookup())
        //            .findVarHandle(WeakHashMap.class, "table", weakHashMapEntryClass);
        //        Entry[] tab = (Entry[]) varHandle.get(m);
        Field field = WeakHashMap.class.getDeclaredField("table");
        field.setAccessible(true);
        Entry<?, ?>[] tab = (Entry<?, ?>[]) field.get(m);
        return tab[index];
    }

    static int hash(Object k) {
        int h = k.hashCode();

        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    @SuppressWarnings("unchecked")
    static class MyNotCorrectWeakHashMap {

        private int capacity = 16;

        private final double loadFactor = 0.75;

        public MyNotCorrectWeakHashMap(int capacity) {
            this.capacity = capacity;
        }

        // TODO not correct implementation for WeakHashMap
        private final WeakReference<MyEntry0>[] tab = new WeakReference[capacity];

        public void put(Object key, Object value) {
            final int i = hashFor(key);
            MyEntry0 e = new MyEntry0(key);
            e.value = value;

            WeakReference<MyEntry0> ref = tab[i];
            if (ref == null) {
                tab[i] = new WeakReference<>(e);
                return;
            }
            MyEntry0 entry = ref.get();
            if (entry == null) {
                entry = e;
                // TODO ??
                tab[i] = new WeakReference<>(e);
                return;
            }
            MyEntry0 last = entry;
            MyEntry0 p = entry.next;
            while (p != null) {
                last = p;
                p = p.next;
            }
            last.next = e;
        }

        public Object get(Object key) {
            final int i = hashFor(key);
            WeakReference<MyEntry0> ref = tab[i];
            if (ref == null) {
                return null;
            }
            MyEntry0 entry = ref.get();
            if (entry == null) {
                return null;
            }
            if (Objects.equals(key, entry.getKey())) {
                return entry.value;
            }
            MyEntry0 p = entry.next;
            while (p != null) {
                if (Objects.equals(key, p.getKey())) {
                    return p.value;
                }
                p = p.next;
            }
            return null;
        }

        private int hashFor(Object key) {
            if (key == null) {
                return 0;
            }
            return hash(key) & (capacity - 1);
        }

        static class MyEntry0 {
            private Object key;

            private Object value;

            private MyEntry0 next;

            public MyEntry0(Object key) {
                this.key = key;
            }

            public Object getKey() {
                return key;
            }

            @Override
            public String toString() {
                final StringBuffer sb = new StringBuffer("MyEntry0{");
                sb.append("key=").append(key);
                sb.append(", value=").append(value);
                sb.append(", next=").append(next);
                sb.append('}');
                return sb.toString();
            }
        }

    }

    static class MySampleWeakHashMap {

        private int capacity = 16;

        private final double loadFactor = 0.75;

        private final MyEntry[] tab = new MyEntry[capacity];

        public MySampleWeakHashMap(int capacity) {
            this.capacity = capacity;
        }

        public void put(Object key, Object value) {
            // TODO should TO invoke WeakHashMap#expungeStaleEntries (value = null ) help GC
            final int i = hashFor(key);
            MyEntry e = new MyEntry(key);
            e.value = value;

            MyEntry entry = tab[i];
            if (entry == null) {
                tab[i] = e;
                return;
            }
            MyEntry last = entry;
            MyEntry p = entry.next;
            while (p != null) {
                last = p;
                p = p.next;
            }
            last.next = e;
        }

        public Object get(Object key) {
            // TODO should TO invoke WeakHashMap#expungeStaleEntries (value = null ) help GC
            final int i = hashFor(key);

            MyEntry entry = tab[i];
            if (entry == null) {
                return null;
            }
            if (Objects.equals(key, entry.getKey())) {
                return entry.value;
            }
            MyEntry p = entry.next;
            while (p != null) {
                if (Objects.equals(key, p.getKey())) {
                    return p.value;
                }
                p = p.next;
            }
            return null;
        }

        private int hashFor(Object key) {
            if (key == null) {
                return 0;
            }
            return hash(key) & (capacity - 1);
        }

        static class MyEntry extends WeakReference<Object> {

            private Object value;

            private MyEntry next;

            public MyEntry(Object key) {
                super(key);
            }

            public Object getKey() {
                return super.get();
            }

            @Override
            public String toString() {
                final StringBuffer sb = new StringBuffer("MyEntry{");
                sb.append("key=").append(getKey());
                sb.append(",value=").append(value);
                sb.append(", next=").append(next);
                sb.append('}');
                return sb.toString();
            }
        }

    }

}
