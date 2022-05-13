package com.revature.models;

/**
 * Reimbursement Types:
 * 1. Lodging
 * 2. Travel
 * 3. Food
 * 4. Other
 *
 */
public enum ReimbType {

    LODGING {
        @Override
        public String toString() {
            return "Lodging";
        }
    },
    TRAVEL {
        @Override
        public String toString() {
            return "Travel";
        }
    },
    FOOD {
        @Override
        public String toString() {
            return "Food";
        }
    },
    OTHER {
        @Override
        public String toString() {
            return "Other";
        }
    }
}
