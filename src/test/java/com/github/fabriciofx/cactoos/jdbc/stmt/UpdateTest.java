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
package com.github.fabriciofx.cactoos.jdbc.stmt;

import com.github.fabriciofx.cactoos.jdbc.Servers;
import com.github.fabriciofx.cactoos.jdbc.Session;
import com.github.fabriciofx.cactoos.jdbc.query.QuerySimple;
import com.github.fabriciofx.cactoos.jdbc.rset.ResultAsValue;
import com.github.fabriciofx.cactoos.jdbc.server.ServerH2;
import com.github.fabriciofx.cactoos.jdbc.server.ServerMysql;
import com.github.fabriciofx.cactoos.jdbc.server.ServerPsql;
import org.cactoos.text.JoinedText;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.llorllale.cactoos.matchers.ScalarHasValue;

/**
 * Update tests.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings(
    {
        "PMD.AvoidDuplicateLiterals",
        "PMD.AvoidInstantiatingObjectsInLoops"
    }
)
public final class UpdateTest {
    @Test
    public void createTable() throws Exception {
        try (
            Servers servers = new Servers(
                new ServerH2(),
                new ServerMysql(),
                new ServerPsql()
            )
        ) {
            for (final Session session : servers.sessions()) {
                MatcherAssert.assertThat(
                    "Can't create a table",
                    new ResultAsValue<>(
                        new Update(
                            session,
                            new QuerySimple(
                                new JoinedText(
                                    " ",
                                    "CREATE TABLE foo1 (id INT,",
                                    "name VARCHAR(50), PRIMARY KEY (id))"
                                )
                            )
                        )
                    ),
                    new ScalarHasValue<>(0)
                );
            }
        }
    }
}
