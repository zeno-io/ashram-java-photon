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

package xyz.flysium.photon.list;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public final class NodeSupport {

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private NodeSupport() {
    }

    protected static int randomValue(int maxValue) {
        return RANDOM.nextInt(maxValue);
    }

    public static Node<?> generateLinkedList(int... values) {
        if (values == null || values.length == 0) {
            return null;
        }
        Node<?> head = new Node<>(values[0], null);
        Node<?> curr = head;

        for (int i = 1; i < values.length; i++) {
            curr.next = new Node(values[i], null);
            curr = curr.next;
        }
        return head;
    }

    public static Node<?> generateRandomLinkedList(int length, int maxValue) {
        if (length <= 0) {
            return null;
        }
        Node<?> head = new Node<>(randomValue(maxValue), null);
        Node<?> curr = head;

        for (int i = 1; i < length; i++) {
            curr.next = new Node(randomValue(maxValue), null);
            curr = curr.next;
        }
        return head;
    }

    public static DoubleNode<?> generateRandomDoubleLinkedList(int length, int maxValue) {
        if (length <= 0) {
            return null;
        }
        DoubleNode<?> head = new DoubleNode<>(randomValue(maxValue), null, null);
        DoubleNode<?> curr = head;

        for (int i = 1; i < length; i++) {
            curr.next = new DoubleNode(randomValue(maxValue), curr, null);
            curr = curr.next;
        }
        return head;
    }

    protected static boolean valueNotEquals(Node<?> d1, Node<?> d2) {
        if (d1 == null) {
            return d2 != null;
        }
        return !Objects.equals(d1.value, d2.value);
    }

    protected static boolean valueNotEquals(DoubleNode<?> d1, DoubleNode<?> d2) {
        if (d1 == null) {
            return d2 == null;
        }
        return !Objects.equals(d1.value, d2.value);
    }

    // 要求无环
    public static boolean equals(Node<?> head1, Node<?> head2) {
        Node<?> p1 = head1;
        Node<?> p2 = head2;
        while (p1 != null && p2 != null) {
            if (valueNotEquals(p1, p2)) {
                return false;
            }
            p1 = p1.next;
            p2 = p2.next;
        }
        return p1 == null && p2 == null;
    }

    // 要求无环
    public static boolean equals(DoubleNode<?> head1, DoubleNode<?> head2) {
        DoubleNode<?> p1 = head1;
        DoubleNode<?> p2 = head2;
        DoubleNode<?> prev1 = null;
        DoubleNode<?> prev2 = null;
        while (p1 != null && p2 != null) {
            if (valueNotEquals(p1, p2)) {
                return false;
            }
            prev1 = p1;
            prev2 = p2;
            p1 = p1.next;
            p2 = p2.next;
        }
        if (p1 != null || p2 != null) {
            return false;
        }
        p1 = prev1;
        p2 = prev2;

        while (p1 != null && p2 != null) {
            if (valueNotEquals(p1, p2)) {
                return false;
            }
            p1 = p1.prev;
            p2 = p2.prev;
        }
        return p1 == null && p2 == null;
    }

    public static String toString(Node<?> head) {
        StringBuilder buf = new StringBuilder();
        Node<?> curr = head;
        String pre = "";
        while (curr != null) {
            buf.append(pre).append(curr.value);
            pre = ",";
            curr = curr.next;
        }
        return buf.toString();
    }

    public static String toString(DoubleNode<?> head) {
        StringBuilder buf = new StringBuilder();
        DoubleNode<?> curr = head;
        String pre = "";
        while (curr != null) {
            buf.append(pre).append(curr.value);
            pre = ",";
            curr = curr.next;
        }
        return buf.toString();
    }

}
