package one.digitalinnovation.personapi.service;

import one.digitalinnovation.personapi.dto.MessageResponseDTO;
import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import one.digitalinnovation.personapi.mapper.PersonMapper;
import one.digitalinnovation.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

  private PersonRepository personRepository;

  private final PersonMapper personMapper = PersonMapper.INSTANCE;

  @Autowired
  public PersonService(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  public MessageResponseDTO createPerson(PersonDTO personDTO) {
    Person personToSave = personMapper.toModel(personDTO);
    Person savedPerson = personRepository.save(personToSave);
    return MessageResponseDTO
            .builder()
            .message("Created person with ID " + savedPerson.getId())
            .build();
  }

  public List<PersonDTO> findAllPerson() {
    List<Person> allPerson = personRepository.findAll();
    return allPerson.stream().map(personMapper::toDTO).collect(Collectors.toList());
  }

  public PersonDTO findPersonById(Long id) {
      return personMapper.toDTO(verifyIfExists(id));
  }

  public void deletePersonById(Long id) {
    verifyIfExists(id);
    personRepository.deleteById(id);
  }

  private Person verifyIfExists(Long id) {
    return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
  }
}
