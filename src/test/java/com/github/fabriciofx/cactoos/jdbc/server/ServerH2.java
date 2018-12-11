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
package com.github.fabriciofx.cactoos.jdbc.server;

import com.github.fabriciofx.cactoos.jdbc.RandomDatabaseName;
import com.github.fabriciofx.cactoos.jdbc.ScriptSql;
import com.github.fabriciofx.cactoos.jdbc.Server;
import com.github.fabriciofx.cactoos.jdbc.Session;
import com.github.fabriciofx.cactoos.jdbc.session.SessionWithoutAuth;
import com.github.fabriciofx.cactoos.jdbc.source.SourceH2;
import java.io.IOException;
import org.cactoos.scalar.StickyScalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * MySQL server, for unit testing.
 *
 * @since 0.2
 */
public final class ServerH2 implements Server {
    /**
     * The Database name.
     */
    private final UncheckedScalar<String> dbname;

    /**
     * SQL Script to initialize the database.
     */
    private final ScriptSql script;

    /**
     * Ctor.
     */
    public ServerH2() {
        this(ScriptSql.NOP);
    }

    /**
     * Ctor.
     * @param scrpt ScriptSql to initialize the database.
     */
    public ServerH2(final ScriptSql scrpt) {
        this.dbname = new UncheckedScalar<>(
            new StickyScalar<>(
                () -> new RandomDatabaseName().asString()
            )
        );
        this.script = scrpt;
    }

    @Override
    public void start() throws Exception {
        this.script.run(this.session());
    }

    @Override
    public void stop() throws Exception {
        // Intended empty.
    }

    @Override
    public Session session() {
        return new SessionWithoutAuth(
            new SourceH2(this.dbname.value())
        );
    }

    @Override
    public void close() throws IOException {
        // Intended empty.
    }
}