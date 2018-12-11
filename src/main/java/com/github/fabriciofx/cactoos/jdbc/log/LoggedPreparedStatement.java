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
package com.github.fabriciofx.cactoos.jdbc.log;

import com.github.fabriciofx.cactoos.jdbc.PreparedStatementEnvelope;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

/**
 * Logged PreparedStatement.
 *
 * @since 0.1
 * @checkstyle ParameterNameCheck (1500 lines)
 * @checkstyle ParameterNumberCheck (1500 lines)
 * @checkstyle LineLengthCheck (1500 lines)
 * @checkstyle EmptyLineSeparatorCheck (1500 lines)
 */
@SuppressWarnings(
    {
        "PMD.TooManyMethods",
        "PMD.LongVariable",
        "PMD.UseVarargs",
        "PMD.LoggerIsNotStaticFinal",
        "PMD.BooleanGetMethodName",
        "PMD.ExcessivePublicCount",
        "PMD.AvoidDuplicateLiterals",
        "PMD.AvoidUsingShortType"
    }
)
public final class LoggedPreparedStatement extends PreparedStatementEnvelope {
    /**
     * The name of source value.
     */
    private final String source;

    /**
     * The logger.
     */
    private final Logger logger;

    /**
     * The log level.
     */
    private final Level level;

    /**
     * The PreparedStatement id.
     */
    private final int id;

