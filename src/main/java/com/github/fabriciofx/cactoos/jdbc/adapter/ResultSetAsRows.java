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
package com.github.fabriciofx.cactoos.jdbc.adapter;

import com.github.fabriciofx.cactoos.jdbc.Rows;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.cactoos.scalar.StickyScalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Rows as ResultSet.
 *
 * @since 0.1
 */
public final class ResultSetAsRows implements Rows {
    /**
     * Rows.
     */
    private final UncheckedScalar<List<Map<String, Object>>> rows;

    /**
     * Ctor.
     * @param rst A ResultSet
     */
    public ResultSetAsRows(final ResultSet rst) {
        this.rows = new UncheckedScalar<>(
            new StickyScalar<>(
                () -> {
                    final List<Map<String, Object>> rws = new LinkedList<>();
                    final ResultSetMetaData rsmd = rst.getMetaData();
                    final int cols = rsmd.getColumnCount();
                    while (rst.next()) {
                        final Map<String, Object> flds = new LinkedHashMap<>();
                        for (int idx = 1; idx <= cols; ++idx) {
                            final String name = rsmd.getColumnName(idx)
                                .toLowerCase(Locale.ENGLISH);
                            final Object data = rst.getObject(idx);
                            flds.put(name, data);
                        }
                        rws.add(flds);
                    }
                    return rws;
                }
            )
        );
    }

    @Override
    public Iterator<Map<String, Object>> iterator() {
        return new UncheckedScalar<>(this.rows).value().iterator();
    }

    @Override
    public int count() {
        return this.rows.value().size();
    }

    @Override
    public Map<String, Object> row(final int index) {
        return this.rows.value().get(index);
    }
}
