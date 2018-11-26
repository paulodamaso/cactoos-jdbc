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
import com.github.fabriciofx.cactoos.jdbc.agenda.Contact;
import com.github.fabriciofx.cactoos.jdbc.agenda.Phones;
import com.github.fabriciofx.cactoos.jdbc.query.SimpleQuery;
import com.github.fabriciofx.cactoos.jdbc.query.param.TextParam;
import com.github.fabriciofx.cactoos.jdbc.query.param.UuidParam;
import com.github.fabriciofx.cactoos.jdbc.rset.ResultSetAsXml;
import com.github.fabriciofx.cactoos.jdbc.stmt.Insert;
import com.github.fabriciofx.cactoos.jdbc.stmt.Select;
import com.github.fabriciofx.cactoos.jdbc.stmt.Update;
import java.util.Map;
import java.util.UUID;
import org.cactoos.text.FormattedText;
import org.cactoos.text.JoinedText;

/**
 * Contact for SQL.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.1
 */
public final class SqlContact implements Contact {
    /**
     * Session.
     */
    private final Session session;

    /**
     * Contact's ID.
     */
    private final UUID id;

    /**
     * Ctor.
     * @param sssn A Session
     * @param id A Contact's ID
     */
    public SqlContact(final Session sssn, final UUID id) {
        this.session = sssn;
        this.id = id;
    }

    @Override
    public String about() throws Exception {
        final String contact = new ResultSetAsXml(
            new Select(
                this.session,
                new SimpleQuery(
                    "SELECT name FROM contact WHERE id = :id",
                    new UuidParam("id", this.id)
                )
            ),
            "contact"
        ).value();
        final String phones = new ResultSetAsXml(
            new Select(
                this.session,
                new SimpleQuery(
                    "SELECT number, carrier FROM phone WHERE contact = :contact",
                    new UuidParam("contact", this.id)
                )
            ),
            "phone"
        ).value();
    }

    @Override
    public Phones phones() throws Exception {
        return new SqlPhones(this.session, this.id);
    }

    @Override
    public void phone(final Map<String, String> properties) throws Exception {
        new Insert(
            this.session,
            new SimpleQuery(
                new JoinedText(
                    " ",
                    "INSERT INTO phone (contact, number, carrier)",
                    "VALUES (:contact, :number, :carrier)"
                ),
                new UuidParam("contact", this.id),
                new TextParam("number", properties.get("number")),
                new TextParam("carrier", properties.get("carrier"))
            )
        ).result();
    }

    @Override
    public void delete() throws Exception {
        new Update(
            this.session,
            new SimpleQuery(
                "DELETE FROM contact WHERE id = :id",
                new UuidParam("id", this.id)
            )
        ).result();
    }

    @Override
    public void update(final Map<String, String> properties) throws Exception {
        new Update(
            this.session,
            new SimpleQuery(
                "UPDATE contact SET name = :name WHERE id = :id",
                new TextParam("name", properties.get("name"))
            )
        ).result();
    }
}
