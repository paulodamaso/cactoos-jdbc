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
package com.github.fabriciofx.cactoos.jdbc.rows;

import com.github.fabriciofx.cactoos.jdbc.QueryParamsSmart;
import com.github.fabriciofx.cactoos.jdbc.Rows;
import com.github.fabriciofx.cactoos.jdbc.Session;
import com.github.fabriciofx.cactoos.jdbc.adapter.ResultSetAsRows;
import com.github.fabriciofx.cactoos.jdbc.query.QueryInBatch;
import com.github.fabriciofx.cactoos.jdbc.query.QuerySimple;
import com.github.fabriciofx.cactoos.jdbc.query.param.QueryParamBool;
import com.github.fabriciofx.cactoos.jdbc.query.param.QueryParamDate;
import com.github.fabriciofx.cactoos.jdbc.query.param.QueryParamDecimal;
import com.github.fabriciofx.cactoos.jdbc.query.param.QueryParamInt;
import com.github.fabriciofx.cactoos.jdbc.query.param.QueryParamText;
import com.github.fabriciofx.cactoos.jdbc.row.FilteredRows;
import com.github.fabriciofx.cactoos.jdbc.session.SessionWithoutAuth;
import com.github.fabriciofx.cactoos.jdbc.source.SourceH2;
import com.github.fabriciofx.cactoos.jdbc.stmt.Batch;
import com.github.fabriciofx.cactoos.jdbc.stmt.Select;
import com.github.fabriciofx.cactoos.jdbc.stmt.Update;
import java.sql.ResultSet;
import java.util.Map;
import org.cactoos.text.JoinedText;
import org.junit.Test;

public final class ResultSetAsRowsTest {
    @Test
    public void rows() throws Exception {
        final Session session = new SessionWithoutAuth(
            new SourceH2("testdb")
        );
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
        final ResultSet rset = new Select(
            session,
            new QuerySimple("SELECT * FROM employee")
        ).result();
        final Rows rows = new ResultSetAsRows(rset);
        for (final Map<String, Object> row : rows) {
            for (final String key : row.keySet()) {
                System.out.println(
                    String.format(
                        "key: %s, value: %s",
                        key,
                        row.get(key)
                    )
                );
            }
        }
        System.out.println("filtered-----");
        final Rows filtered = new FilteredRows(rows, "name", "maried");
        for (final Map<String, Object> row : filtered) {
            for (final String key : row.keySet()) {
                System.out.println(
                    String.format(
                        "key: %s, value: %s",
                        key,
                        row.get(key)
                    )
                );
            }
        }
    }
}
