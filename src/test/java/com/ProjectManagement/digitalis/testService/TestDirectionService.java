package com.ProjectManagement.digitalis.testService;

import com.ProjectManagement.digitalis.dto.DirectionDto;
import com.ProjectManagement.digitalis.entitie.Direction;
import com.ProjectManagement.digitalis.entitie.Organisation;
import com.ProjectManagement.digitalis.repositorie.DirectionRepository;
import com.ProjectManagement.digitalis.repositorie.OrganisationRepository;
import com.ProjectManagement.digitalis.service.DirectionService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Aguibou sow
 * @date 2024-12-05 14:17
 * @package com.ProjectManagement.digitalis.testService
 * @project digitalis
 */
public class TestDirectionService {

    @Mock
    private DirectionRepository directionRepository;

    @Mock
    private OrganisationRepository organisationRepository;

    @InjectMocks
    private DirectionService directionService;

    private static final String ANSI_RESET = "\u001B[0m";
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testSaveDirectionSuccess(){
        DirectionDto directionDto = new DirectionDto();
        directionDto.setNomDirection("testDirection1");
        directionDto.setOrganisation(1L);

        Organisation organisation = new Organisation();
        organisation.setIdOrganisation(1L);
        organisation.setNomOrganisation("orgTest 1");

        Direction direction = new Direction();
        direction.setNomDirection(directionDto.getNomDirection());
        direction.setOrganisation(organisation);


        when(organisationRepository.findById(1L))
                .thenReturn(Optional.of(organisation));

        when(directionRepository.save(any(Direction.class)))
                .thenReturn(direction);


        Direction savedDidrection = directionService.save(directionDto);

        Assertions.assertNotNull(savedDidrection);
        Assertions.assertEquals("testDirection1",savedDidrection.getNomDirection());

        verify(organisationRepository,times(1)).findById(1L);
        verify(directionRepository,times(1)).save(any(Direction.class));
    }

    @Test
    public void testSaveDirectionFailed(){
        DirectionDto directionDto = new DirectionDto();
        directionDto.setNomDirection("testDirection1");
        directionDto.setOrganisation(1L);

        when(organisationRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException runtimeException = Assertions.assertThrows(
                RuntimeException.class,()->{
                        directionService.save(directionDto);
                });
        Assertions.assertEquals("organisation indiqué n'existe pas !",runtimeException.getMessage());
        System.out.println(runtimeException.getMessage()+ANSI_RESET);
        verify(organisationRepository,times(1)).findById(1L);
        verify(directionRepository,never()).save(any(Direction.class));
    }

    @Test
    void testGetDirectionSuccess() {
        // GIVEN
        Direction direction = new Direction();
        direction.setIdDirection(1L);
        direction.setNomDirection("Direction 1");

        when(directionRepository.findById(1L)).thenReturn(Optional.of(direction));

        // WHEN
        Direction foundDirection = directionService.getDirection(1L);

        // THEN
        Assertions.assertNotNull(foundDirection);
        Assertions.assertEquals("Direction 1", foundDirection.getNomDirection());
        verify(directionRepository, times(1)).findById(1L);
    }

    @Test
    void testGetDirectionNotFound() {

        when(directionRepository.findById(1L)).thenReturn(Optional.empty());


        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            directionService.getDirection(1L);
        });

        Assertions.assertEquals("aucune direction correspondante !", exception.getMessage());
        System.out.println(exception.getMessage());
        verify(directionRepository, times(1)).findById(1L);
    }



    @Test
    void testDeleteDirection_Success() {
        // GIVEN
        Direction direction = new Direction();
        direction.setIdDirection(1L);

        when(directionRepository.findById(1L)).thenReturn(Optional.of(direction));
        doNothing().when(directionRepository).delete(direction);

        // WHEN
        directionService.deleteDirection(1L);

        // THEN
        verify(directionRepository, times(1)).findById(1L);
        verify(directionRepository, times(1)).delete(direction);
    }

    @Test
    void testDeleteDirectionNotFound() {
        // GIVEN
        when(directionRepository.findById(1L)).thenReturn(Optional.empty());

        // WHEN & THEN
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            directionService.deleteDirection(1L);
        });

        Assertions.assertEquals("Erreur d'identifiant lors de la suppression", exception.getMessage());
        System.out.println(exception.getMessage());
        verify(directionRepository, times(1)).findById(1L);
        verify(directionRepository, never()).delete(any(Direction.class));
    }


    @Test
    void testEditDirectionSuccess() {
        // GIVEN
        Direction existingDirection = new Direction();
        existingDirection.setIdDirection(1L);
        existingDirection.setNomDirection("direction_name");

        Direction updatedDirection = new Direction();
        updatedDirection.setNomDirection("NewName Direction");

        when(directionRepository.findById(1L)).thenReturn(Optional.of(existingDirection));
        when(directionRepository.save(any(Direction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        Direction result = directionService.editDirection(1L, updatedDirection);

        // THEN
        Assertions.assertNotNull(result);
        Assertions.assertEquals("NewName Direction", result.getNomDirection());

        verify(directionRepository, times(1)).findById(1L);
        verify(directionRepository, times(1)).save(existingDirection);
    }


}
