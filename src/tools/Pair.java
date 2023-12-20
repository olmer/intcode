package tools;

import java.io.Serializable;

public class Pair<K, V> implements Serializable {
  /*
   * Copyright (c) 2010, 2015, Oracle and/or its affiliates. All rights reserved.
   * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
   *
   * This code is free software; you can redistribute it and/or modify it
   * under the terms of the GNU General Public License version 2 only, as
   * published by the Free Software Foundation.  Oracle designates this
   * particular file as subject to the "Classpath" exception as provided
   * by Oracle in the LICENSE file that accompanied this code.
   *
   * This code is distributed in the hope that it will be useful, but WITHOUT
   * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
   * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
   * version 2 for more details (a copy is included in the LICENSE file that
   * accompanied this code).
   *
   * You should have received a copy of the GNU General Public License version
   * 2 along with this work; if not, write to the Free Software Foundation,
   * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
   *
   * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
   * or visit www.oracle.com if you need additional information or have any
   * questions.
   */

  /**
     * Key of this <code>tools.Pair</code>.
     */
    private K key;

    /**
     * Gets the key for this pair.
     * @return key for this pair
     */
    public K getKey() {
      return key;
    }

    /**
     * Value of this this <code>tools.Pair</code>.
     */
    private V value;

    /**
     * Gets the value for this pair.
     * @return value for this pair
     */
    public V getValue() {
      return value;
    }

    /**
     * Creates a new pair
     * @param key The key for this pair
     * @param value The value to use for this pair
     */
    public Pair( K key,  V value) {
      this.key = key;
      this.value = value;
    }

    /**
     * <p><code>String</code> representation of this
     * <code>tools.Pair</code>.</p>
     *
     * <p>The default name/value delimiter '=' is always used.</p>
     *
     *  @return <code>String</code> representation of this <code>tools.Pair</code>
     */
    @Override
    public String toString() {
      return "[" + key + ":" + value + "]";
    }

    /**
     * <p>Generate a hash code for this <code>tools.Pair</code>.</p>
     *
     * <p>The hash code is calculated using both the name and
     * the value of the <code>tools.Pair</code>.</p>
     *
     * @return hash code for this <code>tools.Pair</code>
     */
    @Override
    public int hashCode() {
      int hash = 7;
      hash = 31 * hash + (key != null ? key.hashCode() : 0);
      hash = 31 * hash + (value != null ? value.hashCode() : 0);
      return hash;
    }

    /**
     * <p>Test this <code>tools.Pair</code> for equality with another
     * <code>Object</code>.</p>
     *
     * <p>If the <code>Object</code> to be tested is not a
     * <code>tools.Pair</code> or is <code>null</code>, then this method
     * returns <code>false</code>.</p>
     *
     * <p>Two <code>tools.Pair</code>s are considered equal if and only if
     * both the names and values are equal.</p>
     *
     * @param o the <code>Object</code> to test for
     * equality with this <code>tools.Pair</code>
     * @return <code>true</code> if the given <code>Object</code> is
     * equal to this <code>tools.Pair</code> else <code>false</code>
     */
    @Override
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (o instanceof Pair) {
        Pair pair = (Pair) o;
        if (key != null ? !key.equals(pair.key) : pair.key != null)
          return false;
        if (value != null ? !value.equals(pair.value) : pair.value != null)
          return false;
        return true;
      }
      return false;
    }

    public Pair<K, V> setKey(K k) {
      this.key = k;
      return this;
    }

    public Pair<K, V> setValue(V v) {
      this.value = v;
      return this;
    }
}
