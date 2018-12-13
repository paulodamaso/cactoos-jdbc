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

import com.github.fabriciofx.cactoos.jdbc.QueryParamsSmart;
import com.github.fabriciofx.cactoos.jdbc.Servers;
import com.github.fabriciofx.cactoos.jdbc.Session;
import com.github.fabriciofx.cactoos.jdbc.adapter.ResultSetAsValue;
import com.github.fabriciofx.cactoos.jdbc.adapter.ResultSetAsXml;
import com.github.fabriciofx.cactoos.jdbc.query.QueryForBatch;
import com.github.fabriciofx.cactoos.jdbc.query.QuerySimple;
import com.github.fabriciofx.cactoos.jdbc.query.param.QueryParamBool;
import com.github.fabriciofx.cactoos.jdbc.query.param.QueryParamDate;
import com.github.fabriciofx.cactoos.jdbc.query.param.QueryParamDecimal;
import com.github.fabriciofx.cactoos.jdbc.query.param.QueryParamInt;
import com.github.fabriciofx.cactoos.jdbc.query.param.QueryParamText;
import com.github.fabriciofx.cactoos.jdbc.server.ServerH2;
import com.github.fabriciofx.cactoos.jdbc.server.ServerMysql;
import com.github.fabriciofx.cactoos.jdbc.server.ServerPsql;
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
                new ServerH2(),
                new ServerMysql(),
                new ServerPsql()
            )
        ) {
            for (final Session session : servers.sessions()) {
                new Update(
                    session,
                    new QuerySimple(
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
                    new QueryForBatch(
                        new JoinedText(
                            " ",
                            "INSERT INTO employee",
                            "(id, name, birthday, address, married, salary)",
                            "VALUES (:id, :name, :birthday, :address,",
                            ":married, :salary)"
                        ),
                        new QueryParamsSmart(
                            new QueryParamInt("id", 1),
                            new QueryParamText("name", "John Wick"),
                            new QueryParamDate("birthday", "1980-08-15"),
                            new QueryParamText("address", "Boulevard Street, 34"),
                            new QueryParamBool("married", false),
                            new QueryParamDecimal("salary", "13456.00")
                        ),
                        new QueryParamsSmart(
                            new QueryParamInt("id", 2),
                            new QueryParamText("name", "Adam Park"),
                            new QueryParamDate("birthday", "1985-07-09"),
                            new QueryParamText("address", "Sunset Place, 14"),
                            new QueryParamBool("married", true),
                            new QueryParamDecimal("salary", "12345.00")
                        )
                    )
                ).result();
                MatcherAssert.assertThat(
                    XhtmlMatchers.xhtml(
                        new ResultSetAsXml(
                            new Select(
                                session,
                                new QuerySimple(
                                    "SELECT * FROM employee"
                                )
                            ),
                            "employees",
                            "employee"
                        ).value()
                    ),
                    XhtmlMatchers.hasXPaths(
                        "/employees/employee/id[text()='1']",
                        "/employees/employee/name[text()='John Wick']",
                        String.join(
                            "",
                            "/employees/employee/address[text()=",
                            "'Boulevard Street, 34']"
                        ),
                        "/employees/employee/married[text()='false']",
                        "/employees/employee/salary[text()='13456.00']",
                        "/employees/employee/id[text()='2']",
                        "/employees/employee/name[text()='Adam Park']",
                        String.join(
                            "",
                            "/employees/employee/address[text()=",
                            "'Sunset Place, 14']"
                        ),
                        "/employees/employee/married[text()='true']",
                        "/employees/employee/salary[text()='12345.00']"
                    )
                );
            }
        }
    }

    @Test
    public void any() throws Exception {
        try (
            Servers servers = new Servers(
                new ServerH2(),
                new ServerMysql(),
                new ServerPsql()
            )
        ) {
            for (final Session session : servers.sessions()) {
                new Update(
                    session,
                    new QuerySimple(
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
                    new QueryForBatch(
                        new JoinedText(
                            " ",
                            "INSERT INTO person",
                            "(id, name, created_at, city, working, height)",
                            "VALUES (:id, :name, :created_at, :city,",
                            ":working, :height)"
                        ),
                        new QueryParamsSmart(
                            new QueryParamInt("id", 1),
                            new QueryParamText("name", "Rob Pike"),
                            new QueryParamDate("created_at", LocalDate.now()),
                            new QueryParamText("city", "San Francisco"),
                            new QueryParamBool("working", true),
                            new QueryParamDecimal("height", "1.86")
                        ),
                        new QueryParamsSmart(
                            new QueryParamInt("id", 2),
                            new QueryParamText("name", "Ana Pivot"),
                            new QueryParamDate("created_at", LocalDate.now()),
                            new QueryParamText("city", "Washington"),
                            new QueryParamBool("working", false),
                            new QueryParamDecimal("height", "1.62")
                        )
                    )
                ).result();
                MatcherAssert.assertThat(
                    "Can't rows a person name",
                    new ResultSetAsValue<>(
                        new Select(
                            session,
                            new QuerySimple(
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
