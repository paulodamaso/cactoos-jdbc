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

import com.github.fabriciofx.cactoos.jdbc.Rows;
import com.github.fabriciofx.cactoos.jdbc.row.FilteredRows;
import com.github.fabriciofx.cactoos.jdbc.rset.ResultSetAsRows;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public final class CachedResultSet implements ResultSet {
    private final ResultSet origin;
    private final Rows rows;
    private final AtomicInteger pos;
    private final String[] columns;

    public CachedResultSet(final ResultSet rset, final String... cols) {
        this.origin = rset;
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
    public boolean wasNull() throws SQLException {
        return false;
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
    public byte[] getBytes(final int columnIndex) throws SQLException {
        return new byte[0];
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
    public InputStream getAsciiStream(final int columnIndex) throws SQLException {
        return null;
    }

    /**
     * Get a stream in Unicode.
     * @deprecated It not should be used
     * @param columnIndex Column index
     * @throws SQLException If fails
     */
    @Deprecated
    public InputStream getUnicodeStream(final int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public InputStream getBinaryStream(final int columnIndex) throws SQLException {
        return null;
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
    public byte[] getBytes(final String columnLabel) throws SQLException {
        return new byte[0];
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
    public InputStream getAsciiStream(final String columnLabel) throws SQLException {
        return null;
    }

    /**
     * Get a stream in Unicode.
     * @deprecated It not should be used
     * @param columnLabel Column label
     * @throws SQLException If fails
     */
    @Deprecated
    public InputStream getUnicodeStream(final String columnLabel) throws SQLException {
        return null;
    }

    @Override
    public InputStream getBinaryStream(final String columnLabel) throws SQLException {
        return null;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {
    }

    @Override
    public String getCursorName() throws SQLException {
        return null;
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return new CachedResultSetMetada(
            this.origin.getMetaData(),
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
    public Reader getCharacterStream(final int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public Reader getCharacterStream(final String columnLabel) throws SQLException {
        return null;
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
    public boolean absolute(final int row) throws SQLException {
        return false;
    }

    @Override
    public boolean relative(final int rows) throws SQLException {
        return false;
    }

    @Override
    public boolean previous() throws SQLException {
        return true;
    }

    @Override
    public void setFetchDirection(final int direction) throws SQLException {
    }

    @Override
    public int getFetchDirection() throws SQLException {
        return 0;
    }

    @Override
    public void setFetchSize(final int rows) throws SQLException {
    }

    @Override
    public int getFetchSize() throws SQLException {
        return 0;
    }

    @Override
    public int getType() throws SQLException {
        return 0;
    }

    @Override
    public int getConcurrency() throws SQLException {
        return 0;
    }

    @Override
    public boolean rowUpdated() throws SQLException {
        return false;
    }

    @Override
    public boolean rowInserted() throws SQLException {
        return false;
    }

    @Override
    public boolean rowDeleted() throws SQLException {
        return false;
    }

    @Override
    public void updateNull(final int columnIndex) throws SQLException {
    }

    @Override
    public void updateBoolean(final int columnIndex, final boolean x) throws SQLException {
    }

    @Override
    public void updateByte(final int columnIndex, final byte x) throws SQLException {
    }

    @Override
    public void updateShort(final int columnIndex, final short x) throws SQLException {
    }

    @Override
    public void updateInt(final int columnIndex, final int x) throws SQLException {
    }

    @Override
    public void updateLong(final int columnIndex, final long x) throws SQLException {
    }

    @Override
    public void updateFloat(final int columnIndex, final float x) throws SQLException {
    }

    @Override
    public void updateDouble(final int columnIndex, final double x) throws SQLException {
    }

    @Override
    public void updateBigDecimal(final int columnIndex, final BigDecimal x) throws SQLException {
    }

    @Override
    public void updateString(final int columnIndex, final String x) throws SQLException {
    }
    @Override
    public void updateBytes(final int columnIndex, final byte[] x) throws SQLException {
    }
    @Override
    public void updateDate(final int columnIndex, final Date x) throws SQLException {
    }
    @Override
    public void updateTime(final int columnIndex, final Time x) throws SQLException {
    }
    @Override
    public void updateTimestamp(final int columnIndex, final Timestamp x) throws SQLException {
    }
    @Override
    public void updateAsciiStream(final int columnIndex, final InputStream x,
                                  final int length) throws SQLException {
    }
    @Override
    public void updateBinaryStream(final int columnIndex, final InputStream x, final int length) throws SQLException {
    }
    @Override
    public void updateCharacterStream(final int columnIndex, final Reader x,
                                      final int length) throws SQLException {
    }
    @Override
    public void updateObject(final int columnIndex, final Object x,
                             final int scaleOrLength) throws SQLException {
    }
    @Override
    public void updateObject(final int columnIndex, final Object x) throws SQLException {
    }
    @Override
    public void updateNull(final String columnLabel) throws SQLException {
    }
    @Override
    public void updateBoolean(final String columnLabel, final boolean x) throws SQLException {
    }
    @Override
    public void updateByte(final String columnLabel, final byte x) throws SQLException {
    }
    @Override
    public void updateShort(final String columnLabel, final short x) throws SQLException {
    }
    @Override
    public void updateInt(final String columnLabel, final int x) throws SQLException {
    }
    @Override
    public void updateLong(final String columnLabel, final long x) throws SQLException {
    }
    @Override
    public void updateFloat(final String columnLabel, final float x) throws SQLException {
    }
    @Override
    public void updateDouble(final String columnLabel, final double x) throws SQLException {
    }
    @Override
    public void updateBigDecimal(final String columnLabel,
                                 final BigDecimal x) throws SQLException {
    }
    @Override
    public void updateString(final String columnLabel, final String x) throws SQLException {
    }
    @Override
    public void updateBytes(final String columnLabel, final byte[] x) throws SQLException {
    }
    @Override
    public void updateDate(final String columnLabel, final Date x) throws SQLException {
    }
    @Override
    public void updateTime(final String columnLabel, final Time x) throws SQLException {
    }
    @Override
    public void updateTimestamp(final String columnLabel, final Timestamp x) throws SQLException {
    }
    @Override
    public void updateAsciiStream(final String columnLabel, final InputStream x, final int length) throws SQLException {
    }
    @Override
    public void updateBinaryStream(final String columnLabel, final InputStream x, final int length) throws SQLException {
    }
    @Override
    public void updateCharacterStream(final String columnLabel, final Reader reader, final int length) throws SQLException {
    }
    @Override
    public void updateObject(final String columnLabel, final Object x,
                             final int scaleOrLength) throws SQLException {
    }
    @Override
    public void updateObject(final String columnLabel, final Object x) throws SQLException {
    }
    @Override
    public void insertRow() throws SQLException {
    }
    @Override
    public void updateRow() throws SQLException {
    }
    @Override
    public void deleteRow() throws SQLException {
    }
    @Override
    public void refreshRow() throws SQLException {
    }
    @Override
    public void cancelRowUpdates() throws SQLException {
    }
    @Override
    public void moveToInsertRow() throws SQLException {
    }
    @Override
    public void moveToCurrentRow() throws SQLException {
    }
    @Override
    public Statement getStatement() throws SQLException {
        return null;
    }
    @Override
    public Object getObject(final int columnIndex, final Map<String,
            Class<?>> map) throws SQLException {
        return null;
    }
    @Override
    public Ref getRef(final int columnIndex) throws SQLException {
        return null;
    }
    @Override
    public Blob getBlob(final int columnIndex) throws SQLException {
        return null;
    }
    @Override
    public Clob getClob(final int columnIndex) throws SQLException {
        return null;
    }
    @Override
    public Array getArray(final int columnIndex) throws SQLException {
        return null;
    }
    @Override
    public Object getObject(final String columnLabel, final Map<String,
        Class<?>> map) throws SQLException {
        return null;
    }
    @Override
    public Ref getRef(final String columnLabel) throws SQLException {
        return null;
    }
    @Override
    public Blob getBlob(final String columnLabel) throws SQLException {
        return null;
    }
    @Override
    public Clob getClob(final String columnLabel) throws SQLException {
        return null;
    }
    @Override
    public Array getArray(final String columnLabel) throws SQLException {
        return null;
    }
    @Override
    public Date getDate(final int columnIndex, final Calendar cal) throws SQLException {
        return null;
    }
    @Override
    public Date getDate(final String columnLabel, final Calendar cal) throws SQLException {
        return null;
    }
    @Override
    public Time getTime(final int columnIndex, final Calendar cal) throws SQLException {
        return null;
    }
    @Override
    public Time getTime(final String columnLabel, final Calendar cal) throws SQLException {
        return null;
    }
    @Override
    public Timestamp getTimestamp(final int columnIndex, final Calendar cal) throws SQLException {
        return null;
    }
    @Override
    public Timestamp getTimestamp(final String columnLabel, final Calendar cal) throws SQLException {
        return null;
    }
    @Override
    public URL getURL(final int columnIndex) throws SQLException {
        return null;
    }
    @Override
    public URL getURL(final String columnLabel) throws SQLException {
        return null;
    }
    @Override
    public void updateRef(final int columnIndex, final Ref x) throws SQLException {
    }
    @Override
    public void updateRef(final String columnLabel, final Ref x) throws SQLException {
    }
    @Override
    public void updateBlob(final int columnIndex, final Blob x) throws SQLException {
    }
    @Override
    public void updateBlob(final String columnLabel, final Blob x) throws SQLException {
    }
    @Override
    public void updateClob(final int columnIndex, final Clob x) throws SQLException {
    }
    @Override
    public void updateClob(final String columnLabel, final Clob x) throws SQLException {
    }
    @Override
    public void updateArray(final int columnIndex, final Array x) throws SQLException {
    }
    @Override
    public void updateArray(final String columnLabel, final Array x) throws SQLException {
    }
    @Override
    public RowId getRowId(final int columnIndex) throws SQLException {
        return null;
    }
    @Override
    public RowId getRowId(final String columnLabel) throws SQLException {
        return null;
    }
    @Override
    public void updateRowId(final int columnIndex, final RowId x) throws SQLException {
    }
    @Override
    public void updateRowId(final String columnLabel, final RowId x) throws SQLException {
    }
    @Override
    public int getHoldability() throws SQLException {
        return 0;
    }
    @Override
    public boolean isClosed() throws SQLException {
        return false;
    }
    @Override
    public void updateNString(final int columnIndex, final String nString) throws SQLException {
    }
    @Override
    public void updateNString(final String columnLabel, final String nString) throws SQLException {
    }
    @Override
    public void updateNClob(final int columnIndex, final NClob nClob) throws SQLException {
    }
    @Override
    public void updateNClob(final String columnLabel, final NClob nClob) throws SQLException {
    }
    @Override
    public NClob getNClob(final int columnIndex) throws SQLException {
        return null;
    }
    @Override
    public NClob getNClob(final String columnLabel) throws SQLException {
        return null;
    }
    @Override
    public SQLXML getSQLXML(final int columnIndex) throws SQLException {
        return null;
    }
    @Override
    public SQLXML getSQLXML(final String columnLabel) throws SQLException {
        return null;
    }
    @Override
    public void updateSQLXML(final int columnIndex, final SQLXML xmlObject) throws SQLException {
    }
    @Override
    public void updateSQLXML(final String columnLabel,
                             final SQLXML xmlObject) throws SQLException {
    }
    @Override
    public String getNString(final int columnIndex) throws SQLException {
        return null;
    }
    @Override
    public String getNString(final String columnLabel) throws SQLException {
        return null;
    }
    @Override
    public Reader getNCharacterStream(final int columnIndex) throws SQLException {
        return null;
    }
    @Override
    public Reader getNCharacterStream(final String columnLabel) throws SQLException {
        return null;
    }
    @Override
    public void updateNCharacterStream(final int columnIndex, final Reader x,
                                       final long length) throws SQLException {
    }
    @Override
    public void updateNCharacterStream(final String columnLabel,
                                       final Reader reader,
                                       final long length) throws SQLException {
    }
    @Override
    public void updateAsciiStream(final int columnIndex, final InputStream x,
                                  final long length) throws SQLException {
    }
    @Override
    public void updateBinaryStream(final int columnIndex, final InputStream x
        , final long length) throws SQLException {
    }
    @Override
    public void updateCharacterStream(final int columnIndex, final Reader x,
                                      final long length) throws SQLException {
    }
    @Override
    public void updateAsciiStream(final String columnLabel,
                                  final InputStream x, final long length) throws SQLException {
    }
    @Override
    public void updateBinaryStream(final String columnLabel,
                                   final InputStream x, final long length) throws SQLException {
    }
    @Override
    public void updateCharacterStream(final String columnLabel,
                                      final Reader reader, final long length) throws SQLException {
    }
    @Override
    public void updateBlob(final int columnIndex, final InputStream inputStream, final long length) throws SQLException {
    }
    @Override
    public void updateBlob(final String columnLabel, final InputStream inputStream, final long length) throws SQLException {
    }
    @Override
    public void updateClob(final int columnIndex, final Reader reader,
                           final long length) throws SQLException {
    }
    @Override
    public void updateClob(final String columnLabel, final Reader reader,
                           final long length) throws SQLException {
    }
    @Override
    public void updateNClob(final int columnIndex, final Reader reader,
                            final long length) throws SQLException {
    }
    @Override
    public void updateNClob(final String columnLabel, final Reader reader,
                            final long length) throws SQLException {
    }
    @Override
    public void updateNCharacterStream(final int columnIndex, final Reader x) throws SQLException {
    }
    @Override
    public void updateNCharacterStream(final String columnLabel, final Reader reader) throws SQLException {
    }
    @Override
    public void updateAsciiStream(final int columnIndex, final InputStream x) throws SQLException {
    }
    @Override
    public void updateBinaryStream(final int columnIndex, final InputStream x) throws SQLException {
    }
    @Override
    public void updateCharacterStream(final int columnIndex, final Reader x) throws SQLException {
    }
    @Override
    public void updateAsciiStream(final String columnLabel, final InputStream x) throws SQLException {
    }
    @Override
    public void updateBinaryStream(final String columnLabel, final InputStream x) throws SQLException {
    }
    @Override
    public void updateCharacterStream(final String columnLabel, final Reader reader) throws SQLException {
    }
    @Override
    public void updateBlob(final int columnIndex, final InputStream inputStream) throws SQLException {
    }
    @Override
    public void updateBlob(final String columnLabel, final InputStream inputStream) throws SQLException {
    }
    @Override
    public void updateClob(final int columnIndex, final Reader reader) throws SQLException {
    }
    @Override
    public void updateClob(final String columnLabel, final Reader reader) throws SQLException {
    }
    @Override
    public void updateNClob(final int columnIndex, final Reader reader) throws SQLException {
    }
    @Override
    public void updateNClob(final String columnLabel, final Reader reader) throws SQLException {
    }

    @Override
    public <T> T getObject(final int columnIndex, final Class<T> type) throws SQLException {
        return type.cast(this.getObject(columnIndex));
    }

    @Override
    public <T> T getObject(final String columnLabel, final Class<T> type) throws SQLException {
        return type.cast(this.getObject(columnLabel));
    }

    @Override
    public <T> T unwrap(final Class<T> iface) throws SQLException {
        return null;
    }
    @Override
    public boolean isWrapperFor(final Class<?> iface) throws SQLException {
        return false;
    }
}
