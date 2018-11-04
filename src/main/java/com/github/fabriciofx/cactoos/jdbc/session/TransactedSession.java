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
package com.github.fabriciofx.cactoos.jdbc.session;

import com.github.fabriciofx.cactoos.jdbc.Session;
import com.github.fabriciofx.cactoos.jdbc.txn.TransactedConnection;
import java.sql.Connection;
import org.cactoos.Scalar;
import org.cactoos.scalar.StickyScalar;

/**
 * Transacted session.
 *
 * <p>Produces a {@link java.sql.Connection} that only closes on commit() or
 * rollback()</p>
 *
 * @since 0.1
 */
public final class TransactedSession implements Session {
    /**
     * Holded txn.
     */
    private final Scalar<Connection> scalar;

    /**
     * Ctor.
     * @param session Session
     */
    public TransactedSession(final Session session) {
        this.scalar = new StickyScalar<>(
            () -> {
                final Connection connection = session.connection();
                connection.setAutoCommit(false);
                return new TransactedConnection(connection);
            }
        );
    }

    @Override
    public Connection connection() throws Exception {
        return this.scalar.value();
    }
}
