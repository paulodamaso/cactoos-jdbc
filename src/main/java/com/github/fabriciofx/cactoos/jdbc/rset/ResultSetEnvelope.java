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

abstract class ResultSetEnvelope implements ResultSet {
    private final ResultSet origin;

    public ResultSetEnvelope(final ResultSet rset) {
        this.origin = rset;
    }

    @Override
    public boolean next() throws SQLException {
        return this.origin.next();
    }

    @Override
    public void close() throws SQLException {
        this.origin.close();
    }

    @Override
    public boolean wasNull() throws SQLException {
        return this.origin.wasNull();
    }

    @Override
    public String getString(final int columnIndex) throws SQLException {
        return this.origin.getString(columnIndex);
    }

    @Override
    public boolean getBoolean(final int columnIndex) throws SQLException {
        return this.origin.getBoolean(columnIndex);
    }

    @Override
    public byte getByte(final int columnIndex) throws SQLException {
        return this.origin.getByte(columnIndex);
    }

    @Override
    public short getShort(final int columnIndex) throws SQLException {
        return this.origin.getShort(columnIndex);
    }

    @Override
    public int getInt(final int columnIndex) throws SQLException {
        return this.origin.getInt(columnIndex);
    }

    @Override
    public long getLong(final int columnIndex) throws SQLException {
        return this.origin.getLong(columnIndex);
    }

    @Override
    public float getFloat(final int columnIndex) throws SQLException {
        return this.origin.getFloat(columnIndex);
    }

    @Override
    public double getDouble(final int columnIndex) throws SQLException {
        return this.origin.getDouble(columnIndex);
    }

    /**
     * Get a BigDecimal.
     * @deprecated It not should be used
     * @param columnIndex Column index
     * @param scale Scale
     * @throws SQLException If fails
     */
    @Deprecated
    public BigDecimal getBigDecimal(
        final int columnIndex,
        final int scale
    ) throws SQLException {
        return this.origin.getBigDecimal(columnIndex, scale);
    }

    @Override
    public byte[] getBytes(final int columnIndex) throws SQLException {
        return this.origin.getBytes(columnIndex);
    }

    @Override
    public Date getDate(final int columnIndex) throws SQLException {
        return this.origin.getDate(columnIndex);
    }

    @Override
    public Time getTime(final int columnIndex) throws SQLException {
        return this.origin.getTime(columnIndex);
    }

    @Override
    public Timestamp getTimestamp(final int columnIndex) throws SQLException {
        return this.origin.getTimestamp(columnIndex);
    }

    @Override
    public InputStream getAsciiStream(
        final int columnIndex
    ) throws SQLException {
        return this.origin.getAsciiStream(columnIndex);
    }

    /**
     * Get a stream in Unicode.
     * @deprecated It not should be used
     * @param columnIndex Column index
     * @throws SQLException If fails
     */
    @Deprecated
    public InputStream getUnicodeStream(
        final int columnIndex
    ) throws SQLException {
        return this.origin.getUnicodeStream(columnIndex);
    }

    @Override
    public InputStream getBinaryStream(
        final int columnIndex
    ) throws SQLException {
        return this.origin.getBinaryStream(columnIndex);
    }

    @Override
    public String getString(final String columnLabel) throws SQLException {
        return this.origin.getString(columnLabel);
    }

    @Override
    public boolean getBoolean(final String columnLabel) throws SQLException {
        return this.origin.getBoolean(columnLabel);
    }

    @Override
    public byte getByte(final String columnLabel) throws SQLException {
        return this.origin.getByte(columnLabel);
    }

    @Override
    public short getShort(final String columnLabel) throws SQLException {
        return this.origin.getShort(columnLabel);
    }

    @Override
    public int getInt(final String columnLabel) throws SQLException {
        return this.origin.getInt(columnLabel);
    }

    @Override
    public long getLong(final String columnLabel) throws SQLException {
        return this.origin.getLong(columnLabel);
    }

    @Override
    public float getFloat(final String columnLabel) throws SQLException {
        return this.origin.getFloat(columnLabel);
    }

    @Override
    public double getDouble(final String columnLabel) throws SQLException {
        return this.origin.getDouble(columnLabel);
    }

