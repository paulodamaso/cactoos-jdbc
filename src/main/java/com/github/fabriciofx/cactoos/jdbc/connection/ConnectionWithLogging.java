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
package com.github.fabriciofx.cactoos.jdbc.connection;

import com.github.fabriciofx.cactoos.jdbc.ConnectionEnvelope;
import com.github.fabriciofx.cactoos.jdbc.prepared.PreparedStatementWithLogging;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

/**
 * Logged Connection.
 *
 * @since 0.1
 * @checkstyle ParameterNameCheck (700 lines)
 * @checkstyle ParameterNumberCheck (700 lines)
 */
@SuppressWarnings(
    {
        "PMD.TooManyMethods",
        "PMD.LongVariable",
        "PMD.UseVarargs",
        "PMD.LoggerIsNotStaticFinal",
        "PMD.BooleanGetMethodName",
        "PMD.ExcessivePublicCount",
        "PMD.AvoidDuplicateLiterals"
    }
)
public final class ConnectionWithLogging extends ConnectionEnvelope {
    /**
     * The name of source data.
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
     * The connection id.
     */
    private final int num;

    /**
     * The statements id.
     */
    private final AtomicInteger statements;

    /**
     * Ctor.
     *
     * @param connection Decorated connection
     * @param src The name of source data
     * @param lggr The logger
     * @param lvl The log level
     * @param num The connection id
     * @param stmtsId The statement id
     */
    public ConnectionWithLogging(
        final Connection connection,
        final String src,
        final Logger lggr,
        final Level lvl,
        final int num,
        final AtomicInteger stmtsId
    ) {
        super(connection);
        this.source = src;
        this.logger = lggr;
        this.level = lvl;
        this.num = num;
        this.statements = stmtsId;
    }

