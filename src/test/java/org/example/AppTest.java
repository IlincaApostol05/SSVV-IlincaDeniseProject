package org.example;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.After;
import org.junit.Before;
import repository.*;

import org.junit.Test;
import service.Service;
import validation.*;
/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }


    public class AppTest {

        private Service service;

        @Before
        public void setUp() {
            Validator<Student> validator = new StudentValidator();
            Validator<Tema> validator1 = new TemaValidator();
            Validator<Nota> validator2 = new NotaValidator();
            StudentXMLRepository studentFileRepository = new StudentXMLRepository(validator, "studentiTest.xml", false);
            TemaXMLRepository temaFileRepository = new TemaXMLRepository(validator1, "teme.xml");
            NotaXMLRepository notaFileRepository = new NotaXMLRepository(validator2, "note.xml");

            service = new Service(studentFileRepository, temaFileRepository, notaFileRepository);
        }

        /**
         * Rigorous Test :-)
         */
        @Test
        public void addValidStudent() {
            assertEquals(service.saveStudent("10", "aaa", 935), 0);
        }

        // invalid group
        @Test
        public void addInvalidStudent() {
            assertEquals(service.saveStudent("7", "invalid", 7), 1);
        }

        @Test
        public void addStudent_validId() {
            assertEquals(service.saveStudent("15", "stud", 935), 0);
        }

        @Test
        public void addStudent_invalidId() {
            assertEquals(service.saveStudent("", "stud", 935), 1);
        }

        @Test
        public void addStudent_nullId() {
            assertEquals(service.saveStudent(null, "stud", 935), 1);
        }

        @Test
        public void addStudent_validName() {
            assertEquals(service.saveStudent("11", "stud", 935), 0);
        }

        @Test
        public void addStudent_invalidName() {
            assertEquals(service.saveStudent("11", "", 935), 1);
        }

        @Test
        public void addStudent_nullName() {
            assertEquals(service.saveStudent("1", null, 935), 1);
        }

        @Test
        public void addStudent_BVA1() {
            assertEquals(service.saveStudent("10", "aaa", 110), 1);
        }

        @Test
        public void addStudent_BVA2() {
            assertEquals(service.saveStudent("10", "aaa", 111), 0);
        }

        @Test
        public void addStudent_BVA3() {
            assertEquals(service.saveStudent("10", "aaa", 112), 0);
        }

        @Test
        public void addStudent_BVA4() {
            assertEquals(service.saveStudent("10", "aaa", 936), 0);
        }

        @Test
        public void addStudent_BVA5() {
            assertEquals(service.saveStudent("10", "aaa", 937), 0);
        }

        @Test
        public void addStudent_BVA6() {
            assertEquals(service.saveStudent("10", "aaa", 938), 1);
        }
    }
}
