package com.bridgelabz.payrolldatabase;

/**
 * @desc Custom exception for Payroll Database operations
 */
public class PayrollDBException extends Exception {

    public PayrollDBException(String message, Throwable cause) {
        super(message, cause);
    }
}
