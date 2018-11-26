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
import com.github.fabriciofx.cactoos.jdbc.SmartQueryParams;
import com.github.fabriciofx.cactoos.jdbc.query.BatchQuery;
import com.github.fabriciofx.cactoos.jdbc.query.SimpleQuery;
import com.github.fabriciofx.cactoos.jdbc.query.param.BoolParam;
import com.github.fabriciofx.cactoos.jdbc.query.param.DateParam;
import com.github.fabriciofx.cactoos.jdbc.query.param.DecimalParam;
import com.github.fabriciofx.cactoos.jdbc.query.param.IntParam;
import com.github.fabriciofx.cactoos.jdbc.query.param.TextParam;
import com.github.fabriciofx.cactoos.jdbc.rset.ResultSetAsValue;
import com.github.fabriciofx.cactoos.jdbc.rset.ResultSetAsXml;
import com.github.fabriciofx.cactoos.jdbc.server.H2Server;
import com.github.fabriciofx.cactoos.jdbc.server.MysqlServer;
import com.github.fabriciofx.cactoos.jdbc.server.PsqlServer;
import com.jcabi.matchers.XhtmlMatchers;
import java.time.LocalDate;
import org.cactoos.text.JoinedText;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.llorllale.cactoos.matchers.ScalarHasValue;

/**
 * Select tests.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings(
    {
        "PMD.AvoidInstantiatingObjectsInLoops",
        "PMD.AvoidDuplicateLiterals"
    }
)
public final class SelectTest {
    @Test
    public void select() throws Exception {
        try (
            Servers servers = new Servers(
                new H2Server(),
                new MysqlServer(),
                new PsqlServer()
            )
        ) {
            for (final Session session : servers.sessions()) {
                new Update(
                    session,
                    new SimpleQuery(
                        new JoinedText(
                            " ",
                            "CREATE TABLE employee (id INT,",
                            "name VARCHAR(50), birthday DATE,",
                            "address VARCHAR(100),",
                            "married BOOLEAN, salary DECIMAL(20,2),",
                            "PRIMARY KEY (id))"
                        )
                    )
                ).result();
                new Batch(
                    session,
                    new BatchQuery(
                        new JoinedText(
                            " ",
                            "INSERT INTO employee",
                            "(id, name, birthday, address, married, salary)",
                            "VALUES (:id, :name, :birthday, :address,",
                            ":married, :salary)"
                        ),
                        new SmartQueryParams(
                            new IntParam("id", 1),
                            new TextParam("name", "John Wick"),
                            new DateParam("birthday", "1980-08-15"),
                            new TextParam("address", "Boulevard Street, 34"),
                            new BoolParam("married", false),
                            new DecimalParam("salary", "13456.00")
                        ),
                        new SmartQueryParams(
                            new IntParam("id", 2),
                            new TextParam("name", "Adam Park"),
                            new DateParam("birthday", "1985-07-09"),
                            new TextParam("address", "Sunset Place, 14"),
                            new BoolParam("married", true),
                            new DecimalParam("salary", "12345.00")
                        )
                    )
                ).result();
                MatcherAssert.assertThat(
                    XhtmlMatchers.xhtml(
                        new ResultSetAsXml(
                            new Select(
                                session,
                                new SimpleQuery(
                                    "SELECT * FROM employee"
                                )
                            ),
                            "employee"
                        ).value()
                    ),
                    XhtmlMatchers.hasXPaths(
                        "/employee/id[text()='1']",
                        "/employee/name[text()='John Wick']",
                        "/employee/address[text()='Boulevard Street, 34']",
                        "/employee/married[text()='false']",
                        "/employee/salary[text()='13456.00']",
                        "/employee/id[text()='2']",
                        "/employee/name[text()='Adam Park']",
                        "/employee/address[text()='Sunset Place, 14']",
                        "/employee/married[text()='true']",
                        "/employee/salary[text()='12345.00']"
                    )
                );
            }
        }
    }

    @Test
    public void any() throws Exception {
        try (
            Servers servers = new Servers(
                new H2Server(),
                new MysqlServer(),
                new PsqlServer()
            )
        ) {
            for (final Session session : servers.sessions()) {
                new Update(
                    session,
                    new SimpleQuery(
                        new JoinedText(
                            " ",
                            "CREATE TABLE person (id INT, name VARCHAR(30),",
                            "created_at DATE, city VARCHAR(20),",
                            "working BOOLEAN, height DECIMAL(20,2),",
                            "PRIMARY KEY (id))"
                        )
                    )
                ).result();
                new Batch(
                    session,
                    new BatchQuery(
                        new JoinedText(
                            " ",
                            "INSERT INTO person",
                            "(id, name, created_at, city, working, height)",
                            "VALUES (:id, :name, :created_at, :city,",
                            ":working, :height)"
                        ),
                        new SmartQueryParams(
                            new IntParam("id", 1),
                            new TextParam("name", "Rob Pike"),
                            new DateParam("created_at", LocalDate.now()),
                            new TextParam("city", "San Francisco"),
                            new BoolParam("working", true),
                            new DecimalParam("height", "1.86")
                        ),
                        new SmartQueryParams(
                            new IntParam("id", 2),
                            new TextParam("name", "Ana Pivot"),
                            new DateParam("created_at", LocalDate.now()),
                            new TextParam("city", "Washington"),
                            new BoolParam("working", false),
                            new DecimalParam("height", "1.62")
                        )
                    )
                ).result();
                MatcherAssert.assertThat(
                    "Can't select a person name",
                    new ResultSetAsValue<>(
                        new Select(
                            session,
                            new SimpleQuery(
                                "SELECT name FROM person"
                            )
                        )
                    ),
                    new ScalarHasValue<>("Rob Pike")
                );
            }
        }
    }
}
