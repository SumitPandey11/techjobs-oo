package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.data.JobData;
import org.launchcode.models.forms.JobForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // DONE TODO #1 - get the Job with the given ID and pass it into the view
        Job job = JobData.getInstance().findById(id);
        model.addAttribute("job",job);
        //TODO #1 - END

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // DONE TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if(errors.hasErrors()){
            model.addAttribute(jobForm);
            return "new-job";
        }

        String name = jobForm.getName();
        Employer employer = JobData.getInstance().getEmployers().findById(jobForm.getEmployerId());
        Location location = JobData.getInstance().getLocations().findById(jobForm.getLocationId());
        PositionType positionType = JobData.getInstance().getPositionTypes().findById(jobForm.getPositionTypeId());
        CoreCompetency skill = JobData.getInstance().getCoreCompetencies().findById(jobForm.getCoreCompetencyId());

        Job newJob = new Job(name, employer, location, positionType,skill);
        JobData.getInstance().add(newJob);
        int jobId = newJob.getId();
        //model.addAttribute(newJob);

        return "redirect:/job?id="+jobId;
        // TODO #6 - END
    }
}
