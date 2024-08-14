package net.hasni.ensetdemospringangular.service;

import net.hasni.ensetdemospringangular.dto.CoursDTO;
import net.hasni.ensetdemospringangular.entities.Cours;
import net.hasni.ensetdemospringangular.entities.Payment;
import net.hasni.ensetdemospringangular.repository.CoursRepository;
import net.hasni.ensetdemospringangular.servicesImpl.CoursServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CoursServiceTests {

    @Mock
    private CoursRepository coursRepository;

    @InjectMocks
    private CoursServiceImpl coursService;

    private Cours cour1;
    private Cours cour2;
    private CoursDTO courDTO;
    private Cours cour;

    @BeforeEach
    void setUp() {
        cour1 = Cours.builder()
                .titre("Francais")
                .date("22/05/2024")
                .heure("10h")
                .obligatoire(true)
                .build();

        cour2 = Cours.builder()
                .titre("Anglais")
                .date("03/11/2024")
                .heure("13h")
                .obligatoire(true)
                .build();

        courDTO = CoursDTO.builder()
                .titre("Math")
                .date("06/02/2024")
                .heure("17h")
                .obligatoire(true)
                .build();
        cour = Cours.builder()
                .titre("Math")
                .date("06/02/2024")
                .heure("17h")
                .obligatoire(true)
                .build();
    }

    @Test
    public void CreateCour_ReturnCour() throws IOException {

        // Given: création de l'objet CourDTO dans BeforEach
        Mockito.when(coursRepository.save(Mockito.any(Cours.class))).thenReturn(cour);

        // When
        Cours savedCour = coursService.saveCour(courDTO);

        //Then
        Assertions.assertThat(savedCour).isNotNull();
        Assertions.assertThat(savedCour.getTitre()).isEqualTo("Math");
    }

    @Test
    public void GetListCours_ReturnListCours() throws IOException {

        // Given: les objets cours dans BeforEach
        List<Cours> listCours = List.of(cour1, cour2);
        Mockito.when(coursRepository.findAll()).thenReturn(listCours);

        // When
        List<Cours> list = coursService.listCours();

        //Then
        Assertions.assertThat(list).isNotEmpty();
        Assertions.assertThat(list.size()).isEqualTo(2);
    }
}
