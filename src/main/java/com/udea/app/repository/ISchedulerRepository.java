package com.udea.app.repository;

import com.udea.app.models.Scheduler;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;

public interface ISchedulerRepository extends MongoRepository<Scheduler, String> {

    Scheduler findByDateAndHour(Date date, String hour);
}
