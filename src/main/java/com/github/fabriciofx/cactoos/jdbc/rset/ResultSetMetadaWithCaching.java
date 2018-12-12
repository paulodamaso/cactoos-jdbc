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
package com.github.fabriciofx.cactoos.jdbc.rset;

import com.github.fabriciofx.cactoos.jdbc.cache.Columns;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public final class ResultSetMetadaWithCaching extends
    ResultSetMetadaEnvelope {
    private final Columns columns;

    public ResultSetMetadaWithCaching(
        final ResultSetMetaData rsmd,
        final String... cols
    ) {
        super(rsmd);
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
            result = super.isAutoIncrement(column);
        } else {
            result = false;
        }
        return result;
    }

    @Override
    public boolean isCaseSensitive(final int column) throws SQLException {
        final boolean result;
        if (this.columns.valid(column)) {
            result = super.isCaseSensitive(column);
        } else {
            result = false;
        }
        return result;
    }

    @Override
    public boolean isSearchable(final int column) throws SQLException {
        final boolean result;
        if (this.columns.valid(column)) {
            result = super.isSearchable(column);
        } else {
            result = false;
        }
        return result;
    }

    @Override
    public boolean isCurrency(final int column) throws SQLException {
        final boolean result;
        if (this.columns.valid(column)) {
            result = super.isCurrency(column);
        } else {
            result = false;
        }
        return result;
    }

    @Override
    public int isNullable(final int column) throws SQLException {
        final int result;
        if (this.columns.valid(column)) {
            result = super.isNullable(column);
        } else {
            result = 0;
        }
        return result;
    }

    @Override
    public boolean isSigned(final int column) throws SQLException {
        final boolean result;
        if (this.columns.valid(column)) {
            result = super.isSigned(column);
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
            result = super.getPrecision(column);
        } else {
            result = 0;
        }
        return result;
    }

    @Override
    public int getScale(final int column) throws SQLException {
        final int result;
        if (this.columns.valid(column)) {
            result = super.getScale(column);
        } else {
            result = 0;
        }
        return result;
    }

    @Override
    public String getTableName(final int column) throws SQLException {
        final String result;
        if (this.columns.valid(column)) {
            result = super.getTableName(column);
        } else {
            result = "";
        }
        return result;
    }

    @Override
    public String getCatalogName(final int column) throws SQLException {
        final String result;
        if (this.columns.valid(column)) {
            result = super.getCatalogName(column);
        } else {
            result = "";
        }
        return result;
    }

    @Override
    public int getColumnType(final int column) throws SQLException {
        final int result;
        if (this.columns.valid(column)) {
            result = super.getColumnType(column);
        } else {
            result = 0;
        }
        return result;
    }

    @Override
    public String getColumnTypeName(final int column) throws SQLException {
        final String result;
        if (this.columns.valid(column)) {
            result = super.getColumnTypeName(column);
        } else {
            result = "";
        }
        return result;
    }

    @Override
    public String getColumnClassName(final int column) throws SQLException {
        final String result;
        if (this.columns.valid(column)) {
            result = super.getColumnClassName(column);
        } else {
            result = "";
        }
        return result;
    }
}
