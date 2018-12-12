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
import com.github.fabriciofx.cactoos.jdbc.query.QueryInBatch;
import com.github.fabriciofx.cactoos.jdbc.query.QuerySimple;
import com.github.fabriciofx.cactoos.jdbc.query.param.ParamBool;
import com.github.fabriciofx.cactoos.jdbc.query.param.ParamDate;
import com.github.fabriciofx.cactoos.jdbc.query.param.ParamDecimal;
import com.github.fabriciofx.cactoos.jdbc.query.param.ParamInt;
import com.github.fabriciofx.cactoos.jdbc.query.param.ParamText;
import com.github.fabriciofx.cactoos.jdbc.rset.ResultSetAsValue;
import com.github.fabriciofx.cactoos.jdbc.rset.ResultSetAsXml;
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
                    new QueryInBatch(
                        new JoinedText(
                            " ",
                            "INSERT INTO employee",
                            "(id, name, birthday, address, married, salary)",
                            "VALUES (:id, :name, :birthday, :address,",
                            ":married, :salary)"
                        ),
                        new QueryParamsSmart(
                            new ParamInt("id", 1),
                            new ParamText("name", "John Wick"),
                            new ParamDate("birthday", "1980-08-15"),
                            new ParamText("address", "Boulevard Street, 34"),
                            new ParamBool("married", false),
                            new ParamDecimal("salary", "13456.00")
                        ),
                        new QueryParamsSmart(
                            new ParamInt("id", 2),
                            new ParamText("name", "Adam Park"),
                            new ParamDate("birthday", "1985-07-09"),
                            new ParamText("address", "Sunset Place, 14"),
                            new ParamBool("married", true),
                            new ParamDecimal("salary", "12345.00")
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
                    new QueryInBatch(
                        new JoinedText(
                            " ",
                            "INSERT INTO person",
                            "(id, name, created_at, city, working, height)",
                            "VALUES (:id, :name, :created_at, :city,",
                            ":working, :height)"
                        ),
                        new QueryParamsSmart(
                            new ParamInt("id", 1),
                            new ParamText("name", "Rob Pike"),
                            new ParamDate("created_at", LocalDate.now()),
                            new ParamText("city", "San Francisco"),
                            new ParamBool("working", true),
                            new ParamDecimal("height", "1.86")
                        ),
                        new QueryParamsSmart(
                            new ParamInt("id", 2),
                            new ParamText("name", "Ana Pivot"),
                            new ParamDate("created_at", LocalDate.now()),
                            new ParamText("city", "Washington"),
                            new ParamBool("working", false),
                            new ParamDecimal("height", "1.62")
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
