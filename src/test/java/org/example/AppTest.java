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
}