    /**
     * Ctor.
     * @param prepared Decorated PreparedStatement
     * @param src The name of source data
     * @param lggr The logger
     * @param lvl The log level
     * @param id The PreparedStatement id
     */
    public LoggedPreparedStatement(
        final PreparedStatement prepared,
        final String src,
        final Logger lggr,
        final Level lvl,
        final int id
    ) {
        super(prepared);
        this.source = src;
        this.logger = lggr;
        this.level = lvl;
        this.id = id;
    }
    @Override
    public ResultSet executeQuery() throws SQLException {
        final Instant start = Instant.now();
        final ResultSet rset = super.executeQuery();
        final Instant end = Instant.now();
        final long millis = Duration.between(start, end).toMillis();
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] retrieved a ResultSet in %dms.",
                    this.source,
                    this.id,
                    millis
                )
            ).asString()
        );
        return rset;
    }
    @Override
    public int executeUpdate() throws SQLException {
        final Instant start = Instant.now();
        final int updated = super.executeUpdate();
        final Instant end = Instant.now();
        final long millis = Duration.between(start, end).toMillis();
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] updated a source and returned '%d' in %dms.",
                    this.source,
                    this.id,
                    updated,
                    millis
                )
            ).asString()
        );
        return updated;
    }
    @Override
    public void setNull(final int parameterIndex, final int sqlType) throws SQLException {
        super.setNull(parameterIndex, sqlType);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] changed at parameter[#%d] with '%d' value.",
                    this.source,
                    this.id,
                    parameterIndex,
                    sqlType
                )
            ).asString()
        );
    }
    @Override
    public void setBoolean(final int parameterIndex, final boolean x) throws SQLException {
        super.setBoolean(parameterIndex, x);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] changed at parameter[#%d] with '%s' value.",
                    this.source,
                    this.id,
                    parameterIndex,
                    x
                )
            ).asString()
        );
    }
    @Override
    public void setByte(final int parameterIndex, final byte x) throws SQLException {
        super.setByte(parameterIndex, x);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] changed at parameter[#%d] with '%d' value.",
                    this.source,
                    this.id,
                    parameterIndex,
                    x
                )
            ).asString()
        );
    }
    @Override
    public void setShort(final int parameterIndex, final short x) throws SQLException {
        super.setShort(parameterIndex, x);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] changed at parameter[#%d] with '%d' value.",
                    this.source,
                    this.id,
                    parameterIndex,
                    x
                )
            ).asString()
        );
    }
    @Override
    public void setInt(final int parameterIndex, final int x) throws SQLException {
        super.setInt(parameterIndex, x);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] changed at parameter[#%d] with '%d' value.",
                    this.source,
                    this.id,
                    parameterIndex,
                    x
                )
            ).asString()
        );
    }
    @Override
    public void setLong(final int parameterIndex, final long x) throws SQLException {
        super.setLong(parameterIndex, x);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] changed at parameter[#%d] with '%d' value.",
                    this.source,
                    this.id,
                    parameterIndex,
                    x
                )
            ).asString()
        );
    }
    @Override
    public void setFloat(final int parameterIndex, final float x) throws SQLException {
        super.setFloat(parameterIndex, x);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] changed at parameter[#%d] with '%f' value.",
                    this.source,
                    this.id,
                    parameterIndex,
                    x
                )
            ).asString()
        );
    }
    @Override
    public void setDouble(final int parameterIndex, final double x) throws SQLException {
        super.setDouble(parameterIndex, x);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] changed at parameter[#%d] with '%f' value.",
                    this.source,
                    this.id,
                    parameterIndex,
                    x
                )
            ).asString()
        );
    }
    @Override
    public void setBigDecimal(final int parameterIndex, final BigDecimal x) throws SQLException {
        super.setBigDecimal(parameterIndex, x);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] changed at parameter[#%d] with '%s' value.",
                    this.source,
                    this.id,
                    parameterIndex,
                    x.toString()
                )
            ).asString()
        );
    }
    @Override
    public void setString(final int parameterIndex, final String x) throws SQLException {
        super.setString(parameterIndex, x);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] changed at parameter[#%d] with '%s' value.",
                    this.source,
                    this.id,
                    parameterIndex,
                    x
                )
            ).asString()
        );
    }
    @Override
    public void setBytes(final int parameterIndex, final byte[] x) throws SQLException {
        super.setBytes(parameterIndex, x);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] changed at parameter[#%d] with '%d' bytes.",
                    this.source,
                    this.id,
                    parameterIndex,
                    x.length
                )
            ).asString()
        );
    }
    @Override
    public void setDate(final int parameterIndex, final Date x) throws SQLException {
        super.setDate(parameterIndex, x);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] changed at parameter[#%d] with '%s' value.",
                    this.source,
                    this.id,
                    parameterIndex,
                    x.toString()
                )
            ).asString()
        );
    }
    @Override
    public void setTime(final int parameterIndex, final Time x) throws SQLException {
        super.setTime(parameterIndex, x);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] changed at parameter[#%d] with '%s' value.",
                    this.source,
                    this.id,
                    parameterIndex,
                    x.toString()
                )
            ).asString()
        );
    }
    @Override
    public void setTimestamp(final int parameterIndex, final Timestamp x) throws SQLException {
        super.setTimestamp(parameterIndex, x);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] changed at parameter[#%d] with '%s' value.",
                    this.source,
                    this.id,
                    parameterIndex,
                    x.toString()
                )
            ).asString()
        );
    }
    @Override
    public void setAsciiStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
        super.setAsciiStream(parameterIndex, x, length);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] changed at parameter[#%d] with '%d' bytes.",
                    this.source,
                    this.id,
                    parameterIndex,
                    length
                )
            ).asString()
        );
    }
    /**
     * Set a stream to Unicode.
     * @deprecated It not should be used
     * @param parameterIndex Parameter parameterIndex
     * @param x InputStream
     * @param length Data length
     * @throws SQLException If fails
     */
    @Deprecated
    public void setUnicodeStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
        super.setUnicodeStream(parameterIndex, x, length);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] changed at parameter[#%d] with '%d' bytes.",
                    this.source,
                    this.id,
                    parameterIndex,
                    length
                )
            ).asString()
        );
    }
    @Override
    public void setBinaryStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
        super.setBinaryStream(parameterIndex, x, length);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] changed at parameter[#%d] with '%d' bytes.",
                    this.source,
                    this.id,
                    parameterIndex,
                    length
                )
            ).asString()
        );
    }
    @Override
    public void clearParameters() throws SQLException {
        super.clearParameters();
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] parameters has been cleaned.",
                    this.source,
                    this.id
                )
            ).asString()
        );
    }
    @Override
    public void setObject(final int parameterIndex, final Object x, final int targetSqlType) throws SQLException {
        super.setObject(parameterIndex, x, targetSqlType);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] changed at parameter[#%d] with '%s' data and '%d' type.",
                    this.source,
                    this.id,
                    parameterIndex,
                    x.toString(),
                    targetSqlType
                )
            ).asString()
        );
    }
    @Override
    public void setObject(final int parameterIndex, final Object x) throws SQLException {
        super.setObject(parameterIndex, x);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] changed at parameter[#%d] with '%s' value.",
                    this.source,
                    this.id,
                    parameterIndex,
                    x.toString()
                )
            ).asString()
        );
    }
    @Override
    public boolean execute() throws SQLException {
        final Instant start = Instant.now();
        final boolean result = super.execute();
        final Instant end = Instant.now();
        final long millis = Duration.between(start, end).toMillis();
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] returned '%s' in %dms.",
                    this.source,
                    this.id,
                    result,
                    millis
                )
            ).asString()
        );
        return result;
    }
    @Override
    public void addBatch() throws SQLException {
        super.addBatch();
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] added a batch.",
                    this.source,
                    this.id
                )
            ).asString()
        );
    }
    @Override
    public ResultSet executeQuery(final String sql) throws SQLException {
        final Instant start = Instant.now();
        final ResultSet rset = super.executeQuery(sql);
        final Instant end = Instant.now();
        final long millis = Duration.between(start, end).toMillis();
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] executed SQL %s in %dms.",
                    this.source,
                    this.id,
                    sql,
                    millis
                )
            ).asString()
        );
        return rset;
    }
    @Override
    public int executeUpdate(final String sql) throws SQLException {
        final Instant start = Instant.now();
        final int updated = super.executeUpdate(sql);
        final Instant end = Instant.now();
        final long millis = Duration.between(start, end).toMillis();
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] executed SQL %s in %dms.",
                    this.source,
                    this.id,
                    sql,
                    updated,
                    millis
                )
            ).asString()
        );
        return updated;
    }
    @Override
    public void close() throws SQLException {
        super.close();
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] closed.",
                    this.source,
                    this.id
                )
            ).asString()
        );
    }

    @Override
    public void setMaxFieldSize(final int max) throws SQLException {
        super.setMaxFieldSize(max);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] changed max field size to '%d' bytes.",
                    this.source,
                    this.id,
                    max
                )
            ).asString()
        );
    }

    @Override
    public void setMaxRows(final int max) throws SQLException {
        super.setMaxRows(max);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] changed max rows to '%d'.",
                    this.source,
                    this.id,
                    max
                )
            ).asString()
        );
    }

    @Override
    public void setQueryTimeout(final int seconds) throws SQLException {
        super.setQueryTimeout(seconds);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] changed timeout to '%d' seconds.",
                    this.source,
                    this.id,
                    seconds
                )
            ).asString()
        );
    }

    @Override
    public void cancel() throws SQLException {
        super.cancel();
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] canceled.",
                    this.source,
                    this.id
                )
            ).asString()
        );
    }

    @Override
    public boolean execute(final String sql) throws SQLException {
        final Instant start = Instant.now();
        final boolean result = super.execute(sql);
        final Instant end = Instant.now();
        final long millis = Duration.between(start, end).toMillis();
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] executed SQL '%s' in %dms.",
                    this.source,
                    this.id,
                    sql,
                    millis
                )
            ).asString()
        );
        return result;
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        final Instant start = Instant.now();
        final ResultSet rset = super.getResultSet();
        final Instant end = Instant.now();
        final long millis = Duration.between(start, end).toMillis();
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] returned a ResultSet in %dms.",
                    this.source,
                    this.id,
                    millis
                )
            ).asString()
        );
        return rset;
    }

    @Override
    public void addBatch(final String sql) throws SQLException {
        super.addBatch(sql);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] added batch with SQL '%s'.",
                    this.source,
                    this.id,
                    sql
                )
            ).asString()
        );
    }

    @Override
    public int[] executeBatch() throws SQLException {
        final int[] counts = super.executeBatch();
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] returned '%d' counts.",
                    this.source,
                    this.id,
                    counts.length
                )
            ).asString()
        );
        return counts;
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        final Instant start = Instant.now();
        final ResultSet rset = super.getGeneratedKeys();
        final Instant end = Instant.now();
        final long millis = Duration.between(start, end).toMillis();
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] returned a ResultSet keys in %dms.",
                    this.source,
                    this.id,
                    millis
                )
            ).asString()
        );
        return rset;
    }

    @Override
    public void setPoolable(final boolean poolable) throws SQLException {
        super.setPoolable(poolable);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] changed poolable to %s.",
                    this.source,
                    this.id,
                    poolable
                )
            ).asString()
        );
    }

    @Override
    public void closeOnCompletion() throws SQLException {
        super.closeOnCompletion();
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] will be closed on completion.",
                    this.source,
                    this.id
                )
            ).asString()
        );
    }
}
