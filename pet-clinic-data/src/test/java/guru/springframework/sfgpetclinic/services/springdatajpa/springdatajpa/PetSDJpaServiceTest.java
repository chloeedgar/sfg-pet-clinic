package guru.springframework.sfgpetclinic.services.springdatajpa.springdatajpa;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.respositories.OwnerRepository;
import guru.springframework.sfgpetclinic.respositories.PetRepository;
import guru.springframework.sfgpetclinic.respositories.PetTypeRepository;
import guru.springframework.sfgpetclinic.services.springdatajpa.PetSDJpaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PetSDJpaServiceTest {

    @Mock
    PetRepository petRepository;

    @Mock
    PetTypeRepository petTypeRepository;

    @Mock
    OwnerRepository ownerRepository;

    @InjectMocks
    PetSDJpaService petService;

    Pet pet;


    @BeforeEach
    void setUp() {
        pet = Pet.builder().id(1L).build();
    }

    @Test
    void findAll() {
        // todo - impl
        Set<Pet> returnPetSet = new HashSet<>();
        returnPetSet.add(Pet.builder().id(1L).build());
        returnPetSet.add(Pet.builder().id(2L).build());

        when(petRepository.findAll()).thenReturn(returnPetSet);

        Set<Pet> pets = petService.findAll();

        assertNotNull(pets);
        assertEquals(2, pets.size());
    }


    @Test
    void findById() {
        Long petId = pet.getId();

        when(petRepository.findById(anyLong())).thenReturn(Optional.of(pet));

        Pet pet = petService.findById(petId);

        assertNotNull(pet);
        assertEquals(petId, pet.getId());

    }

    @Test
    void findByIdNotFound() {
        when(petRepository.findById(anyLong())).thenReturn(Optional.empty());

        Pet pet = petService.findById(4L);

        assertNull(pet);
    }

    @Test
    void save() {
        Pet petToSave = Pet.builder().id(1L).build();
        when(petRepository.save(petToSave)).thenReturn(pet);

        Pet savedPet = petService.save(petToSave);

        assertNotNull(savedPet);

        verify(petRepository).save(any());

    }

    @Test
    void delete() {
        //doesn't return a value, good example of using verify
        petService.delete(pet);
        //Verify the ownerRepository delete method is called one time
        verify(petRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        petService.deleteById(anyLong());
        //default times is once
        verify(petRepository).deleteById(anyLong());
    }

}
