/*
 * Copyright 2004-2014 H2 Group. Multiple-Licensed under the MPL 2.0,
 * and the EPL 1.0 (http://h2database.com/html/license.html).
 * Initial Developer: H2 Group
 */
package com.suning.snfddal.result;

import com.suning.snfddal.engine.Constants;
import com.suning.snfddal.util.StatementBuilder;
import com.suning.snfddal.value.Value;

/**
 * Represents a simple row without state.
 */
public class SimpleRow implements SearchRow {

    private final Value[] data;
    private long key;
    private int version;
    private int memory;

    public SimpleRow(Value[] data) {
        this.data = data;
    }

    @Override
    public int getColumnCount() {
        return data.length;
    }

    @Override
    public long getKey() {
        return key;
    }

    @Override
    public void setKey(long key) {
        this.key = key;
    }

    @Override
    public void setKeyAndVersion(SearchRow row) {
        key = row.getKey();
        version = row.getVersion();
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public void setValue(int i, Value v) {
        data[i] = v;
    }

    @Override
    public Value getValue(int i) {
        return data[i];
    }

    @Override
    public String toString() {
        StatementBuilder buff = new StatementBuilder("( /* key:");
        buff.append(getKey());
        if (version != 0) {
            buff.append(" v:" + version);
        }
        buff.append(" */ ");
        for (Value v : data) {
            buff.appendExceptFirst(", ");
            buff.append(v == null ? "null" : v.getTraceSQL());
        }
        return buff.append(')').toString();
    }

    @Override
    public int getMemory() {
        if (memory == 0) {
            int len = data.length;
            memory = Constants.MEMORY_OBJECT + len * Constants.MEMORY_POINTER;
            for (int i = 0; i < len; i++) {
                Value v = data[i];
                if (v != null) {
                    memory += v.getMemory();
                }
            }
        }
        return memory;
    }

}
