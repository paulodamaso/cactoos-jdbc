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

import com.github.fabriciofx.cactoos.jdbc.Rows;
import com.github.fabriciofx.cactoos.jdbc.adapter.ResultSetAsRows;
import com.github.fabriciofx.cactoos.jdbc.row.FilteredRows;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public final class ResultSetWithCaching extends ResultSetEnvelope {
    private final Rows rows;
    private final AtomicInteger pos;
    private final String[] columns;

    public ResultSetWithCaching(final ResultSet rset, final String... cols) {
        super(rset);
        this.rows = new FilteredRows(
            new ResultSetAsRows(rset),
            cols
        );
        this.pos = new AtomicInteger(-1);
        this.columns = cols;
    }

    @Override
    public boolean next() throws SQLException {
        return this.pos.getAndIncrement() < this.rows.count() - 1;
    }

    @Override
    public void close() throws SQLException {
    }

    @Override
    public String getString(final int columnIndex) throws SQLException {
        return (String) this.getObject(columnIndex);
    }

    @Override
    public boolean getBoolean(final int columnIndex) throws SQLException {
        return (Boolean) this.getObject(columnIndex);
    }

    @Override
    public byte getByte(final int columnIndex) throws SQLException {
        return (Byte) this.getObject(columnIndex);
    }

    @Override
    public short getShort(final int columnIndex) throws SQLException {
        return (Short) this.getObject(columnIndex);
    }

    @Override
    public int getInt(final int columnIndex) throws SQLException {
        return (Integer) this.getObject(columnIndex);
    }

    @Override
    public long getLong(final int columnIndex) throws SQLException {
        return (Long) this.getObject(columnIndex);
    }

    @Override
    public float getFloat(final int columnIndex) throws SQLException {
        return (Float) this.getObject(columnIndex);
    }

    @Override
    public double getDouble(final int columnIndex) throws SQLException {
        return (Double) this.getObject(columnIndex);
    }

    /**
     * Get a BigDecimal.
     * @deprecated It not should be used
     * @param columnIndex Column index
     * @param scale Scale
     * @throws SQLException If fails
     */
    @Deprecated
    public BigDecimal getBigDecimal(final int columnIndex, final int scale) throws SQLException {
        return (BigDecimal) this.getObject(columnIndex);
    }

    @Override
    public Date getDate(final int columnIndex) throws SQLException {
        return (Date) this.getObject(columnIndex);
    }

    @Override
    public Time getTime(final int columnIndex) throws SQLException {
        return (Time) this.getObject(columnIndex);
    }

    @Override
    public Timestamp getTimestamp(final int columnIndex) throws SQLException {
        return (Timestamp) this.getObject(columnIndex);
    }

    @Override
    public String getString(final String columnLabel) throws SQLException {
        return (String) this.getObject(columnLabel);
    }

    @Override
    public boolean getBoolean(final String columnLabel) throws SQLException {
        return (Boolean) this.getObject(columnLabel);
    }

    @Override
    public byte getByte(final String columnLabel) throws SQLException {
        return (Byte) this.getObject(columnLabel);
    }

    @Override
    public short getShort(final String columnLabel) throws SQLException {
        return (Short) this.getObject(columnLabel);
    }

    @Override
    public int getInt(final String columnLabel) throws SQLException {
        return (Integer) this.getObject(columnLabel);
    }

    @Override
    public long getLong(final String columnLabel) throws SQLException {
        return (Long) this.getObject(columnLabel);
    }

    @Override
    public float getFloat(final String columnLabel) throws SQLException {
        return (Float) this.getObject(columnLabel);
    }

    @Override
    public double getDouble(final String columnLabel) throws SQLException {
        return (Double) this.getObject(columnLabel);
    }

    /**
     * Get a BigDecimal.
     * @deprecated It not should be used
     * @param columnLabel Column label
     * @param scale Scale
     * @throws SQLException If fails
     */
    @Deprecated
    public BigDecimal getBigDecimal(final String columnLabel,
                                    final int scale) throws SQLException {
        return (BigDecimal) this.getObject(columnLabel);
    }

    @Override
    public Date getDate(final String columnLabel) throws SQLException {
        return (Date) this.getObject(columnLabel);
    }

    @Override
    public Time getTime(final String columnLabel) throws SQLException {
        return (Time) this.getObject(columnLabel);
    }

    @Override
    public Timestamp getTimestamp(final String columnLabel) throws SQLException {
        return (Timestamp) this.getObject(columnLabel);
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return new ResultSetMetadaWithCaching(
            super.getMetaData(),
            this.columns
        );
    }

    @Override
    public Object getObject(final int columnIndex) throws SQLException {
        final Map<String, Object> row = this.rows.row(this.pos.get());
        System.out.println("POS: " + this.pos.get());
        System.out.println("CALC: " + (columnIndex - 1));
        final String key = row.keySet().toArray(new String[0])[columnIndex - 1];
        return row.get(key);
    }

    @Override
    public Object getObject(final String columnLabel) throws SQLException {
        return this.rows.row(this.pos.get()).get(columnLabel);
    }

    @Override
    public int findColumn(final String columnLabel) throws SQLException {
        int column = 0;
        for (final String key : this.rows.row(0).keySet()) {
            if (key.equals(columnLabel)) {
                return column;
            }
        }
        return 0;
    }

    @Override
    public BigDecimal getBigDecimal(final int columnIndex) throws SQLException {
        return (BigDecimal) this.getObject(columnIndex);
    }

    @Override
    public BigDecimal getBigDecimal(final String columnLabel) throws SQLException {
        return (BigDecimal) this.getObject(columnLabel);
    }

    @Override
    public boolean isBeforeFirst() throws SQLException {
        return this.pos.get() < 0;
    }

    @Override
    public boolean isAfterLast() throws SQLException {
        return this.rows.count() >= this.pos.get();
    }

    @Override
    public boolean isFirst() throws SQLException {
        return this.pos.get() == 0;
    }

    @Override
    public boolean isLast() throws SQLException {
        return this.pos.get() == this.rows.count();
    }

    @Override
    public void beforeFirst() throws SQLException {
        this.pos.set(-1);
    }

    @Override
    public void afterLast() throws SQLException {
        this.pos.set(this.rows.count());
    }

    @Override
    public boolean first() throws SQLException {
        this.pos.set(0);
        return true;
    }

    @Override
    public boolean last() throws SQLException {
        this.pos.set(this.rows.count() - 1);
        return true;
    }

    @Override
    public int getRow() throws SQLException {
        return this.pos.get() + 1;
    }

    @Override
    public <T> T getObject(final int columnIndex, final Class<T> type) throws SQLException {
        return type.cast(this.getObject(columnIndex));
    }

    @Override
    public <T> T getObject(final String columnLabel, final Class<T> type) throws SQLException {
        return type.cast(this.getObject(columnLabel));
    }
}
