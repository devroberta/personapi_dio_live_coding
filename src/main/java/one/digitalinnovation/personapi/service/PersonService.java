package one.digitalinnovation.personapi.service;

import lombok.AllArgsConstructor;
import one.digitalinnovation.personapi.dto.response.MessageResponseDTO;
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
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

  private PersonRepository personRepository;

  private final PersonMapper personMapper = PersonMapper.INSTANCE;

  public MessageResponseDTO createPerson(PersonDTO personDTO) {
    Person personToSave = personMapper.toModel(personDTO);
    Person savedPerson = personRepository.save(personToSave);
    return createMessageResponse(savedPerson.getId(), "Created person with ID ");
  }

  public MessageResponseDTO updatePerson(Long id, PersonDTO personDTO) {
    verifyIfExists(id);
    Person updatePerson = personRepository.save(personMapper.toModel(personDTO));
    return createMessageResponse(updatePerson.getId(), "Update person with ID ");
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

  private MessageResponseDTO createMessageResponse(Long id, String message) {
    return MessageResponseDTO.builder().message(message + id).build();
  }
}
