package org.ppcis.ccistool.storage;

/**
 * Copyright © Brian Ronald
 * 22/02/15
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */
public class SeptemberGuarantee {
    public SeptemberGuarantee() {
        year12 = new Guarantee();
        year11 = new Guarantee();
    }

    class Guarantee {
        private Integer guaranteeStatus;
        private Integer LEACode;

        void setGuaranteeStatus(Integer status) {
            this.guaranteeStatus = status;
        }

        void setLEACode(Integer code) {
            this.LEACode = code;
        }
    }
    Guarantee year11;
    Guarantee year12;
}
