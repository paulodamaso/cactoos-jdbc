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

import com.github.fabriciofx.cactoos.jdbc.Rows;
import com.github.fabriciofx.cactoos.jdbc.Session;
import com.github.fabriciofx.cactoos.jdbc.SmartQueryParams;
import com.github.fabriciofx.cactoos.jdbc.cache.CachedResultSet;
import com.github.fabriciofx.cactoos.jdbc.query.BatchQuery;
import com.github.fabriciofx.cactoos.jdbc.query.SimpleQuery;
import com.github.fabriciofx.cactoos.jdbc.query.param.BoolParam;
import com.github.fabriciofx.cactoos.jdbc.query.param.DateParam;
import com.github.fabriciofx.cactoos.jdbc.query.param.DecimalParam;
import com.github.fabriciofx.cactoos.jdbc.query.param.IntParam;
import com.github.fabriciofx.cactoos.jdbc.query.param.TextParam;
import com.github.fabriciofx.cactoos.jdbc.row.FilteredRows;
import com.github.fabriciofx.cactoos.jdbc.rset.ResultSetAsRows;
import com.github.fabriciofx.cactoos.jdbc.rset.ResultSetAsXml;
import com.github.fabriciofx.cactoos.jdbc.session.CachedSession;
import com.github.fabriciofx.cactoos.jdbc.session.NoAuthSession;
import com.github.fabriciofx.cactoos.jdbc.source.H2Source;
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
        final Session session = new NoAuthSession(
            new H2Source("testdb")
        );
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
        final ResultSet rset = new Select(
            session,
            new SimpleQuery("SELECT * FROM employee")
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
