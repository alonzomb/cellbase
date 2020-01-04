/*
 * Copyright 2015-2020 OpenCB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opencb.cellbase.core.common.regulatory;

// Generated Jun 5, 2012 6:41:13 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Pwm generated by hbm2java.
 */
public class Pwm implements java.io.Serializable {

    private int pwmId;
    private String accession;
    private String tfName;
    private String type;
    private String description;
    private String frequencies;
    private int length;
    private String source;
    private Set<Tfbs> tfbses = new HashSet<Tfbs>(0);

    public Pwm() {
    }

    public Pwm(int pwmId, String accession, String tfName, String type, String description, String frequencies,
               int length, String source) {
        this.pwmId = pwmId;
        this.accession = accession;
        this.tfName = tfName;
        this.type = type;
        this.description = description;
        this.frequencies = frequencies;
        this.length = length;
        this.source = source;
    }

    public Pwm(int pwmId, String accession, String tfName, String type,
               String description, String frequencies, int length, String source,
               Set<Tfbs> tfbses) {
        this.pwmId = pwmId;
        this.accession = accession;
        this.tfName = tfName;
        this.type = type;
        this.description = description;
        this.frequencies = frequencies;
        this.length = length;
        this.source = source;
        this.tfbses = tfbses;
    }

    public int getPwmId() {
        return this.pwmId;
    }

    public void setPwmId(int pwmId) {
        this.pwmId = pwmId;
    }

    public String getAccession() {
        return this.accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public String getTfName() {
        return this.tfName;
    }

    public void setTfName(String tfName) {
        this.tfName = tfName;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrequencies() {
        return this.frequencies;
    }

    public void setFrequencies(String frequencies) {
        this.frequencies = frequencies;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Set<Tfbs> getTfbses() {
        return this.tfbses;
    }

    public void setTfbses(Set<Tfbs> tfbses) {
        this.tfbses = tfbses;
    }

}