    /**
     * Get a BigDecimal.
     * @deprecated It not should be used
     * @param columnLabel Column label
     * @param scale Scale
     * @throws SQLException If fails
     */
    @Deprecated
    public BigDecimal getBigDecimal(
        final String columnLabel,
        final int scale
    ) throws SQLException {
        return this.origin.getBigDecimal(columnLabel, scale);
    }

    @Override
    public byte[] getBytes(final String columnLabel) throws SQLException {
        return this.origin.getBytes(columnLabel);
    }

    @Override
    public Date getDate(final String columnLabel) throws SQLException {
        return this.origin.getDate(columnLabel);
    }

    @Override
    public Time getTime(final String columnLabel) throws SQLException {
        return this.origin.getTime(columnLabel);
    }

    @Override
    public Timestamp getTimestamp(
        final String columnLabel
    ) throws SQLException {
        return this.origin.getTimestamp(columnLabel);
    }

    @Override
    public InputStream getAsciiStream(
        final String columnLabel
    ) throws SQLException {
        return this.origin.getAsciiStream(columnLabel);
    }

    /**
     * Get a stream in Unicode.
     * @deprecated It not should be used
     * @param columnLabel Column label
     * @throws SQLException If fails
     */
    @Deprecated
    public InputStream getUnicodeStream(
        final String columnLabel
    ) throws SQLException {
        return this.origin.getUnicodeStream(columnLabel);
    }

