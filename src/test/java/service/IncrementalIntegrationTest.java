package service;

import domain.Nota;
import domain.Pair;
import domain.Student;
import domain.Tema;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import static org.mockito.Mockito.*;

public class IncrementalIntegrationTest {

    @Mock
    private StudentValidator studentValidator;

    @Mock
    private TemaValidator temaValidator;

    @Mock
    private StudentXMLRepository studentXMLRepository;

    @Mock
    private TemaXMLRepository temaXMLRepository;

    @Mock
    private NotaXMLRepository notaXMLRepository;

    @Mock
    private Service service;

    @BeforeEach
    public void setup() {

        service = mock(Service.class);
        temaXMLRepository = mock(TemaXMLRepository.class);
        studentXMLRepository = mock(StudentXMLRepository.class);
        notaXMLRepository = mock(NotaXMLRepository.class);

        //service = new Service(studentXMLRepository, temaXMLRepository, notaXMLRepository);
    }


    @Test
    public void testAddStudent() {

        System.out.println("Test student - add");

        Student s1 = new Student("", "ana", 931);

        try {
            doThrow(new ValidationException("Nume incorect!")).when(service).saveStudent(s1.getID(), s1.getNume(), s1.getGrupa());
        } catch (ValidationException e) {
            e.printStackTrace();
        }

        try {
            Assertions.assertThrows(ValidationException.class, () -> service.saveStudent(s1.getID(), s1.getNume(), s1.getGrupa()));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testAddStudentAndAssignment() {

        System.out.println("Test student and tema - add");

        Student s1 = new Student("222", "ana", 931);
        Tema tema1 = new Tema("223", "", 1, 1);

        try {
            when(studentXMLRepository.save(s1)).thenReturn(null);
            doThrow(new ValidationException("Descriere invalida!")).when(service).saveTema(tema1.getID(), tema1.getDescriere(), tema1.getStartline(), tema1.getDeadline());
        } catch (ValidationException e) {
            e.printStackTrace();
        }

        try {
            int s1_test = service.saveStudent(s1.getID(), s1.getNume(), s1.getGrupa());
            Assertions.assertEquals(s1_test, 0);
            Assertions.assertThrows(ValidationException.class, () -> service.saveTema(tema1.getID(), tema1.getDescriere(), tema1.getStartline(), tema1.getDeadline()));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testAddStudentAndAssignmentAndGrade() {

        System.out.println("Test student and tema and nota - add");

        Student s1 = new Student("222", "ana", 931);
        Tema tema1 = new Tema("222", "a", 1, 2);
        Nota nota1 = new Nota(new Pair<String, String>("222", "222"), 10, 1, "good");


        try {
            when(studentXMLRepository.save(s1)).thenReturn(null);

            when(temaXMLRepository.save(tema1)).thenReturn(null);

            when(studentXMLRepository.findOne(nota1.getID().getObject1())).thenReturn(s1);
            when(temaXMLRepository.findOne(nota1.getID().getObject2())).thenReturn(tema1);
        } catch (ValidationException e) {
            e.printStackTrace();
        }

        try {
            int s1_test = service.saveStudent(s1.getID(), s1.getNume(), s1.getGrupa());
            Assertions.assertEquals(0, s1_test);
            int tema1_test = service.saveTema(tema1.getID(), tema1.getDescriere(), tema1.getStartline(), tema1.getDeadline());
            Assertions.assertEquals(0, tema1_test);

            int nota1_test = service.saveNota(nota1.getID().getObject1(), nota1.getID().getObject2(), nota1.getNota(), nota1.getSaptamanaPredare(), "ok");
            Assertions.assertEquals(10.0, nota1.getNota());
            Assertions.assertEquals(0, nota1_test);

        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

}