    @Override
    public Statement createStatement() throws SQLException {
        final Statement stmt = super.createStatement();
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] Statement[#%d] created.",
                    this.source,
                    this.statements.get()
                )
            ).asString()
        );
        return stmt;
    }

    @Override
    public PreparedStatement prepareStatement(final String sql) throws
        SQLException {
        final PreparedStatement stmt = super.prepareStatement(sql);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] PreparedStatement[#%d] created using SQL '%s'.",
                    this.source,
                    this.statements.get(),
                    sql
                )
            ).asString()
        );
        return new PreparedStatementWithLogging(
            stmt,
            this.source,
            this.logger,
            this.level,
            this.statements.getAndIncrement()
        );
    }

    @Override
    public CallableStatement prepareCall(final String sql) throws SQLException {
        final CallableStatement stmt = super.prepareCall(sql);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] CallableStatement[#%d] created using SQL '%s'.",
                    this.source,
                    this.statements.get(),
                    sql
                )
            ).asString()
        );
        return stmt;
    }

    @Override
    public String nativeSQL(final String sql) throws SQLException {
        final String nat = super.nativeSQL(sql);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] SQL '%s' converted to '%s.",
                    this.source,
                    sql,
                    nat
                )
            ).asString()
        );
        return nat;
    }

    @Override
    public void setAutoCommit(final boolean autoCommit) throws SQLException {
        super.setAutoCommit(autoCommit);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] changed to '%s'.",
                    this.source,
                    autoCommit
                )
            ).asString()
        );
    }

    @Override
    public void commit() throws SQLException {
        final Instant start = Instant.now();
        super.commit();
        final Instant end = Instant.now();
        final long millis = Duration.between(start, end).toMillis();
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] executed in %dms.",
                    this.source,
                    millis
                )
            ).asString()
        );
    }

    @Override
    public void rollback() throws SQLException {
        final Instant start = Instant.now();
        super.rollback();
        final Instant end = Instant.now();
        final long millis = Duration.between(start, end).toMillis();
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] executed in %dms.",
                    this.source,
                    millis
                )
            ).asString()
        );
    }

    @Override
    public void close() throws SQLException {
        super.close();
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] Connection[#%d] closed. ",
                    this.source,
                    this.num
                )
            ).asString()
        );
    }

    @Override
    public void setReadOnly(final boolean readOnly) throws SQLException {
        super.setReadOnly(readOnly);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] changed to '%s'. ",
                    this.source,
                    readOnly
                )
            ).asString()
        );
    }

    @Override
    public void setCatalog(final String catalog) throws SQLException {
        super.setCatalog(catalog);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] changed to '%s'. ",
                    this.source,
                    catalog
                )
            ).asString()
        );
    }

    @Override
    public void setTransactionIsolation(final int lvl) throws SQLException {
        super.setTransactionIsolation(lvl);
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] changed to level '%d'. ",
                    this.source,
                    lvl
                )
            ).asString()
        );
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        final SQLWarning warning = super.getWarnings();
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] generated warning due '%s'. ",
                    this.source,
                    warning.getMessage()
                )
            ).asString()
        );
        return warning;
    }

    @Override
    public void clearWarnings() throws SQLException {
        super.clearWarnings();
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    "[%s] warnings cleaned. ",
                    this.source
                )
            ).asString()
        );
    }

    @Override
    public Statement createStatement(
        final int resultSetType,
        final int resultSetConcurrency
    ) throws SQLException {
        final Statement stmt = super.createStatement(
            resultSetType,
            resultSetConcurrency
        );
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    // @checkstyle LineLengthCheck (1 line)
                    "[%s] Statement[#%d] created with type '%d' and concurrency '%d'.",
                    this.source,
                    this.statements.get(),
                    resultSetType,
                    resultSetConcurrency
                )
            ).asString()
        );
        return stmt;
    }

    @Override
    public PreparedStatement prepareStatement(
        final String sql,
        final int resultSetType,
        final int resultSetConcurrency
    ) throws SQLException {
        final PreparedStatement stmt = super.prepareStatement(
            sql,
            resultSetType,
            resultSetConcurrency
        );
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    // @checkstyle LineLengthCheck (1 line)
                    "[%s] PreparedStatement[#%d] created using SQL '%s', type '%d' and concurrency '%d'.",
                    this.source,
                    this.statements.get(),
                    sql,
                    resultSetType,
                    resultSetConcurrency
                )
            ).asString()
        );
        return stmt;
    }

    @Override
    public CallableStatement prepareCall(
        final String sql,
        final int resultSetType,
        final int resultSetConcurrency
    ) throws SQLException {
        final CallableStatement stmt = super.prepareCall(
            sql,
            resultSetType,
            resultSetConcurrency
        );
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    // @checkstyle LineLengthCheck (1 line)
                    "[%s] CallableStatement[#%d] created using SQL '%s', type '%d' and concurrency '%d'.",
                    this.source,
                    this.statements.get(),
                    sql,
                    resultSetType,
                    resultSetConcurrency
                )
            ).asString()
        );
        return stmt;
    }

    @Override
    public PreparedStatement prepareStatement(
        final String sql,
        final int resultSetType,
        final int resultSetConcurrency,
        final int resultSetHoldability
    ) throws SQLException {
        final PreparedStatement stmt = super.prepareStatement(
            sql,
            resultSetType,
            resultSetConcurrency,
            resultSetHoldability
        );
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    // @checkstyle LineLengthCheck (1 line)
                    "[%s] PreparedStatement[#%d] created using SQL '%s', type '%d', concurrency '%d' and holdability '%d'.",
                    this.source,
                    this.statements.get(),
                    sql,
                    resultSetType,
                    resultSetConcurrency,
                    resultSetHoldability
                )
            ).asString()
        );
        return stmt;
    }

    @Override
    public PreparedStatement prepareStatement(
        final String sql,
        final int autoGeneratedKeys
    ) throws SQLException {
        final PreparedStatement stmt = super.prepareStatement(
            sql,
            autoGeneratedKeys
        );
        final String msg;
        if (autoGeneratedKeys == Statement.RETURN_GENERATED_KEYS) {
            msg = "returned generated keys";
        } else {
            msg = "no returned generated keys";
        }
        this.logger.log(
            this.level,
            new UncheckedText(
                new FormattedText(
                    // @checkstyle LineLengthCheck (1 line)
                    "[%s] PreparedStatement[#%d] created using SQL '%s' and %s.",
                    this.source,
                    this.statements.get(),
                    sql,
                    msg
                )
            ).asString()
        );
        return stmt;
    }
}
