package one.digitalinnovation.personapi.controller;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import one.digitalinnovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/people")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonController {

  private PersonService personService;

  @ApiOperation("List all person saved")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<PersonDTO> findAllPerson() {
    return personService.findAllPerson();
  }

  @ApiOperation("Save a person")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public MessageResponseDTO createPerson(@RequestBody @Valid PersonDTO personDTO) {
    return personService.createPerson(personDTO);
  }

  @ApiOperation("Update a person by id")
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public MessageResponseDTO updatePerson(@PathVariable Long id, @RequestBody @Valid PersonDTO personDTO) {
    return personService.updatePerson(id, personDTO);
  }

  @ApiOperation("Find a person by id")
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public PersonDTO findById (@PathVariable Long id) {
      return personService.findPersonById(id);
  }

  @ApiOperation("Delete a person by id")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletePersonById(@PathVariable Long id) {
    personService.deletePersonById(id);
  }
}
