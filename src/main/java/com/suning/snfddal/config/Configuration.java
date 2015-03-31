/*
 * Copyright 2014 suning.com Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// Created on 2014年3月25日
// $Id$

package com.suning.snfddal.config;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import com.suning.snfddal.route.rule.TableRouter;
import com.suning.snfddal.util.New;

/**
 * @author <a href="mailto:jorgie.mail@gmail.com">jorgie li</a>
 */
public class Configuration {
    
    
    private static Configuration instance = new Configuration();
    
    public static Configuration getInstance() {
        return instance;
    }

    // ddal-config
    private final Properties settings = new Properties();

    private SchemaConfig schemaConfig = new SchemaConfig();

    private final Map<String, ShardConfig> cluster = New.hashMap();

    private final Map<String, DataSource> dataNodes = New.hashMap();
    
    private final Map<String, TableRouter> tableRouters = New.hashMap();

    private final Map<String, Object> ruleAlgorithms = New.hashMap();
    
    private Configuration() {
    }

    public Set<String> getShardNames() {
        return cluster.keySet();
    }

    /**
     * @return the schemaConfig
     */
    public SchemaConfig getSchemaConfig() {
        return schemaConfig;
    }

    /**
     * @param schemaConfig the schemaConfig to set
     */
    public void setSchemaConfig(SchemaConfig schemaConfig) {
        this.schemaConfig = schemaConfig;
    }

    /**
     * @return the settings
     */
    public Properties getSettings() {
        return settings;
    }

    /**
     * @param settings the settings to set
     */
    public void addSettings(String name, String value) {
        if (settings.containsKey(name)) {
            throw new ConfigurationException("Duplicate settings name " + name);
        }
        settings.put(name, value);
    }
    
    /**
     * Get the value of the given property.
     *
     * @param key the property key
     * @return the value as a String
     */
    String getProperty(String key) {
        Object value = settings.get(key);
        if (value == null || !(value instanceof String)) {
            return null;
        }
        return value.toString();
    }

    /**
     * Get the value of the given property.
     *
     * @param key the property key
     * @param defaultValue the default value
     * @return the value as a String
     */
    public int getProperty(String key, int defaultValue) {
        String s = getProperty(key);
        return s == null ? defaultValue : Integer.parseInt(s);
    }

    /**
     * Get the value of the given property.
     *
     * @param key the property key
     * @param defaultValue the default value
     * @return the value as a String
     */
    public String getProperty(String key, String defaultValue) {
        String s = getProperty(key);
        return s == null ? defaultValue : s;
    }



    /**
     * @return the cluster
     */
    public Map<String, ShardConfig> getCluster() {
        return cluster;
    }

    /**
     * @param cluster the cluster to set
     */
    public void addShard(String name, ShardConfig shard) {
        if (cluster.containsKey(name)) {
            throw new ConfigurationException("Duplicate shard name " + name);
        }
        cluster.put(name, shard);
    }

    /**
     * @return the dataNodes
     */
    public Map<String, DataSource> getDataNodes() {
        return dataNodes;
    }

    public void addDataNode(String id, DataSource dataSource) {
        if (dataNodes.containsKey(id)) {
            throw new ConfigurationException("Duplicate datasource id " + id);
        }
        dataNodes.put(id, dataSource);
    }
    
    public void addTableRouter(String id, TableRouter tableRule) {
        if (tableRouters.containsKey(id)) {
            throw new ConfigurationException("Duplicate table router id " + id);
        }
        tableRouters.put(id, tableRule);
    }
    /**
     * @return the tableRules
     */
    public Map<String, TableRouter> getTableRouters() {
        return tableRouters;
    }

    public void addRuleAlgorithm(String id, TableRouter tableRouter) {
        if (tableRouters.containsKey(id)) {
            throw new ConfigurationException("Duplicate table rule id " + id);
        }
        tableRouters.put(id, tableRouter);
    }

    /**
     * @return the ruleAlgorithms
     */
    public Map<String, Object> getRuleAlgorithms() {
        return ruleAlgorithms;
    }

    public void addRuleAlgorithm(String name, Object ruleAlgorithm) {
        if (ruleAlgorithms.containsKey(name)) {
            throw new ConfigurationException("Duplicate ruleAlgorithm name " + name);
        }
        ruleAlgorithms.put(name, ruleAlgorithm);
    }
}
