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
package com.github.fabriciofx.cactoos.jdbc.agenda.sql;

import com.github.fabriciofx.cactoos.jdbc.Session;
import com.github.fabriciofx.cactoos.jdbc.agenda.Phone;
import com.github.fabriciofx.cactoos.jdbc.agenda.Phones;
import com.github.fabriciofx.cactoos.jdbc.query.KeyedQuery;
import com.github.fabriciofx.cactoos.jdbc.query.SimpleQuery;
import com.github.fabriciofx.cactoos.jdbc.query.param.TextParam;
import com.github.fabriciofx.cactoos.jdbc.query.param.UuidParam;
import com.github.fabriciofx.cactoos.jdbc.rset.ResultAsValue;
import com.github.fabriciofx.cactoos.jdbc.rset.ResultSetAsValue;
import com.github.fabriciofx.cactoos.jdbc.rset.ResultSetAsValues;
import com.github.fabriciofx.cactoos.jdbc.stmt.InsertWithKeys;
import com.github.fabriciofx.cactoos.jdbc.stmt.Select;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.cactoos.Scalar;
import org.cactoos.text.FormattedText;
import org.cactoos.text.JoinedText;

/**
 * Phones for SQL.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.1
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings(
    {
        "PMD.AvoidCatchingGenericException",
        "PMD.AvoidInstantiatingObjectsInLoops",
        "PMD.AvoidDuplicateLiterals",
        "PMD.AvoidThrowingRawExceptionTypes"
    }
)
public final class SqlPhones implements Phones {
    /**
     * Session.
     */
    private final Session session;

    /**
     * Contact's ID.
     */
    private final UUID contact;

    /**
     * Ctor.
     * @param sssn A Session
     * @param contact A Contact's ID
     */
    public SqlPhones(final Session sssn, final UUID contact) {
        this.session = sssn;
        this.contact = contact;
    }

    @Override
    public int count() throws Exception {
        return new ResultSetAsValue<Integer>(
            new Select(
                this.session,
                new SimpleQuery(
                    "SELECT COUNT(number) FROM phone WHERE contact = :contact",
                    new UuidParam("contact", this.contact)
                )
            )
        ).value();
    }

    @Override
    public Phone get(final int index) throws Exception {
        final Scalar<Integer> seq = new ResultSetAsValue<>(
            new Select(
                this.session,
                new SimpleQuery(
                    new FormattedText(
                        new JoinedText(
                            " ",
                            "SELECT seq FROM phone WHERE contact = :contact",
                            "FETCH FIRST %d ROWS ONLY"
                        ),
                        index
                    )
                )
            )
        );
        return new SqlPhone(this.session, this.contact, seq.value());
    }

    @Override
    public Iterator<Phone> iterator() {
        try {
            final Scalar<List<Integer>> seqs = new ResultSetAsValues<>(
                new Select(
                    this.session,
                    new SimpleQuery(
                        "SELECT seq FROM phone WHERE contact = :contact",
                        new UuidParam("contact", this.contact)
                    )
                )
            );
            final List<Phone> list = new LinkedList<>();
            for (final int seq : seqs.value()) {
                list.add(new SqlPhone(this.session, this.contact, seq));
            }
            return list.iterator();
            // @checkstyle IllegalCatchCheck (1 line)
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
