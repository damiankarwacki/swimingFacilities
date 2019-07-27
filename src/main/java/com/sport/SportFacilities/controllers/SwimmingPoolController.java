package com.sport.SportFacilities.controllers;

import com.sport.SportFacilities.models.SwimmingPool;
import com.sport.SportFacilities.services.SwimmingPoolService;
import com.sport.SportFacilities.utils.HateoasUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/swimming-pools")
@AllArgsConstructor
public class SwimmingPoolController {

    private SwimmingPoolService swimmingPoolService;
    private HateoasUtils hateoasUtils;

    @Autowired
    public SwimmingPoolController(SwimmingPoolService swimmingPoolService) {
        this.swimmingPoolService = swimmingPoolService;
        this.hateoasUtils = new HateoasUtils();
    }

    @PostMapping()
    public ResponseEntity createSwimmingPool(@Valid @RequestBody SwimmingPool swimmingPool){
        SwimmingPool createdSwimmingPool = swimmingPoolService.createSwimmingPool(swimmingPool);
        URI uri = hateoasUtils.getUriWithPathAndParams("/{id}", createdSwimmingPool.getId());
        return ResponseEntity.created(uri).body(createdSwimmingPool);
    }

    @GetMapping("/{id}")
    public ResponseEntity getSwimmingPoolById(@PathVariable Integer id){
        SwimmingPool swimmingPool = swimmingPoolService.getSwimmingPoolById(id);
        Resource<SwimmingPool> resource = new Resource<>(swimmingPool);
        Link linkToAllInstructors = linkTo(methodOn(SwimmingPoolController.class).getAllSwimmingPools()).withRel("all-swimming-pools");
        resource.add(linkToAllInstructors);
        return ResponseEntity.ok(resource);
    }

    @GetMapping()
    public ResponseEntity getAllSwimmingPools() {
        Set<SwimmingPool> swimmingPools = swimmingPoolService.getAllSwimmingPools();
        return ResponseEntity.ok(swimmingPools);
    }

    @GetMapping(params={"depth"})
    public ResponseEntity getSwimmingPoolsByDepth(@RequestParam Float depth){
        Set<SwimmingPool> swimmingPoolsByDepth = swimmingPoolService.getAllSwimmingPoolsByDepth(depth);
        return ResponseEntity.ok(swimmingPoolsByDepth);
    }

    @GetMapping(params = {"lanes"})
    public ResponseEntity getSwimmingPoolsByLanesQuantity(@RequestParam Integer lanes){
        Set<SwimmingPool> swimmingPoolsByLanesQuantity = swimmingPoolService.getAllSwimmingPoolsByLanesQuantity(lanes);
        return ResponseEntity.ok(swimmingPoolsByLanesQuantity);
    }

    @PutMapping("/{id}")
    public ResponseEntity editSwimmingPool(@RequestBody SwimmingPool swimmingPool, @PathVariable Integer id){
        SwimmingPool editedSwimmingPool = swimmingPoolService.editSwimmingPool(swimmingPool, id);
        return ResponseEntity.ok(editedSwimmingPool);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSwimmingPool(@PathVariable Integer id){
        SwimmingPool swimmingPoolToDelete = swimmingPoolService.getSwimmingPoolById(id);
        swimmingPoolService.deleteSwimmingPool(swimmingPoolToDelete);
        return ResponseEntity.ok().build();
    }
}
