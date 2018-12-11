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
import com.github.fabriciofx.cactoos.jdbc.query.QuerySimple;
import com.github.fabriciofx.cactoos.jdbc.query.param.ParamInt;
import com.github.fabriciofx.cactoos.jdbc.query.param.ParamText;
import com.github.fabriciofx.cactoos.jdbc.query.param.ParamUuid;
import com.github.fabriciofx.cactoos.jdbc.stmt.Update;
import java.util.Map;
import java.util.UUID;
import org.cactoos.text.JoinedText;

/**
 * Phone for SQL.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.1
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class PhoneSql implements Phone {
    /**
     * Session.
     */
    private final Session session;

    /**
     * Contact's ID.
     */
    private final UUID contact;

    /**
     * Sequential number.
     */
    private final int seq;

    /**
     * Ctor.
     * @param sssn A Session
     * @param contact Contact's ID
     * @param seq Sequential number
     */
    public PhoneSql(final Session sssn, final UUID contact, final int seq) {
        this.session = sssn;
        this.contact = contact;
        this.seq = seq;
    }

    @Override
    public void delete() throws Exception {
        new Update(
            this.session,
            new QuerySimple(
                "DELETE FROM phone WHERE (contact = :contact) AND (seq = :seq)",
                new ParamUuid("contact", this.contact),
                new ParamInt("seq", this.seq)
            )
        ).result();
    }

    @Override
    public void update(final Map<String, String> properties) throws Exception {
        new Update(
            this.session,
            new QuerySimple(
                new JoinedText(
                    " ",
                    "UPDATE phone SET number = :number, carrier = :carrier",
                    "WHERE (contact = :contact) AND (seq = :seq)"
                ),
                new ParamText("number", properties.get("number")),
                new ParamText("carrier", properties.get("carrier")),
                new ParamUuid("contact", this.contact),
                new ParamInt("seq", this.seq)
            )
        ).result();
    }
}
