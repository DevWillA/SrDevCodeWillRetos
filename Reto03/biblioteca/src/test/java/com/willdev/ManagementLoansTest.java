package com.willdev;

import org.junit.jupiter.api.Test;

public class ManagementLoansTest {

    @Test
    void testAddLoan() {
        ManagementLoans managementLoans = new ManagementLoans(null, null);
        managementLoans.addLoans("1", "1");
        managementLoans.addLoans("2", "2");
        managementLoans.addLoans("3", "3");
    }

    @Test
    void testReturnLoan() {
        ManagementLoans managementLoans = new ManagementLoans(null, null);
        managementLoans.addLoans("1", "1");
        managementLoans.addLoans("2", "2");
        managementLoans.addLoans("3", "3");
        managementLoans.returLoans(1, 1);
    }



    
}
