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
import java.sql.SQLException;

public final class CachedResultSetMetada implements ResultSetMetaData {
    private final ResultSetMetaData origin;
    private final Columns columns;

    public CachedResultSetMetada(
        final ResultSetMetaData rsmd,
        final String... cols
    ) {
        this.origin = rsmd;
        this.columns = new Columns(rsmd, cols);
    }

    @Override
    public int getColumnCount() throws SQLException {
        return this.columns.count();
    }

    @Override
    public boolean isAutoIncrement(final int column) throws SQLException {
        final boolean result;
        if (this.columns.valid(column)) {
            result = this.origin.isAutoIncrement(column);
        } else {
            result = false;
        }
        return result;
    }

    @Override
    public boolean isCaseSensitive(final int column) throws SQLException {
        final boolean result;
        if (this.columns.valid(column)) {
            result = this.origin.isCaseSensitive(column);
        } else {
            result = false;
        }
        return result;
    }

    @Override
    public boolean isSearchable(final int column) throws SQLException {
        final boolean result;
        if (this.columns.valid(column)) {
            result = this.origin.isSearchable(column);
        } else {
            result = false;
        }
        return result;
    }

    @Override
    public boolean isCurrency(final int column) throws SQLException {
        final boolean result;
        if (this.columns.valid(column)) {
            result = this.origin.isCurrency(column);
        } else {
            result = false;
        }
        return result;
    }

    @Override
    public int isNullable(final int column) throws SQLException {
        final int result;
        if (this.columns.valid(column)) {
            result = this.origin.isNullable(column);
        } else {
            result = 0;
        }
        return result;
    }

    @Override
    public boolean isSigned(final int column) throws SQLException {
        final boolean result;
        if (this.columns.valid(column)) {
            result = this.origin.isSigned(column);
        } else {
            result = false;
        }
        return result;
    }

    @Override
    public int getColumnDisplaySize(final int column) throws SQLException {
        return this.columns.column(column - 1).length();
    }

    @Override
    public String getColumnLabel(final int column) throws SQLException {
        return this.columns.column(column - 1);
    }

    @Override
    public String getColumnName(final int column) throws SQLException {
        return this.columns.column(column - 1);
    }

    @Override
    public String getSchemaName(final int column) throws SQLException {
        return this.columns.column(column - 1);
    }

    @Override
    public int getPrecision(final int column) throws SQLException {
        final int result;
        if (this.columns.valid(column)) {
            result = this.origin.getPrecision(column);
        } else {
            result = 0;
        }
        return result;
    }

    @Override
    public int getScale(final int column) throws SQLException {
        final int result;
        if (this.columns.valid(column)) {
            result = this.origin.getScale(column);
        } else {
            result = 0;
        }
        return result;
    }

    @Override
    public String getTableName(final int column) throws SQLException {
        final String result;
        if (this.columns.valid(column)) {
            result = this.origin.getTableName(column);
        } else {
            result = "";
        }
        return result;
    }

    @Override
    public String getCatalogName(final int column) throws SQLException {
        final String result;
        if (this.columns.valid(column)) {
            result = this.origin.getCatalogName(column);
        } else {
            result = "";
        }
        return result;
    }

    @Override
    public int getColumnType(final int column) throws SQLException {
        final int result;
        if (this.columns.valid(column)) {
            result = this.origin.getColumnType(column);
        } else {
            result = 0;
        }
        return result;
    }

    @Override
    public String getColumnTypeName(final int column) throws SQLException {
        final String result;
        if (this.columns.valid(column)) {
            result = this.origin.getColumnTypeName(column);
        } else {
            result = "";
        }
        return result;
    }

    @Override
    public boolean isReadOnly(final int column) throws SQLException {
        return true;
    }

    @Override
    public boolean isWritable(final int column) throws SQLException {
        return false;
    }

    @Override
    public boolean isDefinitelyWritable(final int column) throws SQLException {
        return false;
    }

    @Override
    public String getColumnClassName(final int column) throws SQLException {
        final String result;
        if (this.columns.valid(column)) {
            result = this.origin.getColumnClassName(column);
        } else {
            result = "";
        }
        return result;
    }

    @Override
    public <T> T unwrap(final Class<T> iface) throws SQLException {
        return this.origin.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(final Class<?> iface) throws SQLException {
        return this.origin.isWrapperFor(iface);
    }
}
