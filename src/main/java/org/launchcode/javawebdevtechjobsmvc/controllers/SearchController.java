package org.launchcode.javawebdevtechjobsmvc.controllers;

import org.launchcode.javawebdevtechjobsmvc.models.Job;
import org.launchcode.javawebdevtechjobsmvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.launchcode.javawebdevtechjobsmvc.controllers.ListController.columnChoices;
import static org.launchcode.javawebdevtechjobsmvc.models.JobData.findAll;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController extends TechJobsController {

    @RequestMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        String searchCategory = "all";
        model.addAttribute("searchCategory", searchCategory);

        return "search";
    }

    // TODO #3 - Create a handler to process a search request and render the updated search view. COMPLETE
    @RequestMapping(value = "results")
    public String processSearchAndUpdateView(Model model, @RequestParam String searchType, @RequestParam String searchTerm) {
        ArrayList<Job> jobs;
        String searchCategory = "all";
        if (searchTerm.toLowerCase().equals("all")|| searchTerm.toLowerCase().equals("")){
            jobs = JobData.findAll();
            model.addAttribute("title", "All Jobs");
        }else {
            jobs = JobData.findByColumnAndValue(searchType, searchTerm);
            model.addAttribute("title", "Jobs with " + columnChoices.get(searchType) + ": " + searchTerm);
        }
        if (!searchType.equals("all")) {
            searchCategory = searchType;
            model.addAttribute("searchCategory", searchCategory);
        }
        model.addAttribute("columns", columnChoices);
        model.addAttribute("jobs", jobs);

        return "search";
    }
}
