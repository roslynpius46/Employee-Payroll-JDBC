import com.bridgelabz.payrolldatabase.EmployeePayroll;
import com.bridgelabz.payrolldatabase.DBConnection;
import com.bridgelabz.payrolldatabase.PayrollDBException;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.assertTrue;
//import static org.assertj.core.api.Assertions.assertThat;

/**
 * JUnit test for EmployeePayroll class
 */
public class EmployeePayrollTest {

    private Connection connection;

    @Before
    public void setUp() {
        // Set up before each test case (e.g., create a database connection)
        connection = DBConnection.getConnection();
    }

    @After
    public void tearDown() {
        // Tear down after each test case (e.g., close the database connection)
        DBConnection.closeConnection();
    }

    @Test
    public void testCompareEmployeeWithDB() throws PayrollDBException {
        // Arrange
        EmployeePayroll payrollService = new EmployeePayroll(connection);
        String employeeName = "Terissa";

        // Act
        boolean isDataMatching = payrollService.compareEmployeeWithDB(employeeName);

        // Assert
        assertTrue("Data does not match with DB for Employee Name: " + employeeName, isDataMatching);
    }


}
