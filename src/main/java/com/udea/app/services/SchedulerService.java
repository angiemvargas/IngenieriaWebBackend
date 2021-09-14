package com.udea.app.services;

import com.udea.app.models.Pet;
import com.udea.app.models.Scheduler;
import com.udea.app.repository.ISchedulerRepository;
import com.udea.app.services.dtos.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/scheduler")
@RestController
public class SchedulerService {

    @Autowired
    ISchedulerRepository schedulerRepository;

    private final static String CITA_NOT_FOUND = "Error: la cita no existe";

    @PostMapping
    public ResponseEntity createAppointment(@RequestBody Scheduler scheduler){

        if (Objects.isNull(scheduler.getDate()) || Objects.isNull(scheduler.getHour())
                || Objects.isNull(scheduler.getTeacher()) || Objects.isNull(scheduler.getIdPet())) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.builder()
                            .message("Error: Faltan datos para crear la cita")
                            .build());
        }

        Scheduler appointment = schedulerRepository.findByDateAndHour(scheduler.getDate(), scheduler.getHour());

        if (Objects.nonNull(appointment)){
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(MessageResponse.builder()
                            .message("Error: Ya exite una cita en este horario")
                            .build());
        }

        schedulerRepository.save(scheduler);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(MessageResponse.builder()
                        .message("La cita se a creado exitosamente")
                        .build());

    }

    @PutMapping
    public ResponseEntity updateAppointment(@RequestBody Scheduler scheduler) {

        if (Objects.isNull(scheduler.getId()) || scheduler.getId().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.builder()
                            .message("Error: es necesario el id de la cita")
                            .build());
        }

        Scheduler schedulerMongo = schedulerRepository.findById(scheduler.getId())
                .orElse(Scheduler.builder().build());

        if (Objects.isNull(schedulerMongo.getHour())) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.builder()
                            .message(CITA_NOT_FOUND)
                            .build());
        }

        Scheduler appointment = schedulerRepository.findByDateAndHour(scheduler.getDate(), scheduler.getHour());

        if (Objects.nonNull(appointment)){
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(MessageResponse.builder()
                            .message("Error: Ya exite una cita en este horario")
                            .build());
        }

        schedulerRepository.save(scheduler);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(MessageResponse.builder()
                        .message("La cita se a actualizado exitosamente")
                        .build());
    }

    @GetMapping
    public ResponseEntity getAllAppointment(){

        List<Scheduler> allAppointment = schedulerRepository.findAll();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(allAppointment);
    }

    @GetMapping("/{id}")
    public ResponseEntity getAppointmentById(@PathVariable String id){

        Scheduler appointment = schedulerRepository.findById(id).orElse(Scheduler.builder().build());

        if (Objects.isNull(appointment.getHour())) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.builder()
                            .message(CITA_NOT_FOUND)
                            .build());
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(appointment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAppointmentById(@PathVariable String id) {

        Scheduler appointment = schedulerRepository.findById(id).orElse(Scheduler.builder().build());

        if (Objects.isNull(appointment.getHour())) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.builder()
                            .message(CITA_NOT_FOUND)
                            .build());
        }

        schedulerRepository.deleteById(id);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(MessageResponse.builder()
                        .message("La cita ha sido eliminada con exito")
                        .build());
    }
}