    @Override
    public InputStream getBinaryStream(
        final String columnLabel
    ) throws SQLException {
        return this.origin.getBinaryStream(columnLabel);
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return this.origin.getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException {
        this.origin.clearWarnings();
    }

    @Override
    public String getCursorName() throws SQLException {
        return this.origin.getCursorName();
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return this.origin.getMetaData();
    }

    @Override
    public Object getObject(final int columnIndex) throws SQLException {
        return this.origin.getObject(columnIndex);
    }

    @Override
    public Object getObject(final String columnLabel) throws SQLException {
        return this.origin.getObject(columnLabel);
    }

    @Override
    public int findColumn(final String columnLabel) throws SQLException {
        return this.origin.findColumn(columnLabel);
    }

    @Override
    public Reader getCharacterStream(
        final int columnIndex
    ) throws SQLException {
        return this.origin.getCharacterStream(columnIndex);
    }

    @Override
    public Reader getCharacterStream(
        final String columnLabel
    ) throws SQLException {
        return this.origin.getCharacterStream(columnLabel);
    }

    @Override
    public BigDecimal getBigDecimal(
        final int columnIndex
    ) throws SQLException {
        return this.origin.getBigDecimal(columnIndex);
    }

    @Override
    public BigDecimal getBigDecimal(
        final String columnLabel
    ) throws SQLException {
        return this.origin.getBigDecimal(columnLabel);
    }

    @Override
    public boolean isBeforeFirst() throws SQLException {
        return this.origin.isBeforeFirst();
    }

    @Override
    public boolean isAfterLast() throws SQLException {
        return this.origin.isAfterLast();
    }

    @Override
    public boolean isFirst() throws SQLException {
        return this.origin.isFirst();
    }

    @Override
    public boolean isLast() throws SQLException {
        return this.origin.isLast();
    }

    @Override
    public void beforeFirst() throws SQLException {
        this.origin.beforeFirst();
    }

    @Override
    public void afterLast() throws SQLException {
        this.origin.afterLast();
    }

    @Override
    public boolean first() throws SQLException {
        return this.origin.first();
    }

    @Override
    public boolean last() throws SQLException {
        return this.origin.last();
    }

    @Override
    public int getRow() throws SQLException {
        return this.origin.getRow();
    }

    @Override
    public boolean absolute(final int row) throws SQLException {
        return this.origin.absolute(row);
    }

    @Override
    public boolean relative(final int rows) throws SQLException {
        return this.origin.relative(rows);
    }

    @Override
    public boolean previous() throws SQLException {
        return this.origin.previous();
    }

    @Override
    public void setFetchDirection(final int direction) throws SQLException {
        this.origin.setFetchDirection(direction);
    }

    @Override
    public int getFetchDirection() throws SQLException {
        return this.origin.getFetchDirection();
    }

    @Override
    public void setFetchSize(final int rows) throws SQLException {
        this.origin.setFetchSize(rows);
    }

    @Override
    public int getFetchSize() throws SQLException {
        return this.origin.getFetchSize();
    }

    @Override
    public int getType() throws SQLException {
        return this.origin.getType();
    }

    @Override
    public int getConcurrency() throws SQLException {
        return this.origin.getConcurrency();
    }

    @Override
    public boolean rowUpdated() throws SQLException {
        return this.origin.rowUpdated();
    }

    @Override
    public boolean rowInserted() throws SQLException {
        return this.origin.rowInserted();
    }

    @Override
    public boolean rowDeleted() throws SQLException {
        return this.origin.rowDeleted();
    }

    @Override
    public void updateNull(final int columnIndex) throws SQLException {
        this.origin.updateNull(columnIndex);
    }

    @Override
    public void updateBoolean(
        final int columnIndex,
        final boolean x
    ) throws SQLException {
        this.origin.updateBoolean(columnIndex, x);
    }

    @Override
    public void updateByte(
        final int columnIndex,
        final byte x
    ) throws SQLException {
        this.origin.updateByte(columnIndex, x);
    }

    @Override
    public void updateShort(
        final int columnIndex,
        final short x
    ) throws SQLException {
        this.origin.updateShort(columnIndex, x);
    }

    @Override
    public void updateInt(
        final int columnIndex,
        final int x
    ) throws SQLException {
        this.origin.updateInt(columnIndex, x);
    }

    @Override
    public void updateLong(
        final int columnIndex,
        final long x
    ) throws SQLException {
        this.origin.updateLong(columnIndex, x);
    }

    @Override
    public void updateFloat(
        final int columnIndex,
        final float x
    ) throws SQLException {
        this.origin.updateFloat(columnIndex, x);
    }

    @Override
    public void updateDouble(
        final int columnIndex,
        final double x
    ) throws SQLException {
        this.origin.updateDouble(columnIndex, x);
    }

    @Override
    public void updateBigDecimal(
        final int columnIndex,
        final BigDecimal x
    ) throws SQLException {
        this.origin.updateBigDecimal(columnIndex, x);
    }

    @Override
    public void updateString(
        final int columnIndex,
        final String x
    ) throws SQLException {
        this.origin.updateString(columnIndex, x);
    }

    @Override
    public void updateBytes(
        final int columnIndex,
        final byte[] x
    ) throws SQLException {
        this.origin.updateBytes(columnIndex, x);
    }

    @Override
    public void updateDate(
        final int columnIndex,
        final Date x
    ) throws SQLException {
        this.origin.updateDate(columnIndex, x);
    }

    @Override
    public void updateTime(
        final int columnIndex,
        final Time x
    ) throws SQLException {
        this.origin.updateTime(columnIndex, x);
    }

    @Override
    public void updateTimestamp(
        final int columnIndex,
        final Timestamp x
    ) throws SQLException {
        this.origin.updateTimestamp(columnIndex, x);
    }

    @Override
    public void updateAsciiStream(
        final int columnIndex,
        final InputStream x,
        final int length
    ) throws SQLException {
        this.origin.updateAsciiStream(columnIndex, x, length);
    }

    @Override
    public void updateBinaryStream(
        final int columnIndex,
        final InputStream x,
        final int length
    ) throws SQLException {
        this.origin.updateBinaryStream(columnIndex, x, length);
    }

    @Override
    public void updateCharacterStream(
        final int columnIndex,
        final Reader x,
        final int length
    ) throws SQLException {
        this.origin.updateCharacterStream(columnIndex, x, length);
    }

    @Override
    public void updateObject(
        final int columnIndex,
        final Object x,
        final int scaleOrLength
    ) throws SQLException {
        this.origin.updateObject(columnIndex, x, scaleOrLength);
    }

    @Override
    public void updateObject(
        final int columnIndex,
        final Object x
    ) throws SQLException {
        this.origin.updateObject(columnIndex, x);
    }

    @Override
    public void updateNull(final String columnLabel) throws SQLException {
        this.origin.updateNull(columnLabel);
    }

    @Override
    public void updateBoolean(
        final String columnLabel,
        final boolean x
    ) throws SQLException {
        this.origin.updateBoolean(columnLabel, x);
    }

    @Override
    public void updateByte(
        final String columnLabel,
        final byte x
    ) throws SQLException {
        this.origin.updateByte(columnLabel, x);
    }

    @Override
    public void updateShort(
        final String columnLabel,
        final short x
    ) throws SQLException {
        this.origin.updateShort(columnLabel, x);
    }

    @Override
    public void updateInt(
        final String columnLabel,
        final int x
    ) throws SQLException {
        this.origin.updateInt(columnLabel, x);
    }

    @Override
    public void updateLong(
        final String columnLabel,
        final long x
    ) throws SQLException {
        this.origin.updateLong(columnLabel, x);
    }

    @Override
    public void updateFloat(
        final String columnLabel,
        final float x
    ) throws SQLException {
        this.origin.updateFloat(columnLabel, x);
    }

    @Override
    public void updateDouble(
        final String columnLabel,
        final double x
    ) throws SQLException {
        this.origin.updateDouble(columnLabel, x);
    }

    @Override
    public void updateBigDecimal(
        final String columnLabel,
        final BigDecimal x
    ) throws SQLException {
        this.origin.updateBigDecimal(columnLabel, x);
    }

    @Override
    public void updateString(
        final String columnLabel,
        final String x
    ) throws SQLException {
        this.origin.updateString(columnLabel, x);
    }

    @Override
    public void updateBytes(
        final String columnLabel,
        final byte[] x
    ) throws SQLException {
        this.origin.updateBytes(columnLabel, x);
    }

    @Override
    public void updateDate(
        final String columnLabel,
        final Date x
    ) throws SQLException {
        this.origin.updateDate(columnLabel, x);
    }

    @Override
    public void updateTime(
        final String columnLabel,
        final Time x
    ) throws SQLException {
        this.origin.updateTime(columnLabel, x);
    }

    @Override
    public void updateTimestamp(
        final String columnLabel,
        final Timestamp x
    ) throws SQLException {
        this.origin.updateTimestamp(columnLabel, x);
    }

    @Override
    public void updateAsciiStream(
        final String columnLabel,
        final InputStream x,
        final int length
    ) throws SQLException {
        this.origin.updateAsciiStream(columnLabel, x, length);
    }

    @Override
    public void updateBinaryStream(
        final String columnLabel,
        final InputStream x,
        final int length
    ) throws SQLException {
        this.origin.updateBinaryStream(columnLabel, x, length);
    }

    @Override
    public void updateCharacterStream(
        final String columnLabel,
        final Reader reader,
        final int length
    ) throws SQLException {
        this.origin.updateCharacterStream(columnLabel, reader, length);
    }

    @Override
    public void updateObject(
        final String columnLabel,
        final Object x,
        final int scaleOrLength
    ) throws SQLException {
        this.origin.updateObject(columnLabel, x, scaleOrLength);
    }

    @Override
    public void updateObject(
        final String columnLabel,
        final Object x
    ) throws SQLException {
        this.origin.updateObject(columnLabel, x);
    }

    @Override
    public void insertRow() throws SQLException {
        this.origin.insertRow();
    }

    @Override
    public void updateRow() throws SQLException {
        this.origin.updateRow();
    }

    @Override
    public void deleteRow() throws SQLException {
        this.origin.deleteRow();
    }

    @Override
    public void refreshRow() throws SQLException {
        this.origin.refreshRow();
    }

    @Override
    public void cancelRowUpdates() throws SQLException {
        this.origin.cancelRowUpdates();
    }

    @Override
    public void moveToInsertRow() throws SQLException {
        this.origin.moveToInsertRow();
    }

    @Override
    public void moveToCurrentRow() throws SQLException {
        this.origin.moveToCurrentRow();
    }

    @Override
    public Statement getStatement() throws SQLException {
        return this.origin.getStatement();
    }

    @Override
    public Object getObject(
        final int columnIndex,
        final Map<String, Class<?>> map
    ) throws SQLException {
        return this.origin.getObject(columnIndex, map);
    }

    @Override
    public Ref getRef(final int columnIndex) throws SQLException {
        return this.origin.getRef(columnIndex);
    }

    @Override
    public Blob getBlob(final int columnIndex) throws SQLException {
        return this.origin.getBlob(columnIndex);
    }

    @Override
    public Clob getClob(final int columnIndex) throws SQLException {
        return this.origin.getClob(columnIndex);
    }

    @Override
    public Array getArray(final int columnIndex) throws SQLException {
        return this.origin.getArray(columnIndex);
    }

    @Override
    public Object getObject(
        final String columnLabel,
        final Map<String, Class<?>> map
    ) throws SQLException {
        return this.origin.getObject(columnLabel, map);
    }

    @Override
    public Ref getRef(final String columnLabel) throws SQLException {
        return this.origin.getRef(columnLabel);
    }

    @Override
    public Blob getBlob(final String columnLabel) throws SQLException {
        return this.origin.getBlob(columnLabel);
    }

    @Override
    public Clob getClob(final String columnLabel) throws SQLException {
        return this.origin.getClob(columnLabel);
    }

    @Override
    public Array getArray(final String columnLabel) throws SQLException {
        return this.origin.getArray(columnLabel);
    }

    @Override
    public Date getDate(
        final int columnIndex,
        final Calendar cal
    ) throws SQLException {
        return this.origin.getDate(columnIndex, cal);
    }

    @Override
    public Date getDate(
        final String columnLabel,
        final Calendar cal
    ) throws SQLException {
        return this.origin.getDate(columnLabel, cal);
    }

    @Override
    public Time getTime(
        final int columnIndex,
        final Calendar cal
    ) throws SQLException {
        return this.origin.getTime(columnIndex, cal);
    }

    @Override
    public Time getTime(
        final String columnLabel,
        final Calendar cal
    ) throws SQLException {
        return this.origin.getTime(columnLabel, cal);
    }

    @Override
    public Timestamp getTimestamp(
        final int columnIndex,
        final Calendar cal
    ) throws SQLException {
        return this.origin.getTimestamp(columnIndex, cal);
    }

    @Override
    public Timestamp getTimestamp(
        final String columnLabel,
        final Calendar cal
    ) throws SQLException {
        return this.origin.getTimestamp(columnLabel, cal);
    }

    @Override
    public URL getURL(final int columnIndex) throws SQLException {
        return this.origin.getURL(columnIndex);
    }

    @Override
    public URL getURL(final String columnLabel) throws SQLException {
        return this.origin.getURL(columnLabel);
    }

    @Override
    public void updateRef(
        final int columnIndex,
        final Ref x
    ) throws SQLException {
        this.origin.updateRef(columnIndex, x);
    }

    @Override
    public void updateRef(
        final String columnLabel,
        final Ref x
    ) throws SQLException {
        this.origin.updateRef(columnLabel, x);
    }

    @Override
    public void updateBlob(
        final int columnIndex,
        final Blob x
    ) throws SQLException {
        this.origin.updateBlob(columnIndex, x);
    }

    @Override
    public void updateBlob(
        final String columnLabel,
        final Blob x
    ) throws SQLException {
        this.origin.updateBlob(columnLabel, x);
    }

    @Override
    public void updateClob(
        final int columnIndex,
        final Clob x
    ) throws SQLException {
        this.origin.updateClob(columnIndex, x);
    }

    @Override
    public void updateClob(
        final String columnLabel,
        final Clob x
    ) throws SQLException {
        this.origin.updateClob(columnLabel, x);
    }

    @Override
    public void updateArray(
        final int columnIndex,
        final Array x
    ) throws SQLException {
        this.origin.updateArray(columnIndex, x);
    }

    @Override
    public void updateArray(
        final String columnLabel,
        final Array x
    ) throws SQLException {
        this.origin.updateArray(columnLabel, x);
    }

    @Override
    public RowId getRowId(final int columnIndex) throws SQLException {
        return this.origin.getRowId(columnIndex);
    }

    @Override
    public RowId getRowId(final String columnLabel) throws SQLException {
        return this.origin.getRowId(columnLabel);
    }

    @Override
    public void updateRowId(
        final int columnIndex,
        final RowId x
    ) throws SQLException {
        this.origin.updateRowId(columnIndex, x);
    }

    @Override
    public void updateRowId(
        final String columnLabel,
        final RowId x
    ) throws SQLException {
        this.origin.updateRowId(columnLabel, x);
    }

    @Override
    public int getHoldability() throws SQLException {
        return this.origin.getHoldability();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return this.origin.isClosed();
    }

    @Override
    public void updateNString(
        final int columnIndex,
        final String nString
    ) throws SQLException {
        this.origin.updateNString(columnIndex, nString);
    }

    @Override
    public void updateNString(
        final String columnLabel,
        final String nString
    ) throws SQLException {
        this.origin.updateNString(columnLabel, nString);
    }

    @Override
    public void updateNClob(
        final int columnIndex,
        final NClob nClob
    ) throws SQLException {
        this.origin.updateNClob(columnIndex, nClob);
    }

    @Override
    public void updateNClob(
        final String columnLabel,
        final NClob nClob
    ) throws SQLException {
        this.origin.updateNClob(columnLabel, nClob);
    }

    @Override
    public NClob getNClob(final int columnIndex) throws SQLException {
        return this.origin.getNClob(columnIndex);
    }

    @Override
    public NClob getNClob(final String columnLabel) throws SQLException {
        return this.origin.getNClob(columnLabel);
    }

    @Override
    public SQLXML getSQLXML(final int columnIndex) throws SQLException {
        return this.origin.getSQLXML(columnIndex);
    }

    @Override
    public SQLXML getSQLXML(final String columnLabel) throws SQLException {
        return this.origin.getSQLXML(columnLabel);
    }

    @Override
    public void updateSQLXML(
        final int columnIndex,
        final SQLXML xmlObject
    ) throws SQLException {
        this.origin.updateSQLXML(columnIndex, xmlObject);
    }

    @Override
    public void updateSQLXML(
        final String columnLabel,
        final SQLXML xmlObject
    ) throws SQLException {
        this.origin.updateSQLXML(columnLabel, xmlObject);
    }

    @Override
    public String getNString(final int columnIndex) throws SQLException {
        return this.origin.getNString(columnIndex);
    }

    @Override
    public String getNString(final String columnLabel) throws SQLException {
        return this.origin.getNString(columnLabel);
    }

    @Override
    public Reader getNCharacterStream(
        final int columnIndex
    ) throws SQLException {
        return this.origin.getNCharacterStream(columnIndex);
    }

    @Override
    public Reader getNCharacterStream(
        final String columnLabel
    ) throws SQLException {
        return this.origin.getNCharacterStream(columnLabel);
    }

    @Override
    public void updateNCharacterStream(
        final int columnIndex,
        final Reader x,
        final long length
    ) throws SQLException {
        this.origin.updateNCharacterStream(columnIndex, x, length);
    }

    @Override
    public void updateNCharacterStream(
        final String columnLabel,
        final Reader reader,
        final long length
    ) throws SQLException {
        this.origin.updateNCharacterStream(columnLabel, reader, length);
    }

    @Override
    public void updateAsciiStream(
        final int columnIndex,
        final InputStream x,
        final long length
    ) throws SQLException {
        this.origin.updateAsciiStream(columnIndex, x, length);
    }

    @Override
    public void updateBinaryStream(
        final int columnIndex,
        final InputStream x,
        final long length
    ) throws SQLException {
        this.origin.updateBinaryStream(columnIndex, x, length);
    }

    @Override
    public void updateCharacterStream(
        final int columnIndex,
        final Reader x,
        final long length
    ) throws SQLException {
        this.origin.updateCharacterStream(columnIndex, x, length);
    }

    @Override
    public void updateAsciiStream(
        final String columnLabel,
        final InputStream x,
        final long length
    ) throws SQLException {
        this.origin.updateAsciiStream(columnLabel, x, length);
    }

    @Override
    public void updateBinaryStream(
        final String columnLabel,
        final InputStream x,
        final long length
    ) throws SQLException {
        this.origin.updateBinaryStream(columnLabel, x, length);
    }

    @Override
    public void updateCharacterStream(
        final String columnLabel,
        final Reader reader,
        final long length
    ) throws SQLException {
        this.origin.updateCharacterStream(columnLabel, reader, length);
    }

    @Override
    public void updateBlob(
        final int columnIndex,
        final InputStream inputStream,
        final long length
    ) throws SQLException {
        this.origin.updateBlob(columnIndex, inputStream, length);
    }

    @Override
    public void updateBlob(
        final String columnLabel,
        final InputStream inputStream,
        final long length
    ) throws SQLException {
        this.origin.updateBlob(columnLabel, inputStream, length);
    }

    @Override
    public void updateClob(
        final int columnIndex,
        final Reader reader,
        final long length
    ) throws SQLException {
        this.origin.updateClob(columnIndex, reader, length);
    }

    @Override
    public void updateClob(
        final String columnLabel,
        final Reader reader,
        final long length
    ) throws SQLException {
        this.origin.updateClob(columnLabel, reader, length);
    }

    @Override
    public void updateNClob(
        final int columnIndex,
        final Reader reader,
        final long length
    ) throws SQLException {
        this.origin.updateNClob(columnIndex, reader, length);
    }

    @Override
    public void updateNClob(
        final String columnLabel,
        final Reader reader,
        final long length
    ) throws SQLException {
        this.origin.updateNClob(columnLabel, reader, length);
    }

    @Override
    public void updateNCharacterStream(
        final int columnIndex,
        final Reader x
    ) throws SQLException {
        this.origin.updateNCharacterStream(columnIndex, x);
    }

    @Override
    public void updateNCharacterStream(
        final String columnLabel,
        final Reader reader
    ) throws SQLException {
        this.origin.updateNCharacterStream(columnLabel, reader);
    }

    @Override
    public void updateAsciiStream(
        final int columnIndex,
        final InputStream x
    ) throws SQLException {
        this.origin.updateAsciiStream(columnIndex, x);
    }

    @Override
    public void updateBinaryStream(
        final int columnIndex,
        final InputStream x
    ) throws SQLException {
        this.origin.updateBinaryStream(columnIndex, x);
    }

    @Override
    public void updateCharacterStream(
        final int columnIndex,
        final Reader x
    ) throws SQLException {
        this.origin.updateCharacterStream(columnIndex, x);
    }

    @Override
    public void updateAsciiStream(
        final String columnLabel,
        final InputStream x
    ) throws SQLException {
        this.origin.updateAsciiStream(columnLabel, x);
    }

    @Override
    public void updateBinaryStream(
        final String columnLabel,
        final InputStream x
    ) throws SQLException {
        this.origin.updateBinaryStream(columnLabel, x);
    }

    @Override
    public void updateCharacterStream(
        final String columnLabel,
        final Reader reader
    ) throws SQLException {
        this.origin.updateCharacterStream(columnLabel, reader);
    }

    @Override
    public void updateBlob(
        final int columnIndex,
        final InputStream inputStream
    ) throws SQLException {
        this.origin.updateBlob(columnIndex, inputStream);
    }

    @Override
    public void updateBlob(
        final String columnLabel,
        final InputStream inputStream
    ) throws SQLException {
        this.origin.updateBlob(columnLabel, inputStream);
    }

    @Override
    public void updateClob(
        final int columnIndex,
        final Reader reader
    ) throws SQLException {
        this.origin.updateClob(columnIndex, reader);
    }

    @Override
    public void updateClob(
        final String columnLabel,
        final Reader reader
    ) throws SQLException {
        this.origin.updateClob(columnLabel, reader);
    }

    @Override
    public void updateNClob(
        final int columnIndex,
        final Reader reader
    ) throws SQLException {
        this.origin.updateNClob(columnIndex, reader);
    }

    @Override
    public void updateNClob(
        final String columnLabel,
        final Reader reader
    ) throws SQLException {
        this.origin.updateNClob(columnLabel, reader);
    }

    @Override
    public <T> T getObject(
        final int columnIndex,
        final Class<T> type
    ) throws SQLException {
        return this.origin.getObject(columnIndex, type);
    }

    @Override
    public <T> T getObject(
        final String columnLabel,
        final Class<T> type
    ) throws SQLException {
        return this.origin.getObject(columnLabel, type);
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
