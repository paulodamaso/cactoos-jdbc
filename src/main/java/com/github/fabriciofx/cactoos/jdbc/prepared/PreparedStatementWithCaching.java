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
package com.github.fabriciofx.cactoos.jdbc.prepared;

import com.github.fabriciofx.cactoos.jdbc.rset.ResultSetWithCaching;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public final class PreparedStatementWithCaching extends
    PreparedStatementEnvelope {
    private final Map<String, ResultSet> cache;
    private final String query;
    private final String[] columns;

    public PreparedStatementWithCaching(
        final PreparedStatement prepared,
        final Map<String, ResultSet> cch,
        final String sql,
        final String... cols
    ) {
        super(prepared);
        this.query = sql;
        this.cache = cch;
        this.columns = cols;
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        if (!this.cache.containsKey(this.query)) {
            final RowSetFactory rsf = RowSetProvider.newFactory();
            final CachedRowSet crs = rsf.createCachedRowSet();
            try (ResultSet rset = super.executeQuery()) {
                crs.populate(rset);
            }
            this.cache.put(this.query, crs);
            return new ResultSetWithCaching(crs, this.columns);
        } else {
            final ResultSet cached = this.cache.get(this.query);
            return new ResultSetWithCaching(cached, this.columns);
        }
    }

    @Override
    public ResultSet executeQuery(final String sql) throws SQLException {
        final ResultSet rset;
        if (this.cache.containsKey(sql)) {
            rset = this.cache.get(sql);
        } else {
            rset = super.executeQuery(sql);
            this.cache.put(sql, rset);
        }
        return rset;
    }
}
