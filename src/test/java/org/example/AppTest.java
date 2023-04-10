package org.example;
import junit.framework.TestCase;
import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import repository.*;

import service.Service;
import validation.*;

@RunWith(JUnit4.class)
public class AppTest extends TestCase {
    private Service service;

    @Before
    public void setUp() {
        Validator<Student> validator = new StudentValidator();
        Validator<Tema> validator1 = new TemaValidator();
        Validator<Nota> validator2 = new NotaValidator();
        StudentXMLRepository studentFileRepository = new StudentXMLRepository(validator, "studentiTest.xml");
        TemaXMLRepository temaFileRepository = new TemaXMLRepository(validator1, "teme.xml");
        NotaXMLRepository notaFileRepository = new NotaXMLRepository(validator2, "note.xml");

        service = new Service(studentFileRepository, temaFileRepository, notaFileRepository);
    }

    @Test
    public void addValidStudent() {
        assertEquals(service.saveStudent("10", "aaa", 222), 0);
    }

    @Test
    public void addInvalidStudent() {
        assertEquals(service.saveStudent("7", "invalid",7),1);
}

    @Test
    public void addStudent_validId(){
        assertEquals(service.saveStudent("15", "stud", 935), 0);
    }

    @Test
    public void addStudent_invalidId(){assertEquals(service.saveStudent("", "stud", 935), 1);}

    @Test
    public void addStudent_nullId(){
        assertEquals(service.saveStudent(null, "stud", 935), 1);
    }

    @Test
    public void addStudent_validName(){
        assertEquals(service.saveStudent("11", "stud", 935), 0);
    }

    @Test
    public void addStudent_invalidName(){
        assertEquals(service.saveStudent("11", "", 935), 1);
    }

    @Test
    public void addStudent_nullName(){assertEquals(service.saveStudent("1", null, 935), 1);}

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

    @Test
    public void addAssignment_nullId(){
        assertEquals(service.saveTema(null, "aaaa", 6, 4), 1);
    }

    @Test
    public void addAssignment_invalidId(){assertEquals(service.saveTema("", "aaaa", 6, 4), 1);}

    @Test
    public void addAssignment_nullDescription(){
        assertEquals(service.saveTema("1", null, 6, 4), 1);
    }

    @Test
    public void addAssignment_invalidDescription(){assertEquals(service.saveTema("1", "", 6, 4), 1);}

    @Test
    public void addAssignment_deadlineSmaller(){
        assertEquals(service.saveTema("1", "description", 0, 4), 1);
    }

    @Test
    public void addAssignment_deadlineBigger(){assertEquals(service.saveTema("1", "description", 15, 4), 1);}


    @Test
    public void addAssignment_startlineSmaller(){
        assertEquals(service.saveTema("1", "description", 4, 0), 1);
    }

    @Test
    public void addAssignment_startlineBigger(){assertEquals(service.saveTema("1", "description", 4, 15), 1);}

    @Test
    public void addValidAssignment(){assertEquals(service.saveTema("11", "description", 9, 7), 0);}

    @Test
    public void addDuplicatedAssignment(){
        assertEquals(service.saveTema("12", "description", 9, 7), 0);
        assertEquals(service.saveTema("12", "description", 9, 7),0);}
}



