/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2018 Fabrício Barros Cabral
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.fabriciofx.cactoos.jdbc.cache;

import java.sql.ResultSetMetaData;
import java.util.LinkedHashMap;
import java.util.Map;
import org.cactoos.scalar.StickyScalar;
import org.cactoos.scalar.UncheckedScalar;

public final class Columns {
    private final UncheckedScalar<Map<String, Boolean>> map;

    public Columns(final ResultSetMetaData rsmd, final String... names) {
        this.map = new UncheckedScalar<>(
            new StickyScalar<>(
                () -> {
                    final Map<String, Boolean> map = new LinkedHashMap<>();
                    final int count = rsmd.getColumnCount();
                    for (int idx = 1; idx <= count; idx++) {
                        final String label = rsmd.getColumnLabel(idx);
                        boolean has = false;
                        for (final String name : names) {
                            if (label.equals(name)) {
                                has = true;
                                break;
                            }
                        }
                        map.put(label, has);
                    }
                    return map;
                }
            )
        );
    }

    public String column(final int index) {
        return this.map.value().keySet().toArray(new String[0])[index];
    }

    public int count() {
        return this.map.value().size();
    }

    public boolean valid(final int column) {
        return this.contains(this.column(column - 1));
    }

    public boolean contains(final String... names) {
        boolean result = true;
        for (final String name : names) {
            result &= this.map.value().getOrDefault(name, false);
        }
        return result;
    }
}
